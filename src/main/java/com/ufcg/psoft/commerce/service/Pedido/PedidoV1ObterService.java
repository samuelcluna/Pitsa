package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoV1ObterService implements PedidoObterService {

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<PedidoResponseDTO> clienteObterPedidos(Long clienteId, String clienteCodigoAcesso) {
        Cliente clienteExistente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

        if (!clienteExistente.getCodigoAcesso().equals(clienteCodigoAcesso)) throw new InvalidAccessException("Código de acesso inválido!");

        List<PedidoResponseDTO> pedidos = new ArrayList<>();
        pedidoRepository.findAll().forEach(pedido -> {
            if (pedido.getClienteId().equals(clienteId)) {
                pedidos.add(modelMapper.map(pedido, PedidoResponseDTO.class));
            }
        });
        return pedidos;
    }

    @Override
    public PedidoResponseDTO clienteObterPedido(Long pedidoId, Long clienteId, String clienteCodigoAcesso) {
        Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));
        Cliente clienteExistente = clienteRepository.findById(pedidoExistente.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

        if (!clienteExistente.getCodigoAcesso().equals(clienteCodigoAcesso)) throw new InvalidAccessException("Código de acesso inválido!");

        if (!pedidoExistente.getClienteId().equals(clienteExistente.getId())) throw new ResourceNotFoundException("O pedido para esse cliente não foi encontrado!");

        return modelMapper.map(pedidoExistente, PedidoResponseDTO.class);
    }

    @Override
    public List<PedidoResponseDTO> estabelecimentoObterPedidos(Long estabelecimentoId, String estabelecimentoCodigoAcesso) {
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

        if (!estabelecimentoExistente.getCodigoAcesso().equals(estabelecimentoCodigoAcesso)) throw new InvalidAccessException("Código de acesso inválido!");

        List<PedidoResponseDTO> pedidos = new ArrayList<>();
        pedidoRepository.findAll().forEach(pedido -> {
            if (pedido.getEstabelecimentoId().equals(estabelecimentoId)) {
                pedidos.add(modelMapper.map(pedido, PedidoResponseDTO.class));
            }
        });
        return pedidos;
    }

    @Override
    public PedidoResponseDTO estabelecimentoObterPedido(Long pedidoId, Long estabelecimentoId, String estabelecimentoCodigoAcesso) {
        Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));

        if (!estabelecimentoExistente.getCodigoAcesso().equals(estabelecimentoCodigoAcesso)) throw new InvalidAccessException("Código de acesso inválido!");

        if (!pedidoExistente.getEstabelecimentoId().equals(estabelecimentoExistente.getId())) throw new ResourceNotFoundException("O pedido para esse estabelecimento não foi encontrado!");

        return modelMapper.map(pedidoExistente, PedidoResponseDTO.class);
    }
}
