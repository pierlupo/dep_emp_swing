package org.example.controller;

import org.example.dao.EmployeeDao;
import org.example.model.Employee;

import java.sql.SQLException;
import java.util.List;

public class EmployeeController {

    private EmployeeDao employeeDao;

    public EmployeeController() {

        this.employeeDao = new EmployeeDao();

    }

    public List<Employee> getAllEmployees() throws SQLException {

        return employeeDao.getAllEmployees();
    }

    public Employee getEmployeeById(int id) {
        return employeeDao.getEmployeeById(id);
    }

    public void addEmployee(Employee employee) throws SQLException {
        employeeDao.addEmployee(employee);
    }

    public void updateEmployee(Employee employee) throws SQLException {
        employeeDao.updateEmployee(employee);
    }

    public void deleteEmployee(int id) throws SQLException {
        employeeDao.deleteEmployee(id);
    }
}
