package com.ufcg.psoft.commerce.service.Estabelecimento;

import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1DeleteService implements EstabelecimentoDeleteService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    @Transactional
    public void delete(Long id, String codigoAcesso){
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));
        if(!estabelecimentoExistente.getCodigoAcesso().equals(codigoAcesso)) throw new RuntimeException();
        estabelecimentoRepository.deleteById(id);
    }
}
