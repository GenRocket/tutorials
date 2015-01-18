package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.testDataLoaders.RoleLoader

/**
 * Created by htaylor on 1/17/15.
 */
class RolesServiceIntegrationTests extends GroovyTestCase {
  def roleService

  protected void setUp() {
    super.setUp()
  }

  protected void tearDown() {
    super.tearDown()
  }

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
    def role = ((Role) node.object)

    roleService.create(role)
    roleService.create(role)

    assertTrue role.hasErrors()
    assertEquals 'unique', role.errors['authority'].code

  }
}
