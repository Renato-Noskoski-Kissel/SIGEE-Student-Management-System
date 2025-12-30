package View.textual;

import Model.Factory;
import Model.SystemSIGEE;
import Model.factory.IAbstractFactory; // Importe sua interface de fábrica
import View.System_View; // Importe sua interface de View
import java.util.Scanner;

public class System_TextualView implements System_View {

    private final IAbstractFactory factory;
    private final SystemSIGEE model;
    private final Scanner scanner;


    public System_TextualView(SystemSIGEE model) {
        this.factory = Factory.getConcreteFactory();
        this.model = model;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void display() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- [SIGEE - Menu Principal] ---");
            System.out.println("1. Gerenciar Pessoas");
            System.out.println("2. Gerenciar Entidades");
            System.out.println("3. Gerenciar Eventos");
            System.out.println("4. Criar Novo Objeto");
            System.out.println("5. Buscar Objeto");
            System.out.println("6. Salvar Dados");
            System.out.println("7. Carregar Dados");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    factory.newPerson_View(null, scanner).display();
                    break;
                case 2:
                    factory.newStudentEntity_View(null, scanner).display();
                    break;
                case 3:
                    factory.newEvent_View(null, scanner).display();
                    break;
                case 4:
                    factory.newCreateObjectView(null, scanner).display();
                    break;
                case 5:
                    factory.newFindView(null, scanner).display();
                    break;
                case 6:
                    handleSaveData();
                    break;
                case 7:
                    handleLoadData();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
        System.out.println("Sistema finalizado.");
    }

    private void handleSaveData() {
        try {
            model.saveData();
            System.out.println("\n>>> Dados salvos com sucesso! <<<");
        } catch (Exception e) {
            System.out.println("\n!!! ERRO AO SALVAR: " + e.getMessage() + " !!!");
        }
        pressEnterToContinue();
    }


    private void handleLoadData() {
        try {
            model.loadData();
            System.out.println("\n>>> Dados carregados com sucesso! <<<");
        } catch (Exception e) {
            System.out.println("\n!!! ERRO AO CARREGAR: " + e.getMessage() + " !!!");
        }
        pressEnterToContinue();
    }

    private int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida. Por favor, digite um número.");
            return -1;
        }
    }

    private void pressEnterToContinue() {
        System.out.println("\n[Pressione ENTER para continuar...]");
        scanner.nextLine();
    }
}
