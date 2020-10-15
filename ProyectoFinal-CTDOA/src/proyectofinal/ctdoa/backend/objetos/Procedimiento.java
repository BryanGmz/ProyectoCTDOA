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
public class Procedimiento {
    
    private String u;
    private Object e;
    private Tipo a;

    public Procedimiento(String u, Object e, Tipo a) {
        this.u = u;
        this.e = e;
        this.a = a;
    }

    public Procedimiento(String u, Object e) {
        this.u = u;
        this.e = e;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public Object getE() {
        return e;
    }

    public void setE(Object e) {
        this.e = e;
    }

    public Tipo getA() {
        return a;
    }

    public void setA(Tipo a) {
        this.a = a;
    }
    
    
}
