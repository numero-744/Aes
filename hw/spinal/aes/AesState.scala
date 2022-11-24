package aes

import spinal.core._

case class AesState() extends Bundle {
  val n = 4

  val state = Vec(Vec(UInt(8 bits), n), n)

  // state is represented as columns:
  // state(0)(0)    state(1)(0)    state(2)(0)    state(3)(0)
  // state(0)(1)    state(1)(1)    state(2)(1)    state(3)(1)
  // state(0)(2)    state(1)(2)    state(2)(2)    state(3)(2)
  // state(0)(3)    state(1)(3)    state(2)(3)    state(3)(3)
  def cols = state

  // It can be transposed as rows, without cloning signals:
  // each UInt in the Vec is aliasing a UInt in state and can be assigned to.
  def rows = Vec(
    for (i <- 0 until n)
      yield Vec(
        for (j <- 0 until n)
          yield cols(j)(i)
      )
  )

  // It can be flattened, to work easily on values directly.
  // Once more, using aliasing.
  def vals = Vec(
    for {
      i <- 0 until n
      j <- 0 until n
    } yield state(i)(j)
  )

  // Vec is represented in bits with first value first (index 0 is MSB)
  // AES needs to have MSB of UInt value at position (0, 0).
  def asUIntParts = Vec(vals.reverse)

  // This representation can then just be considered as Bits to assign to/from UInt
  def asUInt = asUIntParts.asBits.asUInt

  // Helper functions for transformations over values/columns/rows
  def mapVals(f: UInt => UInt) = Vec(vals.map(f))
  def mapCols(f: Vec[UInt] => Vec[UInt]) = Vec(cols.map(f))
  def mapRows(f: Vec[UInt] => Vec[UInt]) = Vec(rows.map(f))
}

object AesState {
  def from(that: UInt): AesState = {
    val s = AesState()
    s.asUIntParts.assignFromBits(that.asBits)
    s
  }
}
