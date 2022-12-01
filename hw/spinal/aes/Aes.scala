package aes

// importcore
import spinal.core._
// importlib fsm._
import spinal.lib.fsm._

// component Aes <Tab>
// pin start Bool()
// pin text UInt(128 bits)
// pin key UInt(128 bits)
// pout ready Bool()
// pout cipher UInt(128 bits)
case class Aes() extends Component {
  val io = new Bundle {
    val start  = in  port Bool()
    val text   = in  port UInt(128 bits)
    val key    = in  port UInt(128 bits)
    val ready  = out port Bool()
    val cipher = out port UInt(128 bits)
  }

  // doing useInputs usingInputs
  val usingInputs = False
  def useInputs(): Unit = usingInputs := True

  // doing run running
  val running = False
  def run(): Unit = running := True
  io.ready := !running

  val round = new Area {
    val number = RegInit(U(0, 4 bits))
    def resetCount = number := 0
    when(running) { number := number + 1 }
    val isLast = number === 9
    val rcon   = Rcons(number)
  }

  val keyExpander = KeyExpander()
  keyExpander.io.enable   := running
  keyExpander.io.readKey  := usingInputs
  keyExpander.io.firstKey := AesState.from(io.key)
  keyExpander.io.rcon     := round.rcon

  val aesRound = AesRound()
  aesRound.io.enable   := running
  aesRound.io.readText := usingInputs
  aesRound.io.text     := AesState.from(io.text)
  aesRound.io.key      := keyExpander.io.roundKey
  // fnmux aesRound.io.enableMixColumns True withoutMixColumns False
  aesRound.io.enableMixColumns := True
  def withoutMixColumns(): Unit = aesRound.io.enableMixColumns := False

  // fnmux io.cipher U(0) enableOutput aesRound.io.cipher.asUInt
  io.cipher := U(0)
  def enableOutput(): Unit = io.cipher := aesRound.io.cipher.asUInt

  // fsm fsm IDLE Rnd0, RndI, RndN, DONE
  val fsm = new StateMachine {
    val IDLE, Rnd0, RndI, RndN, DONE = new State
    setEntry(IDLE)

    IDLE.whenIsActive { when(io.start) { goto(Rnd0) } }
    Rnd0.whenIsActive { goto(RndI) }
    RndI.whenIsActive { when(round.isLast) { goto(RndN) } }
    RndN.whenIsActive { goto(DONE) }
    DONE.whenIsActive { when(!io.start) { goto(IDLE) } }

    IDLE.whenIsActive { round.resetCount }
    Rnd0.whenIsActive { run; useInputs }
    RndI.whenIsActive { run }
    RndN.whenIsActive { run; withoutMixColumns }
    DONE.whenIsActive { enableOutput }
  }
}

object AesGen extends App {
  Config.spinal.generateSystemVerilog(Aes())
}
