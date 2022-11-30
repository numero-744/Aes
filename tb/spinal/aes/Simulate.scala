package aes

import spinal.core.sim._

object Simulate extends App {
  val bench = Config.sim.compile(Aes())

  bench.doSim("tests") { dut =>
    import dut.clockDomain
    import dut.io._

    val period = 10
    clockDomain.forkStimulus(period)
    SimTimeout(1000 * period)

    clockDomain.waitActiveEdge(5)

    doTest(
      dut,
      text = "45732d747520636f6e66696ee865203f",
      key = "2b7e151628aed2a6abf7158809cf4f3c",
      keep = 3
    )

    clockDomain.waitActiveEdge(5)

    doTest(
      dut,
      text = "3243f6a8885a308d313198a2e0370734",
      key = "2b7e151628aed2a6abf7158809cf4f3c",
      expected = "3925841d02dc09fbdc118597196a0b32"
    )

    doTest(
      dut,
      text = "426f6e6a6f75722c206d6f6e64652021", // Bonjour, monde !
      key = "123456789abcdef0deadbeef10cdc0de"
    )

    clockDomain.waitActiveEdge(5)
    println("AES tests successful")
  }

  bench.doSim("empty") { dut => }

  def doTest(
      dut: Aes,
      text: String,
      key: String,
      expected: String = null,
      keep: Int = 0
  ) = {
    import spinal.lib._

    import dut.clockDomain
    import dut.io

    io.text  #= text.asHex
    io.key   #= key.asHex
    io.start #= true
    clockDomain.waitActiveEdgeWhere(!io.ready.toBoolean)
    io.text #= 0
    io.key  #= 0
    if (keep == 0)
      io.start #= false
    clockDomain.waitActiveEdgeWhere(io.ready.toBoolean)

    if (expected != null)
      assert(io.cipher.toBigInt == expected.asHex, message = "Bad result.")

    if (keep > 0) {
      clockDomain.waitActiveEdge(keep)
      io.start #= false
    }
  }
}
