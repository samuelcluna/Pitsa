package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.*;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.repository.Sabor.SaborRepository;
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
    public Pedido save(PedidoPostPutRequestDTO pedidoDTO, Long estabelecimentoId, Long clienteId, String clienteCodigoAcesso) {
        estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));
        Cliente clienteExistente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

        if (!clienteExistente.getCodigoAcesso().equals(clienteCodigoAcesso)) {
            throw new InvalidAccessException("Codigo de acesso invalido!");
        }

        if (pedidoDTO.getEnderecoEntrega().isEmpty()) {
            pedidoDTO.setEnderecoEntrega(clienteExistente.getEndereco());
        }

        Pedido pedidoExistente = modelMapper.map(pedidoDTO, Pedido.class);

        Double total = 0.0;
        for (Pizza pizza : pedidoDTO.getPizzas()) {
            saborRepository.findById(pizza.getSabor1().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("O sabor consultado nao existe!"));
            if (pizza.getSabor2() != null) {
                if (pizza.getTamanho().equals("media")) {
                    throw new ResourceNotFoundException("Pizzas medias so podem ter 1 sabor!");
                }
                saborRepository.findById(pizza.getSabor2().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("O sabor consultado nao existe!"));
                if (!pizza.getSabor2().isDisponivel()) {
                    throw new ResourceNotFoundException("O sabor consultado nao esta disponivel!");
                }
            }

            if (!pizza.getSabor1().isDisponivel()) {
                throw new ResourceNotFoundException("O sabor consultado nao esta disponivel!");
            }

            if (pizza.getTamanho().equals("grande") && pizza.getSabor2() == null) {
                total += pizza.getSabor1().getPrecoG();
            } else if (pizza.getTamanho().equals("media")) {
                total += pizza.getSabor1().getPrecoM();
            } else {
                total += (pizza.getSabor1().getPrecoG() + pizza.getSabor2().getPrecoG()) / 2;
            }
        }

        pedidoExistente.setPreco(total);
        pedidoExistente.setPizzas(pedidoDTO.getPizzas());
        pedidoExistente.setClienteId(clienteId);
        pedidoExistente.setEstabelecimentoId(estabelecimentoId);
        return pedidoRepository.save(pedidoExistente);
    }
}
