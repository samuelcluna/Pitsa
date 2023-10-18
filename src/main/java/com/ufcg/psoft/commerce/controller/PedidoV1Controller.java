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
    PedidoAlterarService alterarService;
    @Autowired
    PedidoDeletarService deletarService;
    @Autowired
    PedidoCancelarService cancelarService;

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
                .body(alterarService.update(pedidoId, codigoAcesso, pedidoDTO));
    }

    @DeleteMapping("clientes/{clienteId}")
    public ResponseEntity<?> clienteDeletarTodosPedidos(
            @PathVariable Long clienteId,
            @RequestParam String clienteCodigoAcesso) {
        deletarService.clienteDeletarTodosPedidos(clienteId, clienteCodigoAcesso);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("clientes/{clienteId}/{pedidoId}")
    public ResponseEntity<?> clienteDeletarPedido(
            @PathVariable Long clienteId,
            @PathVariable Long pedidoId,
            @RequestParam String clienteCodigoAcesso) {
        deletarService.clienteDeletarPedido(pedidoId, clienteId, clienteCodigoAcesso);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
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

    @DeleteMapping("estabelecimentos/{estabelecimentoId}/{pedidoId}")
    public ResponseEntity<?> estabelecimentoDeletarPedido(
            @PathVariable Long estabelecimentoId,
            @PathVariable Long pedidoId,
            @RequestParam String estabelecimentoCodigoAcesso) {
        deletarService.estabelecimentoDeletarPedido(pedidoId, estabelecimentoId, estabelecimentoCodigoAcesso);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("estabelecimentos/{estabelecimentoId}")
    public ResponseEntity<?> estabelecimentoDeletarTodosPedidos(
            @PathVariable Long estabelecimentoId,
            @RequestParam String estabelecimentoCodigoAcesso) {
        deletarService.estabelecimentoDeletarTodosPedidos(estabelecimentoId, estabelecimentoCodigoAcesso);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("clientes/{clienteId}/confirmar-pagamento")
    public ResponseEntity<?> confirmarPagamento(
            @PathVariable Long clienteId,
            @RequestParam String codigoAcessoCliente,
            @RequestParam Long pedidoId,
            @RequestParam String metodoPagamento
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alterarService.confirmarPagamento(pedidoId, codigoAcessoCliente, clienteId, metodoPagamento));
    }

    @PutMapping("estabelecimentos/{estabelecimentoId}/{pedidoId}/preparando-pedido")
    public ResponseEntity<?> estabelecimentoPreparandoPedido(
            @PathVariable Long estabelecimentoId,
            @RequestParam String estabelecimentoCodigoAcesso,
            @RequestParam Long pedidoId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alterarService.definirPreparandoPedido(estabelecimentoId, estabelecimentoCodigoAcesso, pedidoId));
    }

    @PutMapping("estabelecimentos/{estabelecimentoId}/{pedidoId}/pedido-pronto")
    public ResponseEntity<?> estabelecimentoPedidoPronto(
            @PathVariable Long estabelecimentoId,
            @RequestParam String estabelecimentoCodigoAcesso,
            @RequestParam Long pedidoId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alterarService.definirPedidoPronto(estabelecimentoId, estabelecimentoCodigoAcesso, pedidoId));
    }

    @PutMapping("estabelecimentos/{estabelecimentoId}/{pedidoId}/associar-pedido-entregador")
    public ResponseEntity<?> estabelecimentoDefinirEntregador(
            @PathVariable Long estabelecimentoId,
            @RequestParam String estabelecimentoCodigoAcesso,
            @RequestParam Long pedidoId,
            @RequestParam Long associacaoId
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alterarService.definirEntregador(estabelecimentoId, estabelecimentoCodigoAcesso, pedidoId, associacaoId));
    }

    @DeleteMapping("/{pedidoId}/cancelar-pedido")
    public ResponseEntity<?> cancelarPedido(
            @PathVariable Long pedidoId,
            @RequestParam String clienteCodigoAcesso) {
        cancelarService.cancelar(pedidoId, clienteCodigoAcesso);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }
}
