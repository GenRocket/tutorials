package com.genRocket.tutorial.tutorial001.testDataLoader

import com.genRocket.tutorial.tutorial001.Address
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO

/**
 * Created by htaylor on 1/15/15.
 */
class AddressTestDataLoader extends TestDataLoaderBase {
  static SCENARIO = "com.genRocket.Address.grs"
  static SCENARIO_DOMAIN = 'com.genRocket.Address.Address'

  static load() {
    def organizations = runScenario(new ScenarioParams(SCENARIO, SCENARIO_DOMAIN))
    def testData = []

    organizations.each { node ->
      def dto = new LoaderDTO()
      def address = new Address(node)

      dto.object = address
      testData.add(dto)
    }

    return testData
  }
}
