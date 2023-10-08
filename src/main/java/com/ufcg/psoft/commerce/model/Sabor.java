package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.dto.Cliente.ClienteResponseDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sabores")
public class Sabor {
    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("nome")
    String nome;

    @JsonProperty("tipo")
    String tipo;

    @JsonProperty("precoM")
    @Positive
    double precoM;

    @JsonProperty("precoG")
    @Positive
    double precoG;


    @JsonProperty("disponivel")
    Boolean disponivel; // padrao

    @ManyToOne
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimento estabelecimento;

    @JsonProperty("clientesInteressados")
    @ElementCollection
    private Set<Long> clientesInteressados;

    @PrePersist
    public void setDefaultValues() {
        if (disponivel == null) {
            disponivel = true;
        }
    }
    public Boolean isDisponivel() {
        return disponivel;
    }
}
