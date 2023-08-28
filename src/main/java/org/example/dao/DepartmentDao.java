package org.example.dao;

import org.example.model.Department;
import org.example.utils.ConnectionUtil;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DepartmentDao {

    private Connection con;

    private PreparedStatement ps;

    public DepartmentDao() {
            con = ConnectionUtil.getConnection();
    }

    public int addDepartment(Department department) throws SQLException {

        con = ConnectionUtil.getConnection();
        ps = con.prepareStatement("INSERT INTO `department` (`name`) VALUES (?) ");
        ps.setString(1, department.getName());
        int n = ps.executeUpdate();
        con.close();
        return n;

    }

    public void deleteDepartment(int id) throws SQLException {

        con = ConnectionUtil.getConnection();
        ps = con.prepareStatement("DELETE FROM `department` WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        con.close();
    }

    public int updateDepartment(Department department) throws SQLException {

        con = ConnectionUtil.getConnection();
        ps = con.prepareStatement("UPDATE `department` SET name = ? WHERE id = ?");
        ps.setString(1, department.getName());
        ps.setInt(2, department.getId());
        ps.executeUpdate();
        int n = ps.executeUpdate();
        con.close();
        return n;

    }

    public List<Department> getAllDepartments() throws  SQLException {
        List<Department> depList = new ArrayList<>();
        con = ConnectionUtil.getConnection();
        ps = con.prepareStatement("SELECT * FROM department ");
        ps.executeQuery();
        con.close();
        return depList;

    }


    public Department getDepartmentById(int id) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Department department = null;

        try {

            String query = "SELECT * FROM department WHERE id = ?";
            statement = con.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                department = new Department();
                department.setId(resultSet.getInt("id"));
                department.setName(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return department;
    }

    public Department getDepartmentByName(String name) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Department department = null;

        try {

            String query = "SELECT * FROM department WHERE name = ?";
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("name");


                department = new Department(id,name);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return department;
    }

    public Department searchDep(int id) {

        con = ConnectionUtil.getConnection();
        try {
            ps = con.prepareStatement("SELECT * FROM `department` WHERE id=?");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            Department department = null;
            if (result.first()) {
                department = new Department();
                department.setId(result.getInt("id"));
                department.setName(result.getString("name"));
                System.out.println(department);
            }
            con.close();
            return department;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void loadDataDep(DefaultTableModel tableModel) {

        try (Connection conn = ConnectionUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("select * from Department");
            ResultSetMetaData metaData = rs.getMetaData();

            // Names of columns
            Vector<String> columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            // Data of the table
            Vector<Vector<Object>> datadep = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int i = 1; i <= columnCount; i++) {
                    vector.add(rs.getObject(i));
                }
                datadep.add(vector);
            }

            tableModel.setDataVector(datadep, columnNames);
        } catch (Exception e) {

        }
    }
}
