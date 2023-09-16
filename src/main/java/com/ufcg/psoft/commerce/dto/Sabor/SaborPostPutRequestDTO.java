package com.ufcg.psoft.commerce.dto.Sabor;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Nome obrigatorio")
    String nome;

    @JsonProperty("tipo")
    @Pattern(regexp = "^(doce|salgado)$", message = "Tipo deve ser salgado ou doce")
    @NotBlank(message = "Tipo obrigatorio")
    String tipo;

    @JsonProperty("precoM")
    @Positive(message = "PrecoM deve ser maior que zero")
    @NotNull(message = "PrecoM obrigatorio")
    Double precoM;

    @JsonProperty("precoG")
    @Positive(message = "PrecoG deve ser maior que zero")
    @NotNull(message = "PrecoG obrigatorio")
    Double precoG;

    @JsonProperty("disponivel")
    @AssertTrue
    Boolean disponivel;

}