package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.testDataLoader.RoleTestDataLoader
import grails.test.mixin.integration.IntegrationTestMixin
import org.junit.After
import org.junit.Before
import grails.test.mixin.TestMixin
import static org.junit.Assert.*

@TestMixin(IntegrationTestMixin)
class RoleServiceIntegrationTests {
  def roleService

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  void testCreateRole() {
    def roles = (LoaderDTO[]) RoleTestDataLoader.load()
    def node = roles[0]
    def role = ((Role) node.object)

    roleService.create(role)

    assertNotNull "Role should have an id but does not", role.id
  }

  void testCreateRoleUniqueConstraint() {
    def roles = (LoaderDTO[]) RoleTestDataLoader.load()
    def node = roles[1]
    def role1 = ((Role) node.object)

    roleService.create(role1)

    assertNotNull "Role1 should have an id but does not", role1.id

    node = roles[2]
    def role2 = ((Role) node.object)
    role2.authority = role1.authority

    roleService.create(role2)

    assertTrue "Role2 should have errors", role2.hasErrors()
    assertNull "Role2 should not have an id", role2.id
  }
}
