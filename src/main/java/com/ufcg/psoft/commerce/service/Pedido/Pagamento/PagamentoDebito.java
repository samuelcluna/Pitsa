package com.ufcg.psoft.commerce.service.Pedido.Pagamento;

import com.ufcg.psoft.commerce.model.Pedido;

public class PagamentoDebito implements PagamentoStrategy {
    @Override
    public void pagar(Pedido pedido) {
        Double desconto = pedido.getPreco() * 0.025;
        pedido.setPreco(pedido.getPreco()-desconto);
        pedido.setStatusPagamento(true);
    }
}
