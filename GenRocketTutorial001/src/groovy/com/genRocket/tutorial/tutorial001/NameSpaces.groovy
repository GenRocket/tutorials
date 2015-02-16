package com.genRocket.tutorial.tutorial001

enum Namespaces {
  ROLE('role'),
  USER('user'),
  ADDRESS('address'),
  DEPARTMENT('department'),
  ORGANIZATION('organization')

  Namespaces(value) {
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