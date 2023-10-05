package com.ufcg.psoft.commerce.service.Cliente;

import com.ufcg.psoft.commerce.dto.Sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.service.Sabor.SaborDemonstrarInteresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteV1DemonstrarInteresseService implements  ClienteDemonstrarInteresseService{

    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    SaborDemonstrarInteresseService saborDemonstrarInteresseService;

    @Override
    public SaborResponseDTO update(String codigoAcesso, Long id, Long saborId){
        Cliente clienteExistente = clienteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(""));

        if(!clienteExistente.getCodigoAcesso().equals(codigoAcesso))
            throw new InvalidAccessException("Codigo de acesso invalido");

        SaborResponseDTO sabor = saborDemonstrarInteresseService.find(saborId);
        if(sabor.isDisponivel())
            throw new InvalidAccessException("O sabor ja esta disponivel");

        sabor.getClientesInteressados().add(id);
        return saborDemonstrarInteresseService.update(id, sabor);
    }
}
