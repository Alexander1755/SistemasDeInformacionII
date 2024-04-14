
//package com.mycompany.sisinfo2proyecto;

/**
 *
 * @author Fernanda
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class InterfazAnunciosDocente extends JFrame {
    private JTextArea areaAnuncios;
    private JTextField campoAnuncio;
    private Connection conexion;

    public InterfazAnunciosDocente() {
        setTitle("tablon de Anuncios");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());

        JPanel panelAnuncios = new JPanel();
        panelAnuncios.setLayout(new BorderLayout());

        JLabel etiquetaAnuncio = new JLabel("Anuncio:");
        campoAnuncio = new JTextField(20);
        JButton botonEnviar = new JButton("Enviar");
        botonEnviar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                enviarAnuncio();
            }
        });

        panelAnuncios.add(etiquetaAnuncio, BorderLayout.WEST);
        panelAnuncios.add(campoAnuncio, BorderLayout.CENTER);
        panelAnuncios.add(botonEnviar, BorderLayout.EAST);

        JPanel panelMostrarAnuncios = new JPanel();
        panelMostrarAnuncios.setLayout(new BorderLayout());

        JLabel etiquetaMostrar = new JLabel("Anuncios anteriores:");
        areaAnuncios = new JTextArea(10, 30);
        areaAnuncios.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(areaAnuncios);

        panelMostrarAnuncios.add(etiquetaMostrar, BorderLayout.NORTH);
        panelMostrarAnuncios.add(scrollPane, BorderLayout.CENTER);

        panelPrincipal.add(panelAnuncios, BorderLayout.NORTH);
        panelPrincipal.add(panelMostrarAnuncios, BorderLayout.CENTER);

        add(panelPrincipal);
        
        conectarBD();

        cargarAnuncios();

        setVisible(true);
    }

    private void conectarBD() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://dpg-coal268l6cac73emur1g-a.oregon-postgres.render.com/sisinfo2";
            String usuario = "sisinfo2_user";
            String contraseña = "MjJfeyXg5JB1hbCf0vYs14Gmw8MVjo3N";
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (Exception e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

    private void cargarAnuncios() {
        try {
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT anuncios FROM materias");
            while (resultSet.next()) {
                String anuncio = resultSet.getString("anuncios");
                areaAnuncios.append("- " + anuncio + "\n");
            }
        } catch (SQLException ex) {
            System.out.println("Error al cargar anuncios anteriores: " + ex.getMessage());
        }
    }

    private void enviarAnuncio() {
        String anuncio = campoAnuncio.getText();
        if (!anuncio.isEmpty()) {
            areaAnuncios.append("- " + anuncio + "\n");
            guardarAnuncio(anuncio);
            campoAnuncio.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Por favor ingrese un anuncio", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void guardarAnuncio(String anuncio) {
        try {
            String query = "INSERT INTO materias (anuncios) VALUES (?)";
            PreparedStatement statement = conexion.prepareStatement(query);
            statement.setString(1, anuncio);
            statement.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error al guardar el anuncio: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InterfazAnunciosDocente();
            }
        });
    }
}
