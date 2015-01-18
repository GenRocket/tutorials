package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.security.UserRole
import grails.transaction.Transactional

@Transactional
class OrganizationService {
  def departmentService

  def create(Organization organization, Department department, User user, Address address = null) {
    def reverseDomain = organization.reverseDomain

    if (Organization.findByReverseDomain(reverseDomain)) {
      throw new Exception("An organization with reverse domain ${reverseDomain} already exists.")
    }
    
    organization.save()

    if (organization.hasErrors()) {
      throw new Exception("Unable to save organization. Please check that all required attributes are entered.")
    }

    department.organization = organization

    departmentService.create(department, user, address)

    def role = Role.findByAuthority(RoleTypes.ROLE_ORG_ADMIN.toString())

    UserRole.create(user, role, organization)
  }
}
