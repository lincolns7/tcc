/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.projeto.tcc_back_end.repository;

import com.projeto.tcc_back_end.model.CandidaturaBean;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aluno
 */
@Repository
public interface CandidaturaDAO extends JpaRepository<CandidaturaBean, Integer> {

    List<CandidaturaBean> findByMedicoId(Integer medicoId);

    List<CandidaturaBean> findByPlantaoId(Integer plantaoId);

    boolean existsByPlantaoIdAndMedicoId(Integer plantaoId, Integer medicoId);
}
