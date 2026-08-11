/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.CandidaturaBean;
import com.projeto.tcc_back_end.model.HospitalBean;
import com.projeto.tcc_back_end.model.MedicoBean;
import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.service.CandidaturaService;
import com.projeto.tcc_back_end.service.HospitalService;
import com.projeto.tcc_back_end.service.MedicoService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    private HospitalService hospitalService;

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
                    "Apenas médicos podem se candidatar."
            );

            return "redirect:/plantoes";
        }

        MedicoBean medico =
                medicoService.buscarPorUsuario(usuario);

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

    @GetMapping("/minhascandidaturas")
    public String minhasCandidaturas(
            HttpSession session,
            Model model) {

        UsuarioBean usuario =
                (UsuarioBean) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!"MEDICO".equals(usuario.getTipo())) {
            return "redirect:/dashboard";
        }

        MedicoBean medico =
                medicoService.buscarPorUsuario(usuario);

        if (medico == null) {
            return "redirect:/dashboard";
        }

        List<CandidaturaBean> candidaturas =
                candidaturaService.listarPorMedico(medico);

        model.addAttribute(
                "usuario",
                usuario
        );

        model.addAttribute(
                "candidaturas",
                candidaturas
        );

        return "minhascandidaturas";
    }

    @PostMapping("/cancelarcandidatura/{id}")
    public String cancelarCandidatura(
            @PathVariable Integer id,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        UsuarioBean usuario =
                (UsuarioBean) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!"MEDICO".equals(usuario.getTipo())) {
            return "redirect:/dashboard";
        }

        MedicoBean medico =
                medicoService.buscarPorUsuario(usuario);

        if (medico == null) {
            return "redirect:/dashboard";
        }

        try {

            candidaturaService.cancelarCandidatura(id, medico
            );

            redirectAttributes.addFlashAttribute("sucesso","Candidatura cancelada com sucesso."
            );

        } catch (IllegalArgumentException e) {

            redirectAttributes.addFlashAttribute("erro",e.getMessage()
            );
        }

        return "redirect:/minhascandidaturas";
    }

    @GetMapping("/solicitacoes")
    public String solicitacoes(
            HttpSession session,
            Model model) {

        UsuarioBean usuario =
                (UsuarioBean) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!"HOSPITAL".equals(usuario.getTipo())) {
            return "redirect:/dashboard";
        }

        HospitalBean hospital =
                hospitalService.buscarPorUsuario(usuario);

        if (hospital == null) {
            return "redirect:/dashboard";
        }

        List<CandidaturaBean> candidaturas =
                candidaturaService.listarSolicitacoes(hospital);

        model.addAttribute("usuario",usuario
        );

        model.addAttribute("candidaturas",candidaturas
        );

        return "solicitacoes";
    }
    
    @PostMapping("/aceitarcandidatura/{id}")
    public String aceitarCandidatura(
        @PathVariable Integer id,
        HttpSession session,
        RedirectAttributes redirectAttributes) {

    UsuarioBean usuario = (UsuarioBean) session.getAttribute("usuarioLogado");

    if (usuario == null) {
        return "redirect:/login";
    }

    if (!"HOSPITAL".equals(usuario.getTipo())) {
        return "redirect:/dashboard";
    }

    HospitalBean hospital = hospitalService.buscarPorUsuario(usuario);

    if (hospital == null) {
        return "redirect:/dashboard";
    }

    try {

        candidaturaService.aceitarCandidatura(id, hospital);

        redirectAttributes.addFlashAttribute("sucesso","Candidatura aceita com sucesso."
        );

    } catch (IllegalArgumentException e) {

        redirectAttributes.addFlashAttribute("erro",e.getMessage()
        );
    }

    return "redirect:/solicitacoes";
}

        @PostMapping("/recusarcandidatura/{id}")
        public String recusarCandidatura(
        @PathVariable Integer id,
        HttpSession session,
        RedirectAttributes redirectAttributes) {

    UsuarioBean usuario = (UsuarioBean) session.getAttribute("usuarioLogado");

    if (usuario == null) {
        return "redirect:/login";
    }

    if (!"HOSPITAL".equals(usuario.getTipo())) {
        return "redirect:/dashboard";
    }

    HospitalBean hospital = hospitalService.buscarPorUsuario(usuario);

    if (hospital == null) {
        return "redirect:/dashboard";
    }

    try {

        candidaturaService.recusarCandidatura(id, hospital);

        redirectAttributes.addFlashAttribute(
                "sucesso",
                "Candidatura recusada com sucesso."
        );

    } catch (IllegalArgumentException e) {

        redirectAttributes.addFlashAttribute(
                "erro",
                e.getMessage()
        );
    }

    return "redirect:/solicitacoes";
}
        @GetMapping("/meusplantoesmedico")
    public String meusPlantoesMedico(
        HttpSession session,
        Model model) {

    UsuarioBean usuario = (UsuarioBean) session.getAttribute("usuarioLogado");

    if (usuario == null) {
        return "redirect:/login";
    }

    if (!"MEDICO".equals(usuario.getTipo())) {
        return "redirect:/dashboard";
    }

    MedicoBean medico = medicoService.buscarPorUsuario(usuario);

    if (medico == null) {
        return "redirect:/dashboard";
    }

    List<CandidaturaBean> candidaturas =
            candidaturaService.listarPlantaoAceitos(medico);

    model.addAttribute("usuario", usuario);
    model.addAttribute("candidaturas", candidaturas);

    return "meusplantoesmedico";
}
}