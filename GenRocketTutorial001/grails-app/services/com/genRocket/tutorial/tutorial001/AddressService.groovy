package com.genRocket.tutorial.tutorial001

import grails.transaction.Transactional

@Transactional
class AddressService {

  def save(Address address) {
    address.save()
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
