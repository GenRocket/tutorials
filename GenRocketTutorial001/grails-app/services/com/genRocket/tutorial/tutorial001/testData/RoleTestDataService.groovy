package com.genRocket.tutorial.tutorial001.testData

import com.genRocket.tutorial.tutorial001.Namespaces
import grails.transaction.Transactional
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.testDataLoader.RoleTestDataLoader

@Transactional
class RoleTestDataService {
  static transactional = true

  def roleService
  def testDataMapService

  def loadData(Boolean useTestDataMap = false) {
    println "Loading Roles..."

    if (Role.list().size() == 0) {
      def roles = (LoaderDTO[]) RoleTestDataLoader.load()

      roles.each { node ->
        def syntheticId = node.object.id
        def role = (Role) node.object

        roleService.save(role)

        if (useTestDataMap) {
          testDataMapService.save(Namespaces.ROLE, syntheticId, role.id)
        }
      }
    }
  }
}
