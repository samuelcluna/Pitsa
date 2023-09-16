package com.ufcg.psoft.commerce.service.Sabor;

import com.ufcg.psoft.commerce.dto.Sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.model.Sabor;

@FunctionalInterface
public interface SaborAlterarService {

    SaborResponseDTO update(Long idSabor, Long idEstabelecimento, String codigoAcesso, SaborPostPutRequestDTO requestDTO);

}
