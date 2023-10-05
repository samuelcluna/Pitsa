package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteV1ObterPedidoService implements ClienteObterPedidoService {

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ClienteRepository clienteRepository;


    public List<Pedido> clienteFind(Long pedidoId, Long clienteObtemId) {
        if (pedidoId != null && pedidoId > 0) {
            Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                    .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));
            Cliente pedidoCliente = clienteRepository.findById(pedidoExistente.getClienteId())
                    .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));
            Cliente clienteObtem = clienteRepository.findById(clienteObtemId)
                    .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

            if (!pedidoCliente.getCodigoAcesso().equals(clienteObtem.getCodigoAcesso())) {
                throw new InvalidAccessException ("Codigo de acesso invalido!");
            }
        }

        if (pedidoId == null) {
            List<Pedido> pedidos = new ArrayList<>();
            pedidoRepository.findAll().forEach(pedido -> {
                if (pedido.getClienteId().equals(clienteObtemId)) {
                    pedidos.add(pedido);
                }
            });
            return pedidos;
        } else {
            Pedido pedido = pedidoRepository.findById(pedidoId)
                    .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));
            return List.of(pedido);
        }
    }
}