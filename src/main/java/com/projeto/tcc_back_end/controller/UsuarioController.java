/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author TI Paraná
 */
@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String logar(@ModelAttribute UsuarioBean usuario, HttpSession session) {

    UsuarioBean usuarioLogado = service.logar(usuario);

    if (usuarioLogado == null) {
        return "redirect:/login";
    }

    session.setAttribute("usuarioLogado", usuarioLogado);

    return "redirect:/dashboard";
}

    @GetMapping("/cadastrousuario")
    public String cadastroUsuario() {
        return "cadastrousuario";
    }

    @PostMapping("/cadastrousuario")
    public String cadastrar(@ModelAttribute UsuarioBean usuario, Model model) {

        try {
            service.cadastrarUsuario(usuario);
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastrousuario";
        }

        return "redirect:/login";
    }

}
