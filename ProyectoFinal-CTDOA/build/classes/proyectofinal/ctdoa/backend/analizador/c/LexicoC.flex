package proyectofinal.ctdoa.backend.analizador.c;

import java_cup.runtime.*;
import proyectofinal.ctdoa.backend.manejadores.*;

%%
%class LexicoC
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

/* Entrada de Datos */

L = [a-záéíóúA-ZÁÉÍÓÚÑñ]

D = [0-9]

/* Codigos */

/* VB */
    
CodigoVB = "%%VB" ([^%])+

/* Java */

CodigoJava = "%%JAVA" [^%]+

/* Python */

CodigoPython = "%%PY" [^%]+

/* Librerias */

LibraryVB = "\"" "VB" "\"" 
LibraryJava = "\"" "JAVA.*" "\"" 
LibraryPython = "\"" "PY" "\"" 
LibraryC = "<" [A-Za-z0-9áéíóúA-ZÁÉÍÓÚÑñ]+ ((".") [A-Za-z0-9áéíóúA-ZÁÉÍÓÚÑñ]+)* ">"
LibraryClases = "\"" "JAVA" ((".")({L}|{D}|"_")+){1}  "\""

/* Caracter */
Caracter = "\'" ([#-÷áéíóúA-ZÁÉÍÓÚÑñ!] | "\\b" | "\\n" | "\\\"" |"!") ? "\'"

%{   
    private ManejadorCuartetos mc = ManejadorCuartetos.getInstancia();

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

    /* Tipo de Dato */

    ( int )                  {return new Symbol(sym.INT, yycolumn, yyline, new String(yytext()));}
    ( float )                {return new Symbol(sym.FLOAT, yycolumn, yyline, new String(yytext()));}
    ( char )                 {return new Symbol(sym.CHAR, yycolumn, yyline, new String(yytext()));}

    /* Codigos */
    
    { CodigoVB }            {return new Symbol(sym.COD_VB, yycolumn, yyline, new String(yytext()));}
    { CodigoPython }        {return new Symbol(sym.COD_PY, yycolumn, yyline, new String(yytext()));}
    { CodigoJava }          {return new Symbol(sym.COD_JAVA, yycolumn, yyline, new String(yytext()));}
    
    /* Comentario */

    { Comment }         {mc.addComentario(yytext());}

    /* Getch */

    ( "getch" )          {return new Symbol(sym.GETCH, yycolumn, yyline, new String(yytext()));}

    /* Include */

    ("#include")    {return new Symbol(sym.INCLUDE, yycolumn, yyline, new String(yytext()));}  

    /* Librerias */

    {LibraryClases}         {return new Symbol(sym.LIBRERIA_CLASES, yycolumn, yyline, new String(yytext()));}  
    {LibraryVB}             {return new Symbol(sym.LIBRERIA_VB, yycolumn, yyline, new String(yytext()));}  
    {LibraryJava}           {
                                String regresar = yytext().replaceAll("\"", "");
                                regresar = regresar.replaceAll("JAVA.", "");
                                return new Symbol(sym.LIBRERIA_JAVA, yycolumn, yyline, regresar);        
                            }  
    {LibraryPython}         {return new Symbol(sym.LIBRERIA_PYTHON, yycolumn, yyline, new String(yytext()));}  
    {LibraryC}              {return new Symbol(sym.LIBRERIA_C, yycolumn, yyline, new String(yytext()));}  

    /* Caracteres */ 
    
    {Caracter}      {return new Symbol(sym.CARACTER, yycolumn, yyline, new String(yytext()));}  

    /* Llamado de Funciones */

    ( VB )             {return new Symbol(sym.VB, yycolumn, yyline, yytext());}
    ( PY )             {return new Symbol(sym.PY, yycolumn, yyline, yytext());}
    ( JAVA )           {return new Symbol(sym.JAVA, yycolumn, yyline, yytext());}

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

    /* Scanf - printf */

    ( scanf )           {return new Symbol(sym.SCANF, yycolumn, yyline, yytext());}
    ( "%d" )            {return new Symbol(sym.LEER_INT, yycolumn, yyline, yytext());}
    ( "%c" )            {return new Symbol(sym.LEER_CHAR, yycolumn, yyline, yytext());}
    ( "%f" )            {return new Symbol(sym.LEER_FLOAT, yycolumn, yyline, yytext());}

    /* Palabras Reservadas */

    ( "%%PROGRAMA" )    {return new Symbol(sym.PROGRAMA, yycolumn, yyline, yytext());}
    ( clrscr )          {return new Symbol(sym.CLRSCR, yycolumn, yyline, yytext());}
    ( void )            {return new Symbol(sym.VOID, yycolumn, yyline, yytext());}
    ( main )            {return new Symbol(sym.MAIN, yycolumn, yyline, yytext());}
    ( const )           {return new Symbol(sym.CONST, yycolumn, yyline, yytext());}
    ( if )              {return new Symbol(sym.IF, yycolumn, yyline, yytext());}
    ( else )            {return new Symbol(sym.ELSE, yycolumn, yyline, yytext());}
    ( "else if")        {return new Symbol(sym.ELSE_IF, yycolumn, yyline, yytext());}
    ( switch )          {return new Symbol(sym.SWITCH, yycolumn, yyline, yytext());}
    ( case )            {return new Symbol(sym.CASE, yycolumn, yyline, yytext());}
    ( default )         {return new Symbol(sym.DEFAULT, yycolumn, yyline, yytext());}
    ( break )           {return new Symbol(sym.BREAK, yycolumn, yyline, yytext());}
    ( for )             {return new Symbol(sym.FOR, yycolumn, yyline, yytext());}
    ( while )           {return new Symbol(sym.WHILE, yycolumn, yyline, yytext());}
    ( do )              {return new Symbol(sym.DO, yycolumn, yyline, yytext());}
    ( printf )          {return new Symbol(sym.PRINTF, yycolumn, yyline, yytext());}
    
    /* Otros */

    ( "++" )        {return new Symbol(sym.MAS_MAS, yycolumn, yyline, yytext());}
    ( "--" )        {return new Symbol(sym.MENOS_MENOS, yycolumn, yyline, yytext());}
    ( "&" )         {return new Symbol(sym.AMPERSAND, yycolumn, yyline, yytext());}
    ( "\"" )        {return new Symbol(sym.COMILLAS, yycolumn, yyline, yytext());}
    ( "." )         {return new Symbol(sym.PUNTO, yycolumn, yyline, yytext());}
    ( "," )         {return new Symbol(sym.COMA, yycolumn, yyline, yytext());}
    ( ";" )         {return new Symbol(sym.PUNTO_COMA, yycolumn, yyline, yytext());}
    ( ":" )         {return new Symbol(sym.DOS_PUNTOS, yycolumn, yyline, yytext());}
    ( "[" )         {return new Symbol(sym.LLAVE_A, yycolumn, yyline, yytext());}
    ( "]" )         {return new Symbol(sym.LLAVE_C, yycolumn, yyline, yytext());}
    ( "{" )         {return new Symbol(sym.CORCHETE_A, yycolumn, yyline, yytext());}
    ( "}" )         {return new Symbol(sym.CORCHETE_C, yycolumn, yyline, yytext());}
    ( "(" )         {return new Symbol(sym.PARENTESIS_A, yycolumn, yyline, yytext());}
    ( ")" )         {return new Symbol(sym.PARENTESIS_C, yycolumn, yyline, yytext());}
    
    /*************************************************Expresiones*******************************************/
            
    /* Numero */

    {D}+                                                {return new Symbol(sym.NUMERO, yycolumn, yyline, Integer.parseInt(yytext()));}

    /* Version */
    
    ({D}+ (("."){D}+))                                  {return new Symbol(sym.REAL, yycolumn, yyline, Float.parseFloat(yytext()));}

    /* Identificador */

    (({L} | "_")+({L} | {D} | "_")*)                    {return new Symbol(sym.ID, yycolumn, yyline, new String(yytext()));}

    /* Error */
    
    .                                                   {return new Symbol(sym.ID, yycolumn, yyline, new String(yytext()));} 
}