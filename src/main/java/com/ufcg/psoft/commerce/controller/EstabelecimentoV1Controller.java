package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.service.Estabelecimento.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/estabelecimentos",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class EstabelecimentoV1Controller {

    @Autowired
    EstabelecimentoV1CriarService criarService;
    @Autowired
    EstabelecimentoV1DeleteService deleteService;
    @Autowired
    EstabelecimentoV1AlterarService alterarService;
    @Autowired
    EstabelecimentoV1MostrarCadarpioService cardapioService;

    @PostMapping()
    public ResponseEntity<?> criarEstabelecimento(
            @RequestParam String codigoAcesso,
            @RequestBody @Valid EstabelecimentoPostPutRequestDTO estabelecimentoPostPutDTO) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(criarService.save(estabelecimentoPostPutDTO, codigoAcesso));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEstabelecimento(
            @RequestParam String codigoAcesso,
            @PathVariable Long id) {

        deleteService.delete(id,codigoAcesso);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizaEstabelecimento(
            @RequestParam String codigoAcesso,
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoPostPutRequestDTO estabelecimentoPostPutDTO) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alterarService.update(estabelecimentoPostPutDTO, codigoAcesso, id));
    }
    @GetMapping("/{id}/sabores")
    public ResponseEntity<?> cardapioEstabelecimento(
            @PathVariable Long id,
            @RequestBody @Valid EstabelecimentoPostPutRequestDTO estabelecimentoPostDTO)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardapioService.find(estabelecimentoPostDTO, id));
    }
    @GetMapping("/{id}/sabores/tipo")
    public ResponseEntity<?> cardapioTipoEstabelecimento(
            @PathVariable Long id,
            @RequestParam String tipo,
            @RequestBody @Valid EstabelecimentoPostPutRequestDTO estabelecimentoPostDTO)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardapioService.findByTipo(estabelecimentoPostDTO, id, tipo));
    }
}
