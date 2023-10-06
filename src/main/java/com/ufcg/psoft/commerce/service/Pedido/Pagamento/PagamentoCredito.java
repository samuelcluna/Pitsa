package com.ufcg.psoft.commerce.service.Pedido.Pagamento;

import com.ufcg.psoft.commerce.model.Pedido;

public class PagamentoCredito implements PagamentoStrategy {
    @Override
    public void pagar(Pedido pedido) {
        pedido.setStatusPagamento(true);
    }
}
