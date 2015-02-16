package com.genRocket.tutorial.tutorial001.testData

import com.genRocket.tutorial.tutorial001.Department
import com.genRocket.tutorial.tutorial001.Organization
import com.genRocket.tutorial.tutorial001.dto.LoaderDTO
import com.genRocket.tutorial.tutorial001.testDataLoader.OrganizationWSTestDataLoader
import grails.transaction.Transactional

@Transactional
class DepartmentWSTestDataService {
  static transactional = true

  def departmentService
  def organizationWSTestDataService

  def loadData(Organization organization = null) {
    println "Loading Departments..."

    if (!organization) {
      organizationWSTestDataService.loadData()
      organization = Organization.first()
    }

    if (Department.count() == 0) {
      def departments = (LoaderDTO[]) OrganizationWSTestDataLoader.load()

      departments.each { node ->
        def map = (Map) node.object
        def department = (Department) map.department

        department.organization = organization

        departmentService.save(department, map.user, map.address)
      }
    }
  }
}
