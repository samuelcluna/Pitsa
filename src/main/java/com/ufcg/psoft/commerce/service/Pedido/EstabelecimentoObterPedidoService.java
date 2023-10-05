package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.model.Pedido;

import java.util.List;

@FunctionalInterface
public interface EstabelecimentoObterPedidoService {
    List<Pedido> estabelecimentoFind(Long pedidoId, Long estabelecimentoObtemId);
}