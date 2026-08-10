/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.CandidaturaBean;
import com.projeto.tcc_back_end.model.MedicoBean;
import com.projeto.tcc_back_end.model.PlantaoBean;
import com.projeto.tcc_back_end.repository.CandidaturaDAO;
import java.util.List;
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

        MedicoBean medico = medicoService.buscarPorId(medicoId);

        if (medico == null) {
            throw new IllegalArgumentException(
                    "Médico não encontrado."
            );
       }

       if (repository.existsByPlantaoAndMedico(
               plantao,medico)) {

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


     public List<CandidaturaBean> listarPorMedico(
            MedicoBean medico) {

        return repository.findByMedico(medico);
    }
     
    public void cancelarCandidatura(
        int candidaturaId,
            MedicoBean medico) {
    CandidaturaBean candidatura =
            repository.findById(candidaturaId).orElse(null);

    if (candidatura == null) {

        throw new IllegalArgumentException(
                "Candidatura não encontrada."
        );
    }
    if (!candidatura.getMedico().getId()
            .equals(medico.getId())) {

        throw new IllegalArgumentException(
                "Você não pode cancelar esta candidatura."
        );
    }
    if ("ACEITA".equals(candidatura.getStatus())) {

        throw new IllegalArgumentException(
                "Uma candidatura aceita não pode ser cancelada."
        );
    }
    repository.delete(candidatura);
}
}
