package com.ufcg.psoft.commerce.service.Sabor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.CommerceException;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.Sabor.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaborV1AlterarDisponibilidadeService implements SaborAlterarDisponibilidadeService{

    @Autowired
    SaborRepository saborRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    @Transactional
    public SaborResponseDTO update(Long saborId, Long estabelecimentoId, String estabelecimentoCodigoAcesso,
                                   Boolean disponibilidade){

        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(() -> new ResourceNotFoundException(""));

        if(!estabelecimentoExistente.getCodigoAcesso().equals(estabelecimentoCodigoAcesso))
            throw new InvalidAccessException("");

        Sabor saborExistente = saborRepository.findByIdAndEstabelecimentoId(saborId, estabelecimentoId)
                .orElseThrow(() -> new ResourceNotFoundException(""));

        if(saborExistente.getDisponivel().equals(disponibilidade))
            throw new CommerceException("");
        saborExistente.setDisponivel(disponibilidade);

        return objectMapper.convertValue(saborRepository.save(saborExistente),SaborResponseDTO.class);
    }
}
