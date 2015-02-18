package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.security.UserRole
import grails.transaction.Transactional

@Transactional
class DepartmentService {
  def userService
  def addressService

  def save(Department department) {
    department.save()
  }

  def save(Department department, User user, Address address) {
    save(department)

    if (!department.hasErrors()) {
      userService.save(user)

      if (!user.hasErrors()) {
        DepartmentUser.create(department, user)

        def role = Role.findByAuthority(RoleTypes.ROLE_DEPT_ADMIN.toString())
        UserRole.create(user, role, true)

        addressService.create(address)

        if (!address.hasErrors()) {
          UserAddress.create(address, user, true)
        }
      }
    }
  }
}