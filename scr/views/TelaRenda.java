package scr.views;

import scr.controller.RendaController;
import scr.model.Renda;
import scr.model.UtilData;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TelaRenda {
    private final Scanner leitor = new Scanner(System.in);
    private final RendaController controller = new RendaController();

    public void exibirMenu() {
        while (true) {
            System.out.println("\n=== GESTÃO DE RENDAS ===");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar Extras");
            System.out.println("3. Listar Fixas");
            System.out.println("4. Editar");
            System.out.println("5. Excluir");
            System.out.println("6. Visualizar");
            System.out.println("7. Total Mensal");
            System.out.println("0. Voltar");
            System.out.print("Opção: ");

            int opcao = lerInteiro();

            if (opcao == 0) {
                break;
            }

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listarExtras();
                case 3 -> listarFixas();
                case 4 -> editar();
                case 5 -> excluir();
                case 6 -> visualizar();
                case 7 -> totalMensal();
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private void cadastrar() {
        System.out.println("\n--- NOVA RENDA ---");

        System.out.print("Nome: ");
        String nome = leitor.nextLine();

        double valor = lerValorPositivo();

        Date data = lerData();

        boolean tipo = lerTipoRenda();

        Renda novaRenda = controller.cadastrar(nome, valor, data, tipo);

        if (novaRenda != null) {
            System.out.println("\nSucesso! Renda cadastrada.");
            System.out.println("ID gerado: " + novaRenda.getIdRenda());
        } else {
            System.out.println("\nFalha ao cadastrar renda.");
        }
    }

    private void listarExtras() {
        System.out.println("\n--- RENDAS EXTRAS ---");

        List<Renda> lista = controller.listarExtras();

        if (lista.isEmpty()) {
            System.out.println("Nenhuma renda extra cadastrada.");
            return;
        }

        exibirLista(lista);
    }

    private void listarFixas() {
        System.out.println("\n--- RENDAS FIXAS ---");

        List<Renda> lista = controller.listarFixas();

        if (lista.isEmpty()) {
            System.out.println("Nenhuma renda fixa cadastrada.");
            return;
        }

        exibirLista(lista);
    }

    private void editar() {
        System.out.println("\n--- EDITAR RENDA ---");

        List<Renda> todas = listarTodas();

        if (todas.isEmpty()) {
            System.out.println("Nenhuma renda cadastrada para editar.");
            return;
        }

        Renda rendaSelecionada = escolherRenda(todas);

        if (rendaSelecionada == null) {
            return;
        }

        System.out.println("\nEditando: " + rendaSelecionada.getNomeRenda());
        System.out.println("Deixe vazio e aperte Enter para manter o valor atual.");

        System.out.print("Novo nome (" + rendaSelecionada.getNomeRenda() + "): ");
        String novoNome = leitor.nextLine();

        if (novoNome.isBlank()) {
            novoNome = rendaSelecionada.getNomeRenda();
        }

        System.out.print("Novo valor (" + rendaSelecionada.getValor() + "): ");
        String valorTexto = leitor.nextLine();

        double novoValor = rendaSelecionada.getValor();

        if (!valorTexto.isBlank()) {
            try {
                double valorDigitado = Double.parseDouble(valorTexto.replace(",", "."));

                if (valorDigitado > 0) {
                    novoValor = valorDigitado;
                } else {
                    System.out.println("Valor inválido. Mantendo valor anterior.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Mantendo valor anterior.");
            }
        }

        controller.editar(
            rendaSelecionada.getIdRenda(),
            novoNome,
            novoValor
        );

        System.out.println("Renda atualizada com sucesso!");
    }

    private void excluir() {
        System.out.println("\n--- EXCLUIR RENDA ---");

        List<Renda> todas = listarTodas();

        if (todas.isEmpty()) {
            System.out.println("Nenhuma renda cadastrada para excluir.");
            return;
        }

        Renda rendaSelecionada = escolherRenda(todas);

        if (rendaSelecionada == null) {
            return;
        }

        System.out.print(
            "Tem certeza que deseja excluir '" +
            rendaSelecionada.getNomeRenda() +
            "'? (S/N): "
        );

        String confirmacao = leitor.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            boolean excluiu = controller.excluir(rendaSelecionada);

            if (excluiu) {
                System.out.println("Renda excluída com sucesso!");
            } else {
                System.out.println("Erro ao excluir renda.");
            }

        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private void visualizar() {
        System.out.println("\n--- VISUALIZAR RENDA ---");

        List<Renda> todas = listarTodas();

        if (todas.isEmpty()) {
            System.out.println("Nenhuma renda cadastrada.");
            return;
        }

        Renda rendaSelecionada = escolherRenda(todas);

        if (rendaSelecionada == null) {
            return;
        }

        controller.visualizar(rendaSelecionada.getIdRenda());
    }

    private void totalMensal() {
        System.out.println("\n--- TOTAL MENSAL ---");

        System.out.print("Mês: ");
        int mes = lerInteiro();

        System.out.print("Ano: ");
        int ano = lerInteiro();

        double total = controller.calcularTotalMensal(mes, ano);

        System.out.println("Total mensal: R$ " + total);
    }

    private List<Renda> listarTodas() {
        List<Renda> todas = controller.listarFixas();
        todas.addAll(controller.listarExtras());
        return todas;
    }

    private Renda escolherRenda(List<Renda> lista) {
        System.out.println("Selecione uma renda:");

        for (int i = 0; i < lista.size(); i++) {
            Renda r = lista.get(i);

            System.out.println(
                (i + 1) + ". " +
                r.getNomeRenda() +
                " | R$ " +
                r.getValor()
            );
        }

        System.out.println("0. Cancelar");
        System.out.print("Opção: ");

        int opcao = lerInteiro();

        if (opcao == 0) {
            System.out.println("Operação cancelada.");
            return null;
        }

        if (opcao < 1 || opcao > lista.size()) {
            System.out.println("Opção inválida.");
            return null;
        }

        return lista.get(opcao - 1);
    }

    private void exibirLista(List<Renda> lista) {
        for (Renda r : lista) {
            System.out.println(
                r.getIdRenda() +
                " | " +
                r.getNomeRenda() +
                " | R$ " +
                r.getValor()
            );
        }
    }

    private int lerInteiro() {
        try {
            return Integer.parseInt(leitor.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números.");
            return -1;
        }
    }

    private double lerValorPositivo() {
        double valor = -1;

        while (valor <= 0) {
            System.out.print("Valor (maior que 0): ");

            try {
                valor = Double.parseDouble(
                    leitor.nextLine().replace(",", ".")
                );

                if (valor <= 0) {
                    System.out.println("O valor deve ser positivo.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números.");
            }
        }

        return valor;
    }

    private Date lerData() {
        Date data = null;

        while (data == null) {
            try {
                System.out.println("--- Informe a Data ---");

                System.out.print("Dia: ");
                int dia = Integer.parseInt(leitor.nextLine());

                System.out.print("Mês: ");
                int mes = Integer.parseInt(leitor.nextLine());

                System.out.print("Ano: ");
                int ano = Integer.parseInt(leitor.nextLine());

                int anoAtual = java.time.Year.now().getValue();

                if (ano > anoAtual) {
                    System.out.println("O ano não pode ser maior que " + anoAtual);
                    continue;
                }

                String dataTexto = String.format(
                    "%02d/%02d/%d",
                    dia,
                    mes,
                    ano
                );

                data = UtilData.parseDataUsuario(dataTexto);

                if (data == null) {
                    System.out.println("Data inválida.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números inteiros.");
            }
        }

        return data;
    }

    private boolean lerTipoRenda() {
        while (true) {
            System.out.print("É fixa? (1-Sim / 0-Não): ");
            String tipoStr = leitor.nextLine();

            if (tipoStr.equals("1")) {
                return true;
            }

            if (tipoStr.equals("0")) {
                return false;
            }

            System.out.println("Digite 1 para Sim ou 0 para Não.");
        }
    }
}