package genrockettutorial001.security

class Role {
  String authority
  String description

  static mapping = {
    cache true
  }

  static constraints = {
    authority nullable: false, blank: false, unique: true, maxSize: 50
    description nullable: false, blank: false, maxSize: 255
  }
}
