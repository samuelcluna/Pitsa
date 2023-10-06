package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Pedido;
import org.springframework.stereotype.Component;

@Component
public interface PedidoAlterarService {
    Pedido update(Long pedidoId, String codigoAcesso, PedidoPostPutRequestDTO pedidoDTO);

    Pedido update(Long pedidoId, String codigoAcessoCliente, Long clienteId, String metodoPagamento);
}
