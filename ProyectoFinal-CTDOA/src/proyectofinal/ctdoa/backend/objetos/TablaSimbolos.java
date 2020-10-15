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
public class TablaSimbolos {
    
    private final List<Simbolo> simbolos;
    
    public TablaSimbolos() {
        this.simbolos = new ArrayList<>();
    }

    public Simbolo getUltimo(){
        if (!simbolos.isEmpty()) {
            return simbolos.get(simbolos.size() - 1);
        } return null;
    }
    
    public void removerPorTipo(int removerTipo) {
        for (Simbolo simbolo : simbolos) {
            if (simbolo.getTipo().getSymbol() == (removerTipo)) {
                simbolos.remove(simbolo);
                break;
            }
        }
    }
    
    public void removerPorIdTipo(int tipo, String id){
        for (Simbolo simbolo : simbolos) {
            if (simbolo.getId() != null && simbolo.getTipo().getSymbol() == (tipo) && simbolo.getId().equals(id)) {
                simbolos.remove(simbolo);
                break;
            }
        }
    }
    
    public void removerPorId(String removerId) {
        simbolos.stream().filter((simbolo) -> (simbolo.getId().equalsIgnoreCase(removerId))).forEachOrdered((simbolo) -> {
            simbolos.remove(simbolo);
        });
    }
    
    public Object buscarPorTipo(int tipo) {
        for (Simbolo simbolo : simbolos) {
            if (simbolo.getTipo().getSymbol() == (tipo)) {
                return simbolo.getValor();
            }
        } return null;
    }
    
    public Simbolo buscarPorId(String id) {
        for (int i = (simbolos.size() - 1); i >= 0; i--) {
            if (simbolos.get(i).getId() != null && simbolos.get(i).getId().equals(id)) {
                return simbolos.get(i);
            }
        }
        
        /*for (Simbolo simbolo : simbolos) {
            if (simbolo.getId() != null && simbolo.getId().equals(id)) {
                return simbolo;
            }
        } */
        return null;
    }
    
    public Simbolo buscarPorIdAmbito(String id, int ambito){
        for (Simbolo simbolo : simbolos) {
            if (simbolo.getId() != null && simbolo.getId().equals(id) && simbolo.getAmbito() == ambito) {
                return simbolo;
            }
        } return null;
    }
    
    public boolean agregarTablaSimbolos(Simbolo simbolo){
        if (getUltimo() == null) {
            simbolos.add(simbolo);
            return true;
        } else if (getUltimo().getTipo() != null && !getUltimo().getTipo().getNombre().equalsIgnoreCase("Return")){
            simbolos.add(simbolo);
            return true;
        } else if (getUltimo().getTipo() == null) {
            simbolos.add(simbolo);
            return true;
        } return false;
    }

    public void removerPorReferencia(Simbolo sim) {
        simbolos.remove(sim);
    }
    
    public void removerSimbolosAll() {
        List<Simbolo> nueva = new ArrayList<>();
        simbolos.stream().filter((simbolo) -> (!(simbolo.getTipo().getNombre().equalsIgnoreCase("Metodo")
                || simbolo.getTipo().getNombre().equalsIgnoreCase("Funcion")))).forEachOrdered((simbolo) -> {
                    nueva.add(simbolo);
        });
        simbolos.removeAll(nueva);
    }
    
    public void removerSimbolos(List<Simbolo> lista){
        int contador = 0;
        while (contador < lista.size()) {
            simbolos.remove(lista.get(contador));           
            contador++;
        } 
    }
    
    public void print(){
        System.out.println("\n\nTABLA DE SIMBOLOS");
        for (Simbolo simbolo : simbolos) {
            System.out.println("" + simbolo.getId() + "\t\t\t" + simbolo.getAmbito()+ "\t\t\t" + simbolo.getTipo() + "\t\t\t" + simbolo.getValor());
        }
    }
    
}
