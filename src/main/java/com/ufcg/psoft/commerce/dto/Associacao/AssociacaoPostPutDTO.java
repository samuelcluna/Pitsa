package com.ufcg.psoft.commerce.dto.Associacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AssociacaoPostPutDTO {

    @JsonProperty("entregadorId")
    @NotBlank(message = "Id do entregador obrigatório")
    private Entregador entregadorId;

    @JsonProperty("estabelecimentoId")
    @NotBlank(message = "Id do estabelecimento obrigatório")
    private Estabelecimento estabelecimentoId;

}
