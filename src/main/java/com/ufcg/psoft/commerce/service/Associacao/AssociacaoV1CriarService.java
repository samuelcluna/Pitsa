package com.ufcg.psoft.commerce.service.Associacao;

import com.ufcg.psoft.commerce.dto.Associacao.AssociacaoResponseDTO;
import com.ufcg.psoft.commerce.exception.CommerceException;
import com.ufcg.psoft.commerce.model.Associacao;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.repository.AssociacaoRepository;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssociacaoV1CriarService implements AssociacaoCriarService{

    @Autowired
    AssociacaoRepository associacaoRepository;

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    ModelMapper modelMapper;

    public AssociacaoResponseDTO save(Long entregadorId, Long estabelecimentoId, String codigoAcesso){
        Entregador entregador = entregadorRepository.findById(entregadorId).orElseThrow(
                ()-> new CommerceException("O entregador consultado nao existe!"));

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(
                ()-> new CommerceException("O estabelecimento consultado nao existe!"));

        if (!entregador.getCodigoAcesso().equals(codigoAcesso)){
            throw new CommerceException("Codigo de acesso invalido!");
        }

        Associacao associacao = Associacao.builder()
                .estabelecimento(estabelecimento)
                .entregador(entregador)
                .build();

        return modelMapper.map(associacaoRepository.save(associacao), AssociacaoResponseDTO.class);
    }
}
