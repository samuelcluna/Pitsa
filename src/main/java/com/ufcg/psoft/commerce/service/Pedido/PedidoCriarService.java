package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Pedido;

@FunctionalInterface
public interface PedidoCriarService {
    Pedido save(PedidoPostPutRequestDTO pedidoDTO, Long estabelecimentoId, Long clienteId, String clienteCodigoAcesso);

}
