package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.Role
import grails.transaction.Transactional

@Transactional
class RoleService {

  def save(Role role) {
    role.save()
  }
}
