package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @JsonProperty("pizzas")
    List<Pizza> pizzas;

    @JsonProperty("endereco")
    String enderecoEntrega;

    @JsonProperty("estabelecimento")
    Long estabelecimentoId;

    @JsonProperty("cliente")
    Long clienteId;

    @JsonProperty("preco")
    double preco;

}
