package groovy.com.genRocket.tutorial.tutorial001.testDataLoaders

import com.genRocket.utils.ScenarioRunner

/**
 * Created by htaylor on 1/15/15.
 */
abstract class GenRocketInMemoryLoaderBlank {

  static public List<Object> runScenario(String scenarioPath, String fullDomainName, Integer loopCount) {
    def engineDomain = ScenarioRunner.execute(scenarioPath, fullDomainName, loopCount).get(fullDomainName)

    return engineDomain.getReceiver(ScenarioRunner.SIMPLE_MAP_RECEIVER).getInMemoryResult()
  }

  static public List<Object> runScenario(String scenarioPath, String fullDomainName, loopCount = null) {
    def engineDomain

    if (loopCount) {
      engineDomain = ScenarioRunner.executeInMemory(scenarioPath, fullDomainName, loopCount).get(fullDomainName)
    } else {
      engineDomain = ScenarioRunner.executeInMemory(scenarioPath).get(fullDomainName)
    }

    return engineDomain.getReceiver(ScenarioRunner.SIMPLE_MAP_RECEIVER).getInMemoryResult()
  }
}