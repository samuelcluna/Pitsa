package com.ufcg.psoft.commerce.service.Entregador;

import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntregadorV1ObterService implements EntregadorObterService {

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<EntregadorResponseDTO> find(Long id) {
        if (id != null && id > 0) {
            Entregador entregador = entregadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("O entregador consultado nao existe!"));
            return List.of(new EntregadorResponseDTO(entregador));
        }
        List<Entregador> entregadores = entregadorRepository.findAll();
        return entregadores.stream()
                .map(EntregadorResponseDTO::new)
                .collect(Collectors.toList());
    }
}
