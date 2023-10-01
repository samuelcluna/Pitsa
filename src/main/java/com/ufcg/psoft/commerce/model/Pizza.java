package com.ufcg.psoft.commerce.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Pizza {

    private double valor;

    public Pizza(List<Sabor> sabores, String tamanho) {
        if (sabores.size() > 2 && tamanho.equals("grande")) throw new RuntimeException("Estouro para o máximo 2 sabores de pizza grande.");
        valor = this.calcularValor(sabores, tamanho);
    }

    private double calcularValor(List<Sabor> sabores, String tamanho) {
        double total = 0;
        if (tamanho.equals("grande")) {
            for (Sabor sabor: sabores) {
                total += sabor.getPrecoG();
            }
            return total / sabores.size();
        }
        return sabores.get(0).getPrecoM();
    }

}
