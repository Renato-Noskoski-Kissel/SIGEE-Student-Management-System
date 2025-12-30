package Model.patterns;

import Model.SystemSIGEE;

import java.io.*;

public class DAOSerialization implements ISystemDAO{
    private final String fileName;

    public DAOSerialization(String fileName){
        this.fileName = fileName;
    }

    @Override
    public void salvar(SystemSIGEE sistema) throws IOException {
        FileOutputStream file = new FileOutputStream(fileName);
        ObjectOutputStream object = new ObjectOutputStream(file);
        object.writeObject(sistema);
        object.close();
    }

    @Override
    public SystemSIGEE carregar() throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(fileName);
        ObjectInputStream object = new ObjectInputStream(file);
        return (SystemSIGEE) object.readObject();
    }
}
