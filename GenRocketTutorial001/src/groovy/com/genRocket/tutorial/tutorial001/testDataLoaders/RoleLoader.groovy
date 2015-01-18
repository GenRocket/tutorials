package com.genRocket.tutorial.tutorial001.testDataLoaders

import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.utils.ScenarioRunner


/**
 * Created by htaylor on 1/15/15.
 */
class RoleLoader extends TestDataLoaderBase {
  static SCENARIO = "com.genRocket.Roles.grs"
  static SCENARIO_DOMAIN = 'com.genRocket.Roles.Role'

  static load() {
    def organizations = ScenarioRunner.executeOverSocket(SCENARIO_PATH, ACCESS_KEY, SCENARIO, SCENARIO_DOMAIN)
    def requests = []

    organizations.each { node ->
      def dto = new LoaderDTO()
      def role = new Role(node)

      dto.object = role
      requests.add(dto)
    }

    return requests
  }

  public static void main(String[] args) {
    def results = load()

    println(results)
  }
}
