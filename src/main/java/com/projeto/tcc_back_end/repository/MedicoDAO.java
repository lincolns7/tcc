/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.projeto.tcc_back_end.repository;

import com.projeto.tcc_back_end.model.MedicoBean;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Aluno
 */
@Repository
public interface MedicoDAO extends JpaRepository<MedicoBean, Integer> {

    Optional<MedicoBean> findByUsuarioId(Integer usuarioId);

    boolean existsByCrm(String crm);
}
