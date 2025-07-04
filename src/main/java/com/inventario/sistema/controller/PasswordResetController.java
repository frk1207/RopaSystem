package com.inventario.sistema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.inventario.sistema.service.PasswordResetService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class PasswordResetController {

    @Autowired
    private PasswordResetService resetService;

    // Mostrar formulario
    @GetMapping("/reset-password")
    public String showResetForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    // Procesar formulario
    @PostMapping("/reset-password")
    public String processResetForm(
            @RequestParam String token,
            @RequestParam String newPassword,
            RedirectAttributes redirectAttributes) {

        try {
            resetService.resetPassword(token, newPassword);
            redirectAttributes.addFlashAttribute("mensaje", "Contraseña actualizada exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/login";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm(Model model) {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(
            @RequestParam String email,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        try {
            resetService.createPasswordResetToken(email, request);
            redirectAttributes.addFlashAttribute("mensaje", "Revisa tu correo para restablecer tu contraseña.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/forgot-password";
    }
}

