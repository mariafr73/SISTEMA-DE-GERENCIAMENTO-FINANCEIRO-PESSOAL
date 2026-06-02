package scr.service;

import scr.dao.CategoriaDAO;
import java.util.List;
import java.util.UUID;
import scr.model.Categoria;
import scr.model.Sessao;

public class CategoriaService {

    private CategoriaDAO dao = new CategoriaDAO();

    public Categoria cadastrar(String nome) {

        if (nome == null || nome.trim().isEmpty()) {
            System.out.println("[ERRO] Nome inválido.");
            return null;
        }

        if (dao.buscarCategoriaDoUsuario(
                nome,
                Sessao.getIdUsuarioLogado()) != null) {

            System.out.println("[ERRO] Categoria já existe.");
            return null;
        }

        Categoria categoria = new Categoria(
                UUID.randomUUID().toString(),
                nome,
                true,
                Sessao.getIdUsuarioLogado());

        if (dao.inserir(categoria)) {
            return categoria;
        }

        return null;
    }

    public List<Categoria> listar() {
        return dao.listarCategoriasDoUsuario(
                Sessao.getIdUsuarioLogado());
    }

    public Categoria buscar(String nome) {
        return dao.buscarCategoriaDoUsuario(
                nome,
                Sessao.getIdUsuarioLogado());
    }

    public boolean editar(
            Categoria categoria,
            String novoNome) {

        if (!categoria.getStatus()) {
            System.out.println("Categoria inativa.");
            return false;
        }

        categoria.setNomeCategoria(novoNome);

        return dao.atualizar(categoria);
    }

    public boolean desativar(Categoria categoria) {

        if (!categoria.getStatus()) {
            return false;
        }

        categoria.setStatus(false);

        return dao.atualizar(categoria);
    }
}