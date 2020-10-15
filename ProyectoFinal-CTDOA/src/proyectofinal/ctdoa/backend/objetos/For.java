/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.ctdoa.backend.objetos;

import java.util.List;

/**
 *
 * @author bryangmz
 */

public class For {
    private Object a;
    private Object e;
    private Object i;
    private List<String> lista;
    
    /***** python *****/
    private Object rango;
    private Simbolo id;

    public For(Object rango, Simbolo id, List<String> lista) {
        this.rango = rango;
        this.id = id;
        this.lista = lista;
    }
    
    public For(Object a, Object e, Object i, List<String> lista) {
        this.a = a;
        this.e = e;
        this.i = i;
        this.lista = lista;
    }

    public For(Object a, List<String> lista) {
        this.a = a;
        this.lista = lista;
    }
    
    public Object getRango() {
        return rango;
    }

    public void setRango(Object rango) {
        this.rango = rango;
    }

    public Simbolo getId() {
        return id;
    }

    public void setId(Simbolo id) {
        this.id = id;
    }
    
    public Object getA() {
        return a;
    }

    public void setA(Object a) {
        this.a = a;
    }

    public Object getE() {
        return e;
    }

    public void setE(Object e) {
        this.e = e;
    }

    public Object getI() {
        return i;
    }

    public void setI(Object i) {
        this.i = i;
    }

    public List<String> getLista() {
        return lista;
    }

    public void setLista(List<String> lista) {
        this.lista = lista;
    }
    
    
}
