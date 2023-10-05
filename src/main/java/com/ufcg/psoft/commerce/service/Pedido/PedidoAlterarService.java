package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Pedido;

@FunctionalInterface
public interface PedidoAlterarService {
    Pedido update(Long pedidoId, String codigoAcesso, PedidoPostPutRequestDTO pedidoDTO);
}
