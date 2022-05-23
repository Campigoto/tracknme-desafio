package com.campigoto.employees.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.campigoto.employees.dto.AddressDTO;


@Service
public class CepCacheService {

    private final Map<String, AddressDTO> cache = new HashMap<>();

    public AddressDTO getCep(String cep) {

        if (cache.isEmpty())
            return null;

        if (!cache.containsKey(cep))
            return null;

        return cache.get(cep);
    }

    public void saveCep(AddressDTO dto) {
        cache.put(dto.getCep(), dto);
    }

    public Optional<AddressDTO> getOrFetch(String cep, Function<String, AddressDTO> function) {

        AddressDTO dto = getCep(cep);
        if (dto != null)
            return Optional.of(cache.get(cep));

        dto = function.apply(cep);
        cache.put(cep, dto);

        return Optional.of(dto);
    }
}
