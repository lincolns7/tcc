/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.CandidaturaBean;
import com.projeto.tcc_back_end.model.HospitalBean;
import com.projeto.tcc_back_end.model.MedicoBean;
import com.projeto.tcc_back_end.model.PlantaoBean;
import com.projeto.tcc_back_end.repository.CandidaturaDAO;
import com.projeto.tcc_back_end.repository.MedicoDAO;
import com.projeto.tcc_back_end.repository.PlantaoDAO;
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
    private PlantaoDAO plantaoDAO;

    @Autowired
    private MedicoDAO medicoDAO;

    public void candidatar(Integer plantaoId, Integer medicoId) {

        PlantaoBean plantao = plantaoDAO.findById(plantaoId).orElse(null);

        if (plantao == null) {
            throw new IllegalArgumentException("Plantão não encontrado.");
        }

        if (!"ABERTO".equals(plantao.getStatus())) {
    throw new IllegalArgumentException("Este plantão não está disponível.");
}

if (plantao.getData() == null) {
    throw new IllegalArgumentException("A data do plantão é obrigatória.");
}

if (plantao.getData().isBefore(java.time.LocalDate.now())) {
    throw new IllegalArgumentException(
            "Não é possível se candidatar a um plantão que já passou."
    );
}

        MedicoBean medico = medicoDAO.findById(medicoId).orElse(null);

        if (medico == null) {
            throw new IllegalArgumentException("Médico não encontrado.");
        }

        if (!medico.getEspecialidade().equalsIgnoreCase(plantao.getEspecialidade())) {
            throw new IllegalArgumentException("Sua especialidade não é compatível com este plantão.");
        }

        List<CandidaturaBean> candidaturas = repository.findByMedico(medico);

        for (CandidaturaBean candidatura : candidaturas) {

            if (candidatura.getPlantao().getId().equals(plantaoId)) {

                if ("PENDENTE".equals(candidatura.getStatus())) {
                    throw new IllegalArgumentException("Você já está candidatado a este plantão.");
                }

                if ("ACEITA".equals(candidatura.getStatus())) {
                    throw new IllegalArgumentException("Você já foi aceito neste plantão.");
                }
            }
        }

        CandidaturaBean candidatura = new CandidaturaBean();

        candidatura.setPlantao(plantao);
        candidatura.setMedico(medico);
        candidatura.setStatus("PENDENTE");

        repository.save(candidatura);
    }

    public List<CandidaturaBean> listarPorMedico(MedicoBean medico) {

        return repository.findByMedico(medico);
    }

    public List<CandidaturaBean> listarSolicitacoes(HospitalBean hospital) {

        List<CandidaturaBean> todas = repository.findAll();

        List<CandidaturaBean> solicitacoes = new java.util.ArrayList<>();

        for (CandidaturaBean candidatura : todas) {

            if (candidatura.getPlantao() != null &&
                candidatura.getPlantao().getHospital_id() != null &&
                candidatura.getPlantao().getHospital_id().getId().equals(hospital.getId())) {

                solicitacoes.add(candidatura);
            }
        }

        return solicitacoes;
    }

    public CandidaturaBean buscarPorId(Integer id) {

        return repository.findById(id).orElse(null);
    }

    public void aceitarCandidatura(Integer candidaturaId, HospitalBean hospital) {

        CandidaturaBean candidatura = repository.findById(candidaturaId).orElse(null);

        if (candidatura == null) {
            throw new IllegalArgumentException("Candidatura não encontrada.");
        }

        if (candidatura.getPlantao() == null) {
            throw new IllegalArgumentException("Plantão da candidatura não encontrado.");
        }

        HospitalBean hospitalDoPlantao = candidatura.getPlantao().getHospital_id();

        if (hospitalDoPlantao == null || !hospitalDoPlantao.getId().equals(hospital.getId())) {
            throw new IllegalArgumentException("Você não tem permissão para aceitar esta candidatura.");
        }

        if (!"PENDENTE".equals(candidatura.getStatus())) {
            throw new IllegalArgumentException("Esta candidatura não está pendente.");
        }

        List<CandidaturaBean> candidaturasDoMedico =
        repository.findByMedico(candidatura.getMedico());

for (CandidaturaBean outraCandidatura : candidaturasDoMedico) {

    if (outraCandidatura.getId().equals(candidatura.getId())) {
        continue;
    }

    if (!"ACEITA".equals(outraCandidatura.getStatus())) {
        continue;
    }

    if (outraCandidatura.getPlantao() == null) {
        continue;
    }

    PlantaoBean outroPlantao = outraCandidatura.getPlantao();
    PlantaoBean novoPlantao = candidatura.getPlantao();

    if (outroPlantao.getData().equals(novoPlantao.getData())
            && outroPlantao.getHorario().equals(novoPlantao.getHorario())) {

        throw new IllegalArgumentException(
                "Este médico já possui um plantão aceito neste mesmo horário."
        );
    }
}

        if (!"ABERTO".equals(candidatura.getPlantao().getStatus())) {
            throw new IllegalArgumentException("Este plantão não está mais disponível.");
        }

        List<CandidaturaBean> candidaturasDoPlantao = repository.findAll();

        for (CandidaturaBean outraCandidatura : candidaturasDoPlantao) {

            if (outraCandidatura.getPlantao() != null && outraCandidatura.getPlantao().getId().equals(candidatura.getPlantao().getId()) &&
                    "ACEITA".equals(outraCandidatura.getStatus())) {

                throw new IllegalArgumentException("Este plantão já possui um médico contratado.");
            }
        }

        candidatura.setStatus("ACEITA");

        candidatura.getPlantao().setStatus("FECHADO");

        repository.save(candidatura);

        plantaoDAO.save(candidatura.getPlantao());
    }

    public void recusarCandidatura(Integer candidaturaId, HospitalBean hospital) {

        CandidaturaBean candidatura = repository.findById(candidaturaId).orElse(null);

        if (candidatura == null) {
            throw new IllegalArgumentException("Candidatura não encontrada.");
        }

        if (candidatura.getPlantao() == null) {
            throw new IllegalArgumentException("Plantão da candidatura não encontrado.");
        }

        HospitalBean hospitalDoPlantao = candidatura.getPlantao().getHospital_id();

        if (hospitalDoPlantao == null || !hospitalDoPlantao.getId().equals(hospital.getId())) {
            throw new IllegalArgumentException("Você não tem permissão para recusar esta candidatura.");
        }

        if (!"PENDENTE".equals(candidatura.getStatus())) {
            throw new IllegalArgumentException("Esta candidatura não está pendente.");
        }

        candidatura.setStatus("RECUSADA");

        repository.save(candidatura);
    }

    public void cancelarCandidatura(Integer candidaturaId, MedicoBean medico) {

        CandidaturaBean candidatura = repository.findById(candidaturaId).orElse(null);

        if (candidatura == null) {
            throw new IllegalArgumentException("Candidatura não encontrada.");
        }

        if (candidatura.getMedico() == null || !candidatura.getMedico().getId().equals(medico.getId())) {
            throw new IllegalArgumentException("Você não pode cancelar esta candidatura.");
        }

        if ("ACEITA".equals(candidatura.getStatus())) {
            throw new IllegalArgumentException("Não é possível cancelar uma candidatura aceita.");
        }

        repository.delete(candidatura);
    }

    public List<CandidaturaBean> listarPlantaoAceitos(MedicoBean medico) {

        return repository.findByMedicoAndStatus(medico, "ACEITA");
    }
}