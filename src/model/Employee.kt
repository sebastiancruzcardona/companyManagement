package model

open class Employee(
    salary: Double,
    entryYear: Int,
    jobTitle: JobTitle,
    name: String, id: String, gender: String, email: String) : Person(name, id, gender, email) {

    var salary: Double = salary
    var entryYear: Int = entryYear
    val subordinates: MutableList<Employee> = mutableListOf()
    var jobTitle: JobTitle = jobTitle


    /**
     *  This function adds a subordinate to an employee
     *  @param: subordinate: Employee
     *  @return Unit
     *  First validates if argument is not already a subordinate of this and if this is not subordinate of the argument.
     *  Then prints a message
     */
    fun addSubordinate(subordinate: Employee) {
        if(!subordinates.contains(subordinate) && !subordinate.subordinates.contains(this)) {
            subordinates.add(subordinate)
            println("Subordinate added successfully")
        }else{
            println("Already a subordinate of this employee")
        }
    }

    /**
     *  This function searches a subordinate in the list of subordinates of an employee
     *  @param: employeeId: String
     *  @return Employee
     *  First calls the .find{} function, then if some subordinate is found returns it or throws an exception with a message
     */
    fun searchSubordinate(employeeId: String): Employee {
        val subordinate = subordinates.find { subordinate -> subordinate.id == employeeId }
        if(subordinate != null){
            return subordinate
        }
        throw NoSuchElementException("Subordinate with id $employeeId not found")
    }

    /**
     *  This function removes a subordinate from the list of subordinates of an employee
     *  @param: employeeId: String
     *  @return Unit
     *  First calls the .removeIf{} function, that returns true or false.
     *  Prints a message for each case
     */
    fun removeSubordinate(employeeId: String) {
        val removedSubordinate = subordinates.removeIf { subordinate -> subordinate.id == employeeId }
        if(removedSubordinate){
            println("Subordinate removed successfully")
        }else{
            println("Subordinate with id $employeeId not found. Not possible to remove.")
        }
    }
}