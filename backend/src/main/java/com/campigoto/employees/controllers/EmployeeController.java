package com.campigoto.employees.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.campigoto.employees.dto.EmployeeDTO;
import com.campigoto.employees.services.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {


	@Autowired
    public  EmployeeService service;

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/cep/{cep}")
    public ResponseEntity<List<EmployeeDTO>> findByCep(@PathVariable("cep") String cep) throws Exception {
        return ResponseEntity.ok(service.findByCep(cep));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }
    
    @PostMapping
	public ResponseEntity<EmployeeDTO> insert(@Valid @RequestBody EmployeeDTO dto) throws Exception {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
    

	@PutMapping(value = "/{id}")
	public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto) throws Exception {
		dto = service.update(id, dto);
		if (id == null) throw new Exception("User not found");
		return ResponseEntity.ok().body(dto);
	}
	
    @PatchMapping(value = "/{id}")
    public ResponseEntity<EmployeeDTO> updatePatch(@PathVariable Long id, @Valid@RequestBody EmployeeDTO dto) throws Exception {
		dto = service.update(id, dto);
		if (id == null) throw new Exception("User not found");
        return ResponseEntity.ok(service.updatePatch(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.accepted().build();
    }
}
