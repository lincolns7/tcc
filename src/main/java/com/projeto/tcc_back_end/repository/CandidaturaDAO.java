/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.projeto.tcc_back_end.repository;

import com.projeto.tcc_back_end.model.CandidaturaBean;
import com.projeto.tcc_back_end.model.HospitalBean;
import com.projeto.tcc_back_end.model.MedicoBean;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidaturaDAO extends JpaRepository<CandidaturaBean, Integer> {

    List<CandidaturaBean> findByMedico(MedicoBean medico);

    List<CandidaturaBean> findBybuscarPorHospital(HospitalBean hospital);

}
