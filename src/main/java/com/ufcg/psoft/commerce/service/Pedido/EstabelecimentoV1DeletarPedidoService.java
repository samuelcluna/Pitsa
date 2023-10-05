package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1DeletarPedidoService implements EstabelecimentoDeletarPedidoService {

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    public void estabelecimentoDelete(Long pedidoId, Long estabelecimentoDeletaId) {
        if (pedidoId != null && pedidoId > 0) {
            Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                    .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));
            Estabelecimento pedidoEstabelecimento = estabelecimentoRepository.findById(pedidoExistente.getEstabelecimentoId())
                    .orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));
            Estabelecimento estabelecimentoDeleta = estabelecimentoRepository.findById(estabelecimentoDeletaId)
                    .orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));

            if (!pedidoEstabelecimento.getCodigoAcesso().equals(estabelecimentoDeleta.getCodigoAcesso())) {
                throw new InvalidAccessException("Codigo de acesso invalido!");
            }
        }

        if (pedidoId == null) {
            pedidoRepository.findAll().forEach(pedido -> {
                if (pedido.getEstabelecimentoId().equals(estabelecimentoDeletaId)) {
                    pedidoRepository.deleteById(pedido.getId());
                }
            });
        } else {
            pedidoRepository.deleteById(pedidoId);
        }
    }
}