package com.ufcg.psoft.commerce.service.Sabor;

import com.ufcg.psoft.commerce.dto.Sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.InvalidAccessException;
import com.ufcg.psoft.commerce.exception.ResourceNotFoundException;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.Sabor.SaborRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaborV1CriarService implements SaborCriarService {

    @Autowired
    SaborRepository saborRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    @Transactional
    public Sabor criar(SaborPostPutRequestDTO requestDTO, Long idEstabelecimento, String codigoAcesso) {
        Estabelecimento estabelecimentoExistente = estabelecimentoRepository.findById(idEstabelecimento)
                .orElseThrow(() -> new ResourceNotFoundException("Estabelecimento não existe."));
        if (!estabelecimentoExistente.getCodigoAcesso().equals(codigoAcesso)) {
            throw new InvalidAccessException("Codigo de acesso invalido!"); // Bad Request
        }
        Sabor saborSalvar = Sabor.builder().build();
        modelMapper.map(requestDTO, saborSalvar);
        saborSalvar.setEstabelecimento(estabelecimentoExistente);
        return saborRepository.save(saborSalvar);
    }
}
