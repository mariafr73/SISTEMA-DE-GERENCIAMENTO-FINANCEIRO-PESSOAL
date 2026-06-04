package src.controller;

import java.util.Date;
import java.util.List;
import src.model.Categoria;
import src.model.Despesa;
import src.service.CategoriaService;
import src.service.DespesaService;

public class DespesaController {
    private final DespesaService despesaService = new DespesaService();
    private final CategoriaService categoriaService = new CategoriaService();

    public boolean cadastrarDespesa(String nome, double valor, Date data, Categoria categoria) {
        return despesaService.cadastrar(nome, valor, data, categoria);
    }

    public boolean editarDespesa(String idDespesa, String nome, double valor, Date data, Categoria categoria) {
        return despesaService.editar(idDespesa, nome, valor, data, categoria);
    }

    public boolean excluirDespesa(String idDespesa) {
        return despesaService.excluir(idDespesa);
    }

    public List<Despesa> listarDespesas() {
        return despesaService.listar();
    }

    public List<Despesa> listarPorPeriodo(Date inicio, Date fim) {
        return despesaService.listarPorPeriodo(inicio, fim);
    }

    public List<Despesa> listarPorCategoria(String idCategoria) {
        return despesaService.listarPorCategoria(idCategoria);
    }

    public Despesa buscarDespesa(String termo) {
        return despesaService.buscar(termo);
    }

    public double calcularDespesaTotalMensal(int mes, int ano) {
        return despesaService.totalMensal(mes, ano);
    }

    public List<Categoria> listarCategorias() {
        return categoriaService.listar();
    }
}