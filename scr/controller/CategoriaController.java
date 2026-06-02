package scr.controller;

import java.util.List;
import scr.model.Categoria;
import scr.service.CategoriaService;

public class CategoriaController {

    private CategoriaService service =
            new CategoriaService();

    public Categoria cadastrar(String nome) {
        return service.cadastrar(nome);
    }

    public List<Categoria> listar() {
        return service.listar();
    }

    public Categoria buscar(String nome) {
        return service.buscar(nome);
    }

    public boolean editar(
            Categoria categoria,
            String novoNome) {

        return service.editar(categoria, novoNome);
    }

    public boolean desativar(Categoria categoria) {
        return service.desativar(categoria);
    }
}