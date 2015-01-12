package com.genRocket.tutorial.tutorial001.security

import com.genRocket.tutorial.tutorial001.Organization
import org.apache.commons.lang.builder.HashCodeBuilder

class UserRole implements Serializable {
  private static final long serialVersionUID = 1

  User user
  Role role
  Organization organization

  boolean equals(other) {
    if (!(other instanceof UserRole)) {
      return false
    }

    other.user?.id == user?.id &&
        other.role?.id == role?.id &&
        other.organization?.id == organization?.id
  }

  int hashCode() {
    def builder = new HashCodeBuilder()
    if (user) builder.append(user.id)
    if (role) builder.append(role.id)
    if (organization) builder.append(organization.id)
    builder.toHashCode()
  }

  static UserRole get(long userId, long roleId) {
    find 'from UserRole where user.id=:userId and role.id=:roleId',
        [userId: userId, roleId: roleId]
  }

  static UserRole create(User user, Role role, Organization organization, boolean flush = false) {
    new UserRole(user: user, role: role, organization: organization).save(flush: flush, insert: true)
  }

  static boolean remove(User user, Role role, boolean flush = false) {
    UserRole instance = UserRole.findByUserAndRole(user, role)
    if (!instance) {
      return false
    }

    instance.delete(flush: flush)
    true
  }

  static void removeAll(User user) {
    executeUpdate 'DELETE FROM UserRole WHERE user=:user', [user: user]
  }

  static void removeAll(User user, Organization organization) {
    executeUpdate 'DELETE FROM UserRole WHERE user=:user and organization=:organization', [user: user, organization: organization]
  }

  static void removeAll(Role role) {
    executeUpdate 'DELETE FROM UserRole WHERE role=:role', [role: role]
  }

  static mapping = {
    id composite: ['role', 'user', 'organization']
    version false
  }
}
