package com.genRocket.tutorial.tutorial001.testData

import com.genRocket.tutorial.tutorial001.DepartmentUser
import com.genRocket.tutorial.tutorial001.Namespaces
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
  def departmentTestDataService

  def loadData(Boolean useTestDataMap = false, Department department = null) {
    println "Loading Users..."

    if (!department) {
      departmentTestDataService.loadData(useTestDataMap)

      if (!useTestDataMap) {
        department = Department.first()
      }
    }

    if (User.count() == 0) {
      def users = (LoaderDTO[]) UserTestDataLoader.load()

      users.each { node ->
        def user = (User) node.object

        userService.save(user)

        if (useTestDataMap) {
          testDataMapService.save(Namespaces.USER, syntheticId, user.id)

          department = (Department) testDataMapService.getDomain(Namespaces.DEPARTMENT, node.parentId)
        }

        DepartmentUser.create(department, user)
      }
    }
  }
}
