package com.genRocket.tutorial.tutorial001.testData

import grails.transaction.Transactional
import com.genRocket.tutorial.tutorial001.Department
import com.genRocket.tutorial.tutorial001.security.User
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.testDataLoaders.DepartmentLoader

@Transactional
class UserTestDataService {
  static transactional = true

  def userService
  def departmentTestDataService

  def loadData(Department department = null) {
    println "Loading Users..."

    if (!department) {
      departmentTestDataService.loadData()
      department = Department.first()
    }

    if (User.count() == 0) {
      def users = (LoaderDTO[]) DepartmentLoader.load()

      users.each { node ->
        def map = (Map) node.object

        userService.add(department, map.user, map.address)
      }
    }
  }
}
