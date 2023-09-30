package com.ufcg.psoft.commerce.service.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoAlterarService<T,E> {
    T update(E estabelecimentoDTO,String codigoAcesso, Long id);
}
