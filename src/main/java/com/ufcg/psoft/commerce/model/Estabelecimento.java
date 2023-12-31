package com.ufcg.psoft.commerce.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.enums.DisponibilidadeEntregador;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Size;

import java.util.LinkedList;
import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "estabelecimentos")
public class Estabelecimento {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("codigoAcesso")
    @Size(min = 6, max = 6, message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    private String codigoAcesso;

    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Sabor> sabores;

    @JsonProperty("email")
    private String email;

    @JsonIgnore
    @ManyToMany
    private List<Entregador> entregadoresDisponiveis;

    @PrePersist
    private void setDefaultDisponibilidade() {
        if (entregadoresDisponiveis == null) {
            entregadoresDisponiveis = new LinkedList<>();
        }
    }
}
