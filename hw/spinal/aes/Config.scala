package aes

import spinal.core._
import spinal.core.sim._

object Config {
  def spinal = SpinalConfig(
    targetDirectory = "hw/gen",
    defaultConfigForClockDomains = ClockDomainConfig(
      resetActiveLevel = LOW
    ),
    onlyStdLogicVectorAtTopLevelIo = true
  )

  def sim = SimConfig.withConfig(spinal).withFstWave

  abstract class TestBench extends SpinalTestBench {
    override def defaultConfig: SpinalSimConfig = sim
  }
}
