package com.ufcg.psoft.commerce.service.Sabor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Sabor.SaborResponseDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SaborV1ObterService implements SaborObterService{

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private SaborRepository saborRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public List<SaborResponseDTO> findAll(Long idEstabelecimento, String codigoAcesso) {
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não existe."));
        if (!estabelecimentoExistente.getCodigoAcesso().equals(codigoAcesso)) throw new InvalidAccessException("Codigo de acesso invalido!");
        List<Sabor> sabores = saborRepository.findAll();
        List<SaborResponseDTO> saboresResponseDTOS = new ArrayList<>();

        for(Sabor sabor : sabores)
            saboresResponseDTOS.add(modelMapper.map(sabor, SaborResponseDTO.class));

        return saboresResponseDTOS;
    }

    @Override
    @Transactional
    public SaborResponseDTO find(Long idSabor, Long idEstabelecimento, String codigoAcesso) {
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não existe."));
        if (!estabelecimentoExistente.getCodigoAcesso().equals(codigoAcesso)) throw new InvalidAccessException("Codigo de acesso invalido!");

        Sabor saborExistente = saborRepository.findByIdAndEstabelecimentoId(idSabor,idEstabelecimento).orElseThrow(() -> new ResourceNotFoundException("O sabor consultado nao existe!"));
        return modelMapper.map(saborExistente, SaborResponseDTO.class);

    }
}
