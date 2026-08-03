/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.HospitalBean;
import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.service.CandidaturaService;
import com.projeto.tcc_back_end.service.HospitalService;
import com.projeto.tcc_back_end.service.PlantaoService;
import com.projeto.tcc_back_end.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author TI Paraná
 */
@Controller
public class HospitalController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private PlantaoService plantaoService;

    @Autowired
    private CandidaturaService candidaturaService;

    @GetMapping("/cadastrohospital")
    public String telaCadastro() {
        return "cadastrohospital";
    }

    @PostMapping("/cadastrohospital")
    public String cadastrar(@RequestParam String nome,
                            @RequestParam String email,
                            @RequestParam String senha,
                            @RequestParam String cnpj,
                            @RequestParam String nomeHospital,
                            Model model) {
        try {
            UsuarioBean usuario = new UsuarioBean();
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);

            HospitalBean hospital = new HospitalBean();
            hospital.setCnpj(cnpj);
            hospital.setNomeHospital(nomeHospital);

            usuarioService.cadastrarHospital(usuario, hospital);

            return "redirect:/login?cadastro=ok";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            return "cadastrohospital";
        }
    }

    /** Dashboard do hospital: lista os plantoes que ele publicou. */
    @GetMapping("/iniciohospital")
    public String dashboard(HttpSession session, Model model) {

        if (!SessaoUtil.ehTipo(session, "HOSPITAL")) {
            return "redirect:/login";
        }

        HospitalBean hospital = hospitalService.buscarPorUsuario(SessaoUtil.logado(session).getId());
        model.addAttribute("hospital", hospital);
        model.addAttribute("plantoes", plantaoService.listarPorHospital(hospital.getId()));

        return "iniciohospital";
    }

    /** Editar dados do hospital. */
    @GetMapping("/hospital/editar")
    public String telaEditar(HttpSession session, Model model) {

        if (!SessaoUtil.ehTipo(session, "HOSPITAL")) {
            return "redirect:/login";
        }

        model.addAttribute("hospital",
                hospitalService.buscarPorUsuario(SessaoUtil.logado(session).getId()));

        return "editarhospital";
    }

    @PostMapping("/hospital/editar")
    public String editar(@RequestParam String cnpj,
                         @RequestParam String nomeHospital,
                         HttpSession session,
                         Model model) {

        if (!SessaoUtil.ehTipo(session, "HOSPITAL")) {
            return "redirect:/login";
        }

        HospitalBean hospital = hospitalService.buscarPorUsuario(SessaoUtil.logado(session).getId());

        try {
            hospital.setCnpj(cnpj);
            hospital.setNomeHospital(nomeHospital);
            hospitalService.atualizarHospital(hospital);
            return "redirect:/iniciohospital";
        } catch (IllegalArgumentException e) {
            model.addAttribute("hospital", hospital);
            model.addAttribute("erro", e.getMessage());
            return "editarhospital";
        }
    }

    /** Hospital visualiza os medicos candidatos de um plantao. */
    @GetMapping("/hospital/plantao/{id}/candidatos")
    public String candidatos(@PathVariable Integer id, HttpSession session, Model model) {

        if (!SessaoUtil.ehTipo(session, "HOSPITAL")) {
            return "redirect:/login";
        }

        model.addAttribute("plantao", plantaoService.buscarPorId(id).orElse(null));
        model.addAttribute("candidaturas", candidaturaService.listarPorPlantao(id));

        return "candidatos";
    }
}
