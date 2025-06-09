package com.inventario.sistema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/admi-home")
    public String inicioAdmin() {
        return "admin/admi-home";
    }
}
