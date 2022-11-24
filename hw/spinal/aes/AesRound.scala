package aes

// importcore
import spinal.core._

// component AesRound <Tab>
// pin text, key AesState()
// pout cipher AesState()
// pin readText, enable, enableMixColumns Bool()
case class AesRound() extends Component {
  val io = new Bundle {
    // Data
    val text, key = in  port AesState()
    val cipher    = out port AesState()
    // Control
    val readText, enable, enableMixColumns = in port Bool()
  }

  val state = io.cipher

  val subBytes = SubBytes()
  subBytes.io.input := state

  val shiftRows = ShiftRows()
  shiftRows.io.input := subBytes.io.output

  val mixColumns = MixColumns()
  mixColumns.io.input  := shiftRows.io.output
  mixColumns.io.enable := io.enableMixColumns

  val addRoundKey = AddRoundKey()
  addRoundKey.io.text := io.readText ? io.text | mixColumns.io.output
  addRoundKey.io.key  := io.key

  state := RegNextWhen(addRoundKey.io.cipher, io.enable, init = state.getZero)
}
