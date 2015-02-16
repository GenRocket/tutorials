package com.genRocket.tutorial.tutorial001

class TestDataMap {
  Namespaces namespace
  Long syntheticId
  Long trueId

  static constraints = {
    namespace nullable: false, blank: false, maxSize: 100
    syntheticId nullable: false
    trueId nullable: false
  }
}
