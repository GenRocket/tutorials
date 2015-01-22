package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.security.UserRole
import com.genRocket.tutorial.tutorial001.testDataLoader.UserTestDataLoader
import grails.test.mixin.integration.IntegrationTestMixin
import org.junit.After
import org.junit.Before
import grails.test.mixin.TestMixin
import static org.junit.Assert.*

@TestMixin(IntegrationTestMixin)
class UserServiceIntegrationTests {
  def userService
  def departmentTestDataService

  @Before
  public void setUp() {
    departmentTestDataService.loadData()
  }

  @After
  public void tearDown() {}

  void testCreate() {
    def users = (LoaderDTO[]) UserTestDataLoader.load()
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

  void testMove() {
    departmentTestDataService.loadData()

    def users = (LoaderDTO[]) UserTestDataLoader.load()
    def node = users[0]
    def user = ((User) node.object.user)
    def address = ((Address) node.object.address)

    def departments = Department.list()
    def sourceDept = departments[0]
    def destDept = departments[1]

    userService.create(sourceDept, user, address)
    userService.move(user, sourceDept, destDept)

    def departmentUser = DepartmentUser.findByDepartmentAndUser(sourceDept, user)
    assertNull "User should no longer belong to department, ${sourceDept.name}", departmentUser

    departmentUser = DepartmentUser.findByDepartmentAndUser(destDept, user)
    assertNotNull "User should now belong to department, ${destDept.name}", departmentUser
  }
}
