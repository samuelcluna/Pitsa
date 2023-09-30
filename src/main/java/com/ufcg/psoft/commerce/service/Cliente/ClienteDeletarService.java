package com.ufcg.psoft.commerce.service.Cliente;
@FunctionalInterface
public interface ClienteDeletarService {
    void delete(Long id, String codigoAcesso);
}
