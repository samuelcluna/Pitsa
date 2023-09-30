package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.Sabor.SaborPatchRequestDTO;
import com.ufcg.psoft.commerce.dto.Sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.service.Sabor.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/sabores",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class SaborV1Controller {

    @Autowired
    SaborCriarService criarService;

    @Autowired
    SaborDeletarService deletarService;

    @Autowired
    SaborAlterarService alterarService;

    @Autowired
    SaborObterService saborObterService;

    @Autowired
    SaborAlterarDisponibilidadeService alterarDisponibilidadeService;

    @PostMapping
    public ResponseEntity<?> criarSabor(
            @RequestParam Long estabelecimentoId,
            @RequestParam String estabelecimentoCodigoAcesso,
            @RequestBody @Valid SaborPostPutRequestDTO saborPostPutRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(criarService.save(saborPostPutRequestDTO, estabelecimentoId, estabelecimentoCodigoAcesso));
    }

    @DeleteMapping
    public ResponseEntity<?> deletarSabor(
            @RequestParam Long saborId,
            @RequestParam Long estabelecimentoId,
            @RequestParam String estabelecimentoCodigoAcesso
    ) {
        deletarService.delete(saborId, estabelecimentoId, estabelecimentoCodigoAcesso);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping
    public ResponseEntity<?> alterarSabor(
            @RequestParam Long saborId,
            @RequestParam Long estabelecimentoId,
            @RequestParam String estabelecimentoCodigoAcesso,
            @RequestBody @Valid SaborPostPutRequestDTO saborPostPutRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alterarService.update(saborId, estabelecimentoId, estabelecimentoCodigoAcesso, saborPostPutRequestDTO));
    }

    @PatchMapping("/disponibilidade")
    public ResponseEntity<?> alterarDisponibilidade(
            @RequestParam Long saborId,
            @RequestParam Long estabelecimentoId,
            @RequestParam String estabelecimentoCodigoAcesso,
            @RequestBody @Valid SaborPatchRequestDTO saborPatchRequestDTO
            ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alterarDisponibilidadeService.update(saborId, estabelecimentoId, estabelecimentoCodigoAcesso, saborPatchRequestDTO));
    }
    @GetMapping
    public ResponseEntity<?> obterSabores(
            @RequestParam Long estabelecimentoId,
            @RequestParam String estabelecimentoCodigoAcesso
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(saborObterService.findAll(estabelecimentoId, estabelecimentoCodigoAcesso));
    }

    @GetMapping("{saborId}")
    public ResponseEntity<?> obterSabores(
            @PathVariable Long saborId,
            @RequestParam Long estabelecimentoId,
            @RequestParam String estabelecimentoCodigoAcesso
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(saborObterService.find(saborId, estabelecimentoId, estabelecimentoCodigoAcesso));
    }

}
