package com.ufcg.psoft.commerce.dto.Associacao;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AssociacaoResponseDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("entregadorId")
    private Entregador entregador;

    @JsonProperty("estabelecimentoId")
    private Estabelecimento estabelecimento;

    @JsonProperty("status")
    private Boolean status;

    public Boolean isStatus(){
        return status;
    }
}
