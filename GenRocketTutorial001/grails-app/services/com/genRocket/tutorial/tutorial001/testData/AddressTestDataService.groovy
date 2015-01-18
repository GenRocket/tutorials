package com.genRocket.tutorial.tutorial001.testData

import grails.transaction.Transactional
import com.genRocket.tutorial.tutorial001.Address
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.testDataLoaders.UserLoader

@Transactional
class AddressTestDataService {
  static transactional = true

  def addressService
  def userTestDataService

  def loadData(User user = null) {
    println "Loading Addressses..."

    if (!user) {
      userTestDataService.loadData()
      user = User.first()
    }

    if (Address.count() == 0) {
      def addresses = (LoaderDTO[]) UserLoader.load()

      addresses.each { node ->
        def map = (Map) node.object

        addressService.add(user, map.address)
      }
    }
  }
}
