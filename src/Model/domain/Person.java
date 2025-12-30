package Model.domain;

import Model.patterns.IObserver;
import Model.util.Validator;

import java.io.Serializable;

abstract public class Person implements IObserver, Serializable {
    private String name;
    private String email;

    public Person(String name, String email) {
        if(!Validator.isNameValid(name) || !Validator.isEmailValid(email)){
            throw new IllegalArgumentException();
        }
        this.name = name.trim();
        this.email = email.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(!Validator.isNameValid(name)){
            throw new IllegalArgumentException();
        }
        this.name = name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(!Validator.isEmailValid(email)){
            throw new IllegalArgumentException();
        }
        this.email = email.trim();
    }

    abstract public String getIdentifier();

    public void update(String message){
        //uma implementação realística usaria uma API para enviar o email
    }

    @Override
    public String toString(){
        return this.getName() + " (ID: " + this.getIdentifier() + ")";
    }
}
