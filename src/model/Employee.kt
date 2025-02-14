package model

open class Employee(
    salary: Double,
    entryYear: Int,
    jobTitle: JobTitle,
    name: String, id: String, gender: String, email: String) : Person(name, id, gender, email) {

    var salary: Double = salary
    var entryYear: Int = entryYear
    val subordinates: MutableList<Subordinate> = mutableListOf()
    var jobTitle: JobTitle = jobTitle

    //This creates directly a subordinate related to a specific employee from scratch
    class Subordinate(
        val supervisor: Employee,
        salary: Double,
        entryYear: Int,
        jobTitle: JobTitle,
        name: String, id: String, gender: String, email: String, ) : Employee(salary, entryYear, jobTitle, name, id, gender, email) {
        init {
            supervisor.subordinates.add(this)
        }
    }
}