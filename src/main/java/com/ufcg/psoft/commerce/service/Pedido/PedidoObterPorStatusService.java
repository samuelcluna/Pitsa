package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;

import java.util.List;

@FunctionalInterface
public interface PedidoObterPorStatusService {
    List<PedidoResponseDTO> find(Long clienteId, Long estabelecimentoId, String codigoAcesso, String statusEntrega);
}
