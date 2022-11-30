package aes

import spinal.core.sim._
import spinal.core.BitVector

case class AesEnv(dut: Aes) {
  import dut.{clockDomain => cd}
  import dut.io

  val period = 10

  init()
  cd.forkStimulus(period)
  SimTimeout(1000 * period)
  cd.waitSampling()

  def init(): Unit = {
    io.text  #= 0
    io.key   #= 0
    io.start #= false
  }

  def cipher(text: BigInt, key: BigInt, keep: Int = 0): BigInt = {
    io.text  #= text
    io.key   #= key
    io.start #= true

    cd.waitActiveEdgeWhere(!io.ready.toBoolean)
    io.text #= 0
    io.key  #= 0
    if (keep == 0)
      io.start #= false

    cd.waitActiveEdgeWhere(io.ready.toBoolean)
    val cipher = io.cipher.toBigInt

    if (keep > 0) {
      cd.waitActiveEdge(keep)
      io.start #= false
    }

    cipher
  }
}

class AesTestBench extends SpinalTestBench {
  override def defaultConfig: SpinalSimConfig = Config.sim

  val aes = Dut(Aes()) withEnv AesEnv

  def x(str: String): BigInt = BigInt(str, 16)
  val key = x("2b7e151628aed2a6abf7158809cf4f3c")

  aes should "cipher example from class" inSim { env =>
    val c = env.cipher(x("45732d747520636f6e66696ee865203f"), key, keep = 3)
    assert(c != 0)
  }

  aes should "cipher example from website" inSim { env =>
    val c = env.cipher(x("3243f6a8885a308d313198a2e0370734"), key)
    assert(c == x("3925841d02dc09fbdc118597196a0b32"))
  }

  aes should "cipher 'Bonjour, monde!'" inSim { env =>
    val c = env.cipher(
      x("426f6e6a6f75722c206d6f6e64652021"),
      x("123456789abcdef0deadbeef10cdc0de")
    )
    assert(c != 0)
  }
}
