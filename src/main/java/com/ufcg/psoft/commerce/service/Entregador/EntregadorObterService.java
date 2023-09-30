package com.ufcg.psoft.commerce.service.Entregador;

import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;

import java.util.List;

@FunctionalInterface
public interface EntregadorObterService {
    List<EntregadorResponseDTO> find(Long id);
}
