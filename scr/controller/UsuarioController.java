package scr.controller;

import java.util.Date;
import scr.model.Usuario;
import scr.service.UsuarioService;

public class UsuarioController {

    private final UsuarioService usuarioService = new UsuarioService();

    public boolean registrarUsuario(String nome, String email, String senha, Date dataNascimento) {
        return usuarioService.registrarUsuario(nome, email, senha, dataNascimento);
    }

    public Usuario login(String email, String senha) {
        return usuarioService.login(email, senha);
    }

    public Usuario buscarUsuarioLogado() {
        return usuarioService.buscarUsuarioLogado();
    }

    public boolean editarUsuario(Usuario usuario) {
        return usuarioService.editarUsuario(usuario);
    }

    public boolean excluirContaLogada() {
        return usuarioService.excluirContaLogada();
    }

    public void visualizarUsuario(Usuario usuario) {
        usuarioService.visualizarUsuario(usuario);
    }

    public String listarRendasDespesasPorPeriodo(String inicio, String fim) {
        return usuarioService.listarRendasDespesasPorPeriodo(inicio, fim);
    }
}
