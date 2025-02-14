import model.*

fun main() {
    /*
    //Testing inheritance
    println("Hello World!")

    var firstPerson: Person = Person("Pepito", "109688", "Male", "pepito@gmail")
    println(firstPerson.name)

    var firstClient: Client = Client("Av 123", "3155678901", "Pepita", "9999", "Female", "pepita@mail.com")
    println(firstClient.name)*/



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

    var employee2: Employee = Employee(
        name = "Anacleto",
        id = "6666",
        email = "rup@gmail.com",
        gender = "Not binary",
        salary = 150.6,
        jobTitle = jobTitle1,
        entryYear = 2025
    )

    var employee3: Employee = Employee(
        name = "Remigio",
        id = "7777",
        email = "rup@gmail.com",
        gender = "Not binary",
        salary = 150.6,
        jobTitle = jobTitle1,
        entryYear = 2025
    )

    var department: Department = Department("Publicidad")
    department.addEmployee(employee)
    department.addEmployee(employee2)
    println(department.getPayroll())

    /*
    employee.addSubordinate(employee2)
    employee.addSubordinate(employee3)
    for(i in employee.subordinates){
        println(i.name)
    }

    println(employee.searchSubordinate("7777").name)
    employee.removeSubordinate("6666")
    println(employee.searchSubordinate("6666").name)*/



}