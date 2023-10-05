package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.model.Pedido;

import java.util.List;

@FunctionalInterface
public interface ClienteObterPedidoService {
    List<Pedido> clienteFind(Long pedidoId, Long clienteObtemId);
}