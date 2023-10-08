package com.ufcg.psoft.commerce.service.Email;

import com.ufcg.psoft.commerce.dto.Email.EmailPostDTO;
import com.ufcg.psoft.commerce.model.Email;

@FunctionalInterface
public interface EmailCriarService {
    public Email criar(EmailPostDTO emailDTO);
}
