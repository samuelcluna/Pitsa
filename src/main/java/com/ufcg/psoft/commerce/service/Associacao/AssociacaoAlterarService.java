package com.ufcg.psoft.commerce.service.Associacao;

import com.ufcg.psoft.commerce.dto.Associacao.AssociacaoResponseDTO;
import com.ufcg.psoft.commerce.model.Associacao;

@FunctionalInterface
public interface AssociacaoAlterarService {
    public AssociacaoResponseDTO update(Long entregadorId, Long estabelecimentoId, String codigoAcesso);
}
