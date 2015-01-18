package com.genRocket.tutorial.tutorial001

class Address {
  String addressType
  String addressOne
  String addressTwo
  String city
  String state
  String zipCode
  Boolean active = true

  static constraints = {
    addressType nullable: false, blank: false, maxSize: 10
    addressOne nullable: false, blank: false, maxSize: 50
    addressTwo nullable: true, blank: false, maxSize: 50
    city nullable: false, blank: false, maxSize: 35
    state nullable: false, blank: false, maxSize: 2
    zipCode nullable: false, blank: false, maxSize: 10
    active nullable: false
  }
}
