package org.example.view;

import lombok.Data;
import org.example.controller.DepartmentController;
import org.example.controller.EmployeeController;
import org.example.model.Department;
import org.example.model.Employee;
import org.example.model.Role;
import org.example.utils.EmployeeTableModel;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

@Data
public class EmployeeUI extends JFrame{

    private EmployeeController employeeController;
    private DepartmentController departmentController;
    private JFrame frame;
    private JTable employeeTable;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;

    private JButton departmentButton;


    public EmployeeUI( ) throws SQLException {
        this.employeeController = new EmployeeController();
        this.departmentController = new DepartmentController();
        initializeUI();
    }

    private void initializeUI() throws SQLException {

        JFrame frame = new JFrame();
        frame = new JFrame("Managing employees");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        employeeTable = new JTable();
        refreshTable();

        addButton = new JButton("Add");
        updateButton = new JButton("Edit");
        deleteButton = new JButton("Delete");
        departmentButton = new JButton("Department");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    openAjoutDialog();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openModificationDialog();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteSelectedEmployee();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        departmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DepartmentUI.main();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(departmentButton);

        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(employeeTable), BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void refreshTable() throws SQLException {
        List<Employee> employeeList = employeeController.getAllEmployees();
        EmployeeTableModel model = new EmployeeTableModel(employeeList);
        employeeTable.setModel(model);
    }

    private void openAjoutDialog() throws SQLException {
        // Créer une boîte de dialogue pour l'ajout de salarié
        JDialog ajoutDialog = new JDialog(frame, "Add an employee", true);
        ajoutDialog.setBackground(Color.yellow);
        JPanel mainPanel = new JPanel();
        JPanel contentPane = new JPanel();
        ajoutDialog.setSize(600,200);
        mainPanel.setLayout(new GridLayout(5, 2));

        contentPane.setLayout(new BorderLayout());

        ajoutDialog.setLocationRelativeTo(null);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marges internes


        JLabel nameLabel = new JLabel("Lastname:");
        JTextField nameField = new JTextField();

        JLabel nameLabel1 = new JLabel("Firstname:");
        JTextField nameField1 = new JTextField();


        JLabel roleLabel = new JLabel("Role:");
        JRadioButton jRadioButton = new JRadioButton(String.valueOf(Role.MANAGER));
        JRadioButton jRadioButton1 = new JRadioButton(String.valueOf(Role.EMPLOYEE));
        JRadioButton jRadioButton2 = new JRadioButton("EXECUTIVE");

        JPanel radioPanel = new JPanel(new GridLayout(1, 3));
        radioPanel.add(jRadioButton);
        radioPanel.add(jRadioButton1);
        radioPanel.add(jRadioButton2);

        JLabel departmentLabel = new JLabel("Department:");
        JComboBox<String> departmentComboBox = new JComboBox<>();

        // Remplir la liste déroulante avec les noms des départements
        List<Department> departments = departmentController.getAllDepartments();
        for (Department department : departments) {
            departmentComboBox.addItem(department.getName());
        }

        // final String[] selectedRole = {new String()};

        final Role[] roleEnum = new Role[3];
        jRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jRadioButton.isSelected()) {
                    //selectedRole = jRadioButton.getText();
                    roleEnum[0] = Role.valueOf(jRadioButton.getText());
                }
            }
        });

        jRadioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jRadioButton1.isSelected()) {
                    roleEnum[0] = Role.valueOf(jRadioButton1.getText());

                }
            }
        });

        jRadioButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (jRadioButton2.isSelected()) {
                    roleEnum[0] = Role.valueOf(jRadioButton2.getText());

                }
            }
        });





        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstname = nameField1.getText();
                String lastname = nameField.getText();
                String selectedDepartmentName = (String) departmentComboBox.getSelectedItem();
                Department selectedDepartment = departmentController.getDepartmentByName(selectedDepartmentName);
                Employee newEmployee = new Employee(firstname, lastname, String.valueOf(roleEnum[0]), selectedDepartment);
                try {
                    employeeController.addEmployee(newEmployee);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    refreshTable();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                ajoutDialog.dispose();
            }
        });

        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(nameLabel1);
        mainPanel.add(nameField1);
        mainPanel.add(roleLabel);
        mainPanel.add(radioPanel);
        mainPanel.add(departmentLabel);
        mainPanel.add(departmentComboBox);
        mainPanel.add(new JLabel());
        contentPane.add(mainPanel, BorderLayout.CENTER);
        contentPane.add(addButton, BorderLayout.SOUTH);
        ajoutDialog.setContentPane(contentPane);
        ajoutDialog.setVisible(true);
    }


    private void openModificationDialog() {

        int selectedRow = employeeTable.getSelectedRow();

        if (selectedRow >= 0) {

            int employeeId = (int) employeeTable.getValueAt(selectedRow, 0);
            Employee selectedEmployee = employeeController.getEmployeeById(employeeId);

            // Créer une boîte de dialogue pour la modification de salarié
            JDialog modifDialog = new JDialog(frame, "Edit an employee", true);
            modifDialog.setSize(300, 200);
            modifDialog.setLayout(new GridLayout(4, 2));
            modifDialog.setLocationRelativeTo(frame);

            JLabel nameLabel = new JLabel("Name:");

            JTextField nameField = new JTextField(selectedEmployee.getLastName());

            JLabel roleLabel = new JLabel("Role:");
            JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"MANAGER", "EMPLOYEE", "EXECUTIVE"});
            roleComboBox.setSelectedItem(selectedEmployee.getRole());

            JButton updateButton = new JButton("Edit");
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String newName = nameField.getText();
                    String newRole = (String) roleComboBox.getSelectedItem();

                    selectedEmployee.setLastName(newName);
                    selectedEmployee.setRole(String.valueOf(Role.valueOf(newRole)));
                    try {
                        employeeController.updateEmployee(selectedEmployee);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                    try {
                        refreshTable();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    modifDialog.dispose();
                }
            });

            modifDialog.add(nameLabel);
            modifDialog.add(nameField);
            modifDialog.add(roleLabel);
            modifDialog.add(roleComboBox);
            modifDialog.add(new JLabel()); // Placeholder for spacing
            modifDialog.add(updateButton);

            modifDialog.setVisible(true);
        }
    }


    private void deleteSelectedEmployee() throws SQLException {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            int employeeId = (int) employeeTable.getValueAt(selectedRow, 0);
            employeeController.deleteEmployee(employeeId);
            refreshTable();
        }
    }

   /* public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                SalarieController salarieController = new SalarieController(new SalarieDAO()); // Remplacez par le vrai DAO
                new SalarieUI(salarieController);
            }
        });
    }*/
}
