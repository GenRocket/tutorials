package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.testDataLoader.AddressLoader

/**
 * Created by htaylor on 1/18/15.
 */
class AddressServiceIntegrationTests extends GroovyTestCase {
  def addressService
  def userTestDataService

  protected void setUp() {
    super.setUp()

    //userTestDataService.loadData()
  }

  protected void tearDown() {
    super.tearDown()
  }

  void testCreateDepartment() {
    userTestDataService.loadData()

    def addresses = (LoaderDTO[]) AddressLoader.load()
    def node = addresses[0]
    def address = ((Address) node.object)
    def user = User.first()

    addressService.create(user, address)

    assertNotNull "address.id should not be null", address.id
    assertNotNull "User should have an address", UserAddress.findByUserAndAddress(user, address)
  }
}
