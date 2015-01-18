package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.security.UserRole
import grails.transaction.Transactional

@Transactional
class DepartmentService {
  def userService

  def create(Department department, User user, Address address = null) {
    def organization = department.organization
    def name = department.name

    if (!organization) {
      throw new Exception("Department, ${name} must reference an organization.")
    }

    if (Department.findByOrganizationAndName(organization, department.name)) {
      throw new Exception("Department ${name} already exists for organization ${organization}.")
    }

    department.save()

    if (department.hasErrors()) {
      throw new Exception("Unable to save department. Please check that all required attributes are entered.")
    }

    if (!user.id) {
      userService.create(department, user, address)
    } else {
      def source = DepartmentUser.findByUser(user).department
      def role = Role.findByAuthority(RoleTypes.ROLE_DEPT_ADMIN.toString())

      move(user, source, department)

      if (!UserRole.findByUserAndRole(user, role)){
        UserRole.create(user, role, organization)
      }
    }
  }

  def move(User user, Department source, Department dest) {
    def role = Role.findByAuthority(RoleTypes.ROLE_DEPT_ADMIN.toString())
    def count = getUsers(source, role).size()

    if (count == 0) {
      def message = """
        User ${user.username} cannot be moved because department ${source.name} has no other users with role ${RoleTypes.ROLE_DEPT_ADMIN.toString()}...
      """

      throw new Exception(message)
    }

    DepartmentUser.remove(source, user)
    DepartmentUser.create(dest, user)
  }

  def getUsers(Department department, Role role) {
    return User.withCriteria {
      authorities {
        eq('authority': role.authority)

      }

      departments {
        eq('id': department.id)
      }
    }
  }
}
