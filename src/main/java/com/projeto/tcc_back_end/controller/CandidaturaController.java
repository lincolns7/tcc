/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.MedicoBean;
import com.projeto.tcc_back_end.model.PlantaoBean;
import com.projeto.tcc_back_end.service.CandidaturaService;
import com.projeto.tcc_back_end.service.MedicoService;
import com.projeto.tcc_back_end.service.PlantaoService;
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

    @Autowired
    private PlantaoService plantaoService;

    /** Medico se candidata a um plantao. */
    @PostMapping("/plantao/{id}/candidatar")
    public String candidatar(@PathVariable Integer id,
                             HttpSession session,
                             RedirectAttributes flash) {

        if (!SessaoUtil.ehTipo(session, "MEDICO")) {
            return "redirect:/login";
        }

        MedicoBean medico = medicoService.buscarPorUsuario(SessaoUtil.logado(session).getId());
        PlantaoBean plantao = plantaoService.buscarPorId(id).orElse(null);

        if (plantao == null) {
            flash.addFlashAttribute("erro", "Plantao nao encontrado.");
            return "redirect:/iniciomedicos";
        }

        try {
            candidaturaService.candidatar(plantao, medico);
            flash.addFlashAttribute("sucesso", "Candidatura enviada com sucesso!");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/minhascandidaturas";
    }

    /** Hospital aprova um candidato. */
    @PostMapping("/candidatura/{id}/aprovar")
    public String aprovar(@PathVariable Integer id, HttpSession session, RedirectAttributes flash) {

        if (!SessaoUtil.ehTipo(session, "HOSPITAL")) {
            return "redirect:/login";
        }

        Integer plantaoId = candidaturaService.buscarPorId(id).getPlantao().getId();
        candidaturaService.aprovar(id);
        flash.addFlashAttribute("sucesso", "Candidato aprovado. O plantao foi marcado como PREENCHIDO.");

        return "redirect:/hospital/plantao/" + plantaoId + "/candidatos";
    }

    /** Hospital rejeita um candidato. */
    @PostMapping("/candidatura/{id}/rejeitar")
    public String rejeitar(@PathVariable Integer id, HttpSession session, RedirectAttributes flash) {

        if (!SessaoUtil.ehTipo(session, "HOSPITAL")) {
            return "redirect:/login";
        }

        Integer plantaoId = candidaturaService.buscarPorId(id).getPlantao().getId();
        candidaturaService.rejeitar(id);
        flash.addFlashAttribute("sucesso", "Candidato rejeitado.");

        return "redirect:/hospital/plantao/" + plantaoId + "/candidatos";
    }
}