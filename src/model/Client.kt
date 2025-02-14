package model

class Client(address: String, phoneNumber: String, name: String, id: String, gender: String, email: String) : Person(name, id, gender, email) {

    var address: String = address
    var phoneNumber: String = phoneNumber

}