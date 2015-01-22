package com.genRocket.tutorial.tutorial001.testData

import grails.transaction.Transactional
import com.genRocket.tutorial.tutorial001.Department
import com.genRocket.tutorial.tutorial001.Organization
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.testDataLoader.OrganizationTestDataLoader

@Transactional
class DepartmentTestDataService {
  static transactional = true

  def departmentService
  def organizationTestDataService

  def loadData(Organization organization = null) {
    println "Loading Departments..."

    if (!organization) {
      organizationTestDataService.loadData()
      organization = Organization.first()
    }

    if (Department.count() == 0) {
      def departments = (LoaderDTO[]) OrganizationTestDataLoader.load()

      departments.each { node ->
        def map = (Map) node.object
        def department = (Department) map.department

        department.organization = organization

        departmentService.create(department, map.user, map.address)
      }
    }
  }
}
