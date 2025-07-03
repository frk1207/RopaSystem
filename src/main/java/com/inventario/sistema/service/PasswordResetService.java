package com.inventario.sistema.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.inventario.sistema.model.PasswordResetToken;
import com.inventario.sistema.model.Usuario;
import com.inventario.sistema.repository.PasswordResetRepository;
import com.inventario.sistema.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PasswordResetService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioRepository userRepository;

    @Autowired
    private PasswordResetRepository tokenRepository;

    public void createPasswordResetToken(String email, HttpServletRequest request) {
        Usuario user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setFechaExpiracion(LocalDateTime.now().plusMinutes(15)); // 15 min de validez

        tokenRepository.save(resetToken);

         emailService.enviarEnlaceRecuperacion(email, token, request);
    }

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (resetToken.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("El token ha expirado");
        }

        Usuario user = resetToken.getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        userRepository.save(user);

        tokenRepository.delete(resetToken); // ya no se necesita
    }
}
