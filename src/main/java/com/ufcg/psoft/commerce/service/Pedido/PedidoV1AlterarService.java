package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1AlterarService implements PedidoAlterarService {

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PagamentoStrategy pagamentoStrategy;

    @Override
    public Pedido update(Long pedidoId, String codigoAcesso, PedidoPostPutRequestDTO pedidoDTO) {
        Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));

        Cliente clienteExistente = clienteRepository.findById(pedidoExistente.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

        if (!clienteExistente.getCodigoAcesso().equals(codigoAcesso)) {
            throw new InvalidAccessException("Codigo de acesso invalido!");
        }

        modelMapper.map(pedidoDTO, pedidoExistente);
        return pedidoRepository.save(pedidoExistente);
    }

    @Override
    public PedidoResponseDTO update(Long pedidoId, String codigoAcessoCliente, Long clienteId, String metodoPagamento, PedidoPostPutRequestDTO pedidoPostPutRequestDTO) {
        Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));
        Cliente clienteExistente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

        if (!codigoAcessoCliente.equals(clienteExistente.getCodigoAcesso())) {
            throw new InvalidAccessException("Codigo de acesso invalido!");
        }


        if (pedidoExistente.getStatusPagamento()) {
            throw new RuntimeException("O pedido já foi pago!");
        }

        metodoPagamento = metodoPagamento.replaceAll("(?i).*\\b(débito|crédito|PIX)\\b.*", "$1").toLowerCase();

        pagamentoStrategy.processarPagamento(metodoPagamento,Pedido pedidoExistente);

        pedidoExistente.setStatusPagamento(true);
        return modelMapper.map(pedidoRepository.save(pedidoExistente), PedidoResponseDTO.class);
    }
}