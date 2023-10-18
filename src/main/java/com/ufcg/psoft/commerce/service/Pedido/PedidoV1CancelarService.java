package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1CancelarService implements PedidoCancelarService {
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void cancelar(Long pedidoId, String clienteCodigoAcesso) {
        if (pedidoId != null && pedidoId > 0) {
            Pedido pedido = pedidoRepository.findById(pedidoId)
                    .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));
            Cliente clientePedido = clienteRepository.findById(pedido.getClienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

            if (!clientePedido.getCodigoAcesso().equals(clienteCodigoAcesso)) {
                throw new InvalidAccessException("Codigo de acesso invalido!");
            }

            if (pedido.getStatusEntrega().equals("Pedido pronto") || pedido.getStatusEntrega().equals("Pedido em rota")) {
                throw new InvalidAccessException("Codigo de acesso invalido!");
            } else {
                pedidoRepository.deleteById(pedidoId);
            }
        }
    }
}