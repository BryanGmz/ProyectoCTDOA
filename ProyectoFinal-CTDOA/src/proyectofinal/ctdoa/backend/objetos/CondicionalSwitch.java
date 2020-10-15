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
public class CondicionalSwitch extends Estructuras {
    
    private final List<Case> listaCasos;
    private boolean retornarAlMenosUnaVez;
    private boolean retornar;
    
    public CondicionalSwitch() {
        super(new ArrayList<Simbolo>());
        this.listaCasos = new ArrayList<>();
    }

    public boolean isRetornarAlMenosUnaVez() {
        return retornarAlMenosUnaVez;
    }

    public void setRetornarAlMenosUnaVez(boolean retornarAlMenosUnaVez) {
        this.retornarAlMenosUnaVez = retornarAlMenosUnaVez;
    }

    public boolean isRetornar() {
        return retornar;
    }

    public void setRetornar(boolean retornar) {
        this.retornar = retornar;
    }
    
    public void agregarCaso(Case caso) {
        this.listaCasos.add(caso);
    }

    public List<Case> getListaCasos() {
        return listaCasos;
    }
    
}
