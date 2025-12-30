package View.textual;

import Model.SystemSIGEE;
import View.CreateObject_View;

import java.time.LocalDateTime;
import java.util.Scanner;

public class CreateObject_TextualView implements CreateObject_View {

    private final SystemSIGEE model;
    private final Scanner scanner;

    /**
     * Construtor chamado pela TextualConcreteFactory.
     */
    public CreateObject_TextualView(Object object, Scanner scanner) {
        this.model = SystemSIGEE.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void display() {
        System.out.println("\n--- [Criar Novo Objeto] ---");
        System.out.println("O que você deseja criar?");
        System.out.println("1. Aluno de Graduação");
        System.out.println("2. Professor");
        System.out.println("3. Entidade");
        System.out.println("4. Cargo (Role)");
        System.out.println("5. Vínculo (Bond)");
        System.out.println("6. Evento");
        System.out.println("0. Voltar");
        System.out.print("Escolha: ");

        int choice = readInt();
        boolean success = false;
        String errorMessage = "Operação cancelada ou dados inválidos.";

        try {
            switch (choice) {
                case 1: // Aluno
                    System.out.print("Nome: "); String nome = readString();
                    System.out.print("Email (user@ufsc.com): "); String email = readString();
                    System.out.print("Matrícula (8 digitos): "); String mat = readString();
                    success = model.registerUndergraduateStudent(nome, email, mat);
                    break;
                case 2: // Professor
                    System.out.print("Nome: "); nome = readString();
                    System.out.print("Email (user@ufsc.com): "); email = readString();
                    System.out.print("SIAPE (8 digitos): "); String siape = readString();
                    success = model.registerProfessor(nome, email, siape);
                    break;
                case 3: // Entidade
                    System.out.print("Nome da Entidade: "); nome = readString();
                    System.out.print("Descrição: "); String desc = readString();
                    success = model.createEntity(nome, desc);
                    break;
                case 4: // Cargo
                    System.out.print("Nome do Cargo: "); nome = readString();
                    System.out.print("Descrição: "); desc = readString();
                    System.out.print("Pagamento (ex: 1200.50): "); double pay = readDouble();
                    success = model.createRole(nome, desc, pay);
                    break;
                case 5: // Vínculo
                    System.out.print("Não suportado");
                    break;
                case 6: // Evento
                    System.out.print("Nome da Entidade (dona): "); String eName = readString();
                    System.out.print("Nome do Evento: "); nome = readString();
                    System.out.print("Local: "); String loc = readString();
                    System.out.println("Data do evento será definida como 'agora'."); // Simplificado
                    success = model.createEvent(eName, nome, loc, LocalDateTime.now());
                    break;
                case 0:
                    System.out.println("Criação cancelada.");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }

        } catch (NumberFormatException e) {
            errorMessage = "Erro: Formato de número inválido (para pagamento).";
            success = false;
        } catch (Exception e) {
            // Pega exceções dos construtores (IllegalArgumentException)
            // ou falhas em encontrar IDs (NullPointerException)
            errorMessage = "Erro: " + e.getMessage();
            success = false;
        }

        if (success) {
            System.out.println(">>> Objeto criado com sucesso! <<<");
        } else {
            System.out.println("!!! Falha ao criar objeto. " + errorMessage + " !!!");
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

    private double readDouble() throws NumberFormatException {
        return Double.parseDouble(scanner.nextLine());
    }

    private String readString() {
        return scanner.nextLine();
    }

    private void pressEnterToContinue() {
        System.out.println("\n[Pressione ENTER para continuar...]");
        scanner.nextLine();
    }
}