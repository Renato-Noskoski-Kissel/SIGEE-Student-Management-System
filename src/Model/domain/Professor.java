package Model.domain;

import Model.util.Validator;

import java.io.Serializable;

public class Professor extends Person implements Serializable {
    private final String siape;

    public Professor(String name, String email, String siape) {
        super(name, email);
        if(!Validator.isSiapeValid(siape)){
            throw new IllegalArgumentException();
        }
        this.siape = siape.trim();
    }

    @Override
    public String getIdentifier() {
        return siape;
    }
}
