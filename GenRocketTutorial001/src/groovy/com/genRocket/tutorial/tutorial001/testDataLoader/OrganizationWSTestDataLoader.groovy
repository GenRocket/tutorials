package com.genRocket.tutorial.tutorial001.testDataLoader

import com.genRocket.tutorial.tutorial001.Address
import com.genRocket.tutorial.tutorial001.Department
import com.genRocket.tutorial.tutorial001.Organization
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.User

/**
 * Created by htaylor on 1/15/15.
 */
class OrganizationWSTestDataLoader extends TestDataLoaderBase {
  static SCENARIO = "com.genRocket.OrganizationWS.grs"
  static SCENARIO_DOMAIN = 'com.genRocket.OrganizationWS.OrganizationWS'

  static load() {
    def organizations = runScenario(new ScenarioParams(SCENARIO, SCENARIO_DOMAIN))
    def testData = []

    organizations.each { node ->
      def dto = new LoaderDTO()
      def organization = new Organization(node)
      def user = new User(node)

      def address = new Address(node)
      address.addressOne = node.address

      def department = new Department()
      department.name = node.departmentName

      def map = [organization: organization, department: department, user: user, address: address]

      dto.object = map
      testData.add(dto)
    }

    return testData
  }
}
