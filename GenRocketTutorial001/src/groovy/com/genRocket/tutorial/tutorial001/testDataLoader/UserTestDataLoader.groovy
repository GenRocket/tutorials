package com.genRocket.tutorial.tutorial001.testDataLoader

import com.genRocket.tutorial.tutorial001.Address
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.User

/**
 * Created by htaylor on 1/15/15.
 */
class UserTestDataLoader extends TestDataLoaderBase {
  static SCENARIO = "com.genRocket.User.grs"
  static SCENARIO_DOMAIN = 'com.genRocket.User.User'

  static load() {
    def users = runScenario(new ScenarioParams(SCENARIO, SCENARIO_DOMAIN))
    def testData = []

    users.each { node ->
      def dto = new LoaderDTO()
      def parentId = Long.parseLong(node.departmentId)
      def user = new User(node)

      dto.object = user
      dto.parentId = parentId
      testData.add(dto)
    }

    return testData
  }
}
