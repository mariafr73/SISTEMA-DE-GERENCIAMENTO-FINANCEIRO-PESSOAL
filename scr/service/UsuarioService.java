package scr.service;

import scr.dao.DespesaDAO;
import scr.dao.RendaDAO;
import scr.dao.UsuarioDAO;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import scr.model.Despesa;
import scr.model.Sessao;
import scr.model.Usuario;
import scr.model.UtilData;

import org.mindrot.jbcrypt.BCrypt;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public boolean registrarUsuario(String nome, String email, String senhaPura, Date dataNascimento) {
        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Erro: nome obrigatório.");
            return false;
        }

        if (email == null || email.trim().isEmpty()) {
            System.out.println("Erro: email obrigatório.");
            return false;
        }

        if (senhaPura == null || senhaPura.trim().isEmpty()) {
            System.out.println("Erro: senha obrigatória.");
            return false;
        }

        if (dataNascimento == null) {
            System.out.println("Erro: data de nascimento inválida.");
            return false;
        }

        String senhaHash = BCrypt.hashpw(senhaPura, BCrypt.gensalt());

        Usuario novoUsuario = new Usuario();
        novoUsuario.setIdUsuario(UUID.randomUUID().toString());
        novoUsuario.setNome(nome);
        novoUsuario.setEmail(email);
        novoUsuario.setSenha(senhaHash);
        novoUsuario.setDataNascimento(dataNascimento);

        return usuarioDAO.registrarUsuario(novoUsuario);
    }

    public Usuario login(String email, String senhaPura) {
        Usuario usuarioDB = usuarioDAO.buscarPorEmail(email);

        if (usuarioDB == null) {
            System.out.println("Erro de Login: Usuário não encontrado.");
            return null;
        }

        String hashSalvo = usuarioDB.getSenha();

        if (BCrypt.checkpw(senhaPura, hashSalvo)) {
            usuarioDB.setSenha(null);
            Sessao.logar(usuarioDB.getIdUsuario(), usuarioDB.getNome());
            System.out.println("Login bem-sucedido! Bem-vindo(a), " + usuarioDB.getNome());
            return usuarioDB;
        }

        System.out.println("Erro de Login: Senha incorreta.");
        return null;
    }

    public Usuario buscarUsuarioLogado() {
        String idLogado = Sessao.getIdUsuarioLogado();

        if (idLogado == null) {
            return null;
        }

        Usuario usuario = usuarioDAO.buscarPorId(idLogado);

        if (usuario == null) {
            System.out.println("Erro crítico: Usuário logado não encontrado no banco.");
            Sessao.deslogar();
        }

        return usuario;
    }

    public boolean editarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getIdUsuario() == null || usuario.getIdUsuario().isEmpty()) {
            System.err.println("Erro: ID do usuário é necessário para edição.");
            return false;
        }

        boolean sucesso = usuarioDAO.atualizarUsuario(usuario);

        if (sucesso) {
            System.out.println("Perfil atualizado com sucesso!");
        } else {
            System.err.println("Falha ao atualizar perfil. Verifique o ID e o email.");
        }

        return sucesso;
    }

    public boolean excluirContaLogada() {
        Usuario usuario = buscarUsuarioLogado();

        if (usuario == null) {
            return false;
        }

        boolean sucesso = usuarioDAO.excluir(usuario.getIdUsuario());

        if (sucesso) {
            Sessao.deslogar();
        }

        return sucesso;
    }

    public void visualizarUsuario(Usuario usuario) {
        if (usuario == null) {
            System.out.println("Erro: Usuário não informado para visualização.");
            return;
        }

        System.out.println("\n===== SEU PERFIL =====");
        System.out.println("ID: " + usuario.getIdUsuario());
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("Email: " + usuario.getEmail());

        if (usuario.getDataNascimento() != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
            System.out.println("Data de Nascimento: " + sdf.format(usuario.getDataNascimento()));
        } else {
            System.out.println("Data de Nascimento: Não informada");
        }

        System.out.println("=======================\n");
    }

    public String listarRendasDespesasPorPeriodo(String inicio, String fim) {
        Usuario usuario = buscarUsuarioLogado();

        if (usuario == null) {
            return "Usuário não está logado.";
        }

        Date dataInicio = UtilData.parseDataUsuario(inicio);
        Date dataFim = UtilData.parseDataUsuario(fim);

        if (dataInicio == null || dataFim == null) {
            return "Datas inválidas. Use o formato dd/MM/yyyy.";
        }

        String idUsuario = usuario.getIdUsuario();

        List<Despesa> despesas = new DespesaDAO().listarDespesasPorPeriodo(idUsuario, dataInicio, dataFim);

        double totalDespesas = 0;
        for (Despesa d : despesas) {
            totalDespesas += d.getValor();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(dataInicio);

        int mes = cal.get(Calendar.MONTH) + 1;
        int ano = cal.get(Calendar.YEAR);

        double totalRendas = new RendaDAO().calcularRendaTotalMensal(mes, ano, idUsuario);

        double saldo = totalRendas - totalDespesas;

        StringBuilder relatorio = new StringBuilder();
        relatorio.append("\n===== RELATÓRIO FINANCEIRO =====\n");
        relatorio.append("Período: ").append(inicio).append(" até ").append(fim).append("\n\n");
        relatorio.append("Total de Rendas (mês): R$ ").append(totalRendas).append("\n");
        relatorio.append("Total de Despesas (período): R$ ").append(totalDespesas).append("\n");
        relatorio.append("Saldo do Período: R$ ").append(saldo).append("\n");
        relatorio.append("\n===============================\n");

        return relatorio.toString();
    }
}