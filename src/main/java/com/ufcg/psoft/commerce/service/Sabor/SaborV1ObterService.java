package com.ufcg.psoft.commerce.service.Sabor;

import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.Sabor.SaborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SaborV1ObterService implements SaborObterService{

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    SaborRepository saborRepository;

    @Override
    public Set<Sabor> obterTodos(Long idEstabelecimento, String codigoAcesso) {
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não existe."));
        if (!estabelecimentoExistente.getCodigoAcesso().equals(codigoAcesso)) throw new RuntimeException("Codigo de acesso invalido!");
        return saborRepository.findAllByEstabelecimentoId(idEstabelecimento);
    }

    @Override
    public Sabor obter(Long idSabor, Long idEstabelecimento, String codigoAcesso) {
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não existe."));
        if (!estabelecimentoExistente.getCodigoAcesso().equals(codigoAcesso)) throw new RuntimeException("Codigo de acesso invalido!");

        return saborRepository.findByIdAndEstabelecimentoId(idSabor, idEstabelecimento)
                .orElseThrow(() -> new ResourceNotFoundException("Sabor não existe."));

    }
}
