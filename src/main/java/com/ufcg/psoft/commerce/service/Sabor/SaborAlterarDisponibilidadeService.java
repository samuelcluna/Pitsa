package com.ufcg.psoft.commerce.service.Sabor;

import com.ufcg.psoft.commerce.dto.Sabor.SaborResponseDTO;

@FunctionalInterface
public interface SaborAlterarDisponibilidadeService {
    SaborResponseDTO update( Long saborId, Long estabelecimentoId, String estabelecimentoCodigoAcesso, Boolean disponibilidade);
}
