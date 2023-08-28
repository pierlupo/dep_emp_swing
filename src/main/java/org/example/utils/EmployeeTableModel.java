package org.example.utils;


import org.example.dao.EmployeeDao;
import org.example.model.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;

public class EmployeeTableModel {

    private DefaultTableModel model;
    private JPanel contentPanel;
    private JTable jTable;
    private JFrame jFrame;
    private String[] columnNames = {"Id","Firstname","Lastname","Role"};

    EmployeeTableModel() throws SQLException {

        contentPanel = new JPanel();
        Employee employee = new Employee();
        EmployeeDao employeeDao = new EmployeeDao();

        jFrame = new JFrame();
        jFrame.setTitle("Employee Management System");

        model = new DefaultTableModel(null, columnNames);

        List<Employee> data = employeeDao.getAllEmployees(employee);

        data.forEach(e ->{
            model.addRow(new Object[]{e.getId(),e.getFirstName(),e.getLastName(),e.getRole()});
        });

        employeeDao.loadDataEmp(model);

        jTable = new JTable(model);
        jTable.setBounds(30, 40, 800, 500);
        JScrollPane sp = new JScrollPane(jTable);
        jFrame.add(sp);
        jFrame.setSize(800, 750);
        jFrame.setVisible(true);
    }
}
