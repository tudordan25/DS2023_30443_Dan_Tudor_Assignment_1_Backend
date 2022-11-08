package com.example.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "admin")
public class Admin extends User implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    public Admin(String username, String email, String password) {
        super(username, email, password);
    }

    public Admin() {}

    public Long getId() {
        return id;
    }
}
