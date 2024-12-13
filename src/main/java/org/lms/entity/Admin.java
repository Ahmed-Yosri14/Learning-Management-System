package org.lms.entity;

public class Admin extends User {
    public Admin(int id, String email, String password, Integer role) {
        super(id, email, password, role);
    }
}
