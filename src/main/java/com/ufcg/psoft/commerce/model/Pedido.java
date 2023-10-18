package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pedido")
public class Pedido {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty
    @Builder.Default
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "pizzas")
    private List<Pizza> pizzas = new ArrayList<>();

    @JsonProperty("endereco")
    private String enderecoEntrega;

    @JsonProperty("estabelecimento")
    private Long estabelecimentoId;

    @JsonProperty("cliente")
    private Long clienteId;

    @JsonProperty("preco")
    private Double preco;

    @JsonProperty("entregador")
    private Long entregadorId;

    @JsonProperty("statusEntrega")
    @Builder.Default
    private String statusEntrega = "Pedido recebido";

    @JsonProperty("status_pagamento")
    private Boolean statusPagamento;

}
