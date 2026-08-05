/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.repository.UsuarioDAO;
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

    public UsuarioBean logar(UsuarioBean usuario) {

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("E-mail obrigatório.");
        }

        if (usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
            throw new IllegalArgumentException("Senha obrigatória.");
        }

        UsuarioBean usuarioBanco = repository.findByEmail(usuario.getEmail());

        if (usuarioBanco == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        if (!usuarioBanco.getSenha().equals(usuario.getSenha())) {
            throw new IllegalArgumentException("Senha incorreta.");
        }

        return usuarioBanco;
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

        if (usuario.getSenha().length() < 6) {
            throw new IllegalArgumentException("A senha deve possuir no mínimo 6 caracteres.");
        }

        if (usuario.getTipo() == null || usuario.getTipo().trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo de usuário obrigatório.");
        }

        if (repository.findByEmail(usuario.getEmail()) != null) {
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse e-mail.");
        }

        repository.save(usuario);
    }

    public UsuarioBean buscarPorId(Integer id) {

        return repository.findById(id).orElse(null);

    }

    public void atualizarUsuario(UsuarioBean usuario) {

        if (usuario.getId() == null) {
            throw new IllegalArgumentException("Usuário inválido.");
        }

        repository.save(usuario);

    }

    public void excluirUsuario(Integer id) {

        if (repository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        repository.deleteById(id);

    }

}