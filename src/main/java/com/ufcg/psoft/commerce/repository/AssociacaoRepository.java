package com.ufcg.psoft.commerce.repository;

import com.ufcg.psoft.commerce.model.Associacao;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociacaoRepository extends JpaRepository<Associacao,Long> {
    Associacao findByEntregadorAndEstabelecimento(Entregador entregador, Estabelecimento estabelecimento);
}
