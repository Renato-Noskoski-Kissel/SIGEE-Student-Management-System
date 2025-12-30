package Model.factory;

import Model.Factory;
import Model.SystemSIGEE;
import Model.domain.Event;
import Model.domain.Person;
import Model.domain.StudentEntity;
import View.*;
import View.gui.CreateObjectView;
import View.gui.FindView;
import View.textual.*;

import java.awt.*;
import java.util.List;
import java.util.Scanner;

public class TextConcreteFactory extends Factory {
    @Override
    public Event_View newEvent_View(Object owner, Scanner scanner) {
        return new Event_TextualView(scanner);
    }

    @Override
    public Person_View newPerson_View(Object owner, Scanner scanner) {
        return new Person_TextualView(scanner);
    }

    @Override
    public StudentEntity_View newStudentEntity_View(Object owner, Scanner scanner) {
        return new StudentEntity_TextualView(scanner);
    }

    @Override
    public System_View newSystem_View() {
        return new System_TextualView(SystemSIGEE.getInstance());
    }

    // GUI não usada nesta versão textual
    @Override
    public List_View newListView(java.util.List<?> list, String title, Object owner){
        return new List_TextualView(list, title, owner);
    }

    @Override
    public Find_View newFindView(Object owner, Scanner scanner) {
        return new Find_TextualView(owner, scanner);
    }

    @Override
    public CreateObject_View newCreateObjectView(Object owner, Scanner scanner) {
        return new CreateObject_TextualView(owner, scanner);
    }
}
