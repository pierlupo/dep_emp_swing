package org.example.utils;


import org.example.dao.EmployeeDao;
import org.example.model.Employee;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class EmployeeTableModel extends AbstractTableModel {
    private List<Employee> employeeList;
    private String[] columnNames = {"ID", "Lastname","Firstname","Role"};

    public EmployeeTableModel(List<Employee> employeeList) {

        this.employeeList = employeeList;
    }

    @Override
    public int getRowCount() {
        return employeeList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee employee = employeeList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return employee.getId();
            case 1:
                return employee.getLastName();
            case 2:
                return employee.getFirstName();
            case 3:
                return employee.getRole();
            default:
                return null;
        }
    }
}
