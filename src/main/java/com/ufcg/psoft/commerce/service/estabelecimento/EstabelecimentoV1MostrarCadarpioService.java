package com.ufcg.psoft.commerce.service.estabelecimento;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.CommerceException;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstabelecimentoV1MostrarCadarpioService implements EstabelecimentoMostrarCardapioService<SaborResponseDTO, EstabelecimentoPostPutRequestDTO>{
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public List<SaborResponseDTO> find(EstabelecimentoPostPutRequestDTO estabelecimentoPostRequestDTO, Long id){
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));
        if(!estabelecimentoExistente.getCodigoAcesso().equals(estabelecimentoPostRequestDTO.getCodigoAcesso()))
            throw new InvalidAccessException("Codigo de acesso difere");

        List<SaborResponseDTO> sabores = new ArrayList<>();

        for(Sabor sabor : estabelecimentoExistente.getSabores()) {
            if(sabor.isDisponivel())
                sabores.add(modelMapper.map(sabor, SaborResponseDTO.class));
        }
        for(Sabor sabor : estabelecimentoExistente.getSabores()) {
            if(!sabor.isDisponivel())
                sabores.add(modelMapper.map(sabor, SaborResponseDTO.class));
        }
        return sabores;
    }
    @Override
    public List<SaborResponseDTO> findByTipo(EstabelecimentoPostPutRequestDTO  estabelecimentoPostRequestDTO, Long id, String tipo){
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));
        if(!estabelecimentoExistente.getCodigoAcesso().equals(estabelecimentoPostRequestDTO.getCodigoAcesso()))
            throw new InvalidAccessException("Codigo de acesso difere.");
        if(!(tipo.equals("salgado") || (tipo.equals("doce"))))
            throw new CommerceException("");
        List<SaborResponseDTO> sabores = new ArrayList<>();

        for(Sabor sabor : estabelecimentoExistente.getSabores()) {
            if(sabor.isDisponivel() && sabor.getTipo().equals(tipo))
                sabores.add(modelMapper.map(sabor, SaborResponseDTO.class));
        }
        for(Sabor sabor : estabelecimentoExistente.getSabores()) {
            if(!(sabor.isDisponivel()) && sabor.getTipo().equals(tipo))
                sabores.add(modelMapper.map(sabor, SaborResponseDTO.class));
        }
        return sabores;
    }
}
