package org.lms.entity;

import jakarta.persistence.Entity;

@Entity
public class Student extends User {
    public Student(int id, String email, String password, Integer role) {
        super(id, email, password, role);
    }

    public Student() {

    }
}
