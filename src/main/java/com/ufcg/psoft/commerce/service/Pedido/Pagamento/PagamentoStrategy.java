package com.ufcg.psoft.commerce.service.Pedido.Pagamento;

import com.ufcg.psoft.commerce.model.Pedido;


public abstract class PagamentoStrategy {

    protected Double desconto = 0.0;

    public void pagar(Pedido pedido) {
        processarPagamento(pedido);
    }

    protected void processarPagamento(Pedido pedido) {
        Double desconto = getDesconto(pedido);
        pedido.setPreco(pedido.getPreco() - desconto);
        pedido.setStatusPagamento(true);
    }

    protected Double getDesconto(Pedido pedido) {
        return pedido.getPreco() * this.desconto;
    }
}
