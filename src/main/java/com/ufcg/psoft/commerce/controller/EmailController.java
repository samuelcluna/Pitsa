package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.Email.EmailPostDTO;
import com.ufcg.psoft.commerce.service.Email.EmailV1CriarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/emails",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class EmailController {
    @Autowired
    EmailV1CriarService criarService;

    @PostMapping
    public ResponseEntity<?> enviarEmail(@RequestBody @Valid EmailPostDTO emailPostDTO){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(criarService.criar(emailPostDTO));
    }
}
