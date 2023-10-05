package com.ufcg.psoft.commerce.service.Pedido;

@FunctionalInterface
public interface ClienteDeletarPedidoService {
    void clienteDelete(Long pedidoId, Long clienteDeletaId);
}