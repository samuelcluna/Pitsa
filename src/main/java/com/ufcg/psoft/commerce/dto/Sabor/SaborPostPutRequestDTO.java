package com.ufcg.psoft.commerce.dto.Sabor;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
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
    Double precoM;

    @JsonProperty("precoG")
    @Positive
    Double precoG;

    @JsonProperty("disponivel")
    @AssertTrue
    Boolean disponivel;

}
