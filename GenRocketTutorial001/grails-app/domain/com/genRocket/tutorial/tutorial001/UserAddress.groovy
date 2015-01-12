package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.User
import org.apache.commons.lang.builder.HashCodeBuilder

class UserAddress implements Serializable {
  Address address
  User user

  boolean equals(other) {
    if (!(other instanceof UserAddress)) {
      return false
    }

    other.user?.id == user?.id &&
        other.address?.id == address?.id
  }

  int hashCode() {
    def builder = new HashCodeBuilder()
    if (user) builder.append(user.id)
    if (address) builder.append(address.id)
    builder.toHashCode()
  }

  static UserAddress get(long addressId, long userId) {
    find 'from UserAddress where user.id=:userId and address.id=:addressId',
        [userId: userId, addressId: addressId]
  }

  static List<User> getUsers(long addressId) {
    def addressUsers = findAll 'from UserAddress where address.id=:addressId', [addressId: addressId]
    addressUsers.collect { it.user }
  }

  static UserAddress create(Address address, User user, boolean flush = false) {
    new UserAddress(user: user, address: address).save(flush: flush, insert: true)
  }

  static boolean remove(Address address, User user, boolean flush = false) {
    UserAddress instance = UserAddress.findByUserAndAddress(user, address)
    if (!instance) {
      return false
    }

    instance.delete(flush: flush)
    true
  }

  static void removeAll(User user) {
    executeUpdate 'DELETE FROM UserAddress WHERE user=:user', [user: user]
  }

  static void removeAll(Address address) {
    executeUpdate 'DELETE FROM UserAddress WHERE address=:address', [address: address]
  }

  static mapping = {
    id composite: ['address', 'user']
    version false
  }
}
