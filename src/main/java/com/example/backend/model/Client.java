package com.example.backend.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "client")
public class Client extends User {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;

    @OneToMany(mappedBy = "client")
    private Set<Device> devices;

    public Client(String username, String email, String password, String firstName, String lastName) {
        super(username, email, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Client() {}

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Device> getDevices() {
        return devices;
    }

    public void setMeteringDevices(Set<Device> meteringDevices) {
        this.devices = meteringDevices;
    }
}