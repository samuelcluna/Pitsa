package com.ufcg.psoft.commerce.repository;

import com.ufcg.psoft.commerce.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email,Long> {

}
