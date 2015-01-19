package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.security.UserRole
import com.genRocket.tutorial.tutorial001.testDataLoader.DepartmentLoader
import grails.test.mixin.integration.IntegrationTestMixin
import org.junit.After
import org.junit.Before
import grails.test.mixin.TestMixin
import static org.junit.Assert.*

@TestMixin(IntegrationTestMixin)
class DepartmentServiceIntegrationTests {
  def departmentService
  def organizationTestDataService

  @Before
  public void setUp() {
    organizationTestDataService.loadData()
  }

  @After
  public void tearDown() {}

  void testCreateDepartment() {
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
