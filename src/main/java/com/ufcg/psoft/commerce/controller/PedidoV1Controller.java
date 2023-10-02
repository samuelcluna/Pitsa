package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.service.Pedido.PedidoCriarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/pedidos",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PedidoV1Controller {


    @Autowired
    PedidoCriarService criarService;


    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(
            @RequestParam Long estabelecimentoId,
            @RequestParam Long clienteId,
            @RequestParam String codigoAcessoCliente,
            @RequestBody @Valid PedidoPostPutRequestDTO pedidoDTO
            ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(criarService.save(pedidoDTO, estabelecimentoId, clienteId, codigoAcessoCliente));
    }

}
