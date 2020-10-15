/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.ctdoa;

import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import proyectofinal.ctdoa.frontend.gui.FrameCTDOA;

/**
 *
 * @author bryangmz
 */
public class ProyectoFinalCTDOA {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
//        GeneradorArchivo.generador(1);
//        GeneradorArchivo.generador(2);
//        GeneradorArchivo.generador(4);
//        GeneradorArchivo.generador(3);
        try {
            // TODO code application logic here
            javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            JOptionPane.showMessageDialog(null, "No se encuentra el estilo, por lo tanto se utlizara por defecto..");
        }        
        FrameCTDOA frameCTDOA = new FrameCTDOA();
        frameCTDOA.setVisible(true);

    }
}
