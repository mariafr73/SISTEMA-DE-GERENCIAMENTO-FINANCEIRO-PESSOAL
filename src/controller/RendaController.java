package src.controller;

import java.util.Date;
import java.util.List;
import src.model.Renda;
import src.service.RendaService;

public class RendaController {
    private final RendaService rendaService = new RendaService();

    public Renda cadastrar(String nome, double valor, Date data, boolean tipo) {
        return rendaService.cadastrarRenda(nome, valor, data, tipo);
    }

    public List<Renda> listarExtras() {
        return rendaService.listarRendasExtras();
    }

    public List<Renda> listarFixas() {
        return rendaService.listarRendasFixas();
    }

    public void editar(String id, String nome, double valor) {
        rendaService.editarRenda(id, nome, valor);
    }

    public boolean excluir(Renda renda) {
        return rendaService.excluirRenda(renda);
    }

    public Renda buscarPorId(String id) {
        return rendaService.buscarPorId(id);
    }

    public void visualizar(String id) {
        rendaService.visualizarRenda(id);
    }

    public double calcularTotalMensal(int mes, int ano) {
        return rendaService.calcularRendaTotalMensal(mes, ano);
    }

    public List<Renda> listarPorPeriodo(Date inicio, Date fim) {
        return rendaService.listarPorPeriodo(inicio, fim);
    }
}