package groovy.com.genRocket.tutorial.tutorial001.testDataLoaders

import com.genRocket.tutorial.tutorial001.Address
import groovy.com.genRocket.tutorial.tutorial001.dto.LoaderDTO

/**
 * Created by htaylor on 1/15/15.
 */
class AddressLoader extends TestDataLoaderBase {
  static SCENARIO = "com.genRocket.Address.grs"
  static SCENARIO_DOMAIN = 'com.genRocket.Address.Organization'

  static load() {
    def organizations = TestDataLoader.runScenario(SCENARIO_PATH, ACCESS_KEY, SCENARIO, SCENARIO_DOMAIN)
    def requests = []

    organizations.each { node ->
      def dto = new LoaderDTO()
      def address = new Address(node)
      address.addressOne = node.address

      def map = [address: address]

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
