package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pizzas")
public class Pizza {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JsonProperty("tamanho")
    private String tamanho;

    @JsonProperty("sabor1")
    @OneToOne
    private Sabor sabor1;

    @JsonProperty("sabor2")
    @OneToOne
    private Sabor sabor2;

    public double calcularPreco() {
        double precoSabor1;
        double precoSabor2;

        if (tamanho.equals("grande")) {
            precoSabor1 = sabor1.getPrecoG();
            precoSabor2 = (sabor2 != null) ? sabor2.getPrecoG() : 0;
        } else {
            precoSabor1 = sabor1.getPrecoM();
            precoSabor2 = (sabor2 != null) ? sabor2.getPrecoM() : 0;
        }

        return (precoSabor1 + precoSabor2) / ((sabor2 != null) ? 2 : 1);
    }

}