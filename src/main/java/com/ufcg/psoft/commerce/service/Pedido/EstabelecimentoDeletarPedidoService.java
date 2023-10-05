package com.ufcg.psoft.commerce.service.Pedido;

@FunctionalInterface
public interface EstabelecimentoDeletarPedidoService {
    void estabelecimentoDelete(Long pedidoId, Long estabelecimentoDeletaId);
}