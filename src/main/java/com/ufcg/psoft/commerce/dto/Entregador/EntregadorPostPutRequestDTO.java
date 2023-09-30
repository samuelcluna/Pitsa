package com.ufcg.psoft.commerce.dto.Entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EntregadorPostPutRequestDTO {

    @JsonProperty("nome")
    @NotBlank(message = "Nome e obrigatorio")
    private String nome;

    @JsonProperty("codigoAcesso")
    @NotNull(message = "Codigo de acesso obrigatorio")
    @Pattern(regexp = "^\\d{6}$", message = "Codigo de acesso invalido!")
    private String codigoAcesso;

    @JsonProperty("placaVeiculo")
    @NotBlank(message = "Placa do veiculo e obrigatoria")
    private String placaVeiculo;

    @JsonProperty("tipoVeiculo")
    @NotBlank(message = "Tipo do veiculo e obrigatorio")
    @Pattern(regexp = "^(moto|carro)$", message = "Tipo do veiculo deve ser moto ou carro")
    private String tipoVeiculo;

    @JsonProperty("corVeiculo")
    @NotBlank(message = "Cor do veiculo e obrigatoria")
    private String corVeiculo;
}