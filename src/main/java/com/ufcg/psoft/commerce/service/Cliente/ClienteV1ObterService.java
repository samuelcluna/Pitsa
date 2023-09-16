package com.ufcg.psoft.commerce.service.Cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteV1ObterService implements ClienteObterService {

    @Autowired
    ClienteRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public ClienteResponseDTO find(Long idCliente) {
        Cliente retorno = repository.findById(idCliente).orElseThrow(() -> new InvalidAccessException("O cliente consultado nao existe!"));
        return objectMapper.convertValue(retorno, ClienteResponseDTO.class);
    }

    @Override
    public List<Cliente> findAll() {
        return repository.findAll();
    }
}
