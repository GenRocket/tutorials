package groovy.com.genRocket.tutorial.tutorial001.testDataLoaders

import com.genRocket.tutorial.tutorial001.Address
import com.genRocket.tutorial.tutorial001.Department
import com.genRocket.tutorial.tutorial001.Organization
import com.genRocket.tutorial.tutorial001.security.User
import groovy.com.genRocket.tutorial.tutorial001.dto.LoaderDTO

/**
 * Created by htaylor on 1/15/15.
 */
class OrganizationLoader extends TestDataLoaderBase {
  static SCENARIO = "com.genRocket.Organizations.grs"
  static SCENARIO_DOMAIN = 'com.genRocket.Organizations.Organization'

  static load() {
    def organizations = TestDataLoader.runScenario(SCENARIO_PATH, ACCESS_KEY, SCENARIO, SCENARIO_DOMAIN)
    def requests = []

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
      requests.add(dto)
    }

    return requests
  }

  public static void main(String[] args) {
    def results = load()

    println(results)
  }
}
