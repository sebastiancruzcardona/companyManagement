package model

class Company(legalName: String, nit: String, address: String, clients: MutableList<Client>, departments: MutableList<Department>) {

    var legalName: String = legalName
    var nit: String = nit
    var address: String = address
    val clients: MutableList<Client> = mutableListOf()
}