package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.security.UserRole
import grails.transaction.Transactional

@Transactional
class UserService {
  def addressService

  def create(Department department, User user, Address address) {
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

    addressService.create(user, address)

    UserRole.create(user, Role.findByAuthority(RoleTypes.ROLE_USER.toString()), organization)
    DepartmentUser.create(department, user)

    def role = Role.findByAuthority(RoleTypes.ROLE_DEPT_ADMIN.toString())

    if (getUsersWithRole(department, role).size() == 0) {
      UserRole.create(user, role, organization)
    }
  }

  def getUsersWithRole(Department department, Role role) {
    def users = DepartmentUser.findAllByDepartment(department).user
    def usersWithRole = []

    users.each { user ->
      if (UserRole.findByUserAndRole(user, role)) {
        usersWithRole.add(user)
      }
    }

    return usersWithRole
  }

//  def getUsers(Department department, Role role) {
//    return User.withCriteria {
//      authorities() {
//        eq('authority': role.authority)
//      }
//
//      departments() {
//        eq('id': department.id)
//      }
//    }
//  }
}
