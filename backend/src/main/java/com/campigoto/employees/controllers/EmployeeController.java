package com.campigoto.employees.controllers;

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
    public ResponseEntity<List<EmployeeDTO>> findByCep(@PathVariable("cep") String cep) {
        return ResponseEntity.ok(service.findByCep(cep));
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@Valid @RequestBody EmployeeDTO dto) throws Exception {
        return ResponseEntity.ok(service.save(dto));
    }

    @PutMapping
    public ResponseEntity<EmployeeDTO> update(@Valid @RequestBody EmployeeDTO dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @PatchMapping
    public ResponseEntity<EmployeeDTO> updatePatch(@RequestBody EmployeeDTO dto) throws Exception {
        return ResponseEntity.ok(service.updatePatch(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.accepted().build();
    }
}
