package com.mycompany.sisinfo2proyecto;

import java.sql.Connection;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import java.sql.ResultSet;
//import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
//import javax.swing.JOptionPane;

public class Lista_estudiantes extends javax.swing.JFrame {

    private Conexion conexion;
    private DefaultTableModel modelo = new DefaultTableModel();
    private Statement ejecutor = null;

    protected void cargar_lista(String materia) {

        modelo.setRowCount(0);
        String datos[] = new String[5];
        String query = "SELECT row_number() OVER (ORDER BY apellido_paterno DESC) as puesto, apellido_paterno,"
                     + "apellido_materno, nombres, email "
                     + "FROM materia,inscripcion,alumno,usuario "             
                     + "WHERE materia.id_materia = inscripcion.id_materia AND alumno.id_alumno = inscripcion.id_alumno"
                     + " AND usuario.id_usuario = alumno.id_usuario AND materia =" + materia + " "
                     + "ORDER BY apellido_paterno DESC";

        ResultSet rs;
        try {
            ejecutor = conexion.getConnection().createStatement();
            ejecutor.setQueryTimeout(20);
            rs = ejecutor.executeQuery(query);
            while (rs.next() == true) {
                datos[0] = rs.getString("n°");
                datos[1] = rs.getString("Apellido Paterno");
                datos[2] = rs.getString("Apellido Materno");
                datos[3] = rs.getString("Nombres");
                datos[4] = rs.getString("Email");

                modelo.addRow(datos);
            }
            tablaEstudiantes.setModel(modelo);

        } catch (Exception e) {

        }

    }

    private void conectar() throws ClassNotFoundException {
        conexion = new Conexion();
        Connection con = null;
        con = conexion.getConnection();
    }


    public Lista_estudiantes(){
        initComponents();

        modelo.addColumn("n°");
        modelo.addColumn("Apellido Paterno");
        modelo.addColumn("Apellido Materno");
        modelo.addColumn("Nombres");
        modelo.addColumn("Email");

        try {
            conectar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Lista_estudiantes.class.getName()).log(Level.SEVERE, null, ex);
        }
        cargar_lista("");
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEstudiantes = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tablaEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°", "APELLIDO_PATERNO", "APELLIDO_MATERNO", "NOMBRES", "EMAIL"
            }
        ));
        jScrollPane1.setViewportView(tablaEstudiantes);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(114, 114, 114)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(162, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Lista_estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lista_estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lista_estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lista_estudiantes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lista_estudiantes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaEstudiantes;
    // End of variables declaration//GEN-END:variables
}
