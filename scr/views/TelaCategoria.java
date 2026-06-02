package scr.views;

import scr.controller.CategoriaController;
import java.util.List;
import java.util.Scanner;
import scr.model.Categoria;

public class TelaCategoria {

    private final Scanner leitor = new Scanner(System.in);
    private final CategoriaController controller = new CategoriaController();

    public void exibirMenu() {

        while (true) {

            System.out.println("\n====== MENU DE CATEGORIAS ======");
            System.out.println("1 - Cadastrar Categoria");
            System.out.println("2 - Listar Categorias");
            System.out.println("3 - Visualizar Categoria");
            System.out.println("4 - Buscar Categoria");
            System.out.println("5 - Editar Categoria");
            System.out.println("6 - Desativar Categoria");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma opção: ");

            String entrada = leitor.nextLine().trim();

            int opcao;

            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
                continue;
            }

            switch (opcao) {

                case 1 -> cadastrar();

                case 2 -> listar();

                case 3 -> visualizarCategoria();

                case 4 -> buscar();

                case 5 -> editar();

                case 6 -> desativar();

                case 0 -> {
                    return;
                }

                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrar() {

        System.out.print("Digite o nome da categoria: ");
        String nome = leitor.nextLine().trim();

        Categoria categoria = controller.cadastrar(nome);

        if (categoria != null) {
            System.out.println("Categoria cadastrada com sucesso!");
        }
    }

    private void listar() {

        List<Categoria> categorias = controller.listar();

        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria cadastrada.");
            return;
        }

        System.out.println("\n===== LISTA DE CATEGORIAS =====");

        for (Categoria categoria : categorias) {
            categoria.visualizarCategoria();
        }
    }

    private void visualizarCategoria() {

        List<Categoria> categorias = controller.listar();

        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria encontrada.");
            return;
        }

        System.out.println("\n===== ESCOLHA UMA CATEGORIA =====");

        for (int i = 0; i < categorias.size(); i++) {

            Categoria categoria = categorias.get(i);

            String status =
                    categoria.getStatus() ? "Ativa" : "Inativa";

            System.out.println(
                    "[" + i + "] "
                    + categoria.getNomeCategoria()
                    + " (" + status + ")");
        }

        System.out.print("Digite o número da categoria: ");
        String entrada = leitor.nextLine();

        try {

            int indice = Integer.parseInt(entrada);

            if (indice < 0 || indice >= categorias.size()) {
                System.out.println("Opção inválida.");
                return;
            }

            Categoria categoriaSelecionada =
                    categorias.get(indice);

            categoriaSelecionada.visualizarCategoria();

        } catch (NumberFormatException e) {

            System.out.println(
                    "Erro: Digite apenas números inteiros.");
        }
    }

    private void buscar() {

        System.out.print(
                "Digite o nome da categoria para buscar: ");

        String nome = leitor.nextLine().trim();

        Categoria categoria = controller.buscar(nome);

        if (categoria == null) {

            System.out.println(
                    "Categoria não encontrada.");
            return;
        }

        categoria.visualizarCategoria();
    }

    private void editar() {

        System.out.print(
                "Digite o nome da categoria que deseja editar: ");

        String nomeAtual = leitor.nextLine().trim();

        Categoria categoria =
                controller.buscar(nomeAtual);

        if (categoria == null) {

            System.out.println(
                    "Categoria não encontrada.");
            return;
        }

        System.out.print(
                "Digite o novo nome da categoria: ");

        String novoNome = leitor.nextLine().trim();

        boolean sucesso =
                controller.editar(categoria, novoNome);

        if (sucesso) {

            System.out.println(
                    "Categoria atualizada com sucesso!");
        } else {

            System.out.println(
                    "Não foi possível atualizar a categoria.");
        }
    }

    private void desativar() {

        System.out.print(
                "Digite o nome da categoria que deseja desativar: ");

        String nome = leitor.nextLine().trim();

        Categoria categoria =
                controller.buscar(nome);

        if (categoria == null) {

            System.out.println(
                    "Categoria não encontrada.");
            return;
        }

        boolean sucesso =
                controller.desativar(categoria);

        if (sucesso) {

            System.out.println(
                    "Categoria desativada com sucesso!");
        } else {

            System.out.println(
                    "Não foi possível desativar a categoria.");
        }
    }
}