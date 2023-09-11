package com.ufcg.psoft.commerce.service.Entregador;

import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorV1DeletarService implements EntregadorDeletarService {

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public void deletar(Long id, String codigoAcesso) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("O entregador consultado nao existe!"));
        if (!entregador.getCodigoAcesso().equals(codigoAcesso)) {
            throw new InvalidAccessException(("Codigo de acesso invalido!"));
        }
        entregadorRepository.delete(entregador);
    }
}