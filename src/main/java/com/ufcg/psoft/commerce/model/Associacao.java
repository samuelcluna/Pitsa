package com.ufcg.psoft.commerce.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "associacoes")
public class Associacao {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("entregadorId")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "entregador_id")
    private Entregador entregador;

    @JsonProperty("estabelecimentoId")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimento estabelecimento;

    @JsonProperty("status")
    private Boolean status;

    @PrePersist
    public void setDefaultValues() {
        if (status == null) {
            status = false;
        }
    }

    public Boolean isStatus(){
        return status;
    }
}
