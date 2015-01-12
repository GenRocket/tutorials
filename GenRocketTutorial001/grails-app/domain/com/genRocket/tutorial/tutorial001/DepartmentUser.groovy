package com.genRocket.tutorial.tutorial001

import com.genRocket.tutorial.tutorial001.security.User
import org.apache.commons.lang.builder.HashCodeBuilder

class DepartmentUser implements Serializable {
    Department department
    User user

    boolean equals(other) {
      if (!(other instanceof DepartmentUser)) {
        return false
      }

      other.user?.id == user?.id &&
          other.department?.id == department?.id
    }

    int hashCode() {
      def builder = new HashCodeBuilder()
      if (user) builder.append(user.id)
      if (department) builder.append(department.id)
      builder.toHashCode()
    }

    static DepartmentUser get(long departmentId, long userId) {
      find 'from DepartmentUser where user.id=:userId and department.id=:departmentId',
          [userId: userId, departmentId: departmentId]
    }

    static List<User> getUsers(long departmentId){
      def departmentUsers = findAll 'from DepartmentUser where department.id=:departmentId', [departmentId: departmentId]
      departmentUsers.collect {it.user}
    }

    static DepartmentUser create(Department department, User user, boolean flush = false) {
      new DepartmentUser(user: user, department: department).save(flush: flush, insert: true)
    }

    static boolean remove(Department department, User user, boolean flush = false) {
      DepartmentUser instance = DepartmentUser.findByUserAndDepartment(user, department)
      if (!instance) {
        return false
      }

      instance.delete(flush: flush)
      true
    }

    static void removeAll(User user) {
      executeUpdate 'DELETE FROM DepartmentUser WHERE user=:user', [user: user]
    }

    static void removeAll(Department department) {
      executeUpdate 'DELETE FROM DepartmentUser WHERE department=:department', [department: department]
    }

    static mapping = {
      id composite: ['department', 'user']
      version false
    }
}
