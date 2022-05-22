package com.campigoto.employees.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.campigoto.employees.dto.AddressDTO;
import com.campigoto.employees.dto.EmployeeDTO;
import com.campigoto.employees.entities.Employee;
import com.campigoto.employees.mappers.EmployeeMapper;
import com.campigoto.employees.repositories.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
    public  EmployeeRepository repository;
	
	@Autowired
    public  EmployeeMapper mapper;

	@Autowired
    public  ViaCepService viaCepService;

    public EmployeeDTO save(EmployeeDTO dto) throws Exception {
        getAddressIfNotInformed(dto);
        Employee employee = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(employee);
    }

    public EmployeeDTO findById(Long id) {
        Employee employee = repository.findById(id).orElseThrow();
        return mapper.toDTO(employee);
    }

    public List<EmployeeDTO> findByCep(String cep) {
        return repository.findByCep(cep).stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public List<EmployeeDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public EmployeeDTO update(EmployeeDTO dto) {
        Employee employee = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(employee);
    }

    public EmployeeDTO updatePatch(EmployeeDTO dto) throws Exception {
        Employee employee = repository.findById(dto.getId()).orElseThrow();

        if (dto.getName() != null && !dto.getName().isBlank()) {
            employee.setName(dto.getName());
        }

        if (dto.getAge() != null && dto.getAge() > 0) {
            employee.setAge(dto.getAge());
        }

        if (dto.getCep() != null && !dto.getCep().isBlank()) {
            employee.setCep(dto.getCep());
        }

        if (dto.getAddress() != null && !dto.getAddress().isBlank()) {
            employee.setAddress(dto.getAddress());
        }

        if (dto.getDistrict() != null && !dto.getDistrict().isBlank()) {
            employee.setDistrict(dto.getDistrict());
        }

        if (dto.getCity() != null && !dto.getCity().isBlank()) {
            employee.setCity(dto.getCity());
        }

        if (dto.getState() != null && !dto.getState().isBlank()) {
            employee.setState(dto.getState());
        }

        getAddressIfNotInformed(dto);

        employee = repository.save(mapper.toEntity(dto));
        return mapper.toDTO(employee);
    }

    private void getAddressIfNotInformed(EmployeeDTO dto) throws Exception {
        if ((dto.getCep() != null && dto.getCep().isBlank()) && (dto.getAddress() == null || dto.getAddress().isEmpty())) {
            AddressDTO address = viaCepService.findAddressByCep(dto.getCep());
            dto.setAddress(address.getAddress());
            dto.setCity(address.getCity());
            dto.setState(address.getState());
            dto.setDistrict(address.getDistrict());
        }
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
          
}

