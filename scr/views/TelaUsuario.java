package scr.views;

import scr.controller.UsuarioController;
import scr.model.Sessao;
import scr.model.Usuario;
import scr.model.UtilData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class TelaUsuario {

    private final Scanner scanner;
    private final UsuarioController controller = new UsuarioController();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

    public TelaUsuario(Scanner scanner) {
        this.scanner = scanner;
        this.dateFormat.setLenient(false);
    }

    public String exibirMenuLogin() {
        Usuario usuario = realizarLogin();
        return usuario != null ? usuario.getIdUsuario() : null;
    }

    public void exibirMenuCadastro() {
        cadastrarUsuario();
    }

    public void exibirMenuPerfil() {
        int opcao = -1;

        while (opcao != 0) {
            if (!Sessao.isLogado()) {
                return;
            }

            System.out.println("\n===== MEU PERFIL =====");
            System.out.println("1. Visualizar Meus Dados");
            System.out.println("2. Editar Meus Dados");
            System.out.println("3. Excluir Minha Conta");
            System.out.println("4. Visualizar Relatório Financeiro");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("======================");
            System.out.print("Escolha uma opção: ");

            opcao = lerInteiro();

            switch (opcao) {
                case 1 -> exibirDadosUsuarioLogado();
                case 2 -> editarPerfil();
                case 3 -> {
                    if (excluirConta()) {
                        return;
                    }
                }
                case 4 -> visualizarRelatorioFinanceiro();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void exibirDadosUsuarioLogado() {
        Usuario usuario = buscarUsuarioLogado();

        if (usuario != null) {
            controller.visualizarUsuario(usuario);
            System.out.println("Pressione ENTER para continuar...");
            scanner.nextLine();
        }
    }

    private void editarPerfil() {
        Usuario usuario = buscarUsuarioLogado();

        if (usuario == null) {
            return;
        }

        System.out.println("\n--- EDITAR PERFIL ---");
        System.out.println("(Deixe vazio e aperte Enter para manter o valor atual)");

        System.out.print("Novo Nome [" + usuario.getNome() + "]: ");
        String novoNome = scanner.nextLine();

        if (!novoNome.trim().isEmpty()) {
            usuario.setNome(novoNome);
        }

        System.out.print("Novo Email [" + usuario.getEmail() + "]: ");
        String novoEmail = scanner.nextLine();

        if (!novoEmail.trim().isEmpty()) {
            usuario.setEmail(novoEmail);
        }

        String dataAtualStr = usuario.getDataNascimento() != null
                ? UtilData.formatarData(usuario.getDataNascimento())
                : "N/D";

        System.out.print("Nova Data (dd/MM/yyyy) [" + dataAtualStr + "]: ");
        String novaDataStr = scanner.nextLine();

        if (!novaDataStr.trim().isEmpty()) {
            try {
                Date novaData = dateFormat.parse(novaDataStr);
                usuario.setDataNascimento(novaData);
            } catch (ParseException e) {
                System.out.println("Data inválida. A data antiga foi mantida.");
            }
        }

        if (controller.editarUsuario(usuario)) {
            System.out.println("Novos dados foram salvos.");
        } else {
            System.out.println("Erro ao atualizar perfil. O email pode já estar em uso.");
        }
    }

    private boolean excluirConta() {
        System.out.println("\n ATENÇÃO: EXCLUSÃO DE CONTA ");
        System.out.println("Tem certeza que deseja excluir sua conta?");
        System.out.println("Isso apagará TODAS as suas Despesas e Rendas permanentemente.");
        System.out.print("Digite 'SIM' para confirmar a exclusão: ");

        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("SIM")) {
            if (controller.excluirContaLogada()) {
                System.out.println("Conta excluída com sucesso.");
                System.out.println("Você será deslogado agora.");
                return true;
            } else {
                System.out.println("Erro ao excluir conta.");
            }
        } else {
            System.out.println("Operação cancelada.");
        }

        return false;
    }

    private Usuario buscarUsuarioLogado() {
        return controller.buscarUsuarioLogado();
    }

    private void cadastrarUsuario() {
        System.out.println("\n--- CADASTRO DE USUÁRIO ---");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Date dataNascimento = lerDataNascimento();

        boolean sucesso = controller.registrarUsuario(
                nome,
                email,
                senha,
                dataNascimento
        );

        if (sucesso) {
            System.out.println("Usuário registrado com sucesso! Faça login para continuar.");
        } else {
            System.err.println("Falha ao registrar. O email pode já existir.");
        }
    }

    private Usuario realizarLogin() {
        System.out.println("\n--- LOGIN ---");

        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Usuario usuarioLogado = controller.login(email, senha);

        if (usuarioLogado == null) {
            System.err.println("Login falhou. Email ou senha incorretos.");
        }

        return usuarioLogado;
    }

    private void visualizarRelatorioFinanceiro() {
        System.out.println("\n--- RELATÓRIO FINANCEIRO ---");

        System.out.print("Data inicial (dd/MM/yyyy): ");
        String inicio = scanner.nextLine();

        System.out.print("Data final (dd/MM/yyyy): ");
        String fim = scanner.nextLine();

        String relatorio = controller.listarRendasDespesasPorPeriodo(inicio, fim);

        System.out.println(relatorio);
    }

    private Date lerDataNascimento() {
        Date dataNascimento = null;
        final int anoLimite = java.time.Year.now().getValue();

        while (dataNascimento == null) {
            try {
                System.out.println("--- Informe a Data de Nascimento (Limite: " + anoLimite + ") ---");

                System.out.print("Dia: ");
                int dia = Integer.parseInt(scanner.nextLine());

                System.out.print("Mês: ");
                int mes = Integer.parseInt(scanner.nextLine());

                System.out.print("Ano: ");
                int ano = Integer.parseInt(scanner.nextLine());

                if (ano > anoLimite) {
                    System.out.println("Erro: o ano de nascimento não pode ser maior que " + anoLimite + ".");
                    continue;
                }

                String dataTexto = String.format("%02d/%02d/%d", dia, mes, ano);
                dataNascimento = UtilData.parseDataUsuario(dataTexto);

                if (dataNascimento == null) {
                    System.out.println("Data inválida! Verifique se o dia e o mês são válidos.");
                    continue;
                }

                if (dataNascimento.after(new Date())) {
                    System.out.println("Erro: a data de nascimento não pode ser no futuro.");
                    dataNascimento = null;
                }

            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números inteiros para dia, mês e ano.");
            }
        }

        return dataNascimento;
    }

    private int lerInteiro() {
        try {
            if (scanner.hasNextInt()) {
                int numero = scanner.nextInt();
                scanner.nextLine();
                return numero;
            }

            scanner.nextLine();
            return -1;

        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
}