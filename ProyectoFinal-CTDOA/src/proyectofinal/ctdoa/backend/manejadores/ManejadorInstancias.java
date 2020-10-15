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
import proyectofinal.ctdoa.backend.objetos.Funcion;
import proyectofinal.ctdoa.backend.objetos.Metodo;
import proyectofinal.ctdoa.backend.objetos.Simbolo;
import proyectofinal.ctdoa.backend.objetos.Tipo;

/**
 *
 * @author bryangmz
 */
public class ManejadorInstancias {
    
    private Simbolo claseVB;
    private List<Simbolo> clasesJAVA;
    private Simbolo clasePY;
    private static ManejadorInstancias manejadorInstancias;
    private final ManejadorSintacticoJP msjp = ManejadorSintacticoJP.getInstacia();
    private ManejadorCuartetos manejadorCuartetos = ManejadorCuartetos.getInstancia();
    
    private ManejadorInstancias() {}
    
    public static ManejadorInstancias getInstance(){
        if (manejadorInstancias == null) {
            manejadorInstancias = new ManejadorInstancias();
        } return manejadorInstancias;
    }    
    
    public void nuevoAnalisis() {
        this.clasePY = null;
        this.claseVB = null;
        this.clasesJAVA.clear();
    }

    public Simbolo getClaseVB() {
        return claseVB;
    }

    public void setClaseVB(Simbolo claseVB) {
        this.claseVB = claseVB;
    }

    public void addClasesJava(Object clases){
        if (clasesJAVA == null) {
            clasesJAVA = new ArrayList<>();
        }
        if (clases != null) {
            if (clases instanceof Simbolo) {
                clasesJAVA.add((Simbolo) clases);
            } else {
                clasesJAVA.addAll((List<Simbolo>) clases);
            }
        }
    }
    
    public Simbolo getClasePY() {
        return clasePY;
    }

    public void setClasePY(Simbolo clasePY) {
        this.clasePY = clasePY;
    }
    
    public List<Simbolo> param(Object params){
        List<Simbolo> lista = new ArrayList<>();
        if (params != null) {
            if (params instanceof Simbolo) {
                lista.add((Simbolo) params);
            } else {
                lista = (List<Simbolo>) params;
            }
        }
        return lista;
    }
   
    public boolean comprobarMatchParam(Object paramMethodFunction, Object paramInvoke, int l, int r, boolean python) {
        List<Simbolo> listParamMF = param(paramMethodFunction);
        List<Simbolo> listaParamInvoke = param(paramInvoke);
        if (listParamMF.isEmpty() && listaParamInvoke.isEmpty()) {
            return true;
        }
        if (listParamMF.size() == listaParamInvoke.size()) {
            if (python) {
                return true;
            }
            if (listParamMF.get(0).getTipo() != null && listaParamInvoke.get(0).getTipo() != null) {
                for (int i = 0; i < listParamMF.size(); i++) {
                    if (listParamMF.get(i).getTipo() != null && listaParamInvoke.get(i) != null) {
                        if (!(listaParamInvoke.get(i).getTipo().isFatherOf(listParamMF.get(i).getTipo().getSymbol()))) {
                            msjp.errorSemantico(l, r, "Match", "Error, los parametros no no son compatibles a nivel de tipo.");
                            return false;
                        } 
                    } else {
                        msjp.errorSemantico(l, r, "Match", "Error, los parametros no no son compatibles a nivel de tipo.");
                        return false;
                    }
                }
                return true;
            } 
            msjp.errorSemantico(l, r, "Match", "Error, los parametros no hacen match con los tipos al llamar una funcion o metodo.");
            return false;
        } else {
            if (listaParamInvoke.isEmpty()) {
                return false;
            }
            msjp.errorSemantico(l, r, "Match", "Error, los parametros no hacen match con los tipos al llamar una funcion o metodo.");
            return false;
        }
       
    }
    
    public Simbolo contructores(String idClase, int l, int r, Object param){
        for (Simbolo simbolo : clasesJAVA) {
            if (simbolo.getId().equals(idClase)) {
                for (Simbolo intrucciones : (List<Simbolo>) simbolo.getValor()) {
                    if (intrucciones.getId().equals(idClase)) {
                        Metodo m = (Metodo) intrucciones.getValor();
                        if (comprobarMatchParam(m.getParametros(), param, l, r, false)) {
                            return intrucciones;
                        } else {
                            List<Simbolo> lista = param(param);
                            if (lista.isEmpty()) {
                                return simbolo;
                            }
                            msjp.errorSemantico(l, r, "Match", "Error, los parametros no hacen match con los tipos al llamar una funcion o metodo.");
                            return null;
                        }
                    }
                } 
                if (param != null && param instanceof ArrayList) {
                    if (((List<Simbolo>) param).isEmpty()) {
                        return  simbolo;
                    } else {
                        msjp.errorSemantico(l, r, "Instancia", "Error, no se encuentra definida el constructor con los parametros enviados.");
                    }
                }

            }
        } 
        msjp.errorSintax(l, r, "Instancia", "Error, no se ecuentra ninguna clase con el id: " + idClase + ", en la libreira de Java.");
        return null;
    }
    
    public void tipado(Simbolo simbolo){
        switch (simbolo.getTipoFuncion().getSymbol()) {
            case Constantes.CHAR:
                simbolo.setTipo(Constantes.CHAR_VAR_PJ);
                break;
            case Constantes.FLOAT:
                simbolo.setTipo(Constantes.FLOAT_VAR_PJ);
                break;
            case Constantes.INT:
                simbolo.setTipo(Constantes.INT_VAR_PJ);
                break;
            default:
                throw new AssertionError();
        }
        simbolo.setTipoFuncion(new Tipo("Funcion", Constantes.FUNCION));
    }
    
    public Simbolo buscarFuncion(String idFuncion, Simbolo clase, Object param, int l, int r, String mensaje, boolean python) {
        if (clase == null) {
                msjp.errorSemantico(l, r, "Invoke", mensaje);
                return null;
            } else {
                for (Simbolo instrucciones : (List<Simbolo>) clase.getValor()) {
                    if (instrucciones.getId() != null) {
                        if (instrucciones.getId().equals(idFuncion)) {
                            if (instrucciones.getTipo() != null && instrucciones.getTipo().getNombre() != null) {
                                if (instrucciones.getTipo().getNombre().equalsIgnoreCase("Metodo")) {
                                    Metodo m = (Metodo) instrucciones.getValor();
                                    if (comprobarMatchParam(m.getParametros(), param, l, r, python)) {
                                        return  instrucciones;
                                    } else { 
                                        return null;
                                    }
                                } else if (instrucciones.getTipo().getNombre().equalsIgnoreCase("Funcion")) {
                                    Funcion f = (Funcion) instrucciones.getValor();
                                    if (comprobarMatchParam(f.getParametros(), param, l, r, python)) {
                                        try {
                                            Simbolo s = instrucciones.clone();
                                            tipado(s);
                                            return s;
                                        } catch (CloneNotSupportedException ex) {
                                            Logger.getLogger(ManejadorInstancias.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    } else {
                                        return null;
                                    }
                                }
                            }
                        }
                    }
                } 
            msjp.errorSemantico(l, r, "Invoke", "Error, la funcion o metodo no se encuentra definida.");
            return null;
        }
    }
    
    public Simbolo existeFuncion(int clase, String idFuncion, String idClase, Object listaParam, int l, int r){
        switch (clase) {
            case 1://Visual Basic 
                return buscarFuncion(idFuncion, claseVB, listaParam, l, r, "Error, en la libreria VB no se encuentran instrucciones.", false);
            case 2:
                if (clasesJAVA != null && !clasesJAVA.isEmpty() ) {
                    for (Simbolo simbolo : clasesJAVA) {
                        if (simbolo.getId() != null && simbolo.getId().equals(idClase)) {
                            return buscarFuncion(idFuncion, simbolo, listaParam, l, r, "Error, en la clase: " + idClase + " no se encuentran definido instrucciones", false);
                        }
                    }
                }
            case 3:
                return buscarFuncion(idFuncion, clasePY, listaParam, l, r, "Error, en la libreria PY no se encuentran instrucciones.", true);
        } return null;
    }
    
    public Simbolo buscarMetodo(Simbolo clase, Object param, String idFuncion, int tipo, int l, int r, String claseBuscarLenguajes){
        if (clase == null) {
            return null;
        }
        Simbolo claseBuscar = clase;
        if (claseBuscar.getValor() == null) {
            msjp.errorSemantico(l, r, "Instancia", "Error, la clase no possee instrucciones.");
            return null;
        }
        List<Simbolo> listaMetodosYFunciones = new ArrayList<>();
        if (clase.getValor() instanceof Simbolo) {
            listaMetodosYFunciones.add((Simbolo) clase.getValor());
        } else {
            listaMetodosYFunciones = (List<Simbolo> ) clase.getValor();
        }
        for (Simbolo lmf : listaMetodosYFunciones) {
            if  (lmf.getId() == null ? idFuncion == null : lmf.getId().equals(idFuncion)){
                return existeFuncion(tipo, idFuncion, claseBuscarLenguajes, param, l, r);
            }
        }
        msjp.errorSemantico(l, r, "Invoke", "Error, no se encuentra definida una funcion o metodo con el ID: " + idFuncion);
        return null;
   }
    
    public Simbolo searchClase(String id, int l, int r){
        for (Simbolo simbolo : clasesJAVA) {
            if (simbolo.getId().equals(id)) {
                return simbolo;
            }
        } 
        msjp.errorSemantico(l, r, "Clase", "Error, no se encuentra definido una clase con el ID: " + id);
        return null;
    }
    
    public Cuarteto escribirFuncion(String funcion, Object params){
        if (params != null) {
            manejadorCuartetos.imprimirParams((List<Simbolo>) params);
            return manejadorCuartetos.imprimirProcedimiento(funcion, params);
        } else {
            manejadorCuartetos.imprimirParams(new ArrayList<>());
            return manejadorCuartetos.imprimirProcedimiento(funcion, new ArrayList<>());
        }
    }
    
    public Simbolo searchSimbolo(String id, int caso, int l, int r, Object params, String idClase){
        switch (caso) {
            case 1: {
                Simbolo s = msjp.getTablaSimbolos().buscarPorId("$VB");
                if (s == null) {
                    msjp.errorSintax(l, r, "Libreria", "Error, no se encuentra definido la libreria. < VB >");
                }
                Simbolo simbolo = buscarMetodo(claseVB, params, id, 1, l, r, idClase);
                if (simbolo != null) {
                    simbolo.setCuarteto(escribirFuncion("VB_" + id, params));
                }
                return simbolo;
            }
            case 2: {
                Simbolo s = msjp.getTablaSimbolos().buscarPorId("$*");
                Simbolo simbolo;
                if (s == null) {
                    s = msjp.getTablaSimbolos().buscarPorId("$" + idClase);
                    if (s == null) {
                        msjp.errorSintax(l, r, "Libreria", "Error, no se encuentra definido la libreria. < JAVA: " + idClase + ">");
                        return null;
                    } else {
                        simbolo = buscarMetodo(searchClase(idClase, l, r), params, id, 2, l, r, idClase);
                        if (simbolo != null) {
                            simbolo.setCuarteto(escribirFuncion("JAVA_" + idClase + "_" + id, params));
                        }
                        return simbolo;
                    }
                } 
                simbolo = buscarMetodo(searchClase(idClase, l, r), params, id, 2, l, r, idClase);
                if (simbolo != null) {
                    simbolo.setCuarteto(escribirFuncion("JAVA_" + idClase +"_" + id, params));
                }
                return simbolo;
            }
            case 3: {
                Simbolo s = msjp.getTablaSimbolos().buscarPorId("$PY");
                if (s == null) {
                    msjp.errorSintax(l, r, "Libreria", "Error, no se encuentra definido la libreria. < PY >");
                }
                Simbolo simbolo = buscarMetodo(clasePY, params, id, 3, l, r, idClase);
                if (simbolo != null) {
                    simbolo.setCuarteto(escribirFuncion("PY_" + id, params));
                }
                return simbolo;
            }
        } return null;
    }
    
    public Simbolo buscarInstancia(String  idFuncion,String idInstacia, int l, int r, Object params, String idClase){
        Simbolo instacia = msjp.getTablaSimbolos().buscarPorId(idInstacia);
        if (instacia == null) {
            return null;
        }
        if (instacia.getTipo().getNombre().equals("Instancia")) {
            return searchSimbolo(idFuncion, 2, l, r, params, instacia.getValor().toString());
        } 
        msjp.errorSemantico(l, r, idClase, "Error, la variable no es una instancia de una de las clases de java.");
        return null;
    }
    
}
