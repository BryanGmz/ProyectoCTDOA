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
public class Cuarteto {
    
    private String operador;
    private Simbolo operando1;
    private Simbolo operando2;
    private Simbolo resultado;
    private String cod3D;

    public Cuarteto(String operador, Simbolo operando1, Simbolo operando2, Simbolo resultado) {
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = operando2;
        this.resultado = resultado;
    }
    
    public Cuarteto clone(){
        return new Cuarteto(operador, operando1, operando2, resultado);
    }
    
    public String getCod3D() {
        return cod3D;
    }

    public void setCod3D(String cod3D) {
        this.cod3D = cod3D;
    }

    public String getOperador() {
        return operador;
    }

    public void setOperador(String operador) {
        this.operador = operador;
    }

    public Simbolo getOperando1() {
        return operando1;
    }

    public void setOperando1(Simbolo operando1) {
        this.operando1 = operando1;
    }

    public Simbolo getOperando2() {
        return operando2;
    }

    public void setOperando2(Simbolo operando2) {
        this.operando2 = operando2;
    }

    public Simbolo getResultado() {
        return resultado;
    }

    public void setResultado(Simbolo resultado) {
        this.resultado = resultado;
    }
    
    
}
