package com.genRocket.tutorial.tutorial001.testData

import com.genRocket.tutorial.tutorial001.Address
import com.genRocket.tutorial.tutorial001.Department
import com.genRocket.tutorial.tutorial001.Namespaces
import com.genRocket.tutorial.tutorial001.Organization
import com.genRocket.tutorial.tutorial001.TestDataMap
import com.genRocket.tutorial.tutorial001.security.Role
import com.genRocket.tutorial.tutorial001.security.User
import grails.transaction.Transactional

@Transactional
class TestDataMapService {

  def save(Namespaces namespace, Long syntheticId, Long trueId) {
    def testDataMap = new TestDataMap(
      namespace: namespace,
      syntheticId: syntheticId,
      trueId: trueId
    )

    testDataMap.save()
  }

  def getDomain(Namespaces namespace, Long syntheticId) {
    def trueId = TestDataMap.findByNamespaceAndSyntheticId(namespace, syntheticId).trueId

    switch(namespace) {
      case Namespaces.ROLE: return Role.get(trueId)
      case Namespaces.ORGANIZATION : return Organization.get(trueId)
      case Namespaces.DEPARTMENT : return Department.get(trueId)
      case Namespaces.USER: return User.get(trueId)
      case Namespaces.ADDRESS: return Address.get(trueId)
    }
  }

  def truncate() {
    TestDataMap.deleteAll()
  }
}
