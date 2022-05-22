package com.campigoto.employees.mappers;

import org.springframework.stereotype.Service;

import com.campigoto.employees.dto.EmployeeDTO;
import com.campigoto.employees.entities.Employee;

@Service
public class EmployeeMapper {

	public EmployeeDTO toDTO(Employee employee) {

		EmployeeDTO dto = new EmployeeDTO();
		dto.setId(employee.getId());
		dto.setName(employee.getName());
		dto.setAge(employee.getAge());
		dto.setSex(employee.getSex());
		dto.setCep(employee.getCep());
		dto.setAddress(employee.getAddress());
		dto.setDistrict(employee.getDistrict());
		dto.setCity(employee.getCity());
		dto.setState(employee.getState());
		return dto;
	}

	

	public Employee toEntity(EmployeeDTO dto) {
		return new Employee(dto.getId(), 
				dto.getName(),
				dto.getAge(), 
				dto.getCep(),
				dto.getSex(), 
				dto.getAddress(),
				dto.getDistrict(), 
				dto.getCity(), 
				dto.getState());
	}
}