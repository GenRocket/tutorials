package com.genRocket.tutorial.tutorial001.testData

import com.genRocket.tutorial.tutorial001.Department
import com.genRocket.tutorial.tutorial001.Namespaces
import com.genRocket.tutorial.tutorial001.Organization
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.testDataLoader.DepartmentTestDataLoader
import grails.transaction.Transactional

@Transactional
class DepartmentTestDataService {
  static transactional = true

  def departmentService
  def testDataMapService
  def organizationTestDataService

  def loadData(Boolean useTestDataMap = false, Organization organization = null) {
    println "Loading Departments..."

    if (Department.count() == 0) {

      if (!organization) {
        organizationTestDataService.loadData(useTestDataMap)

        if (!useTestDataMap) {
          organization = Organization.first()
        }
      }

      def departments = (LoaderDTO[]) DepartmentTestDataLoader.load()

      departments.each { node ->
        def syntheticId = node.object.id
        def department = (Department) node.object

        if (useTestDataMap) {
          organization = (Organization) testDataMapService.getDomain(Namespaces.ORGANIZATION, node.parentId)
        }

        department.organization = organization
        departmentService.save(department)

        if (useTestDataMap) {
          testDataMapService.save(Namespaces.DEPARTMENT, syntheticId, department.id)
        }
      }
    }
  }
}
