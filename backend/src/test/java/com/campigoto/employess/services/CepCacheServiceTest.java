package com.campigoto.employess.services;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.campigoto.employees.dto.AddressDTO;
import com.campigoto.employees.services.CepCacheService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
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
