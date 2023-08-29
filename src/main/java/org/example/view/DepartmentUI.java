package org.example.view;


import lombok.Data;
import org.example.controller.DepartmentController;
import org.example.dao.DepartmentDao;
import org.example.model.Department;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

@Data
public class DepartmentUI extends JFrame {

    private static DepartmentController departmentController;
    private static DepartmentDao departmentDao;
    private DefaultListModel<Department> departmentListModel;
    private JList<Department> departmentList;

    private static DepartmentUI departmentUI;


    public DepartmentUI() throws SQLException {
        this.departmentController = new DepartmentController();
        initializeUI();
    }


    public void initializeUI() throws SQLException {

        // departmentController = new DepartmentController();
        setTitle("Managing departments");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        departmentListModel = new DefaultListModel<>();
        departmentList = new JList<>(departmentListModel);
        refreshDepList();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAjoutDialog();
            }
        });

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openModificationDialog();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteSelectedDepartment();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JButton salarieButton = new JButton("Employee");

        salarieButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                departmentUI.setVisible(false);
                System.out.println("coucou");
            }
        });


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(salarieButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(departmentList), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void refreshDepList() throws SQLException {
        departmentListModel.clear();
        List<Department> departments = departmentController.getAllDepartments();
        for (Department department : departments) {
            departmentListModel.addElement(department);
        }
    }


    private void openAjoutDialog() {
        // Créer une boîte de dialogue pour l'ajout de salarié
        JDialog ajoutDialog = new JDialog(this, "Add a department", true);
        JPanel mainPanel = new JPanel();
        JPanel contentPane = new JPanel();
        ajoutDialog.setBounds(300, 200, 300, 200);
        mainPanel.setLayout(new GridLayout(4, 3));
        ajoutDialog.setLocationRelativeTo(null);
        contentPane.setLayout(new BorderLayout());


        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marges internes


        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        Dimension textFieldSize = new Dimension(50, 10); // Largeur: 200, Hauteur: 30
        nameField.setPreferredSize(textFieldSize);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();

                Department department = new Department(name);
                try {
                    departmentController.addDepartment(department);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    refreshDepList();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                ajoutDialog.dispose();
            }
        });

        mainPanel.add(nameLabel);
        mainPanel.add(nameField);
        mainPanel.add(new JLabel()); // Placeholder for spacing
        mainPanel.add(addButton);

        contentPane.add(mainPanel, BorderLayout.CENTER);
        ajoutDialog.setContentPane(contentPane);
        ajoutDialog.setVisible(true);
    }

    private void openModificationDialog() {
        int selectedIndex = departmentList.getSelectedIndex();
        if (selectedIndex >= 0) {
            Department selectedDepartement = departmentListModel.getElementAt(selectedIndex);

            JDialog modifDialog = new JDialog(this, "Edit a department", true);
            modifDialog.setSize(300, 200);
            modifDialog.setLayout(new GridLayout(4, 2));
            modifDialog.setLocationRelativeTo(null);
            JLabel nameLabel = new JLabel("Nom:");
            JTextField nameField = new JTextField(selectedDepartement.getName());

            JButton updateButton = new JButton("Edit");
            updateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String newName = nameField.getText();
                    selectedDepartement.setName(newName);

                    try {
                        departmentController.updateDepartment(selectedDepartement);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    try {
                        refreshDepList();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    modifDialog.dispose();
                }
            });

            modifDialog.add(nameLabel);
            modifDialog.add(nameField);
            modifDialog.add(new JLabel()); // Placeholder for spacing
            modifDialog.add(updateButton);

            modifDialog.setVisible(true);
        }
    }


    private void deleteSelectedDepartment() throws SQLException {
        int selectedIndex = departmentList.getSelectedIndex();
        if (selectedIndex >= 0) {
            Department selectedDepartement = departmentListModel.getElementAt(selectedIndex);



            departmentDao.deleteDepartment(selectedDepartement.getId());


            departmentListModel.removeElement(selectedDepartement);
            updateDepartementList();
        }
    }

    private void updateDepartementList() throws SQLException {
        departmentListModel.clear(); // Efface le modèle de liste actuel

        // Ajoutez tous les départements actuels à partir de votre source de données
        List<Department> allDepartments = departmentDao.getAllDepartments(); // Utilisez votre DepartementDao pour obtenir tous les départements
        for (Department department : allDepartments) {
            departmentListModel.addElement(department);
        }
    }

    public static void main() throws SQLException {

        departmentController = new DepartmentController();
        departmentUI = new DepartmentUI();
        departmentUI.setVisible(true);


    }
}
