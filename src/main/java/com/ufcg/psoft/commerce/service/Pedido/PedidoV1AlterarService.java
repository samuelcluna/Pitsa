package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.InvalidResourceException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.*;
import com.ufcg.psoft.commerce.repository.*;
import com.ufcg.psoft.commerce.service.Pedido.Pagamento.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PedidoV1AlterarService implements PedidoAlterarService {

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    AssociacaoRepository associacaoRepository;

    private final Map<String, PagamentoStrategy> pagamentoMap = Map.of(
      "crédito", new PagamentoCredito(),
      "débito", new PagamentoDebito(),
      "pix", new PagamentoPix()
    );

    @Override
    public PedidoResponseDTO update(Long pedidoId, String codigoAcesso, PedidoPostPutRequestDTO pedidoDTO) {
        Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));

        Cliente clienteExistente = clienteRepository.findById(pedidoExistente.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

        if (!clienteExistente.getCodigoAcesso().equals(codigoAcesso)) {
            throw new InvalidAccessException("Codigo de acesso invalido!");
        }

        modelMapper.map(pedidoDTO, pedidoExistente);
        return modelMapper.map(pedidoRepository.save(pedidoExistente), PedidoResponseDTO.class);
    }

    @Override
    public PedidoResponseDTO confirmarPagamento(Long pedidoId, String codigoAcessoCliente, Long clienteId, String metodoPagamento) {

        Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));
        Cliente clienteExistente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

        if (!codigoAcessoCliente.equals(clienteExistente.getCodigoAcesso())) {
            throw new InvalidAccessException("Codigo de acesso invalido!");
        }

        if (pedidoExistente.getStatusPagamento()) {
            throw new InvalidResourceException("O pedido já foi pago!");
        }

        String tipo = metodoPagamento.replaceAll("(?i).*\\b(débito|crédito|pix)\\b.*", "$1").toLowerCase();
        PagamentoStrategy pagamento = pagamentoMap.get(tipo);
        pagamento.pagar(pedidoExistente);
        pedidoRepository.flush();
        return modelMapper.map(pedidoExistente, PedidoResponseDTO.class);
    }

    @Override
    public PedidoResponseDTO definirPreparandoPedido(Long estabelecimentoId, String codidoAcessoEstabelecimento, Long pedidoId){

        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));
        Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));

        if(!estabelecimentoExistente.getCodigoAcesso().equals(codidoAcessoEstabelecimento))
            throw new InvalidAccessException("Codigo de acesso invalido!");

        if(!pedidoExistente.getStatusPagamento()){
            throw new InvalidAccessException("O pedido ainda não foi confirmado");
        }

        pedidoExistente.setStatusEntrega("Pedido em preparo");
        pedidoRepository.flush();
        return modelMapper.map(pedidoExistente, PedidoResponseDTO.class);
    }

    @Override
    public PedidoResponseDTO definirPedidoPronto(Long estabelecimentoId, String codidoAcessoEstabelecimento, Long pedidoId){

        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));
        Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));

        if(!estabelecimentoExistente.getCodigoAcesso().equals(codidoAcessoEstabelecimento))
            throw new InvalidAccessException("Codigo de acesso invalido!");

        if(!pedidoExistente.getStatusEntrega().equals("Pedido em preparo")){
            throw new InvalidAccessException("O pedido ainda não foi preparado");
        }

        pedidoExistente.setStatusEntrega("Pedido pronto");
        pedidoRepository.flush();
        return modelMapper.map(pedidoExistente, PedidoResponseDTO.class);
    }

    @Override
    public PedidoResponseDTO definirEntregador(Long estabelecimentoId, String codidoAcessoEstabelecimento, Long pedidoId, Long associacaoId){

        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(estabelecimentoId)
                .orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));
        Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));
        Associacao associacaoExistente = associacaoRepository.findById(associacaoId)
                .orElseThrow(() -> new ResourceNotFoundException("O associado consultado nao existe!"));

        if(!estabelecimentoExistente.getCodigoAcesso().equals(codidoAcessoEstabelecimento))
            throw new InvalidAccessException("Codigo de acesso invalido!");

        if(!associacaoExistente.getEstabelecimento().getId().equals(estabelecimentoId)){
            throw new InvalidAccessException("Estabelecimento diferente da associacao");
        }
        if(!associacaoExistente.getStatus()){
            throw new InvalidAccessException("O associado não está aprovado");
        }
        if(!pedidoExistente.getStatusEntrega().equals("Pedido pronto")){
            throw new InvalidAccessException("O pedido ainda não está pronto");
        }

        pedidoExistente.setStatusEntrega("Pedido em rota");
        pedidoExistente.setEntregadorId(associacaoExistente.getEntregador().getId());
        pedidoRepository.flush();
        return modelMapper.map(pedidoExistente, PedidoResponseDTO.class);
    }

    @Override
    public PedidoResponseDTO confirmarEntrega(Long pedidoId, String codigoAcessoCliente, Long clienteId) {
        Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));

        Cliente clienteExistente = clienteRepository.findById(pedidoExistente.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

        if (!clienteExistente.getCodigoAcesso().equals(codigoAcessoCliente)) {
            throw new InvalidAccessException("Codigo de acesso invalido!");
        }

        if (!pedidoExistente.getStatusEntrega().equals("Pedido em rota")) {
            throw new InvalidResourceException("Pedido com status de entrega inválido.");
        }

        pedidoExistente.setStatusEntrega("Pedido entregue");
        pedidoRepository.flush();
        return modelMapper.map(pedidoExistente, PedidoResponseDTO.class);
    }
}