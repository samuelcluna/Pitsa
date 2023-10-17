package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.*;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.repository.SaborRepository;
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
    SaborRepository saborRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public PedidoResponseDTO save(PedidoPostPutRequestDTO pedidoDTO, Long estabelecimentoId, Long clienteId, String clienteCodigoAcesso) {
        estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));
        Cliente clienteExistente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

        if (!clienteExistente.getCodigoAcesso().equals(clienteCodigoAcesso)) {
            throw new InvalidAccessException("Código de acesso inválido!");
        }

        if (pedidoDTO.getEnderecoEntrega() == null || pedidoDTO.getEnderecoEntrega().isEmpty()) {
            pedidoDTO.setEnderecoEntrega(clienteExistente.getEndereco());
        }

        Pedido pedidoExistente = modelMapper.map(pedidoDTO, Pedido.class);

        double total = calcularPreco(pedidoExistente);

        pedidoExistente.setPreco(total);
        pedidoExistente.setPizzas(pedidoDTO.getPizzas());
        pedidoExistente.setClienteId(clienteId);
        pedidoExistente.setEstabelecimentoId(estabelecimentoId);
        pedidoExistente.setStatusPagamento(false);
        pedidoExistente.setStatusEntrega("Pedido recebido");
        return modelMapper.map(pedidoRepository.save(pedidoExistente), PedidoResponseDTO.class);
    }

    private Double calcularPreco(Pedido pedido) {
        double total = 0.0;
        for (Pizza pizza : pedido.getPizzas()) {
            total += pizza.calcularPreco();
        }
        return total;
    }
}
