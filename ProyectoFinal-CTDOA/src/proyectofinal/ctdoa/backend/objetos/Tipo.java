/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.ctdoa.backend.objetos;

/**
 *
 * @author bryangmz
 */
public class Tipo {
    
    private String nombre;
    private Tipo padre;
    private Tipo hijo;
    private int symbol;

    public Tipo(String nombre, int symbol) {
        this.nombre = nombre;
        this.symbol = symbol;
    }    

    public Tipo(String nombre, Tipo hijo, int symbol) {
        this.nombre = nombre;
        this.hijo = hijo;
        this.symbol = symbol;
    }
    
    public Boolean isFatherOf(int symbol){
        if(this.symbol == symbol){
            return true;
        }else if(hijo != null){
            return hijo.isFatherOf(symbol);
        }else{
            return false;
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Tipo getPadre() {
        return padre;
    }

    public void setPadre(Tipo padre) {
        this.padre = padre;
    }

    public Tipo getHijo() {
        return hijo;
    }

    public void setHijo(Tipo hijo) {
        this.hijo = hijo;
    }

    public int getSymbol() {
        return symbol;
    }

    public void setSymbol(int symbol) {
        this.symbol = symbol;
    }
 
    @Override
    public String toString(){
        return nombre;
    }
    
}
