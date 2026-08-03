/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.HospitalBean;
import com.projeto.tcc_back_end.model.PlantaoBean;
import com.projeto.tcc_back_end.service.HospitalService;
import com.projeto.tcc_back_end.service.PlantaoService;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
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
public class PlantaoController {

    @Autowired
    private PlantaoService plantaoService;

    @Autowired
    private HospitalService hospitalService;

    /** Dashboard do medico: apenas plantoes ABERTOS. */
    @GetMapping("/iniciomedicos")
    public String iniciomedicos(HttpSession session, Model model) {

        if (!SessaoUtil.ehTipo(session, "MEDICO")) {
            return "redirect:/login";
        }

        model.addAttribute("plantoes", plantaoService.listarAbertos());
        model.addAttribute("usuario", SessaoUtil.logado(session));

        return "iniciomedicos";
    }

    /** Detalhes do plantao (medico). */
    @GetMapping("/plantao/{id}")
    public String detalhes(@PathVariable Integer id, HttpSession session, Model model) {

        if (SessaoUtil.logado(session) == null) {
            return "redirect:/login";
        }

        PlantaoBean plantao = plantaoService.buscarPorId(id).orElse(null);

        if (plantao == null) {
            return "redirect:/iniciomedicos";
        }

        model.addAttribute("plantao", plantao);

        return "detalhesplantao";
    }

    /** Formulario de novo plantao (hospital). */
    @GetMapping("/plantao/novo")
    public String novo(HttpSession session, Model model) {

        if (!SessaoUtil.ehTipo(session, "HOSPITAL")) {
            return "redirect:/login";
        }

        model.addAttribute("plantao", new PlantaoBean());

        return "formplantao";
    }

    /** Formulario de edicao de plantao (hospital). */
    @GetMapping("/plantao/{id}/editar")
    public String editar(@PathVariable Integer id, HttpSession session, Model model) {

        if (!SessaoUtil.ehTipo(session, "HOSPITAL")) {
            return "redirect:/login";
        }

        model.addAttribute("plantao", plantaoService.buscarPorId(id).orElse(new PlantaoBean()));

        return "formplantao";
    }

    /** Salva (cria ou atualiza) o plantao do hospital logado. */
    @PostMapping("/plantao/salvar")
    public String salvar(@RequestParam(required = false) Integer id,
                         @RequestParam String titulo,
                         @RequestParam String especialidade,
                         @RequestParam String data,
                         @RequestParam String horario,
                         @RequestParam Double valor,
                         @RequestParam(required = false) String status,
                         HttpSession session,
                         Model model) {

        if (!SessaoUtil.ehTipo(session, "HOSPITAL")) {
            return "redirect:/login";
        }

        HospitalBean hospital = hospitalService.buscarPorUsuario(SessaoUtil.logado(session).getId());

        PlantaoBean plantao = (id != null)
                ? plantaoService.buscarPorId(id).orElse(new PlantaoBean())
                : new PlantaoBean();

        try {
            plantao.setHospital(hospital);
            plantao.setTitulo(titulo);
            plantao.setEspecialidade(especialidade);
            plantao.setData(LocalDate.parse(data));
            plantao.setHorario(LocalTime.parse(horario));
            plantao.setValor(valor);
            plantao.setStatus(status);

            plantaoService.salvar(plantao);

            return "redirect:/iniciohospital";
        } catch (RuntimeException e) {
            model.addAttribute("plantao", plantao);
            model.addAttribute("erro", "Nao foi possivel salvar: " + e.getMessage());
            return "formplantao";
        }
    }

    @PostMapping("/plantao/{id}/excluir")
    public String excluir(@PathVariable Integer id, HttpSession session) {

        if (!SessaoUtil.ehTipo(session, "HOSPITAL")) {
            return "redirect:/login";
        }

        plantaoService.excluirPlantao(id);

        return "redirect:/iniciohospital";
    }
}
