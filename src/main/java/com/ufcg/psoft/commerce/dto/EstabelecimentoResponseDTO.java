package com.ufcg.psoft.commerce.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Size(min = 6, max = 6, message = "O código de acesso deve ter exatamente 6 caracteres.")
    private String codigoAcesso;
}
