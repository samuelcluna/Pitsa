package com.ufcg.psoft.commerce.service.Sabor;

import com.ufcg.psoft.commerce.dto.Sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Sabor;

@FunctionalInterface
public interface SaborAlterarService {

    Sabor alterar(Long idSabor, Long idEstabelecimento, String codigoAcesso, SaborPostPutRequestDTO requestDTO);

}
