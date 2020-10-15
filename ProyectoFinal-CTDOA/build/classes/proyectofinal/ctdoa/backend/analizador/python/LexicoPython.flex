package proyectofinal.ctdoa.backend.analizador.python;

import java_cup.runtime.*;
import java.util.Stack;
import proyectofinal.ctdoa.backend.manejadores.*;
import proyectofinal.ctdoa.frontend.gui.FrameCTDOA;

%%
%class LexicoPython
%type java_cup.runtime.Symbol
%cup
%public
%cupdebug
%full
%line
%column
%char

LineTerminator = \r
InputCaracter = [^\r\n]
WhiteSpace = \r | [ \f]

/* Comentarios */

Comment = {ComentarioTradicional} | {FinLineaComment} | {DocumentoComentado}
ComentarioTradicional = "/*" [^*] ~"*/" | "/*" "*"+ "/"
FinLineaComment = "//" {InputCaracter}* ({LineTerminator} | "\n" | "\r" "\n")?
DocumentoComentado = "/**" {CommentContenido} "*"+ "/"
CommentContenido = ( [^*] | \*+ [^/*] )*

/* Entrada de Datos */

IInput = "intinput"
FInput = "floatinput"
CInput = "charinput"

/* Indent y Dedent */

IDent = "\n"+ ("\t" | " " | "\b")*

/* Caracter */
Caracter = "\'" ([#-÷] | "\\b" | "\\n" | "\\\"" |"!") ?   "\'"

/* Cadena */
Cadena = "\"" ([#-÷áéíóúAZÁÉÍÓÚÑñ!] | " ") * "\""

L = [a-záéíóúA-ZÁÉÍÓÚÑñ]

D = [0-9]

%{
   
    private boolean bandera;
    private FrameCTDOA frameCTDOA;
    private ManejadorCuartetos mc = ManejadorCuartetos.getInstancia();

    private Stack<Integer> pila = new Stack<>();

    public void limpiarStack() {
        pila.clear();
    }

    public void setFrameCTDOA(FrameCTDOA frameCTDOA) {
        this.frameCTDOA = frameCTDOA;
    }
    
    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline + 1, yycolumn + 1, value);
    }

    private Symbol symbol(int type){
        return new Symbol(type, yyline + 1, yycolumn + 1);
    }

%}
%%

<YYINITIAL> {

    /* Espacios*/
    
    { WhiteSpace }      {/*Ignore*/}
    ( "â" )*          {/*Ignore*/}
    ( " " )*            {/*Ignore*/}
    { Comment }         {mc.addComentario(yytext());}

    /* Solicitud de Datos */
    
    {IInput}        {/*System.out.println(yytext()); */return new Symbol(sym.IINPUT, yycolumn, yyline, new String(yytext()));}
    {FInput}        {/*System.out.println(yytext()); */return new Symbol(sym.FINPUT, yycolumn, yyline, new String(yytext()));}
    {CInput}        {/*System.out.println(yytext()); */return new Symbol(sym.CINPUT, yycolumn, yyline, new String(yytext()));}
        
    { IDent }        {
                        int tab = 0;
                        char[] split = yytext().toCharArray();
                            int contador = 0;
                            int contadorEspacios = 0;
                            for (int i = 0; i < split.length; i++) {
                                switch (split[i]) {
                                    case '\t':
                                        contador++;
                                        contadorEspacios = 0;
                                        break;
                                    case '\b':
                                    case ' ':
                                        if (contadorEspacios == 3) {
                                            contador++;
                                            contadorEspacios = 0;
                                        } else {
                                            contadorEspacios++;
                                        }   break;
                                    default:
                                        contadorEspacios = 0;
                                        break;
                                }
                            }
                        /*System.out.println("\n\nTABS " + contador + "\n\n");*/
                        if(contador == 0 && !bandera) {
                            bandera = true;
                            return new Symbol(sym.SALTO, yycolumn, yyline, "SALTO");
                        }
                        if (pila.empty()) {
                            pila.push(contador);
                            //System.out.println("SALT INDENT");
                            return new Symbol(sym.INDENT, yycolumn, yyline, "INDENT");
                        } else {
                            tab = pila.peek();
                            if(tab > contador) {
                                pila.pop();
                                //System.out.println("DEDENT");
                                return new Symbol(sym.DEDENT, yycolumn, yyline, "DEDENT");
                            } else if (tab < contador) {
                                pila.push(contador);
                                //System.out.println("INDENT");
                                return new Symbol(sym.INDENT, yycolumn, yyline, "INDENT");
                            } else { 
                                //System.out.println("SALTO: TAB: " + tab + " CONTADOR: " + contador);
                                return new Symbol(sym.SALTO, yycolumn, yyline, "SALTO");
                            }
                        }
                    }

    /* Cadena  */
    
    {Cadena}        {/*System.out.println(yytext()); */return new Symbol(sym.CADENA, yycolumn, yyline, new String(yytext()));}  

    /* Caracteres */ 
    
    {Caracter}      {/*System.out.println(yytext()); */return new Symbol(sym.CARACTER, yycolumn, yyline, new String(yytext()));}  

    /* Operadores - Aritmeticos  */
    
    ( "+" )             {/*System.out.println(yytext()); */return new Symbol(sym.MAS, yycolumn, yyline, yytext());}
    ( "-" )             {/*System.out.println(yytext()); */return new Symbol(sym.MENOS, yycolumn, yyline, yytext());}
    ( "*" )             {/*System.out.println(yytext()); */return new Symbol(sym.POR, yycolumn, yyline, yytext());}
    ( "/" )             {/*System.out.println(yytext()); */return new Symbol(sym.DIV, yycolumn, yyline, yytext());}
    ( "%" )             {/*System.out.println(yytext()); */return new Symbol(sym.MOD, yycolumn, yyline, yytext());}
    
    /* Operadores - Comparacion */

    ( "=" )             {/*System.out.println(yytext()); */return new Symbol(sym.IGUAL, yycolumn, yyline, yytext());}
    ( "<>" )            {/*System.out.println(yytext()); */return new Symbol(sym.DIFERENTE, yycolumn, yyline, yytext());}
    ( "!=" )            {/*System.out.println(yytext()); */return new Symbol(sym.DISTINTO, yycolumn, yyline, yytext());}
    ( "==" )            {/*System.out.println(yytext()); */return new Symbol(sym.IGUAL_IGUAL, yycolumn, yyline, yytext());}
    ( "<" )             {/*System.out.println(yytext()); */return new Symbol(sym.MENOR, yycolumn, yyline, yytext());}
    ( ">" )             {/*System.out.println(yytext()); */return new Symbol(sym.MAYOR, yycolumn, yyline, yytext());}
    ( "<=" )            {/*System.out.println(yytext()); */return new Symbol(sym.MENOR_IGUAL, yycolumn, yyline, yytext());} 
    ( ">=" )            {/*System.out.println(yytext()); */return new Symbol(sym.MAYOR_IGUAL, yycolumn, yyline, yytext());}
    ( not )             {/*System.out.println(yytext()); */return new Symbol(sym.NOT, yycolumn, yyline, yytext());}
    ( and )             {/*System.out.println(yytext()); */return new Symbol(sym.AND, yycolumn, yyline, yytext());}
    ( or )              {/*System.out.println(yytext()); */return new Symbol(sym.OR, yycolumn, yyline, yytext());}
    
    /* Palabras Reservadas */

    ( print )       {/*System.out.println(yytext()); */return new Symbol(sym.PRINT, yycolumn, yyline, yytext());}
    ( while )       {/*System.out.println(yytext()); */return new Symbol(sym.WHILE, yycolumn, yyline, yytext());}
    ( for )         {/*System.out.println(yytext()); */return new Symbol(sym.FOR, yycolumn, yyline, yytext());}
    ( in )          {/*System.out.println(yytext()); */return new Symbol(sym.IN, yycolumn, yyline, yytext());}
    ( range )       {/*System.out.println(yytext()); */return new Symbol(sym.RANGE, yycolumn, yyline, yytext());}
    ( if )          {/*System.out.println(yytext()); */return new Symbol(sym.IF, yycolumn, yyline, yytext());}
    ( else )        {/*System.out.println(yytext()); */return new Symbol(sym.ELSE, yycolumn, yyline, yytext());}
    ( elif )        {/*System.out.println(yytext()); */return new Symbol(sym.ELIF, yycolumn, yyline, yytext());}
    ( def )         {/*System.out.println(yytext()); */return new Symbol(sym.DEF, yycolumn, yyline, yytext());}
    ( return )      {/*System.out.println(yytext()); */return new Symbol(sym.RETURN, yycolumn, yyline, yytext());}

    /* Otros */

    ( "," )         {/*System.out.println(yytext()); */return new Symbol(sym.COMA, yycolumn, yyline, yytext());}
    ( ":" )         {/*System.out.println(yytext()); */return new Symbol(sym.DOS_PUNTOS, yycolumn, yyline, yytext());}
    ( "(" )         {/*System.out.println(yytext()); */return new Symbol(sym.PARENTESIS_A, yycolumn, yyline, yytext());}
    ( ")" )         {/*System.out.println(yytext()); */return new Symbol(sym.PARENTESIS_C, yycolumn, yyline, yytext());}
    
    /*************************************************Expresiones*******************************************/
            
    /* Numero */

    {D}+                                                {/*System.out.println(yytext()); */return new Symbol(sym.NUMERO, yycolumn, yyline, Integer.parseInt(yytext()));}

    /* Version */
    
    ({D}+ (("."){D}+))                                  {/*System.out.println(yytext()); */return new Symbol(sym.REAL, yycolumn, yyline, Float.parseFloat(yytext()));}

    /* Identificador */

    (({L} | "_")+({L} | {D} | "_")*)                    {/*System.out.println(yytext()); */return new Symbol(sym.ID, yycolumn, yyline, new String(yytext()));}

    /* Error */

    . {
            frameCTDOA.addErrores(
                      "\nError Lexico: "
              + "\n\tLinea #:                     << " + (yyline + 1) + " >> "
              + "\n\tColumna #:                   << " + (yycolumn + 1) + " >> "
              + "\n\tToken NO Reconocido:         << " + yytext() + " >> ");
    } 
}
