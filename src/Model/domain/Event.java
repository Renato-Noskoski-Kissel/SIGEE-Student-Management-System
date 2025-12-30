package Model.domain;

import Model.patterns.IObserver;
import Model.patterns.ISubject;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import Model.util.Validator;

public class Event implements ISubject, Serializable {
    private String name;
    private String location;
    private LocalDateTime date;
    private final List<IObserver> observers;
    private final List<Person> participants;
    private final List<Person> organizers;
    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Event(String name, String location, LocalDateTime date) {
        if(!Validator.isNameValid(name) || !Validator.isLocationValid(location) || date == null || date.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException();
        }
        this.name = name.trim();
        this.location = location.trim();
        this.date = date;
        this.observers = new ArrayList<>();
        this.participants = new ArrayList<>();
        this.organizers = new ArrayList<>();
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if(!Validator.isLocationValid(location)){
            throw new IllegalArgumentException();
        }
        this.location = location.trim();
    }

    public String getDate() {
        if (this.date == null){ return "Data não definida";}
        return this.date.format(FORMATADOR);
    }

    public void setDate(LocalDateTime date) {
        if(date == null || date.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException();
        }
        this.date = date;
    }

    public List<Person> getParticipants() {
        return participants;
    }

    public List<Person> getOrganizers() {
        return organizers;
    }

    public List<IObserver> getObservers() {
        return observers;
    }

    public void addParticipants(Person person){
        if (!this.participants.contains(person)){
        this.participants.add(person);

        this.observers.add(person);
    }
    }

    public void removeParticipants(Person person){
            this.participants.remove(person);
            this.observers.remove(person);
    }

    public void addOrganizers(Person person){
        if (!this.organizers.contains(person)){
            this.organizers.add(person);

            this.observers.add(person);
        }
    }

    public void removeOrganizers(Person person){
        this.organizers.remove(person);
        this.observers.remove(person);
    }

    @Override
    public void addObserver(IObserver observer){
        if (!this.observers.contains(observer)) {
            this.observers.add(observer);
        }
    }

    @Override
    public void removeObserver(IObserver observer){
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message){
        for(IObserver observer : this.observers){
            observer.update(message);
        }
    }

    @Override
    public String toString(){
        return this.getName();
    }
}
