package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.User
import grails.transaction.Transactional

@Transactional
class AddressService {

  def save(Address address) {
    address.save()
  }

  def save(User user, Address address) {
    save(address)

    if (!address.hasErrors()) {
      UserAddress.create(user, address)
    }
  }

  def update(Address address) {
    address.save()
  }

  def delete(Address address) {
    def userAddress = UserAddress.findByAddress(address)

    userAddress?.delete()
    address.delete()
  }
}
