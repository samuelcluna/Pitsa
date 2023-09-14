package com.ufcg.psoft.commerce.service.Associacao;


import com.ufcg.psoft.commerce.dto.Associacao.AssociacaoPostPutDTO;
import com.ufcg.psoft.commerce.model.Associacao;

@FunctionalInterface
public interface AssociacaoCriarService {
    Associacao criar(Long entregadorId, Long estabelecimentoId, String codigoAcesso);
}
