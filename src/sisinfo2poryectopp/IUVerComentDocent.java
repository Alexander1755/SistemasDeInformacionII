/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sisinfo2poryectopp;
/**
 * @authora Sidney Angelly Sahonero Garrado
 */
import java.awt.*;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @authora Sidney Angelly Sahonero Garrado
 */
public class IUVerComentDocent extends JFrame{
    private JPanel panelContenedor;
    private Conexion conect = new Conexion();
    
    //tamano pantalla
    private Toolkit pantalla = Toolkit.getDefaultToolkit();
    private Dimension tamPantalla = pantalla.getScreenSize();
    private int anchoPantalla = tamPantalla.width;
    private int alturaPantalla = tamPantalla.height;
    
    public IUVerComentDocent(int idEst){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //se cierre con x
        setTitle("COMENTARIOS");
        setSize(anchoPantalla/2,alturaPantalla-20);//tamano del frame
        setLocationRelativeTo(null);//ventana en el centro de la pantalla
        setResizable(false); //evitar que se pueda cambiar el tamaño de la ventana manualmente
        
        // Crear el panel contenedor y configurar el layout
        panelContenedor = new JPanel();
        panelContenedor.setLayout(new BoxLayout(panelContenedor, BoxLayout.Y_AXIS));

        // Crear y agregar paneles al contenedor
        ArrayList<String> comentarios = conect.comentarios(idEst);
        
        // Crear y agregar anuncios al contenedor
        int i =0;
        for (String comentario : comentarios) {
            JLabel label = new JLabel();
            label.setPreferredSize(new Dimension(anchoPantalla/2, 400));//tamano panel, no importa mucho
            label.setOpaque(true); // Establecer que el JLabel sea opaco para mostrar el color de fondo
            if(i%2 == 0){
                label.setBackground(new Color(200, 200, 200)); // Color panel
            }
            label.setText(comentario);
            label.setFont(new Font("Arial",0,14));
            panelContenedor.add(label);
            i++;
            //System.out.println(comentario);
        }
        
        // Crear un JScrollPane y agregar el panel contenedor
        JScrollPane scrollPane = new JScrollPane(panelContenedor);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Agregar el JScrollPane al JFrame
        add(scrollPane);
        
        setVisible(true);
    }
}

