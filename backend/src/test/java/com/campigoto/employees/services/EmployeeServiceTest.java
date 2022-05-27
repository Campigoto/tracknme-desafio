package com.campigoto.employees.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.campigoto.employees.dto.AddressDTO;
import com.campigoto.employees.dto.EmployeeDTO;
import com.campigoto.employees.entities.Employee;
import com.campigoto.employees.mappers.EmployeeMapper;
import com.campigoto.employees.repositories.EmployeeRepository;
import com.campigoto.employees.services.exceptions.ResourceNotFoundException;
import com.campigoto.employees.tests.factory;

@ExtendWith(SpringExtension.class)
public class EmployeeServiceTest {

	@InjectMocks
	private EmployeeService employeeService;

	@Mock
	private EmployeeRepository repository;

	@Mock
	ViaCepService viaCepService;

	@Spy
	EmployeeMapper mapper;

	@Captor
	ArgumentCaptor<Employee> employeeCaptor;

	private long existingId;
	private long nonExistingId;
	private String existingCep;
	private String nonExistingCep;

	private Employee employee;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 2L;
		nonExistingId = 1L;
		existingCep = "89230680";
		nonExistingCep = "89230000";
		employee = factory.createEmployee();

		Mockito.when(repository.save(any())).thenReturn(employee);

		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(employee));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		Mockito.when(repository.findByCep(existingCep)).thenReturn(List.of(employee));
		Mockito.when(repository.findByCep(nonExistingCep)).thenReturn(List.of());

		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
	}

	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			employeeService.findById(nonExistingId);
		});

		Mockito.verify(repository, times(1)).findById(nonExistingId);
	}

	@Test
	public void findByCepShouldThrowResourceNotFoundExceptionWhenCepDoesNotExist() {

		Assertions.assertThrows(Exception.class, () -> employeeService.findByCep(nonExistingCep));

		Mockito.verify(repository, times(1)).findByCep(nonExistingCep);
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
	

    @Test
    void whenNewEmployeeDoNotHaveAddressThenShouldFetchAndSave() throws Exception {
    	// Pelo factoru, este cara foi criado COM CEP e SEM o ADDRESS
        EmployeeDTO dto = factory.createEmployeeDTO();
        Employee employee = createEmployee(dto);
        employee.setId(new Random().nextLong());

        AddressDTO addressDTO = createAddressDTO();

        when(repository.save(any())).thenReturn(employee);
        when(viaCepService.findAddressByCep(dto.getCep())).thenReturn(addressDTO); // ATUALIZA o DTO com esse retorno que tu mandou retornar caso execute o findAddressByCEP <<<<<<

        // Quando executar o SAVE, ele vai passar pelo getAddressIfNotInformed
        // esse método por sua vez, valida se tem CEP e NAO TEM ADDRESS
        // se cair nesse IF, passa pelo  viaCepService.findAddressByCep(dto.getCep()); e ATUALIZA o dto
        EmployeeDTO savedEmployee = employeeService.save(dto);
        
        
        verify(viaCepService, times(1)).findAddressByCep(dto.getCep());
        verify(repository, times(1)).save(employeeCaptor.capture());
        
        assertEquals(dto.getName(), employeeCaptor.getValue().getName());
        assertEquals(dto.getAge(), employeeCaptor.getValue().getAge());
        assertEquals(dto.getSex(), employeeCaptor.getValue().getSex());
        
        assertEquals(addressDTO.getCity(), employeeCaptor.getValue().getCity());
        assertEquals(addressDTO.getState(), employeeCaptor.getValue().getState());
        assertEquals(addressDTO.getCep(), employeeCaptor.getValue().getCep());
        assertEquals(addressDTO.getDistrict(), employeeCaptor.getValue().getDistrict());
        assertEquals(savedEmployee.getId(), employee.getId());
    }

    @Test
    void whenNewEmployeeHaveAddressThenShouldNotFetchAndSave() throws Exception {
        EmployeeDTO dto = factory.createEmployeeDTO();
        dto.setAddress("Some district");

        Employee employee = createEmployee(dto);
        employee.setId(new Random().nextLong());

        when(repository.save(any())).thenReturn(employee);

        EmployeeDTO savedEmployee = employeeService.save(dto);

        verify(viaCepService, never()).findAddressByCep(dto.getCep());
        verify(repository, times(1)).save(employeeCaptor.capture());

        assertEquals(dto.getName(), employeeCaptor.getValue().getName());
        assertEquals(dto.getAge(), employeeCaptor.getValue().getAge());
        assertEquals(dto.getSex(), employeeCaptor.getValue().getSex());
        assertEquals(dto.getCity(), employeeCaptor.getValue().getCity());
        assertEquals(dto.getState(), employeeCaptor.getValue().getState());
        assertEquals(dto.getCep(), employeeCaptor.getValue().getCep());
        assertEquals(dto.getDistrict(), employeeCaptor.getValue().getDistrict());
        assertEquals(savedEmployee.getId(), employee.getId());
    }

    private Employee createEmployee(EmployeeDTO dto) {
        return mapper.toEntity(dto);
    }

    private AddressDTO createAddressDTO() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setCity("Campalegre");
        addressDTO.setState("Santa Catarina");
        addressDTO.setCep("89230680");
        addressDTO.setDistrict("o nome do bairro");
        addressDTO.setAddress("o nome da rua");
        return addressDTO;
    }
}
