package com.ufcg.psoft.commerce.repository;

import com.ufcg.psoft.commerce.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findAllByClienteId(Long clienteId);

    List<Pedido> findAllByClienteIdAndStatusEntrega(Long clienteId, String statusEntrega);
    List<Pedido> findAllByClienteIdAndEstabelecimentoId(Long clienteId, Long estabelecimentoId);
    List<Pedido> findAllByClienteIdAndEstabelecimentoIdAndStatusEntrega(Long clienteId, Long estabelecimentoId, String statusEntrega);

}
