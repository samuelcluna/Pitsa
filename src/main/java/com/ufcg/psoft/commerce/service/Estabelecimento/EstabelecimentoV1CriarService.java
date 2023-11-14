package com.ufcg.psoft.commerce.service.Estabelecimento;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoResponseDTO;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class EstabelecimentoV1CriarService implements EstabelecimentoCriarService<EstabelecimentoResponseDTO, EstabelecimentoPostPutRequestDTO> {

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    @Transactional
    public EstabelecimentoResponseDTO save(EstabelecimentoPostPutRequestDTO estabelecimentoPostDTO, String codigoDeAcesso){

        if(!codigoDeAcesso.equals(estabelecimentoPostDTO.getCodigoAcesso()))
            throw new InvalidAccessException("Codigo de acesso invalido!");

        Estabelecimento estabelecimento = estabelecimentoRepository.save(objectMapper.convertValue(estabelecimentoPostDTO, Estabelecimento.class));

        return objectMapper.convertValue(estabelecimento, EstabelecimentoResponseDTO.class);
    }
}
