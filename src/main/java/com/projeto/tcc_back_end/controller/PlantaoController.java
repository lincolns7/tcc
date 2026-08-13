/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.HospitalBean;
import com.projeto.tcc_back_end.model.PlantaoBean;
import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.service.CandidaturaService;
import com.projeto.tcc_back_end.service.HospitalService;
import com.projeto.tcc_back_end.service.PlantaoService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author TI Paraná
 */
@Controller
public class PlantaoController {

    @Autowired
    private PlantaoService service;

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private CandidaturaService candidaturaService;

    @GetMapping("/cadastroplantao")
    public String telaCadastro(HttpSession session) {

        UsuarioBean usuario =
                (UsuarioBean) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!"HOSPITAL".equals(usuario.getTipo())) {
            return "redirect:/dashboard";
        }

        return "cadastroplantao";
    }

    @PostMapping("/cadastroplantao")
    public String cadastrar(
            @ModelAttribute PlantaoBean plantao,
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

            model.addAttribute(
                    "erro",
                    "Hospital não encontrado."
            );

            return "cadastroplantao";
        }

        plantao.setHospital_id(hospital);
        plantao.setStatus("ABERTO");

        try {

            service.cadastrarPlantao(plantao);

        } catch (IllegalArgumentException e) {

            model.addAttribute(
                    "erro",
                    e.getMessage()
            );

            return "cadastroplantao";
        }

        return "redirect:/dashboard";
    }

    @GetMapping("/plantoes")
    public String listar(
            Model model,
            HttpSession session) {

        UsuarioBean usuario =
                (UsuarioBean) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!"MEDICO".equals(usuario.getTipo())) {
            return "redirect:/dashboard";
        }

        model.addAttribute(
                "plantoes",
                service.listarDisponiveis()
        );

        model.addAttribute(
                "usuario",
                usuario
        );

        return "plantoes";
    }

    @GetMapping("/meusplantoes")
    public String meusPlantoes(
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

        List<PlantaoBean> plantoes =
                service.listarPorHospital(hospital);

        model.addAttribute(
                "plantoes",
                plantoes
        );

        model.addAttribute(
                "usuario",
                usuario
        );

        return "meusplantoes";
    }

    @GetMapping("/meuscandidatos/{id}")
    public String meusCandidatos(
            @PathVariable Integer id,
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

        PlantaoBean plantao =
                service.buscarPorId(id);

        if (plantao == null) {
            return "redirect:/meusplantoes";
        }

        if (plantao.getHospital_id() == null ||
                !plantao.getHospital_id().getId()
                        .equals(hospital.getId())) {

            return "redirect:/meusplantoes";
        }

        model.addAttribute(
                "plantao",
                plantao
        );

        model.addAttribute(
                "usuario",
                usuario
        );

        return "candidatos";
    }
}