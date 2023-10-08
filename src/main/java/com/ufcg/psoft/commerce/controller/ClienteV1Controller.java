package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.Cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.service.Cliente.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/clientes",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ClienteV1Controller {
    @Autowired
    ClienteV1CriarService criarService;
    @Autowired
    ClienteV1AlterarService alterarService;
    @Autowired
    ClienteV1DeletarService deletarService;
    @Autowired
    ClienteV1ObterService obterService;
    @Autowired
    ClienteDemonstrarInteresseService demonstrarInteresseService;

    @PostMapping()
    public ResponseEntity<?> criarCliente(
            @RequestBody @Valid ClientePostPutRequestDTO adicionar){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(criarService.save(adicionar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCliente(
            @PathVariable Long id,
            @RequestParam String codigoAcesso
            ) {
        deletarService.delete(id,codigoAcesso);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizaCliente(
            @RequestParam String codigoAcesso,
            @PathVariable Long id,
            @RequestBody @Valid ClientePostPutRequestDTO cliente) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alterarService.update(codigoAcesso, id, cliente));
    }
    @PutMapping("/{id}/demonstrarInteresse")
    public ResponseEntity<?> clienteDesmonstrarInteresseEmSabor(
            @RequestParam String codigoAcesso,
            @RequestParam Long saborId,
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(demonstrarInteresseService.update(codigoAcesso, id, saborId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> lerCliente(
            @PathVariable Long id)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(obterService.find(id));
    }

    @GetMapping()
    public ResponseEntity<?> lerClientes()
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(obterService.findAll());
    }

}
