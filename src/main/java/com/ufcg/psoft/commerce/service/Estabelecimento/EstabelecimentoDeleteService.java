package com.ufcg.psoft.commerce.service.Estabelecimento;

import org.springframework.stereotype.Component;

@Component
public interface EstabelecimentoDeleteService {
    void delete(Long id, String codigoAcesso);
}
