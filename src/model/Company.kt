package model

class Company(legalName: String, nit: String, address: String) {

    var legalName: String = legalName
    var nit: String = nit
    var address: String = address
    val clients: MutableList<Client> = mutableListOf()
    val departments: MutableList<Department> = mutableListOf()


    fun getTotalPayrroll(): Double {
        var payroll = 0.0
        departments.forEach { department -> payroll += department.getPayroll()}
        return payroll
    }

    fun getPayRollByDepartment(): Map<String, Double> {
        val payroll = departments.map { department -> department.name to department.getPayroll() }
        return payroll.toMap()
    }

    fun getPercentageClientsByGender(): Map<String, Double> {

    }

    fun getTotalEmployeesByJobTitle(): Map<String, Int> {

    }

    fun getOldestEmployee(): Employee {

    }

    fun addClient(client: Client) {

    }

    fun searchClient(clientId: String): Client {

    }

    fun removeClient(clientId: String) {

    }

    fun updateClient(clientId: String, address: String, phoneNumber: String) {

    }

    fun addDepartment(department: Department) {
        if(!departments.contains(department)) {
            departments.add(department)
            println("Department added successfully")
        }else{
            println("The department ${department.name} already exist in the company")
        }
    }

    fun searchDepartment(departmentName: String): Department {
        val department = departments.find { department -> department.name == departmentName }
        if(department != null){
            return department
        }
        throw NoSuchElementException("Department $departmentName does not exist")

    }

    fun removeDepartment(departmentName: String) {
        val removedDepartment = departments.removeIf { department -> department.name == departmentName }
        if(removedDepartment){
            println("Department removed successfully")
        }else{
            println("There is not such department. Not possible to remove.")
        }
    }

    fun updateDepartment(departmentName: String) {
        val department = searchDepartment(departmentName)
        department.name = departmentName
    }
}