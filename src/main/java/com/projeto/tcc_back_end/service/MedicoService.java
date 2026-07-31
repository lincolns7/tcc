/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.MedicoBean;
import com.projeto.tcc_back_end.repository.MedicoDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aluno
 */
@Service
public class MedicoService {

    @Autowired
    private MedicoDAO repository;

    public List<MedicoBean> listarMedicos() {

        return repository.findAll();

    }

    public MedicoBean buscarPorId(Integer id) {

        return repository.findById(id).orElse(null);

    }

    public void cadastrarMedico(MedicoBean medico) {

        if (medico.getCrm() == null || medico.getCrm().trim().isEmpty()) {
            throw new IllegalArgumentException("CRM obrigatório.");
        }

        if (medico.getEspecialidade() == null || medico.getEspecialidade().trim().isEmpty()) {
            throw new IllegalArgumentException("Especialidade obrigatória.");
        }

        repository.save(medico);

    }
    public void atualizarMedico(MedicoBean medico) {

        repository.save(medico);

    }

    public void excluirMedico(Integer id) {

        repository.deleteById(id);

    }

}
