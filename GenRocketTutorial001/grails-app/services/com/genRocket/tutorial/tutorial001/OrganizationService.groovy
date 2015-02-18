package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.security.UserRole
import grails.transaction.Transactional

@Transactional
class OrganizationService {
  def userService
  def addressService
  def departmentService

  def save(Organization organization) {
    organization.save()
  }

  def save(Organization organization, Department department, User user, Address address) {
    save(organization)

    if (!organization.hasErrors()) {
      department.organization = organization
      departmentService.save(department)

      if (!department.hasErrors()) {
        userService.save(user)

        if (!user.hasErrors()) {
          DepartmentUser.create(department, user)

          def role = Role.findByAuthority(RoleTypes.ROLE_ORG_ADMIN.toString())
          UserRole.create(user, role)

          role = Role.findByAuthority(RoleTypes.ROLE_DEPT_ADMIN.toString())
          UserRole.create(user, role)

          addressService.save(address)

          if (!address.hasErrors()) {
            UserAddress.create(user, address, true)
          }
        }
      }
    }
  }
}
