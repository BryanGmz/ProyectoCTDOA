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
public class CondicionalIf extends Estructuras {
    
    private Condicional condicionalIf;
    private final List<Condicional> condicionalesElseIf;
    private Condicional condicionalElse;
    private boolean retornarEnTodasCondiciones;
    private boolean almenosUnRetornar;

    public CondicionalIf(Condicional condicionalIf) {
        super(new ArrayList<Simbolo>());
        this.condicionalIf = condicionalIf;
        this.condicionalesElseIf = new ArrayList<>();
    }

    public boolean isRetornarEnTodasCondiciones() {
        return retornarEnTodasCondiciones;
    }

    public void setRetornarEnTodasCondiciones(boolean retornarEnTodasCondiciones) {
        this.retornarEnTodasCondiciones = retornarEnTodasCondiciones;
    }

    public boolean isAlmenosUnRetornar() {
        return almenosUnRetornar;
    }

    public void setAlmenosUnRetornar(boolean almenosUnRetornar) {
        this.almenosUnRetornar = almenosUnRetornar;
    }
    
    public Condicional getCondicionalIf() {
        return condicionalIf;
    }

    public void setCondicionalIf(Condicional condicionalIf) {
        this.condicionalIf = condicionalIf;
    }
    
    public void addCondicionalesElseIf(Condicional condicionalesElseIf) {
        this.condicionalesElseIf.add(condicionalesElseIf);
    }

    public List<Condicional> getCondicionalesElseIf() {
        return condicionalesElseIf;
    }
 
    public Condicional getCondicionalElse() {
        return condicionalElse;
    }

    public void setCondicionalElse(Condicional condicionalElse) {
        this.condicionalElse = condicionalElse;
    }
    
}
