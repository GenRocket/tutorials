package com.genRocket.tutorial.tutorial001.testData

import grails.transaction.Transactional
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.testDataLoader.RoleTestDataLoader

@Transactional
class RoleTestDataService {
  static transactional = true

  def roleService

  def loadData() {
    println "Loading Roles..."

    if (Role.list().size() == 0) {
      def roles = (LoaderDTO[]) RoleTestDataLoader.load()

      roles.each { node ->
        def role = (Role) node.object

        roleService.create(role)
      }
    }
  }
}
