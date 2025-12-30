package Model.factory;

import Model.SystemSIGEE;
import Model.domain.Event;
import Model.domain.Person;
import Model.domain.StudentEntity;
import View.*;
import View.gui.CreateObjectView;
import View.gui.FindView;
import View.gui.System_GraphicView;
import java.util.List;
import java.util.Scanner;

public interface IAbstractFactory {
    public System_View newSystem_View();
    public Event_View newEvent_View(Object owner, Scanner scanner);
    public Person_View newPerson_View(Object owner, Scanner scanner);
    public StudentEntity_View newStudentEntity_View(Object owner, Scanner scanner);
    public List_View newListView(List<?> list, String title, Object owner);
    public Find_View newFindView(Object owner, Scanner scanner);
    public CreateObject_View newCreateObjectView(Object owner, Scanner scanner);
}
