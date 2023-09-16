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
public class AssociacaoV1AlterarService implements AssociacaoAlterarService{

    @Autowired
    AssociacaoRepository associacaoRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EntregadorRepository entregadorRepository;

    public AssociacaoResponseDTO update(Long entregadorId, Long estabelecimentoId, String codigoAcesso){
        Entregador entregador = entregadorRepository.findById(entregadorId).orElseThrow(
                ()-> new CommerceException("O entregador consultado nao existe!"));

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId).orElseThrow(
                ()-> new CommerceException("O estabelecimento consultado nao existe!"));

        if (!estabelecimento.getCodigoAcesso().equals(codigoAcesso)){
            throw new CommerceException("Codigo de acesso invalido!");
        }

        Associacao associacao = associacaoRepository.findByEntregadorAndEstabelecimento(entregador,estabelecimento);
        if (associacao == null){
            throw new CommerceException("Associaçao invalida!");
        }
        associacao.setStatus(true);
        return modelMapper.map(associacaoRepository.save(associacao), AssociacaoResponseDTO.class);
    }
}
