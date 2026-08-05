/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.HospitalBean;
import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.repository.HospitalDAO;
import com.projeto.tcc_back_end.repository.UsuarioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aluno
 */
@Service
public class HospitalService {

    @Autowired
    private HospitalDAO repository;

    @Autowired
    private HospitalService hospitalService;
    
    @Autowired
    private UsuarioDAO usuarioDAO;

    public void cadastrarHospital(String nome,String email,String senha,String telefone,String cnpj,String nomeHospital,String endereco){

        if(nome == null || nome.trim().isEmpty()){
            throw new IllegalArgumentException("Nome do responsável obrigatório.");
        }

        if(email == null || email.trim().isEmpty()){
            throw new IllegalArgumentException("E-mail obrigatório.");
        }

        if(usuarioDAO.findByEmail(email) != null){
            throw new IllegalArgumentException("Já existe um usuário com esse e-mail.");
        }

        if(senha == null || senha.trim().isEmpty()){
            throw new IllegalArgumentException("Senha obrigatória.");
        }

        if(senha.length() < 6){
            throw new IllegalArgumentException("A senha deve possuir no mínimo 6 caracteres.");
        }


        if(telefone == null || telefone.trim().isEmpty()){
            throw new IllegalArgumentException("Telefone obrigatório.");
        }

        if(cnpj == null || cnpj.trim().isEmpty()){
            throw new IllegalArgumentException("CNPJ obrigatório.");
        }

        if(repository.findByCnpj(cnpj) != null){
            throw new IllegalArgumentException("Já existe um hospital com esse CNPJ.");
        }

        if(nomeHospital == null || nomeHospital.trim().isEmpty()){
            throw new IllegalArgumentException("Nome do hospital obrigatório.");
        }

        if(endereco == null || endereco.trim().isEmpty()){
            throw new IllegalArgumentException("Endereço obrigatório.");
        }


        UsuarioBean usuario = new UsuarioBean();

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setTipo("HOSPITAL");

        usuarioDAO.save(usuario);


        HospitalBean hospital = new HospitalBean();

        hospital.setUsuario(usuario);
        hospital.setTelefone(telefone);
        hospital.setCnpj(cnpj);
        hospital.setNomeHospital(nomeHospital);
        hospital.setEndereco(endereco);

        repository.save(hospital);

    }

    public HospitalBean buscarPorId(Integer id){

        return repository.findById(id).orElse(null);

    }

    public void atualizarHospital(HospitalBean hospital){

        if(hospital.getId() == null){
            throw new IllegalArgumentException("Hospital inválido.");
        }

        if(hospital.getTelefone() == null || hospital.getTelefone().trim().isEmpty()){
            throw new IllegalArgumentException("Telefone obrigatório.");
        }

        if(hospital.getEndereco() == null || hospital.getEndereco().trim().isEmpty()){
            throw new IllegalArgumentException("Endereço obrigatório.");
        }

        repository.save(hospital);

    }

    public void excluirHospital(Integer id){

        if(repository.findById(id).isEmpty()){
            throw new IllegalArgumentException("Hospital não encontrado.");
        }

        repository.deleteById(id);

    }

}
