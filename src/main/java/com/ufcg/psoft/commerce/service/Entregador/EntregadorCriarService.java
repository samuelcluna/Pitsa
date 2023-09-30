package com.ufcg.psoft.commerce.service.Entregador;

import com.ufcg.psoft.commerce.dto.Entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Entregador;

@FunctionalInterface
public interface EntregadorCriarService {
    Entregador save(EntregadorPostPutRequestDTO entregadorPostPutRequestDTO);
}