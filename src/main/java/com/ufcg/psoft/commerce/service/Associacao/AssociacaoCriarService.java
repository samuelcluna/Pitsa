package com.ufcg.psoft.commerce.service.Associacao;


import com.ufcg.psoft.commerce.model.Associacao;

@FunctionalInterface
public interface AssociacaoCriarService {
    Associacao save(Long entregadorId, Long estabelecimentoId, String codigoAcesso);
}
