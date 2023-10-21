package com.ufcg.psoft.commerce.listener;

import com.ufcg.psoft.commerce.events.EventoPedidoEmRota;
import com.ufcg.psoft.commerce.events.EventoPedidoEntregue;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.service.Email.NotifyV1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoEventListener {

    @Autowired
    NotifyV1Service notifier;

    @EventListener
    public void notificaPedidoEmRota(EventoPedidoEmRota evento){
        Entregador entregador = evento.getEntregador();
        String receptor = evento.getCliente().getEmail();
        String assunto = "Pedido a caminho!!!";
        String texto = "Olá, " + evento.getCliente().getNome() + "!\n" +
                "Só passando para te informar que seu pedido saiu para entrega!\n" +
                "Nome do entregador: " + entregador.getNome() + "\n"+
                "Veiculo: " + entregador.getTipoVeiculo() + "\n"+
                "Cor do veiculo: " + entregador.getCorVeiculo() + "\n"+
                "Placa do veiculo: " + entregador.getPlacaVeiculo();
        notifier.notify(receptor, assunto, texto);
    }
    @EventListener
    public void notificaPedidoEntregue(EventoPedidoEntregue evento){
        Estabelecimento estabelecimento = evento.getEstabelecimento();
        Cliente cliente = evento.getCliente();
        String receptor = estabelecimento.getEmail();
        String assunto = "Pedido entregue!!";
        String texto = "O pedido do cliente " + cliente.getNome() + "foi entregue com sucesso!\n" +
                "Bom trabalho!";
        notifier.notify(receptor, assunto, texto);
    }
}
