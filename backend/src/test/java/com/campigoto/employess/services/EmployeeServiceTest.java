package com.campigoto.employess.services;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.campigoto.employees.dto.AddressDTO;
import com.campigoto.employees.dto.EmployeeDTO;
import com.campigoto.employees.services.EmployeeService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class EmployeeServiceTest {

	@InjectMocks
	private EmployeeService employeeService;
	
	@Test
	void whenGetEmpployeeIsEmptyThenReturnNull() {
		
		long someInexistentEmployee = 23L;
		EmployeeDTO dto = employeeService.findById(someInexistentEmployee);
		
		assertNull(dto);
	}
	
}
