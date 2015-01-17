package com.genRocket.tutorial.tutorial001.testData

import com.genRocket.tutorial.tutorial001.Organization
import com.genRocket.tutorial.tutorial001.security.Role
import grails.transaction.Transactional
import groovy.com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import groovy.com.genRocket.tutorial.tutorial001.testDataLoaders.OrganizationLoader

@Transactional
class OrganizationTestDataService {
  static transactional = true

  def organizationService
  def roleTestDataService

  def loadData() {
    println "Loading Organizations..."

    if (Role.count() == 0) {
      roleTestDataService.loadData()
    }

    if (Organization.count() == 0) {
      def organizations = (LoaderDTO[]) OrganizationLoader.load()

      organizations.each { node ->
        def map = (Map) node.object

        organizationService.add(map.organization, map.department, map.user, map.address)
      }
    }
  }
}
