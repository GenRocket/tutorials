package com.genRocket.tutorial.tutorial001.testDataLoaders

import com.genRocket.tutorial.tutorial001.Address
import com.genRocket.utils.ScenarioRunner
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO

/**
 * Created by htaylor on 1/15/15.
 */
class AddressLoader extends TestDataLoaderBase {
  static SCENARIO = "com.genRocket.Addresses.grs"
  static SCENARIO_DOMAIN = 'com.genRocket.Addresses.Address'

  static load() {
    def organizations = ScenarioRunner.executeOverSocket(SCENARIO_PATH, ACCESS_KEY, SCENARIO, SCENARIO_DOMAIN)
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
