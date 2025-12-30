package Model.patterns;

import Model.SystemSIGEE;

import java.io.IOException;

public interface ISystemDAO {

    public void salvar(SystemSIGEE sistema) throws IOException;

    public SystemSIGEE carregar() throws IOException, ClassNotFoundException;

}
