package Model.domain;

import Model.util.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentEntity implements Serializable {
    private String name;
    private String description;
    private final List<Event> events;

    public StudentEntity(String name, String description) {
        if(!Validator.isNameValid(name) || description.trim().isEmpty() || description == null || description.length() <2){
            throw new IllegalArgumentException();
        }
        this.name = name.trim();
        this.description = description.trim();
        this.events = new ArrayList<>();
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
        if(description.trim().isEmpty() || description == null || description.length() < 2){
            throw new IllegalArgumentException();
        }
        this.description = description.trim();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event){
        if (!this.events.contains(event)) {
            this.events.add(event);
        }
    }

    public void removeEvent(Event event){
        this.events.remove(event);
    }

    @Override
    public String toString(){
        return this.getName();
    }

}
