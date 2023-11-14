package com.ufcg.psoft.commerce.dto.Pedido;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.Pizza;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoPostPutRequestDTO {

    @JsonProperty("endereco")
    private String enderecoEntrega;

    @JsonProperty
    @NotEmpty(message = "Pizzas obrigatorias")
    private List<Pizza> pizzas;

}
