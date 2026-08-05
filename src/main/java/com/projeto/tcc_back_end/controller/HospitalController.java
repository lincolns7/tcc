/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author TI Paraná
 */
@Controller
public class HospitalController {

    @Autowired
    private HospitalService service;

    @GetMapping("/cadastrohospital")
    public String cadastroHospital(){
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
            @RequestParam String endereco){

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

}