package src.views;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import src.controller.DespesaController;
import src.model.Categoria;
import src.model.Despesa;
import src.model.UtilData;

public class TelaDespesa {

    private final Scanner leitor = new Scanner(System.in);
    private final DespesaController controller = new DespesaController();

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n====== MENU DE DESPESAS ======");
            System.out.println("1 - Cadastrar Despesa");
            System.out.println("2 - Listar Despesas");
            System.out.println("3 - Visualizar Despesa");
            System.out.println("4 - Buscar Despesa");
            System.out.println("5 - Editar Despesa");
            System.out.println("6 - Excluir Despesa");
            System.out.println("7 - Listar por Categoria");
            System.out.println("8 - Listar por Período");
            System.out.println("9 - Total Mensal");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            try {
                opcao = Integer.parseInt(leitor.nextLine());
                switch (opcao) {
                    case 1 -> cadastrarDespesa();
                    case 2 -> listarDespesas();
                    case 3 -> visualizarDespesa();
                    case 4 -> buscarDespesa();
                    case 5 -> editarDespesa();
                    case 6 -> excluirDespesa();
                    case 7 -> listarPorCategoria();
                    case 8 -> listarPorPeriodo();
                    case 9 -> calcularTotalMensal();
                    case 0 -> System.out.println("Retornando...");
                    default -> System.out.println("Opção inválida.");

                }

            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void cadastrarDespesa() {
        try {
            System.out.print("Nome da despesa: ");
            String nome = leitor.nextLine();

            System.out.print("Valor: ");
            double valor = Double.parseDouble(leitor.nextLine());

            System.out.print("Data (dd/MM/yyyy): ");
            Date data = UtilData.parseDataUsuario(leitor.nextLine());

            if (data == null) {
                System.out.println("Data inválida.");
                return;
            }

            List<Categoria> categorias = controller.listarCategorias();

            if (categorias.isEmpty()) {
                System.out.println("Nenhuma categoria cadastrada.");
                return;
            }

            System.out.println("\nCategorias:");
            for (int i = 0; i < categorias.size(); i++) {
                System.out.println("[" + i + "] " + categorias.get(i).getNomeCategoria());
            }

            System.out.print("Escolha a categoria: ");

            int pos = Integer.parseInt(leitor.nextLine());

            if (pos < 0 || pos >= categorias.size()) {
                System.out.println("Categoria inválida.");
                return;
            }

            Categoria categoria = categorias.get(pos);

            boolean sucesso = controller.cadastrarDespesa(nome, valor, data, categoria);

            if (sucesso) {
                System.out.println("Despesa cadastrada com sucesso.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar despesa.");
        }
    }

    private void listarDespesas() {
        List<Despesa> despesas = controller.listarDespesas();

        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa encontrada.");
            return;
        }

        despesas.forEach(Despesa::visualizarDespesa);
    }

    private void visualizarDespesa() {

        List<Despesa> despesas = controller.listarDespesas();

        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa encontrada.");
            return;
        }

        for (int i = 0; i < despesas.size(); i++) {
            System.out.println("[" + i + "] " + despesas.get(i).getNomeDespesa());
        }

        System.out.print("Escolha a despesa: ");

        try {
            int pos = Integer.parseInt(leitor.nextLine());

            despesas.get(pos).visualizarDespesa();

        } catch (Exception e) {
            System.out.println("Opção inválida.");
        }
    }

    private void buscarDespesa() {
        System.out.print("Digite um termo: ");

        String termo = leitor.nextLine();

        Despesa despesa = controller.buscarDespesa(termo);

        if (despesa == null) {
            System.out.println("Despesa não encontrada.");
            return;
        }

        despesa.visualizarDespesa();
    }

    private void editarDespesa() {
        List<Despesa> despesas = controller.listarDespesas();

        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa encontrada.");
            return;
        }

        for (int i = 0; i < despesas.size(); i++) {
            System.out.println("[" + i + "] " + despesas.get(i).getNomeDespesa());
        }

        try {
            System.out.print("Escolha a despesa: ");
            int pos = Integer.parseInt(leitor.nextLine());
            Despesa despesa = despesas.get(pos);
            System.out.print("Novo nome: ");
            String nome = leitor.nextLine();
            System.out.print("Novo valor: ");
            double valor = Double.parseDouble(leitor.nextLine());
            System.out.print("Nova data (dd/MM/yyyy): ");
            Date data = UtilData.parseDataUsuario(leitor.nextLine());
            List<Categoria> categorias = controller.listarCategorias();

            for (int i = 0; i < categorias.size(); i++) {
                System.out.println("[" + i + "] " + categorias.get(i).getNomeCategoria());
            }

            System.out.print("Categoria: ");
            int posCat = Integer.parseInt(leitor.nextLine());
            Categoria categoria = categorias.get(posCat);
            controller.editarDespesa(despesa.getIdDespesa(), nome, valor, data, categoria);
            System.out.println("Despesa atualizada.");

        } catch (Exception e) {
            System.out.println("Erro ao editar despesa.");
        }
    }

    private void excluirDespesa() {
        List<Despesa> despesas = controller.listarDespesas();

        if (despesas.isEmpty()) {
            System.out.println("Nenhuma despesa encontrada.");
            return;
        }

        for (int i = 0; i < despesas.size(); i++) {
            System.out.println("[" + i + "] " + despesas.get(i).getNomeDespesa());
        }

        try {
            System.out.print("Escolha a despesa: ");
            int pos = Integer.parseInt(leitor.nextLine());
            boolean sucesso = controller.excluirDespesa(despesas.get(pos).getIdDespesa());

            if (sucesso) {
                System.out.println("Despesa removida.");
            }

        } catch (Exception e) {
            System.out.println("Erro ao excluir.");
        }
    }

    private void listarPorCategoria() {
        List<Categoria> categorias = controller.listarCategorias();

        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria cadastrada.");
            return;
        }

        for (int i = 0; i < categorias.size(); i++) {
            System.out.println("[" + i + "] " + categorias.get(i).getNomeCategoria());
        }

        try {
            System.out.print("Categoria: ");
            int pos = Integer.parseInt(leitor.nextLine());
            List<Despesa> despesas = controller.listarPorCategoria(categorias.get(pos).getIdCategoria());
            despesas.forEach(Despesa::visualizarDespesa);

        } catch (Exception e) {
            System.out.println("Categoria inválida.");
        }
    }

    private void listarPorPeriodo() {
        System.out.print("Data inicial: ");
        Date inicio = UtilData.parseDataUsuario(leitor.nextLine());

        System.out.print("Data final: ");
        Date fim = UtilData.parseDataUsuario(leitor.nextLine());

        List<Despesa> despesas = controller.listarPorPeriodo(inicio, fim);

        despesas.forEach(Despesa::visualizarDespesa);
    }

    private void calcularTotalMensal() {
        try {
            System.out.print("Mês: ");
            int mes = Integer.parseInt(leitor.nextLine());

            System.out.print("Ano: ");
            int ano = Integer.parseInt(leitor.nextLine());
            double total = controller.calcularDespesaTotalMensal(mes, ano);
            System.out.printf("Total: R$ %.2f%n", total);

        } catch (Exception e) {
            System.out.println("Dados inválidos.");
        }
    }
}