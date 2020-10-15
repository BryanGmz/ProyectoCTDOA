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
public class Funcion extends Estructuras{
    
    private final List<Simbolo> parametros;
    
    public Funcion() {
        super(new ArrayList<Simbolo>());
        this.parametros = new ArrayList<>();
    }

    public List<Simbolo> getParametros() {
        return parametros;
    }

    public void setParametros(Object parametros) {
        if (parametros != null) {
            if (parametros instanceof Simbolo) {
                this.parametros.add((Simbolo) parametros);
            } else {
                this.parametros.addAll((List<Simbolo>) parametros);
            }
        }
    }
    
}
