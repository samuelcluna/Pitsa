package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.Entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.service.Entregador.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/entregadores",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class EntregadorV1Controller {

    @Autowired
    EntregadorObterService entregadorObterService;
    @Autowired
    EntregadorCriarService entregadorCriarService;
    @Autowired
    EntregadorAlterarService entregadorAlterarService;
    @Autowired
    EntregadorDeletarService entregadorDeletarService;

    @PostMapping()
    public ResponseEntity<?> criarEntregador(
            @RequestBody @Valid EntregadorPostPutRequestDTO entregadorPostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(entregadorCriarService.criar(entregadorPostPutRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterUmEntregador(
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorObterService.obter(id));
    }

    @GetMapping("")
    public ResponseEntity<?> obterTodosEntregadores() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorObterService.obter(null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEntregador(
            @PathVariable Long id,
            @RequestParam String codigoAcesso,
            @RequestBody @Valid EntregadorPostPutRequestDTO entregadorPostPutRequestDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(entregadorAlterarService.alterar(id, entregadorPostPutRequestDto, codigoAcesso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarEntregador(
            @PathVariable Long id,
            @RequestParam String codigoAcesso
    ) {
        entregadorDeletarService.deletar(id, codigoAcesso);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body("");
    }
}