package com.genRocket.tutorial.tutorial001.testData

import grails.transaction.Transactional
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.testDataLoaders.RoleLoader

@Transactional
class RoleTestDataService {
  static transactional = true

  def roleSer

  def loadData() {
    println "Loading Roles..."

    if (Role.list().size() == 0) {
      def roles = (LoaderDTO[]) RoleLoader.load()

      roles.each { node ->
        def attribute = (Role) node.object

        roleSer.save(attribute)
      }
    }
  }
}
