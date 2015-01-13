package com.genRocket.tutorial.tutorial001.security

import com.genRocket.tutorial.tutorial001.Department
import com.genRocket.tutorial.tutorial001.DepartmentUser
import com.genRocket.tutorial.tutorial001.Organization

class User {
  transient springSecurityService

  String firstName
  String lastName
  String phoneNumber
  String emailAddress

  String username
  String password

  boolean enabled = true
  boolean accountExpired = false
  boolean accountLocked = false
  boolean passwordExpired = false

  Date dateCreated
  Date lastUpdated

  static transients = [
      'springSecurityService'
  ]

  static belongsTo = [
      organization: Organization
  ]

  static constraints = {
    organization nullable: false
    firstName nullable: false, maxSize: 50, blank: false, unique: false
    lastName nullable: false, maxSize: 50, blank: false, unique: false
    emailAddress nullable: false, maxSize: 100, blank: false

    phoneNumber nullable: true, maxSize: 25, blank: false

    username nullable: false, blank: false, unique: true
    password nullable: false, blank: false
    enabled nullable: false
    accountExpired nullable: false
    accountLocked nullable: false
    passwordExpired nullable: false

    dateCreated nullable: true
    lastUpdated nullable: true
  }

  static mapping = {
    password column: '`password`'
  }

  Set<Role> getAuthorities() {
    UserRole.findAllByUser(this).collect { it.role }
  }

  Set<Department> getDepartments() {
    DepartmentUser.findAllByUser(this).collect { it.department } as Set
  }

  def beforeInsert() {
    encodePassword()
  }

  def beforeUpdate() {
    if (isDirty('password')) {
      encodePassword()
    }
  }

  protected void encodePassword() {
    password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
  }
}