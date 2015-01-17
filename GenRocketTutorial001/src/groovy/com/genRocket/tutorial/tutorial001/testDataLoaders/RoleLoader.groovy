package groovy.com.genRocket.tutorial.tutorial001.testDataLoaders

import com.genRocket.tutorial.tutorial001.security.Role
import groovy.com.genRocket.tutorial.tutorial001.dto.LoaderDTO


/**
 * Created by htaylor on 1/15/15.
 */
class RoleLoader extends TestDataLoaderBase {
  static SCENARIO = "com.genRocket.Role.grs"
  static SCENARIO_DOMAIN = 'com.genRocket.Role.Organization'

  static load() {
    def organizations = TestDataLoader.runScenario(SCENARIO_PATH, ACCESS_KEY, SCENARIO, SCENARIO_DOMAIN)
    def requests = []

    organizations.each { node ->
      def dto = new LoaderDTO()
      def role = new Role(node)

      def map = [role: role]

      dto.object = map
      requests.add(dto)
    }

    return requests
  }

  public static void main(String[] args) {
    def results = load()

    println(results)
  }
}
