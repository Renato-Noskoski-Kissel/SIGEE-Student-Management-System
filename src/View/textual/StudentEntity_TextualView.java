package View.textual;

import Model.Factory;
import Model.SystemSIGEE;
import Model.domain.StudentEntity;
import View.StudentEntity_View;
import java.util.Scanner;


public class StudentEntity_TextualView implements StudentEntity_View {

    private final SystemSIGEE model;
    private final Scanner scanner;

    public StudentEntity_TextualView(Scanner scanner) {
        this.model = SystemSIGEE.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void display() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- [Gerenciar Entidades] ---");
            System.out.println("1. Listar todas as Entidades");
            System.out.println("2. Ver detalhes de uma Entidade (Eventos/Vínculos)");
            System.out.println("3. Editar uma Entidade (Nome/Descrição)");
            System.out.println("4. Excluir uma Entidade");
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
        // Usa a fábrica para criar a ListView
        Factory.getConcreteFactory().newListView(
                model.getEntities(),
                "Todas as Entidades",
                null // 'owner' é null no modo textual
        ).display();
    }


    private void handleShowDetails() {
        StudentEntity entity = findEntity(); // Chama o método auxiliar
        if (entity == null) return;

        System.out.println("\n--- Detalhes: " + entity.getName() + " ---");
        System.out.println("Descrição: " + entity.getDescription());

        System.out.print("Deseja ver os Eventos desta entidade? (s/n): ");
        if (readString().equalsIgnoreCase("s")) {
            Factory.getConcreteFactory().newListView(
                    entity.getEvents(), // Pega eventos da própria entidade
                    "Eventos de " + entity.getName(),
                    null
            ).display();
        }

        System.out.print("Deseja ver os Vínculos (ativos) desta entidade? (s/n): ");
        if (readString().equalsIgnoreCase("s")) {
            Factory.getConcreteFactory().newListView(
                    model.getActiveBondsOfEntity(entity.getName()), // Pega vínculos do modelo
                    "Vínculos Ativos de " + entity.getName(),
                    null
            ).display();
        }
    }

    private void handleEdit() {
        StudentEntity entity = findEntity();
        if (entity == null) return;

        try {
            System.out.print("Novo nome (atual: " + entity.getName() + "): ");
            String newName = readString();
            if (!newName.isEmpty()) { // Permite pular
                entity.setName(newName);
            }

            System.out.print("Nova descrição (atual: " + entity.getDescription() + "): ");
            String newDesc = readString();
            if (!newDesc.isEmpty()) { // Permite pular
                entity.setDescription(newDesc);
            }

            System.out.println("Entidade atualizada com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao atualizar: Dados inválidos (ex: nome vazio).");
        }
    }


    private void handleDelete() {
        StudentEntity entity = findEntity();
        if (entity == null) return;

        System.out.print("Tem certeza que deseja excluir " + entity.getName() + "? (s/n): ");
        if (readString().equalsIgnoreCase("s")) {
            if (model.deleteEntity(entity.getName())) {
                System.out.println("Entidade excluída com sucesso.");
            } else {
                System.out.println("Erro ao excluir entidade (pode estar em uso ou não foi encontrada).");
            }
        }
    }


    private StudentEntity findEntity() {
        System.out.print("\nDigite o nome da Entidade: ");
        String name = readString();
        StudentEntity entity = model.findEntityByName(name);
        if (entity == null) {
            System.out.println("Erro: Entidade '" + name + "' não encontrada.");
        }
        return entity;
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