package com.ufcg.psoft.commerce.service.Sabor;

import com.ufcg.psoft.commerce.dto.Sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.model.Sabor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface SaborObterService {

    List<SaborResponseDTO> findAll(Long idEstabelecimento, String codigoAcesso);

    SaborResponseDTO find(Long idSabor, Long idEstabelecimento, String codigoAcesso);

}
