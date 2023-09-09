package com.ufcg.psoft.commerce.service.Cliente;

import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
import com.ufcg.psoft.commerce.model.Cliente;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ClienteMostrarService {
    public ClienteResponseDTO lerCliente(Long id);

    public List<Cliente> lerClientes();
}
