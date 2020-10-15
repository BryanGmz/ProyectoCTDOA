/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.ctdoa.backend.manejadores;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import proyectofinal.ctdoa.backend.objetos.Constantes;
import proyectofinal.ctdoa.backend.objetos.Cuarteto;
import proyectofinal.ctdoa.backend.objetos.Simbolo;
import proyectofinal.ctdoa.backend.objetos.Tipo;

/**
 *
 * @author bryangmz
 */
public class ManejadorArreglos {
 
    private static ManejadorArreglos manejadorArreglos;
    private final ManejadorSintacticoJP msjp = ManejadorSintacticoJP.getInstacia();
    private final ManejadorCuartetos manejadorCuartetos = ManejadorCuartetos.getInstancia();
    
    private ManejadorArreglos() {}
    
    public static ManejadorArreglos getInstancia() {
        if (manejadorArreglos == null) {
            manejadorArreglos = new ManejadorArreglos();
        } return manejadorArreglos;
    }
    
    public List<Simbolo> listaDimensiones(Object a, Object e, int l, int r){
        if (a == null || e == null) {
             return null;
        }
        List<Simbolo> lista = new ArrayList<>();
        if (a instanceof Simbolo) { 
            lista.add((Simbolo) a);
        } else  {   
            lista = ((List<Simbolo>) a);
        }
        if (((Simbolo) e).getTipo() != Constantes.CHAR_VAR_PJ && 
                Constantes.FLOAT_VAR_PJ.isFatherOf(((Simbolo) e).getTipo().getSymbol())) {  
            lista.add((Simbolo) e);
        } else {
            msjp.errorSemantico(l, r, "Arreglo", "Error, comprobacion de tipos en la dimension de arreglos.");
        }
        return lista;
    }
    
    public Object comprobarValor(Simbolo s){
        if (s.getCuarteto() != null) {
            return s.getCuarteto().getResultado().getId();
        } else if (s.getId() != null) {
            return s.getId();
        } else {
            return s.getValor();
        }
    }
    
    public List<Object> lista(Object lista){
        List<Object> listaNueva = new ArrayList<>();
        if (lista == null) {
            return listaNueva;
        } else {
            List<Simbolo> listaSimbolos = new ArrayList<>();
            if (lista instanceof Simbolo) {
                listaSimbolos.add(((Simbolo) lista));
            } else {
                listaSimbolos = (List<Simbolo>) lista;
            }
            for (Simbolo simbolo : listaSimbolos) {
                listaNueva.add(comprobarValor(simbolo));
            }
        } return listaNueva;
    }
    
    public void imprimir(List<Object> list){
        list.forEach((object) -> {
            System.out.println(object);
        });
        System.out.println("\n\n");
    }
    
    public void addArreglo(List<Object> lista, Tipo tipo, Simbolo destino){
        if (!lista.isEmpty()) {
            Object aux1 = lista.remove(0);
            if (lista.isEmpty()) {
                manejadorCuartetos.imprimirDeclaracionArreglo(destino, tipo, aux1);
            } else {
                Cuarteto c;
                while (!lista.isEmpty()){
                    try {
                        c = manejadorCuartetos.cuartetoOperacionAritmetica(3, new Simbolo(Constantes.FLOAT_VAR_PJ, aux1), new Simbolo(Constantes.FLOAT_VAR_PJ, lista.remove(0)), null, Constantes.FLOAT_VAR_PJ);
                        aux1 = c.getResultado().getId();
                    } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(ManejadorArreglos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                manejadorCuartetos.imprimirDeclaracionArreglo(destino, tipo, aux1);
            }            
        }
    }
    
    public void recursivo(){
        
    }
}
