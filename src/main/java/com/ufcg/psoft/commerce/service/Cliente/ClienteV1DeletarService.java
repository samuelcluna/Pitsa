package com.ufcg.psoft.commerce.service.Cliente;

import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteV1DeletarService implements ClienteDeletarService {

    @Autowired
    ClienteRepository repository;

    @Override
    @Transactional
    public void delete(Long id, String codigoAcesso) {
        Cliente delete = repository.findById(id).orElseThrow(() -> new InvalidAccessException("O cliente consultado nao existe!"));
        if(!delete.getCodigoAcesso().equals(codigoAcesso)){
            throw new InvalidAccessException("Codigo de acesso invalido!");
        }
        repository.deleteById(id);
    }
}
