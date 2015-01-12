package com.genRocket.tutorial.tutorial001

class Department {
  String name

  static belongsTo = [
      organization : Organization
  ]

  static constraints = {
    name nullable: false, blank: false, maxSize: 50
  }
}
