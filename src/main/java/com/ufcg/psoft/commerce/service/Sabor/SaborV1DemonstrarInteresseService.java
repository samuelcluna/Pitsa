package com.ufcg.psoft.commerce.service.Sabor;

import com.ufcg.psoft.commerce.dto.Sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborV1DemonstrarInteresseService implements SaborDemonstrarInteresseService{

    @Autowired
    SaborRepository saborRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public SaborResponseDTO find(Long id){
        return modelMapper.map(saborRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("O sabor consultado nao existe!")), SaborResponseDTO.class);
    }

    @Override
    public SaborResponseDTO update(Long id, SaborResponseDTO saborDTO){
        Sabor sabor = saborRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("O sabor consultado nao existe!"));
        modelMapper.map(saborDTO, sabor);
        return modelMapper.map(saborRepository.save(sabor), SaborResponseDTO.class);
    }
}
