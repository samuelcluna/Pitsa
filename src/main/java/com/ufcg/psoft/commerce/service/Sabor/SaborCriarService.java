package com.ufcg.psoft.commerce.service.Sabor;

import com.ufcg.psoft.commerce.dto.Sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.model.Sabor;

@FunctionalInterface
public interface SaborCriarService {

    SaborResponseDTO save(SaborPostPutRequestDTO requestDTO, Long id, String codigoAcesso);

}
