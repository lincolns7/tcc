/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.CandidaturaBean;
import com.projeto.tcc_back_end.model.MedicoBean;
import com.projeto.tcc_back_end.model.PlantaoBean;
import com.projeto.tcc_back_end.repository.CandidaturaDAO;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
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

    @Transactional
    public void candidatar(PlantaoBean plantao, MedicoBean medico) {

        if (!"ABERTO".equalsIgnoreCase(plantao.getStatus())) {
            throw new IllegalArgumentException("Este plantao nao esta mais aberto.");
        }

        if (repository.existsByPlantaoIdAndMedicoId(plantao.getId(), medico.getId())) {
            throw new IllegalArgumentException("Voce ja se candidatou a este plantao.");
        }

        CandidaturaBean candidatura = new CandidaturaBean();
        candidatura.setPlantao(plantao);
        candidatura.setMedico(medico);
        candidatura.setStatus("PENDENTE");
        candidatura.setDataCandidatura(LocalDateTime.now());

        repository.save(candidatura);
    }

    public List<CandidaturaBean> listarPorMedico(Integer medicoId) {
        return repository.findByMedicoId(medicoId);
    }

    public List<CandidaturaBean> listarPorPlantao(Integer plantaoId) {
        return repository.findByPlantaoId(plantaoId);
    }

    public CandidaturaBean buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void aprovar(Integer candidaturaId) {

        CandidaturaBean candidatura = repository.findById(candidaturaId)
                .orElseThrow(() -> new IllegalArgumentException("Candidatura nao encontrada."));

        candidatura.setStatus("APROVADA");
        repository.save(candidatura);

        PlantaoBean plantao = candidatura.getPlantao();

        for (CandidaturaBean outra : repository.findByPlantaoId(plantao.getId())) {
            if (!outra.getId().equals(candidatura.getId()) && "PENDENTE".equals(outra.getStatus())) {
                outra.setStatus("REJEITADA");
                repository.save(outra);
            }
        }

        plantaoService.marcarComoPreenchido(plantao);
    }

    public void rejeitar(Integer candidaturaId) {

        CandidaturaBean candidatura = repository.findById(candidaturaId)
                .orElseThrow(() -> new IllegalArgumentException("Candidatura nao encontrada."));

        candidatura.setStatus("REJEITADA");
        repository.save(candidatura);
    }
}
