package com.ufcg.psoft.commerce.service.Estabelecimento;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EstabelecimentoMostrarCardapioService<T,E> {
    List<T> find(E estabelecimentoDTO, Long id);
    List<T> findByTipo(E estabelecimentoDTO, Long id, String tipo);
}
