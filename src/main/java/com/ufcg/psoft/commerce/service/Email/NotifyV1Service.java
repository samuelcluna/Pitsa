package com.ufcg.psoft.commerce.service.Email;

import com.ufcg.psoft.commerce.dto.Email.EmailPostDTO;
import com.ufcg.psoft.commerce.model.Email;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Service
public class NotifyV1Service implements NotifyService {

    private final String baseUrl = "http://localhost:8080";

    @Override
    public Email notify(String proprietarioRef, String emissor, String receptor, String assunto, String text){
        EmailPostDTO emailPostDTO = new EmailPostDTO().builder()
                .proprietarioRef(proprietarioRef)
                .emissor(emissor)
                .receptor(receptor)
                .assunto(assunto)
                .text(text)
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<EmailPostDTO> requestEntity = new HttpEntity<>(emailPostDTO, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(baseUrl + "/emails", requestEntity, Email.class);
    }
}
