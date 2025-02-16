class DepartmentNotFoundException(name: String) : Exception("Couldn't find department '$name'")
class DepartmentAlreadyExistsException(name: String) : Exception("Department '$name' already exists")

class ClientNotFoundException(id: String) : Exception("Couldn't find client with ID '$id'")
class ClientAlreadyExistsException(field: String, id: String) : Exception("Client with $field '$id' already exists")
class FieldTakenException(field: String, value: String) : Exception("$field '$value' is already taken")

class JobTitleNotFoundException(name: String) : Exception("Couldn't find job title '$name'")
class JobTitleAlreadyExistsException(name: String) : Exception("Job title '$name' already exists")

class EmployeeAlreadyExistsException(id: String) : Exception("Employee with ID '$id' already exists")
class EmployeeNotFoundException(id: String) : Exception("Couldn't find employee with ID '$id'")

class SubordinateAlreadyRegisteredException(id: String) : Exception("Subordinate with ID '$id' is already registered")
class SubordinateNotFoundException(id: String) : Exception("Couldn't find subordinate with ID '$id'")

class CompanyMustHaveDepartmentsException() : Exception("The company must always have at least one department")
class DepartmentMustHaveEmployeesException() : Exception("A department must always have at least one employee.")