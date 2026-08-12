/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.MedicoBean;
import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.repository.MedicoDAO;
import com.projeto.tcc_back_end.repository.UsuarioDAO;
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

    public void cadastrarMedico(String nome, String email, String senha, String telefone, String crm, String especialidade) {

        if(nome == null || nome.trim().isEmpty()){
            throw new IllegalArgumentException("Nome obrigatório.");
        }

        if(email == null || email.trim().isEmpty()){
    throw new IllegalArgumentException("E-mail obrigatório.");
}

email = email.trim();

if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
    throw new IllegalArgumentException("Informe um e-mail válido.");
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

        crm = crm.trim();

        if(!crm.matches("\\d+")){
            throw new IllegalArgumentException("O CRM deve conter apenas números.");
        }

        if(crm.length() < 4 || crm.length() > 10){
            throw new IllegalArgumentException("Informe um CRM válido.");
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

        String telefoneNumeros = telefone.replaceAll("\\D", "");

        if(telefoneNumeros.length() != 10 && telefoneNumeros.length() != 11){
            throw new IllegalArgumentException("Informe um telefone válido.");
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

    String telefoneNumeros = medico.getTelefone().replaceAll("\\D", "");

    if(telefoneNumeros.length() != 10 && telefoneNumeros.length() != 11){
        throw new IllegalArgumentException("Informe um telefone válido.");
    }

    if(medico.getCrm() == null || medico.getCrm().trim().isEmpty()){
        throw new IllegalArgumentException("CRM obrigatório.");
    }

    String crmNumeros = medico.getCrm().replaceAll("\\D", "");

    if(crmNumeros.length() != 6){
        throw new IllegalArgumentException("Informe um CRM válido.");
    }

    if(medico.getEspecialidade() == null || medico.getEspecialidade().trim().isEmpty()){
        throw new IllegalArgumentException("Especialidade obrigatória.");
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