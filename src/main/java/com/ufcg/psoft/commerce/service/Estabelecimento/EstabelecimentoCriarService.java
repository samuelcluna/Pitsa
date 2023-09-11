package com.ufcg.psoft.commerce.service.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoCriarService<T, E> {
    T save(E estabelecimentoDTO, String codigoDeAcesso);
}
