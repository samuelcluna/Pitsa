package com.ufcg.psoft.commerce.service.estabelecimento;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface EstabelecimentoMostrarCardapioService<T,E> {
    Set<T> find(E estabelecimentoDTO, Long id);
}
