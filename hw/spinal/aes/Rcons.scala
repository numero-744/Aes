package aes

import spinal.core._

object Rcons {
  def apply(i: UInt): UInt = Vec(
    U"8'h1",
    U"8'h2",
    U"8'h4",
    U"8'h8",
    U"8'h10",
    U"8'h20",
    U"8'h40",
    U"8'h80",
    U"8'h1b",
    U"8'h36"
  )(i)
}
