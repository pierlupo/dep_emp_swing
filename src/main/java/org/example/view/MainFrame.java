package org.example.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JDialog{

    private JPanel contentPanel;

    private JButton addButton, editButton, deleteButton, depButton;

    public void getMainFrame() {

        JFrame mainFrame = new JFrame("Employee System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


//        JPanel containerPanel = new JPanel();
//        mainFrame.add(containerPanel);
//
//
//        CardLayout cardLayout = new CardLayout();
//        cardLayout.setHgap(5);
//        cardLayout.setVgap(5);
//
//        containerPanel.setLayout(cardLayout);

            contentPanel = new JPanel();
            setTitle("Main Frame");
            setBounds(500, 500, 400, 250);
            getContentPane().setLayout(new BorderLayout());
            contentPanel.setBorder(new EmptyBorder(10, 10,10,10));
            getContentPane().add(contentPanel, BorderLayout.NORTH);
            contentPanel.setLayout(null);

            JPanel jPanelButton = new JPanel();
            JPanel jPanelSelectButton = new JPanel();
            getContentPane().add(jPanelButton, BorderLayout.NORTH);
            getContentPane().add(jPanelSelectButton, BorderLayout.CENTER);


            addButton = new JButton("Add");
            jPanelButton.add(addButton);

            addButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        AddDialog dialog = new AddDialog();
                        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        dialog.setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            });

            editButton = new JButton("Edit");
            jPanelButton.add(editButton);

            editButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        EditDialog dialog = new EditDialog();
                        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        dialog.setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

//            deleteButton = new JButton("delete");
//            jPanelButton.add(deleteButton);
//
//            deleteButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    try {
//                        DeleteDialog dialog = new DeleteDialog();
//                        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
//                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//                        dialog.setVisible(true);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            });

            depButton = new JButton("department");
            jPanelSelectButton.add(depButton);

            depButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        DepDialog dialog = new DepDialog();
                        dialog.setModalityType(ModalityType.APPLICATION_MODAL);
                        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                        dialog.setVisible(true);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
