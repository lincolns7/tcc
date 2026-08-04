/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.projeto.tcc_back_end.repository;

import com.projeto.tcc_back_end.model.HospitalBean;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Aluno
 */
public interface HospitalDAO extends JpaRepository<HospitalBean, Integer> {

    HospitalBean findByCnpj(String cnpj);

    HospitalBean findByNomeHospital(String nomeHospital);

}
