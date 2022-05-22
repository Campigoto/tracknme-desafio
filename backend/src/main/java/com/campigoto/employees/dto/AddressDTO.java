package com.campigoto.employees.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AddressDTO implements Serializable {
	private static final long serialVersionUID = 1L;

    private String cep;

    @JsonProperty("logradouro")
    private String address;

    @JsonProperty("bairro")
    private String district;

    @JsonProperty("localidade")
    private String city;


	@JsonProperty("uf")
    private String state;

    @JsonProperty("erro")
    private boolean error;

    
    public AddressDTO() {
	}
    

    public AddressDTO(String cep, String address, String district, String city, String state, boolean error) {
		super();
		this.cep = cep;
		this.address = address;
		this.district = district;
		this.city = city;
		this.state = state;
		this.error = error;
		
	}


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
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


	public boolean isError() {
		return error;
	}


	public void setError(boolean error) {
		this.error = error;
	}
    
    

    
}
