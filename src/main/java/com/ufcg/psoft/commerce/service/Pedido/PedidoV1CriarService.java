package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.*;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1CriarService implements PedidoCriarService {

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public PedidoResponseDTO save(PedidoPostPutRequestDTO pedidoDTO, Long estabelecimentoId, Long clienteId, String codigoAcessoCliente) {
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não encontrado!"));
        Cliente clienteExistente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado!"));
        if (pedidoDTO.getEnderecoEntrega().isEmpty()) {
            pedidoDTO.setEnderecoEntrega(clienteExistente.getEndereco());
        }

        Pedido pedidoSalvar = modelMapper.map(pedidoDTO, Pedido.class);
        pedidoSalvar.setClienteId(clienteId);
        pedidoSalvar.setEstabelecimentoId(estabelecimentoId);
        return modelMapper.map(pedidoRepository.save(pedidoSalvar), PedidoResponseDTO.class);
    }

    public double calcularTotal(Pedido pedido) {
        double total = 0;
        for (Pizza pizza: pedido.getPizzas()) {
            total += pizza.getPreco();
        }
        return total;
    }

}
