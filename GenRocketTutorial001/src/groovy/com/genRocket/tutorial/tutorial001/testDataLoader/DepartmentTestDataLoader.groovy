package com.genRocket.tutorial.tutorial001.testDataLoader

import com.genRocket.tutorial.tutorial001.Department
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO

/**
 * Created by htaylor on 1/15/15.
 */
class DepartmentTestDataLoader extends TestDataLoaderBase {
  static SCENARIO = "com.genRocket.Department.grs"
  static SCENARIO_DOMAIN = 'com.genRocket.Department.Department'

  static load() {
    def organizations = runScenario(new ScenarioParams(SCENARIO, SCENARIO_DOMAIN))
    def testData = []

    organizations.each { node ->
      def dto = new LoaderDTO()
      def parentId = Long.parseLong(node.organizationId)
      def department = new Department(node)

      dto.object = department
      dto.parentId = parentId
      testData.add(dto)
    }

    return testData
  }
}
