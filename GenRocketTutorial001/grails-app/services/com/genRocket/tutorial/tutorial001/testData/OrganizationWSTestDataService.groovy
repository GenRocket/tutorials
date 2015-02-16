package com.genRocket.tutorial.tutorial001.testData

import com.genRocket.tutorial.tutorial001.Organization
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.testDataLoader.OrganizationWSTestDataLoader
import grails.transaction.Transactional

@Transactional
class OrganizationWSTestDataService {
  static transactional = true

  def organizationService
  def roleTestDataService

  def load() {
    println "Loading Organizations..."

    if (Organization.count() == 0) {

      if (Role.count() == 0) {
        roleTestDataService.loadData()
      }

      def organizations = (LoaderDTO[]) OrganizationWSTestDataLoader.load()

      organizations.each { node ->
        def map = (Map) node.object

        organizationService.save(map.organization, map.department, map.user, map.address)
      }
    }
  }
}
