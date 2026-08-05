/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.MedicoBean;
import com.projeto.tcc_back_end.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/cadastromedico")
    public String cadastroMedico(){

        return "cadastromedico";
    }
    @PostMapping("/cadastromedico")
    public String cadastrar(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String telefone,
            @RequestParam String senha,
            @RequestParam String crm,
            @RequestParam String especialidade){

        service.cadastrarMedico(
                nome,
                email,
                senha,
                telefone,
                crm,
                especialidade);
        return "redirect:/login";
    }

}
