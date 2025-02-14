package model

class Department(name: String) {

    var name: String = name
    val employees: MutableList<Employee> = mutableListOf()

    /**
     *  This function calculates a department's payroll
     *  @param:
     *  @return Double
     *  Creates a payroll variable, iterates over employees, and sums ups every salary to payroll variable
     *  returns payroll
     */
    fun getPayroll(): Double{
        var payroll = 0.0
        employees.forEach{ employee -> payroll += employee.salary}
        return payroll
    }

    /**
     *  This function links an employee to a department
     *  @param: employee: Employee
     *  @return Unit
     *  First validates if argument is not already an employee that belongs to this department.
     *  Then prints a message
     */
    fun addEmployee(employee: Employee) {
        if(!employees.contains(employee)) {
            employees.add(employee)
            println("Employee added successfully to department $name")
        }else{
            println("The employee already belongs to department $name")
        }
    }

    /**
     *  This function searches an employee in the list of employees that belong to a department
     *  @param: employeeId: String
     *  @return Employee
     *  First calls the .find{} function, then if the employee is found, returns it, or throws an exception with a message if not
     */
    fun searchEmployee(employeeId: String): Employee {
        val employee = employees.find { employee -> employee.id == employeeId }
        if(employee != null){
            return employee
        }
        throw NoSuchElementException("Employee with id $employeeId does not belong to department $name")
    }

    /**
     *  This function removes a subordinate from the list of subordinates of an employee
     *  @param: employeeId: String
     *  @return Unit
     *  First calls the .removeIf{} function, that returns true or false.
     *  Prints a message for each case
     */
    fun removeEmployee(employeeId: String) {
        val removedEmployee = employees.removeIf { employee -> employee.id == employeeId }
        if(removedEmployee){
            println("Employee removed successfully from department $name")
        }else{
            println("Employee with id $employeeId not found. Not possible to remove.")
        }
    }

    /**
     *  This function updates salary and JobTitle searching by id
     *  @param: employeeId: String
     *  @param: newSalary: Double
     *  @param: newJobTitle: JobTitle
     *  @return Unit
     *  Calls the searchEmployee function and sets attributes
     */
    fun updateEmployee(employeeId: String, newSalary: Double, newJobTitle: JobTitle) {
        val employee = searchEmployee(employeeId)
        employee.salary = newSalary
        employee.jobTitle = newJobTitle
    }
}