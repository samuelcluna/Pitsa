package com.ufcg.psoft.commerce.service.Sabor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
import com.ufcg.psoft.commerce.dto.Sabor.SaborPatchRequestDTO;
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
    SaborNotificaClienteService saborNotificaClienteService;
    @Autowired
    ModelMapper modelMapper;


    @Override
    @Transactional
    public SaborResponseDTO update(Long saborId, Long estabelecimentoId, String estabelecimentoCodigoAcesso,
                                   SaborPatchRequestDTO saborPatchRequestDTO){

        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento nao encontrado"));

        if(!estabelecimentoExistente.getCodigoAcesso().equals(estabelecimentoCodigoAcesso))
            throw new InvalidAccessException("Codigo de acesso invalido!");

        Sabor saborExistente = saborRepository.findByIdAndEstabelecimentoId(saborId, estabelecimentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Sabor nao encontrado"));

        if(saborExistente.getDisponivel().equals(saborPatchRequestDTO.getDisponivel()))
            if(saborPatchRequestDTO.getDisponivel().equals(true))
                throw new CommerceException("O sabor consultado ja esta disponivel!");
            else
                throw  new CommerceException("O sabor consultado ja esta indisponivel!");

        saborExistente.setDisponivel(saborPatchRequestDTO.getDisponivel());

        if(saborPatchRequestDTO.getDisponivel().equals(true)) {
            String message = " Aproveite! O sabor " + saborExistente.getNome() + "esta disponivel!";
            saborNotificaClienteService.notifyClientes(saborExistente.getClientesInteressados(), message);
        }

        return modelMapper.map(saborRepository.save(saborExistente),SaborResponseDTO.class);
    }
}
