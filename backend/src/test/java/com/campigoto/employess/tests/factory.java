package com.campigoto.employess.tests;

import com.campigoto.employees.dto.EmployeeDTO;
import com.campigoto.employees.entities.Employee;
import com.campigoto.employees.entities.Sex;

public class factory {

	public static Employee createEmployee() {
	
		Employee employee = new Employee(2L, "Evandro", 53, "89230680",Sex.M, " "," "," "," ");
		return employee;
	
    }
	
	public static EmployeeDTO createEmployeeDTO() {
		Employee employee = createEmployee();
		return new EmployeeDTO(employee);
		
	}
}


