package com.ufcg.psoft.commerce.service.Cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteV1CriarService implements ClienteCriarService{

    @Autowired
    ClienteRepository repository;

    @Autowired
    ObjectMapper objectMapper;
    @Override
    public ClienteResponseDTO criar(ClientePostPutRequestDTO cliente) {
        Cliente adicionado = repository.save(objectMapper.convertValue(cliente, Cliente.class));
        return objectMapper.convertValue(adicionado, ClienteResponseDTO.class);
    }
}
