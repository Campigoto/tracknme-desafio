package com.campigoto.employess.services;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.campigoto.employees.dto.AddressDTO;
import com.campigoto.employees.services.CepCacheService;

@ExtendWith(SpringExtension.class)
public class CepCacheServiceTest {

	@InjectMocks
	private CepCacheService cepCacheService;
	
	@Test
	void whenGetCepAndCacheIsEmptyThenReturnNull() {
		
		String someInexistentCep = "xablau";
		AddressDTO dto = cepCacheService.getCep(someInexistentCep);
		
		assertNull(dto);
	}
	
}
