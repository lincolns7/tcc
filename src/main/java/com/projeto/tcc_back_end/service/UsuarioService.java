/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.PlantaoBean;
import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.repository.UsuarioDAO;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author TI Paraná
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioDAO repository;

    /**
     * Valida as credenciais informadas.
     * Retorna o UsuarioBean encontrado no banco se e-mail e senha conferem,
     * ou null caso contrário.
     */
    public UsuarioBean logar(UsuarioBean usuario) {

        Optional<UsuarioBean> encontrado = repository.findByEmail(usuario.getEmail());

        if (encontrado.isPresent() && encontrado.get().getSenha().equals(usuario.getSenha())) {
            return encontrado.get();
        }

        return null;
    }

    public void cadastrarUsuario(UsuarioBean usuario) {

        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome obrigatório.");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail obrigatório.");
        }

        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha obrigatória.");
        }

        if (repository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse e-mail.");
        }

        repository.save(usuario);
    }

}