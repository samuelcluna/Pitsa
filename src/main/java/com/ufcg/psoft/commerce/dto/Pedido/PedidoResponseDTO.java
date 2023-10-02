package com.ufcg.psoft.commerce.dto.Pedido;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.Pizza;

import java.util.List;

public class PedidoResponseDTO {

    @JsonProperty("id")
    Long id;

    @JsonProperty("endereco")
    String enderecoEntrega;

    @JsonProperty("pizzas")
    List<Pizza> pizzas;

    @JsonProperty("estabelecimento")
    Long estabelecimentoId;

    @JsonProperty("preco")
    double preco;

}
