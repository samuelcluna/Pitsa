package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.events.EventoPedidoEmRota;
import com.ufcg.psoft.commerce.events.EventoPedidoEntregue;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.InvalidResourceException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.*;
import com.ufcg.psoft.commerce.model.enums.DisponibilidadeEntregador;
import com.ufcg.psoft.commerce.model.enums.PedidoStatusEntregaEnum;
import com.ufcg.psoft.commerce.repository.*;
import com.ufcg.psoft.commerce.service.Entregador.EntregadorV1AlterarService;
import com.ufcg.psoft.commerce.service.Pedido.Pagamento.*;
import io.swagger.v3.oas.models.links.Link;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class PedidoV1AlterarService implements PedidoAlterarService {

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    AssociacaoRepository associacaoRepository;
    @Autowired
    ApplicationEventPublisher publisher;
    @Autowired
    EntregadorV1AlterarService entregadorAlterarService;
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
            throw new InvalidResourceException("Codigo de acesso invalido!");

        if(!pedidoExistente.getStatusPagamento()){
            throw new InvalidResourceException("O pedido ainda não foi confirmado");
        }

        pedidoExistente.setStatusEntrega(PedidoStatusEntregaEnum.PEDIDO_EM_PREPARO);
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
            throw new InvalidResourceException("Codigo de acesso invalido!");


        if(!pedidoExistente.getStatusEntrega().equals(PedidoStatusEntregaEnum.PEDIDO_EM_PREPARO)){
            throw new InvalidAccessException("O pedido ainda não foi preparado");
        }

        pedidoExistente.setStatusEntrega(PedidoStatusEntregaEnum.PEDIDO_PRONTO);
        pedidoRepository.flush();

        List<Entregador> entregadoresDisponiveis = new LinkedList<>(entregadorRepository.findAllByDisponibilidadeOrderByTempoDisponivelAsc(DisponibilidadeEntregador.ATIVO));

        if(!entregadoresDisponiveis.isEmpty()){
            for (Entregador entregador : entregadoresDisponiveis) {
                Associacao associacao = associacaoRepository.findByEntregadorAndEstabelecimento(entregador, estabelecimentoExistente);
                if (associacao != null) {
                    this.definirEntregador(estabelecimentoExistente.getId(), codidoAcessoEstabelecimento, pedidoId, associacao.getId());
                    break;
                }
            }
        }

        if(pedidoExistente.getStatusEntrega().equals(PedidoStatusEntregaEnum.PEDIDO_PRONTO)){
            System.out.println("Nenhum entregador disponivel");
        }

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
        Cliente clienteExistente = clienteRepository.findById(pedidoExistente.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("O cliente Consultado nao existente"));

        if(!estabelecimentoExistente.getCodigoAcesso().equals(codidoAcessoEstabelecimento))
            throw new InvalidAccessException("Codigo de acesso invalido!");

        if(!associacaoExistente.getEstabelecimento().getId().equals(estabelecimentoId)){
            throw new InvalidAccessException("Estabelecimento diferente da associacao");
        }
        if(!associacaoExistente.getStatus()){
            throw new InvalidResourceException("O associado não está aprovado");
        }
        if(!pedidoExistente.getStatusEntrega().equals(PedidoStatusEntregaEnum.PEDIDO_PRONTO)){
            throw new InvalidAccessException("O pedido ainda não está pronto");
        }

        pedidoExistente.setStatusEntrega(PedidoStatusEntregaEnum.PEDIDO_EM_ROTA);
        pedidoExistente.setEntregadorId(associacaoExistente.getEntregador().getId());
        pedidoRepository.flush();
        EventoPedidoEmRota evento = EventoPedidoEmRota.builder()
                .pedido(pedidoExistente)
                .cliente(clienteExistente)
                .entregador(associacaoExistente.getEntregador())
                .build();

        publisher.publishEvent(evento);

        return modelMapper.map(pedidoExistente, PedidoResponseDTO.class);
    }

    @Override
    public PedidoResponseDTO confirmarEntrega(Long pedidoId, String codigoAcessoCliente, Long clienteId) {
        Pedido pedidoExistente = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ResourceNotFoundException("O pedido consultado nao existe!"));

        Cliente clienteExistente = clienteRepository.findById(pedidoExistente.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("O cliente consultado nao existe!"));

        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(pedidoExistente.getEstabelecimentoId())
                .orElseThrow(() -> new ResourceNotFoundException("O estabelecimento consultado nao existe!"));

        if (!clienteExistente.getCodigoAcesso().equals(codigoAcessoCliente)) {
            throw new InvalidAccessException("Codigo de acesso invalido!");
        }

        if (!pedidoExistente.getStatusEntrega().equals(PedidoStatusEntregaEnum.PEDIDO_EM_ROTA)) {
            throw new InvalidResourceException("Pedido com status de entrega inválido.");
        }

        pedidoExistente.setStatusEntrega(PedidoStatusEntregaEnum.PEDIDO_ENTREGUE);
        pedidoRepository.flush();

        Entregador entregador = entregadorRepository.findById(pedidoExistente.getEntregadorId()).orElse(null);
        entregadorAlterarService.trocarDisponibilidade(entregador.getId(), entregador.getCodigoAcesso());

        EventoPedidoEntregue evento = EventoPedidoEntregue.builder()
                .estabelecimento(estabelecimentoExistente)
                .pedido(pedidoExistente)
                .cliente(clienteExistente)
                .build();
        publisher.publishEvent(evento);

        return modelMapper.map(pedidoExistente, PedidoResponseDTO.class);
    }
}