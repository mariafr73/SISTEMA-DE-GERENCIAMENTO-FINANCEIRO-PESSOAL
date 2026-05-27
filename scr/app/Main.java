package scr.app;

import java.util.Scanner;
import scr.model.Sessao;
import scr.views.TelaRenda;
import scr.views.TelaUsuario;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            if (!Sessao.isLogado()) {
                exibirMenuAcesso(scanner);
            } else {
                exibirMenuPrincipal(scanner);
            }
        }
    }

    private static void exibirMenuAcesso(Scanner scanner) {
        System.out.println("\n==========================================");
        System.out.println("     SISTEMA DE FINANÇAS PESSOAIS");
        System.out.println("==========================================");
        System.out.println("1. Login / Entrar");
        System.out.println("2. Cadastrar Novo Usuário");
        System.out.println("0. Sair do Sistema");
        System.out.println("==========================================");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcao(scanner);

        TelaUsuario telaUsuario = new TelaUsuario(scanner);

        switch (opcao) {
            case 1:
                telaUsuario.exibirMenuLogin();
                break;

            case 2:
                telaUsuario.exibirMenuCadastro();
                break;

            case 0:
                System.out.println("Saindo... Até logo!");
                scanner.close();
                System.exit(0);
                break;

            default:
                System.out.println("Opção inválida!");
        }
    }

    private static void exibirMenuPrincipal(Scanner scanner) {
        String idUsuario = Sessao.getIdUsuarioLogado();

        String idDisplay = idUsuario.length() > 8
                ? idUsuario.substring(0, 8) + "..."
                : idUsuario;

        System.out.println("\n==========================================");
        System.out.println("     MENU PRINCIPAL (ID: " + idDisplay + ")");
        System.out.println("==========================================");
        System.out.println("1. Módulo de Rendas");
        System.out.println("2. Módulo de Despesas");
        System.out.println("3. Módulo de Categorias");
        System.out.println("4. Meu Perfil");
        System.out.println("5. Logout (Trocar Usuário)");
        System.out.println("0. Sair do Sistema");
        System.out.println("==========================================");
        System.out.print("Escolha uma opção: ");

        int opcao = lerOpcao(scanner);

        switch (opcao) {
            case 1:
                new TelaRenda().exibirMenu();
                break;

            case 2:
                System.out.println("Módulo de despesas ainda será implementado.");
                break;

            case 3:
                System.out.println("Módulo de categorias ainda será implementado.");
                break;

            case 4:
                new TelaUsuario(scanner).exibirMenuPerfil();
                break;

            case 5:
                Sessao.deslogar();
                System.out.println("Logout realizado. Retornando à tela de acesso.");
                break;

            case 0:
                System.out.println("Saindo... Até logo!");
                scanner.close();
                System.exit(0);
                break;

            default:
                System.out.println("Opção inválida!");
        }
    }

    private static int lerOpcao(Scanner scanner) {
        try {
            if (scanner.hasNextInt()) {
                int opcao = scanner.nextInt();
                scanner.nextLine();
                return opcao;
            }

            scanner.nextLine();
            return -1;

        } catch (Exception e) {
            scanner.nextLine();
            return -1;
        }
    }
}