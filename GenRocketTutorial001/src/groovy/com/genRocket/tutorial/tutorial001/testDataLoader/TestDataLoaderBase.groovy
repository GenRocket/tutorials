package com.genRocket.tutorial.tutorial001.testDataLoader

import com.genRocket.utils.ScenarioRunner

/**
 * Created by htaylor on 1/17/15.
 */
class TestDataLoaderBase {
  static ACCESS_KEY = System.getenv()['GR_ACCESS_KEY']
  static SCENARIO_PATH = System.getenv()['GR_TUTORIAL_OO1']

  static runScenario(ScenarioParams params) {
    def result = ScenarioRunner.executeOverSocket(
        SCENARIO_PATH, ACCESS_KEY, params.scenario, params.scenarioDomain, params.inMemory, params.loopCount, params.host, params.port
    )

    if (result.responseType == 'data' || result.responseType == 'message') {
      return result.response
    } else {
      throw new Exception(result.response)
    }
  }
}