package com.ufcg.psoft.commerce.service.Sabor;

@FunctionalInterface
public interface SaborDeletarService {

    void deletar(Long idSabor, Long idEstabelecimento, String codigoAcesso);

}
