package model

import EmployeeAlreadyExistsException
import EmployeeNotFoundException
import FieldTakenException
import JobTitleNotFoundException

class Department(
    var name: String,
) {
    val employees: MutableList<Employee> = mutableListOf()

    // ---------------- Business Logic ----------------  //

    /**
     *  Calculates a department's total payroll
     *
     *  @return Sum of every employee's salary
     */
    fun getPayroll(): Double = employees.sumOf { it.salary }

    /**
     * Counts the total number of employees with a specific job title
     *
     * @param jobTitle The job title to filter employees by
     * @return The total number of employees with the specified job title in the department
     */
    fun getTotalEmployeesByJobTitle(jobTitle: String): Int = employees.count { it.jobTitle.name == jobTitle }

    // ---------------- Employees CRUD ----------------  //

    /**
     * Adds an employee to the department's employee list if the employee is not under another or the same department
     *
     * @param employee The employee to be added to the department's employee list
     * @throws EmployeeAlreadyExistsException If the employee is already registered to a department
     * */
    fun addEmployee(employee: Employee) {
        if (Company.getAllEmployees().any { it.id == employee.id }) {
            throw EmployeeAlreadyExistsException(employee.id)
        }
        if (Company.getAllEmployees().any { it.email == employee.email }) {
            throw FieldTakenException("Email", employee.email)
        }
        if (Company.jobTitles.none { it.name == employee.jobTitle.name }) {
            throw JobTitleNotFoundException(employee.jobTitle.name)
        }
        employees.add(employee)
    }

    /**
     * Searches an employee in the employees list by ID
     *
     * @param employeeId The ID to search for
     * @throws EmployeeNotFoundException If no employee exists with inputted ID
     * */
    fun findEmployee(employeeId: String): Employee =
        employees.find { it.id == employeeId } ?: throw EmployeeNotFoundException(employeeId)

    /**
     * Removes an employee from the department's employee list by ID
     *
     * @param employeeId The ID to search for
     * @throws EmployeeNotFoundException If no employee exists with inputted ID
     * */
    fun removeEmployee(employeeId: String) {
        if (!employees.removeIf { it.id == employeeId }) {
            throw EmployeeNotFoundException(employeeId)
        }
    }

    /**
     * Updates an employee's data
     *
     * @param employeeId The employee to update
     * @param name The new name
     * @param gender The new gender
     * @param email The new email
     * @param salary The new salary
     * @param jobTitle The new job title
     *
     * @throws FieldTakenException If the new email is already taken
     * @throws JobTitleNotFoundException If the job title does not exist in the company's job title list
     * */
    fun updateEmployee(
        employeeId: String,
        name: String? = null,
        gender: String? = null,
        email: String? = null,
        salary: Double? = null,
        jobTitle: JobTitle? = null,
    ) {
        val employee = findEmployee(employeeId)

        if (email != null && Company.getAllEmployees().any { it.email == email && it.id != employeeId }) {
            throw FieldTakenException("Email", email)
        }
        if (jobTitle != null && Company.jobTitles.none { it.name == jobTitle.name }) {
            throw JobTitleNotFoundException(jobTitle.name)
        }

        employee.apply {
            name?.let { this.name = it }
            gender?.let { this.gender = it }
            email?.let { this.email = it }
            salary?.let { this.salary = it }
            jobTitle?.let { this.jobTitle = it }
        }
    }
}