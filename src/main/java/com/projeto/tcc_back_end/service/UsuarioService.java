/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.PlantaoBean;
import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.repository.UsuarioDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author TI Paraná
 */
@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioDAO repository;
    
    public String logar(UsuarioBean usuario){

    //vAlidar

    return "iniciomedicos";

}

}