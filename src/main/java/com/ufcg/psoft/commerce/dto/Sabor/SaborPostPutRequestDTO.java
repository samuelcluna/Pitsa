package com.ufcg.psoft.commerce.dto.Sabor;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaborPostPutRequestDTO {

    @JsonProperty("nome")
    @NotBlank
    String nome;

    @JsonProperty("tipo")
    @NotBlank
    String tipo;

    @JsonProperty("precoM")
    @Positive
    double precoM;

    @JsonProperty("precoG")
    @Positive
    double precoG;

    @JsonProperty("disponivel")
    @NotNull
    Boolean disponivel;

}
