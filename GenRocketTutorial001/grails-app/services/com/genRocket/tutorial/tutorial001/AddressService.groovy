package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.User
import grails.transaction.Transactional

@Transactional
class AddressService {

  def create(User user, Address address) {
    if (!user.id) {
      throw new Exception("User does not have an Id.")
    }

    address.save()

    if (address.hasErrors()) {
      throw new Exception("Unable to save address. Please check that all required attributes are entered.")
    }

    UserAddress.create(address, user)
  }
}
