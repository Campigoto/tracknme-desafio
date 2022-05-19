package com.campigoto.employees.dto;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.campigoto.employees.entities.Employee;
import com.campigoto.employees.entities.Sex;

public class EmployeeDTO implements Serializable {
	private static final long serialVersionUID = 1L;



	private Long id;
	
	@Size(min = 5, max = 60, message = "Deve ter entre 5 e 60 caracteres")
	@NotBlank(message = "Campo requerido")
	private String name;

	
	@Positive(message = "Idade deve ser um valor positivo")
	private int age;
	

	@Size(min = 8, max = 8, message = "Deve ter  8 caracteres")
	@NotBlank(message = "Campo requerido")
	private String cep;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String address;
    private String district;
    private String city;
    private String state;
	
    public EmployeeDTO() {
	}

	public EmployeeDTO(Long id,	String name, int age,String cep,Sex sex, String address, String district, String city, String state) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.cep = cep;
		this.sex = sex;
		this.address = address;
		this.district = district;
		this.city = city;
		this.state = state;
	}
    

	
	public EmployeeDTO(Employee entity) {
		this.id = entity.getId();
		this.name = entity.getName();
		this.age = entity.getAge();
		this.cep = entity.getCep();
		this.sex = entity.getSex();
		this.address = entity.getAddress();
		this.district = entity.getDistrict();
		this.city = entity.getCity();
		this.state = entity.getState();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
    
}

