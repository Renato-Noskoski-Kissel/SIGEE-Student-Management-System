package View.textual;

import Model.Factory;
import Model.SystemSIGEE;
import Model.domain.Person;
import Model.domain.UndergraduateStudent;
import View.Person_View;
import java.util.Scanner;


public class Person_TextualView implements Person_View {

    private final SystemSIGEE model;
    private final Scanner scanner;


    public Person_TextualView(Scanner scanner) {
        this.model = SystemSIGEE.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void display() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- [Gerenciar Pessoas] ---");
            System.out.println("1. Listar todas as Pessoas");
            System.out.println("2. Ver detalhes de uma Pessoa (Eventos/Vínculos)");
            System.out.println("3. Editar uma Pessoa (Nome/Email)");
            System.out.println("4. Excluir uma Pessoa");
            System.out.println("0. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    handleListAll();
                    break;
                case 2:
                    handleShowDetails();
                    break;
                case 3:
                    handleEdit();
                    break;
                case 4:
                    handleDelete();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }


    private void handleListAll() {
        Factory.getConcreteFactory().newListView(
                model.getAcademics(),
                "Todas as Pessoas",
                null
        ).display();
    }

    private void handleShowDetails() {
        Person person = findPerson();
        if (person == null) return;

        System.out.println("\n--- Detalhes: " + person.getName() + " ---");
        System.out.println("Email: " + person.getEmail());
        if (person instanceof UndergraduateStudent) {
            System.out.println("Matrícula: " + person.getIdentifier());
        } else {
            System.out.println("SIAPE: " + person.getIdentifier());
        }

        System.out.print("Deseja ver os Eventos que participou? (s/n): ");
        if (readString().equalsIgnoreCase("s")) {
            Factory.getConcreteFactory().newListView(
                    model.getEventsofPerson(person.getIdentifier(), "participant"),
                    "Eventos (Participante)",
                    null
            ).display();
        }

        System.out.print("Deseja ver os Eventos que organizou? (s/n): ");
        if (readString().equalsIgnoreCase("s")) {
            Factory.getConcreteFactory().newListView(
                    model.getEventsofPerson(person.getIdentifier(), "organizer"),
                    "Eventos (Organizador)",
                    null
            ).display();
        }

        System.out.print("Deseja ver os Vínculos desta pessoa? (s/n): ");
        if (readString().equalsIgnoreCase("s")) {
            Factory.getConcreteFactory().newListView(
                    model.getBondsOfPerson(person.getIdentifier()),
                    "Vínculos de " + person.getName(),
                    null
            ).display();
        }
    }

    private void handleEdit() {
        Person person = findPerson();
        if (person == null) return;

        try {
            System.out.print("Novo nome (atual: " + person.getName() + "): ");
            String newName = readString();
            if (!newName.isEmpty()) {
                person.setName(newName);
            }

            System.out.print("Novo email (atual: " + person.getEmail() + "): ");
            String newEmail = readString();
            if (!newEmail.isEmpty()) {
                person.setEmail(newEmail);
            }

            System.out.println("Pessoa atualizada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao atualizar: Dados inválidos (ex: email ou nome vazio).");
        }
    }

    private void handleDelete() {
        Person person = findPerson();
        if (person == null) return;

        System.out.print("Tem certeza que deseja excluir " + person.getName() + "? (s/n): ");
        if (readString().equalsIgnoreCase("s")) {
            if (model.deletePerson(person.getIdentifier())) {
                System.out.println("Pessoa excluída com sucesso.");
            } else {
                System.out.println("Erro ao excluir pessoa (pode estar em uso ou não foi encontrada).");
            }
        }
    }

    private Person findPerson() {
        System.out.print("\nDigite o ID da Pessoa (Matrícula/SIAPE): ");
        String id = readString();
        Person person = model.findPersonById(id);
        if (person == null) {
            System.out.println("Erro: Pessoa com ID '" + id + "' não encontrada.");
        }
        return person;
    }

    private int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            return -1;
        }
    }

    private String readString() {
        return scanner.nextLine();
    }
}