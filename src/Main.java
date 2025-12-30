import Model.Factory;
import Model.SystemSIGEE;
import Model.factory.IAbstractFactory;
import Model.factory.GraphicConcreteFactory;
import Model.factory.TextConcreteFactory;
import View.System_View;
import Model.dataSeeding;

public class Main {

    public static void main(String[] args) {
        IAbstractFactory factory;

        if (args.length > 0) {
            for (String arg: args) {
                if (arg.equals("--gui")) {
                    Factory.setConcreteFactory(new GraphicConcreteFactory());
                } else if (arg.equals("--terminal")) {
                    Factory.setConcreteFactory(new TextConcreteFactory());
                }
            }
        }
        dataSeeding.popularDados();
        System_View frontend = Factory.getConcreteFactory().newSystem_View();
        frontend.display();
    }
}