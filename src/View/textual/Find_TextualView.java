package View.textual;

import Model.SystemSIGEE;
import View.Find_View;
import java.util.Scanner;


public class Find_TextualView implements Find_View {

    private final SystemSIGEE model;
    private final Scanner scanner;


    public Find_TextualView(Object owner, Scanner scanner) {
        this.model = SystemSIGEE.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void display() {
        System.out.println("\n--- [Buscar Objeto] ---");
        System.out.println("O que você deseja buscar?");
        System.out.println("1. Pessoa (por ID)");
        System.out.println("2. Entidade (por Nome)");
        System.out.println("3. Evento (por Nome)");
        System.out.println("4. Cargo (Role) (por Nome)");
        System.out.println("5. Vínculo (Bond) (por ID da Pessoa e Nome da Entidade)");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");

        int choice = readInt();
        Object result = null; // Usamos um Object genérico para guardar o resultado
        String searchTerm;

        try {
            switch (choice) {
                case 1:
                    System.out.print("Digite o ID (Matrícula/SIAPE): ");
                    searchTerm = readString();
                    result = model.findPersonById(searchTerm);
                    break;
                case 2:
                    System.out.print("Digite o Nome da Entidade: ");
                    searchTerm = readString();
                    result = model.findEntityByName(searchTerm);
                    break;
                case 3:
                    System.out.print("Digite o Nome do Evento: ");
                    searchTerm = readString();
                    result = model.findEventByName(searchTerm);
                    break;
                case 4:
                    System.out.print("Digite o Nome do Cargo: ");
                    searchTerm = readString();
                    result = model.findRoleByName(searchTerm);
                    break;
                case 5:
                    System.out.print("Digite o ID da Pessoa (Matrícula/SIAPE): ");
                    String personId = readString();
                    System.out.print("Digite o Nome da Entidade: ");
                    String entityName = readString();
                    result = model.getBondOfPerson(entityName, personId);
                    break;
                case 0:
                    System.out.println("Busca cancelada.");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }

        } catch (Exception e) {
            System.out.println("Erro durante a busca: " + e.getMessage());
        }

        if (result != null) {
            System.out.println("\n>>> Objeto Encontrado! <<<");
            // Imprime o resultado usando o .toString() do objeto
            System.out.println(result.toString());
        } else {
            System.out.println("\n!!! Objeto não encontrado. !!!");
        }

        pressEnterToContinue();
    }


    private int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return -1;
        }
    }

    private String readString() {
        return scanner.nextLine();
    }

    private void pressEnterToContinue() {
        System.out.println("\n[Pressione ENTER para continuar...]");
        scanner.nextLine();
    }
}