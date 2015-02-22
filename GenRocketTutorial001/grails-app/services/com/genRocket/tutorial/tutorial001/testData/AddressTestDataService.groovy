package com.genRocket.tutorial.tutorial001.testData

import com.genRocket.tutorial.tutorial001.Address
import com.genRocket.tutorial.tutorial001.Namespaces
import com.genRocket.tutorial.tutorial001.UserAddress
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.testDataLoader.AddressTestDataLoader
import grails.transaction.Transactional

@Transactional
class AddressTestDataService {
  static transactional = true

  def testDataMapService
  def addressService
  def userTestDataService

  def loadData(Boolean useTestDataMap, User user = null) {
    println "Loading Addresses..."

    if (Address.count() == 0) {
      if (!user) {
        userTestDataService.loadData(useTestDataMap)

        if (!useTestDataMap) {
          user = User.first()
        }
      }

      def addresses = (LoaderDTO[]) AddressTestDataLoader.load()

      addresses.each { node ->
        def syntheticId = node.object.id
        def address = (Address) node.object

        addressService.save(address)

        if (useTestDataMap) {
          user = (User) testDataMapService.getDomain(Namespaces.USER, node.parentId)
        }

        UserAddress.create(user, address)

        if (useTestDataMap) {
          testDataMapService.save(Namespaces.ADDRESS, syntheticId, address.id)
        }
      }
    }
  }
}