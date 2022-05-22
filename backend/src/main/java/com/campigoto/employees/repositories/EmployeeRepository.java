package com.campigoto.employees.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.campigoto.employees.entities.Employee;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByCep(String cep);
}
