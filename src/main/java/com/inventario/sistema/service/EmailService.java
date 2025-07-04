package com.inventario.sistema.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEnlaceRecuperacion(String toEmail, String token, HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder
                .fromRequestUri(request)
                .replacePath(null)
                .build()    
                .toUriString();

        String enlace = baseUrl + "/reset-password?token=" + token;
        
        // Construir el mensaje de correo electrónico
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(toEmail);
        mensaje.setSubject("Recuperación de contraseña");
        mensaje.setText("Haz clic en el siguiente enlace para restablecer tu contraseña:\n" + enlace);

        mailSender.send(mensaje);
    }
}
