package com.ufcg.psoft.commerce.service.Email;

import com.ufcg.psoft.commerce.dto.Email.EmailPostDTO;
import com.ufcg.psoft.commerce.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Service
public class NotifyV1Service implements NotifyService {

    @Autowired
    EmailCriarService emailCriarService;

    @Override
    public Email notify(String receptor, String assunto, String text){
        EmailPostDTO emailPostDTO = new EmailPostDTO().builder()
                .receptor(receptor)
                .assunto(assunto)
                .text(text)
                .build();
        return emailCriarService.criar(emailPostDTO);
    }
}
