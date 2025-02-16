package model

import model.Company.clients
import java.time.Year

open class Employee(
    id: String,
    name: String,
    gender: String,
    email: String,
    var salary: Double,
    val entryYear: Int,
    var jobTitle: JobTitle
) : Person(id, name, gender, email) {

    val subordinates: MutableList<Employee> = mutableListOf()

    /**
     * Calculates an employee's total time in the company (in years)
     *
     * @param currentYear Defaults to Year.now()
     * @return Difference between employee's entry year and given year
     */
    fun getTimeInCompany(currentYear: Int = Year.now().value): Int = currentYear - entryYear

    // ---------------- Subordinate CRUD ----------------  //

    // This function checks the subordinate already exists as an employee AND isn't already registered as someone's subordinate.

    /**
     * Adds a subordinate to the employee's subordinate list if the subordinate is not under another or the same employee
     *
     * @param subordinate The employee object to be added to the employee's subordinate list
     * @throws SubordinateAlreadyRegisteredException If the subordinate is already registered to an employee
     * @throws SubordinateNotFoundException If the subordinate does not exist as an employee
     * */
    fun addSubordinate(subordinate: Employee) {
        if (Company.getAllEmployees().none { it.id == subordinate.id }) {
            throw SubordinateNotFoundException(subordinate.id)
        }
        if (Company.getAllEmployees().any { it.subordinates.contains(subordinate) }) {
            throw SubordinateAlreadyRegisteredException(subordinate.id)
        }
        subordinates.add(subordinate)
    }

    /**
     * Searches a subordinate in the subordinates list by ID
     *
     * @param subordinateId The ID to search for
     * @throws SubordinateNotFoundException If no subordinate exists with inputted ID
     * */
    fun findSubordinate(subordinateId: String): Employee =
        subordinates.find { it.id == subordinateId } ?: throw SubordinateNotFoundException(subordinateId)

    /**
     * Removes a subordinate from the company's subordinate list by ID
     *
     * @param subordinateId The ID to search for
     * @throws SubordinateNotFoundException If no subordinate exists with inputted ID
     * */
    fun removeSubordinate(subordinateId: String) {
        if (!subordinates.removeIf { it.id == subordinateId }) {
            throw SubordinateNotFoundException(subordinateId)
        }
    }

    /**
     * Updates a subordinate's data
     *
     * @param subordinateId The subordinate to update
     * @param name The new name
     * @param gender The new gender
     * @param email The new email
     * @param salary The new salary
     * @param jobTitle The new job title
     *
     * @throws FieldTakenException If the new email is taken
     * @throws JobTitleNotFoundException If the job title does not exist in the company's job title list
     * */
    fun updateSubordinate(
        subordinateId: String,
        name: String?,
        gender: String?,
        email: String?,
        salary: Double?,
        jobTitle: JobTitle?
    ) {
        val subordinate = findSubordinate(subordinateId)

        if (email != null && Company.getAllEmployees().any { it.email == email && it.id != subordinateId }) {
            throw FieldTakenException("Email", email)
        }
        if (jobTitle != null && Company.jobTitles.none { it.name == jobTitle.name }) {
            throw JobTitleNotFoundException(jobTitle.name)
        }

        subordinate.apply {
            name?.let { this.name = it }
            gender?.let { this.gender = it }
            email?.let { this.email = it }
            salary?.let { this.salary = it }
            jobTitle?.let { this.jobTitle = it }
        }
    }
}