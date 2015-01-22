package com.genRocket.tutorial.tutorial001.testDataLoader

import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.Role

/**
 * Created by htaylor on 1/15/15.
 */
class RoleTestDataLoader extends TestDataLoaderBase {
  static SCENARIO = "com.genRocket.Roles.grs"
  static SCENARIO_DOMAIN = 'com.genRocket.Roles.Role'

  static load() {
    def organizations = runScenario(SCENARIO, SCENARIO_DOMAIN)
    def testData = []

    organizations.each { node ->
      def dto = new LoaderDTO()
      def role = new Role(node)

      dto.object = role
      testData.add(dto)
    }

    return testData
  }
}
