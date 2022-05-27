package com.campigoto.employees.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.campigoto.employees.dto.AddressDTO;
import com.campigoto.employees.dto.EmployeeDTO;
import com.campigoto.employees.entities.Employee;
import com.campigoto.employees.mappers.EmployeeMapper;
import com.campigoto.employees.repositories.EmployeeRepository;
import com.campigoto.employees.services.exceptions.DatabaseException;
import com.campigoto.employees.services.exceptions.ResourceNotFoundException;

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

    @Transactional(readOnly = true)
    public EmployeeDTO findById(Long id) {
        Employee employee = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Id not found "));
        return mapper.toDTO(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> findByCep(String cep) throws Exception {
    	List<EmployeeDTO> employees = repository.findByCep(cep).stream().map(mapper::toDTO).collect(Collectors.toList());
    	
    	if (employees.isEmpty()) throw new Exception("Cep not found ");
    	
        return employees;
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> findAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }
    
    @Transactional
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
        if ((dto.getCep() != null && !dto.getCep().isBlank()) && (dto.getAddress() == null || dto.getAddress().isEmpty())) {
            AddressDTO address = viaCepService.findAddressByCep(dto.getCep());
            dto.setAddress(address.getAddress());
            dto.setCity(address.getCity());
            dto.setState(address.getState());
            dto.setDistrict(address.getDistrict());
        }
    }

    public void delete(Long id) {
    	try {
    		repository.deleteById(id);
    		}
    		catch (EmptyResultDataAccessException e) {
    			throw new ResourceNotFoundException("Id not found " + id);
    		}
    		catch (DataIntegrityViolationException e) {
    			throw new DatabaseException("Integrity violation");
    		}
    }
    
    @Transactional
	public EmployeeDTO insert(EmployeeDTO dto) throws Exception {
    	Employee entity = new Employee();   
    	getAddressIfNotInformed(dto);
    	entity = repository.save(mapper.toEntity(dto));
		return new EmployeeDTO(entity);
	}

	@Transactional
	public EmployeeDTO update(Long id, EmployeeDTO dto) throws Exception {
		try {
			Employee entity = repository.getById(id);
			
			if (entity == null ) throw new Exception ("User not found");
			 
	    	getAddressIfNotInformed(dto);
			entity = repository.save(mapper.toEntity(dto));
			
			return new EmployeeDTO(entity);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}		
	}
          
}

