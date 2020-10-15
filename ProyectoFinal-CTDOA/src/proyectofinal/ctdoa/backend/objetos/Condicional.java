/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.ctdoa.backend.objetos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bryangmz
 */
public class Condicional extends Estructuras {
    
    private Tipo tipoCondicional; //Variable que indica si es una condicional if, else, else if
    private Object condicional; //Condicional 3D
    private List<Cuarteto> cuartetos;
    private boolean retornar;
    
    public Condicional(Tipo tipoCondicional, Object condicional, boolean retornar) {
        super(new ArrayList<Simbolo>());
        this.retornar = retornar;
        this.tipoCondicional = tipoCondicional;
        this.condicional = condicional;
        this.cuartetos = new ArrayList<>();
    }

    public boolean isRetornar() {
        return retornar;
    }

    public void setRetornar(boolean retornar) {
        this.retornar = retornar;
    }
    
    public List<Cuarteto> getCuartetos() {
        return cuartetos;
    }

    public void setCuartetos(List<Cuarteto> cuartetos) {
        this.cuartetos = cuartetos;
    }
    
    public Tipo getTipoCondicional() {
        return tipoCondicional;
    }

    public void setTipoCondicional(Tipo tipoCondicional) {
        this.tipoCondicional = tipoCondicional;
    }

    public Object getCondicional() {
        return condicional;
    }

    public void setCondicional(Object condicional) {
        this.condicional = condicional;
    }
    
}