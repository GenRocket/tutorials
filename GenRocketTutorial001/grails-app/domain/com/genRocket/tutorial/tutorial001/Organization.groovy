package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.User

class Organization {
  String name

  static hasMany = [
      departments: Department,
      users : User
  ]

  static constraints = {
    name nullable: false, blank: false, maxSize: 50
  }
}
