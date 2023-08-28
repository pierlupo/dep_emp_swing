package org.example.model;

import lombok.Data;

@Data
public class Employee {

    private int id;
    private String firstName;
    private String lastName;
    private String Role;

    public Employee(int id, String firstName, String lastName, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        Role = role;
    }

    public Employee(String firstName, String lastName, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        Role = role;
    }

    public Employee() {
    }
}