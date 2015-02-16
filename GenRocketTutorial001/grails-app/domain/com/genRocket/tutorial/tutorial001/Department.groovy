package com.genRocket.tutorial.tutorial001

class Department {
  String name

  static belongsTo = [
      organization : Organization
  ]

  static constraints = {
    organization nullable: false
    name nullable: false, blank: false, maxSize: 50, unique: 'organization'
  }
}
