package aes

import spinal.core._

// component MixColumns <Tab>
// pin input AesState()
// pout output AesState()
// pin enable Bool()
case class MixColumns() extends Component {
  val io = new Bundle {
    val input  = in  port AesState()
    val output = out port AesState()
    val enable = in  port Bool()
  }

  val mul2, mul3, res = AesState()

  mul2.vals := io.input.mapVals { x =>
    val rotated = x rotateLeft 1
    val reduced = rotated ^ U"8'x1a"
    rotated(0) ? reduced | rotated
  }

  mul3.vals := mul2.vals ^ io.input.vals

  res.cols := Vec((io.input.cols, mul2.cols, mul3.cols).zipped.map {
    (cin, cm2, cm3) =>
      Vec(
        cm2(0) ^ cm3(1) ^ cin(2) ^ cin(3),
        cin(0) ^ cm2(1) ^ cm3(2) ^ cin(3),
        cin(0) ^ cin(1) ^ cm2(2) ^ cm3(3),
        cm3(0) ^ cin(1) ^ cin(2) ^ cm2(3)
      )
  })

  io.output := io.enable ? res | io.input
}
