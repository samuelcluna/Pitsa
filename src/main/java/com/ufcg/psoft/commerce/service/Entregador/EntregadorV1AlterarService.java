package com.ufcg.psoft.commerce.service.Entregador;

import com.ufcg.psoft.commerce.dto.Entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Associacao;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.model.enums.DisponibilidadeEntregador;
import com.ufcg.psoft.commerce.model.enums.PedidoStatusEntregaEnum;
import com.ufcg.psoft.commerce.repository.AssociacaoRepository;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.service.Pedido.PedidoDefinirEntregadorService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class EntregadorV1AlterarService implements EntregadorAlterarService {

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    AssociacaoRepository associacaoRepository;

    @Autowired
    PedidoDefinirEntregadorService pedidoDefinirEntregadorService;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public Entregador update(Long id, EntregadorPostPutRequestDTO entregadorPostPutRequestDTO, String codigoAcesso) {

        Entregador entregador = entregadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("O entregador consultado nao existe!"));
        if (!entregador.getCodigoAcesso().equals(codigoAcesso)) {
            throw new InvalidAccessException(("Codigo de acesso invalido!"));
        }
        modelMapper.map(entregadorPostPutRequestDTO, entregador);
        return entregadorRepository.save(entregador);
    }

    @Override
    public EntregadorResponseDTO trocarDisponibilidade(Long id, String codigoAcesso) {
        Entregador entregador = entregadorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("O entregador consultado nao existe!"));
        if (!entregador.getCodigoAcesso().equals(codigoAcesso)) {
            throw new InvalidAccessException(("Codigo de acesso invalido!"));
        }

        this.trocarEstado(entregador);

        if(entregador.getDisponibilidade().equals(DisponibilidadeEntregador.ATIVO))
            this.pedidosEmEspera(entregador);

        entregadorRepository.flush();
        return new EntregadorResponseDTO(entregador);
    }

    public void trocarEstado(Entregador entregador){

        if(!entregador.getDisponibilidade().equals(DisponibilidadeEntregador.ATIVO)){

            entregador.setDisponibilidade(DisponibilidadeEntregador.ATIVO);
            entregador.setTempoDisponivel(LocalDateTime.now());

        }else{
            entregador.setDisponibilidade(DisponibilidadeEntregador.DESCANSO);
            entregador.setTempoDisponivel(null);
        }

        entregadorRepository.flush();
    }

    public void pedidosEmEspera(Entregador entregador){
        List<Pedido> pedidosEmEspera = new LinkedList<>(pedidoRepository.findAllByStatusEntregaOrderByDataAsc(PedidoStatusEntregaEnum.PEDIDO_PRONTO));

        if(!pedidosEmEspera.isEmpty()){
            for(Pedido pedido : pedidosEmEspera){
                Estabelecimento estabelecimento = estabelecimentoRepository.findById(pedido.getEstabelecimentoId()).orElse(null);
                Associacao associacao = associacaoRepository.findByEntregadorAndEstabelecimento(entregador,estabelecimento);
                if(associacao != null){
                    pedidoDefinirEntregadorService.definirEntregador(pedido.getEstabelecimentoId(), estabelecimento.getCodigoAcesso(), pedido.getId(), associacao.getId());
                    entregador.setDisponibilidade(DisponibilidadeEntregador.OCUPADO);
                    break;
                }
            }
        }
    }

}