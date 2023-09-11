package com.ufcg.psoft.commerce.service.Estabelecimento;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoResponseDTO;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1AlterarService implements EstabelecimentoAlterarService<EstabelecimentoResponseDTO, EstabelecimentoPostPutRequestDTO> {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    @Transactional
    public EstabelecimentoResponseDTO update(EstabelecimentoPostPutRequestDTO estabelecimentoPutRequestDTO, String codigoAcesso, Long id){
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(id).orElseThrow(RuntimeException::new);
        if(!estabelecimentoExistente.getCodigoAcesso().equals(codigoAcesso)) throw new RuntimeException();
        Estabelecimento estabelecimento = objectMapper.convertValue(estabelecimentoPutRequestDTO, Estabelecimento.class);
        estabelecimento.setId(estabelecimentoExistente.getId());
        return objectMapper.convertValue(estabelecimentoRepository.save(estabelecimento), EstabelecimentoResponseDTO.class);
    }
}
