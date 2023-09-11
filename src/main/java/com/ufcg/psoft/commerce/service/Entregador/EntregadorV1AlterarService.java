package com.ufcg.psoft.commerce.service.Entregador;

import com.ufcg.psoft.commerce.dto.Entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorV1AlterarService implements EntregadorAlterarService {

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public Entregador alterar(Long id, EntregadorPostPutRequestDTO entregadorPostPutRequestDTO, String codigoAcesso) {

        Entregador entregador = entregadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("O entregador consultado nao existe!"));
        if (!entregador.getCodigoAcesso().equals(codigoAcesso)) {
            throw new InvalidAccessException(("Codigo de acesso invalido!"));
        }
        modelMapper.map(entregadorPostPutRequestDTO, entregador);
        return entregadorRepository.save(entregador);
    }
}