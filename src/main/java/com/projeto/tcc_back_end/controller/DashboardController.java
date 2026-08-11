/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.service.PlantaoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Aluno
 */
@Controller
public class DashboardController {

    @Autowired
    private PlantaoService plantaoService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {

        UsuarioBean usuario = (UsuarioBean) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);

        model.addAttribute("plantoes", plantaoService.listarDisponiveis());

        if ("MEDICO".equals(usuario.getTipo())) {
            return "dashboard";
        }

        if ("HOSPITAL".equals(usuario.getTipo())) {
            return "dashboard-hospital";
        }

        return "redirect:/";
    }
}