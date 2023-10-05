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
public class ClienteV1DeletarPedidoService implements ClienteDeletarPedidoService {

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ClienteRepository clienteRepository;

    public void clienteDelete(Long pedidoId, Long clienteDeletaId) {
        if (pedidoId != null && pedidoId > 0) {
            Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                    .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));
            Cliente pedidoCliente = clienteRepository.findById(pedidoExistente.getClienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));
            Cliente clienteDeleta = clienteRepository.findById(clienteDeletaId)
                    .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

            if (!pedidoCliente.getCodigoAcesso().equals(clienteDeleta.getCodigoAcesso())) {
                throw new InvalidAccessException("Codigo de acesso invalido!");
            }
        }

        if (pedidoId == null) {
            pedidoRepository.findAll().forEach(pedido -> {
                if (pedido.getClienteId().equals(clienteDeletaId)) {
                    pedidoRepository.deleteById(pedido.getId());
                }
            });
        } else {
            pedidoRepository.deleteById(pedidoId);
        }
    }
}