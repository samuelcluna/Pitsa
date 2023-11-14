package com.ufcg.psoft.commerce.service.Sabor;

import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborV1DeletarService implements SaborDeletarService {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    SaborRepository saborRepository;

    @Override
    @Transactional
    public void delete(Long idSabor, Long idEstabelecimento, String codigoAcesso) {
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não existe."));
        if (!estabelecimentoExistente.getCodigoAcesso().equals(codigoAcesso)) {
            throw new InvalidAccessException("Codigo de acesso invalido!"); // Bad Request
        }

        if (!saborRepository.existsByIdAndEstabelecimentoId(idSabor, idEstabelecimento)) {
            throw new ResourceNotFoundException("O sabor consultado nao existe!");
        }

        Sabor saborRemover = saborRepository.findById(idSabor)
                .orElseThrow(() -> new ResourceNotFoundException("Sabor não encontrado."));
        saborRepository.delete(saborRemover);
    }
}
