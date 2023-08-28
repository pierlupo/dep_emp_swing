package org.example.controller;

import org.example.dao.DepartmentDao;
import org.example.model.Department;

import java.sql.SQLException;
import java.util.List;

public class DepartmentController {

    private DepartmentDao departmentDao;

    public DepartmentController() {
        this.departmentDao = new DepartmentDao();
    }

    public List<Department> getAllDepartments() throws SQLException {
        return departmentDao.getAllDepartments();
    }

    public Department getDepartmentById(int id) {
        return departmentDao.getDepartmentById(id);
    }

    public void addDepartment(Department department) throws SQLException {
        departmentDao.addDepartment(department);
    }

    public Department getDepartmentByName(String name) {
        return departmentDao.getDepartmentByName(name);
    }
    public void updateDepartment(Department departement) throws SQLException {
        departmentDao.updateDepartment(departement);
    }

    public void deleteDepartment(Department department) {
        departmentDao.deleteDepartment(department.getId());
    }
}
