package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.service.Associacao.AssociacaoV1AlterarService;
import com.ufcg.psoft.commerce.service.Associacao.AssociacaoV1CriarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/associacao",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AssociacaoV1Controller {

    @Autowired
    private AssociacaoV1CriarService criarService;

    @Autowired
    private AssociacaoV1AlterarService alterarAssociacao;

    @PostMapping
    public ResponseEntity<?> criarAssociacao(
            @Valid @RequestParam Long entregadorId,
            @RequestParam String codigoAcesso,
            @RequestParam Long estabelecimentoId
            ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(criarService.criar(entregadorId, estabelecimentoId, codigoAcesso));
    }

    @PutMapping
    public ResponseEntity<?> alterarAssociacao(
            @Valid @RequestParam Long entregadorId,
            @RequestParam String codigoAcesso,
            @RequestParam Long estabelecimentoId
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alterarAssociacao.alterar(entregadorId, estabelecimentoId, codigoAcesso));
    }
}
