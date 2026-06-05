package src.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import src.dao.DespesaDAO;
import src.model.Categoria;
import src.model.Despesa;
import util.Sessao;

public class DespesaService {
    private final DespesaDAO dao = new DespesaDAO();

    public boolean cadastrar(String nome, double valor, Date data, Categoria categoria) {
        if (!Sessao.isLogado()) {
            System.out.println("Erro: nenhum usuário logado.");
            return false;
        }

        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Erro: nome da despesa obrigatório.");
            return false;
        }

        if (valor <= 0) {
            System.out.println("Erro: valor deve ser maior que zero.");
            return false;
        }

        if (data == null) {
            System.out.println("Erro: data inválida.");
            return false;
        }

        if (categoria == null) {
            System.out.println("Erro: categoria inválida.");
            return false;
        }

        if (!categoria.getStatus()) {
            System.out.println("Erro: categoria desativada.");
            return false;
        }

        Despesa despesa = new Despesa(nome, valor, data, categoria, Sessao.getIdUsuarioLogado());
        despesa.setIdDespesa(UUID.randomUUID().toString());
        return dao.cadastrarDespesa(despesa);
    }

    public boolean editar(String idDespesa, String nome, double valor, Date data, Categoria categoria) {
        if (!Sessao.isLogado()) {
            System.out.println("Erro: nenhum usuário logado.");
            return false;
        }

        if (idDespesa == null || idDespesa.trim().isEmpty()) {
            System.out.println("Erro: ID da despesa inválido.");
            return false;
        }

        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("Erro: nome da despesa obrigatório.");
            return false;
        }

        if (valor <= 0) {
            System.out.println("Erro: valor deve ser maior que zero.");
            return false;
        }

        if (data == null) {
            System.out.println("Erro: data inválida.");
            return false;
        }

        if (categoria == null || !categoria.getStatus()) {
            System.out.println("Erro: categoria inválida ou desativada.");
            return false;
        }

        Despesa despesa = new Despesa(nome, valor, data, categoria, Sessao.getIdUsuarioLogado());
        despesa.setIdDespesa(idDespesa);
        return dao.editarDespesa(despesa);
    }

    public boolean excluir(String idDespesa) {
        return dao.excluirDespesa(idDespesa, Sessao.getIdUsuarioLogado());
    }

    public List<Despesa> listar() {
        return dao.listarDespesas(Sessao.getIdUsuarioLogado());
    }

    public List<Despesa> listarPorPeriodo(Date inicio, Date fim) {
        return dao.listarDespesasPorPeriodo(Sessao.getIdUsuarioLogado(), inicio, fim);
    }

    public List<Despesa> listarPorCategoria(String idCategoria) {
        return dao.listarPorCategoria(idCategoria, Sessao.getIdUsuarioLogado());
    }

    public Despesa buscar(String termo) {
        return dao.buscarPorTermo(termo, Sessao.getIdUsuarioLogado());
    }

    public double totalMensal(int mes, int ano) {
        return dao.calcularDespesaTotalMensal(mes, ano, Sessao.getIdUsuarioLogado());
    }
}