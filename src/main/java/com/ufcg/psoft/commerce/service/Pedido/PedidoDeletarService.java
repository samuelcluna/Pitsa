package com.ufcg.psoft.commerce.service.Pedido;

import org.springframework.stereotype.Component;

@Component
public interface PedidoDeletarService {

    void clienteDeletarPedido(Long pedidoId, Long clienteId, String clienteCodigoAcesso);

    void clienteDeletarTodosPedidos(Long clienteId, String clienteCodigoAcesso);

    void estabelecimentoDeletarPedido(Long pedidoId, Long estabelecimentoId, String estabelecimentoCodigoAcesso);

    void estabelecimentoDeletarTodosPedidos(Long estabelecimentoId, String estabelecimentoCodigoAcesso);

}
