package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.Pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.service.Pedido.*;
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
    @Autowired
    PedidoObterService obterService;
    @Autowired
    PedidoAlterarService pedidoAlterarService;
    @Autowired
    ClienteDeletarPedidoService clienteDeletarPedidoService;
    @Autowired
    EstabelecimentoDeletarPedidoService estabelecimentoDeletarPedidoService;

    @PostMapping
    public ResponseEntity<?> criarPedido(
            @RequestParam Long estabelecimentoId,
            @RequestParam Long clienteId,
            @RequestParam String clienteCodigoAcesso,
            @RequestBody @Valid PedidoPostPutRequestDTO pedidoDTO
            ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(criarService.save(pedidoDTO, estabelecimentoId, clienteId, clienteCodigoAcesso));
    }

    @GetMapping("clientes/{clienteId}")
    public ResponseEntity<?> clienteObterTodosPedidos(
            @PathVariable Long clienteId,
            @RequestParam String clienteCodigoAcesso) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(obterService.clienteObterPedidos(clienteId, clienteCodigoAcesso));
    }

    @GetMapping("clientes/{clienteId}/{pedidoId}")
    public ResponseEntity<?> clienteObterUmPedido(
            @PathVariable Long clienteId,
            @PathVariable Long pedidoId,
            @RequestParam String clienteCodigoAcesso) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(obterService.clienteObterPedido(pedidoId, clienteId, clienteCodigoAcesso));
    }

    @PutMapping
    public ResponseEntity<?> alterarPedido(
            @RequestParam Long pedidoId,
            @RequestParam String codigoAcesso,
            @RequestBody @Valid PedidoPostPutRequestDTO pedidoDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidoAlterarService.update(pedidoId, codigoAcesso, pedidoDTO));
    }

    @DeleteMapping
    public ResponseEntity<?> clienteDeletarTodosPedidos(
            @RequestParam Long clienteId) {
        clienteDeletarPedidoService.clienteDelete(null, clienteId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @DeleteMapping("{pedidoId}/{clienteId}")
    public ResponseEntity<?> clienteDeletarPedido(
            @PathVariable Long pedidoId,
            @PathVariable Long clienteId) {
        clienteDeletarPedidoService.clienteDelete(pedidoId, clienteId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @GetMapping("estabelecimentos/{estabelecimentoId}")
    public ResponseEntity<?> estabelecimentoObterTodosPedidos(
            @PathVariable Long estabelecimentoId,
            @RequestParam String estabelecimentoCodigoAcesso) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(obterService.estabelecimentoObterPedidos(estabelecimentoId, estabelecimentoCodigoAcesso));
    }

    @GetMapping("estabelecimentos/{estabelecimentoId}/{pedidoId}")
    public ResponseEntity<?> estabelecimentoObterUmPedido(
            @PathVariable Long estabelecimentoId,
            @PathVariable Long pedidoId,
            @RequestParam String estabelecimentoCodigoAcesso) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(obterService.estabelecimentoObterPedido(pedidoId, estabelecimentoId, estabelecimentoCodigoAcesso));
    }

    @DeleteMapping("{pedidoId}/{estabelecimentoId}/{estabelecimentoCodigoAcesso}")
    public ResponseEntity<?> estabelecimentoDeletarPedido(
            @PathVariable Long estabelecimentoId,
            @PathVariable Long pedidoId) {
        estabelecimentoDeletarPedidoService.estabelecimentoDelete(pedidoId, estabelecimentoId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @DeleteMapping("{estabelecimentoId}")
    public ResponseEntity<?> estabelecimentoDeletarTodosPedidos(
            @PathVariable Long estabelecimentoId) {
        estabelecimentoDeletarPedidoService.estabelecimentoDelete(null, estabelecimentoId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }
    @PutMapping("{clienteId}/confirmar-pagamento")
    public ResponseEntity<?> confirmarPagamento(
            @PathVariable Long clienteId,
            @RequestParam String codigoAcessoCliente,
            @RequestParam Long pedidoId,
            @RequestParam String metodoPagamento
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidoAlterarService.update(pedidoId, codigoAcessoCliente, clienteId, metodoPagamento));
    }
}
