/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.repository.UsuarioDAO;
import com.projeto.tcc_back_end.service.PlantaoService;
import com.projeto.tcc_back_end.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author TI Paraná
 */
@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String logar(@RequestParam String email,
                        @RequestParam String senha,
                        HttpSession session,
                        Model model) {
        try {
            UsuarioBean usuario = service.autenticar(email, senha);
            SessaoUtil.logar(session, usuario);
            return service.telaInicial(usuario);
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}