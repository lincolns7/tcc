/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.MedicoBean;
import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.service.CandidaturaService;
import com.projeto.tcc_back_end.service.MedicoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Aluno
 */
@Controller
public class CandidaturaController {

    @Autowired
    private CandidaturaService candidaturaService;

    @Autowired
    private MedicoService medicoService;

    @PostMapping("/candidatar/{plantaoId}")
    public String candidatar(
            @PathVariable Integer plantaoId,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        UsuarioBean usuario =
                (UsuarioBean) session.getAttribute("usuarioLogado");

        if (usuario == null) {

            return "redirect:/login";
        }
        
        if (!"MEDICO".equals(usuario.getTipo())) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    "Apenas médicos podem se candidatar aos plantões."
            );

            return "redirect:/plantoes";
        }

        MedicoBean medico = medicoService.buscarPorUsuario(usuario);

        if (medico == null) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    "Cadastro de médico não encontrado."
            );

            return "redirect:/dashboard";
        }

        try {

            candidaturaService.candidatar(
                    plantaoId,
                    medico.getId()
            );

            redirectAttributes.addFlashAttribute(
                    "sucesso",
                    "Candidatura realizada com sucesso!"
            );

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute(
                    "erro",
                    e.getMessage()
            );
        }

        return "redirect:/plantoes";
    }
}