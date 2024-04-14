
//package com.mycompany.sisinfo2proyecto;

/**
 *
 * @author Fernanda
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterfazTareasPendientes extends JFrame {
    private DefaultListModel<String> tareasListModel;
    private JList<String> tareasList;

    public InterfazTareasPendientes() {
        super("Tareas Pendientes");

        tareasListModel = new DefaultListModel<>();
        tareasList = new JList<>(tareasListModel);

        JScrollPane scrollPane = new JScrollPane(tareasList);

        JButton addButton = new JButton("Agregar Tarea");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String tarea = JOptionPane.showInputDialog("Ingrese la nueva tarea:");
                if (tarea != null && !tarea.isEmpty()) {
                    tareasListModel.addElement(tarea);
                }
            }
        });

        JButton deleteButton = new JButton("Eliminar Tarea");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = tareasList.getSelectedIndex();
                if (selectedIndex != -1) {
                    tareasListModel.remove(selectedIndex);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(scrollPane, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfazTareasPendientes();
            }
        });
    }
}

