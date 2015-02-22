package com.genRocket.tutorial.tutorial001.testData

import com.genRocket.tutorial.tutorial001.DepartmentUser
import com.genRocket.tutorial.tutorial001.Namespaces
import com.genRocket.tutorial.tutorial001.RoleTypes
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.UserRole
import com.genRocket.tutorial.tutorial001.testDataLoader.UserTestDataLoader
import grails.transaction.Transactional
import com.genRocket.tutorial.tutorial001.Department
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO

@Transactional
class UserTestDataService {
  static transactional = true

  def userService
  def testDataMapService
  def roleTestDataService
  def departmentTestDataService

  def loadData(Boolean useTestDataMap = false, Department department = null, Role role = null) {
    println "Loading Users..."

    if (User.count() == 0) {
      if (!role) {
        roleTestDataService.loadData(useTestDataMap)

        role = Role.findByAuthority(RoleTypes.ROLE_USER.toString())
      }

      if (!department) {
        departmentTestDataService.loadData(useTestDataMap)

        if (!useTestDataMap) {
          department = Department.first()
        }
      }

      def users = (LoaderDTO[]) UserTestDataLoader.load()

      users.each { node ->
        def user = (User) node.object

        userService.save(user)

        if (useTestDataMap) {
          testDataMapService.save(Namespaces.USER, syntheticId, user.id)

          department = (Department) testDataMapService.getDomain(Namespaces.DEPARTMENT, node.parentId)
        }

        UserRole.create(user, role)
        DepartmentUser.create(department, user)
      }
    }
  }
}
