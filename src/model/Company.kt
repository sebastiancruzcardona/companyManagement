package model

import ClientAlreadyExistsException
import ClientNotFoundException
import CompanyMustHaveDepartmentsException
import DepartmentAlreadyExistsException
import DepartmentNotFoundException
import FieldTakenException
import JobTitleAlreadyExistsException
import JobTitleNotFoundException

object Company {
    private val legalName: String = "Company Name"
    private val nit: String = "000000000"
    private val address: String = "Address"
    val clients: MutableList<Client> = mutableListOf()
    val jobTitles: MutableList<JobTitle> = mutableListOf()
    val departments: MutableList<Department> = mutableListOf()

    /**
     * Initializes the company with default values for departments, employees, and job titles
     *
     * @param department
     * @param jobTitle
     * @param employee
     */
    fun init(
        department: Department = Department("Central Department"),
        jobTitle: JobTitle = JobTitle("CEO", 5),
        employee: Employee = Employee("1", "One", "None", "Email", 50.00, 2025, jobTitle),
    ): Boolean {
        jobTitles.add(jobTitle)
        val defaultDepartments = listOf(
            Department("Sales"),
            Department("Human Resources"),
            Department("Management"),
            Department("Operations")
        )
        departments.addAll(defaultDepartments)
        department.employees.add(employee)
        departments.add(department)
        return true
    }

    /**
     * Gets all the employees across all departments
     *
     * @return List<Employee>
     */
    fun getAllEmployees(): List<Employee> =
        departments.flatMap { it.employees }

    // ---------------- Business Logic ----------------  //

    /**
     * Sums up every department's payroll
     *
     * @return The sum of every employee's salary for every department
     */
    fun getTotalPayroll(): Double =
        departments.sumOf { it.getPayroll() }

    /**
     * Finds the payroll of a specific department by name.
     *
     * @param departmentName The name to search for
     * @return The department payroll
     * */
    fun getPayrollByDepartment(departmentName: String): Double =
        findDepartment(departmentName).getPayroll()

    /**
     * Calculates the total number of employees for each job title across all departments.
     *
     * @return <Map<String, Int> Where each key is a job title and each value is the total count of employees with that title.
     */
    fun getTotalEmployeesByJobTitle(): Map<String, Int> =
        jobTitles.associate { jobTitle -> jobTitle.name to departments.sumOf { it.getTotalEmployeesByJobTitle(jobTitle.name) } }

    /**
     * Calculates the total number of employees in a specific job title across all departments
     *
     * @return Int The total count of employees
     */
    fun getEmployeesByJobTitle(jobTitle: String): Int =
        departments.sumOf { it.getTotalEmployeesByJobTitle(jobTitle) }

    /**
     * Gets the company's oldest employee and their department
     *
     * @return Map<Employee, Department>
     */
    fun getOldestEmployee(): Map<Employee, Department> {
        val oldestEmployee = getAllEmployees().minByOrNull { it.entryYear }!!
        val department = departments.find { department ->
            department.employees.any { it.id == oldestEmployee.id } }!! // non-null assertion (!!) because there is always at least one employee (and therefore, at least one department)
        return mapOf(oldestEmployee to department)
    }

    /**
     * Calculates the percentage of employees by their gender
     *
     * @return Map<String, Double> Where the key is the gender and the value is the percentage
     *         of employees with said gender (rounded up to 2 decimal spaces)
     */
    fun getPercentageClientsByGender(): Map<String, Double> {
        val genderCount = getAllEmployees().groupingBy { it.gender }.eachCount() // groups employees by gender and total with said gender ({ "male" to 7, "female" to 8 }, etc).
        return genderCount.mapValues { (_, count) -> // "_" means we ignore the key (gender) and modify only the value (count of employees with said gender)
            "%.2f".format((count.toDouble()/ getAllEmployees().size) * 100).toDouble() // converts the count to double to ensure accurate division, divides by total amount of employees, and multiplies by 100 to convert into percentage
            // additionally, rounds up to two decimal places
        }
    }

    // ---------------- Department CRUD ----------------  //

    /**
     * Adds a department to the company's department list if the department to add is not registered
     *
     * @param department The department to be added to the company's departments list
     * @throws DepartmentAlreadyExistsException If an existing department has the same name
     * */
    fun addDepartment(department: Department) {
        if (departments.any { it.name == department.name }) {
            throw DepartmentAlreadyExistsException(department.name)
        }
        departments.add(department)
    }

    /**
     * Searches a department in the departments list by name
     *
     * @param departmentName The ID to search for
     * @throws DepartmentNotFoundException If no department exists with inputted name
     * */
    fun findDepartment(departmentName: String): Department =
        departments.find { it.name == departmentName } ?: throw DepartmentNotFoundException(departmentName)

    /**
     * Removes a department from the company's departments list by name
     *
     * @param departmentName The name to search for
     * @throws DepartmentNotFoundException If no department exists with inputted name
     * */
    fun removeDepartment(departmentName: String) {
        if (departments.size == 1) {
            throw CompanyMustHaveDepartmentsException()
        }
        if (!departments.removeIf { it.name == departmentName }) {
            throw DepartmentNotFoundException(departmentName)
        }
    }

    /**
     * Updates a department's name
     *
     * @param departmentName The name to search for
     * @param newName The new name to assign
     *
     * @throws DepartmentAlreadyExistsException If an existing department has the same name
     * */
    fun updateDepartment(departmentName: String, newName: String) {
        val department = findDepartment(departmentName)
        if (departments.any { it.name == newName }) {
            throw DepartmentAlreadyExistsException(newName)
        }
        department.name = newName
    }

    // ---------------- Client CRUD ----------------  //

    /**
     * Adds a client to the company's client list if the client to add is not registered
     *
     * @param client The client to be added to the company's client list
     * @throws ClientAlreadyExistsException If an existing client has the same ID, email, and/or phone number
     * */
    fun addClient(client: Client) {
        if (clients.any { it.id == client.id }) {
            throw ClientAlreadyExistsException("ID", client.id)
        }
        if (clients.any { it.email == client.email }) {
            throw ClientAlreadyExistsException("Email", client.email)
        }
        if (clients.any { it.phoneNumber == client.phoneNumber }) {
            throw ClientAlreadyExistsException("Phone number", client.phoneNumber)
        }
        clients.add(client)
    }

    /**
     * Searches a client in the clients list by ID
     *
     * @param clientId The ID to search for
     * @throws ClientNotFoundException If no client exists with inputted ID
     * */
    fun findClient(clientId: String): Client =
        clients.find { it.id == clientId } ?: throw ClientNotFoundException(clientId)

    /**
     * Removes a client from the company's client list by ID
     *
     * @param clientId The ID to search for
     * @throws ClientNotFoundException If no client exists with inputted ID
     * */
    fun removeClient(clientId: String) {
        if (!clients.removeIf { it.id == clientId }) {
            throw ClientNotFoundException(clientId)
        }
    }

    /**
     * Updates a client's data
     *
     * @param clientId The client to update
     * @param name The new name
     * @param gender The new gender
     * @param email The new email
     * @param address The new address
     * @param phoneNumber The new phone number
     *
     * @throws FieldTakenException If the new email is taken and/or phone number is taken
     * */
    fun updateClient(
        clientId: String,
        name: String?,
        gender: String?,
        email: String?,
        address: String?,
        phoneNumber: String?,
    ) {
        val client = findClient(clientId)

        if (email != null && clients.any { it.email == email && it.id != clientId }) {
            throw FieldTakenException("Email", email)
        }
        if (phoneNumber != null && clients.any { it.phoneNumber == phoneNumber && it.id != clientId }) {
            throw FieldTakenException("Phone number", phoneNumber)
        }

        client.apply {
            name?.let { this.name = it }
            gender?.let { this.gender = it }
            email?.let { this.email = it }
            address?.let { this.address = it }
            phoneNumber?.let { this.phoneNumber = it }
        }
    }

    // ---------------- Job Title CRUD ----------------  //

    /**
     * Adds a job title to the company's job title list if the job title to add is not registered
     *
     * @param jobTitle The job title to be added to the company's job title list
     * @throws JobTitleAlreadyExistsException If an existing job title has the same name
     * */
    fun addJobTitle(jobTitle: JobTitle) {
        if (jobTitles.any { it.name == jobTitle.name }) {
            throw JobTitleAlreadyExistsException(jobTitle.name)
        }
        jobTitles.add(jobTitle)
    }

    /**
     * Searches a job title in the job title list by name
     *
     * @param jobTitleName The job title name to search for
     * @throws JobTitleNotFoundException If no job title exists with inputted name
     * */
    fun findJobTitle(jobTitleName: String): JobTitle =
        jobTitles.find { it.name == jobTitleName } ?: throw JobTitleNotFoundException(jobTitleName)

    /**
     * Removes a job title from the company's job title list by name
     *
     * @param jobTitleName The job title name to search for
     * @throws JobTitleNotFoundException If no department exists with inputted name
     * */
    fun removeJobTitle(jobTitleName: String) {
        if (!jobTitles.removeIf { it.name == jobTitleName }) {
            throw JobTitleNotFoundException(jobTitleName)
        }
    }

    /**
     * Updates a job title's name
     *
     * @param jobTitleName The name to search for
     * @param newName The new name to assign
     *
     * @throws JobTitleAlreadyExistsException If there is a job title with the same name in the company's job title list
     * */
    fun updateJobTitle(jobTitleName: String, newName: String, newHierarchyLevel: Int) {
        val jobTitle = findJobTitle(jobTitleName)
        if (jobTitles.any { it.name == newName }) {
            throw JobTitleAlreadyExistsException(newName)
        }
        jobTitle.name = newName
        jobTitle.hierarchyLevel = newHierarchyLevel
    }
}