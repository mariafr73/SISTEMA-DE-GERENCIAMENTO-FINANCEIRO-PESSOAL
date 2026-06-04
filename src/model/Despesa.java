package src.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Despesa {
    private String idDespesa;
    private String nomeDespesa;
    private double valor;
    private Date data;
    private Categoria categoria;
    private String idUsuario;

    public Despesa() {}

    public Despesa(String nomeDespesa, double valor, Date data, Categoria categoria, String idUsuario) {
        this.nomeDespesa = nomeDespesa;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
        this.idUsuario = idUsuario;
    }

    public String getIdDespesa() {
        return idDespesa;
    }

    public void setIdDespesa(String idDespesa) {
        this.idDespesa = idDespesa;
    }

    public String getNomeDespesa() {
        return nomeDespesa;
    }

    public void setNomeDespesa(String nomeDespesa) {
        this.nomeDespesa = nomeDespesa;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void visualizarDespesa() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("\n--- DESPESA ---");
        System.out.println("ID: " + idDespesa);
        System.out.println("Nome: " + nomeDespesa);
        System.out.printf("Valor: R$ %.2f%n", valor);
        System.out.println("Data: " + (data != null ? sdf.format(data) : "Não informada"));
        System.out.println("Categoria: " + (categoria != null ? categoria.getNomeCategoria() : "Sem categoria"));
        System.out.println("---------------------");
    }
}