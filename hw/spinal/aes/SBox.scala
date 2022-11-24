package aes

import spinal.core._

// fncomp SBox input UInt UInt output <Tab>
object SBox {
  def apply(input: UInt): UInt = {
    val sBox = SBox()
    sBox.io.input := input
    sBox.io.output
  }
}

// component SBox <Tab>
// pin input UInt(8 bits)
// pout output UInt(8 bits)
case class SBox() extends Component {
  val io = new Bundle {
    val input  = in  port UInt(8 bits)
    val output = out port UInt(8 bits)
  }

  val sbox = Vec(UInt(8 bits), 256)

  io.output := sbox(io.input)

  sbox := Vec(
    // format: off
    U"8'x63", U"8'x7c", U"8'x77", U"8'x7b", U"8'xf2", U"8'x6b", U"8'x6f", U"8'xc5", U"8'x30", U"8'x01", U"8'x67", U"8'x2b", U"8'xfe", U"8'xd7", U"8'xab", U"8'x76",
    U"8'xca", U"8'x82", U"8'xc9", U"8'x7d", U"8'xfa", U"8'x59", U"8'x47", U"8'xf0", U"8'xad", U"8'xd4", U"8'xa2", U"8'xaf", U"8'x9c", U"8'xa4", U"8'x72", U"8'xc0",
    U"8'xb7", U"8'xfd", U"8'x93", U"8'x26", U"8'x36", U"8'x3f", U"8'xf7", U"8'xcc", U"8'x34", U"8'xa5", U"8'xe5", U"8'xf1", U"8'x71", U"8'xd8", U"8'x31", U"8'x15",
    U"8'x04", U"8'xc7", U"8'x23", U"8'xc3", U"8'x18", U"8'x96", U"8'x05", U"8'x9a", U"8'x07", U"8'x12", U"8'x80", U"8'xe2", U"8'xeb", U"8'x27", U"8'xb2", U"8'x75",
    U"8'x09", U"8'x83", U"8'x2c", U"8'x1a", U"8'x1b", U"8'x6e", U"8'x5a", U"8'xa0", U"8'x52", U"8'x3b", U"8'xd6", U"8'xb3", U"8'x29", U"8'xe3", U"8'x2f", U"8'x84",
    U"8'x53", U"8'xd1", U"8'x00", U"8'xed", U"8'x20", U"8'xfc", U"8'xb1", U"8'x5b", U"8'x6a", U"8'xcb", U"8'xbe", U"8'x39", U"8'x4a", U"8'x4c", U"8'x58", U"8'xcf",
    U"8'xd0", U"8'xef", U"8'xaa", U"8'xfb", U"8'x43", U"8'x4d", U"8'x33", U"8'x85", U"8'x45", U"8'xf9", U"8'x02", U"8'x7f", U"8'x50", U"8'x3c", U"8'x9f", U"8'xa8",
    U"8'x51", U"8'xa3", U"8'x40", U"8'x8f", U"8'x92", U"8'x9d", U"8'x38", U"8'xf5", U"8'xbc", U"8'xb6", U"8'xda", U"8'x21", U"8'x10", U"8'xff", U"8'xf3", U"8'xd2",
    U"8'xcd", U"8'x0c", U"8'x13", U"8'xec", U"8'x5f", U"8'x97", U"8'x44", U"8'x17", U"8'xc4", U"8'xa7", U"8'x7e", U"8'x3d", U"8'x64", U"8'x5d", U"8'x19", U"8'x73",
    U"8'x60", U"8'x81", U"8'x4f", U"8'xdc", U"8'x22", U"8'x2a", U"8'x90", U"8'x88", U"8'x46", U"8'xee", U"8'xb8", U"8'x14", U"8'xde", U"8'x5e", U"8'x0b", U"8'xdb",
    U"8'xe0", U"8'x32", U"8'x3a", U"8'x0a", U"8'x49", U"8'x06", U"8'x24", U"8'x5c", U"8'xc2", U"8'xd3", U"8'xac", U"8'x62", U"8'x91", U"8'x95", U"8'xe4", U"8'x79",
    U"8'xe7", U"8'xc8", U"8'x37", U"8'x6d", U"8'x8d", U"8'xd5", U"8'x4e", U"8'xa9", U"8'x6c", U"8'x56", U"8'xf4", U"8'xea", U"8'x65", U"8'x7a", U"8'xae", U"8'x08",
    U"8'xba", U"8'x78", U"8'x25", U"8'x2e", U"8'x1c", U"8'xa6", U"8'xb4", U"8'xc6", U"8'xe8", U"8'xdd", U"8'x74", U"8'x1f", U"8'x4b", U"8'xbd", U"8'x8b", U"8'x8a",
    U"8'x70", U"8'x3e", U"8'xb5", U"8'x66", U"8'x48", U"8'x03", U"8'xf6", U"8'x0e", U"8'x61", U"8'x35", U"8'x57", U"8'xb9", U"8'x86", U"8'xc1", U"8'x1d", U"8'x9e",
    U"8'xe1", U"8'xf8", U"8'x98", U"8'x11", U"8'x69", U"8'xd9", U"8'x8e", U"8'x94", U"8'x9b", U"8'x1e", U"8'x87", U"8'xe9", U"8'xce", U"8'x55", U"8'x28", U"8'xdf",
    U"8'x8c", U"8'xa1", U"8'x89", U"8'x0d", U"8'xbf", U"8'xe6", U"8'x42", U"8'x68", U"8'x41", U"8'x99", U"8'x2d", U"8'x0f", U"8'xb0", U"8'x54", U"8'xbb", U"8'x16"
    // format: on
  )
}
