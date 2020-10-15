/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.ctdoa.backend.objetos;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.text.BadLocationException;
import proyectofinal.ctdoa.frontend.gui.*;

/**
 *
 * @author bryan
 */
public class Pestaña {
    
    private Pestaña estaPestaña;
    private String nombre;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private NumeroLinea numeroLinea;
    private String path;
    private String titulo;
    private final FrameCTDOA frame;
    
    public Pestaña(String nombre, JTextArea textArea, JScrollPane scrollPane, String path, FrameCTDOA frame, String titulo) {
        this.nombre = nombre;
        this.titulo = titulo;
        this.textArea = textArea;
        this.scrollPane = scrollPane;
        this.path = path;
        this.frame = frame;
        setNumeroLinea();
        addEscucha();
        addCaret();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Pestaña getEstaPestaña() {
        return estaPestaña;
    }

    public void setEstaPestaña(Pestaña estaPestaña) {
        this.estaPestaña = estaPestaña;
    }
    
    @Override
    public Pestaña clone() throws CloneNotSupportedException{
        return new Pestaña(nombre, textArea, scrollPane, path, frame, titulo);
    }
    
    private void addEscucha(){
        textArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (estaPestaña != null) {
                    frame.setLblPestaniaSelect(estaPestaña.titulo);
                    frame.setPestañaActual(estaPestaña);
                }
            }
        });   
    }
    
    private void addCaret(){
        textArea.addCaretListener((CaretEvent evt) -> {
            JTextArea editArea = (javax.swing.JTextArea) evt.getSource();
            int linea = 1;
            int columna = 1;
            try {
                int caretPos = editArea.getCaretPosition();
                linea = editArea.getLineOfOffset(caretPos);
                columna = caretPos - editArea.getLineStartOffset(linea);
                linea += 1;
                columna += 1;
            } catch (BadLocationException e) {
            }
            frame.getLblCursor().setText("Linea: " + linea + " - Columna: " + columna);
        });
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public JTextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(JTextArea textArea) {
        this.textArea = textArea;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    private void setNumeroLinea() {
        this.numeroLinea = new NumeroLinea(textArea);
        this.scrollPane.setRowHeaderView(numeroLinea);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    
}
