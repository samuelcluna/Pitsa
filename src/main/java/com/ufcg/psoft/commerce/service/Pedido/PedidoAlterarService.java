package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public interface PedidoAlterarService {
    PedidoResponseDTO update(Long pedidoId, String codigoAcesso, PedidoPostPutRequestDTO pedidoDTO);

    PedidoResponseDTO confirmarPagamento(Long pedidoId, String codigoAcessoCliente, Long clienteId, String metodoPagamento);
}
