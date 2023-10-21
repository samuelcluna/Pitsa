package com.ufcg.psoft.commerce.repository;

import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.model.enums.PedidoStatusEntregaEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findAllByClienteId(Long clienteId);


    @Query("SELECT p FROM Pedido p WHERE p.clienteId = :clienteId AND (:estabelecimentoId IS NULL OR p.estabelecimentoId = :estabelecimentoId) AND (:statusEntrega IS NULL OR p.statusEntrega = :statusEntrega)")
    List<Pedido> findAllByClienteIdAndEstabelecimentoIdAndStatusEntrega(
            @Param("clienteId") Long clienteId,
            @Param("estabelecimentoId") Long estabelecimentoId,
            @Param("statusEntrega") PedidoStatusEntregaEnum statusEntrega
    );

}
