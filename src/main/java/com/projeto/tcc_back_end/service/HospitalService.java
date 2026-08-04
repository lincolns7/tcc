/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.service;

import com.projeto.tcc_back_end.model.HospitalBean;
import com.projeto.tcc_back_end.model.UsuarioBean;
import com.projeto.tcc_back_end.repository.HospitalDAO;
import com.projeto.tcc_back_end.repository.UsuarioDAO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Aluno
 */
@Service
public class HospitalService {

    @Autowired
    private HospitalDAO hospitalDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    public List<HospitalBean> listarHospitais() {

        return hospitalDAO.findAll();

    }

    public HospitalBean buscarPorId(Integer id) {

        return hospitalDAO.findById(id).orElse(null);

    }

    public void cadastrarHospital(String nome,
                                  String email,
                                  String senha,
                                  String nomeHospital,
                                  String cnpj,
                                  String telefone,
                                  String endereco) {

        UsuarioBean usuario = new UsuarioBean();

        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setTipo("HOSPITAL");

        usuarioDAO.save(usuario);

        HospitalBean hospital = new HospitalBean();

        hospital.setUsuario(usuario);
        hospital.setNomeHospital(nomeHospital);
        hospital.setCnpj(cnpj);
        hospital.setTelefone(telefone);
        hospital.setEndereco(endereco);

        hospitalDAO.save(hospital);

    }

    public void atualizarHospital(HospitalBean hospital) {

        hospitalDAO.save(hospital);

    }

    public void excluirHospital(Integer id) {

        hospitalDAO.deleteById(id);

    }

}
