package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.testDataLoader.RoleLoader
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
    def roles = (LoaderDTO[]) RoleLoader.load()
    def node = roles[0]
    def role = ((Role) node.object)

    roleService.create(role)

    assertNotNull "Role should have an id but does not", role.id
  }

  void testCreateRoleUniqueConstraint() {
    def roles = (LoaderDTO[]) RoleLoader.load()
    def node = roles[1]
    def role1 = ((Role) node.object)
    def role2 = ((Role) node.object)

    roleService.create(role1)

    try {
      roleService.create(role2)
      assertTrue "Service did not catch duplicate error.", false
    } catch (e) {
      def message = "Role, ${role2.authority} already exists a may not be added twice."
      assertTrue message == e.getMessage()
    }
  }
}
