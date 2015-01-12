package genrockettutorial001

class Address {
  String addressType
  String addressOne
  String addressTwo
  String city
  String state
  String zip
  Boolean active = true

  static constraints = {
    addressType nullable: false, blank: fale, maxSize: 10
    addressOne nullable: false, blank: fale, maxSize: 50
    addressTwo nullable: false, blank: fale, maxSize: 50
    city nullable: false, blank: fale, maxSize: 35
    state nullable: false, blank: fale, maxSize: 2
    zip nullable: false, blank: fale, maxSize: 10
    active nullable: false
  }
}
