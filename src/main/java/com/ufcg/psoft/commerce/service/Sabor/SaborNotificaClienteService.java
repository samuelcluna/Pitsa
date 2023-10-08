package com.ufcg.psoft.commerce.service.Sabor;

import com.ufcg.psoft.commerce.model.Email;

import java.util.List;
import java.util.Set;

@FunctionalInterface
public interface SaborNotificaClienteService {
    List<Email> notifyClientes(Set<Long> clientesId, String message);
}
