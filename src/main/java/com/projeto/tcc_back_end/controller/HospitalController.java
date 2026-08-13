/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.HospitalBean;
import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.service.HospitalService;
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
 * @author Aluno
 */
@Controller
public class HospitalController {

    @Autowired
    private HospitalService service;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/cadastrohospital")
    public String cadastroHospital() {

        return "cadastrohospital";
    }

    @PostMapping("/cadastrohospital")
    public String cadastrar(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String telefone,
            @RequestParam String cnpj,
            @RequestParam String nomeHospital,
            @RequestParam String endereco) {

        service.cadastrarHospital(
                nome,
                email,
                senha,
                telefone,
                cnpj,
                nomeHospital,
                endereco);

        return "redirect:/login";
    }

    @GetMapping("/editarhospital")
    public String editarHospital(
            HttpSession session,
            Model model) {

        UsuarioBean usuario =
                (UsuarioBean) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!"HOSPITAL".equals(usuario.getTipo())) {
            return "redirect:/";
        }

        HospitalBean hospital =
                service.buscarPorUsuario(usuario);

        if (hospital == null) {
            return "redirect:/";
        }

        model.addAttribute("hospital", hospital);
        model.addAttribute("usuario", usuario);

        return "editarhospital";
    }

    @PostMapping("/editarhospital")
    public String salvarEdicao(
            @ModelAttribute HospitalBean hospital,
            @RequestParam String nome,
            @RequestParam String email,
            HttpSession session) {

        UsuarioBean usuario =
                (UsuarioBean) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        if (!"HOSPITAL".equals(usuario.getTipo())) {
            return "redirect:/";
        }

        HospitalBean hospitalBanco =
                service.buscarPorId(hospital.getId());

        if (hospitalBanco == null) {
            return "redirect:/";
        }

        hospitalBanco.setTelefone(hospital.getTelefone());
        hospitalBanco.setCnpj(hospital.getCnpj());
        hospitalBanco.setNomeHospital(hospital.getNomeHospital());
        hospitalBanco.setEndereco(hospital.getEndereco());

        service.atualizarHospital(hospitalBanco);

        usuario.setNome(nome);
        usuario.setEmail(email);

        usuarioService.atualizarUsuario(usuario);

        session.setAttribute("usuarioLogado", usuario);

        return "redirect:/dashboard";
    }
}