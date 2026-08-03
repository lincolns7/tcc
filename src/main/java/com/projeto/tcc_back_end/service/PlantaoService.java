/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.PlantaoBean;
import com.projeto.tcc_back_end.repository.PlantaoDAO;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aluno
 */
@Service
public class PlantaoService {

    @Autowired
    private PlantaoDAO repository;

    public List<PlantaoBean> listarPlantoes() {
        return repository.findAll();
    }

    /** Dashboard do medico: somente plantoes abertos. */
    public List<PlantaoBean> listarAbertos() {
        return repository.findByStatusOrderByDataAsc("ABERTO");
    }

    /** Dashboard do hospital: somente os plantoes daquele hospital. */
    public List<PlantaoBean> listarPorHospital(Integer hospitalId) {
        return repository.findByHospitalIdOrderByDataAsc(hospitalId);
    }

    public Optional<PlantaoBean> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public void salvar(PlantaoBean plantao) {

        if (plantao.getHospital() == null) {
            throw new IllegalArgumentException("Plantao precisa estar vinculado a um hospital.");
        }

        if (plantao.getTitulo() == null || plantao.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("O titulo e obrigatorio.");
        }

        if (plantao.getEspecialidade() == null || plantao.getEspecialidade().trim().isEmpty()) {
            throw new IllegalArgumentException("A especialidade e obrigatoria.");
        }

        if (plantao.getData() == null) {
            throw new IllegalArgumentException("A data e obrigatoria.");
        }

        if (plantao.getHorario() == null) {
            throw new IllegalArgumentException("O horario e obrigatorio.");
        }

        if (plantao.getValor() == null || plantao.getValor() <= 0) {
            throw new IllegalArgumentException("Informe um valor valido.");
        }

        if (plantao.getStatus() == null || plantao.getStatus().trim().isEmpty()) {
            plantao.setStatus("ABERTO");
        }

        repository.save(plantao);
    }

    public void excluirPlantao(Integer id) {
        repository.deleteById(id);
    }

    public void marcarComoPreenchido(PlantaoBean plantao) {
        plantao.setStatus("PREENCHIDO");
        repository.save(plantao);
    }
}
