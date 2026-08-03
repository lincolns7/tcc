/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.MedicoBean;
import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.service.CandidaturaService;
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
    private UsuarioService usuarioService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private CandidaturaService candidaturaService;

    @GetMapping("/cadastromedico")
    public String telaCadastro() {
        return "cadastromedico";
    }

    @PostMapping("/cadastromedico")
    public String cadastrar(@RequestParam String nome,
                            @RequestParam String email,
                            @RequestParam String senha,
                            @RequestParam String crm,
                            @RequestParam String especialidade,
                            Model model) {
        try {
            UsuarioBean usuario = new UsuarioBean();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);

            MedicoBean medico = new MedicoBean();
            medico.setCrm(crm);
            medico.setEspecialidade(especialidade);

            usuarioService.cadastrarMedico(usuario, medico);

            return "redirect:/login?cadastro=ok";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastromedico";
        }
    }

    /** Minhas candidaturas (area do medico). */
    @GetMapping("/minhascandidaturas")
    public String minhasCandidaturas(HttpSession session, Model model) {

        if (!SessaoUtil.ehTipo(session, "MEDICO")) {
            return "redirect:/login";
        }

        MedicoBean medico = medicoService.buscarPorUsuario(SessaoUtil.logado(session).getId());
        model.addAttribute("candidaturas", candidaturaService.listarPorMedico(medico.getId()));

        return "minhascandidaturas";
    }
}
