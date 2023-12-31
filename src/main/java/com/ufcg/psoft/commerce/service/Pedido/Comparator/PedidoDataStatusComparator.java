package com.ufcg.psoft.commerce.service.Pedido.Comparator;

import com.ufcg.psoft.commerce.model.Pedido;

import java.util.Comparator;

public class PedidoDataStatusComparator implements Comparator<Pedido> {
    @Override
    public int compare(Pedido o1, Pedido o2) {

        int dataCompare = o2.getData().compareTo(o1.getData());
        int statusCompare = o1.getStatusEntrega().compareTo(o2.getStatusEntrega());

        if(statusCompare != 0) return statusCompare;

        return dataCompare;
    }

}
