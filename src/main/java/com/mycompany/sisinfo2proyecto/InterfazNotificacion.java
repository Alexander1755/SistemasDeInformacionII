
//package com.mycompany.sisinfo2proyecto;

/**
 *
 * @author Fernanda
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class InterfazNotificacion extends JFrame {
    private JButton verEstudianteButton;
    private Connection conectar;

    public InterfazNotificacion() {
        setTitle("Notificacion");
        setSize(400, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        verEstudianteButton = new JButton("Agregar Estudiante");

        verEstudianteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agregarEstudiante();
            }
        });

        setLayout(new FlowLayout());
        add(verEstudianteButton);

        ConexionBD conexionBD = new ConexionBD();
        conectar = conexionBD.conexionEstablecida();
    }

    private void agregarEstudiante() {
        try {
            Statement statement = conectar.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT nombre, apellidos FROM estudiante");

            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String apellidos = resultSet.getString("apellidos");
                mostrarNotificacion("El estudiante " + nombre + " " + apellidos + " se ha unido a la clase.");
            }

            statement.close();
            conectar.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarNotificacion(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Notificación", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                InterfazNotificacion interfaz = new InterfazNotificacion();
                interfaz.setVisible(true);
            }
        });
    }
}

