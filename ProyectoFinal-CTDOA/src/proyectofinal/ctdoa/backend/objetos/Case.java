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
public class Case extends Estructuras {
    
    private Simbolo caso;
    private boolean retornar;
    
    public Case(Simbolo caso, boolean retornar) {
        super(new ArrayList<Simbolo>());
        this.caso = caso;
        this.retornar = retornar;
    }

    public Case(boolean retornar) {
        super(new ArrayList<Simbolo>());
        this.retornar = retornar;
    }

    public void setRetornar(boolean retornar) {
        this.retornar = retornar;
    }
    
    public Simbolo getCaso() {
        return caso;
    }

    public boolean isRetornar() {
        return retornar;
    }
    
}
