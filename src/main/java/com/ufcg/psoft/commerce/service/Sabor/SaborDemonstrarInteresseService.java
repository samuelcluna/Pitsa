package com.ufcg.psoft.commerce.service.Sabor;

import com.ufcg.psoft.commerce.dto.Sabor.SaborResponseDTO;

public interface SaborDemonstrarInteresseService {
    SaborResponseDTO find(Long id);
    SaborResponseDTO update(Long id, SaborResponseDTO saborDTO);
}
