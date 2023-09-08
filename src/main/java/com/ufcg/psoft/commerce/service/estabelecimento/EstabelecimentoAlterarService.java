package com.ufcg.psoft.commerce.service.estabelecimento;

@FunctionalInterface
public interface EstabelecimentoAlterarService<T,E> {
    T update(E estabelecimentoDTO,String codigoAcesso, Long id);
}
