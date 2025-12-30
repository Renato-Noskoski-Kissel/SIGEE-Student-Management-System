package Model;

import Model.factory.GraphicConcreteFactory;
import Model.factory.IAbstractFactory;

public abstract class Factory implements IAbstractFactory {
    private static Factory concreteFactory;

    static {
        concreteFactory = new GraphicConcreteFactory();
    }

    public static Factory getConcreteFactory() {
        return concreteFactory;
    }

    public static void setConcreteFactory(Factory factory){
        concreteFactory = factory;
    }
}
