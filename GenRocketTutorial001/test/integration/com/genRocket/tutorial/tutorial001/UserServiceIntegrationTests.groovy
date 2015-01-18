package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.security.UserRole
import com.genRocket.tutorial.tutorial001.testDataLoaders.UserLoader

/**
 * Created by htaylor on 1/18/15.
 */
class UserServiceIntegrationTests extends GroovyTestCase {
  def userService
  def departmentTestDataService

  protected void setUp() {
    super.setUp()

    //departmentTestDataService.loadData()
  }

  protected void tearDown() {
    super.tearDown()
  }

  void testCreateDepartment() {
    departmentTestDataService.loadData()

    def users = (LoaderDTO[]) UserLoader.load()
    def node = users[0]
    def user = ((User) node.object.user)
    def address = ((Address) node.object.address)
    def department = Department.first()

    userService.create(department, user, address)

    assertNotNull "user.id should not be null", user.id
    assertNotNull "address.id should not be null", address.id
    assertNotNull "User should have an address", UserAddress.findByUserAndAddress(user, address)

    def role = Role.findByAuthority(RoleTypes.ROLE_DEPT_ADMIN.toString())
    assertNull "User should not be assigned a role of ${role.authority}", UserRole.findByUserAndRole(user, role)

    role = Role.findByAuthority(RoleTypes.ROLE_USER.toString())
    assertNotNull "User should have a role of ${role.authority}", UserRole.findByUserAndRole(user, role)
  }
}
