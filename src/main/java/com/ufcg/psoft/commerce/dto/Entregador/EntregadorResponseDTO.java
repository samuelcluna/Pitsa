package com.ufcg.psoft.commerce.dto.Entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.Entregador;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntregadorResponseDTO {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("placaVeiculo")
    private String placaVeiculo;

    @JsonProperty("tipoVeiculo")
    private String tipoVeiculo;

    @JsonProperty("corVeiculo")
    private String corVeiculo;

    public EntregadorResponseDTO(Entregador entregador) {
        this.id = entregador.getId();
        this.nome = entregador.getNome();
        this.placaVeiculo = entregador.getPlacaVeiculo();
        this.tipoVeiculo = entregador.getTipoVeiculo();
        this.corVeiculo = entregador.getCorVeiculo();
    }
}

