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
public class While {
    
    private String idEtiqueta;
    private Object a;

    public While(String idEtiqueta, Object a) {
        this.idEtiqueta = idEtiqueta;
        this.a = a;
    }
    
    public String getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(String idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

    public Object getA() {
        return a;
    }

    public void setA(Object a) {
        this.a = a;
    }
        
        
}
