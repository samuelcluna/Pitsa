package com.ufcg.psoft.commerce.service.Entregador;

@FunctionalInterface
public interface EntregadorDeletarService {
    void delete(Long id, String codigoAcesso);
}