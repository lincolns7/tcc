/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.HospitalBean;
import com.projeto.tcc_back_end.repository.HospitalDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aluno
 */
@Service
public class HospitalService {

    @Autowired
    private HospitalDAO repository;

    public List<HospitalBean> listarHospitais() {
        return repository.findAll();
    }

    public HospitalBean buscarPorId(Integer id) {
        return repository.findById(id).orElse(null);
    }

    /** Busca o hospital vinculado ao usuario logado. */
    public HospitalBean buscarPorUsuario(Integer usuarioId) {
        return repository.findByUsuarioId(usuarioId).orElse(null);
    }

    public void atualizarHospital(HospitalBean hospital) {

        if (hospital.getNomeHospital() == null || hospital.getNomeHospital().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do hospital obrigatorio.");
        }

        if (hospital.getCnpj() == null || hospital.getCnpj().trim().isEmpty()) {
            throw new IllegalArgumentException("CNPJ obrigatorio.");
        }

        repository.save(hospital);
    }

    public void excluirHospital(Integer id) {
        repository.deleteById(id);
    }
}
