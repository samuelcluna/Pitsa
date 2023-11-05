package com.ufcg.psoft.commerce.repository;

import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.model.enums.DisponibilidadeEntregador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntregadorRepository extends JpaRepository<Entregador, Long> {
}