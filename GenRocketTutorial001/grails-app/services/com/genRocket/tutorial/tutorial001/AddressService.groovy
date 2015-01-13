package com.genRocket.tutorial.tutorial001

import grails.transaction.Transactional

@Transactional
class AddressService {

  def update(Address address) {
    if (!address.id) {
      throw new Exception("A new address must be created the Department Service workflow only.")
    }

    address.save()

    if (address.hasErrors()) {
      throw new Exception("Unable to save address. Please check that all required attributes are entered.")
    }
  }
}
