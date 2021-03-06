package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.security.UserRole
import com.genRocket.tutorial.tutorial001.testDataLoader.OrganizationTestDataLoader
import com.genRocket.tutorial.tutorial001.testDataLoader.OrganizationWSTestDataLoader
import grails.test.mixin.integration.IntegrationTestMixin
import org.junit.After
import org.junit.Before
import grails.test.mixin.TestMixin
import static org.junit.Assert.*

@TestMixin(IntegrationTestMixin)
class OrganizationServiceIntegrationTests {
  def organizationService
  def roleTestDataService

  @Before
  public void setUp() {
    roleTestDataService.loadData()
  }

  @After
  public void tearDown() {}

  void testSave() {
    def organizations = (LoaderDTO[]) OrganizationTestDataLoader.load()
    def node = organizations[0]
    def organization = ((Organization) node.object)

    organizationService.save(organization)

    assertNotNull "organization.id should not be null", organization.id
  }

  void testSaveUniqueReverseDomain() {
    def organizations = (LoaderDTO[]) OrganizationTestDataLoader.load()
    def node = organizations[1]
    def org1 = ((Organization) node.object)

    organizationService.save(org1)

    assertNotNull "Organization One should have an id but does not", org1.id

    node = organizations[2]
    def org2 = ((Organization) node.object)
    org2.reverseDomain = org1.reverseDomain

    organizationService.save(org2)

    assertTrue "Organization Two should have errors", org2.hasErrors()
    assertNull "Organization Two should not have an id", org2.id
  }

  void testSaveOrganizationWS() {
    def organizations = (LoaderDTO[]) OrganizationWSTestDataLoader.load()
    def node = organizations[0]
    def organization = ((Organization) node.object.organization)
    def department = ((Department) node.object.department)
    def user = ((User) node.object.user)
    def address = ((Address) node.object.address)

    organizationService.save(organization, department, user, address)

    assertNotNull "organization.id should not be null", organization.id
    assertNotNull "department.id should not be null", department.id
    assertNotNull "user.id should not be null", user.id
    assertNotNull "address.id should not be null", address.id
    assertNotNull "User should have an address", UserAddress.findByUserAndAddress(user, address)
    assertNotNull "User should belong to department", DepartmentUser.findByDepartmentAndUser(department, user)

    assertTrue "department should belong to organization", organization.id == department.organization.id

    def role = Role.findByAuthority(RoleTypes.ROLE_ORG_ADMIN.toString())
    assertNotNull "User should have a role of ${role.authority}", UserRole.findByUserAndRole(user, role)

    role = Role.findByAuthority(RoleTypes.ROLE_DEPT_ADMIN.toString())
    assertNotNull "User should have a role of ${role.authority}", UserRole.findByUserAndRole(user, role)

    role = Role.findByAuthority(RoleTypes.ROLE_USER.toString())
    assertNotNull "User should have a role of ${role.authority}", UserRole.findByUserAndRole(user, role)
  }
}
