package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.security.UserRole
import grails.transaction.Transactional

@Transactional
class OrganizationService {
  def departmentService

  def admin = RoleTypes.ROLE_ADMIN
  def orgAdmin = RoleTypes.ROLE_ORG_ADMIN

  def create(Organization organization, Department department, User user, Address address) {
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

  def enable(User user) {
    if (user.hasRole(admin)) {
      throw new Exception("User with role ${orgAdmin.toString()} cannot enable user with role ${admin.toString()}.")
    }
  }

  def disable(User user) {
    if (user.hasRole(admin)) {
      throw new Exception("User with role ${orgAdmin.toString()} cannot disable user with role ${admin.toString()}.")
    }
  }

  def activate(Address address) {
    def user = UserAddress.findByAddress(address).user

    if (user.hasRole(admin)) {
      throw new Exception("User with role ${orgAdmin.toString()} cannot activate address of user with role ${admin.toString()}.")
    }

    address.active = true
    address.save()
  }

  def deactivate(Address address) {
    def user = UserAddress.findByAddress(address).user

    if (user.hasRole(admin)) {
      throw new Exception("User with role ${orgAdmin.toString()} cannot deactivate address of user with role ${admin.toString()}.")
    }

    address.active = false
    address.save()
  }
}
