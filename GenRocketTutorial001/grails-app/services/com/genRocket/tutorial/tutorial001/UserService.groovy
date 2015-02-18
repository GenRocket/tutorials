package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.security.UserRole
import grails.transaction.Transactional

@Transactional
class UserService {

  def save(User user) {
    if (!user.id) {
      user.save()

      def role = Role.findByAuthority(RoleTypes.ROLE_USER.toString())

      UserRole.create(user, role)
    }
  }

  def update(User user) {
    user.save()
  }

  def move(User user, Department source, Department dest) {
    def roleType = RoleTypes.ROLE_DEPT_ADMIN
    def count = getUsersWithRole(source, roleType).size()

    if (count == 0) {
      def message = """
        User ${user.username} cannot be moved because department ${source.name} has no other users with role ${
        roleType.toString()
      }...
      """

      throw new Exception(message)
    }

    DepartmentUser.remove(source, user)
    DepartmentUser.create(dest, user)
  }

  def getUsersWithRole(Department department, RoleTypes roleType) {
    def users = DepartmentUser.findAllByDepartment(department).user
    def usersWithRole = []

    users.each { user ->
      if (user.hasRole(roleType)) {
        usersWithRole.add(user)
      }
    }

    return usersWithRole
  }
}
