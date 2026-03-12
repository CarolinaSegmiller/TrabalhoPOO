package aplicacao;

import controle.PaisController;
import dao.DB;
import dao.DbException;
import dao.PaisDAO;
import modelo.Pais;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Programa {

    private static final Scanner sc = new Scanner(System.in).useLocale(Locale.US);

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);

        PaisController controller = new PaisController(new PaisDAO(DB.getConnection()));
        controller.carregarDadosIniciais();

        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            try {
                switch (opcao) {
                    case 1 -> cadastrar(controller);
                    case 2 -> listar(controller);
                    case 3 -> atualizar(controller);
                    case 4 -> excluir(controller);
                    case 5 -> comparar(controller);
                    case 6 -> buscar(controller);
                    case 0 -> System.out.println("Encerrando o sistema...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (IllegalArgumentException | DbException e) {
                System.out.println("Erro: " + e.getMessage());
            }
            System.out.println();
        } while (opcao != 0);

        DB.closeConnection();
        sc.close();
    }

    private static void exibirMenu() {
        System.out.println("========================================");
        System.out.println("   SUPER TRUNFO PAÍSES - JDBC EM JAVA   ");
        System.out.println("========================================");
        System.out.println("1 - Cadastrar país");
        System.out.println("2 - Listar países");
        System.out.println("3 - Atualizar país");
        System.out.println("4 - Excluir país");
        System.out.println("5 - Comparar dois países");
        System.out.println("6 - Buscar país por ID");
        System.out.println("0 - Sair");
    }

    private static void cadastrar(PaisController controller) {
        System.out.println("Cadastro de país");
        String nome = lerTexto("Nome: ");
        String capital = lerTexto("Capital: ");
        long populacao = lerLong("População: ");
        double area = lerDouble("Área em km²: ");
        double pib = lerDouble("PIB em trilhões de US$: ");
        int pontos = lerInteiro("Quantidade de pontos turísticos: ");

        Pais pais = controller.cadastrarPais(nome, capital, populacao, area, pib, pontos);
        System.out.println("País cadastrado com sucesso! ID gerado: " + pais.getId());
    }

    private static void listar(PaisController controller) {
        List<Pais> paises = controller.listarPaises();
        if (paises.isEmpty()) {
            System.out.println("Nenhum país cadastrado.");
        } else {
            paises.forEach(System.out::println);
        }
    }

    private static void atualizar(PaisController controller) {
        int id = lerInteiro("Informe o ID do país a atualizar: ");
        String nome = lerTexto("Novo nome: ");
        String capital = lerTexto("Nova capital: ");
        long populacao = lerLong("Nova população: ");
        double area = lerDouble("Nova área em km²: ");
        double pib = lerDouble("Novo PIB em trilhões de US$: ");
        int pontos = lerInteiro("Nova quantidade de pontos turísticos: ");

        controller.atualizarPais(id, nome, capital, populacao, area, pib, pontos);
        System.out.println("País atualizado com sucesso!");
    }

    private static void excluir(PaisController controller) {
        int id = lerInteiro("Informe o ID do país a excluir: ");
        controller.excluirPais(id);
        System.out.println("País excluído com sucesso!");
    }

    private static void comparar(PaisController controller) {
        listar(controller);
        int id1 = lerInteiro("ID do primeiro país: ");
        int id2 = lerInteiro("ID do segundo país: ");
        String atributo = lerTexto("Atributo para comparação (populacao, area, pib ou pontos): ");
        System.out.println(controller.compararPaises(id1, id2, atributo));
    }

    private static void buscar(PaisController controller) {
        int id = lerInteiro("Informe o ID do país: ");
        Pais pais = controller.buscarPais(id);
        if (pais == null) {
            System.out.println("País não encontrado.");
        } else {
            System.out.println(pais);
        }
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return sc.nextLine();
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                int valor = Integer.parseInt(sc.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static long lerLong(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                long valor = Long.parseLong(sc.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro longo válido.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                double valor = Double.parseDouble(sc.nextLine().replace(",", "."));
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Digite um número decimal válido.");
            }
        }
    }
}
