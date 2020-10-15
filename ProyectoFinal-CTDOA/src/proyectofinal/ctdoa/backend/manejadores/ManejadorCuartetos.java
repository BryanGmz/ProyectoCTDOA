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
import proyectofinal.ctdoa.backend.objetos.*;

/**
 *
 * @author bryangmz
 */
public class ManejadorCuartetos {
    
    private String claseActual;
    private boolean banderaAND;
    private boolean banderaOR;
    private static int contadorVariables;
    private static int contadoreEtiquetas;
    private static int contadoreEtiquetasFin;
    private static ManejadorCuartetos manejadorCuartetos;
    private static List<Cuarteto> cuartetos;
    private static List<String> librerias;
    public static String MAS = "+";
    public static String MENOS = "-";
    public static String DIV = "/";
    public static String POR = "*";
    public static String MOD = "%";
    public static String IGUAL_IGUAL = "==";
    public static String DIFERENTE = "!=";
    public static String MENOR = "<";
    public static String MENOR_IGUAL = "<=";
    public static String MAYOR = ">";
    public static String MAYOR_IGUAL = ">=";
    public static String ETIQUETA = "etq";
    public static String GOTO = "goto";
    public static String DECLARACION = "$Declaracion";
    public static String IMPRIMIR_CONSOLA = "print";
    public static String RETURN = "return";
    public static String PROCEDIMIENTO = "Procedimiento";
    public static String COMENTARIO = "$Comentario";
    public static String PARAMETROS = "param";
    public static String CALL = "call";
    public static String SCANF  = "scanf";
    public static String LIBRERIA  = "libreria";
    public static String GETCH  = "getch";
    public static String CLRSCR  = "clrscr";
    public static String ARREGLO  = "ARREGLO";
    private static List<String> listaEtiquetas;
    private static List<String> listaEtiquetasFin;
    private static List<String> listaEtiquetasEscape;
    private static List<List<String>> listaEtiquetasEscapeFin;
    private static List<String> listaEtiquetasInicios;
    private Simbolo etiquetaSwitch;
    private String etqAux;
    
    private ManejadorCuartetos() {}
    
    public static ManejadorCuartetos getInstancia(){
        if (manejadorCuartetos == null) {
            contadorVariables = 0;
            contadoreEtiquetas = 0;
            contadoreEtiquetasFin = 0;
            cuartetos = new ArrayList<>();
            librerias = new ArrayList<>();
            listaEtiquetasFin = new ArrayList<>();
            listaEtiquetasInicios = new ArrayList<>();
            listaEtiquetasEscape = new ArrayList<>();
            listaEtiquetasEscapeFin = new ArrayList<>();
            listaEtiquetas = new ArrayList<>();
            manejadorCuartetos = new ManejadorCuartetos();
        } return manejadorCuartetos;
    }

    public void cambioEtiquetasNOT(){
        String etiqIncial = listaEtiquetas.get(0);
        listaEtiquetas.set(0, listaEtiquetas.get(1));
        listaEtiquetas.set(1, etiqIncial);
    }
    
    public void addClaseActual(String id){
        claseActual = id;
    }
    
    public Simbolo getEtiquetaSwitch() {
        return etiquetaSwitch;
    }

    public void setEtiquetaSwitch(Simbolo etiquetaSwitch) {
        this.etiquetaSwitch = etiquetaSwitch;
    }
    
    public void imprimirParams(List<Simbolo> simbolos){
        Cuarteto c;
        for (Simbolo simbolo : simbolos) {
            c = new Cuarteto(PARAMETROS, simbolo, null, null);
            cuartetos.add(c);
        }
    }
    
    public void removerUltmio(){
        if (!cuartetos.isEmpty()) {
            cuartetos.remove(cuartetos.size() - 1);
        }
    }
    
    public void imprimirScanf(int caso, String e){
        Cuarteto cuarteto;
        switch (caso) {
            case 1:
                cuarteto = new Cuarteto(SCANF, new Simbolo(null, null, ("\"%d\", &" + e)), null, null);
                break;
            case 2:
                cuarteto = new Cuarteto(SCANF, new Simbolo(null, null, ("\"%c\", &" + e)), null, null);
                break;
            case 3:
                cuarteto = new Cuarteto(SCANF, new Simbolo(null, null, ("\"%f\", &" + e)), null, null);
                break;
            default:
                throw new AssertionError();
        }
        if (cuarteto != null) {
            cuartetos.add(cuarteto);
        }
    }
    
    public void imprimirPrintf(int caso, String e){
        Cuarteto cuarteto;
        switch (caso) {
            case 1:
                cuarteto = new Cuarteto(SCANF, new Simbolo(null, null, ("\"%d\", " + e)), null, null);
                break;
            case 2:
                cuarteto = new Cuarteto(SCANF, new Simbolo(null, null, ("\"%c\", " + e)), null, null);
                break;
            case 3:
                cuarteto = new Cuarteto(SCANF, new Simbolo(null, null, ("\"%f\", " + e)), null, null);
                break;
            default:
                throw new AssertionError();
        }
        if (cuarteto != null) {
            cuartetos.add(cuarteto);
        }
    }
    
    public Cuarteto imprimirProcedimiento(String id, Object parametros){
        List<Simbolo> param = new ArrayList<>();
        String salida = "call " + id; 
        if (parametros != null) {
            if (parametros instanceof Simbolo) {
                param.add((Simbolo) parametros);
            } else {
                param.addAll(((List<Simbolo>) parametros));
            }
        } 
        for (Simbolo simbolo : param) {
            switch (simbolo.getTipo().getSymbol()) {
                case Constantes.CHAR:
                    salida += "_char";
                    break;
                case Constantes.INT:
                    salida += "_int";
                    break;
                case Constantes.FLOAT:
                    salida += "_float";
                    break;
                default:
                    break;
            }
        }
        salida += "() " + param.size();
        Cuarteto c = new Cuarteto(CALL, new Simbolo(null, null, salida), null, null);
        cuartetos.add(c);
        return c;
    }
    
    public void etqFinTurno(){
        cuartetos.add(new Cuarteto(ETIQUETA, null, null, new Simbolo(null, null, "etqf" + contadoreEtiquetasFin)));
        contadoreEtiquetasFin++;
    }
    
    public void nuevoAnalisisBoolean() {
        banderaAND = false;
        banderaOR = false;
        List<String> nuevo = new ArrayList<>();
        nuevo.addAll(listaEtiquetasEscape);
        listaEtiquetasEscape.clear();
        if (!nuevo.isEmpty()) {
            listaEtiquetasEscapeFin.add(nuevo);
        }
    }
    
    public void nuevoAnalisis(){
        librerias.clear();
        cuartetos.clear();
        listaEtiquetas.clear();
        listaEtiquetasEscapeFin.clear();
        listaEtiquetasEscape.clear();
        listaEtiquetasFin.clear();
        contadorVariables = 0;
        contadoreEtiquetas = 0;
        contadoreEtiquetasFin = 0;
        
    }
    
    public void addComentario(String comentario){
        cuartetos.add(new Cuarteto(COMENTARIO, new Simbolo(null, null, comentario), null, null));
    }
    
    public void addCuartetoConstructorJava(String id, String clase, Object parametros, Tipo tipo){
        List<Simbolo> param = new ArrayList<>();
        String salida = "void ";
        salida += clase;
        salida += "_" + id;
        if (parametros != null) {
            if (parametros instanceof Simbolo) {
                param.add((Simbolo) parametros);
            } else {
                param.addAll(((List<Simbolo>) parametros));
            }
        } 
        for (Simbolo simbolo : param) {
            switch (simbolo.getTipo().getSymbol()) {
                case Constantes.CHAR:
                    salida += "_char";
                    break;
                case Constantes.INT:
                    salida += "_int";
                    break;
                case Constantes.FLOAT:
                    salida += "_float";
                    break;
                default:
                    break;
            }
        }
        salida += "(){";
        cuartetos.add(new Cuarteto(PROCEDIMIENTO, new Simbolo(null, null, salida), null, null));
    }
    
    public void addCuartetoProcedimientoJava(String id, String clase, Object parametros, Tipo tipo){
        List<Simbolo> param = new ArrayList<>();
        String salida = "void ";
        salida += clase;
        salida += "_"+ claseActual + "_" + id;
        if (parametros != null) {
            if (parametros instanceof Simbolo) {
                param.add((Simbolo) parametros);
            } else {
                param.addAll(((List<Simbolo>) parametros));
            }
        } 
        for (Simbolo simbolo : param) {
            switch (simbolo.getTipo().getSymbol()) {
                case Constantes.CHAR:
                    salida += "_char";
                    break;
                case Constantes.INT:
                    salida += "_int";
                    break;
                case Constantes.FLOAT:
                    salida += "_float";
                    break;
                default:
                    break;
            }
        }
        salida += "(){";
        cuartetos.add(new Cuarteto(PROCEDIMIENTO, new Simbolo(null, null, salida), null, null));
    }
    
    public void addCuartetoProcedimiento(int caso, String id, String clase, Object parametros, Tipo tipo){
        List<Simbolo> param = new ArrayList<>();
        String salida = "";
        switch (caso) {
            case 1://Funcion
                switch (tipo.getSymbol()) {
                   case Constantes.CHAR:
                        salida += "char ";
                        break;
                    case Constantes.INT:
                        salida += "int ";
                        break;
                    case Constantes.FLOAT:
                        salida += "float ";
                        break;
                }
                salida += clase + "_" + id;
                break;
            case 2:
                if ("".equals(clase)) {
                    salida += "void " + id;
                } else {
                    salida += "void " + clase + "_" + id;
                }
                break;
        }       
        if (parametros != null) {
            if (parametros instanceof Simbolo) {
                param.add((Simbolo) parametros);
            } else {
                param.addAll(((List<Simbolo>) parametros));
            }
        } 
        for (Simbolo simbolo : param) {
            switch (simbolo.getTipo().getSymbol()) {
                case Constantes.CHAR:
                    salida += "_char";
                    break;
                case Constantes.INT:
                    salida += "_int";
                    break;
                case Constantes.FLOAT:
                    salida += "_float";
                    break;
                default:
                    break;
            }
        }
        salida += "(){";
        cuartetos.add(new Cuarteto(PROCEDIMIENTO, new Simbolo(null, null, salida), null, null));
    }
    
    public void addLibreria(String a){
        librerias.add(a);
    }
    
    public void finClase(){
        claseActual = "";
    }
    
    public void finProcedimiento(boolean python){
        String salida = "";
        salida += "}\n";
        cuartetos.add(new Cuarteto("$FinProcedimiento", new Simbolo(null, null, salida), null, null));
    }
    
    public Cuarteto auxCuartetos(Simbolo op1, Simbolo op2, String tipo, Simbolo destino, Cuarteto cuarteto) throws CloneNotSupportedException{
        if (op1.getCuarteto() == null && op2.getCuarteto() == null) {
            cuarteto = new Cuarteto(tipo, op1.clone(), op2.clone(), destino);
        } else if (op1.getCuarteto() != null && op2.getCuarteto() != null) {
            cuarteto = new Cuarteto(tipo, op1.getCuarteto().getResultado().clone(), op2.getCuarteto().getResultado().clone(), destino);
        } else if (op1.getCuarteto() == null){
            cuarteto = new Cuarteto(tipo, op1.clone(), op2.getCuarteto().getResultado().clone(), destino);                
        } else {
            cuarteto = new Cuarteto(tipo, op1.getCuarteto().getResultado().clone(), op2.clone(), destino);
        }
        cuartetos.add(cuarteto);
        return cuarteto;
    }

    public List<Cuarteto> cuartetoOpLogica(int caso, Simbolo op1, Simbolo op2) throws CloneNotSupportedException{
        Simbolo destino = new Simbolo(null, null, "goto etq" + contadoreEtiquetas);
        Cuarteto cuarteto = null;
        List<Cuarteto> lc = new ArrayList<>();
        switch (caso) {
            case 1:
                cuarteto = auxCuartetos(op1, op2, IGUAL_IGUAL, destino, cuarteto);
                break;
            case 2:
                cuarteto = auxCuartetos(op1, op2, DIFERENTE, destino, cuarteto);
                break;
            case 3:
                cuarteto = auxCuartetos(op1, op2, MENOR, destino, cuarteto);
                break;
            case 4:
                cuarteto = auxCuartetos(op1, op2, MENOR_IGUAL, destino, cuarteto);
                break;
            case 5:
                cuarteto = auxCuartetos(op1, op2, MAYOR, destino, cuarteto);
                break;
            case 6:
                cuarteto = auxCuartetos(op1, op2, MAYOR_IGUAL, destino, cuarteto);
                break;
            default:
                throw new AssertionError();
        }
        listaEtiquetas.add("etq" + contadoreEtiquetas);
        contadoreEtiquetas++;
        lc.add(cuarteto);
        return lc;
    }
    
    public void gotoEscribir(){
        Cuarteto c = new Cuarteto(GOTO, null, null, new Simbolo(null, null, "goto etq" + contadoreEtiquetas));
        listaEtiquetas.add("etq" + contadoreEtiquetas);
        contadoreEtiquetas++;
        cuartetos.add(c);
    }
    
    public void gotoEscribirEtiqueta(String id){
        Cuarteto gotoCuarteto = new Cuarteto(GOTO, null, null, new Simbolo(null, null, "goto " + id));
        cuartetos.add(gotoCuarteto);
    }
    
    public Cuarteto etiquetaGoto(){
        Cuarteto c = new Cuarteto(GOTO, null, null, new Simbolo(null, null, "goto etqf" + contadoreEtiquetasFin));
        cuartetos.add(c);
        boolean bandera =  false;
        for (String le : listaEtiquetasFin) {
            if (le.equalsIgnoreCase("etqf" + contadoreEtiquetasFin)) {
                bandera = true;
                break;
            }
        }
        if (!bandera) {
            listaEtiquetasFin.add("etqf" + contadoreEtiquetasFin);
        }
        return c;
    }
    
    public boolean ultimoAgregadorEtiqueta(){
        if (cuartetos.get(cuartetos.size() - 1) != null && cuartetos.get(cuartetos.size() - 1).getOperador() != null) {
            return (cuartetos.get(cuartetos.size() - 1).getOperador().equals(ETIQUETA));
        } return false;
        
    }
    
    public void condicionalAND(){
        if (listaEtiquetas.size() > 1) {
            listaEtiquetasEscape.add(listaEtiquetas.remove(1));
        }
        banderaAND = true;
    }
    
    public void escribirEtiquetasEscape(){
        while (!listaEtiquetasEscape.isEmpty()) {
            Cuarteto cuarteto = new Cuarteto(ETIQUETA, null, null, new Simbolo(null, null, listaEtiquetasEscape.remove(0)));
            cuartetos.add(cuarteto);
        }
    }
    
    public void and(){
        if (banderaOR) {
            if (!listaEtiquetas.isEmpty()) {
                listaEtiquetasInicios.add(listaEtiquetas.remove(0));                
            }            
        }
        banderaOR = false;
        escribirEtiquetasInicio(true);
    }
    
    public void or(){
        if (banderaAND) {
            if (!listaEtiquetas.isEmpty()) {
                listaEtiquetasInicios.add(listaEtiquetas.remove(0));
            }            
        }
        banderaAND = false;
        escribirEtiquetasEscape();
    }
        
    
    public void escribirEtiquetasInicio(boolean and){
        while (!listaEtiquetasInicios.isEmpty()) {
            if (listaEtiquetasInicios.size() == 1) {
                listaEtiquetas.add(0, listaEtiquetasInicios.remove(0));
                break;
            }
            Cuarteto cuarteto = new Cuarteto(ETIQUETA, null, null, new Simbolo(null, null, listaEtiquetasInicios.remove(0)));
            cuartetos.add(cuarteto);
        }
    }
    
    public void condicionalOR(){
        if (listaEtiquetas.size() > 1) {
            listaEtiquetasInicios.add(listaEtiquetas.remove(0));
        }
        banderaOR = true;
    }
    
    public Cuarteto escribirEtiqueta(){
        if (!listaEtiquetas.isEmpty()) {
            Cuarteto cuarteto = new Cuarteto(ETIQUETA, null, null, new Simbolo(null, null, listaEtiquetas.remove(0)));
            cuartetos.add(cuarteto);
            return cuarteto;
        } return null;
    }

    public Cuarteto escribirEtiquetaFin(){
        if (!listaEtiquetas.isEmpty()) {
            Cuarteto cuarteto = new Cuarteto(ETIQUETA, null, null, new Simbolo(null, null, listaEtiquetas.remove(0)));
            cuartetos.add(cuarteto);
            if (!listaEtiquetas.isEmpty()) {
                listaEtiquetasEscape.addAll(listaEtiquetas);
                listaEtiquetas.clear();
            }
            return cuarteto;
        }
        return null;
    }
    
    public void escribirNuevaEtiqueta(String id){
        Cuarteto cuarteto = new Cuarteto(ETIQUETA, null, null, new Simbolo(null, null, id));
        cuartetos.add(cuarteto);
    }
    
    public Cuarteto cuartetoOperacionAritmetica(int caso, Simbolo op1, Simbolo op2, Simbolo destino, Tipo destinoTipo) throws CloneNotSupportedException{
        if (destino == null) {
            destino = new Simbolo(destinoTipo, null, "temp" + contadorVariables);
            declararVariable(null, destino);
            contadorVariables++;
        } 
        Cuarteto cuarteto = null;
        switch (caso) {
            case 1:      
                cuarteto = auxCuartetos(op1, op2, MAS, destino, cuarteto);
                break;
            case 2:
                cuarteto = auxCuartetos(op1, op2, MENOS, destino, cuarteto);
                break;
            case 3:
                cuarteto = auxCuartetos(op1, op2, POR, destino, cuarteto);
                break;
            case 4:
                cuarteto = auxCuartetos(op1, op2, DIV, destino, cuarteto);
                break;
            case 5:
                cuarteto = auxCuartetos(op1, op2, MOD, destino, cuarteto);
                break;
            default:
                throw new AssertionError();
        } return cuarteto;
    }
    
    public void imprimirResto(){
        if (!listaEtiquetasEscapeFin.isEmpty()) {
            List<String> nuevo = listaEtiquetasEscapeFin.remove(0);
            while (!nuevo.isEmpty()) {
                Cuarteto cuarteto = new Cuarteto(ETIQUETA, null, null, new Simbolo(null, null, nuevo.remove(0)));
                cuartetos.add(cuarteto);
            } nuevo.clear();
        }
    }
    
    public void condicional(Simbolo comparacion){
        String idEtiquetaSiguiente = "etq" + contadoreEtiquetas;
        contadoreEtiquetas++;
        listaEtiquetas.add(idEtiquetaSiguiente);
        Cuarteto cuarteto;
        if (comparacion.getCuarteto() != null) {
            if (etiquetaSwitch.getCuarteto() != null) {
                cuarteto =  new Cuarteto(DIFERENTE, etiquetaSwitch.getCuarteto().getResultado(), comparacion.getCuarteto().getResultado(), new Simbolo(null, null, "goto " +idEtiquetaSiguiente));
            } else {
                cuarteto =  new Cuarteto(DIFERENTE, etiquetaSwitch, comparacion.getCuarteto().getResultado(), new Simbolo(null, null, "goto " +idEtiquetaSiguiente));
            }
        } else {
            if (etiquetaSwitch.getCuarteto() != null) {
                cuarteto =  new Cuarteto(DIFERENTE, etiquetaSwitch.getCuarteto().getResultado(), comparacion, new Simbolo(null, null, "goto " +idEtiquetaSiguiente));
            } else {
                cuarteto =  new Cuarteto(DIFERENTE, etiquetaSwitch, comparacion, new Simbolo(null, null, "goto " +idEtiquetaSiguiente));
            }   
        }
        cuartetos.add(cuarteto);
    }
    
    public void imprimir(Simbolo comparacion){
        if (comparacion != null) {
            Cuarteto cuarteto;
            if (comparacion.getCuarteto() != null) {
                cuarteto =  new Cuarteto(IMPRIMIR_CONSOLA, comparacion.getCuarteto().getResultado(), null, null);
            } else {
                cuarteto =  new Cuarteto(IMPRIMIR_CONSOLA, comparacion, null, null);
            }
            cuartetos.add(cuarteto);
        }
    }
    
    public void imprimirNuevaLinea(){
        cuartetos.add(new Cuarteto(IMPRIMIR_CONSOLA, new Simbolo(null, "\"" + "\\n" + "\""), null, null));
    }
    
    public void finCadaCaso(){
        etiquetaGoto();
        escribirNuevaEtiqueta(listaEtiquetas.remove(0));
    }
    
    public String addDoWhile(){
        String etiquetaIncial = "etq" + contadoreEtiquetas;
        contadoreEtiquetas++;
        escribirNuevaEtiqueta(etiquetaIncial);
        return etiquetaIncial;
    }
    
    public String addWhile(){
        String etiquetaInicial = "etq" + contadoreEtiquetas;
        contadoreEtiquetas++;
        escribirNuevaEtiqueta(etiquetaInicial);
        return etiquetaInicial;
    }
    
    public void finWhile(String etiquetaFinal){
        gotoEscribirEtiqueta(etiquetaFinal);
        while (!listaEtiquetas.isEmpty()) {
            escribirNuevaEtiqueta(listaEtiquetas.remove(0));
        }
        escribirEtiquetasEscape();
    }
    
    public void finDoWhile(String etiquetaInicio){
        escribirEtiquetasInicio(false);
        escribirEtiqueta();
        gotoEscribirEtiqueta(etiquetaInicio);
        System.out.println("Lista " + listaEtiquetas.size());
        while (!listaEtiquetas.isEmpty()) {
            escribirNuevaEtiqueta(listaEtiquetas.remove(0));
        }
        escribirEtiquetasEscape();
    }
    
    public List<String> addForPY(Object op1, Object rango) throws CloneNotSupportedException{
        if (op1 == null || rango == null) {
            return null;
        }
        Cuarteto c;
        Simbolo step = null;
        List<String> lista = new ArrayList<>();
        String id = ((Simbolo) op1).getId();
        String etiqRegresar = "etq" + contadoreEtiquetas;
        contadoreEtiquetas++;
        String etiqRefreso = "etq" + contadoreEtiquetas;
        contadoreEtiquetas++;
        String etiqVerdadera = "etq" + contadoreEtiquetas;
        contadoreEtiquetas++;
        String etiqFalsa = "etqf" + contadoreEtiquetasFin++;
        contadoreEtiquetasFin++;
        escribirNuevaEtiqueta(etiqRegresar);
        if (rango instanceof Simbolo) {
            cuartetoCondicional(new Simbolo(null, 0), (Simbolo) rango, new Simbolo(null, null, ("goto "+ etiqVerdadera)), IGUAL_IGUAL);
        } else {
            cuartetoCondicional(((List<Simbolo>) rango).get(0), ((List<Simbolo>) rango).get(1), new Simbolo(null, null, ("goto "+ etiqVerdadera)), IGUAL_IGUAL);
            if (((List<Simbolo>) rango).size() == 3) {
                step = ((List<Simbolo>) rango).get(2);
            }
        }
        gotoEscribirEtiqueta(etiqFalsa);
        escribirNuevaEtiqueta(etiqRefreso);
        if (step == null) {
            c = cuartetoOperacionAritmetica(1, new Simbolo(((Simbolo) op1).getTipo(), 1, null), new Simbolo(((Simbolo) op1).getTipo(), null, id), null, ((Simbolo) op1).getTipo());
        } else {
            c = cuartetoOperacionAritmetica(1, step, new Simbolo(((Simbolo) op1).getTipo(), null, id), null, ((Simbolo) op1).getTipo());
        }
        asignacionCuarteto(c.getResultado(), ((Simbolo) op1));
        gotoEscribirEtiqueta(etiqRegresar);
        escribirNuevaEtiqueta(etiqVerdadera);
        lista.add(etiqFalsa);
        lista.add(etiqRefreso);
        return lista;
    }
    
    public List<String> addForJPV(Simbolo op1){
        if (op1 == null ) {
            return null;
        }
        List<String> lista = new ArrayList<>();
        String id = op1.getId();
        String etiqRegresar = "etq" + contadoreEtiquetas;
        contadoreEtiquetas++;
        String etiqRefreso = "etq" + contadoreEtiquetas;
        contadoreEtiquetas++;
        lista.add(id);
        lista.add(etiqRegresar);
        lista.add(etiqRefreso);
        etqAux = etiqRefreso;
        escribirNuevaEtiqueta(etiqRegresar);
        return lista;
    }
    
    public void addCLRSCR(){
        cuartetos.add(new Cuarteto(CLRSCR, null, null, null));
    }
    
    public void addGetch(Simbolo destino){
        if (destino != null) {
            cuartetos.add(new Cuarteto(GETCH, new Simbolo(null, null, GETCH+ "()"), null, destino));
        } else {
            cuartetos.add(new Cuarteto(GETCH, new Simbolo(null, null, GETCH+ "()"), null, null));
        }
    }
    
    public void etiqueta(){
        if (etqAux != null) {
            escribirNuevaEtiqueta(etqAux);
        }
    }
    
    public For addForJPA(For forVar, Simbolo operacionesAsignacion) throws CloneNotSupportedException{
        if (forVar.getLista() == null || operacionesAsignacion== null) {
            return null;
        }
        Simbolo op1 = (Simbolo) forVar.getA();
        For var = forVar;
        Cuarteto c = null;
        Simbolo asignacion = operacionesAsignacion;
        String id = var.getLista().get(0);
        String etiqRegresar = forVar.getLista().get(1);
        if (asignacion.getValor() instanceof Simbolo) {
            if (((Simbolo) asignacion.getValor()).getCuarteto() != null) {
                asignacionCuarteto(((Simbolo) asignacion.getValor()).getCuarteto().getResultado(), asignacion);
            } else {
                asignacionCuarteto((Simbolo) asignacion.getValor(), asignacion);
            }
        } else {
            if (asignacion.getValor() instanceof Integer) {
                if (((int) asignacion.getValor()) == -1) {
                    asignacionCuarteto(new Simbolo(Constantes.FLOAT_VAR_PJ, 1), asignacion);
                } else {
                    asignacionCuarteto(new Simbolo(Constantes.FLOAT_VAR_PJ, -1), asignacion);
                }
            } else {
                c = cuartetoOperacionAritmetica(1, asignacion, new Simbolo(op1.getTipo(), null, id), null, op1.getTipo());
                asignacionCuarteto(c.getResultado(), asignacion);
            }
        }
        gotoEscribirEtiqueta(etiqRegresar);
        nuevoAnalisisBoolean();
        escribirEtiquetasInicio(false);
        escribirEtiquetaFin();
        return forVar;
    }
    
    public List<String> addFor(Simbolo op1, Simbolo op2, Simbolo step){
        try {
            if(op1 == null || op2 == null){
                return null;
            }
            List<String> lista = new ArrayList<>();
            String id = op1.getId();
            String etiqRegresar = "etq" + contadoreEtiquetas;
            contadoreEtiquetas++;
            String etiqRefreso = "etq" + contadoreEtiquetas;
            contadoreEtiquetas++;
            String etiqVerdadera = "etq" + contadoreEtiquetas;
            contadoreEtiquetas++;
            String etiqFalsa = "etqf" + contadoreEtiquetasFin++;
            contadoreEtiquetasFin++;
            escribirNuevaEtiqueta(etiqRegresar);
            cuartetoCondicional(op1, op2, new Simbolo(null, null, "goto "+ etiqVerdadera), IGUAL_IGUAL);
            gotoEscribirEtiqueta(etiqFalsa);
            escribirNuevaEtiqueta(etiqRefreso);
            if (step == null) {
                Cuarteto c = cuartetoOperacionAritmetica(1, new Simbolo(op1.getTipo(), 1, null), new Simbolo(op1.getTipo(), null, id), null, op1.getTipo());
                asignacionCuarteto(c.getResultado(), op1);
            } else {
                Cuarteto c = cuartetoOperacionAritmetica(1, step, new Simbolo(op1.getTipo(), null, id), null, op1.getTipo());
                asignacionCuarteto(c.getResultado(), op1);
            }
            gotoEscribirEtiqueta(etiqRegresar);
            escribirNuevaEtiqueta(etiqVerdadera);
            lista.add(etiqFalsa);
            lista.add(etiqRefreso);
            return lista;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ManejadorCuartetos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void finFor(List<String> listaEtiqueta){
        if (listaEtiqueta != null) {
            gotoEscribirEtiqueta(listaEtiqueta.get(1));
            escribirNuevaEtiqueta(listaEtiqueta.get(0));
        }
    }
    
    public void actualizar(){
        gotoEscribirEtiqueta(etqAux);
        etqAux = null;
        escribirEtiquetasEscape();
        escribirEtiqueta();
    }
    public void returnCuarteto(Simbolo simbolo){
        Simbolo destino = null;
        if (destino == null) {
            destino = new Simbolo(simbolo.getTipo(), null, "temp" + contadorVariables);
            declararVariable(null, destino);
            contadorVariables++;
        } 
        Cuarteto cuarteto;
        if (simbolo.getCuarteto() != null) {
            asignacionCuarteto(simbolo.getCuarteto().getResultado(), destino);
//            cuarteto = new Cuarteto(RETURN, simbolo.getCuarteto().getResultado(), null, null);
        } else {
            asignacionCuarteto(simbolo, destino);
//            cuarteto = new Cuarteto(RETURN, simbolo, null, null);
        }
//        cuartetos.add(cuarteto);
    }
    
    public void cuartetoCondicional(Simbolo op1, Simbolo op2, Simbolo destino, String tipo) throws CloneNotSupportedException{
        Cuarteto cuarteto;
        if (op1.getCuarteto() == null && op2.getCuarteto() == null) {
            cuarteto = new Cuarteto(tipo, op1.clone(), op2.clone(), destino);
        } else if (op1.getCuarteto() == null){
            cuarteto = new Cuarteto(tipo, op1.clone(), op2.clone().getCuarteto().getResultado().clone(), destino);                
        } else {
            cuarteto = new Cuarteto(tipo, op1.getCuarteto().getResultado().clone(), op2.clone(), destino);
        }
        cuartetos.add(cuarteto);
    }
    
    public Cuarteto declararVariable(Simbolo op1, Simbolo destino){
        try {
            Cuarteto cuarteto;
            if (op1 == null) {
                cuarteto = new Cuarteto(DECLARACION, null, null, destino);
            } else {
                cuarteto = new Cuarteto(DECLARACION, op1.clone(), null, destino);
            }
            cuartetos.add(cuarteto);
            return cuarteto;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(ManejadorCuartetos.class.getName()).log(Level.SEVERE, null, ex);
        } return null;
    }
    
    public void imprimirDeclaracionArreglo(Simbolo arreglo, Tipo tipo, Object valor){
        cuartetos.add(new Cuarteto(ARREGLO, new Simbolo(tipo, null, "[" + valor + "]"), null, arreglo));
    }
    
    public Cuarteto asignacionCuarteto(Simbolo s, Simbolo destino){
        Cuarteto cuarteto = null;
        if (s.getCuarteto() == null) {
            try {
                cuarteto = new Cuarteto(null, s.clone(), null, destino);
                cuartetos.add(cuarteto);
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(ManejadorCuartetos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            cuarteto = new Cuarteto(null, s.getCuarteto().getResultado(), null, destino);
            cuartetos.add(cuarteto);
        } 
        return null;
    }
    
    public String printAux(Cuarteto cuarteto, String salida){
        if (cuarteto.getOperando1().getId() != null) {
            salida += cuarteto.getOperando1().getId();
        } else if (cuarteto.getOperando1().getValor() != null) {
            salida += cuarteto.getOperando1().getValor().toString();
        } 
        if (cuarteto.getOperando2() != null) {
            salida += " " + cuarteto.getOperador() + " ";
            if (cuarteto.getOperando2().getId() != null) {
                salida += cuarteto.getOperando2().getId();
            } else if (cuarteto.getOperando2().getValor() != null) {
                salida += cuarteto.getOperando2().getValor().toString();
            }
        }
        return salida;
    }
    
    public String print(){
        String regresar = "";
        System.out.println("COD 3D \n\n\n\n\n\n");
        regresar = librerias.stream().map((libreria) ->  "#include " + libreria + "\n").reduce(regresar, String::concat);
        regresar += "\n";
        for (Cuarteto cuarteto : cuartetos) {
            String salida = "";
            if (cuarteto.getOperador() != null) {
                if (cuarteto.getOperador().equals(DECLARACION)) {
                    switch (cuarteto.getResultado().getTipo().getSymbol()) {
                        case Constantes.CHAR:
                            salida += "char ";
                            break;
                        case Constantes.INT:
                            salida += "int ";
                            break;
                        case Constantes.FLOAT:
                            salida += "float ";
                            break;
                        default:
                    }
                    salida += cuarteto.getResultado().getId();
                    if (cuarteto.getOperando1() != null) {
                        salida += " = ";
                        salida += printAux(cuarteto, "");
                        salida += ";";
                    } else { 
                        salida += ";";
                    }
                } else if (cuarteto.getOperador().equalsIgnoreCase(MAS) ||
                        cuarteto.getOperador().equalsIgnoreCase(MENOS) ||
                        cuarteto.getOperador().equalsIgnoreCase(POR) ||
                        cuarteto.getOperador().equalsIgnoreCase(DIV) ||
                        cuarteto.getOperador().equalsIgnoreCase(MOD)){
                    salida += cuarteto.getResultado().getId();
                    salida += " = ";
                    salida += printAux(cuarteto, "");
                        salida += ";";
                } else if (cuarteto.getOperador().equalsIgnoreCase(IGUAL_IGUAL) ||
                        cuarteto.getOperador().equalsIgnoreCase(DIFERENTE) ||
                        cuarteto.getOperador().equalsIgnoreCase(MAYOR_IGUAL) ||
                        cuarteto.getOperador().equalsIgnoreCase(MAYOR) ||
                        cuarteto.getOperador().equals(MENOR_IGUAL) ||
                        cuarteto.getOperador().equalsIgnoreCase(MENOR)){
                        salida += "if (" + printAux(cuarteto, "") + ")" + " " +cuarteto.getResultado().getId();
                        salida += ";";
                } else if (cuarteto.getOperador().equalsIgnoreCase(GOTO)){
                    salida += cuarteto.getResultado().getId();
                    salida += ";";
                } else if (cuarteto.getOperador().equalsIgnoreCase(ETIQUETA)){
                    salida += cuarteto.getResultado().getId();
                    salida += ":";
                } else if (cuarteto.getOperador().equalsIgnoreCase(IMPRIMIR_CONSOLA)) {
                    salida += IMPRIMIR_CONSOLA + "(" + cuarteto.getOperando1().toString()+ ");";
                } else if (cuarteto.getOperador().equalsIgnoreCase("$FinProcedimiento")
                        || cuarteto.getOperador().equalsIgnoreCase(PROCEDIMIENTO)) {
                    salida += cuarteto.getOperando1().getId();
                } else if (cuarteto.getOperador().equalsIgnoreCase(RETURN)){
                    if (cuarteto.getOperando1().getId() != null) {
                        salida += "//" + RETURN + " " + cuarteto.getOperando1().getId() + ";";
                    } else {
                        salida += "//" + RETURN + " " + cuarteto.getOperando1() + ";";
                    }                    
                } else if (cuarteto.getOperador().equalsIgnoreCase(COMENTARIO)) {
                    salida += cuarteto.getOperando1().getId().replaceAll("\n", "");
                } else if (cuarteto.getOperador().equalsIgnoreCase(PARAMETROS)) {
                    if (cuarteto.getOperando1().getCuarteto() != null) {
                        salida += PARAMETROS + " " + cuarteto.getOperando1().getCuarteto().getResultado();
                    } else {
                        salida += PARAMETROS + " " + cuarteto.getOperando1();
                    }
                    salida += ";";
                } else if (cuarteto.getOperador().equalsIgnoreCase(CALL)){
                    salida += cuarteto.getOperando1().getId();
                } else if (cuarteto.getOperador().equalsIgnoreCase(SCANF)){
                    salida += SCANF + " (" + cuarteto.getOperando1().getId() + ");";
                } else if (cuarteto.getOperador().equalsIgnoreCase(GETCH)) {
                    if (cuarteto.getResultado() != null) {
                        salida += cuarteto.getResultado().getId() + " = " + GETCH + "()";
                    } else {
                        salida += GETCH + "()";
                    }
                } else if (cuarteto.getOperador().equalsIgnoreCase(CLRSCR)) {
                    salida += CLRSCR + "();";
                } else if (cuarteto.getOperador().equalsIgnoreCase(ARREGLO)) {
                    switch (cuarteto.getResultado().getTipo().getSymbol()) {
                        case Constantes.CHAR:
                            salida += "char ";
                            break;
                        case Constantes.INT:
                            salida += "int ";
                            break;
                        case Constantes.FLOAT:
                            salida += "float ";
                            break;
                        default:
                    }
                    salida += cuarteto.getResultado().getId();
                    salida += cuarteto.getOperando1().getId();
                }
            } else {
                salida += cuarteto.getResultado().getId();
                if (cuarteto.getOperando1() != null) {
                    salida += " = ";
                    salida += printAux(cuarteto, "");
                    salida += ";";
                } else { 
                    salida += ";";
                }
            }
            regresar += salida + "\n";
        } return regresar;
    }
    
    public List<Cuarteto> clonarCuartetos() {
        List<Cuarteto> nueva = new ArrayList<>();
        nueva.addAll(cuartetos);
        print();
        System.out.println("\n\n\n\n");
        cuartetos.clear();
        librerias.clear();
        return nueva;
    }
}
