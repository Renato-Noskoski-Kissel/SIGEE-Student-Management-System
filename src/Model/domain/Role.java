package Model.domain;

import Model.util.Validator;
import com.sun.javafx.binding.StringFormatter;

import java.io.Serializable;

public class Role implements Serializable {
    String name;
    String description;
    double payment;

    public Role(String name, String description, double payment) {
    if(!Validator.isNameValid(name) || description.trim().isEmpty() || description == null || description.length() <2 || payment < 0.0){
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.description = description;
        this.payment = payment;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description.trim().isEmpty() || description == null || description.length() <2){
            throw new IllegalArgumentException();
        }
        this.description = description;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        if(payment < 0.0){
            throw new IllegalArgumentException();
        }
        this.payment = payment;
    }

    @Override
    public String toString(){
        return this.getName();
    }
}
