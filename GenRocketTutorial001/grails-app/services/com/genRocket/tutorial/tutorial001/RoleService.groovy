package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.Role
import grails.transaction.Transactional

@Transactional
class RoleService {

  def save(Role role) {
    def authority = role.authority

    if (Role.findByAuthority(authority)) {
      throw new Exception("Role, ${authority} already exists a may not be added twice.")
    }

    role.save()

    if (role.hasErrors()) {
      throw new Exception("Unable to save role ${authority}. Please check that all required attributes are entered.")
    }
  }
}
