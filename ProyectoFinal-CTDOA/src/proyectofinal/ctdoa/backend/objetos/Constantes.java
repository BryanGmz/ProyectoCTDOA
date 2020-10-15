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
public class Constantes {
    
    public final static int ERROR = -1;
    public final static int INT = 1;
    public final static int FLOAT = 2;
    public final static int CHAR = 3;
    public final static int STRING = 4;
    public final static int IF = 5;
    public final static int ELSE_IF = 6;
    public final static int ELSE = 7;
    public final static int PARSER_VB = 8;
    public final static int PARSER_JAVA = 9;
    public final static int PARSER_PY = 10;
    public final static int PARSER_PRINCIPAL = 11;
    public final static int RETURN = 12;
    public final static int WHILE = 13;
    public final static int DO_WHILE = 14;
    public final static int FOR = 15;
    public final static int SWITCH = 17;
    public final static int FUNCION = 18;
    public final static int METODO = 19;
    public final static int CASE = 20;
    public final static int CLASE_VB = 21;
    public final static int CLASE_PJ = 22;
    public final static int CLASE_PY = 23;
    public final static int CLASE_JAVA = 24;
    public final static int LIBRERIAS = 25;
    public final static int INSTANCIAS = 26;
    //Lebguajes Principal - Java
    public final static Tipo CHAR_VAR_PJ = new Tipo("Char", null, CHAR);
    public final static Tipo INT_VAR_PJ = new Tipo("Integer", CHAR_VAR_PJ, INT);
    public final static Tipo FLOAT_VAR_PJ = new Tipo("Float", INT_VAR_PJ, FLOAT);
    //Lenguajes Visual Basic - Python
    public final static Tipo CHAR_VAR_VB_PY = new Tipo("Char", null, CHAR);
    public final static Tipo INT_VAR_VB_PY = new Tipo("Integer", null, INT);
    public final static Tipo FLOAT_VAR_VB_PY = new Tipo("Float", INT_VAR_VB_PY, FLOAT);
    public final static Tipo RETURN_VAR = new Tipo("return", null, RETURN);
    
}
