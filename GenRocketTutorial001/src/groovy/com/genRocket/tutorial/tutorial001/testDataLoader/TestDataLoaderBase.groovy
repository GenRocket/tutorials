package com.genRocket.tutorial.tutorial001.testDataLoader

import com.genRocket.utils.ScenarioRunner

/**
 * Created by htaylor on 1/17/15.
 *
 * All test data loaders extend TestDataLoaderBase. It contains a helper
 * method to simplify passing the parameters to run a Scenario. The helper
 * method uses GenRocket’s ScenarioRunner.executeOverSocket() method; it
 * communicates over a socket with a running GenRocket SocketServer, normally
 * running on the same machine, to execute a Scenario.
 */
class TestDataLoaderBase {
  static ACCESS_KEY = System.getenv()['GR_ACCESS_KEY']
  static SCENARIO_PATH = System.getenv()['GR_TUTORIAL_001']

  static runScenario(ScenarioParams params) {
    def result = ScenarioRunner.executeOverSocket(
        SCENARIO_PATH, ACCESS_KEY, params.scenario, params.scenarioDomain, params.inMemory, params.loopCount, params.host, params.port
    )

    if (result.responseType == 'data' || result.responseType == 'message') {
      return result.response
    } else {
      throw new Exception((String) result.response)
    }
  }
}