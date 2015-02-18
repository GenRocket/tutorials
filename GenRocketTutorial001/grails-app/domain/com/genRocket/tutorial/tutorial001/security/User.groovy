package com.genRocket.tutorial.tutorial001.security

import com.genRocket.tutorial.tutorial001.RoleTypes

class User {
  String firstName
  String lastName
  String phoneNumber
  String emailAddress

  String username
  String password

  boolean enabled = true

  static constraints = {
    firstName nullable: false, maxSize: 50, blank: false
    lastName nullable: false, maxSize: 50, blank: false
    emailAddress nullable: false, maxSize: 100, blank: false
    phoneNumber nullable: true, maxSize: 25, blank: false
    username nullable: false, maxSize: 100, blank: false, unique: true
    password nullable: false, maxSize: 255, blank: false
    enabled nullable: false
  }

  Set<Role> getAuthorities() {
    UserRole.findAllByUser(this).collect { it.role }
  }

  def hasRole(RoleTypes roleType) {
    def roleFound = false

    getAuthorities().each { role ->
      if (role.authority == roleType.toString()) {
        roleFound = true
      }
    }

    return roleFound
  }
}