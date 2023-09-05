package com.ufcg.psoft.commerce.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/example",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class ExampleController {

    @GetMapping("/text")
    public ResponseEntity<?> test() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("An example of GET endpoint");
    }

 }
