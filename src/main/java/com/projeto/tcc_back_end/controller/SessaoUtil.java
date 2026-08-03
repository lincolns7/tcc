/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.projeto.tcc_back_end.controller;

import com.projeto.tcc_back_end.model.UsuarioBean;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Aluno
 */
public final class SessaoUtil {

    public static final String USUARIO = "usuarioLogado";

    private SessaoUtil() {
    }

    public static void logar(HttpSession session, UsuarioBean usuario) {
        session.setAttribute(USUARIO, usuario);
    }

    public static UsuarioBean logado(HttpSession session) {
        return (UsuarioBean) session.getAttribute(USUARIO);
    }

    public static boolean ehTipo(HttpSession session, String tipo) {
        UsuarioBean u = logado(session);
        return u != null && u.getTipo() != null && u.getTipo().equalsIgnoreCase(tipo);
    }
}

