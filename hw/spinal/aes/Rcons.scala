package aes

import spinal.core._

object Rcons {
  def apply(i: UInt): UInt = i.mux(
    default -> U"8'h1",
    0x1     -> U"8'h2",
    0x2     -> U"8'h4",
    0x3     -> U"8'h8",
    0x4     -> U"8'h10",
    0x5     -> U"8'h20",
    0x6     -> U"8'h40",
    0x7     -> U"8'h80",
    0x8     -> U"8'h1b",
    0x9     -> U"8'h36"
  )
}
