/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.repository;

import com.projeto.tcc_back_end.model.UsuarioBean;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author TI Paraná
 */
@Repository
public interface UsuarioDAO extends JpaRepository<UsuarioBean, Integer> {

    Optional<UsuarioBean> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UsuarioBean> findByTipo(String tipo);
}