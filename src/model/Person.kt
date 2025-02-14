package model

//Primary constructor without annotations or visibility modifiers therefore constructor keyword can be omitted
//Concise syntax can be used (but was not to be explicit) for declaring properties and initializing then in the primary constructor
//Al properties set to var to be mutable (reference can change)
//public modifier by default
//open to allow inheritance
open class Person(name: String, id: String, gender: String, email: String) {

    var name: String = name
    var id: String = id
    var gender: String = gender
    var email: String = email
}

