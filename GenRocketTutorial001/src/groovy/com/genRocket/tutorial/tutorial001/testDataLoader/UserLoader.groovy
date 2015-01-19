package com.genRocket.tutorial.tutorial001.testDataLoader

import com.genRocket.tutorial.tutorial001.Address
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.User

/**
 * Created by htaylor on 1/15/15.
 */
class UserLoader extends TestDataLoaderBase {
  static SCENARIO = "com.genRocket.Users.grs"
  static SCENARIO_DOMAIN = 'com.genRocket.Users.User'

  static load() {
    def organizations = runScenario(SCENARIO, SCENARIO_DOMAIN)
    def requests = []

    organizations.each { node ->
      def dto = new LoaderDTO()
      def user = new User(node)

      def address = new Address(node)
      address.addressOne = node.address

      def map = [user: user, address: address]

      dto.object = map
      requests.add(dto)
    }

    return requests
  }
}
