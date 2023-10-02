package com.ufcg.psoft.commerce.dto.Pedido;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.Sabor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PizzaPostPutRequestDTO {

    @JsonProperty("tamanho")
    @NotBlank(message = "Tamanho obrigatorio")
    @Pattern(regexp = "media|grande", message = "Tamanho deve ser media ou grande")
    private String tamanho;

    @JsonProperty("sabor1")
    @NotNull(message = "Deve existir ao menos 1 sabor.")
    Sabor sabor1;

    @JsonProperty("sabor2")
    Sabor sabor2;

}