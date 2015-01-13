package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.security.UserRole
import grails.transaction.Transactional

@Transactional
class UserService {

  def add(User user, Address address, Department department) {
    def organization = department.organization

    if (!organization) {
      throw new Exception("Department, ${department.name} must reference an organization.")
    }

    if (user.id) {
      throw new Exception('User already exist and can only be moved from one department to another department.')
    }

    if (User.findByUsername(user.username)) {
      throw new Exception('Username is not unique.')
    }

    user.organization = department.organization
    user.save()

    if (user.hasErrors()) {
      throw new Exception('Unable to save user.  Please check that all required attributes are entered.')
    }

    UserRole.create(user, Role.findByAuthority(RoleTypes.ROLE_USER.toString()), organization)

    address.save()

    if (address.hasErrors()) {
      throw new Exception('Unable to save address. Please check that all required attributes are entered.')
    }

    UserAddress.create(address, user)
    DepartmentUser.create(department, user)

    // need if check...
    def role = Role.findByAuthority(RoleTypes.ROLE_DEPT_ADMIN.toString())

    UserRole.create(user, role, organization)
  }
}
