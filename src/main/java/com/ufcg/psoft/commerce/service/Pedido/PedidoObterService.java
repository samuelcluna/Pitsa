package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PedidoObterService {

    List<PedidoResponseDTO> clienteObterPedidos(Long clienteId, String clienteCodigoAcesso);

    PedidoResponseDTO clienteObterPedido(Long pedidoId, Long clienteId, String clienteCodigoAcesso);

    List<PedidoResponseDTO> estabelecimentoObterPedidos(Long estabelecimentoId, String estabelecimentoCodigoAcesso);

    PedidoResponseDTO estabelecimentoObterPedido(Long pedidoId, Long estabelecimentoId, String estabelecimentoCodigoAcesso);

}
