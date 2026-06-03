package scr.service;

import scr.dao.DespesaDAO;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import scr.model.Despesa;
import scr.model.Sessao;

public class DespesaService {
    private final DespesaDAO dao =
            new DespesaDAO();

    public boolean cadastrar(Despesa despesa) {
        if (despesa.getCategoria() == null) {

            System.out.println("Categoria inválida.");
            return false;
        }

        if (!despesa.getCategoria().getStatus()) {

            System.out.println("Categoria desativada.");
            return false;
        }

        despesa.setIdDespesa(UUID.randomUUID().toString());

        return dao.cadastrarDespesa(despesa);
    }

    public boolean editar(Despesa despesa) {
        if (!despesa.getCategoria().getStatus()) {

            System.out.println("Categoria desativada.");
            return false;
        }

        return dao.editarDespesa(despesa);
    }

    public boolean excluir(String idDespesa) {
        return dao.excluirDespesa(idDespesa, Sessao.getIdUsuarioLogado());
    }

    public List<Despesa> listar() {

        return dao.listarDespesas(
                Sessao.getIdUsuarioLogado());
    }

    public List<Despesa> listarPorPeriodo(
            Date inicio,
            Date fim) {

        return dao.listarDespesasPorPeriodo(
                Sessao.getIdUsuarioLogado(),
                inicio,
                fim);
    }

    public List<Despesa> listarPorCategoria(
            String idCategoria) {

        return dao.listarPorCategoria(
                idCategoria,
                Sessao.getIdUsuarioLogado());
    }

    public Despesa buscar(String termo) {

        return dao.buscarPorTermo(
                termo,
                Sessao.getIdUsuarioLogado());
    }

    public double totalMensal(
            int mes,
            int ano) {

        return dao.calcularDespesaTotalMensal(
                mes,
                ano,
                Sessao.getIdUsuarioLogado());
    }
}