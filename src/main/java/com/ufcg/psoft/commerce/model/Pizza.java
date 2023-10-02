package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pizzas")
public class Pizza {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    @JsonProperty("sabor1")
    private Sabor sabor1;

    @JsonProperty("sabor2")
    private Sabor sabor2;

    @JsonProperty("tamanho")
    @Column(nullable = false)
    private String tamanho;

    @JsonProperty("preco")
    private double preco;

//    @PrePersist
//    @PreUpdate
//    private void calcularValor() {
//        double total = 0;
//        if (tamanho.equals("grande")) {
//            for (Sabor sabor: sabores) {
//                total += sabor.getPrecoG();
//            }
//            this.preco = total / sabores.size();
//        }
//        this.preco = sabores.get(0).getPrecoM();
//    }

}
