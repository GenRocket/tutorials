package com.genRocket.tutorial.tutorial001.testData

import com.genRocket.tutorial.tutorial001.Namespaces
import com.genRocket.tutorial.tutorial001.Organization
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.testDataLoader.OrganizationTestDataLoader
import grails.transaction.Transactional

@Transactional
class OrganizationTestDataService {
  static transactional = true

  def testDataMapService
  def organizationService

  def loadData(Boolean useTestDataMap = false) {
    println "Loading Organizations..."

    if (Organization.count() == 0) {
      def organizations = (LoaderDTO[]) OrganizationTestDataLoader.load()

      organizations.each { node ->
        def syntheticId = node.object.id
        def organization = (Organization) node.object

        organizationService.save(organization)

        if (useTestDataMap) {
          testDataMapService.save(Namespaces.ORGANIZATION, syntheticId, organization.id)
        }
      }
    }
  }
}