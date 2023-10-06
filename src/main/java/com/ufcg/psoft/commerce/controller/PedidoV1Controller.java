package com.ufcg.psoft.commerce.controller;

import com.electronwill.nightconfig.core.conversion.Path;
import com.ufcg.psoft.commerce.dto.Pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.service.Pedido.ClienteObterPedidoService;
import com.ufcg.psoft.commerce.service.Pedido.PedidoAlterarService;
import com.ufcg.psoft.commerce.service.Pedido.PedidoCriarService;
import com.ufcg.psoft.commerce.service.Pedido.ClienteDeletarPedidoService;
import com.ufcg.psoft.commerce.service.Pedido.EstabelecimentoObterPedidoService;
import com.ufcg.psoft.commerce.service.Pedido.EstabelecimentoDeletarPedidoService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
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
    ClienteObterPedidoService clienteObterPedidoService;
    @Autowired
    PedidoAlterarService pedidoAlterarService;
    @Autowired
    ClienteDeletarPedidoService clienteDeletarPedidoService;
    @Autowired
    EstabelecimentoObterPedidoService estabelecimentoObterPedidoService;
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

    @GetMapping("")
    public ResponseEntity<?> clienteObterTodosPedidos(
            @RequestParam Long clienteId
            ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteObterPedidoService.clienteFind(null, clienteId));
    }

    @GetMapping("/{pedidoId}/{clienteId}")
    public ResponseEntity<?> clienteObterUmPedido(
            @PathVariable Long pedidoId,
            @PathVariable Long clienteId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(clienteObterPedidoService.clienteFind(pedidoId, clienteId));
    }

    @PutMapping()
    public ResponseEntity<?> alterarPedido(
            @RequestParam Long pedidoId,
            @RequestParam String codigoAcesso,
            @RequestBody @Valid PedidoPostPutRequestDTO pedidoDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidoAlterarService.update(pedidoId, codigoAcesso, pedidoDTO));
    }

    @DeleteMapping("")
    public ResponseEntity<?> clienteDeletarTodosPedidos(
            @RequestParam Long clienteId) {
        clienteDeletarPedidoService.clienteDelete(null, clienteId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @DeleteMapping("/{pedidoId}/{clienteId}")
    public ResponseEntity<?> clienteDeletarPedido(
            @PathVariable Long pedidoId,
            @PathVariable Long clienteId) {
        clienteDeletarPedidoService.clienteDelete(pedidoId, clienteId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @GetMapping("/{estabelecimentoId}")
    public ResponseEntity<?> estabelecimentoObterTodosPedidos(
            @PathVariable Long estabelecimentoId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoObterPedidoService.estabelecimentoFind(null, estabelecimentoId));
    }

    @GetMapping("/{pedidoId}/{estabelecimentoId}/{estabelecimentoCodigoAcesso}")
    public ResponseEntity<?> estabelecimentoObterUmPedido(
            @PathVariable Long pedidoId,
            @PathVariable Long estabelecimentoId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(estabelecimentoObterPedidoService.estabelecimentoFind(pedidoId, estabelecimentoId));
    }

    @DeleteMapping("/{pedidoId}/{estabelecimentoId}/{estabelecimentoCodigoAcesso}")
    public ResponseEntity<?> estabelecimentoDeletarPedido(
            @PathVariable Long estabelecimentoId,
            @PathVariable Long pedidoId) {
        estabelecimentoDeletarPedidoService.estabelecimentoDelete(pedidoId, estabelecimentoId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }

    @DeleteMapping("/{estabelecimentoId}")
    public ResponseEntity<?> estabelecimentoDeletarTodosPedidos(
            @PathVariable Long estabelecimentoId) {
        estabelecimentoDeletarPedidoService.estabelecimentoDelete(null, estabelecimentoId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }
    @PutMapping("/{clienteId}/confirmar-pagamento")
    public ResponseEntity<?> confirmarPagamento(
            @PathVariable Long clienteId,
            @RequestParam String codigoAcessoCliente,
            @RequestParam Long pedidoId,
            @RequestParam String metodoPagamento,
            @RequestBody @Valid PedidoPostPutRequestDTO pedidoPostPutRequestDTO
            ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pedidoAlterarService.update(pedidoId, codigoAcessoCliente, clienteId, metodoPagamento, pedidoPostPutRequestDTO));
    }
}
