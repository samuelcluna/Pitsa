package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;
import org.springframework.stereotype.Component;

@FunctionalInterface
public interface PedidoDefinirEntregadorService {
    PedidoResponseDTO definirEntregador(Long estabelecimentoId, String codidoAcessoEstabelecimento, Long pedidoId, Long entregadorId);
}
