package com.ufcg.psoft.commerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.dto.Cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Email.EmailPostDTO;
import com.ufcg.psoft.commerce.exception.CommerceException;
import com.ufcg.psoft.commerce.exception.CustomErrorType;
import com.ufcg.psoft.commerce.model.*;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.EmailRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Emails")
public class EmailControllerTests {
    final String URI_EMAILS = "/emails";

    @Autowired
    MockMvc driver;

    @Autowired
    EmailRepository emailRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Email email;

    EmailPostDTO emailPostDTO;

    String emailTeste = "emailcontrollertest@gmail.com"; // COLOCAR EMAIL VÁLIDO

    @BeforeEach
    void setup() {
        // Object Mapper suporte para LocalDateTime
        objectMapper.registerModule(new JavaTimeModule());
        email = emailRepository.save(Email.builder()
                .receptor(emailTeste)
                .assunto("setup")
                .dataDeEnvio(LocalDateTime.now())
                .text("teste do setup")
                .status(true)
                .build()
        );
    }

    @AfterEach
    void tearDown() {
        emailRepository.deleteAll();
    }

    @DisplayName("testes com dados válidos")
    @Test
    public void quandoOsDadosSaoValidos() throws Exception{
        EmailPostDTO emailPostDTO = EmailPostDTO.builder()
                .assunto("testes dados validos")
                .receptor(emailTeste)
                .text("testes com dados validos")
                .build();
        String responseJsonString = driver.perform(post(URI_EMAILS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emailPostDTO)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        Email resultado = objectMapper.readValue(responseJsonString, Email.class);

        // Assert
        assertAll(
                () -> assertEquals(2, emailRepository.count()),
                () -> assertNotNull(resultado.getId()),
                () -> assertNotNull(resultado.getAssunto()),
                () -> assertEquals(emailPostDTO.getAssunto(), resultado.getAssunto()),
                () -> assertNotNull(resultado.getText()),
                () -> assertEquals(emailPostDTO.getText(), resultado.getText()),
                () -> assertNotNull(resultado.getReceptor()),
                () -> assertEquals(emailPostDTO.getReceptor(), resultado.getReceptor())
        );
    }

    @DisplayName("testes com dados inválidos")
    @Test
    public void quandoOsDadosSaoInvalidos() throws Exception{
        EmailPostDTO emailPostDTO = EmailPostDTO.builder()
                .assunto("")
                .receptor(emailTeste)
                .text("testes com dados invalidos")
                .build();
        String responseJsonString = driver.perform(post(URI_EMAILS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emailPostDTO)))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

        // Assert
        assertAll(
                () -> assertNotNull(resultado.getMessage()),
                () -> assertEquals(resultado.getMessage(), "Erros de validacao encontrados")
        );
    }

}
