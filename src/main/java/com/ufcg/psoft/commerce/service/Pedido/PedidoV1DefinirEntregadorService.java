package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.events.EventoPedidoEmRota;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.InvalidResourceException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Associacao;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.model.enums.PedidoStatusEntregaEnum;
import com.ufcg.psoft.commerce.repository.AssociacaoRepository;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1DefinirEntregadorService implements PedidoDefinirEntregadorService {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    AssociacaoRepository associacaoRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ApplicationEventPublisher publisher;

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
}
