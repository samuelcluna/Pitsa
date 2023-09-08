package com.ufcg.psoft.commerce.service.Sabor;

import com.ufcg.psoft.commerce.dto.Sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Sabor;

@FunctionalInterface
public interface SaborCriarService {

    Sabor criar(SaborPostPutRequestDTO requestDTO, Long id, String codigoAcesso);

}
