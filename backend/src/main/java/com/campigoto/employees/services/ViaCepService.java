package com.campigoto.employees.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.campigoto.employees.dto.AddressDTO;

@Service
public class ViaCepService {

    @Value("${via.cep.url}")
    public String viaCepUrl;

    @Autowired
    public CepCacheService cepCacheService;

    public AddressDTO findAddressByCep(String cep) throws Exception {
        return cepCacheService.getOrFetch(cep, (c) -> {

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<AddressDTO> response = restTemplate.getForEntity(viaCepUrl, AddressDTO.class, c);

            if (!response.getStatusCode().is2xxSuccessful())
                return null;

            AddressDTO dto = response.getBody();
            if (dto.isError())
                return null;

            cepCacheService.saveCep(dto);
            return dto;

        }).orElseThrow(() -> new Exception("Cep não encontrado"));
    }
}
