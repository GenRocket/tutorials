package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.security.UserRole
import com.genRocket.tutorial.tutorial001.testDataLoader.OrganizationLoader

/**
 * Created by htaylor on 1/17/15.
 */
class OrganizationServiceIntegrationTests extends GroovyTestCase {
  def organizationService
  def roleTestDataService

  protected void setUp() {
    super.setUp()

    //roleTestDataService.loadData()
  }

  protected void tearDown() {
    super.tearDown()
  }

  void testCreateOrganization() {
    roleTestDataService.loadData()

    def organizations = (LoaderDTO[]) OrganizationLoader.load()
    def node = organizations[0]
    def organization = ((Organization) node.object.organization)
    def department = ((Department) node.object.department)
    def user = ((User) node.object.user)
    def address = ((Address) node.object.address)

    organizationService.create(organization, department, user, address)

    assertNotNull "organization.id should not be null", organization.id
    assertNotNull "department.id should not be null", department.id
    assertNotNull "user.id should not be null", user.id
    assertNotNull "address.id should not be null", address.id
    assertNotNull "User should have an address", UserAddress.findByUserAndAddress(user, address)

    def role = Role.findByAuthority(RoleTypes.ROLE_ORG_ADMIN.toString())
    assertNotNull "User should have a role of ${role.authority}", UserRole.findByUserAndRole(user, role)

    role = Role.findByAuthority(RoleTypes.ROLE_DEPT_ADMIN.toString())
    assertNotNull "User should have a role of ${role.authority}", UserRole.findByUserAndRole(user, role)

    role = Role.findByAuthority(RoleTypes.ROLE_USER.toString())
    assertNotNull "User should have a role of ${role.authority}", UserRole.findByUserAndRole(user, role)
  }


}
