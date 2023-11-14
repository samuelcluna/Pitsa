package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;

@FunctionalInterface
public interface PedidoCriarService {
    PedidoResponseDTO save(PedidoPostPutRequestDTO pedidoDTO, Long estabelecimentoId, Long clienteId, String clienteCodigoAcesso);

}
