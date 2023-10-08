package com.ufcg.psoft.commerce.service.Cliente;

import com.ufcg.psoft.commerce.dto.Sabor.SaborResponseDTO;

@FunctionalInterface
public interface ClienteDemonstrarInteresseService {
    SaborResponseDTO update(String codigoAcesso, Long id, Long saborId);
}
