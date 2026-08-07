/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.CandidaturaBean;
import com.projeto.tcc_back_end.model.MedicoBean;
import com.projeto.tcc_back_end.model.PlantaoBean;
import com.projeto.tcc_back_end.repository.CandidaturaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aluno
 */
@Service
public class CandidaturaService {

    @Autowired
    private CandidaturaDAO repository;

    @Autowired
    private PlantaoService plantaoService;

    @Autowired
    private MedicoService medicoService;

    public void candidatar(Integer plantaoId, Integer medicoId) {

        PlantaoBean plantao =
                plantaoService.buscarPorId(plantaoId);

        if (plantao == null) {
            throw new IllegalArgumentException(
                    "Plantão não encontrado."
            );
        }

        if (!"ABERTO".equals(plantao.getStatus())) {
            throw new IllegalArgumentException(
                    "Este plantão não está mais aberto."
            );
        }

        MedicoBean medico =
                medicoService.buscarPorId(medicoId);

        if (medico == null) {
            throw new IllegalArgumentException(
                    "Médico não encontrado."
            );
        }

        if (repository.existsByPlantaoAndMedico(
                plantao,
                medico)) {

            throw new IllegalArgumentException(
                    "Você já se candidatou a este plantão."
            );
        }

        CandidaturaBean candidatura =
                new CandidaturaBean();

        candidatura.setPlantao(plantao);
        candidatura.setMedico(medico);
        candidatura.setStatus("PENDENTE");

        repository.save(candidatura);
    }
}
