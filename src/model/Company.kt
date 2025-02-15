package model

object Company {
    private var legalName: String = "Company Name"
    private var nit: String = "000000000"
    private var address: String = "Address"
    private val clients: MutableList<Client> = mutableListOf()
    private val jobTitles: MutableList<JobTitle> = mutableListOf()
    private val departments: MutableList<Department> = mutableListOf() // has to have at least 1

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
    ) {
        jobTitles.add(jobTitle)
        department.employees.add(employee)
        departments.add(department)
        println("Company created successfully!")
    }

    /**
     * Sums up every department's payroll
     *
     * @return The sum of every employee's salary for every department
     */
    fun getTotalPayroll(): Double {
        return departments.sumOf { it.getPayroll() }
    }

    /**
     * Finds the payroll of a specific department by name.
     *
     * Calls obtainPayrollByDepartment()
     * Handles the NoSuchElementException with a try catch block
     *
     * @param departmentName The name to search for.
     *
     * @return The department payroll or null
     * */
    fun getPayrollByDepartment(departmentName: String): Double? {
        try {
            return obtainPayrollByDepartment(departmentName)
        }catch (e: Exception){
            println(e.message)
        }
        return null
    }

    /**
     * Calculates the total number of employees for each job title across all departments.
     *
     * This function iterates through the list of jobTitles. For each title,
     * sums up the number of employees with that title in all departments.
     * The result is stored in a map where:
     * - The key (String) is the jobTitle name.
     * - The value (Int) is the total number of employees with that job title.
     *
     * @return A <Map<String, Int> where each key is a job title and each value is the total count of employees with that title.
     *
     */
    fun getTotalEmployeesByJobTitle(): Map<String, Int> {
        val map: MutableMap<String, Int> = mutableMapOf()
        jobTitles.forEach { jobTitle ->
            map[jobTitle.name] = departments.sumOf { it.getTotalEmployeesByJobTitle(jobTitle.name) }
        }
        return map
    }

    /**
     * Adds a department to the company's department list if the department to add is not registered.
     *
     * Calls createDepartment()
     * Handles the IllegalStateException with a try catch block
     *
     * @param department The department to be added to the company's departments list.
     *
     * @return Unit
     * */
    fun addDepartment(department: Department) {
        try {
            createDepartment(department)
        }catch (e: Exception){
            println(e.message)
        }
    }

    /**
     * Searches a department in the departments list by name.
     *
     * Calls findDepartment()
     * Handles the NoSuchElementException with a try catch block
     *
     * @param departmentName The ID to search for.
     *
     * @return The Department object corresponding to the given name or null
     * */
    fun searchDepartment(departmentName: String): Department? {
        try {
            return findDepartment(departmentName)
        }catch (e: Exception){
            println(e.message)
        }
        return null
    }

    /**
     * Removes a department from the company's departments list by name.
     *
     * Calls deleteDepartment()
     * Handles the IllegalStateException with a try catch block
     *
     * @param departmentName The name to search for.
     *
     * @return Unit
     * */
    fun removeDepartment(departmentName: String) {
        try {
            deleteDepartment(departmentName)
        }catch (e: IllegalStateException){
            println(e.message)
        }
    }

    /**
     * Updates a department's name.
     *
     * Calls searchDepartment()
     * If the department exists, changes its name.
     * If no department with the given name exists, does not perform anything
     *
     * @param departmentName The name to search for.
     * @param newName The new name to assign
     *
     * @return Unit
     * */
    fun updateDepartment(departmentName: String, newName: String) {
        val department: Department? = searchDepartment(departmentName)
        if (department != null) {
            department.name = departmentName
        }
    }

    /**
     * Adds a CLIENT to the company's clients list if the client to add is not registered.
     *
     * Calls createClient()
     * Handles the IllegalStateException with a try catch block
     *
     * @param client The client to be added to the company's clients list.
     *
     * @return Unit
     * */
    fun addClient(client: Client) {
        try {
            createClient(client)
        }catch (e: Exception){
            println(e.message)
        }
    }

    /**
     * Searches a client in the company's clients list by ID.
     *
     * Calls findClient()
     * Handles the NoSuchElementException with a try catch block
     *
     * @param clientId The ID to search for.
     *
     * @return The Client object corresponding to the given ID or null
     * */
    fun searchClient(clientId: String): Client? {
        try {
            return findClient(clientId)
        } catch (e: Exception) {
            println(e.message)
        }
        return null
    }


        //Remaining functions to implement. With the remaining CRUD functions, it's mostly a matter of copying and adapting.

    //two business logic functions
//    fun getPercentageClientsByGender(): Map<String, Double> {
//
//    }
//    fun getOldestEmployee(): Employee {
//
//    }

    //two Client CRUD fuctions
//    fun removeClient(clientId: String) {
//
//    }
//
//    fun updateClient(clientId: String, address: String, phoneNumber: String) {
//
//    }

    //All JobTitle CRUD functions
//    fun addJobTitle(client: Client) {
//
//    }
//
//    fun searchJobTitle(clientId: String): Client {
//
//    }
//
//    fun removeJobTitle(clientId: String) {
//
//    }
//
//    fun updateJobTitle(clientId: String, address: String, phoneNumber: String) {
//
//    }



    //SERVICE LAYER LIKE FUNCTIONS

    /**
     * Finds the payroll of a specific department by name.
     *
     * Looks for department in the departments list by name.
     * If the department is found, it returns department.getPayroll()
     * If no department with the given name exists, a NoSuchElementException is thrown.
     *
     * @param departmentName
     *
     * @return The department's payroll
     *
     * @throws NoSuchElementException If no department with the specified ID is found.
     */
    private fun obtainPayrollByDepartment(departmentName: String): Double {
        val department: Department = departments.find { it.name == departmentName }
            ?: throw NoSuchElementException("There is not a department called $departmentName")
        return department.getPayroll()
    }

    /**
     * Adds a department to the company's department list if the department to add is not registered.
     *
     * Checks whether a department with the same name already exists in the company.
     * If the department is found, an error message is printed.
     * If the department is not found, adds the department.
     *
     * @param department The department to be added to the company's departments list.
     *
     * @return Unit.
     *
     * @throws IllegalStateException If a department with the same name already exists in the company's departments list.
     */
    private fun createDepartment(department: Department) {
        check(departments.find { it.name == department.name } == null){
            "Could not add department. ${department.name} department already exists."
        }
        departments.add(department)
    }

    /**
     * Searches for a department in the company's department list by name.
     *
     * Looks for a department in the company's department list by name.
     * If the department is found, it returns a Department object.
     * If no department with the given name exists, a NoSuchElementException is thrown.
     *
     * @param departmentName The name to search for.
     *
     * @return The Department object corresponding to the given name.
     *
     * @throws NoSuchElementException If no department with the specified name is found in the company's department list.
     */
    private fun findDepartment(departmentName: String): Department {
        val department: Department = departments.find { it.name == departmentName }
            ?: throw NoSuchElementException("There is not a department called $departmentName")
        return department
    }

    /**
     * Deletes a department from the company's departments list based on name.
     *
     * This function removes a department with the given departmentName from the departments list.
     * If no department with the given name exists, it throws an IllegalStateException.
     *
     * @param departmentName The name of the department to be removed.
     *
     * @return Unit
     *
     * @throws IllegalStateException If the department does not exist in the company's departments list.
     */
    private fun deleteDepartment(departmentName: String) {
        check(departments.removeIf { it.name == departmentName }){
            "Could not remove. There is not $departmentName department registered."
        }
    }

    /**
     * Adds a client to the company's clients list if the client to add is not registered.
     *
     * Checks whether a client with the same ID already exists.
     * If the client is found, an error message is printed.
     * If the client is not found, adds the client.
     *
     * @param client The client to be added.
     *
     * @return Unit
     *
     * @throws IllegalStateException If an employee with the same ID already exists in the department.
     */
    private fun createClient(client: Client) {
        check(clients.find { it.id == client.id } == null) {
            "There is already a client identified with the id ${client.id}."
        }
        clients.add(client)
    }

    /**
     * Searches for a client in the company's clients list by ID.
     *
     * Looks for a client in the company's clients list by name.
     * If the client is found, it returns a Client object.
     * If no client with the given ID exists, a NoSuchElementException is thrown.
     *
     * @param clientId The ID to search for.
     *
     * @return The Client object corresponding to the given ID.
     *
     * @throws NoSuchElementException If no client with the specified ID is found in the company's clients list.
     */
    private fun findClient(clientId: String): Client {
        val client: Client = clients.find { it.id == clientId }
            ?: throw NoSuchElementException("There is not an client registered with id $clientId")
        return client
    }
}