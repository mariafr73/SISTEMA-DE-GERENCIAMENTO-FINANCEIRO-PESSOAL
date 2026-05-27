package service;

import dao.RendaDAO;
import model.Renda;
import model.Sessao;

import java.util.Date;
import java.util.List;

public class RendaService {

    private final RendaDAO rendaDAO = new RendaDAO();

    public Renda cadastrarRenda(String nome, double valor, Date data, boolean tipo) {
        if (!Sessao.isLogado()) {
            System.out.println("Erro: nenhum usuário logado.");
            return null;
        }

        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Erro: nome da renda obrigatório.");
            return null;
        }

        if (valor <= 0) {
            System.out.println("Erro: valor deve ser maior que zero.");
            return null;
        }

        if (data == null) {
            System.out.println("Erro: data inválida.");
            return null;
        }

        Renda renda = new Renda(
            Sessao.getIdUsuarioLogado(),
            nome,
            valor,
            data,
            tipo
        );

        return rendaDAO.cadastrarRenda(renda);
    }

    public List<Renda> listarRendasExtras() {
        return rendaDAO.listarRendasExtras(Sessao.getIdUsuarioLogado());
    }

    public List<Renda> listarRendasFixas() {
        return rendaDAO.listarRendasFixas(Sessao.getIdUsuarioLogado());
    }

    public void editarRenda(String id, String nome, double valor) {
        rendaDAO.editarRenda(id, nome, valor);
    }

    public boolean excluirRenda(Renda renda) {
        return rendaDAO.excluirRenda(renda);
    }

    public Renda buscarPorId(String id) {
        return rendaDAO.buscarPorId(id, Sessao.getIdUsuarioLogado());
    }

    public void visualizarRenda(String id) {
        rendaDAO.visualizarRenda(id);
    }

    public double calcularRendaTotalMensal(int mes, int ano) {
        return rendaDAO.calcularRendaTotalMensal(
            mes,
            ano,
            Sessao.getIdUsuarioLogado()
        );
    }
}