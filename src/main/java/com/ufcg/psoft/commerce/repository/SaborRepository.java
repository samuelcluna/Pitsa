package com.ufcg.psoft.commerce.repository;

import com.ufcg.psoft.commerce.model.Sabor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SaborRepository extends JpaRepository<Sabor, Long> {

    boolean existsByIdAndEstabelecimentoId(Long idSabor, Long idEstabelecimento);

    Set<Sabor> findAllByEstabelecimentoIdAndTipo(Long estabelecimentoId, String tipo);

    Set<Sabor> findAllByEstabelecimentoId(Long estabelecimentoId);

    Optional<Sabor> findByIdAndEstabelecimentoId(Long idSabor, Long idEstabelecimento);

}
