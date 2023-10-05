package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstabelecimentoV1ObterPedidoService implements EstabelecimentoObterPedidoService {

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public List<Pedido> estabelecimentoFind(Long pedidoId, Long estabelecimentoObtemId) {

        if (pedidoId != null && pedidoId > 0) {
            Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                    .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));
            Estabelecimento pedidoEstabelecimento = estabelecimentoRepository.findById(pedidoExistente.getEstabelecimentoId())
                    .orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));
            Estabelecimento estabelecimentoObtem = estabelecimentoRepository.findById(estabelecimentoObtemId)
                    .orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));

            if (!pedidoEstabelecimento.getCodigoAcesso().equals(estabelecimentoObtem.getCodigoAcesso())) {
                throw new InvalidAccessException("Codigo de acesso invalido!");
            }
        }

        if (pedidoId == null) {
            List<Pedido> pedidos = new ArrayList<>();
            pedidoRepository.findAll().forEach(pedido -> {
                if (pedido.getEstabelecimentoId().equals(estabelecimentoObtemId)) {
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