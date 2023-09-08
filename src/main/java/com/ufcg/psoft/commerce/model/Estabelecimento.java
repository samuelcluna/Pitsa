package com.ufcg.psoft.commerce.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Size;

import java.util.Set;


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
    @Size(min = 6, max = 6, message = "O código de acesso deve ter exatamente 6 caracteres.")
    private String codigoAcesso;

    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Sabor> cardapio;

}
