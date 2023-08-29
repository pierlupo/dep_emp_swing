package org.example;

import org.example.view.EmployeeUI;
import org.example.view.MainFrame;

import javax.swing.*;
import java.sql.SQLException;

public class Main {

        public static void main(String[] args) {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {

                    try {
                        EmployeeUI employeeUI = new EmployeeUI();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }


                }
            });
        }
    }
