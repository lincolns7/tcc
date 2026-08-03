/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

/**
 *
 * @author TI Paraná
 */
@Entity
@Table(name = "candidatura")
public class CandidaturaBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "plantao_id", nullable = false)
    private PlantaoBean plantao;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private MedicoBean medico;

    private String status;

    @jakarta.persistence.Column(name = "data_candidatura")
    private LocalDateTime dataCandidatura;

    public CandidaturaBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PlantaoBean getPlantao() {
        return plantao;
    }

    public void setPlantao(PlantaoBean plantao) {
        this.plantao = plantao;
    }

    public MedicoBean getMedico() {
        return medico;
    }

    public void setMedico(MedicoBean medico) {
        this.medico = medico;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataCandidatura() {
        return dataCandidatura;
    }

    public void setDataCandidatura(LocalDateTime dataCandidatura) {
        this.dataCandidatura = dataCandidatura;
    }
}
