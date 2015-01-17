package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.User
import grails.transaction.Transactional

@Transactional
class AddressService {

  def update(User user, Address address) {
    if (!address.id) {
      throw new Exception("A new address must be created the Department Service workflow only.")
    }

    address.save()

    if (address.hasErrors()) {
      throw new Exception("Unable to save address. Please check that all required attributes are entered.")
    }

    UserAddress.create(address, user)
  }
}
