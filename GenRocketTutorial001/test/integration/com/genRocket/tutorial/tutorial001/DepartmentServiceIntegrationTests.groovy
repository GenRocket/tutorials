package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.security.UserRole
import com.genRocket.tutorial.tutorial001.testDataLoaders.DepartmentLoader

/**
 * Created by htaylor on 1/18/15.
 */
class DepartmentServiceIntegrationTests extends GroovyTestCase {
  def departmentService
  def organizationTestDataService

  protected void setUp() {
    super.setUp()

    //organizationTestDataService.loadData()
  }

  protected void tearDown() {
    super.tearDown()
  }

  void testCreateDepartment() {
    organizationTestDataService.loadData()

    def departments = (LoaderDTO[]) DepartmentLoader.load()
    def node = departments[0]
    def department = ((Department) node.object.department)
    def user = ((User) node.object.user)
    def address = ((Address) node.object.address)

    department.organization = Organization.first()

    departmentService.create(department, user, address)

    assertNotNull "department.id should not be null", department.id
    assertNotNull "user.id should not be null", user.id
    assertNotNull "address.id should not be null", address.id
    assertNotNull "User should have an address", UserAddress.findByUserAndAddress(user, address)

    def role = Role.findByAuthority(RoleTypes.ROLE_DEPT_ADMIN.toString())
    assertNotNull "User should have a role of ${role.authority}", UserRole.findByUserAndRole(user, role)

    role = Role.findByAuthority(RoleTypes.ROLE_USER.toString())
    assertNotNull "User should have a role of ${role.authority}", UserRole.findByUserAndRole(user, role)
  }
}
