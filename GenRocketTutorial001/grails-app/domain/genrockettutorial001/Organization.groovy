package genrockettutorial001

class Organization {
  String name

  static hasMany = [
      departments: Department
  ]

  static constraints = {
    name nullable: false, blank: false, maxSize: 50
  }
}
