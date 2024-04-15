/*
 * 
 */
package sisinfo2poryectopp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * @authora Sidney Angelly Sahonero Garrado
 */
public class IURegistro extends JFrame{
    private JPanel lineaSup,otro, divCodigo, fondo;
    private JLabel cuentaL, instruccionL, codigoL, advertenciaL, titulo;
    private int anchoPantalla, alturaPantalla, alturaLineaSup;
    private JButton salirB, unirseB;
    private JTextField codigoClaseT;
    Conexion conect = new Conexion();
    private String textDatos,textCodigo, textAdvertencia, textInstrucciones;
    
    public IURegistro(int idEst){
        String nombre,correo;
        nombre = conect.recupNombre(idEst);
        correo = conect.recupCorreo(idEst);
        
        textCodigo = "<html>Código de la clase<br>Pídele a tu profesor el código de la clase y luego ingrésalo aquí.</html>";
        textDatos ="<html>Accediste como:<br>" + nombre + "<br>" + correo + "</html>";
        textAdvertencia = "<html>no incluyen espacios ni símbolos.<br>Los códigos de clase tienen entre 5 y<br>7 caracteres, incluidas letras y números, y<br></html>";
        textInstrucciones = "<html>Para acceder con un código de la clase<br>- Usa una cuenta autorizada.<br>- Usa un código de la clase que tenga entre 5 y 7 letras o números,<br>sin espacios ni símbolos.</html>";

        //Obtener tamano de nuestra pantalla
        Toolkit pantalla = Toolkit.getDefaultToolkit();
        Dimension tamPantalla = pantalla.getScreenSize();
        anchoPantalla = tamPantalla.width;
        alturaPantalla = tamPantalla.height;
        
        // caracteristicas del frame
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
        setSize(anchoPantalla,alturaPantalla);
        setResizable(false); //evitar que se pueda cambiar el tamaño de la ventana manualmente
        setLocationRelativeTo(null);//ventana en el centro de la pantalla
        setTitle("Unirse a una nueva clase");
        setLocationRelativeTo(null);//frame al medio
        
        //tamano de linea superior para ordenar
        alturaLineaSup = alturaPantalla/8;
        
        //inicializamos variables y ubicamos
        lineaSup = new JPanel();
        titulo = new JLabel("UNIRSE A UNA CLASE");
        salirB = new JButton("X");
        unirseB = new JButton("Unirte");  
        fondo = new JPanel(new GridLayout(1,4));
        otro = new JPanel(new GridLayout(3,1));
        cuentaL = new JLabel();
        divCodigo = new JPanel(new GridLayout(3,1));
        instruccionL = new JLabel();
        codigoL = new JLabel();
        advertenciaL = new JLabel();
        codigoClaseT = new JTextField();
        
        //linea superior configuraciones
        lineaSup.setBounds(anchoPantalla/3, alturaLineaSup/4, anchoPantalla/3 , alturaLineaSup/2);
        titulo.setFont(new Font("Arial",1,30));//tipo de letra y tamano
        
        salirB.setFont(new Font("Arial",1,20));//tipo de letra y tamano
        salirB.setContentAreaFilled(false);//boton transparente
        salirB.setBounds(alturaLineaSup/4, alturaLineaSup/4, alturaLineaSup/2, alturaLineaSup/2);
        salirB.setMargin(new Insets(0,0,0,0));//quitar margen
        unirseB.setFont(new Font("Arial",1,18));//tipo de letra y tamano
        unirseB.setBounds((anchoPantalla-alturaLineaSup-40), alturaLineaSup/3, alturaLineaSup, alturaLineaSup/3);
        unirseB.setBackground(Color.BLUE);
        unirseB.setMargin(new Insets(0,0,0,0));//quitar margen
        unirseB.setForeground(Color.WHITE);
        
        //otro configuraciones
        otro.setBounds(anchoPantalla/3, alturaLineaSup, anchoPantalla/3, alturaPantalla-alturaLineaSup);
        cuentaL.setSize(anchoPantalla/3,alturaPantalla/4);
        cuentaL.setText(textDatos);
        cuentaL.setFont(new Font("Arial",1,18));
        
        //divCodigo config
        codigoL.setSize(anchoPantalla/3,alturaPantalla/8);
        codigoL.setText(textCodigo);
        codigoL.setFont(new Font("Arial",0,15));
        advertenciaL.setSize(anchoPantalla/3,alturaPantalla/8);
        codigoClaseT.setFont(new Font("Arial",1,18));
        instruccionL.setText(textInstrucciones);
        instruccionL.setFont(new Font("Arial",0,15));
        
        
        //anadir todo
        add(salirB);
        add(unirseB);
        lineaSup.add(titulo,BorderLayout.CENTER);
        add(lineaSup);
        add(otro);
        divCodigo.add(codigoL);
        divCodigo.add(codigoClaseT);
        //divCodigo.add(advertenciaL);
        otro.add(cuentaL);
        otro.add(divCodigo);
        otro.add(instruccionL);
        add(fondo);

        //Acciones de botones
        unirseB.addActionListener( new ActionListener(){   //para recuperar datos de lo seleccionado
            @Override
            public void actionPerformed(ActionEvent e) {
                String codigoResult = codigoClaseT.getText();
                Boolean existe = false;
                
                if(!codigoResult.isEmpty() || codigoResult.length()<5 || codigoResult.length()>7){
                    existe = conect.ExistClase(codigoResult); 
                }            
                if(existe){
                    conect.registrarEstEnClase(idEst,codigoResult);
                }
                if(existe=false || codigoResult.isEmpty()){
                    codigoClaseT.setText("");
                    advertenciaL.setText(textAdvertencia);
                    advertenciaL.setFont(new Font("Arial",0,12));
                    advertenciaL.setForeground(Color.red);
                    divCodigo.add(advertenciaL);
                } 
            }
        });
        salirB.addActionListener( new ActionListener(){   //para recuperar datos de lo seleccionado
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        setVisible(true);
    }
    
    
}
