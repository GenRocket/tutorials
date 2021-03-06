package com.genRocket.tutorial.tutorial001.testDataLoader

import com.genRocket.tutorial.tutorial001.Address
import com.genRocket.tutorial.tutorial001.Department
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.User

/**
 * Created by htaylor on 1/15/15.
 */
class DepartmentWSTestDataLoader extends TestDataLoaderBase {
  static SCENARIO = "com.genRocket.DepartmentWS.grs"
  static SCENARIO_DOMAIN = 'com.genRocket.DepartmentWS.DepartmentWS'

  static load() {
    def organizations = runScenario(new ScenarioParams(SCENARIO, SCENARIO_DOMAIN))
    def testData = []

    organizations.each { node ->
      def dto = new LoaderDTO()
      def department = new Department(node)
      def user = new User(node)
      def address = new Address(node)

      address.addressOne = node.address

      def map = [department: department, user: user, address: address]

      dto.object = map
      testData.add(dto)
    }

    return testData
  }
}
