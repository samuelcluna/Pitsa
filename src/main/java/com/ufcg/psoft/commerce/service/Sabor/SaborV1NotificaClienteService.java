package com.ufcg.psoft.commerce.service.Sabor;

import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.Email;
import com.ufcg.psoft.commerce.service.Cliente.ClienteObterService;
import com.ufcg.psoft.commerce.service.Email.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SaborV1NotificaClienteService implements SaborNotificaClienteService{

    @Autowired
    NotifyService notifyService;

    @Autowired
    ClienteObterService clienteObterService;

    @Override
    public List<Email> notifyClientes(Set<Long> clientesId, String message){
        List<Email> emailResponse = new ArrayList<>();
        ClienteResponseDTO cliente = new ClienteResponseDTO();
        for(Long clienteId : clientesId) {
            cliente = clienteObterService.find(clienteId);

            emailResponse.add(notifyService.notify())
        }
    }

}
