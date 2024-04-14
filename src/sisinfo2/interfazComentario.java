/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sisinfo2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class interfazComentario extends JFrame{
    private ConexionBD conexionBD;
    private generadorCodigo ventanaGeneradorCodigo;
    DefaultTableModel model;
    
    private JButton botonRegresar;
    private JTable tablaEstudiantes;
    private JTextArea comentario;
    
    
    public interfazComentario(ConexionBD conexionBD) {
        super("Interfaz de Comentarios");
        this.conexionBD = conexionBD;
        configurarVentana();
        inicializarComponentes();
        ventanaGeneradorCodigo = new generadorCodigo(conexionBD);
         mostrarTabla();
    }
    
    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 700);
        setLocationRelativeTo(null);
        setLayout(null);
    }
    
    private void inicializarComponentes() {
        
        DefaultTableModel modeloTabla = new DefaultTableModel();
        
        modeloTabla.addColumn("id");
        modeloTabla.addColumn("nombre");
        modeloTabla.addColumn("cod_sis");
        modeloTabla.addColumn("edad");
        modeloTabla.addColumn("correo");
        modeloTabla.addColumn("comentarios");
        
        tablaEstudiantes = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaEstudiantes);
        scrollPane.setBounds(100, 50, 580, 400);
        add(scrollPane);
        tablaEstudiantes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
             mostrarComentario(e);
             
            }  
        });
        
        comentario = new JTextArea();
        comentario.setBounds(50, 470, 500, 150);
        comentario.setEditable(false);
        add(comentario);
       
        botonRegresar = new JButton("Regresar");
        botonRegresar.setBounds(600, 470, 150, 150);
        botonRegresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regresarPanel();
            }
        });
        add(botonRegresar);
    }
    
    private void regresarPanel(){
        ventanaGeneradorCodigo.setVisible(true);
        dispose();
    }
        
    private void mostrarComentario(MouseEvent e){
    if (e.getButton() == MouseEvent.BUTTON1) {
        int fila = tablaEstudiantes.getSelectedRow();
        System.out.println("Fila seleccionada: " + fila); // Agregar mensaje de depuración
        try {
            Connection conn = conexionBD.conexionEstablecida(); 
            String sql = "SELECT comentarios FROM estudiante WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tablaEstudiantes.getValueAt(fila, 0).toString()));
            ResultSet rs = statement.executeQuery();
            
            if (rs.next()) {
                comentario.setText(rs.getString("comentarios"));
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
    
    private void mostrarTabla() {
        try {
            Connection conn = conexionBD.conexionEstablecida();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT * FROM estudiante");

            DefaultTableModel modeloTabla = (DefaultTableModel) tablaEstudiantes.getModel();
            modeloTabla.setRowCount(0); // Limpiar el modelo antes de agregar nuevas filas

            while (rs.next()) {
                String id = rs.getString("id");
                String nombre = rs.getString("nombre");
                String edad = rs.getString("edad");
                String correo = rs.getString("correo");
                String comentarios = rs.getString("comentarios");
                String cod_sis = rs.getString("cod_sis");

                modeloTabla.addRow(new String[]{id, nombre, cod_sis, edad, correo, comentarios});
            }

            rs.close();
            stmt.close();
            conn.close();

            // Actualizar la tabla en la interfaz gráfica
            tablaEstudiantes.revalidate();
            tablaEstudiantes.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al ejecutar la consulta SQL: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        ConexionBD conexionBD = new ConexionBD();
        interfazComentario interfaz = new interfazComentario(conexionBD);
        interfaz.setVisible(true);
        
     }
   
}
