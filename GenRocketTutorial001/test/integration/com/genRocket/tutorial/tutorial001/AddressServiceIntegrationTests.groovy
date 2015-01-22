package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.testDataLoader.AddressTestDataLoader
import grails.test.mixin.integration.IntegrationTestMixin
import org.junit.After
import org.junit.Before
import grails.test.mixin.TestMixin
import static org.junit.Assert.*

@TestMixin(IntegrationTestMixin)
class AddressServiceIntegrationTests {
  def addressService
  def userTestDataService

  @Before
  public void setUp() {
    userTestDataService.loadData()
  }

  @After
  public void tearDown() {}

  void testCreateDepartment() {
    def addresses = (LoaderDTO[]) AddressTestDataLoader.load()
    def node = addresses[0]
    def address = ((Address) node.object)
    def user = User.first()

    addressService.create(user, address)

    assertNotNull "address.id should not be null", address.id
    assertNotNull "User should have an address", UserAddress.findByUserAndAddress(user, address)
  }
}
