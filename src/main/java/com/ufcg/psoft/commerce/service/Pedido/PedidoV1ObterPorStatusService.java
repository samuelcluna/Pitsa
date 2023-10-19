package com.ufcg.psoft.commerce.service.Pedido;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;
import java.util.Stack;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.service.Cliente.ClienteObterService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoV1ObterPorStatusService implements PedidoObterPorStatusService {

    @Autowired
    ClienteObterService clienteObterService;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    PedidoObterService pedidoObterService;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<PedidoResponseDTO> find(Long clienteId, Long estabelecimentoId, String codigoAcesso, String statusEntrega){
        clienteObterService.find(clienteId);
        List<Pedido> pedidosAux;
        if(estabelecimentoId == null && statusEntrega != null){
            pedidosAux = pedidoRepository.findAllByClienteIdAndStatusEntrega(clienteId, statusEntrega);
        } else if(estabelecimentoId != null && statusEntrega != null){
            pedidosAux = pedidoRepository.findAllByClienteIdAndEstabelecimentoIdAndStatusEntrega(clienteId,estabelecimentoId,statusEntrega);
        } else if (estabelecimentoId != null ){
            pedidosAux = pedidoRepository.findAllByClienteIdAndEstabelecimentoId(clienteId, estabelecimentoId);
        } else return pedidoObterService.estabelecimentoObterPedidos(clienteId, codigoAcesso);

        Stack<PedidoResponseDTO> pedidoStack = new Stack<>();
        for(Pedido pedido : pedidosAux){
            if(pedido.getStatusEntrega().equals("Pedido entregue"))
                pedidoStack.push(modelMapper.map(pedido, PedidoResponseDTO.class));
        }
        for(Pedido pedido : pedidosAux){
            if(!pedido.getStatusEntrega().equals("Pedido entregue"))
                pedidoStack.push(modelMapper.map(pedido, PedidoResponseDTO.class));
        }

        List<PedidoResponseDTO> pedidos = new ArrayList<>();
        while((long) pedidoStack.size() > 0)
            pedidos.add(pedidoStack.pop());

        return pedidos;
    }
}
