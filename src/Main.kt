import model.Client
import model.Employee
import model.JobTitle
import model.Person

fun main() {
    /*
    //Testing inheritance
    println("Hello World!")

    var firstPerson: Person = Person("Pepito", "109688", "Male", "pepito@gmail")
    println(firstPerson.name)

    var firstClient: Client = Client("Av 123", "3155678901", "Pepita", "9999", "Female", "pepita@mail.com")
    println(firstClient.name)*/


    /*
    //Testing inner class to create subordinates
    
    var jobTitle1: JobTitle = JobTitle(name = "Manager", hierarchyLevel = 5)

    var employee: Employee = Employee(
        name = "Ruperto",
        id = "5555",
        email = "rup@gmail.com",
        gender = "Not binary",
        salary = 123.6,
        jobTitle = jobTitle1,
        entryYear = 2025
    )

    val subordinate = Employee.Subordinate(
        supervisor =  employee,
        name = "Anacleto",
        id = "5555",
        email = "rup@gmail.com",
        gender = "Not binary",
        salary = 123.6,
        jobTitle = jobTitle1,
        entryYear = 2025
    )

    println(employee.subordinates[0].name)*/
}