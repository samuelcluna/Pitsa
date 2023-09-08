package com.ufcg.psoft.commerce.service.estabelecimento;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.SaborResponseDTO;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EstabelecimentoV1MostrarCadarpioService implements EstabelecimentoMostrarCardapioService<SaborResponseDTO, EstabelecimentoPostPutRequestDTO>{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ObjectMapper objectMapper;
    public Set<SaborResponseDTO> find(EstabelecimentoPostPutRequestDTO estabelecimentoPostRequestDTO, Long id){
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(id).orElseThrow(RuntimeException::new);
        if(!estabelecimentoExistente.getCodigoAcesso().equals(estabelecimentoPostRequestDTO.getCodigoAcesso()))
            throw new RuntimeException();
        Set<SaborResponseDTO> cardapio = objectMapper.convertValue(estabelecimentoExistente.getCardapio(), new TypeReference<>() {});
        return cardapio;
    }
}
