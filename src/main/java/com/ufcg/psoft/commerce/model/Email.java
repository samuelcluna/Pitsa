package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "email")
public class Email implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonProperty("receptor")
    private String receptor;
    @JsonProperty("assunto")
    private String assunto;
    @JsonProperty("text")
    @Column(columnDefinition = "TEXT")
    private String text;
    @JsonProperty("dataDeEnvio")
    private LocalDateTime dataDeEnvio;
    @JsonProperty("status")
    private boolean status;

}
