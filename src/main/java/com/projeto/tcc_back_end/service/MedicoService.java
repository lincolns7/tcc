/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.MedicoBean;
import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.repository.MedicoDAO;
import com.projeto.tcc_back_end.repository.UsuarioDAO;
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

    @Autowired
    private UsuarioDAO usuarioDAO;

    public void cadastrarMedico(String nome,String email,String senha,String telefone,String crm,String especialidade){

    
        if(nome == null || nome.trim().isEmpty()){
            throw new IllegalArgumentException("Nome obrigatório.");
        }

        if(email == null || email.trim().isEmpty()){
            throw new IllegalArgumentException("E-mail obrigatório.");
        }

        if(usuarioDAO.findByEmail(email) != null){
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        if(senha == null || senha.trim().isEmpty()){
            throw new IllegalArgumentException("Senha obrigatória.");
        }

        if(senha.length() < 6){
            throw new IllegalArgumentException("A senha deve possuir no mínimo 6 caracteres.");
        }

        if(crm == null || crm.trim().isEmpty()){
            throw new IllegalArgumentException("CRM obrigatório.");
        }

        if(repository.findByCrm(crm) != null){
            throw new IllegalArgumentException("CRM já cadastrado.");
        }

        if(especialidade == null || especialidade.trim().isEmpty()){
            throw new IllegalArgumentException("Especialidade obrigatória.");
        }

        if(telefone == null || telefone.trim().isEmpty()){
            throw new IllegalArgumentException("Telefone obrigatório.");
        }

        UsuarioBean usuario = new UsuarioBean();

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setTipo("MEDICO");

        usuarioDAO.save(usuario);


        MedicoBean medico = new MedicoBean();

        medico.setUsuario(usuario);
        medico.setCrm(crm);
        medico.setTelefone(telefone);
        medico.setEspecialidade(especialidade);

        repository.save(medico);

    }

    public MedicoBean buscarPorId(Integer id){

        return repository.findById(id).orElse(null);

    }

    public void atualizarMedico(MedicoBean medico){

        if(medico.getId() == null){
            throw new IllegalArgumentException("Médico inválido.");
        }

        if(medico.getTelefone() == null || medico.getTelefone().trim().isEmpty()){
            throw new IllegalArgumentException("Telefone obrigatório.");
        }

        repository.save(medico);

    }

    public void excluirMedico(Integer id){

        if(repository.findById(id).isEmpty()){
            throw new IllegalArgumentException("Médico não encontrado.");
        }

        repository.deleteById(id);

    }
    
    public MedicoBean buscarPorUsuario(UsuarioBean usuario) {

    if (usuario == null) {
        return null;
    }

    return repository.findByUsuario(usuario);
    }

}
