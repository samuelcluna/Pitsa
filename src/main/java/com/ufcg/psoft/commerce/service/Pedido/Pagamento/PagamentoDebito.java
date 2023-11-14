package com.ufcg.psoft.commerce.service.Pedido.Pagamento;

import com.ufcg.psoft.commerce.model.Pedido;

public class PagamentoDebito extends PagamentoStrategy {
    public PagamentoDebito() {
        desconto = 0.025;
    }
}
