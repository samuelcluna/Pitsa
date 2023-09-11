package com.ufcg.psoft.commerce.service.Entregador;

@FunctionalInterface
public interface EntregadorDeletarService {
    void deletar(Long id, String codigoAcesso);
}