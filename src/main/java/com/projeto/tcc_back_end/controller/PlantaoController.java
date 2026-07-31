/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.PlantaoBean;
import com.projeto.tcc_back_end.service.PlantaoService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author TI Paraná
 */
@Controller
public class PlantaoController {
    @Autowired
    private PlantaoService plantaoService;

    @GetMapping("/iniciomedicos")
    public String index(Model model){

    model.addAttribute("plantoes", plantaoService.listarPlantoes());

        return "/iniciomedicos";
    }
    
    @GetMapping("/iniciomedicos/plantao-{id}")
    public String verplantao(@PathVariable int id, Model model){
        Optional<PlantaoBean> detalhes = plantaoService.buscarPorId(id);
        return "detalhes";
    }
    }
