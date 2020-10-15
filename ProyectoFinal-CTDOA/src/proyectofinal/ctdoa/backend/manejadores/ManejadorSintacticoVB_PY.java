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
public class ManejadorSintacticoVB_PY extends ManejadorSintacticoJP{
    
    private static ManejadorSintacticoVB_PY manejadorSintacticoVB_PY;
    private static ManejadorCuartetos manejadorCuartetos = ManejadorCuartetos.getInstancia();
    private static TablaSimbolos tablaSimbolos;
    int contadorAmbitos;
    
    private ManejadorSintacticoVB_PY() {}

    /** Llamado a la instancia unica de esta clase
     * @return  Retorna la instancia de la clase
     */
    
    public static ManejadorSintacticoVB_PY getInstacia(){
        if (manejadorSintacticoVB_PY == null) {
            manejadorSintacticoVB_PY = new ManejadorSintacticoVB_PY();
        } return manejadorSintacticoVB_PY;
    }
    
    @Override
    public Simbolo agregarCualquierSimbolo(Simbolo s){
        boolean bandera = tablaSimbolos.agregarTablaSimbolos(s);
        return s;
    }
    
    @Override
    public void removerCualquierSimbolo(Object s){
        if (s != null) {
            tablaSimbolos.removerPorReferencia((Simbolo) s);
        }
    }
    
    @Override
    public void errorSemantico(int left, int right, Object valor, String mensaje) {
        super.errorSemantico(left, right, valor, mensaje); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void errorSintax(int left, int right, Object value, String mensaje) {
        super.errorSintax(left, right, value, mensaje); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void nuevoAnalisis() {
        this.contadorAmbitos = 0;
        tablaSimbolos = new TablaSimbolos();
    }

    @Override
    public Simbolo declaraUnaVariableParametro(Tipo tipo, String id, int l, int r) {
        if (tablaSimbolos.buscarPorId(id) == null) {
            Simbolo simbolo  = new Simbolo(tipo, null, id);
            boolean bandera = tablaSimbolos.agregarTablaSimbolos(simbolo);
            return simbolo;
        } else {
            errorSintax(l, r, id, "Ya se encuentra definida la variable: < " + id + " >");
            return null;
        }
    }
    
    public Simbolo declaraUnaVariableAsignacion(Tipo tipo, Object valor, String id, int l, int r) {
        if (valor == null) {
            errorSemantico(l, r, "null", "No se puede asignar el valor a la variable: < " + id + " > debido a que el tipo de dato no corresponde.");
            return null;
        } else {
            if (tipo.isFatherOf(((Simbolo) valor).getTipo().getSymbol())) {
                if (tablaSimbolos.buscarPorId(id) == null) {
                    Simbolo simbolo  = (Simbolo) valor;
                    simbolo.setId(id);
                    simbolo.setTipo(tipo);
                    if (simbolo.getCuarteto() != null) {
                        simbolo.setCuarteto(manejadorCuartetos.declararVariable(((Simbolo) simbolo).getCuarteto().getResultado(), simbolo));
                    } else {
                        if (simbolo.getValor() instanceof Simbolo) {
                            simbolo.setCuarteto(manejadorCuartetos.declararVariable((Simbolo) simbolo.getValor(), simbolo));
                        } else {
                            simbolo.setCuarteto(manejadorCuartetos.declararVariable(new Simbolo(null, simbolo.getValor()), simbolo));
                        }
                        if(simbolo.getInput() != 0){
                            manejadorCuartetos.imprimirScanf(simbolo.getInput(), simbolo.getId());
                        }
                    }
                    boolean bandera = tablaSimbolos.agregarTablaSimbolos(simbolo);
                    return simbolo;
                } else {
                    errorSintax(l, r, id, "Ya se encuentra definida la variable: < " + id + " >");
                    return null;
                }
            } else {
                errorSemantico(l, r, ((Simbolo) valor).getValor(), "No se puede asignar el valor a la variable: < " + id + " > debido a que el tipo de dato no corresponde.");
                return null;
            }
        }
    }
   
    public Simbolo declaraUnaVariable(Object valor, int l, int r) {
        if (valor == null) {
            errorSemantico(l, r, "null", "No se puede asignar el valor a la variable, debido a que el tipo de dato no corresponde.");
            return null;
        } else {
            if (tablaSimbolos.buscarPorId(((Simbolo) valor).getId()) == null) {
                Simbolo simbolo  = (Simbolo) valor;
                if (simbolo.getValor() == null) {
                    simbolo.setCuarteto(manejadorCuartetos.declararVariable(null, simbolo));
                } else {
                    if (simbolo.getValor() instanceof Simbolo) {
                        simbolo.setCuarteto(manejadorCuartetos.declararVariable(((Simbolo) simbolo.getValor()).getCuarteto().getResultado(), simbolo));
                        if(((Simbolo) simbolo.getValor()).getInput() != 0){
                            manejadorCuartetos.imprimirScanf(((Simbolo) simbolo.getValor()).getInput(), simbolo.getId());
                        }
                    } else {
                        simbolo.setCuarteto(manejadorCuartetos.declararVariable((Simbolo) simbolo, simbolo));
                    }
                }
                boolean bandera = tablaSimbolos.agregarTablaSimbolos(simbolo);
                return simbolo;
            } else {
                errorSintax(l, r, ((Simbolo) valor).getId(), "Ya se encuentra definida la variable: < " + ((Simbolo) valor).getId() + " >");
                return null;
            }
        } 
    }
    
    /**
     * Metodo que declara varias variables
     * @param listaSimbolos lista de id's que se declararan
     * @param tipo tipo de la declaracion 
     * @param l left del simbolo
     * @param r right del simbolo
     * @return La declaracion de la variable
     */
    
    public List<Simbolo> declararVariasVariables(List<Simbolo> listaSimbolos, Tipo tipo, int l, int r) {
        List<Simbolo> lista = new ArrayList<>();
        listaSimbolos.stream().map((simbolo) -> declaraUnaVariable(simbolo, l, r)).filter((s) -> (s != null)).map((s) -> {
            s.setTipo(tipo);
            return s;
        }).forEachOrdered((s) -> {
            lista.add(s);
        });
        return lista;
    }
    
    public Simbolo parametroPY(String id, int l, int r){
        if (tablaSimbolos.buscarPorId(id) == null) {
            Simbolo param = new Simbolo(Constantes.FLOAT_VAR_VB_PY, "Parametro", id);
            tablaSimbolos.agregarTablaSimbolos(param);
            /*tablaSimbolos.print();*/
            return param;
        } else {
            errorSintax(l, r, "Parametro", "Error, la variable: " + id + ", se encuentra definida.");
            return null;
        }
    }
    
    public Simbolo idPY(String id, int l, int r) {
        Simbolo simbolo = tablaSimbolos.buscarPorId(id);
        if (simbolo == null) {
            simbolo = new Simbolo(Constantes.FLOAT_VAR_VB_PY, null, id);
            tablaSimbolos.agregarTablaSimbolos(simbolo);
            simbolo.setCuarteto(manejadorCuartetos.declararVariable(null, simbolo));
            return simbolo;
        } else {
            if (Constantes.FLOAT_VAR_VB_PY.isFatherOf(simbolo.getTipo().getSymbol())) {
                return simbolo;
            } else {
                errorSemantico(l, r, "ID", "Error, en la declaracion de la variable: < " + id + " >, el tipo no es compatible." );
                return null;
            }
        }
    }
    
    public Simbolo declararVaiablesPY(int l, int r, Simbolo valor){
        if (valor != null) {
            if (valor.getValor() == null) {
                errorSemantico(l, r, "Asignacion", "Error, en la asignacion de variable.");
                return null;
            }
            Simbolo simbolo = tablaSimbolos.buscarPorId(valor.getId());
            if (simbolo != null) {
                if (simbolo.getTipo().isFatherOf(valor.getTipo().getSymbol())) {
                    simbolo.setCuarteto(manejadorCuartetos.asignacionCuarteto(((Simbolo) valor.getValor()), simbolo));
                    if (valor.getValor() instanceof Simbolo) {
                        if(((Simbolo) valor.getValor()).getInput() != 0){
                            manejadorCuartetos.imprimirScanf(((Simbolo) valor.getValor()).getInput(), simbolo.getId());
                        }
                    }
                    return simbolo;
                } else {
                    errorSemantico(l, r, "Asignacion", "Error, en la  comprobacion de tipos");
                    return null;
                }
            } else {
                valor.setCuarteto(manejadorCuartetos.declararVariable((Simbolo) valor.getValor(), valor));
                tablaSimbolos.agregarTablaSimbolos(valor);
                if (valor.getValor() instanceof Simbolo) {
                    if(((Simbolo) valor.getValor()).getInput() != 0){
                            manejadorCuartetos.imprimirScanf(((Simbolo) valor.getValor()).getInput(), valor.getId());
                        }
                    }
                return valor;
            } 
        } return null;
    }

    @Override
    public Simbolo metodoBuscarID(String id, int l, int r) {
        Simbolo retornar = tablaSimbolos.buscarPorId(id);
        if (retornar == null) {
            errorSintax(l, r, id, "Error no se encuentra definida la variable");
        } 
        return retornar;
    }

    @Override
    public Simbolo modificarVariables(Object s, int l, int r) throws CloneNotSupportedException {
        if (s == null) {
            return null;
        } else {
            if (((Simbolo) s).getTipo() == Constantes.CHAR_VAR_VB_PY) {
                errorSemantico(l, r, "Operacion", "El tipo de dato: Char, no puede tener un signo negativo.");
                return null;
            }
        } return (Simbolo) s;
    }

    @Override
    public boolean comprobarCompatibilidadTipos(Object op1, Object op2, int l1, int r1, int l2, int r2) {
        return super.comprobarCompatibilidadTipos(op1, op2, l1, r1, l2, r2); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Simbolo realizarOperaciones(Object op1, Object op2, int l1, int r1, int l2, int r2, int tipoOperacion) {
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

    @Override
    public Simbolo asignacionVariables(Object s, int l, int r) {
        if (s != null) {
            Simbolo aux = metodoBuscarID(((Simbolo) s).getId(), l, r);
            if (aux != null) {
                if (((Simbolo) ((Simbolo) s).getValor()).getInput() != 0) {
                    manejadorCuartetos.imprimirScanf(((Simbolo) ((Simbolo) s).getValor()).getInput(), aux.getId());
                    return aux;
                }
                if (aux.getTipo().isFatherOf(((Simbolo) s).getTipo().getSymbol())) {
                    aux.setValor(((Simbolo) s).getValor());
                    aux.setCuarteto(manejadorCuartetos.asignacionCuarteto((Simbolo)((Simbolo) s).getValor(), aux));
                    return aux;
                } else {
                    errorSemantico(l, r, ((Simbolo) s).getId(), "No se puede realizar la asigacion debido a que los tipos no son compatibles.");
                }  
            } 
        } return null;
    }

    @Override
    public Simbolo comprobarOperacionesLogicas(Object op1, Object op2, int tipoOperacion, int l, int r) {
        return super.comprobarOperacionesLogicas(op1, op2, tipoOperacion, l, r); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Simbolo comprobacionCondicional(Object op1, Object op2, int tipoOperacion, int l, int r) {
        return super.comprobacionCondicional(op1, op2, tipoOperacion, l, r); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Simbolo> retornarEstructuras(Object estructura, Object lista) {
        return super.retornarEstructuras(estructura, lista); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public Simbolo contruirEstructuraCiloDW(Object statement, Object condicional, int tipo) {
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
                boolean bandera = tablaSimbolos.agregarTablaSimbolos(simbolo);
                return simbolo;
            case 2:
                CicloDoWhile cicloDoWhile = new CicloDoWhile();
                cicloDoWhile.addStatements(statement);
                vaciarAmbitos(statement);
                Simbolo simbolo1 = new Simbolo(new Tipo("DoWhile", Constantes.DO_WHILE), cicloDoWhile);
                boolean bandera1 = tablaSimbolos.agregarTablaSimbolos(simbolo1);
                return simbolo1;
            case 3:
                CicloFor cicloFor = new CicloFor();
                cicloFor.addStatements(statement);
                vaciarAmbitos(statement);
                Simbolo simbolo2 = new Simbolo(new Tipo("For", Constantes.FOR), cicloFor);
                boolean bandera2 = tablaSimbolos.agregarTablaSimbolos(simbolo2);
                return simbolo2;
            case 4:
                Metodo metodo = new Metodo();
                metodo.addStatements(statement);
                vaciarAmbitos(statement);
                Simbolo simbolo3 = new Simbolo(new Tipo("Metodo", Constantes.METODO), metodo);
                boolean bandera3 = tablaSimbolos.agregarTablaSimbolos(simbolo3);
                return simbolo3;
        } return null;
    }
    
    public Simbolo cicloForPY(String id1, int left, int right, Object rango, Object statement){
        if (rango != null && statement != null) {
            if (!(rango instanceof Simbolo)) {
                if (((List<Simbolo>) rango).isEmpty()) {
                    return null;
                }
                if (((List<Simbolo>) rango).size() == 2) {
                    Simbolo sim1 = ((List<Simbolo>) rango).get(0);
                    Simbolo sim2 = ((List<Simbolo>) rango).get(1);
                    if (sim1.getTipo().isFatherOf(sim2.getTipo().getSymbol())
                        || sim2.getTipo().isFatherOf(sim1.getTipo().getSymbol())) {
                    } else {
                        errorSintax(left, right, "Rango", "Error, los rangos son de distinto tipo.");
                        return null;
                    }
                }
                if (((List<Simbolo>) rango).size() == 3) {
                    Simbolo sim1 = ((List<Simbolo>) rango).get(0);
                    Simbolo sim2 = ((List<Simbolo>) rango).get(1);
                    Simbolo sim3 = ((List<Simbolo>) rango).get(2);
                    if (sim1.getTipo().isFatherOf(sim2.getTipo().getSymbol())
                        || sim2.getTipo().isFatherOf(sim1.getTipo().getSymbol())
                        && (sim2.getTipo().isFatherOf(sim3.getTipo().getSymbol())
                        || sim3.getTipo().isFatherOf(sim2.getTipo().getSymbol()))) {
                    } else {
                        errorSintax(left, right, "Rango", "Error, los rangos son de distinto tipo.");
                        return null;
                    }
                }
                
            } 
            CicloFor cicloFor = new CicloFor();
            cicloFor.addStatements(statement);
            vaciarAmbitos(statement);
            Simbolo simbolo = new Simbolo(new Tipo("For", Constantes.FOR), cicloFor);
            boolean bandera = tablaSimbolos.agregarTablaSimbolos(simbolo);
            return simbolo;
        }
        vaciarAmbitos(rango);
        return null;
    }

    @Override
    public Simbolo comprobarAsignacionFor(Object valor, String id, int l, int r, Tipo tipo) {
        if (tablaSimbolos.buscarPorId(id) == null || tipo == null) {
            if (valor != null) {
                if (tipo != null) {
                    if (tipo.isFatherOf(((Simbolo) valor).getTipo().getSymbol())) {
                        Simbolo s = new Simbolo(tipo, ((Simbolo) valor).getValor(), id);
                        manejadorCuartetos.declararVariable((Simbolo) valor, s); 
                        boolean bandera = tablaSimbolos.agregarTablaSimbolos(s);
                        return s;
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

    @Override
    public boolean comprobarIdFor(Object idComprobar, String id, int l, int r) {
        if (idComprobar != null) {
            if ((((Simbolo) idComprobar).getId()).equals(id) ) {
                return true;
            } else { 
                if (tablaSimbolos.buscarPorId(id) != null) {
                    return true;
                } else {
                    errorSintax(l, r, id, "Error, la variable <" + id + ">, no se encuentra definida.");
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    @Override
    public Simbolo crearEstructuraCondicional(Object valor, Object condicional, int tipo) {
        if (condicional != null) {
            Condicional condicionalIFE = null;
            switch (tipo) {
                case 1:
                    condicionalIFE = new Condicional(new Tipo("If", Constantes.IF), valor, true);
                    break;
                case 2:
                    
                    condicionalIFE = new Condicional(new Tipo("ElseIf", Constantes.ELSE_IF), valor, true);
                    break;
                case 3:
                    condicionalIFE = new Condicional(new Tipo("Else", Constantes.ELSE), valor, true);
                    break;
            }
            vaciarAmbitos(valor);
            if (condicionalIFE != null) {
                condicionalIFE.addStatements(valor);/****** esto no etaba */
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

    @Override
    public Simbolo construirCondicionalIf(Object simboloIf, Object listaElseIf, Object simboloElse) {
        if (simboloIf != null) {
            CondicionalIf condicionalIf = new CondicionalIf((Condicional)((Simbolo) simboloIf).getValor());
            tablaSimbolos.removerPorReferencia(((Simbolo) simboloIf));
            if (listaElseIf != null) {
                if (listaElseIf instanceof Simbolo) {
                    condicionalIf.addCondicionalesElseIf((Condicional)((Simbolo)listaElseIf).getValor());
                    tablaSimbolos.removerPorReferencia(((Simbolo) listaElseIf));
                } else {
                    for (Simbolo simbolo : (List<Simbolo>) listaElseIf) {
                        condicionalIf.addCondicionalesElseIf((Condicional) simbolo.getValor());
                        tablaSimbolos.removerPorReferencia(simbolo);
                    }
                } 
            }
            if (simboloElse != null) {
                condicionalIf.setCondicionalElse((Condicional)((Simbolo) simboloElse).getValor());
                tablaSimbolos.removerPorReferencia(((Simbolo) simboloElse));
            } else {
                manejadorCuartetos.escribirEtiqueta();
            }
            Simbolo simbolo = new Simbolo(((Simbolo) simboloIf).getTipo(), condicionalIf); 
            boolean bandera = tablaSimbolos.agregarTablaSimbolos(simbolo);
            manejadorCuartetos.etqFinTurno();
            return simbolo;
        }
        return null;
    }

    @Override
    public Simbolo crearEstructuraCases(Object caso, Object statement, int l, int r, boolean elseC) {
        if (caso != null || elseC) {
            Case c;
            if (elseC) {
                c = new Case(true);
            } else {
                c = new Case((Simbolo) caso, true);
            }
            if (statement != null) {
                c.addStatements(statement);
                vaciarAmbitos(statement);
                return new Simbolo(new Tipo("Case", Constantes.CASE), c);
            } return null;
        } else {
            vaciarAmbitos(statement);
            errorSintax(l, r, "Case", "Error en la comprobacion del case");
        }
        return null;
    }

    @Override
    public Simbolo crearCase(Object listaCasos, Object casoDefault, Object simbolo, int l, int r) {
        if (simbolo != null) {
            Simbolo comprobacionCase = (Simbolo) simbolo;
            Simbolo casoEstuctura;
            CondicionalSwitch condicionalSwitch = new CondicionalSwitch();
            if (listaCasos != null) {
                if (listaCasos instanceof Simbolo) {
                    Tipo t = ((Case)((Simbolo) listaCasos).getValor()).getCaso().getTipo();
                    if (comprobacionCase.getTipo().isFatherOf(t.getSymbol())) {
                        condicionalSwitch.agregarCaso((Case)((Simbolo) listaCasos).getValor());
                    } else { 
                        errorSemantico(l, r, "Case", "Error comporbacion de tipo del case, no son commpatibles.");
                        return null;
                    }
                } else {
                    for (Simbolo sim : (List<Simbolo>) listaCasos) {
                        if (comprobacionCase.getTipo().isFatherOf(((Case) sim.getValor()).getCaso().getTipo().getSymbol())) {
                            condicionalSwitch.agregarCaso((Case)((Simbolo) sim).getValor());
                        } else { 
                            errorSemantico(l, r, "Case", "Error comporbacion de tipo del case, no son commpatible.");
                            return null;
                        }
                    }
                }
            }
            if (casoDefault != null) {
                condicionalSwitch.agregarCaso((Case)((Simbolo) casoDefault).getValor());
            }
            casoEstuctura = new Simbolo(new Tipo("Switch", Constantes.SWITCH), condicionalSwitch);
            boolean bandera = tablaSimbolos.agregarTablaSimbolos(casoEstuctura);
            return casoEstuctura;
        }  return null;
    }
    
    @Override
    public Simbolo funcion(Object parametros, Tipo tipo, Object estructuras, Object regresar, String id, int l, int r){
        if (parametros != null) {
            vaciarAmbitos(parametros);
            if (regresar != null) {
                if (!tipo.isFatherOf( ((Simbolo) ((Simbolo) regresar).getValor()).getTipo().getSymbol()) ) {
                    vaciarAmbitos(estructuras);
                    errorSemantico(l, r, "Tipos", "Error en la comprobacion de tipos, el valor del Return no es compatible con la funcion.");
                  return null;
                } 
                Funcion f = new Funcion();
                f.setParametros(parametros);
                f.addStatements(estructuras);
                vaciarAmbitos(estructuras);
                Simbolo function = new Simbolo(new Tipo("Funcion", Constantes.FUNCION), f, id);
                function.setTipoFuncion(tipo);
                if (tablaSimbolos.buscarPorId(id) == null) {
                    boolean bandera = tablaSimbolos.agregarTablaSimbolos(function);
                    /*tablaSimbolos.print();*/
                    return function; 
                } else {
                    vaciarAmbitos(estructuras);
                    errorSintax(l, r, "Funcion < " + id + " > ", "Error existe un metodo o funcion declarada con el mismo nombre.");
                    return null;
                }
            } else {
                vaciarAmbitos(estructuras);
                errorSemantico(l, r, "Error", "Error en las operaciones del return.");
                return null;
            }
        } else {
            vaciarAmbitos(estructuras);
            /*tablaSimbolos.print();*/
        } 
        vaciarAmbitos(estructuras);
        return null;
    }
    
    @Override
    public Simbolo metodos(Object parametros, Object estructuras, String id, int l, int r){
        if (parametros != null) {
            vaciarAmbitos(parametros);
            Metodo m = new Metodo();
            m.setParametros(parametros);
            m.addStatements(estructuras);
            vaciarAmbitos(estructuras);
            Simbolo metodo = new Simbolo(new Tipo("Metodo", Constantes.METODO), m, id);
            if (tablaSimbolos.buscarPorId(id) == null) {
                boolean bandera = tablaSimbolos.agregarTablaSimbolos(metodo);
                /*tablaSimbolos.print();*/
                return metodo; 
            } else {
                vaciarAmbitos(parametros);
                errorSintax(l, r, "Metodo <" + id + "> ", "Error existe un metodo o funcion declarada con el mismo nombre.");
                return null;
            }
        } else {
            vaciarAmbitos(estructuras);
         } 
        return null;
    }

    public Simbolo def(String id, Object parametros, Object statement, int l ,int r){
        if (statement == null) {
            errorSemantico(l, r, "Intrucciones", "Error en las instrucciones;");
            return null;
        } else {
            Metodo m = new Metodo();
            m.addStatements(statement);
            if (m.comporbarSiTieneReturnMetodo()) {
                /*tablaSimbolos.print();*/
                Simbolo f =  funcion(parametros, Constantes.FLOAT_VAR_VB_PY, statement, new Simbolo(Constantes.FLOAT_VAR_VB_PY, new Simbolo(Constantes.FLOAT_VAR_VB_PY, 0)), id, l, r);
                tablaSimbolos.removerSimbolosAll();
                /*tablaSimbolos.print();*/
                return f;
            } else {
                Simbolo ms = metodos(parametros, statement, id, l, r);
                tablaSimbolos.removerSimbolosAll();
                return ms;
            }
            
        }
    }
    
    @Override
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
    
    public void vaciarAmbitosPY(Object simbolos) {
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
    
    public Simbolo claseVB(Object simbolo){
        if (simbolo != null) {
            Simbolo clase;
            vaciarAmbitos(simbolo);
            clase = new Simbolo(new Tipo("ClaseVB", Constantes.CLASE_VB), simbolo, "$ClaseVB");
            return clase;
        } return new Simbolo(new Tipo("ClaseVB", Constantes.CLASE_VB), null, "$ClaseVB");
    }
    
    public Simbolo clasePY(Object simbolo){
        if (simbolo != null) {
            Simbolo clase;
            vaciarAmbitos(simbolo);
            /*tablaSimbolos.print();*/
            clase = new Simbolo(new Tipo("ClasePY", Constantes.CLASE_PY), simbolo, "$ClasePY");
            return clase;
        } return new Simbolo(new Tipo("ClasePY", Constantes.CLASE_PY), null, "$ClasePY");
    }
}