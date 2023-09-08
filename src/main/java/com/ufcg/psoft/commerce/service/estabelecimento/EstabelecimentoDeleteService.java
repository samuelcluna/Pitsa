package com.ufcg.psoft.commerce.service.estabelecimento;

import org.springframework.stereotype.Component;

@Component
public interface EstabelecimentoDeleteService {
    void delete(Long id, String codigoAcesso);
}
