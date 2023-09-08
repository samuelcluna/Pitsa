package com.ufcg.psoft.commerce.service.Sabor;

import com.ufcg.psoft.commerce.model.Sabor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public interface SaborObterService {

    Set<Sabor> obterTodos(Long idEstabelecimento, String codigoAcesso);

    Sabor obter(Long idSabor, Long idEstabelecimento, String codigoAcesso);

}
