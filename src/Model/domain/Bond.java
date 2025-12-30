package Model.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class Bond implements Serializable {
    private final Person person;
    private final StudentEntity entity;
    private Role role;
    private LocalDate entryDate;
    private LocalDate departureDate;
    private StatusBond status;

    public Bond(Person person, StudentEntity entity, Role role) {
        this.person = person;
        this.entity = entity;
        this.role = role;
        this.status = StatusBond.ACTIVE;
    }

    public Person getPerson() {
        return person;
    }

    public StudentEntity getEntity() {
        return entity;
    }

    public Role getRole() {
        return role;
    }

    public StatusBond getStatus() {
        return status;
    }

    public void setStatus(StatusBond status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return String.format("%s - %s (%s)", this.person, this.role, this.status);
    }
}
