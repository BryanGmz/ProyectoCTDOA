/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.ctdoa.backend.manejadores;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import proyectofinal.ctdoa.backend.objetos.*;
import proyectofinal.ctdoa.frontend.gui.FrameCTDOA;

/**
 *
 * @author bryangmz
 */
public class ManejadorSintacticoJP {
    
    private static ManejadorSintacticoJP manejadorSintactico;
    private static ManejadorCuartetos manejadorCuartetos = ManejadorCuartetos.getInstancia();
    private FrameCTDOA frameCTDOA;
    private static TablaSimbolos tablaSimbolos;
    private int contadorAmbitos;
    private int contadorLineas;
    
    protected ManejadorSintacticoJP() {}
    
    /** Llamado a la instancia unica de esta clase
     * @return  Retorna la instancia de la clase
     */
    
    public static ManejadorSintacticoJP getInstacia(){
        if (manejadorSintactico == null) {
            manejadorSintactico = new ManejadorSintacticoJP();
        } return manejadorSintactico;
    }

    public void setFrameCTDOA(FrameCTDOA frameCTDOA) {
        this.frameCTDOA = frameCTDOA;
    }
    
    public TablaSimbolos getTablaSimbolos() {
        return tablaSimbolos;
    }
    
    public void setContadorLineas(int contadorLineas){
        this.contadorLineas = contadorLineas;
    }
    
    /** Metodo para resetear los recursos que se utilizaran en esta clase. 
    */
    
    public void nuevoAnalisis(){
        this.contadorAmbitos = 1;
        tablaSimbolos = new TablaSimbolos();
    }
    
    public void contadorAmbitos(boolean aumentar){
        if (aumentar) {
            contadorAmbitos++;
        } else {
            contadorAmbitos--;
        }
    }
    
    // Metodos que almacenan los errores
    
    /* Errores */
    
    public void errorSintax(int left, int right, Object value, String mensaje){
        if (frameCTDOA == null) {
            JOptionPane.showMessageDialog(null, "Ups, algo salio mal, vuelve a intentarlo.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            if (mensaje.isEmpty()) {
                frameCTDOA.addErrores(
                "\nError de Sintaxis: " 
                + "\n\tLinea #:                 << " + (right + 1 + contadorLineas) + " >>"
                + "\n\tColumna #                << " + (left + 1) + " >>"
                + "\n\tToken NO Reconocido:     << " + (value) + " >>" );
            } else {
                frameCTDOA.addErrores(
                "\nError de Sintaxis: " 
                + "\n\tLinea #:                 << " + (right + 1 + contadorLineas) + " >>"
                + "\n\tColumna #                << " + (left + 1) + " >>"
                + "\n\tToken NO Reconocido:     << " + (value) + " >>" 
                + "\n\tMensaje (Informacion): "
                + "\n\t\t-> " + mensaje);
            }
        }
    }
    
    public void errorSemantico(int left, int right, Object valor, String mensaje){
        if (frameCTDOA == null) {
            JOptionPane.showMessageDialog(null, "Ups, algo salio mal, vuelve a intentarlo.", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {       
            frameCTDOA.addErrores(
                "\nError Semantico: " 
                + "\n\tLinea #:                 << " + (right + 1 + contadorLineas) + " >>"
                + "\n\tColumna #                << " + (left + 1) + " >>"
                + "\n\tValor:                   << " + valor + " >>"
                + "\n\tMensaje (Informacion): "
                + "\n\t\t-> " + mensaje);
        }
    }
    
    public Simbolo agregarCualquierSimbolo(Simbolo s){
        if (tablaSimbolos.agregarTablaSimbolos(s)) {
            return s;
        } return null;
    }
    
    
    
    public List<Simbolo> lista(Object a, Object e){
        List<Simbolo> lista = new ArrayList<>();
        if (a != null) {
            if (a instanceof Simbolo) { 
                lista.add((Simbolo) a);
            } else  {   
                lista = ((List<Simbolo>) a);
            }
        }
        if (e != null) {
            lista.add((Simbolo) e);
        }
        return lista;
    }
    
    public Simbolo agregarLibreria(String libreria, int l, int r, int caso){
        Simbolo library = tablaSimbolos.buscarPorId("$" + libreria);
        if (library == null) {
            libreria = libreria.replaceAll("\"", "");
            switch (caso) {
                case 1:
                    library = new Simbolo(new Tipo("LibreriaVB", Constantes.LIBRERIAS), libreria, "$" + libreria);
                    break;
                case 2:
                    library = new Simbolo(new Tipo("LibreriaJava", Constantes.LIBRERIAS), "*", "$" + "*");
                    break;
                case 3:
                    library = new Simbolo(new Tipo("LibreriaPY", Constantes.LIBRERIAS), libreria, "$" + libreria);
                    break;
                case 4:
                    library = new Simbolo(new Tipo("LibreriaC", Constantes.LIBRERIAS), libreria, "$" + libreria);
                    manejadorCuartetos.addLibreria(libreria);
                    break;
                case 5:
                    library = new Simbolo(new Tipo("LibreriaJava", Constantes.LIBRERIAS), libreria, "$" + libreria);
                    break;
            }
            tablaSimbolos.agregarTablaSimbolos(library);
            return library;
        } else { 
            errorSintax(l, r, "Libreria", "Error, ya se encuentra definida la libreria");
            return null;
        }
    }
    
    
    public void removerCualquierSimbolo(Object s){
        if (s != null) {
            tablaSimbolos.removerPorReferencia((Simbolo) s);
        }
    }
    
    // Metodos para declarar variables
    
    /**
     * Metodo que declara una variable sin el tipo de id, y comprueba que el valor asignado sea el correspondiente 
     * @param s valor que se le asignara a la variable
     * @param l left del simbolo
     * @param r right del simbolo
     * @return La declaracion de la variable
     */
    

    public Object declararVariablesGlobales(Simbolo s, int l, int r){
        Tipo tipo = s.getTipo();
        if (s.getValor() instanceof Simbolo) {
            if (((Simbolo) s.getValor()).getTipo() == null) {
                ((Simbolo) s.getValor()).setTipo(tipo);
                ((Simbolo) s.getValor()).setAmbito(0);
                if (tablaSimbolos.buscarPorId(((Simbolo) s.getValor()).getId()) == null) {
                    tablaSimbolos.agregarTablaSimbolos(((Simbolo) s.getValor()));
                    if (s.getId() != null && !s.getId().isEmpty()) {
                        auxiliarVariable(s);
                    } else {
                        auxiliarVariable(((Simbolo) s.getValor()));
                    }
                    return ((Simbolo) s.getValor());
                } else {
                    errorSintax(l, r, "Declaracion", "Error: Declaracion de variable < " + (((Simbolo) s.getValor()).getId()) + " > ya existe una variable declarada con el mismo nombre.");
                    return null;
                }
            } else {
                if (tipo.isFatherOf(s.getTipo().getSymbol())) {
                    ((Simbolo) s.getValor()).setTipo(tipo);
                    ((Simbolo) s.getValor()).setAmbito(0);
                    if (tablaSimbolos.buscarPorId(((Simbolo) s.getValor()).getId()) == null) {
                        tablaSimbolos.agregarTablaSimbolos(((Simbolo) s.getValor()));
                        if (s.getId() != null && !s.getId().isEmpty()) {
                            auxiliarVariable(s);
                        } else {
                            auxiliarVariable(((Simbolo) s.getValor()));
                        }
                        return ((Simbolo) s.getValor());
                    } else {
                        errorSintax(l, r, "Declaracion" , "Error: Declaracion de variable < " + (((Simbolo) s.getValor()).getId()) + " > ya existe una variable declarada con el mismo nombre.");
                        return null;
                    }
                } else {
                    errorSintax(l, r, "Declaracion", "Error: El tipo de dato no es compatible al que se desea agregas");
                    return null;
                }
            }
        } else {
            List<Simbolo> retornar = new ArrayList<>();
            for (Simbolo simbolo : (List<Simbolo>) s.getValor()) {
                simbolo.setAmbito(0);
                if (((Simbolo) simbolo.getValor()).getTipo() == null) {
                    simbolo.setTipo(tipo);
                    if (tablaSimbolos.buscarPorId(simbolo.getId()) == null) {
                        tablaSimbolos.agregarTablaSimbolos(simbolo);
                        retornar.add(simbolo);
                        auxiliarVariable(simbolo);
                    } else {
                        errorSintax(l, r, "Declaracion", "Error: Declaracion de variable < " + simbolo.getId() + " > ya existe una variable declarada con el mismo nombre.");
                    }
                } else {
                    if (tipo.isFatherOf(simbolo.getTipo().getSymbol())) {
                        simbolo.setTipo(tipo);
                        if (tablaSimbolos.buscarPorId(simbolo.getId()) == null) {
                            tablaSimbolos.agregarTablaSimbolos(simbolo);
                            retornar.add(simbolo);
                            auxiliarVariable(simbolo);
                        } else {
                            errorSintax(l, r, "Declaracion" , "Error: Declaracion de variable < " + simbolo.getId() + " >ya existe una variable declarada con el mismo nombre.");
                        }
                    } else {
                        errorSintax(l, r, "Declaracion", "Error: El tipo de dato no es compatible al que se desea agregas");
                    }
                }
            } return retornar;
        }
    }
   
    /**
     * Metodo que declara una variable, y comprueba que el valor asignado sea el correspondiente 
     * @param tipo tipo de dato de la variable
     * @param id nombre de la variable
     * @param l left del simbolo
     * @param r right del simbolo
     * @return La declaracion de la variable
     */
    
    public Simbolo declaraUnaVariableParametro(Tipo tipo, String id, int l, int r){
        Simbolo aux = tablaSimbolos.buscarPorId(id);
        if (aux == null || aux.getAmbito() == 0) {
            Simbolo simbolo  = new Simbolo(tipo, null, id);
            simbolo.setAmbito(1);
            tablaSimbolos.agregarTablaSimbolos(simbolo);
            return simbolo;
        } else {
            errorSintax(l, r, id, "Ya se encuentra definida la variable: < " + id + " >");
            return null;
        }
    }
    
    public Simbolo declararArreglo(Object a, int l, int r, Tipo tipo){
        if (a == null) {
            errorSemantico(l, r, "Variable", "Error, en la asignacion de dimensiones del arreglo.");
            return null;
        }
        int dimension;
        if (((Simbolo) a).getValor() instanceof Simbolo) {
            dimension = 1;
        } else {
            dimension = ((List<Simbolo>) ((Simbolo) a).getValor()).size();
        }
        Simbolo arreglo = tablaSimbolos.buscarPorId(((Simbolo) a).getId());
        if (arreglo == null) {
            arreglo = new Simbolo(tipo, ((List<Simbolo>) ((Simbolo) a).getValor()), ((Simbolo) a).getId());
            arreglo.setAux(dimension);
            ManejadorArreglos ma = ManejadorArreglos.getInstancia();
            ma.addArreglo(ma.lista((List<Simbolo>) ((Simbolo) a).getValor()), tipo, arreglo);
            ma.imprimir(ma.lista( (List<Simbolo>) ((Simbolo) a).getValor()) );
            tablaSimbolos.agregarTablaSimbolos(arreglo);
            return  arreglo;
        } else {
            errorSemantico(l, r, "Variables", "Error, ya se encuentra definida una variable con el ID: " + ((Simbolo) a).getId());
            return null;
        }
    }
    
    public void auxiliarVariable(Simbolo simbolo){
        if (simbolo.getValor() == null) {
            simbolo.setCuarteto(manejadorCuartetos.declararVariable(null, simbolo));
        } else {
            if (simbolo.getValor() instanceof Simbolo) {
                if (((Simbolo) simbolo.getValor()).getCuarteto() == null) {
                    simbolo.setCuarteto(manejadorCuartetos.declararVariable(((Simbolo) simbolo.getValor()), simbolo));
                    if(((Simbolo) simbolo.getValor()).getInput() != 0){
                        manejadorCuartetos.imprimirScanf(((Simbolo) simbolo.getValor()).getInput(), simbolo.getId());
                    }
                } else { 
                    simbolo.setCuarteto(manejadorCuartetos.declararVariable(((Simbolo) simbolo.getValor()).getCuarteto().getResultado(), simbolo));
                }
            } else {
                simbolo.setCuarteto(manejadorCuartetos.declararVariable(new Simbolo(null, simbolo.getValor()), simbolo));
            }
        }
    }
    
    /**
     * Metodo que declara varias variables
     * @param a Objeto que contiene las declaraciones
     * @param l left del simbolo
     * @param r right del simbolo
     * @return La declaracion de la variable
     */
    
    public Object declararVariables(Object a, int l, int r){
        if (a == null) {
            return null;
        }
        Simbolo s = (Simbolo) a;
        Tipo tipo = s.getTipo();
        Simbolo aux;
        if (s.getValor() instanceof Simbolo) {
            if (((Simbolo) s.getValor()).getTipo() == null) {
                aux = (Simbolo) s.getValor();
                aux.setTipo(tipo);
                aux.setAmbito(1);
                Simbolo comprobar = tablaSimbolos.buscarPorId(aux.getId());
                if (comprobar == null || comprobar.getAmbito() == 0) {
                    if (tablaSimbolos.agregarTablaSimbolos(aux)) {
                        auxiliarVariable(aux);
                        return aux;
                    } else { 
                        errorSemantico(l, r, "Declaracion", "Error: Despues de haber declarado la instruccion return, no se podra seguir declarando mas instrucciones.");
                        return null;
                    }
                } else {
                    errorSintax(l, r, "Declaracion", "Error: Declaracion de variable <" + aux.getId() + "> ya existe una variable declarada con el mismo nombre.");
                    return null;
                }
            } else {
                if (tipo.isFatherOf(s.getTipo().getSymbol())) {
                    aux = (Simbolo) s.getValor();
                    aux.setTipo(tipo);
                    aux.setAmbito(contadorAmbitos);
                    Simbolo comprobar = tablaSimbolos.buscarPorId(aux.getId());
                    if (comprobar == null || comprobar.getAmbito() == 0) {
                        if (tablaSimbolos.agregarTablaSimbolos(aux)) {
                            auxiliarVariable(aux);
                            return aux;
                        } else {
                            errorSemantico(l, r, "Declaracion", "Error: Despues de haber declarado la instruccion return, no se podra seguir declarando mas instrucciones.");
                            return null;
                        }
                    } else {
                        errorSintax(l, r, "Declaracion", "Error: Declaracion de variable <" + aux.getId() + "> ya existe una variable declarada con el mismo nombre.");
                        return null;
                    }
                } else {
                    errorSintax(l, r, "Declaracion", "Error: El tipo de dato no es compatible al que se desea agregas");
                    return null;
                }
            }
        } else {
            List<Simbolo> retornar = new ArrayList<>();
            for (Simbolo simbolo : (List<Simbolo>) s.getValor()) {
                simbolo.setAmbito(contadorAmbitos);
                if (simbolo.getTipo() == null) {
                    Simbolo comprobar = tablaSimbolos.buscarPorId(simbolo.getId());
                    if (comprobar == null || comprobar.getAmbito() == 0) {
                        simbolo.setTipo(tipo);
                        if (tablaSimbolos.agregarTablaSimbolos(simbolo)) {
                            auxiliarVariable(simbolo);
                            retornar.add(simbolo);
                        } else { 
                            errorSemantico(l, r, "Declaracion", "Error: Despues de haber declarado la instruccion return, no se podra seguir declarando mas instrucciones.");
                        }
                    } else {
                        errorSintax(l, r, "Declaracion", "Error: Declaracion de variable < " + simbolo.getId() +  "> ya existe una variable declarada con el mismo nombre.");
                    }
                } else {
                    if (tipo.isFatherOf(simbolo.getTipo().getSymbol())) {
                        Simbolo comprobar = tablaSimbolos.buscarPorId(simbolo.getId());
                        if (comprobar == null || comprobar.getAmbito() == 0) {
                            simbolo.setTipo(tipo);
                            if (tablaSimbolos.agregarTablaSimbolos(simbolo)) {
                                auxiliarVariable(simbolo);
                                retornar.add(simbolo);
                            } else { 
                                errorSemantico(l, r, "Declaracion", "Error: Despues de haber declarado la instruccion return, no se podra seguir declarando mas instrucciones.");
                            }
                        } else {
                            errorSintax(l, r, "Declaracion", "Error: Declaracion de variable < " + simbolo.getId() +  "> ya existe una variable declarada con el mismo nombre.");
                        }
                    } else {
                        errorSintax(l, r, "Declaracion", "Error: ID <" + simbolo.getId() + "> El tipo de dato no es compatible.");
                    }
                }
            } return retornar;
        }
    }
    
    /**
     * Funcion para buscar un id en la tabla de simbolos
     * @param id nombre de la variable
     * @param l left del simbolo
     * @param r right del simbolo
     * @return Retorna el simmbolo
     */
    
    public Simbolo metodoBuscarID(String id, int l, int r){
        Simbolo retornar = tablaSimbolos.buscarPorId(id);
        if (retornar == null) {
            errorSintax(l, r, id, "Error no se encuentra definida la variable");
        }
        return retornar;
    }
    
    /**
     * Funcion para modiicar el valor de una variable (Hacerla negativa)
     * @param s Simbolo
     * @param l left del simbolo
     * @param r right del simbolo
     * @return Retorna el valor modificado
     * @throws java.lang.CloneNotSupportedException
     */
    
    public Simbolo modificarVariables(Object s, int l, int r) throws CloneNotSupportedException{
        if (s == null) {
            return null;
        } else {
            if (((Simbolo) s).getTipo() == Constantes.CHAR_VAR_VB_PY) {
                errorSemantico(l, r, "Operacion", "El tipo de dato: Char, no puede tener un signo negativo.");
                return null;
            }
        } return (Simbolo) s;
    }
    
    /**
     * Funcion que comprueba si dos operandos son del mismo tipo
     * @param op1 OOperando 1
     * @param op2 Operando 2
     * @param l1 left del simbolo
     * @param r2 right del simbolo
     * @param r1 right del simbolo
     * @param l2 left del simbolo
     * @return Retorna true si pueden ser operados, de lo contrario false
     * @param 2: right del simbolo
     */
    
    public boolean comprobarCompatibilidadTipos(Object op1, Object op2, int l1, int r1, int l2, int r2) {
        if (op1 == null || op2 == null) {
            if (op1 == null) {
                errorSemantico(l1, r1, "null", "Lo siento no se puede realizar la operacion debido a que no se encuentra definida la variable.");
            } else {
                errorSemantico(l1, r1, "null", "Lo siento no se puede realizar la operacion debido a que no se encuentra definida la variable.");
            } return false;
        } else {
            return ((Simbolo) op1).getTipo().isFatherOf(((Simbolo) op2).getTipo().getSymbol()) || ((Simbolo) op2).getTipo().isFatherOf(((Simbolo) op1).getTipo().getSymbol());
        }
    }
    
    /**
     * Funcion que realiza operaciones
     * @param op1 OOperando 1
     * @param op2 Operando 2
     * @param l1 left del simbolo
     * @param r2 right del simbolo
     * @param r1 right del simbolo
     * @param l2 left del simbolo
     * @param tipoOperacion tipo de operacion que se realizara 1. suma, 2. resta, 3. multiplicacion, 4. division, 5. mod 
     * @return Resultado de la operacion
     */
    
    public Simbolo realizarOperaciones(Object op1, Object op2, int l1, int r1, int l2, int r2, int tipoOperacion){
        if (comprobarCompatibilidadTipos(op1, op2, l1, r1, l2, r2)) {
            if (((Simbolo) op1).getTipo().isFatherOf(((Simbolo) op2).getTipo().getSymbol()))  {
                try {
                    Simbolo regresar = new Simbolo(((Simbolo) op1).getTipo(), tipoOperacion);
                    regresar.setCuarteto(manejadorCuartetos.cuartetoOperacionAritmetica(tipoOperacion, (Simbolo) op1, (Simbolo) op2, null, ((Simbolo) op1).getTipo()));
                    return regresar;
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(ManejadorSintacticoVB_PY.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    Simbolo regresar = new Simbolo(((Simbolo) op2).getTipo(), tipoOperacion);
                    regresar.setCuarteto(manejadorCuartetos.cuartetoOperacionAritmetica(tipoOperacion, (Simbolo) op1, (Simbolo) op2, null, ((Simbolo) op2).getTipo()));
                    return regresar;
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(ManejadorSintacticoVB_PY.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            errorSemantico(l1, r1, "Operacion Aritmetica", "Error: No se puede realizar la operacion aritemtica debido a que los tipos no son compatibles.");
            return null;
        } return null;
    }
 
    /**
     * Metodo que asigna una vaiable
     * @param s valor que se le asignara a la variable
     * @param l left del simbolo
     * @param r right del simbolo
     * @return 
     */
    
    public Simbolo asignacionVariables(Object s, int l, int r){
        if (s != null) {
            Simbolo aux = metodoBuscarID(((Simbolo) s).getId(), l, r);
            if (aux != null) {
                if (((Simbolo) ((Simbolo) s).getValor()).getInput() != 0) {
                    manejadorCuartetos.imprimirScanf(((Simbolo) ((Simbolo) s).getValor()).getInput(), aux.getId());
                    return aux;
                }
                //Parte de Funciones
                if ((((Simbolo) s).getValor() instanceof Simbolo && ((Simbolo) ((Simbolo) s).getValor()).getTipoFuncion() != null)) {
                    if (aux.getTipo().isFatherOf(((Simbolo) s).getTipo().getSymbol())) {
                        aux.setValor(((Simbolo) s).getValor());
                        if (((Simbolo) s).getValor() instanceof Simbolo) {
                            if (((Simbolo) ((Simbolo) s).getValor()).getTipoFuncion() != null) {
                                manejadorCuartetos.removerUltmio();
                                aux.setCuarteto(manejadorCuartetos.asignacionCuarteto(((Simbolo) ((Simbolo) s).getValor()).getCuarteto().getOperando1(), aux));
                            } else {
                                aux.setCuarteto(manejadorCuartetos.asignacionCuarteto((Simbolo)((Simbolo) s).getValor(), aux));
                            }
                        }
                        return aux;
                    } else {
                        errorSemantico(l, r, "Asignacion", "No se puede realizar la asigacion debido a que los tipos no son compatibles.");
                    }  
                }
                if (aux.getTipo().isFatherOf(((Simbolo) s).getTipo().getSymbol())) {
                    aux.setValor(((Simbolo) s).getValor());
                    if (((Simbolo) s).getValor() instanceof Simbolo) {
                        aux.setCuarteto(manejadorCuartetos.asignacionCuarteto((Simbolo)((Simbolo) s).getValor(), aux));
                    }
                    return aux;
                } else {
                    errorSemantico(l, r, "Asignacion", "No se puede realizar la asigacion debido a que los tipos no son compatibles.");
                }  
            } 
        }
        errorSintax(l, r, "Asignacion", "Error, en la asigacion de variables.");
        return null;
    }

    public Simbolo asignacionArreglos(Object s, int l, int r, Simbolo comparar){
        if (s != null && comparar != null) {
            String id = ((Simbolo) s).getId();
            Simbolo aux = metodoBuscarID(id, l, r);
            if (aux != null) {
                if (aux.getTipo().isFatherOf(comparar.getTipo().getSymbol())) {
                    if (((List<Simbolo>) aux.getValor()).size() == 
                            ((List<Simbolo>) ((Simbolo) s).getValor()).size()) {
                        return aux;
                    }
                    errorSemantico(l, r, "Arreglo", "Error, asignacion del arreglo la dimension no corresponde.");
                    return null;
                } else {
                    errorSemantico(l, r, "Asignacion", "Error, no se puede hacer la asignacion debido a que los tipos no son compatibles.");
                } 
            }
        }
        errorSemantico(l, r, "Asignacion", "Error, en la asignacion de valores al arreglo");
        return null; 
    }
    
    public Simbolo buscarArreglo(Object s, int l, int r){
        if (s != null) {
            String id = ((Simbolo) s).getId();
            Simbolo aux = metodoBuscarID(id, l, r);
            if (aux != null) {
                 if (aux.getAux() == ((List<Simbolo>) ((Simbolo) s).getValor()).size()) {
                        return aux;
                }
                errorSemantico(l, r, "Arreglo", "Error, busqueda del arreglo la dimension no corresponde.");
                return null;
            }
        } 
        errorSemantico(l, r, "Arreglo", "Error, en los operadores del arreglo");
        return null; 
    }
    
    /**
     * Metodo que asigna una vaiable
     * @param id id de la variable
     * @param asignacion valor asignacion
     * @param l left del simbolo
     * @param r right del simbolo
     */
    
    public void asignacionVariablesThis(String id, Object asignacion, int l, int r){
        if (asignacion != null) {
            Simbolo aux = tablaSimbolos.buscarPorIdAmbito(id, 0);
            if (aux != null) {
                if (aux.getTipo().isFatherOf(((Simbolo) asignacion).getTipo().getSymbol())) {
                    aux.setValor(((Simbolo) asignacion).getValor());
                    if (asignacion instanceof Simbolo) {
                        if (((Simbolo) asignacion).getInput() != 0) {
                            manejadorCuartetos.imprimirScanf(((Simbolo) asignacion).getInput(), id);
                        } else {
                            if (((Simbolo) asignacion).getCuarteto() != null) {
                                aux.setCuarteto(manejadorCuartetos.asignacionCuarteto(((Simbolo) asignacion).getCuarteto().getResultado(), aux));
                            } else {
                                aux.setCuarteto(manejadorCuartetos.asignacionCuarteto((Simbolo) asignacion, aux));
                            }
                        }
                    } else {
                        aux.setCuarteto(manejadorCuartetos.asignacionCuarteto(new Simbolo(aux.getTipo(), asignacion), aux));
                    }
                } else {
                    errorSemantico(l, r, id, "No se puede realizar la asigacion debido a que los tipos no son compatibles.");
                }  
            } 
        } 
    }
    
    /**
     * Metodo que comprueba si dos operandos pueden ser validados
     * 1. Igual
     * 2. Distinto
     * 3. Menor
     * 4. Menor Igual
     * 5. Mayor
     * 6. Mayor Igual
     * @param op1 Operando 1
     * @param op2 Operando 2
     * @param l left del simbolo
     * @param tipoOperacion tipo de la operacion condicional
     * @param r right del simbolo
     * @return Retorna el valor false o true si la comapracion de los operandos es coherente, de lo contrario null
    */
    
    public Simbolo comprobarOperacionesLogicas(Object op1, Object op2, int tipoOperacion, int l, int r){
        if (op1 != null && op2 != null) {
            switch (tipoOperacion) {
                default:
                    if ((((Simbolo) op1).getTipo().isFatherOf(((Simbolo) op2).getTipo().getSymbol()))) {
                        try {
                            Simbolo s = new Simbolo(((Simbolo) op1).getTipo(), true);
                            List<Cuarteto> lista = manejadorCuartetos.cuartetoOpLogica(tipoOperacion, (Simbolo) op1, (Simbolo) op2);
                            s.setListaCuarteto(lista);
                            return s;
                        } catch (CloneNotSupportedException ex) {
                            Logger.getLogger(ManejadorSintacticoJP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        errorSintax(l, r, "Tipo", "No se puede realizar la operacion logica debido a que uno de los operandos no es del mismo tipo.");
                        return null;
                    }
            }
        } else {
            errorSintax(l, r, "Condicional", "No se puede realizar la operacion logica debido, errores en el analisis de la condicional.");
        }
        return null;
    }
    
    /**
     * Funcion que comprueba la validacion de tipos de una comprobacion condicional
     * @param op1 Operando 1
     * @param op2 Operando 2
     * @param l left del simbolo
     * @param tipoOperacion tipo de la operacion condicional
     * @param r right del simbolo
     * @return Retorna el valor false o true si la comapracion de los operandos es coherente, de lo contrario null
     */

    public Simbolo comprobacionCondicional(Object op1, Object op2, int tipoOperacion, int l, int r) {
        if (op1 != null && op2 != null) {
            switch (tipoOperacion) {
                case 1:
                    manejadorCuartetos.condicionalAND();
                    break;
                case 2:
                    manejadorCuartetos.condicionalOR();
                    break;
                default:
                    manejadorCuartetos.cambioEtiquetasNOT();
                    break;
            }
            return new Simbolo(((Simbolo) op1).getTipo(), true);
        } else {
            errorSintax(l, r, "Condicional", "No se puede realizar la operacion logica debido, a errores en el analisis.");
        } return null;
    }
    
    
    /**
     * Funcion que realiza la concatenacion para poder imprimir en pantalla
     * @param op1 Operando 1
     * @param op2 Operando 2
     * @return Retorna el valor concatenado
     */

    public String concatencacion(Object op1, Object op2){
        String regresar = "";
        if (op1 != null) {
            regresar += "<<" + op1;
        }
        if (op2 != null) {
            regresar += "<<" + op2;
        }
        return regresar;
    }
    
    /**
     * Metodo agregar un conjunto de Simbolos
     * @param estructura Objeto de la estructuras
     * @param lista Objeto de la lista al cual hay que agregar
     * @return Retorna la lista junto a los simbolos
     */
    
    public List<Simbolo> retornarEstructuras(Object estructura, Object lista){
        List<Simbolo> listaSimbolos = new ArrayList<>();
        if(lista != null) {
            if(lista instanceof Simbolo) {
                listaSimbolos.add((Simbolo) lista);
            } else {
                listaSimbolos = (List<Simbolo>) lista;
            }
        }
        if(estructura != null) {
            if(estructura instanceof Simbolo) {
                listaSimbolos.add((Simbolo) estructura);
            } else {
                listaSimbolos.addAll((List<Simbolo>) estructura);
            }
        }
        return listaSimbolos;
    }
    
    /**
    * Metodo que contruye una estructura
    * 1.Estructura WHILE
      2.Estructura DO WHILE
      3. Estructura FOR
     * @param statement Intrucciones 
     * @param condicional 
     * @param tipo
     * @return Estructura de un ciclo while o do while
    */
    //Aun falta agregar lo de los cuartetos.
    public Simbolo contruirEstructuraCiloDW(Object statement, Object condicional, int tipo){
        if (condicional == null) {
            vaciarAmbitos(statement);
            return null;
        }
        switch (tipo) {
            case 1:
                CicloWhile cicloWhile = new CicloWhile();
                cicloWhile.addStatements(statement);
                vaciarAmbitos(statement);
                Simbolo simbolo = new Simbolo(new Tipo("While", Constantes.WHILE), cicloWhile);
                 if (tablaSimbolos.agregarTablaSimbolos(simbolo)) {
                    return simbolo;
                } else {
                    errorSemantico(-1, -1, "Declaracion - WHILE", "Error: Despues de haber declarado la instruccion return, no se podra seguir declarando mas instrucciones.");
                    return null;
                }
            case 2:
                CicloDoWhile cicloDoWhile = new CicloDoWhile();
                cicloDoWhile.addStatements(statement);
                vaciarAmbitos(statement);
                Simbolo simbolo1 = new Simbolo(new Tipo("DoWhile", Constantes.DO_WHILE), cicloDoWhile);
                 if (tablaSimbolos.agregarTablaSimbolos(simbolo1)) {
                    return simbolo1;
                } else {
                    errorSemantico(-1, -1, "Declaracion - DO WHILE", "Error: Despues de haber declarado la instruccion return, no se podra seguir declarando mas instrucciones.");
                    return null;
                }
            case 3:
                CicloFor cicloFor = new CicloFor();
                cicloFor.addStatements(statement);
                vaciarAmbitos(statement);
                Simbolo simbolo2 = new Simbolo(new Tipo("For", Constantes.FOR), cicloFor);
                 if (tablaSimbolos.agregarTablaSimbolos(simbolo2)) {
                    return simbolo2;
                } else {
                    errorSemantico(-1, -1, "Declaracion - FOR", "Error: Despues de haber declarado la instruccion return, no se podra seguir declarando mas instrucciones.");
                    return null;
                }
            case 4:
                Metodo metodo = new Metodo();
                metodo.addStatements(statement);
                vaciarAmbitos(statement);
                Simbolo simbolo3 = new Simbolo(new Tipo("Metodo", Constantes.METODO), metodo);
                tablaSimbolos.agregarTablaSimbolos(simbolo3);
                return simbolo3;
        } return null;
    }
    
    /**
     * @param valor valor que se le asignara a la variable
     * @param id
     * @param l left del simbolo
     * @param r right del simbolo
     * @param tipo tipo de la variable
     * @return el simbolo comprobado
     */
    
    public Simbolo comprobarAsignacionFor(Object valor, String id, int l, int r, Tipo tipo){
        if (tablaSimbolos.buscarPorId(id) == null || tipo == null) {
            if (valor != null) {
                if (tipo != null) {
                    if (tipo.isFatherOf(((Simbolo) valor).getTipo().getSymbol())) {
                        Simbolo s = new Simbolo(tipo, ((Simbolo) valor).getValor(), id);
                        manejadorCuartetos.declararVariable((Simbolo) valor, s);
                        if (tablaSimbolos.agregarTablaSimbolos(s)) {
                            return s;
                        } else {
                            errorSemantico(-1, -1, "Declaracion", "Error: Despues de haber declarado la instruccion return, no se podra seguir declarando mas instrucciones.");
                            return null;
                        }
                    } else {
                        errorSemantico(l, r, "Asignacion", "Error en la asignacion la compatibilidad de tipos es diferente. ID: > " + id);
                    }
                } else {
                    Simbolo s = new Simbolo(((Simbolo) valor).getTipo(), ((Simbolo) valor).getValor(), id);
                    manejadorCuartetos.asignacionCuarteto((Simbolo) valor, s);
                    return s;
                }
            }
        } else {
            errorSintax(l, r, id, "La variable ya se encuentra definida en la estructura.");
            return null;
        } return null;
    }
    
    /**
     * Funcion que comprueba si existe el id del loop
     * @param idComprobar comprueba que el id definido sea igual al que esta definido
     * @param id id definido en loop
     * @param l left del simbolo
     * @param r right del simbolo
     * @return retorna verdadero si fue encontrado
     */
    
    public boolean comprobarIdFor(Object idComprobar, String id, int l , int r){
        if (idComprobar != null) {
            if ((((Simbolo) idComprobar).getId()).equals(id) ) {
                return true;
            } else {
                if (tablaSimbolos.buscarPorId(id) != null) {
                    return true;
                } else {
                    errorSintax(l, r, id, "La variable <" + id + "> no se encuentra definida.");
                    return false;
                }
            }
        } else {
            return false;
        }
    }
    
    /**
     * Metodo que retira todas las intrucciones de la tabla de simbolos
     * @param simbolos instrucciones
     */

    public void vaciarAmbitos(Object simbolos) {
        if (simbolos != null) {
            if (simbolos instanceof Simbolo) {
                List<Simbolo> temp = new ArrayList<>();
                temp.add((Simbolo) simbolos);
                tablaSimbolos.removerSimbolos(temp);
            } else {
                tablaSimbolos.removerSimbolos((List<Simbolo>) simbolos);
            }
            
        }
    }
    
    public boolean comprobarReturn(Object e){
        if (e != null) {
            if (e instanceof Simbolo) {
                return (((Simbolo) e).getTipo().equals(Constantes.RETURN));
            } else {
                for (Simbolo simbolo : (List<Simbolo>) e) {
                    if (simbolo.getTipo().equals(Constantes.RETURN_VAR)) {
                        return true;
                    }
                }
            }
        } return false;
    }
    
    /**
     * Funcion que construye una estructura If, Else, ElseIf elimando de la tabla de simbolos los elementos que estan detro de cada uno.
     * @param valor
     * @param condicional Instrucciones dentro de la condicional
     * @param tipo Tipo de comprobacion: 1. If, 2. ElseIf, 3. Else
     * @return retorna la estuctura condicional
     */
    
    public Simbolo crearEstructuraCondicional(Object valor, Object condicional, int tipo) {
        if (condicional != null) {
            Condicional condicionalIFE = null;
            switch (tipo) {
                case 1:
                    condicionalIFE = new Condicional(new Tipo("If", Constantes.IF), condicional, comprobarReturn(valor));
                    break;
                case 2:
                    condicionalIFE = new Condicional(new Tipo("ElseIf", Constantes.ELSE_IF), condicional, comprobarReturn(valor));
                    break;
                case 3:
                    condicionalIFE = new Condicional(new Tipo("Else", Constantes.ELSE), condicional, comprobarReturn(valor));
                    break;
            }
            vaciarAmbitos(valor);
            if (condicionalIFE != null) {
                condicionalIFE.addStatements(valor);
                condicionalIFE.setCondicional(condicional);
                manejadorCuartetos.etiquetaGoto();
                manejadorCuartetos.escribirEtiquetasEscape();
                manejadorCuartetos.escribirEtiqueta();
                return new Simbolo(condicionalIFE.getTipoCondicional(), condicionalIFE);
            }    
        }   
        vaciarAmbitos(valor);
        return null;
    }
    
    /**
     * Funcion que construye una condicional Validando cada una de las instrucciones
     * @param simboloIf Estructura condicional If
     * @param listaElseIf  Estructuras condicional Else If
     * @param simboloElse  Estructura condicional Else 
     * @return retorna la estructura
     */
    
    public Simbolo construirCondicionalIf(Object simboloIf, Object listaElseIf, Object simboloElse){
        if (simboloIf != null) {
            boolean retornar = false;
            boolean retornarAlmenosUnaVez = false;
            CondicionalIf condicionalIf = new CondicionalIf((Condicional)((Simbolo) simboloIf).getValor());
            tablaSimbolos.removerPorReferencia(((Simbolo) simboloIf));
            if (condicionalIf.getCondicionalIf().isRetornar()) {
                retornar = true;
                retornarAlmenosUnaVez = true;
            }
            if (listaElseIf != null) {
                if (listaElseIf instanceof Simbolo) {
                    Condicional c = (Condicional)((Simbolo)listaElseIf).getValor();
                    if (c.isRetornar() && !retornarAlmenosUnaVez) {
                        retornarAlmenosUnaVez = true;
                        retornar = true;
                    } else {
                        if (!c.isRetornar()) {
                            retornar = false;
                        }
                    }
                    condicionalIf.addCondicionalesElseIf(c);
                    tablaSimbolos.removerPorReferencia(((Simbolo) listaElseIf));
                } else {
                    for (Simbolo simbolo : (List<Simbolo>) listaElseIf) {
                        Condicional c = (Condicional) simbolo.getValor();
                        if (c.isRetornar() && !retornarAlmenosUnaVez) {
                            retornarAlmenosUnaVez = true;
                            retornar = true;
                        } else {
                            if (!c.isRetornar()) {
                                retornar = false;
                            }
                        }
                        condicionalIf.addCondicionalesElseIf(c);
                        tablaSimbolos.removerPorReferencia(simbolo);
                    }
                } 
            }
            if (simboloElse != null) {
                Condicional c = (Condicional)((Simbolo) simboloElse).getValor();
                if (c.isRetornar() && !retornarAlmenosUnaVez) {
                    retornarAlmenosUnaVez = true;
                    retornar = true;
                } else {
                    if (!c.isRetornar()) {
                        retornar = false;
                    }
                }
                condicionalIf.setCondicionalElse(c);
                 tablaSimbolos.removerPorReferencia(((Simbolo) simboloElse));
            } else {
                manejadorCuartetos.escribirEtiqueta();
            }
            condicionalIf.setAlmenosUnRetornar(retornarAlmenosUnaVez);
            condicionalIf.setRetornarEnTodasCondiciones(retornar); //Variable que me dice si todas las declaracaciones de if ifelse else tienen un return
            Simbolo simbolo = new Simbolo(((Simbolo) simboloIf).getTipo(), condicionalIf); 
            manejadorCuartetos.etqFinTurno();
            if (tablaSimbolos.agregarTablaSimbolos(simbolo)) {
                return simbolo;
            } else {
                errorSemantico(-1, -1, "Declaracion - IF", "Error: Despues de haber declarado la instruccion return, no se podra seguir declarando mas instrucciones.");
                return null;
            }
        } return null;
    }
    
    public Simbolo crearEstructuraCases(Object caso, Object statement, int l, int r, boolean elseC){
        if (caso != null || elseC) {
            Case c;
            if (elseC) {
                c = new Case(comprobarReturn(statement));
            } else {
                c = new Case((Simbolo) caso, comprobarReturn(statement));
            }
            if (statement != null) {
                c.addStatements(statement);
                vaciarAmbitos(statement);
                return new Simbolo(new Tipo("Case", Constantes.CASE), c);
            } 
            vaciarAmbitos(statement);
            return null;
        } else {
            vaciarAmbitos(statement);
            errorSintax(l, r, "Case", "Error en la comprobacion del case");
            return null;
        }
    }
    
    public Simbolo crearCase(Object listaCasos, Object casoDefault, Object simbolo, int l, int r){
        if (simbolo != null) {
            boolean AlMenosUnaVez = false;
            boolean retornar = false;
            Simbolo comprobacionCase = (Simbolo) simbolo;
            Simbolo casoEstuctura;
            CondicionalSwitch condicionalSwitch = new CondicionalSwitch();
            if (listaCasos != null) {
                if (listaCasos instanceof Simbolo) {
                    Tipo t = ((Case)((Simbolo) listaCasos).getValor()).getCaso().getTipo();
                    if (comprobacionCase.getTipo().isFatherOf(t.getSymbol())) {
                        Case c = (Case)((Simbolo) listaCasos).getValor();
                        if (c.isRetornar()) {
                            AlMenosUnaVez = true;
                            retornar = true;
                        }
                        
                        condicionalSwitch.agregarCaso(c);
                    } else { 
                        errorSemantico(l, r, "Case", "Error comprobacion de tipo del case, no son commpatibles.");
                        return null;
                    }
                } else {
                    for (Simbolo sim : (List<Simbolo>) listaCasos) {
                        if (comprobacionCase.getTipo().isFatherOf(((Case) sim.getValor()).getCaso().getTipo().getSymbol())) {
                            Case c = (Case)(sim).getValor();
                            if (c.isRetornar() && !AlMenosUnaVez) {
                                AlMenosUnaVez = true;
                                retornar = true;
                            } else {
                                if (!c.isRetornar()) {
                                    retornar = false;
                                }
                            }
                            condicionalSwitch.agregarCaso(c);
                        } else { 
                            errorSemantico(l, r, "Case", "Error comprobacion de tipo del case, no son commpatibles.");
                            return null;
                        }
                    }
                }
            }
            if (casoDefault != null) {
                Case c = (Case)((Simbolo) casoDefault).getValor();
                if (c.isRetornar() && !AlMenosUnaVez) {
                    AlMenosUnaVez = true;
                    retornar = true;
                } else {
                    if (!c.isRetornar()) {
                        retornar = false;
                    }
                }
                condicionalSwitch.agregarCaso(c);
            }
            condicionalSwitch.setRetornar(retornar);
            condicionalSwitch.setRetornarAlMenosUnaVez(AlMenosUnaVez);
            casoEstuctura = new Simbolo(new Tipo("Switch", Constantes.SWITCH), condicionalSwitch);
            if (tablaSimbolos.agregarTablaSimbolos(casoEstuctura)) {
                return casoEstuctura;
            } else {
                errorSemantico(l, r, "Declaracion - SWITCH", "Error: Despues de haber declarado la instruccion return, no se podra seguir declarando mas instrucciones.");
                return null;
            }
            
        } return null;
    }
    
    
    public Simbolo funcion(Object parametros, Tipo tipo, Object estructuras, Object regresar, String id, int l, int r){
        if (parametros != null) {
            vaciarAmbitos(parametros);
            Funcion f = new Funcion();
            f.setParametros(parametros);
            f.addStatements(estructuras);
            vaciarAmbitos(estructuras);
            if (!f.comprobarSiTieneReturn(tipo, l, r, true)) {
                vaciarAmbitos(estructuras);
                errorSemantico(l, r, "Return", "Error el funcion conflictos con la instruccion RETURN.");
                return null;
            }
            Simbolo function = new Simbolo(new Tipo("Funcion", Constantes.FUNCION), f, id);
            function.setTipoFuncion(tipo);
            if (tablaSimbolos.buscarPorId(id) == null) {
                boolean bandera = tablaSimbolos.agregarTablaSimbolos(function);
                //tablaSimbolos.print();
                return function;
            } else {
                vaciarAmbitos(estructuras);
                errorSintax(l, r, "Funcion < " + id + " > ", "Error existe un metodo, funcion o variable declarada con el mismo nombre.");
                return null;
            }
        } else {
            errorSemantico(l, r, "Parametros", "Error en los parametros algunos de ellos se encuentran definida");
        } return null;
    }
    
    public Simbolo metodos(Object parametros, Object estructuras, String id, int l, int r){
        if (parametros != null) {
            vaciarAmbitos(parametros);
            Metodo m = new Metodo();
            m.setParametros(parametros);
            m.addStatements(estructuras);
            vaciarAmbitos(estructuras);
            if (m.comporbarSiTieneReturnMetodo()) {
                errorSemantico(l, r, "Return", "Error el metodo no debe tener la instruccion RETURN.");
                return null;
            }
            Simbolo metodo = new Simbolo(new Tipo("Metodo", Constantes.METODO), m, id);
            if (tablaSimbolos.buscarPorId(id) == null) {
                boolean bandera = tablaSimbolos.agregarTablaSimbolos(metodo);
                //tablaSimbolos.print();
                return metodo; 
            } else {
                errorSintax(l, r, "Metodo <" + id + "> ", "Error existe un metodo o funcion declarada con el mismo nombre.");
                return null;
            }
        } else {
            vaciarAmbitos(estructuras);
            errorSemantico(l, r, "Parametros", "Error en los parametros algunos de ellos se encuentran definida");
        } return null;
    }

    public List<Simbolo> simbolosReturn(Object a, Object e, int l, int r){
        List<Simbolo> listaSimbolos = new ArrayList<>();
        if(a != null) {
            if(a instanceof Simbolo) {
                listaSimbolos.add((Simbolo) a);
            } else {
                listaSimbolos = (List<Simbolo>) a;
            }
        }
        if(e != null) {
            if(e instanceof Simbolo) {
                listaSimbolos.add((Simbolo) e);
            } else {
                listaSimbolos.addAll((List<Simbolo>) e);
            }
        }
        return listaSimbolos;
    }
    
    public Simbolo claseJava(String id, Object variablesLocales, Simbolo constructor, Object statment, int l, int r){
        List<Simbolo> simbolos = new ArrayList<>();
        if (statment != null) {
            if (statment instanceof Simbolo) {
                simbolos.add((Simbolo) statment);
            } else {
                simbolos = (List<Simbolo>) statment;
            }
        }
        if (constructor != null) {
            if (constructor.getId().equals(id)) {
                simbolos.add(constructor);
            } else {
                errorSemantico(l, r, "Construtor", "Error al definir el construcor: ID < " + constructor.getId()+ " >, el nombre con el que fue definido no es igual al de la clase.");
            }
        }
        vaciarAmbitos(variablesLocales);
        vaciarAmbitos(constructor);
        vaciarAmbitos(statment);
        Simbolo claseJava = new Simbolo(new Tipo("ClaseJava", Constantes.CLASE_PJ), simbolos, id);
        if (tablaSimbolos.buscarPorId(id) == null) {
            tablaSimbolos.agregarTablaSimbolos(claseJava);
            //tablaSimbolos.print();
            return claseJava;
        } else {
            errorSintax(l, r, "Clase <" + id + "> ", "Error existe una clase declarada con el mismo nombre.");
            return null;
        }
    }
    
    
    public List<Simbolo> agregarInstancias(List<Simbolo> idInstancias, String clase, int l, int r){
        List<Simbolo> lista = new ArrayList<>();
        ManejadorInstancias mi = ManejadorInstancias.getInstance();
        if (tablaSimbolos.buscarPorId("$*") == null) {
            if (tablaSimbolos.buscarPorId("&" + clase) == null) {
                errorSintax(l, r, "Libreria", "Error, ninguna libreria tiene la clase con el id: " + clase + ".");
                return null;
            }
        }
        for (Simbolo idInstancia : idInstancias) {
            Simbolo simbolo = tablaSimbolos.buscarPorId(idInstancia.getId());
            if (simbolo == null) {
                if (mi.contructores(clase, l, r, idInstancia.getValor()) != null) {
                    simbolo = new Simbolo(new Tipo("Instancia", Constantes.INSTANCIAS), clase, idInstancia.getId());
                    tablaSimbolos.agregarTablaSimbolos(simbolo);
                    List<Simbolo> params = mi.param(idInstancia.getValor());
                    simbolo.setCuarteto(mi.escribirFuncion("JAVA_" + clase + "_" + clase, params));
                    manejadorCuartetos.removerUltmio();
                    simbolo.setCuarteto(manejadorCuartetos.asignacionCuarteto(simbolo.getCuarteto().getOperando1(), simbolo));
                    lista.add(simbolo);
                } 
            } else {
                errorSintax(l, r, "Delcaracion", "Error, no se puede delcarar la instancia con el ID: " + idInstancia + " ya existe una varibale definida con este id.");
            }
        } return lista;
    }
    
    public List<Integer> listaNumeros(Object e, Object a){
        List<Integer> lista = new ArrayList<>();
        if (a != null) {
            if (a instanceof Integer) {
                lista.add((int) a);
            } else {
                lista = (List<Integer>) a;
            }
        } 
        if (e != null) {
            if( e instanceof Integer) {
                lista.add((int) e);
            } else {
                lista.addAll((List<Integer>) e);
            }
        }
        return lista;
    }
    
    public List<Simbolo> comprobarPrint(List<Integer> lista, Object listaIds, int l, int r){
        if (lista == null || listaIds == null) {
            return null;
        }
        List<Simbolo> simbolos = new ArrayList<>();
        if (listaIds instanceof Simbolo) {
            simbolos.add((Simbolo) listaIds);
        } else { 
            simbolos = ((List<Simbolo>) listaIds);
        }
        if (lista.size() == simbolos.size()) {
            while (!lista.isEmpty()) {
                manejadorCuartetos.imprimirPrintf(lista.remove(0), simbolos.remove(0).getId());
            }
        } else {
            errorSemantico(l, r, "PRINTF", "Error, la cantidad de valores asignar, no hacen match con la cnatidad de caracteres especificados.");
        } return simbolos;
    }
}