package com.ufcg.psoft.commerce.service.Pedido.Pagamento;

import com.ufcg.psoft.commerce.model.Pedido;

@FunctionalInterface
public interface PagamentoStrategy {
    void pagar(Pedido pedido);
}
