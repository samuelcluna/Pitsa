package com.ufcg.psoft.commerce.service.Entregador;

import com.ufcg.psoft.commerce.dto.Entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.model.Entregador;
import org.springframework.stereotype.Component;

@Component
public interface EntregadorAlterarService {
    Entregador update(Long id, EntregadorPostPutRequestDTO entregadorPostPutRequestDTO, String codigoAcesso);

    EntregadorResponseDTO trocarDisponibilidade(Long id, String codigoAcesso);
}