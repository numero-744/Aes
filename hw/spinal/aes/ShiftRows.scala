package aes

import spinal.core._

// component ShiftRows <Tab>
// pin input AesState()
// pout output AesState()
case class ShiftRows() extends Component {
  val io = new Bundle {
    val input  = in  port AesState()
    val output = out port AesState()
  }

  io.output.rows := Vec(
    for ((row, iRow) <- io.input.rows.zipWithIndex)
      yield Vec(
        for (iCol <- row.range)
          yield row((iCol + iRow) % row.length)
      )
  )
}
