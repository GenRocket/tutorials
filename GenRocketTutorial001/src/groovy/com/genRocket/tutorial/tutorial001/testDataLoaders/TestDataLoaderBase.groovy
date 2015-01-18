package com.genRocket.tutorial.tutorial001.testDataLoaders

import com.genRocket.utils.ScenarioRunner

/**
 * Created by htaylor on 1/17/15.
 */
class TestDataLoaderBase {
  static ACCESS_KEY = System.getenv()['GR_ACCESS_KEY']
  static SCENARIO_PATH = System.getenv()['GR_TUTORIAL_OO1']

  static runScenario(scenario, scenarioDomain) {
    return ScenarioRunner.executeOverSocket(SCENARIO_PATH, ACCESS_KEY, scenario, scenarioDomain)
  }
}
