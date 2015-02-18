package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.testDataLoader.UserTestDataLoader
import grails.test.mixin.TestMixin
import grails.test.mixin.integration.IntegrationTestMixin
import org.junit.After
import org.junit.Before

import static org.junit.Assert.*

@TestMixin(IntegrationTestMixin)
class UserServiceIntegrationTests {
  def userService

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}


  void testSaveUser() {
    def roles = (LoaderDTO[]) UserTestDataLoader.load()
    def node = roles[0]
    def role = ((User) node.object)

    userService.save(role)

    assertNotNull "User should have an id but does not", role.id
  }

  void testSaveUserUniqueConstraint() {
    def roles = (LoaderDTO[]) UserTestDataLoader.load()
    def node = roles[1]
    def role1 = ((User) node.object)

    userService.save(role1)

    assertNotNull "User1 should have an id but does not", role1.id

    node = roles[2]
    def role2 = ((User) node.object)
    role2.username = role1.username

    userService.save(role2)

    assertTrue "User2 should have errors", role2.hasErrors()
    assertNull "User2 should not have an id", role2.id
  }
}
