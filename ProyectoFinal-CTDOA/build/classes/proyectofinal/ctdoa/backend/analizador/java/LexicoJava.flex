package proyectofinal.ctdoa.backend.analizador.java;

import java_cup.runtime.*;
import proyectofinal.ctdoa.backend.manejadores.ManejadorCuartetos;
import proyectofinal.ctdoa.frontend.gui.FrameCTDOA;

%%
%class LexicoJava
%type java_cup.runtime.Symbol
%cup
%public
%cupdebug
%full
%line
%column
%char

LineTerminator = \r|\n|\r\n
InputCaracter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]

/* Comentarios */

Comment = {ComentarioTradicional} | {FinLineaComment} | {DocumentoComentado}
ComentarioTradicional = "/*" [^*] ~"*/" | "/*" "*"+ "/"
FinLineaComment = "//" {InputCaracter}* {LineTerminator}?
DocumentoComentado = "/**" {CommentContenido} "*"+ "/"
CommentContenido = ( [^*] | \*+ [^/*] )*

/* Imprimir en Cosola */
Console = ("System.out.print" | "System.out.println")

/* Entrada de Datos */

IInput = "intinput"
FInput = "floatinput"
CInput = "charinput"

/* Caracter */
Caracter = "\'" ([#-÷áéíóúAZÁÉÍÓÚÑñ!] | " ")? "\'"

/* Cadena */
Cadena = "\"" ([#-÷áéíóúAZÁÉÍÓÚÑñ!] | " ") * "\""

L = [a-záéíóúA-ZÁÉÍÓÚÑñ]

D = [0-9]

%{

    private ManejadorCuartetos mc = ManejadorCuartetos.getInstancia();
    private FrameCTDOA frameCTDOA;

    private Symbol symbol(int type, Object value){
        return new Symbol(type, yyline + 1, yycolumn + 1, value);
    }

    private Symbol symbol(int type){
        return new Symbol(type, yyline + 1, yycolumn + 1);
    }

    public void setFrameCTDOA(FrameCTDOA frameCTDOA) {
        this.frameCTDOA = frameCTDOA;
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
    
    {IInput}        {return new Symbol(sym.IINPUT, yycolumn, yyline, new String(yytext()));}
    {FInput}        {return new Symbol(sym.FINPUT, yycolumn, yyline, new String(yytext()));}
    {CInput}        {return new Symbol(sym.CINPUT, yycolumn, yyline, new String(yytext()));}

    /* MensajeConsola */
    
    ("System.out.print")        {return new Symbol(sym.CONSOLA, yycolumn, yyline, new String(yytext()));}  
    ("System.out.println")      {return new Symbol(sym.CONSOLA_LINEA, yycolumn, yyline, new String(yytext()));}  
    
    /* Cadena  */
    
    {Cadena}        {return new Symbol(sym.CADENA, yycolumn, yyline, new String(yytext()));}  

    /* Caracteres */ 
    
    {Caracter}      {return new Symbol(sym.CARACTER, yycolumn, yyline, new String(yytext()));}  

    /* Tipos de Datos */
    
    ( int )             {return new Symbol(sym.INT, yycolumn, yyline, yytext());}
    ( float )           {return new Symbol(sym.FLOAT, yycolumn, yyline, yytext());}
    ( char )            {return new Symbol(sym.CHAR, yycolumn, yyline, yytext());}

    /* Operadores - Aritmeticos  */
    
    ( "+" )             {return new Symbol(sym.MAS, yycolumn, yyline, yytext());}
    ( "-" )             {return new Symbol(sym.MENOS, yycolumn, yyline, yytext());}
    ( "*" )             {return new Symbol(sym.POR, yycolumn, yyline, yytext());}
    ( "/" )             {return new Symbol(sym.DIV, yycolumn, yyline, yytext());}
    ( "%" )             {return new Symbol(sym.MOD, yycolumn, yyline, yytext());}

    /* Operadores - Asignacion */
    
    ( "=" )             {return new Symbol(sym.IGUAL, yycolumn, yyline, yytext());}

    /* Operadores - Comparacion */

    ( "==" )            {return new Symbol(sym.IGUAL_IGUAL, yycolumn, yyline, yytext());}
    ( "!=" )            {return new Symbol(sym.DIFERENTE, yycolumn, yyline, yytext());}
    ( "<" )             {return new Symbol(sym.MENOR, yycolumn, yyline, yytext());}
    ( ">" )             {return new Symbol(sym.MAYOR, yycolumn, yyline, yytext());}
    ( "<=" )            {return new Symbol(sym.MENOR_IGUAL, yycolumn, yyline, yytext());} 
    ( ">=" )            {return new Symbol(sym.MAYOR_IGUAL, yycolumn, yyline, yytext());}
    ( "!" )             {return new Symbol(sym.NOT, yycolumn, yyline, yytext());}
    ( "&&" )            {return new Symbol(sym.AND, yycolumn, yyline, yytext());}
    ( "||" )            {return new Symbol(sym.OR, yycolumn, yyline, yytext());}

    /* Palabras Reservadas */

    ( while )       {return new Symbol(sym.WHILE, yycolumn, yyline, yytext());}
    ( do )          {return new Symbol(sym.DO, yycolumn, yyline, yytext());}
    ( break )       {return new Symbol(sym.BREAK, yycolumn, yyline, yytext());}
    ( this )         {return new Symbol(sym.THIS, yycolumn, yyline, yytext());}
    ( for )         {return new Symbol(sym.FOR, yycolumn, yyline, yytext());}
    ( "else if" )   {return new Symbol(sym.ELSE_IF, yycolumn, yyline, yytext());}
    ( if )          {return new Symbol(sym.IF, yycolumn, yyline, yytext());}
    ( else )        {return new Symbol(sym.ELSE, yycolumn, yyline, yytext());}
    ( switch )      {return new Symbol(sym.SWITCH, yycolumn, yyline, yytext());}
    ( default )     {return new Symbol(sym.DEFAULT, yycolumn, yyline, yytext());}
    ( return )      {return new Symbol(sym.RETURN, yycolumn, yyline, yytext());}
    ( case )        {return new Symbol(sym.CASE, yycolumn, yyline, yytext());}
    ( public )      {return new Symbol(sym.PUBLIC, yycolumn, yyline, yytext());}
    ( void )        {return new Symbol(sym.VOID, yycolumn, yyline, yytext());}
    ( class )       {return new Symbol(sym.CLASS, yycolumn, yyline, yytext());}

    /* Otros */

    ( "++" )        {return new Symbol(sym.MAS_MAS, yycolumn, yyline, yytext());}
    ( "--" )        {return new Symbol(sym.MENOS_MENOS, yycolumn, yyline, yytext());}
    ( ";" )         {return new Symbol(sym.PUNTO_COMA, yycolumn, yyline, yytext());}
    ( ":" )         {return new Symbol(sym.DOS_PUNTOS, yycolumn, yyline, yytext());}
    ( "." )         {return new Symbol(sym.PUNTO, yycolumn, yyline, yytext());}
    ( "," )         {return new Symbol(sym.COMA, yycolumn, yyline, yytext());}
    ( "{" )         {return new Symbol(sym.CORCHETE_A, yycolumn, yyline, yytext());}
    ( "}" )         {return new Symbol(sym.CORCHETE_C, yycolumn, yyline, yytext());}
    ( "(" )         {return new Symbol(sym.PARENTESIS_A, yycolumn, yyline, yytext());}
    ( ")" )         {return new Symbol(sym.PARENTESIS_C, yycolumn, yyline, yytext());}
    
    /*************************************************Expresiones*******************************************/
            
    /* Numero */

    {D}+                                                {return new Symbol(sym.NUMERO, yycolumn, yyline, Integer.parseInt(yytext()));}

    /* Real */
    
    ({D}+ (("."){D}+))                                  {return new Symbol(sym.REAL, yycolumn, yyline, Float.parseFloat(yytext()));}

    /* Identificador */

    (({L} | "_")+({L} | {D} | "_")*)                    {return new Symbol(sym.ID, yycolumn, yyline, new String(yytext()));}

   /* Error */

    . {
        frameCTDOA.addErrores(
                      "\nError Lexico: "
              + "\n\tLinea #:                     << " + (yyline + 1) + " >> "
              + "\n\tColumna #:                   << " + (yycolumn + 1) + " >> "
              + "\n\tToken NO Reconocido:         << " + yytext() + " >> ");
    } 
}