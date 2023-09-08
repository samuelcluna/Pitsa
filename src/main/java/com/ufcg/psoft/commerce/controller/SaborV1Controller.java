package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.Sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.service.Sabor.SaborCriarService;
import com.ufcg.psoft.commerce.service.Sabor.SaborDeletarService;
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

//    @Autowired
//    SaborAlterarService alterarService;

//    @Autowired
//    SaborObterService saborObterService;

    @PostMapping
    public ResponseEntity<Sabor> criarSabor(
            @RequestParam Long idEstabelecimento,
            @RequestParam String codigoAcesso,
            @RequestBody @Valid SaborPostPutRequestDTO saborPostPutRequestDTO
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(criarService.criar(saborPostPutRequestDTO, idEstabelecimento, codigoAcesso));
    }

    @DeleteMapping
    public ResponseEntity<?> deletarSabor(
            @RequestParam Long idSabor,
            @RequestParam Long idEstabelecimento,
            @RequestParam String codigoAcesso
    ) {
        deletarService.deletar(idSabor, idEstabelecimento, codigoAcesso);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
