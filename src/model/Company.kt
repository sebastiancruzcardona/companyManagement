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
        jobTitle: JobTitle = JobTitle("Admin", 5),
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
     * Adds a department to the company if the department to add is not registered.
     *
     * Calls createDepartment()
     * Handles the IllegalStateException with a try catch block
     *
     * @param department The department to be added to the company.
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


        // Old functions, revise and add exceptions to them. Could also optimize them.

//    fun getPercentageClientsByGender(): Map<String, Double> {
//
//    }
//
//    fun getTotalEmployeesByJobTitle(): Map<String, Int> {
//
//    }
//
//    fun getOldestEmployee(): Employee {
//
//    }
//
//    fun addClient(client: Client) {
//
//    }
//
//    fun searchClient(clientId: String): Client {
//
//    }
//
//    fun removeClient(clientId: String) {
//
//    }
//
//    fun updateClient(clientId: String, address: String, phoneNumber: String) {
//
//    }
//
//    //

//
//    fun removeDepartment(departmentName: String) {
//        val removedDepartment = departments.removeIf { department -> department.name == departmentName }
//        if (removedDepartment) {
//            println("Department removed successfully")
//        } else {
//            println("There is not such department. Not possible to remove.")
//        }
//    }
//
//    fun updateDepartment(departmentName: String) {
//        val department = searchDepartment(departmentName)
//        department.name = departmentName
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
     * Adds a department to the company if the department to add is not registered.
     *
     * Checks whether a department with the same name already exists in the company.
     * If the department is found, an error message is printed.
     * If the department is not found, adds the department.
     *
     * @param department The department to be added to the company.
     *
     * @return Unit.
     *
     * @throws IllegalStateException If a department with the same name already exists in the company.
     */
    private fun createDepartment(department: Department) {
        check(departments.find { it.name == department.name } == null){
            "Could not add department. ${department.name} department already exists."
        }
        departments.add(department)
    }

    /**
     * Searches for a department in the company by name.
     *
     * Looks for a department in the departments list by name.
     * If the department is found, it returns a Department object.
     * If no department with the given name exists, a NoSuchElementException is thrown.
     *
     * @param departmentName The name to search for.
     *
     * @return The Department object corresponding to the given name.
     *
     * @throws NoSuchElementException If no department with the specified name is found in the comapny.
     */
    private fun findDepartment(departmentName: String): Department {
        val department: Department = departments.find { it.name == departmentName }
            ?: throw NoSuchElementException("There is not a department called $departmentName")
        return department
    }

}