package com.campigoto.employess.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.campigoto.employees.entities.Employee;
import com.campigoto.employees.repositories.EmployeeRepository;
import com.campigoto.employees.services.EmployeeService;
import com.campigoto.employees.services.exceptions.ResourceNotFoundException;
import com.campigoto.employess.tests.factory;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

	@InjectMocks
	private EmployeeService employeeService;
	
	@Mock
	private EmployeeRepository repository;
	
	private long existingId;
	private long nonExistingId;
	private Employee employee;
	
	@BeforeEach
	void setUp() throws Exception {
		existingId = 2L;
		nonExistingId = 1L;
		employee = factory.createEmployee();
		
		
		
		Mockito.when(repository.save(any())).thenReturn(employee);
		
		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(employee));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());
		
		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
	}
	
		
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			employeeService.delete(nonExistingId);
		});

		Mockito.verify(repository, times(1)).deleteById(nonExistingId);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		
		Assertions.assertDoesNotThrow(() -> {
			employeeService.delete(existingId);
		});
		
		Mockito.verify(repository, times(1)).deleteById(existingId);
	}
}

	

