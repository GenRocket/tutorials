package com.genRocket.tutorial.tutorial001.testDataLoader

import com.genRocket.tutorial.tutorial001.Organization
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO

/**
 * Created by htaylor on 2/15/15.
 */
class OrganizationTestDataLoader extends TestDataLoaderBase {
  static SCENARIO = "com.genRocket.Organization.grs"
  static SCENARIO_DOMAIN = 'com.genRocket.Organization.Organization'

  static load() {
    def organizations = runScenario(new ScenarioParams(SCENARIO, SCENARIO_DOMAIN))
    def testData = []

    organizations.each { node ->
      def dto = new LoaderDTO()
      def organization = new Organization(node)

      dto.object = organization
      testData.add(dto)
    }

    return testData
  }
}
