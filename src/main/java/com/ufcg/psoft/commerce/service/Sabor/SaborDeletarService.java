package com.ufcg.psoft.commerce.service.Sabor;

@FunctionalInterface
public interface SaborDeletarService {

    void delete(Long idSabor, Long idEstabelecimento, String codigoAcesso);

}
