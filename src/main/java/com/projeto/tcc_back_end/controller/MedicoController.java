/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.MedicoBean;
import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.service.MedicoService;
import com.projeto.tcc_back_end.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author TI Paraná
 */
@Controller
public class MedicoController {

    @Autowired
    private MedicoService service;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastromedico")
    public String cadastroMedico() {

        return "cadastromedico";

    }

    @PostMapping("/cadastromedico")
    public String cadastrar(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String telefone,
            @RequestParam String senha,
            @RequestParam String crm,
            @RequestParam String especialidade) {

        service.cadastrarMedico(
                nome,
                email,
                senha,
                telefone,
                crm,
                especialidade);

        return "redirect:/login";
    }

    @GetMapping("/editarmedico")
    public String editarMedico(
            HttpSession session,
            Model model) {

        UsuarioBean usuario =
                (UsuarioBean) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!"MEDICO".equals(usuario.getTipo())) {
            return "redirect:/";
        }

        MedicoBean medico =
                service.buscarPorUsuario(usuario);

        if (medico == null) {
            return "redirect:/";
        }

        model.addAttribute("medico", medico);
        model.addAttribute("usuario", usuario);

        return "editarmedico";
    }

    @PostMapping("/editarmedico")
    public String salvarEdicao(
            @ModelAttribute MedicoBean medico,
            @RequestParam String nome,
            @RequestParam String email,
            HttpSession session) {

        UsuarioBean usuario =
                (UsuarioBean) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!"MEDICO".equals(usuario.getTipo())) {
            return "redirect:/";
        }

        MedicoBean medicoBanco =
                service.buscarPorId(medico.getId());

        if (medicoBanco == null) {
            return "redirect:/";
        }

        medicoBanco.setTelefone(medico.getTelefone());
        medicoBanco.setEspecialidade(medico.getEspecialidade());

        service.atualizarMedico(medicoBanco);

        usuario.setNome(nome);
        usuario.setEmail(email);

        usuarioService.atualizarUsuario(usuario);

        session.setAttribute("usuarioLogado", usuario);

        return "redirect:/iniciomedicos";
    }
}