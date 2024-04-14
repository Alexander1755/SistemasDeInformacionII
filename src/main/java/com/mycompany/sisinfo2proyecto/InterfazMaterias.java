
package com.mycompany.sisinfo2proyecto;

/**
 *
 * @author Fernanda
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class InterfazMaterias extends JFrame implements ActionListener {
    private JTextField campoNombre, campoDocente;
    private JComboBox<String> nivelMateria;
    private JButton btnAgregar, btnMostrar;
    private JTextArea areaMaterias;
    private Connection conexion;

    public InterfazMaterias() {
        
        setTitle("Gestor de Materias");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel lblNombre = new JLabel("Nombre de la materia:");
        campoNombre = new JTextField(20);
        JLabel lblDocente = new JLabel("Nombre del docente:");
        campoDocente = new JTextField(20);
        JLabel lblNivel = new JLabel("Nivel de la materia:");
        String[] niveles = {"Básico", "Intermedio", "Avanzado"};
        nivelMateria = new JComboBox<>(niveles);
        btnAgregar = new JButton("Agregar Materia");
        btnMostrar = new JButton("Mostrar Materias");
        areaMaterias = new JTextArea(10, 30);
        areaMaterias.setEditable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(lblNombre);
        panel.add(campoNombre);
        panel.add(lblDocente);
        panel.add(campoDocente);
        panel.add(lblNivel);
        panel.add(nivelMateria);
        panel.add(btnAgregar);
        panel.add(btnMostrar);

        btnAgregar.addActionListener(this);
        btnMostrar.addActionListener(this);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(areaMaterias), BorderLayout.CENTER);

        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection("jdbc:postgresql://dpg-coal268l6cac73emur1g-a.oregon-postgres.render.com/sisinfo2", "sisinfo2_user", "MjJfeyXg5JB1hbCf0vYs14Gmw8MVjo3N");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAgregar) {
            agregarMateria();
        } else if (e.getSource() == btnMostrar) {
            mostrarMaterias();
        }
    }

    private void agregarMateria() {
        String nombre = campoNombre.getText();
        String docente = campoDocente.getText();
        String nivel = (String) nivelMateria.getSelectedItem();
        try {
            PreparedStatement pst = conexion.prepareStatement("INSERT INTO materia (nombre_materia, area_materia, nivel_materia) VALUES (?, ?, ?)");
            pst.setString(1, nombre);
            pst.setString(2, docente);
            pst.setString(3, nivel); 
            int resultado = pst.executeUpdate();
            if (resultado > 0) {
                JOptionPane.showMessageDialog(null, "Materia agregada correctamente");
                campoNombre.setText("");
                campoDocente.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar la materia");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void mostrarMaterias() {
        areaMaterias.setText("");
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("SELECT nombre_materia, area_materia, nivel_materia FROM materia");
            while (rs.next()) {
                String nombre = rs.getString("nombre_materia");
                String docente = rs.getString("area_materia");
                String nivel = rs.getString("nivel_materia");
                areaMaterias.append("Nombre: " + nombre + ", Docente: " + docente + ", Nivel: " + nivel + "\n");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        InterfazMaterias ventana = new InterfazMaterias();
        ventana.setVisible(true);
    }
}
