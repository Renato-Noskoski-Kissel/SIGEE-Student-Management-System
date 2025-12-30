package View.textual;

import Model.Factory;
import Model.SystemSIGEE;
import Model.domain.Event;
import Model.domain.Person;
import Model.domain.StudentEntity;
import View.Event_View;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Event_TextualView implements Event_View {

    private final SystemSIGEE model;
    private final Scanner scanner;
    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");



    public Event_TextualView(Scanner scanner) {
        this.model = SystemSIGEE.getInstance();
        this.scanner = scanner;
    }

    @Override
    public void display() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- [Gerenciar Eventos] ---");
            System.out.println("1. Listar todos os Eventos");
            System.out.println("2. Ver detalhes de um Evento (Participantes/Organizadores)");
            System.out.println("3. Editar um Evento (Nome/Local/Data)");
            System.out.println("4. Adicionar Participante a um Evento");
            System.out.println("5. Adicionar Organizador a um Evento");
            System.out.println("6. Notificar Observadores de um Evento");
            System.out.println("7. Excluir um Evento");
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
                    handleAddParticipant();
                    break;
                case 5:
                    handleAddOrganizer();
                    break;
                case 6:
                    handleNotify();
                    break;
                case 7:
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
                model.getAllEvents(),
                "Todos os Eventos",
                null
        ).display();
    }


    private void handleShowDetails() {
        Event event = findEvent(); // Chama o método auxiliar
        if (event == null) return;

        StudentEntity owner = model.getEntityOfEvent(event.getName());

        System.out.println("\n--- Detalhes: " + event.getName() + " ---");
        System.out.println("Organizado por: " + (owner != null ? owner.getName() : "Desconhecido"));
        System.out.println("Local: " + event.getLocation());
        System.out.println("Data: " + event.getDate());

        System.out.print("Deseja ver os Participantes? (s/n): ");
        if (readString().equalsIgnoreCase("s")) {
            Factory.getConcreteFactory().newListView(
                    event.getParticipants(),
                    "Participantes de " + event.getName(),
                    null
            ).display();
        }

        System.out.print("Deseja ver os Organizadores? (s/n): ");
        if (readString().equalsIgnoreCase("s")) {
            Factory.getConcreteFactory().newListView(
                    event.getOrganizers(),
                    "Organizadores de " + event.getName(),
                    null
            ).display();
        }
    }

    private void handleEdit() {
        Event event = findEvent();
        if (event == null) return;

        try {
            System.out.print("Novo nome (atual: " + event.getName() + "): ");
            String newName = readString();
            if (!newName.isEmpty()) {
                event.setName(newName);
            }

            System.out.print("Novo local (atual: " + event.getLocation() + "): ");
            String newLoc = readString();
            if (!newLoc.isEmpty()) {
                event.setLocation(newLoc);
            }

            System.out.print("Nova data (dd/MM/yyyy HH:mm) (atual: " + event.getDate() + "): ");
            String newDateStr = readString();
            if (!newDateStr.isEmpty()) {
                LocalDateTime newDate = LocalDateTime.parse(newDateStr, FORMATADOR);
                event.setDate(newDate);
            }

            System.out.println("Evento atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao atualizar: Dados inválidos (ex: nome vazio).");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar: Formato de data inválido. Use dd/MM/yyyy HH:mm");
        }
    }

    /**
     * Adiciona um participante a um evento.
     */
    private void handleAddParticipant() {
        System.out.println("\n--- Adicionar Participante ---");
        Event event = findEvent();
        if (event == null) return;

        Person person = findPerson();
        if (person == null) return;

        if (model.addParticipantToEvent(person.getIdentifier(), event.getName())) {
            System.out.println(person.getName() + " adicionado como participante de " + event.getName());
        } else {
            System.out.println("Erro ao adicionar participante.");
        }
    }

    /**
     * Adiciona um organizador a um evento.
     */
    private void handleAddOrganizer() {
        System.out.println("\n--- Adicionar Organizador ---");
        Event event = findEvent();
        if (event == null) return;

        Person person = findPerson();
        if (person == null) return;

        if (model.addOrganizerToEvent(person.getIdentifier(), event.getName())) {
            System.out.println(person.getName() + " adicionado como organizador de " + event.getName());
        } else {
            System.out.println("Erro ao adicionar organizador.");
        }
    }

    /**
     * Dispara a notificação (simulada) para todos os observadores do evento.
     */
    private void handleNotify() {
        Event event = findEvent();
        if (event == null) return;

        System.out.print("Digite a mensagem de notificação: ");
        String msg = readString();

        event.notifyObservers(msg);
        System.out.println("Notificações enviadas para " + event.getObservers().size() + " observadores.");
    }

    /**
     * Pede um nome, encontra um evento, pede confirmação e o exclui.
     */
    private void handleDelete() {
        Event event = findEvent();
        if (event == null) return;

        StudentEntity owner = model.getEntityOfEvent(event.getName());
        if (owner == null) {
            System.out.println("Erro: Não foi possível encontrar a entidade dona deste evento.");
            return;
        }

        System.out.print("Tem certeza que deseja excluir " + event.getName() + "? (s/n): ");
        if (readString().equalsIgnoreCase("s")) {
            if (model.deleteEvent(owner.getName(), event.getName())) {
                System.out.println("Evento excluído com sucesso.");
            } else {
                System.out.println("Erro ao excluir evento.");
            }
        }
    }

    // --- MÉTODOS AUXILIARES ---

    private Event findEvent() {
        System.out.print("\nDigite o nome do Evento: ");
        String name = readString();
        Event event = model.findEventByName(name);
        if (event == null) {
            System.out.println("Erro: Evento '" + name + "' não encontrado.");
        }
        return event;
    }

    private Person findPerson() {
        System.out.print("Digite o ID da Pessoa (Matrícula/SIAPE): ");
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