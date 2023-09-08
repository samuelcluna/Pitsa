package com.ufcg.psoft.commerce.service.estabelecimento;

import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1DeleteService implements EstabelecimentoDeleteService{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    public void delete(Long id, String codigoAcesso){
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(id).orElseThrow(RuntimeException::new);
        if(!estabelecimentoExistente.getCodigoAcesso().equals(codigoAcesso)) throw new RuntimeException();
        estabelecimentoRepository.deleteById(id);
    }
}
