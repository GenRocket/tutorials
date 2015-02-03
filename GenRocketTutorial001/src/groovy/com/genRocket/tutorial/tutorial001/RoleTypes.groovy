package com.genRocket.tutorial.tutorial001
/**
 * Created by htaylor on 1/12/15.
 */
public enum RoleTypes {
  ROLE_USER('ROLE_USER'),
  ROLE_ADMIN('ROLE_ADMIN'),
  ROLE_ORG_ADMIN('ROLE_ORG_ADMIN'),
  ROLE_DEPT_ADMIN('ROLE_DEPT_ADMIN')

  RoleTypes(value) {
    this.value = value
  }

  private final String value

  String getKey() {
    name()
  }

  String toString() {
    value
  }
}