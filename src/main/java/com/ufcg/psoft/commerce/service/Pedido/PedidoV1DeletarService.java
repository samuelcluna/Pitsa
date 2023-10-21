package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.exception.CommerceException;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.model.enums.PedidoStatusEntregaEnum;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoV1DeletarService implements PedidoDeletarService {

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public void clienteDeletarTodosPedidos(Long clienteId, String clienteCodigoAcesso) {
        Cliente clienteDeleta = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

        if (!clienteDeleta.getCodigoAcesso().equals(clienteCodigoAcesso)) throw new InvalidAccessException("Código de acesso inválido!");

        List<Pedido> todosPedidos = pedidoRepository.findAll();
        todosPedidos.stream()
                .filter(pedido -> pedido.getClienteId().equals(clienteId))
                .forEach(pedido -> {
                    pedidoRepository.deleteById(pedido.getId());
                });
    }

    @Override
    public void clienteDeletarPedido(Long pedidoId, Long clienteId, String clienteCodigoAcesso) {
        Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));
        Cliente clienteDeleta = clienteRepository.findById(pedidoExistente.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

        if (!clienteDeleta.getCodigoAcesso().equals(clienteCodigoAcesso)) throw new InvalidAccessException("Código de acesso inválido!");
        if (!pedidoExistente.getClienteId().equals(clienteDeleta.getId())) throw new ResourceNotFoundException("O pedido para esse cliente não foi encontrado!");

        pedidoRepository.deleteById(pedidoId);
    }

    @Override
    public void estabelecimentoDeletarTodosPedidos(Long estabelecimentoId, String estabelecimentoCodigoAcesso) {
        Estabelecimento estabelecimentoDeleta = estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));

        if (!estabelecimentoDeleta.getCodigoAcesso().equals(estabelecimentoCodigoAcesso)) throw new InvalidAccessException("Código de acesso inválido!");

        List<Pedido> todosPedidos = pedidoRepository.findAll();
        todosPedidos.stream()
                .filter(pedido -> pedido.getClienteId().equals(estabelecimentoId))
                .forEach(pedido -> {
                    pedidoRepository.deleteById(pedido.getId());
                });
    }

    @Override
    public void estabelecimentoDeletarPedido(Long pedidoId, Long estabelecimentoId, String estabelecimentoCodigoAcesso) {
        Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));
        Estabelecimento estabelecimentoDeleta = estabelecimentoRepository.findById(pedidoExistente.getEstabelecimentoId())
                .orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));

        if (!estabelecimentoDeleta.getCodigoAcesso().equals(estabelecimentoCodigoAcesso)) throw new InvalidAccessException("Código de acesso inválido!");
        if (!pedidoExistente.getEstabelecimentoId().equals(estabelecimentoDeleta.getId())) throw new ResourceNotFoundException("O pedido para esse estabelecimento não foi encontrado!");

        pedidoRepository.deleteById(pedidoId);
    }

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

            if ((pedido.getStatusEntrega().compareTo(PedidoStatusEntregaEnum.PEDIDO_PRONTO) <= 0)||
                    (pedido.getStatusEntrega().equals(PedidoStatusEntregaEnum.PEDIDO_ENTREGUE))) {
                throw new CommerceException("Pedidos que ja estao prontos nao podem ser cancelados!");
            } else {
                pedidoRepository.deleteById(pedidoId);
            }
        }
    }
}
