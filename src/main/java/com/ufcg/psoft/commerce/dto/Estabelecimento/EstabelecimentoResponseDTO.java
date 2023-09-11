package com.ufcg.psoft.commerce.dto.Estabelecimento;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoResponseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("codigoAcesso")
    @Pattern(regexp = "^\\d{6}$", message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    private String codigoAcesso;
}
