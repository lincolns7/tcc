/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.PlantaoBean;
import com.projeto.tcc_back_end.repository.PlantaoDAO;
import java.util.List;
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

    public PlantaoBean buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void cadastrarPlantao(PlantaoBean plantao) {

        if (plantao.getTitulo() == null || plantao.getTitulo().trim().isEmpty()) {
            throw new IllegalArgumentException("O título é obrigatório.");
        }

        if (plantao.getEspecialidade() == null || plantao.getEspecialidade().trim().isEmpty()) {
            throw new IllegalArgumentException("A especialidade é obrigatória.");
        }

        if (plantao.getData() == null) {
            throw new IllegalArgumentException("A data é obrigatória.");
        }

        if (plantao.getHorario() == null) {
            throw new IllegalArgumentException("O horário é obrigatório.");
        }

        if (plantao.getValor() == null || plantao.getValor() <= 0) {
            throw new IllegalArgumentException("Informe um valor válido.");
        }

        if (plantao.getStatus() == null || plantao.getStatus().trim().isEmpty()) {
            plantao.setStatus("ABERTO");
        }

        repository.save(plantao);
    }

    public void atualizarPlantao(PlantaoBean plantao) {
        repository.save(plantao);
    }

    public void excluirPlantao(Integer id) {
        repository.deleteById(id);
    }
}
