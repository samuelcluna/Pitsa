package com.ufcg.psoft.commerce.service.estabelecimento;

import org.springframework.stereotype.Component;

@FunctionalInterface
public interface EstabelecimentoCriarService<T, E> {
    T save(E estabelecimentoDTO, String codigoDeAcesso);
}
