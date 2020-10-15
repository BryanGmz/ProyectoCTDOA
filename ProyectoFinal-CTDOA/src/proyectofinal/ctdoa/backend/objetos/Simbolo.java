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
public class Simbolo {
    
    private Tipo tipo;
    private Object valor;
    private Tipo tipoFuncion;
    private String id;
    private int ambito;
    private int aux;
    private Cuarteto cuarteto;
    private List<Cuarteto> listaCuarteto;
    private boolean funcion;
    private int input;
    
    public Simbolo(Tipo tipo, Object valor) {
        this.tipo = tipo;
        this.valor = valor;
    }
    
    public Simbolo(Tipo tipo, Object valor, String id) {
        this.tipo = tipo;
        this.valor = valor;
        this.id = id;
    }

    public Simbolo(Tipo tipo, Object valor, int input) {
        this.tipo = tipo;
        this.valor = valor;
        this.input = input;
    }

    public Simbolo(Tipo tipo, Object valor, String id, int ambito) {
        this.tipo = tipo;
        this.valor = valor;
        this.id = id;
        this.ambito = ambito;
    }

    public Simbolo(Tipo tipo, Object valor, Tipo tipoFuncion, String id, int ambito, int aux, Cuarteto cuarteto) {
        this.tipo = tipo;
        this.valor = valor;
        this.tipoFuncion = tipoFuncion;
        this.id = id;
        this.ambito = ambito;
        this.aux = aux;
        this.cuarteto = cuarteto;
    }

    public int getInput() {
        return input;
    }

    public void setInput(int input) {
        this.input = input;
    }

    public boolean isFuncion() {
        return funcion;
    }

    public void setFuncion(boolean funcion) {
        this.funcion = funcion;
    }
    
    public void inicializarLista(){
        listaCuarteto = new ArrayList<>();
    }

    public List<Cuarteto> getListaCuarteto() {
        return listaCuarteto;
    }

    public void setListaCuarteto(List<Cuarteto> listaCuarteto) {
        this.listaCuarteto = listaCuarteto;
    }
    
    public Tipo getTipoFuncion() {
        return tipoFuncion;
    }

    public void setTipoFuncion(Tipo tipoFuncion) {
        this.tipoFuncion = tipoFuncion;
    }
    
    public int getAux() {
        return aux;
    }

    public void setAux(int aux) {
        this.aux = aux;
    }

    public Cuarteto getCuarteto() {
        return cuarteto;
    }

    public void setCuarteto(Cuarteto cuarteto) {
        this.cuarteto = cuarteto;
    }
    
    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmbito() {
        return ambito;
    }

    public void setAmbito(int ambito) {
        this.ambito = ambito;
    }
    
    /**
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public Simbolo clone() throws CloneNotSupportedException{
        return new Simbolo(tipo, valor, tipoFuncion, id, ambito, aux, cuarteto);
    }
 
    @Override
    public String toString(){
        if (id == null) {
            if (valor == null) {
                return id;
            }
            return valor.toString();
        }
        return id;
    }
    
}
