package com.ufcg.psoft.commerce.service.Cliente;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteV1AlterarService implements ClienteAlterarService{

    @Autowired
    ClienteRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    @Transactional
    public ClienteResponseDTO update(String codigoAcesso, Long idCliente, ClientePostPutRequestDTO cliente) {
        Cliente delete = repository.findById(idCliente).orElseThrow(() -> new InvalidAccessException("O cliente consultado nao existe!"));
        if(!delete.getCodigoAcesso().equals(codigoAcesso)){
            throw new InvalidAccessException("Codigo de acesso invalido!");
        }
        Cliente atualizar = objectMapper.convertValue(cliente, Cliente.class);
        atualizar.setId(delete.getId());
        return objectMapper.convertValue(repository.save(atualizar),ClienteResponseDTO.class);
    }
}
