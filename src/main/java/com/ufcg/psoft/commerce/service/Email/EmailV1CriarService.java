package com.ufcg.psoft.commerce.service.Email;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Associacao.AssociacaoResponseDTO;
import com.ufcg.psoft.commerce.dto.Email.EmailPostDTO;
import com.ufcg.psoft.commerce.model.Email;
import com.ufcg.psoft.commerce.repository.EmailRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailV1CriarService implements EmailCriarService{
    @Autowired
    EmailRepository emailRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private JavaMailSender emailSender;

    public Email criar(EmailPostDTO emailDTO){
        Email email = modelMapper.map(emailDTO, Email.class);
        email.setDataDeEnvio(LocalDateTime.now());


        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(email.getReceptor());
        mensagem.setSubject(email.getAssunto());
        mensagem.setText(email.getText());
        emailSender.send(mensagem);
        email.setStatus(true);
        return emailRepository.save(email);

    }
}
