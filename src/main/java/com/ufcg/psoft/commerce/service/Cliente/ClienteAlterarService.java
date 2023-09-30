package com.ufcg.psoft.commerce.service.Cliente;

import com.ufcg.psoft.commerce.dto.Cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
@FunctionalInterface
public interface ClienteAlterarService {
    ClienteResponseDTO update(String codigoAcesso, Long id, ClientePostPutRequestDTO cliente);
}
