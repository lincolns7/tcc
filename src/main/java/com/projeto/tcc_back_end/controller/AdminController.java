/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.service.HospitalService;
import com.projeto.tcc_back_end.service.MedicoService;
import com.projeto.tcc_back_end.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Aluno
 */
@Controller
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MedicoService medicoService;

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/admin")
    public String dashboard(HttpSession session, Model model) {

        if (!SessaoUtil.ehTipo(session, "ADMIN")) {
            return "redirect:/login";
        }

        model.addAttribute("usuarios", usuarioService.listarUsuarios());
        model.addAttribute("medicos", medicoService.listarMedicos());
        model.addAttribute("hospitais", hospitalService.listarHospitais());

        return "admin";
    }

    @PostMapping("/admin/usuario/{id}/excluir")
    public String excluirUsuario(@PathVariable Integer id, HttpSession session) {

        if (!SessaoUtil.ehTipo(session, "ADMIN")) {
            return "redirect:/login";
        }

        usuarioService.excluirUsuario(id);

        return "redirect:/admin";
    }
}
