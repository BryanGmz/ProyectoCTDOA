/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.ctdoa.backend.objetos;

import java.util.ArrayList;
import java.util.List;
import proyectofinal.ctdoa.backend.manejadores.ManejadorSintacticoJP;

/**
 *
 * @author bryangmz
 */
public class Estructuras {
    
    private final List<Simbolo> statement;
    private final List<Cuarteto> cuartetos;
    private String etiquetaIsntrucciones;
    
    public Estructuras(List<Simbolo> statement) {
        this.statement = statement;
        this.cuartetos = new ArrayList<>();
    }

    public String getEtiquetaIsntrucciones() {
        return etiquetaIsntrucciones;
    }

    public void setEtiquetaIsntrucciones(String etiquetaIsntrucciones) {
        this.etiquetaIsntrucciones = etiquetaIsntrucciones;
    }
    
    public List<Simbolo> getStatement() {
        return statement;
    }

    public List<Cuarteto> getCuartetos() {
        return cuartetos;
    }

    public void addStatements(Object lista) {
        if (lista != null) {
             List<Simbolo> simbolo;
            if (lista instanceof Simbolo) {
                simbolo = new ArrayList<>();
                ((Simbolo) lista).setCuarteto(null);
                simbolo.add((Simbolo) lista);
            } else {
                simbolo = (List<Simbolo>) lista;
            }
            simbolo.forEach((s) -> {
                if(s.getCuarteto() != null) {
                    cuartetos.add(s.getCuarteto());
                    s.setCuarteto(null);
                }
                this.statement.add(s);
            });
        }
    }
    
    public boolean comprobarSiTieneReturn(Tipo simboloComprobar, int l, int r, boolean primerVuelta){
        for (Simbolo simbolo : statement) {
            if (simbolo.getTipo().getNombre().equalsIgnoreCase("Return")) {
                if (simbolo.getValor() != null && simboloComprobar.isFatherOf(((Simbolo) simbolo.getValor()).getTipo().getSymbol())) {
                    return true;
                } 
                ManejadorSintacticoJP msjp = ManejadorSintacticoJP.getInstacia();
                msjp.errorSemantico(l, r, "RETURN", "El tipo de dato de: < " + simbolo.getTipo().getNombre() + " > no es compatible, con el tipo de dato de la funcion.");
                return false;
            }
        }
        boolean aux = true;
        if (statement.isEmpty()) {
            aux = false;
        }
        for (Simbolo simbolo : statement) {
            switch (simbolo.getTipo().getSymbol()) {
                case Constantes.IF:
                    CondicionalIf condicionalIf = (CondicionalIf) simbolo.getValor();
                    if (condicionalIf.getCondicionalIf().comprobarSiTieneReturn(simboloComprobar, l, r, false)) {
                        if (!condicionalIf.getCondicionalesElseIf().stream().noneMatch((c) -> (!c.comprobarSiTieneReturn(simboloComprobar, l, r, false)))) {
                            return false;
                        }
                        if (condicionalIf.getCondicionalElse() != null) {
                            if (!condicionalIf.getCondicionalElse().comprobarSiTieneReturn(simboloComprobar, l, r, false)) {
                                return false;
                            }
                        } 
                    } else {
                        return false;
                    } break;
                case Constantes.SWITCH:
                    CondicionalSwitch condicionalSwitch = (CondicionalSwitch) simbolo.getValor();
                    if (!condicionalSwitch.getListaCasos().stream().noneMatch((casos) -> (!casos.comprobarSiTieneReturn(simboloComprobar, l, r, false)))) {
                        return false;
                    }
                    break;
                case Constantes.WHILE:
                case Constantes.DO_WHILE:
                case Constantes.FOR:
                    aux = false;
                    if (!((Estructuras) simbolo.getValor()).comprobarSiTieneReturn(simboloComprobar, l, r, false)) {
                        return false;
                    }
                    break;
                default:
                    aux = false;
                    break;
            }
        } 
        return aux;
    }
    
    public boolean comporbarSiTieneReturnMetodo(){
        if (statement.stream().anyMatch((simbolo) -> (simbolo.getTipo().getNombre().equalsIgnoreCase("Return")))) {
            return true;
        }
        boolean aux = false;
        if (statement.isEmpty()) {
            aux = false;
        }
        for (Simbolo simbolo : statement) {
            switch (simbolo.getTipo().getSymbol()) {
                case Constantes.IF:
                    CondicionalIf condicionalIf = (CondicionalIf) simbolo.getValor();
                    
                    if (condicionalIf.getCondicionalIf().comporbarSiTieneReturnMetodo()) {
                        return true;  
                    } 
                    if (condicionalIf.getCondicionalesElseIf().stream().anyMatch((condicional) -> (condicional.comporbarSiTieneReturnMetodo()))) {
                        return true;
                    }
                    if (condicionalIf.getCondicionalElse() != null) {
                        if (condicionalIf.getCondicionalElse().comporbarSiTieneReturnMetodo()) {
                            return true;
                        }
                    } 
                    break;
                case Constantes.SWITCH:
                    CondicionalSwitch condicionalSwitch = (CondicionalSwitch) simbolo.getValor();
                    if (condicionalSwitch.getListaCasos().stream().anyMatch((casos) -> (casos.comporbarSiTieneReturnMetodo()))) {
                        return true;
                    }
                    break;
                case Constantes.WHILE:
                case Constantes.DO_WHILE:
                case Constantes.FOR:
                    if (((Estructuras) simbolo.getValor()).comporbarSiTieneReturnMetodo()) {
                        return true;
                    }
                    break;
                default:
                    break;
            }
        }
        return aux;
    }
}
