package model

//Primary constructor without annotations or visibility modifiers therefore constructor keyword can be omitted
//Concise syntax was used for declaring properties and initializing then in the primary constructor
//Al properties set to var to be mutable (reference can change)
//public modifier by default
//open to allow inheritance
open class Person(var name: String, var id: String, var gender: String, var email: String) {

}

