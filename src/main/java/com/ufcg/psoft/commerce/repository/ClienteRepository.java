package com.ufcg.psoft.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ufcg.psoft.commerce.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long>{
}
