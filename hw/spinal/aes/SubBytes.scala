package aes

import spinal.core._

// component SubBytes <Tab>
// pin input AesState()
// pout output AesState()
case class SubBytes() extends Component {
  val io = new Bundle {
    val input  = in  port AesState()
    val output = out port AesState()
  }

  io.output.vals := io.input.mapVals(SBox(_))
}
