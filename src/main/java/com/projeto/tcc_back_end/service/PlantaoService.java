/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.HospitalBean;
import com.projeto.tcc_back_end.model.PlantaoBean;
import com.projeto.tcc_back_end.repository.PlantaoDAO;
import java.time.LocalDate;
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

    public List<PlantaoBean> listarPlantoes(){

        return repository.findAll();

    }

    public List<PlantaoBean> listarDisponiveis(){

        return repository.findByStatus("ABERTO");

    }

    public PlantaoBean buscarPorId(Integer id){

        return repository.findById(id).orElse(null);

    }

    public void cadastrarPlantao(PlantaoBean plantao){

        if(plantao.getTitulo() == null || plantao.getTitulo().trim().isEmpty()){
            throw new IllegalArgumentException("Título obrigatório.");
        }

        if(plantao.getEspecialidade() == null || plantao.getEspecialidade().trim().isEmpty()){
            throw new IllegalArgumentException("Especialidade obrigatória.");
        }

        if(plantao.getData() == null){
            throw new IllegalArgumentException("Data obrigatória.");
        }

        if(plantao.getData().isBefore(LocalDate.now())){
            throw new IllegalArgumentException("A data não pode ser anterior ao dia atual.");
        }

        if(plantao.getHorario() == null){
            throw new IllegalArgumentException("Horário obrigatório.");
        }

        if(plantao.getValor() == null){
            throw new IllegalArgumentException("Valor obrigatório.");
        }

        if (plantao.getValor() <= 0) {
        throw new IllegalArgumentException("Valor deve ser maior que zero.");
    }

        if(plantao.getStatus() == null || plantao.getStatus().trim().isEmpty()){
            throw new IllegalArgumentException("Status obrigatório.");
        }

        if(plantao.getHospital_id()== null){
            throw new IllegalArgumentException("Hospital obrigatório.");
        }

        repository.save(plantao);

    }

    public void atualizarPlantao(PlantaoBean plantao){

    if (plantao.getValor() <= 0) {
        throw new IllegalArgumentException("Valor deve ser maior que zero.");
    }
    repository.save(plantao);
    }

    public void excluirPlantao(Integer id){

        if(repository.findById(id).isEmpty()){
            throw new IllegalArgumentException("Plantão não encontrado.");
        }
        repository.deleteById(id);

    }
    public List<PlantaoBean> listarPorHospital(HospitalBean hospital) {

    if (hospital == null) {
        throw new IllegalArgumentException("Hospital inválido.");
    }
    return repository.buscarPorHospital(hospital);
}
}
