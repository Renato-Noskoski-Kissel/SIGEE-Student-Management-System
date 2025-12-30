package Model;

import Model.domain.*;
import Model.patterns.DAOSerialization;
import Model.patterns.IObserver;
import Model.patterns.ISystemDAO;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SystemSIGEE implements Serializable {
    private List<Person> academics;
    private List<Role> roles;
    private List<StudentEntity> entities;
    private List<Bond> bonds;
    private transient ISystemDAO DAO;
    static SystemSIGEE uniqueInstance = new SystemSIGEE();

    private SystemSIGEE(){
        this.roles = new ArrayList<>();
        this.academics = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.bonds = new ArrayList<>();
        this.DAO = new DAOSerialization("sigee_dados");
    }

    public void setDao(ISystemDAO dao) {
        this.DAO = dao;
    }

    public void saveData() throws Exception {
        if (this.DAO != null) {
            this.DAO.salvar(this);
        }
    }

    public void loadData() throws Exception{
        if(this.DAO != null){
            SystemSIGEE loadedSystem = DAO.carregar();

            this.academics = loadedSystem.academics;
            this.bonds = loadedSystem.bonds;
            this.entities = loadedSystem.entities;
            this.roles = loadedSystem.roles;
        }
    }

    public static SystemSIGEE getInstance(){
        return uniqueInstance;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public List<Person> getAcademics() {
        return academics;
    }

    public List<StudentEntity> getEntities() {
        return entities;
    }

    public List<Bond> getBonds() {
        return bonds;
    }

    public Person findPersonById(String id){
        for(Person person : academics){
            if(person.getIdentifier().equals(id)){
                return person;
            }
        }
        return null;
    }

    public StudentEntity findEntityByName(String name){
        for(StudentEntity entity : entities){
            if(entity.getName().equals(name)){
                return entity;
            }
        }
        return null;
    }

    public Role findRoleByName(String name){
        for(Role role : roles){
            if(role.getName().equals(name)){
                return role;
            }
        }
        return null;
    }

    public Event findEventByName(String name){
        for (StudentEntity entity : entities) {
            for (Event event : entity.getEvents()) {
                if (event.getName().equals(name)) {
                    return event;
                }
            }
        }
        return null;
    }

    public Bond findBond(String personId, String entityName, String roleName){
        Person person = findPersonById(personId);
        StudentEntity entity = findEntityByName(entityName);
        Role role = findRoleByName(roleName);

        for(Bond bond : bonds){
            if(bond.getPerson().equals(person) && bond.getEntity().equals(entity) && bond.getRole().equals(role)){
                return bond;
            }
        }
        return null;
    }

    public boolean registerUndergraduateStudent(String name, String email, String enrollment){
        try{
            if (findPersonById(enrollment) != null) {
                return false;
            }
            for(Person person : academics){
                if(person.getEmail().equals(email.trim())){
                    return false;
                }
            }

            UndergraduateStudent newStudent = new UndergraduateStudent(name, email, enrollment);
            academics.add(newStudent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean registerProfessor(String name, String email, String siape){
        try{
            if (findPersonById(siape) != null) {
                return false;
            }
            for(Person person : academics){
                if(person.getEmail().equals(email.trim())){
                    return false;
                }
            }

            Professor newProfessor = new Professor(name, email, siape);
            academics.add(newProfessor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deletePerson(String identifier) {
        try{
            Person person = findPersonById(identifier);

            if (person == null) {
                return false;
            }

            for(StudentEntity se : this.getEntities()) {
                for (Event e : se.getEvents()) {
                    for (Person p : e.getOrganizers()) {
                        if (p.equals(person)) {
                            e.removeOrganizers(person);
                        }
                    }
                    for (Person p : e.getParticipants()) {
                        if (p.equals(person)) {
                            e.removeParticipants(person);
                        }
                    }
                }
                for (Bond b : this.getBondsOfEntity(se.getName())) {
                    if (b.getPerson().equals(person)) {
                        b.setStatus(StatusBond.INACTIVE);
                    }
                }
            }
            academics.remove(person);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean createRole(String name, String description, double payment){
        try{
            if (findRoleByName(name) != null) {
                return false;
            }

            Role newRole = new Role(name, description, payment);
            roles.add(newRole);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteRole(String name) {
        try {
            Role role = findRoleByName(name);

            if (role == null) {
                return false;
            }

            roles.remove(role);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean createEntity(String name, String description){
        try{
            if (findEntityByName(name) != null) {
                return false;
            }

            StudentEntity newEntity = new StudentEntity(name, description);
            entities.add(newEntity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteEntity(String name) {
        try {
            StudentEntity entity = findEntityByName(name);

            if (entity == null) {
                return false;
            }

            for(Event e : entity.getEvents()){
                deleteEvent(name, e.getName());
            }
            for(Bond b : getBondsOfEntity(name)){
                b.setStatus(StatusBond.INACTIVE);
            }
            entities.remove(entity);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean createEvent(String entityName, String name, String location, LocalDateTime date){
        try {
            if (findEventByName(name) != null) {
                return false;
            }

            StudentEntity entity = findEntityByName(entityName);
            Event newEvent = new Event(name, location, date);
            entity.addEvent(newEvent);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean addParticipantToEvent(String personID, String eventName) {
        try {
            Person person = findPersonById(personID);
            Event event = findEventByName(eventName);

            event.addParticipants(person);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addOrganizerToEvent(String personID, String eventName) {
        try {
            Person person = findPersonById(personID);
            Event event = findEventByName(eventName);

            event.addOrganizers(person);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    public boolean deleteEvent(String entityName, String eventName) {
        try {
            StudentEntity entity = findEntityByName(entityName);
            Event event = findEventByName(eventName);

            if (event == null) {
                return false;
            }

            for(Person p : event.getOrganizers()){
                event.removeOrganizers(p);
            }
            for(Person p : event.getParticipants()){
                event.removeParticipants(p);
            }
            for(IObserver p : event.getObservers()){
                event.removeObserver(p);
            }
            entity.removeEvent(event);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public List<Event> getAllEvents(){
        List<Event> events = new ArrayList<>();

        for(StudentEntity se : this.getEntities()){
            for(Event e : se.getEvents()){
                events.add(e);
            }
        }
        return events;
    }

    public List<Event> getEventsofPerson(String personId, String typeOfParticipation){
        List<Event> events = new ArrayList<>();

        for (Event event : getAllEvents()) {
            List<Person> listToCheck;

            if (typeOfParticipation.equals("participant")) {
                listToCheck = event.getParticipants();
            } else {
                listToCheck = event.getOrganizers();
            }

            for (Person person : listToCheck) {
                if (person.getIdentifier().equals(personId)) {
                    events.add(event);
                    break;
                }
            }
        }
        return events;
    }

    public StudentEntity getEntityOfEvent(String eventName){
        for(StudentEntity se : this.getEntities()){
            for(Event e : se.getEvents()){
                if(e == findEventByName(eventName));{
                    return se;
                }
            }
        }
        return null;
    }

    public boolean createBond(String personID, String entityName, String roleName){
        try {
            Person person = findPersonById(personID);
            StudentEntity entity = findEntityByName(entityName);
            Role role = findRoleByName(roleName);

            Bond newBond = new Bond(person, entity, role);
            bonds.add(newBond);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean inactivateBond(String personId, String entityName, String roleName) {
        try {
            Bond bond = findBond(personId, entityName, roleName);
            bond.setStatus(StatusBond.INACTIVE);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<Bond> getBondsOfEntity(String entityName){
        ArrayList<Bond> entityBonds = new ArrayList<>();

        for(Bond bond : bonds){
            if(bond.getEntity().equals(findEntityByName(entityName))){
                entityBonds.add(bond);
            }
        }
        return entityBonds;
    }

    public Bond getBondOfPerson(String entityName, String personId){

        for(Bond bond : bonds){
            if(bond.getEntity().equals(findEntityByName(entityName)) && bond.getPerson().equals(findPersonById(personId))){
                return bond;
            }
        }
        return null;
    }

    public List<Bond> getBondsOfPerson(String personId){
        List<Bond> bonds = new ArrayList<>();

        for(Bond bond : this.bonds){
            if(bond.getPerson().equals(findPersonById(personId))){
                bonds.add(bond);
            }
        }
        return bonds;
    }

    public ArrayList<Bond> getActiveBondsOfEntity(String entityName){
        ArrayList<Bond> entityActiveBonds = new ArrayList<>();

        for(Bond bond : bonds){
            if(bond.getEntity().equals(findEntityByName(entityName)) && bond.getStatus().equals(StatusBond.ACTIVE)){
                entityActiveBonds.add(bond);
            }
        }
        return entityActiveBonds;
    }

}
