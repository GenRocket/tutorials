package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.security.UserRole
import com.genRocket.tutorial.tutorial001.testDataLoader.DepartmentTestDataLoader
import com.genRocket.tutorial.tutorial001.testDataLoader.DepartmentWSTestDataLoader
import grails.test.mixin.integration.IntegrationTestMixin
import org.junit.After
import org.junit.Before
import grails.test.mixin.TestMixin
import static org.junit.Assert.*

@TestMixin(IntegrationTestMixin)
class DepartmentServiceIntegrationTests {
  def departmentService
  def organizationTestDataService
  def organizationWSTestDataService

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  void testSaveDepartment() {
    organizationTestDataService.loadData()

    def departments = (LoaderDTO[]) DepartmentTestDataLoader.load()
    def node = departments[0]
    def department = ((Department) node.object)

    department.organization = Organization.first()
    departmentService.save(department)

    assertNotNull "department.id should not be null", department.id
  }

  void testSaveUniqueName() {
    organizationTestDataService.loadData()

    def departments = (LoaderDTO[]) DepartmentTestDataLoader.load()
    def node = departments[1]
    def department1 = ((Department) node.object)

    department1.organization = Organization.first()
    departmentService.save(department1)

    assertNotNull "Department1 should have an id but does not", department1.id

    node = departments[2]
    def department2 = ((Department) node.object)
    department2.organization = department1.organization
    department2.name = department1.name

    departmentService.save(department2)

    assertTrue "Department2 should have errors", department2.hasErrors()
    assertNull "Department2 should not have an id", department2.id
  }

  void testSaveDepartmentWS() {
    organizationWSTestDataService.loadData()

    def departments = (LoaderDTO[]) DepartmentWSTestDataLoader.load()
    def node = departments[0]
    def department = ((Department) node.object.department)
    def user = ((User) node.object.user)
    def address = ((Address) node.object.address)

    department.organization = Organization.first()
    departmentService.save(department, user, address)

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
