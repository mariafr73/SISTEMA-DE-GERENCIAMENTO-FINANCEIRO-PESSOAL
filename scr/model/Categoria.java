package scr.model;

public class Categoria {

    private String idCategoria;
    private String nomeCategoria;
    private Boolean status;
    private String idUsuario;

    public Categoria() {}

    public Categoria(
            String idCategoria,
            String nomeCategoria,
            Boolean status,
            String idUsuario) {

        this.idCategoria = idCategoria;
        this.nomeCategoria = nomeCategoria;
        this.status = status;
        this.idUsuario = idUsuario;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void visualizarCategoria() {
        System.out.println("\n--- Categoria ---");
        System.out.println("ID: " + idCategoria);
        System.out.println("Nome: " + nomeCategoria);
        System.out.println("Status: " + (status ? "ATIVA" : "INATIVA"));
        System.out.println("----------------------");
    }
}