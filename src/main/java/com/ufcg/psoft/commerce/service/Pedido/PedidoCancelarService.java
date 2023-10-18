package com.ufcg.psoft.commerce.service.Pedido;

@FunctionalInterface
public interface PedidoCancelarService {
    void cancelar(Long pedidoId, String codigoAcessoCliente);
}