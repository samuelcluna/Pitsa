package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.Cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.service.Cliente.ClienteV1AlterarService;
import com.ufcg.psoft.commerce.service.Cliente.ClienteV1CriarService;
import com.ufcg.psoft.commerce.service.Cliente.ClienteV1DeletarService;
import com.ufcg.psoft.commerce.service.Cliente.ClienteV1ObterService;
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
