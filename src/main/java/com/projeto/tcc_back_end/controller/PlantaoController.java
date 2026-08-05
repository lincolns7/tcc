/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.HospitalBean;
import com.projeto.tcc_back_end.model.PlantaoBean;
import com.projeto.tcc_back_end.service.HospitalService;
import com.projeto.tcc_back_end.service.PlantaoService;
import java.util.Optional;
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

    @GetMapping("/cadastroplantao")
    public String telaCadastro() {

        return "cadastroplantao";

    }

    @PostMapping("/cadastroplantao")
public String cadastrar(@ModelAttribute PlantaoBean plantao) {

    plantao.setHospital_id(hospitalService.buscarPorId(1));

    plantao.setStatus("ABERTO");

    service.cadastrarPlantao(plantao);

    return "redirect:/iniciohospital";
}

    @GetMapping("/plantoes")
    public String listar(Model model) {

        model.addAttribute("plantoes", service.listarDisponiveis());

        return "plantoes";

    }
    
}