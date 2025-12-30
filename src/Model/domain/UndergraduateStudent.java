package Model.domain;

import Model.util.Validator;

import java.io.Serializable;

public class UndergraduateStudent extends Person implements Serializable {
    private final String enrollment;

    public UndergraduateStudent(String name, String email, String enrollment) {
        super(name, email);
        if(!Validator.isEnrollmentValid(enrollment)){
            throw new IllegalArgumentException();
        }
        this.enrollment = enrollment.trim();
    }

    @Override
    public String getIdentifier() {
        return enrollment;
    }
}
