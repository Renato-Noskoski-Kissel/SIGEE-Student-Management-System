package Model.factory;

import Model.Factory;
import Model.SystemSIGEE;
import Model.domain.Event;
import Model.domain.Person;
import Model.domain.StudentEntity;
import View.*;
import View.gui.*;
import java.awt.*;
import java.util.List;
import java.util.Scanner;

public class GraphicConcreteFactory extends Factory {

    @Override
    public Event_View newEvent_View(Object owner, Scanner scanner) {
        return new Event_GraphicView((Dialog) owner);
    }

    @Override
    public Person_View newPerson_View(Object owner, Scanner scanner) {
        return new Person_GraphicView((Dialog) owner);
    }

    @Override
    public StudentEntity_View newStudentEntity_View(Object owner, Scanner scanner) {
        return new StudentEntity_GraphicView((Dialog) owner);
    }

    @Override
    public System_View newSystem_View() {
        return new System_GraphicView(SystemSIGEE.getInstance());
    }

    @Override
    public List_View newListView(List<?> list, String title, Object owner) {
        return new ListView(list, title, (Dialog) owner);
    }

    @Override
    public Find_View newFindView(Object owner, Scanner scanner) {
        return new FindView((Dialog) owner);
    }

    @Override
    public CreateObject_View newCreateObjectView(Object owner, Scanner scanner) {
        return new CreateObjectView((Dialog) owner);
    }
}
