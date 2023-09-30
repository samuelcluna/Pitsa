package com.ufcg.psoft.commerce.service.Cliente;

import com.ufcg.psoft.commerce.dto.Cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
@FunctionalInterface
public interface ClienteCriarService {
    ClienteResponseDTO save(ClientePostPutRequestDTO cliente);
}
