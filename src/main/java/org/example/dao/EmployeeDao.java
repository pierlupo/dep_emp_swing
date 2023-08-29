package org.example.dao;

import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Role;
import org.example.utils.ConnectionUtil;
import java.sql.Connection;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class EmployeeDao {

    private Connection con;
    private DepartmentDao departmentDao;
    private PreparedStatement ps;

    public int addEmployee(Employee employee) throws SQLException {

        con = ConnectionUtil.getConnection();
        ps = con.prepareStatement("INSERT INTO `employee` (`first_name`,`last_name`,`role`) VALUES (?, ?, ?) ");
        ps.setString(1, employee.getFirstName());
        ps.setString(2, employee.getLastName());
        ps.setString(3, employee.getRole());
        int n = ps.executeUpdate();
//        con.close();
        return n;

    }

    public void deleteEmployee(int id) throws SQLException {

        con = ConnectionUtil.getConnection();
        ps = con.prepareStatement("DELETE FROM `employee` WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
        con.close();
    }

    public int updateEmployee(Employee employee) throws SQLException {

        con = ConnectionUtil.getConnection();
        ps = con.prepareStatement("UPDATE `contact` SET name = ?, number = ? WHERE id = ?");
        ps.setString(1, employee.getFirstName());
        ps.setString(2, employee.getLastName());
        ps.setString(3, employee.getRole());
        ps.setInt(3, employee.getId());
        ps.executeUpdate();
        int n = ps.executeUpdate();
        con.close();
        return n;

    }

    public List<Employee> getAllEmployees() throws  SQLException {
        List<Employee> empList = new ArrayList<>();
        String query = "SELECT * FROM employee";
        con = ConnectionUtil.getConnection();
        try (PreparedStatement preparedStatement = con.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setFirstName(resultSet.getString("first_name"));
                String roleOrdinal = resultSet.getString("role");
                employee.setRole(String.valueOf(Role.valueOf(roleOrdinal)));
                int id = resultSet.getInt("departement_id");
                Department department;
                if( departmentDao.getDepartmentById(id)!= null){
                    department = departmentDao.getDepartmentById(id);
                }else{
                    department = null;
                }
                // Vous devrez charger le département associé ici
                employee.setDepartment(department);
                empList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empList;

    }
    public Employee getEmployeeById(int id) {

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Employee employee = null;

        try {

            String query = "SELECT * FROM employee WHERE id = ?";
            statement = con.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                employee = new Employee();
                employee.setId(resultSet.getInt("id"));
                employee.setFirstName(resultSet.getString("first_name"));
                employee.setLastName(resultSet.getString("last_name"));
                employee.setRole(resultSet.getString("role"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    public Employee searchEmp(int id) {

        con = ConnectionUtil.getConnection();
        try {
            ps = con.prepareStatement("SELECT * FROM `Employee` WHERE id=?", ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
            ps.setInt(1, id);

            ResultSet result = ps.executeQuery();
            //result.getInt("id");
            Employee employee = null;
            if (result.first()) {
                employee = new Employee();
                employee.setId(result.getInt("id"));
                employee.setFirstName(result.getString("first_name"));
                employee.setLastName(result.getString("last_name"));
                employee.setRole(result.getString(("Role")));
                System.out.println(employee);
            }
            con.close();
           return employee;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public void loadDataEmp(DefaultTableModel tableModel) {

        try (Connection conn = ConnectionUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("select * from Employee");
            ResultSetMetaData metaData = rs.getMetaData();

            // Names of columns
            Vector<String> columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            // Data of the table
            Vector<Vector<Object>> dataemp = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int i = 1; i <= columnCount; i++) {
                    vector.add(rs.getObject(i));
                }
                dataemp.add(vector);
            }

            tableModel.setDataVector(dataemp, columnNames);
        } catch (Exception e) {

        }
    }




}
