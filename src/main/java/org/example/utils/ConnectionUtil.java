package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            String dataBase = "emp_dep";
            String URL = "jdbc:mysql://localhost:3306/";
            java.sql.Connection con = DriverManager.getConnection(URL + dataBase , "root" , "azerty123");

            return con;

        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
