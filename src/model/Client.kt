package model

class Client(
    id: String,
    name: String,
    gender: String,
    email: String,
    var address: String,
    var phoneNumber: String
) : Person(id, name, gender, email)