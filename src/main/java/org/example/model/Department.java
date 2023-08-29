package org.example.model;


import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Department implements Serializable {

    private int id;
    private String name;
    private List<Employee> employees;

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Department(String name) {
        this.name = name;
    }

    public Department() {
    }
}
