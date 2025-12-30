package View.textual;

import View.List_View;
import java.util.List;
import java.util.Scanner;


public class List_TextualView implements List_View {

    private final List<?> list;
    private final String title;

    public List_TextualView(List<?> list, String title, Object owner) {
        this.list = list;
        this.title = title;
    }

    @Override
    public void display() {
        System.out.println("\n--- [ " + title + " ] ---");

        if (list == null || list.isEmpty()) {
            System.out.println("[ A lista está vazia ]");
        } else {
            for (int i = 0; i < list.size(); i++) {
                // Chama o método .toString() de cada objeto (Person, Event, Bond, etc.)
                System.out.println((i + 1) + ". " + list.get(i).toString());
            }
        }

        System.out.println("---------------------------------");
        pressEnterToContinue();
    }

    private void pressEnterToContinue() {
        System.out.println("\n[Pressione ENTER para continuar...]");
        new Scanner(System.in).nextLine();
    }
}