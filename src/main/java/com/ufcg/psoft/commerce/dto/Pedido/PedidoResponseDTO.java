package com.ufcg.psoft.commerce.dto.Pedido;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.Pizza;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty
    @Builder.Default
    private List<Pizza> pizzas = List.of();

    @JsonProperty("endereco")
    private String enderecoEntrega;

    @JsonProperty("estabelecimento")
    private Long estabelecimentoId;

    @JsonProperty("preco")
    private Double preco;

    @JsonProperty("cliente")
    private Long clienteId;

    @JsonProperty("status_pagamento")
    private boolean statusPagamento;

}
