package org.example.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Employee implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private String Role;
    private Department department;

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

    public Employee(String firstName, String lastName, String role, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        Role = role;
        this.department = department;
    }

    public Employee() {
    }
}