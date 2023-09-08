package com.ufcg.psoft.commerce.service.estabelecimento;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.EstabelecimentoResponseDTO;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1CriarService implements EstabelecimentoCriarService<EstabelecimentoResponseDTO, EstabelecimentoPostPutRequestDTO>{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ObjectMapper objectMapper;
    public EstabelecimentoResponseDTO save(EstabelecimentoPostPutRequestDTO estabelecimentoPostDTO, String codigoDeAcesso){
        if(!codigoDeAcesso.equals(estabelecimentoPostDTO.getCodigoAcesso())) throw new RuntimeException();
        Estabelecimento estabelecimento = estabelecimentoRepository.save(objectMapper.convertValue(estabelecimentoPostDTO, Estabelecimento.class));
        return objectMapper.convertValue(estabelecimento, EstabelecimentoResponseDTO.class);
    }
}
