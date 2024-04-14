/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sisinfo2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author hp
 */
public class generadorCodigo extends JFrame{
    private ConexionBD conexionBD;
    
    private JPanel panelDocente;
    private JLabel labelNombre;
    //private JLabel labelPrecio;
    private JTextField campoNombre;
    //private JTextField campoPrecio;
    private JButton botonAgregar;
    private JButton botonGuardar;
    
    public generadorCodigo(ConexionBD conexionBD) {
        super("Generador de Codigos");
        this.conexionBD = conexionBD;
        configurarVentana();
        inicializarComponentes();
    }
    
    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        setLayout(null);
    }
    
    private void inicializarComponentes() {
        
        labelNombre = new JLabel("Codigo:");
        labelNombre.setBounds(50, 30, 80, 25);
        add(labelNombre);

        campoNombre = new JTextField();
        campoNombre.setBounds(130, 30, 200, 25);
        campoNombre.setEditable(false);
        add(campoNombre);

        botonAgregar = new JButton("Generar Codigo");
        botonAgregar.setBounds(50, 100, 150, 25);
        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarCodigo(8);
            }
        });
        add(botonAgregar);
        
        botonGuardar = new JButton("Guardar");
        botonGuardar.setBounds(230, 100, 100, 25);
        botonGuardar.addActionListener(new ActionListener(){
              @Override
              public void actionPerformed(ActionEvent e){
                  try {
                      guardarCodigo();
                  } catch (SQLException ex) {
                      Logger.getLogger(generadorCodigo.class.getName()).log(Level.SEVERE, null, ex);
                  }
              }
        });
        add(botonGuardar);
    }
    
    private void generarCodigo(int length){
        String Abecedario_minuscula = "abcdefghijklmnñopqrstuvwxyz";
        String Abecedario_mayuscula = Abecedario_minuscula.toUpperCase();
        //String simbolos = "!#$%&/()=¡?-+*";
        String numeros = "0123456789";
        
        String CARACTERES = Abecedario_minuscula + Abecedario_mayuscula /*+ simbolos*/ + numeros;
        
        Random n = new Random();
        
        String resultado = "";
        
        for(int i = 0; i < length; i++){
            int posicion = n.nextInt(CARACTERES.length());
            char caracter = CARACTERES.charAt(posicion);
            resultado = resultado + caracter;
        }
        campoNombre.setText(resultado);
    
    }
    
    private void guardarCodigo() throws SQLException{
        String nombre = campoNombre.getText();
        
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor genere el codigo de la clase");
            return;
        }
        
        try{
        Connection conexion = conexionBD.conexionEstablecida();
        
        if (conexion != null) {
                // Consulta para obtener el primer postre en la lista
                String consulta = "SELECT codigoMateria FROM materia";
                PreparedStatement statement = conexion.prepareStatement(consulta);
                ResultSet resultado = statement.executeQuery();
                
                String insertarCodigo = "INSERT INTO materia (codigoMateria) VALUES (?)";
                PreparedStatement insertStatement = conexion.prepareStatement(insertarCodigo);
                insertStatement.setString(1, nombre);
                insertStatement.executeUpdate();
                
                JOptionPane.showMessageDialog(this, "El codigo fue insertado correctamente");
                
                statement.close();
                insertStatement.close();
                conexion.close();
        } else {
                JOptionPane.showMessageDialog(this, "No se pudo establecer la conexión con la base de datos");
            }
        
        }catch (SQLException ex){
            Logger.getLogger(generadorCodigo.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Error al agregar el codigo: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        ConexionBD conexionBD = new ConexionBD();
        generadorCodigo interfaz = new generadorCodigo(conexionBD);
        interfaz.setVisible(true);
        
    }
}
