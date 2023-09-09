package com.ufcg.psoft.commerce.service.Cliente;
@FunctionalInterface
public interface ClienteDeleteService {
    void delete(Long id, String codigoAcesso);
}
