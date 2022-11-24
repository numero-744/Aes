package aes

object GenerateAes extends App {
  Config.spinal.generateSystemVerilog(Aes()).printPruned()
}
