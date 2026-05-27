package scr.model;

import java.util.Date;

public class Renda {
    private String idRenda;
    private String nomeRenda;
    private double valor;
    private Date data;
    private boolean tipoRenda;
    private String idUsuario;

    public Renda() {}

    public Renda(String idUsuario, String nomeRenda, double valor, Date data, boolean tipoRenda) {
        this.idUsuario = idUsuario;
        this.nomeRenda = nomeRenda;
        this.valor = valor;
        this.data = data;
        this.tipoRenda = tipoRenda;
    }

    public String getIdRenda() { return idRenda; }
    public void setIdRenda(String idRenda) { this.idRenda = idRenda; }

    public String getNomeRenda() { return nomeRenda; }
    public void setNomeRenda(String nomeRenda) { this.nomeRenda = nomeRenda; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public boolean isTipoRenda() { return tipoRenda; }
    public void setTipoRenda(boolean tipoRenda) { this.tipoRenda = tipoRenda; }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
}