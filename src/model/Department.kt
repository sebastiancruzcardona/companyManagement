package model

class Department(
    var name: String
) {
    val employees: MutableList<Employee> = mutableListOf()

    /**
     *  Calculates a department's total payroll
     *
     *  @return Sum of every employee's salary
     */
    fun getPayroll(): Double {
        return employees.sumOf { it.salary }
    }

    /**
     * Adds an employee to the department if the employee to add is not registered
     *
     * Calls createEmployee()
     * Handles the IllegalStateException with a try catch block
     *
     * @param employee The employee to be added to the department.
     *
     * @return Unit
     * */
    fun addEmployee(employee: Employee) {
        try {
            createEmployee(employee)
        } catch (e: Exception) {
            println(e.message)
        }
    }

    /**
     * Searches an employee in the department by ID.
     *
     * Calls findEmployee()
     * Handles the NoSuchElementException with a try catch block
     *
     * @param employeeId The ID to search for.
     *
     * @return The Employee object corresponding to the given ID or null
     * */
    fun searchEmployee(employeeId: String): Employee? {
        try {
            return findEmployee(employeeId)
        } catch (e: Exception) {
            println(e.message)
        }
        return null
    }

    /**
     * Removes an employee from the department by ID.
     *
     * Calls deleteEmployee()
     * Handles the IllegalStateException with a try catch block
     *
     * @param employeeId The ID to search for.
     *
     * @return Unit
     * */
    fun removeEmployee(employeeId: String) {
        try {
            deleteEmployee(employeeId)
        } catch (e: IllegalStateException) {
            println(e.message)
        }
    }

    /**
     * Updates an employee's salary and jobTitle searching by ID.
     *
     * Calls searchEmployee()
     *
     * @param employeeId The ID to search for.
     * @param newSalary The new salary to assign
     * @param newJobTitle Te new jobTitle to asign
     *
     * @return Unit
     * */
    fun updateEmployee(employeeId: String, newSalary: Double, newJobTitle: JobTitle) {
        val employee: Employee? = searchEmployee(employeeId)
        if (employee != null) {
            employee.salary = newSalary
            employee.jobTitle = newJobTitle
        }
    }

    //SERVICE LAYER LIKE FUNCTIONS

    /**
     * Adds an employee to the department if the employee to add is not registered.
     *
     * Checks whether an employee with the same ID already exists in the department.
     * If the employee is found, an error message is printed.
     * If the employee is not found, adds the employee.
     *
     * @param employee The employee to be added to the department.
     *
     * @return Unit
     *
     * @throws IllegalStateException If an employee with the same ID already exists in the department.
     */
    private fun createEmployee(employee: Employee) {
        check(employees.find { it.id == employee.id } == null) {
            "There is already an employee identified with the id ${employee.id} working on department $name"
        }
        employees.add(employee)
    }

    /**
     * Searches for an employee in the department by their ID.
     *
     * Looks for an employee in the employees list by employeeId.
     * If the employee is found, it returns an Employee object.
     * If no employee with the given ID exists, a NoSuchElementException is thrown.
     *
     * @param employeeId The ID to search for.
     * @return The Employee object corresponding to the given ID.
     * @throws NoSuchElementException If no employee with the specified ID is found in the department.
     */
    private fun findEmployee(employeeId: String): Employee {
        val employee: Employee = employees.find { it.id == employeeId }
            ?: throw NoSuchElementException("There is not an employee with id $employeeId working on department $name")
        return employee
    }

    /**
     * Deletes an employee from the department based on ID.
     *
     * This function removes an employee with the given employeeId from the employees list.
     * If no employee with the given ID exists, it throws an IllegalStateException.
     *
     * @param employeeId The ID of the employee to be removed.
     * @throws IllegalStateException If the employee does not exist in the department.
     */
    private fun deleteEmployee(employeeId: String) {
        check(employees.removeIf { employee -> employee.id == employeeId }) {
            "Could not remove. There is not an employee with id $employeeId working on department $name."
        }
    }
}