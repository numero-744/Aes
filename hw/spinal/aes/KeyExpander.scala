package aes

import spinal.core._
import spinal.lib.fsm._

// component KeyExpander <Tab>
// pin enable, readKey Bool()
// pin firstKey AesState()
// pin rcon UInt(8 bits)
// pout roundKey AesState()
case class KeyExpander() extends Component {
  val io = new Bundle {
    // Control
    val enable, readKey = in port Bool()
    // Data
    val firstKey = in  port AesState()
    val rcon     = in  port UInt(8 bits)
    val roundKey = out port AesState()
  }

  val key = io.roundKey

  val rcon, rotword, sboxed = Vec(UInt(8 bits), 4)

  rcon := Vec(io.rcon, U"8'0", U"8'0", U"8'0")

  for (l <- 0 until rotword.length)
    rotword(l) := key.cols(3)((l + 1) % 4)

  sboxed := Vec(rotword.map(SBox.apply))

  val nextKey = AesState()
  nextKey.cols(0) := key.cols(0) ^ sboxed ^ rcon
  for (i <- 1 until nextKey.cols.length)
    nextKey.cols(i) := nextKey.cols(i - 1) ^ key.cols(i)

  val expandedKey = RegNextWhen(nextKey, io.enable, init = AesState().getZero)

  // This assigns io.roundKey because key is the same wire as io.roundKey
  key := io.readKey ? io.firstKey | expandedKey
}
