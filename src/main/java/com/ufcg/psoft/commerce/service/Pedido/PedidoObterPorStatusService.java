package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.model.enums.PedidoStatusEntregaEnum;

import java.util.List;

@FunctionalInterface
public interface PedidoObterPorStatusService {
    List<PedidoResponseDTO> find(Long clienteId, Long estabelecimentoId, String codigoAcesso, PedidoStatusEntregaEnum statusEntrega);
}
