package proyectofinal.ctdoa.backend.analizador.visual_basic;

import java_cup.runtime.*;
import proyectofinal.ctdoa.backend.manejadores.*;
import proyectofinal.ctdoa.frontend.gui.FrameCTDOA;

%%
%class LexicoVisualBasic
%type java_cup.runtime.Symbol
%cup
%public
%cupdebug
%full
%line
%column
%char
%ignorecase

LineTerminator = \r
InputCaracter = [^\r\n]
WhiteSpace =  \r | [ \t\f]

/* Comentarios */

Comment = {ComentarioTradicional} | {FinLineaComment} | {DocumentoComentado}
ComentarioTradicional = "/*" [^*] ~"*/" | "/*" "*"+ "/"
FinLineaComment = "//" {InputCaracter}* ({LineTerminator} | "\n" | "\r" "\n")?
DocumentoComentado = "/**" {CommentContenido} "*"+ "/"
CommentContenido = ( [^*] | \*+ [^/*] )*

LineNew = "\n"+

/* Imprimir en Cosola */

/* Entrada de Datos */

IInput = "intinput"
FInput = "floatinput"
CInput = "charinput"

/* Caracter */
Caracter = "\'"[#-÷áéíóúAZÁÉÍÓÚÑñ!]? "\'"

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

    public void setFrameCTDOA(FrameCTDOA frameCTDOA) {
        this.frameCTDOA = frameCTDOA;
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
    { Comment }         { mc.addComentario(yytext()); }

    /* Solicitud de Datos */
    
    {IInput}        {return new Symbol(sym.IINPUT, yycolumn, yyline, new String(yytext()));}
    {FInput}        {return new Symbol(sym.FINPUT, yycolumn, yyline, new String(yytext()));}
    {CInput}        {return new Symbol(sym.CINPUT, yycolumn, yyline, new String(yytext()));}

    /* MensajeConsola */
    
    ("Console.Write")           {return new Symbol(sym.CONSOLA, yycolumn, yyline, new String(yytext()));}  
    ("Console.WriteLine")       {return new Symbol(sym.CONSOLA_LINEA, yycolumn, yyline, new String(yytext()));}  

    /* Cadena  */
    
    {Cadena}        {return new Symbol(sym.CADENA, yycolumn, yyline, yytext().replaceAll("\"", ""));}  

    /* Caracteres */ 
    
    {Caracter}      {return new Symbol(sym.CARACTER, yycolumn, yyline, yytext().replaceAll("\'", ""));}

    /* Tipos de Datos */
    
    ( Integer )         {return new Symbol(sym.INTEGER, yycolumn, yyline, yytext());}
    ( Decimal )         {return new Symbol(sym.DECIMAL, yycolumn, yyline, yytext());}
    ( Char )            {return new Symbol(sym.CHAR, yycolumn, yyline, yytext());}

    /* Operadores - Aritmeticos  */
    
    ( "+" )             {return new Symbol(sym.MAS, yycolumn, yyline, yytext());}
    ( "-" )             {return new Symbol(sym.MENOS, yycolumn, yyline, yytext());}
    ( "*" )             {return new Symbol(sym.POR, yycolumn, yyline, yytext());}
    ( "/" )             {return new Symbol(sym.DIV, yycolumn, yyline, yytext());}
    ( MOD )             {return new Symbol(sym.MODULO, yycolumn, yyline, yytext());}

    /* Operadores - Asignacion */
    
    ( "=" )             {return new Symbol(sym.IGUAL, yycolumn, yyline, yytext());}

    /* Operadores - Comparacion */

    ( "<>" )            {return new Symbol(sym.DISTINTO, yycolumn, yyline, yytext());}
    ( "<" )             {return new Symbol(sym.MENOR, yycolumn, yyline, yytext());}
    ( ">" )             {return new Symbol(sym.MAYOR, yycolumn, yyline, yytext());}
    ( "<=" )            {return new Symbol(sym.MENOR_IGUAL, yycolumn, yyline, yytext());} 
    ( "=<" )            {return new Symbol(sym.MENOR_IGUAL, yycolumn, yyline, yytext());}
    ( "=>" )            {return new Symbol(sym.MAYOR_IGUAL, yycolumn, yyline, yytext());}
    ( ">=" )            {return new Symbol(sym.MAYOR_IGUAL, yycolumn, yyline, yytext());}
    ( Not )             {return new Symbol(sym.NOT, yycolumn, yyline, yytext());}
    ( And )             {return new Symbol(sym.AND, yycolumn, yyline, yytext());}
    ( Or )              {return new Symbol(sym.OR, yycolumn, yyline, yytext());}
    
    /* Operadores - Concatenar */

    ( "&" )             {return new Symbol(sym.AMPERSAND, yycolumn, yyline, yytext());}

    /* Palabras Reservadas */

    ( Dim )         {return new Symbol(sym.DIM, yycolumn, yyline, yytext());}
    ( As )          {return new Symbol(sym.AS, yycolumn, yyline, yytext());}
    ( End )         {return new Symbol(sym.END, yycolumn, yyline, yytext());}
    ( While )       {return new Symbol(sym.WHILE, yycolumn, yyline, yytext());}
    ( Do )          {return new Symbol(sym.DO, yycolumn, yyline, yytext());}
    ( Until )       {return new Symbol(sym.UNTIL, yycolumn, yyline, yytext());}
    ( Loop )        {return new Symbol(sym.LOOP, yycolumn, yyline, yytext());}
    ( To )          {return new Symbol(sym.TO, yycolumn, yyline, yytext());}
    ( For )         {return new Symbol(sym.FOR, yycolumn, yyline, yytext());}
    ( Step )        {return new Symbol(sym.STEP, yycolumn, yyline, yytext());}
    ( Next )        {return new Symbol(sym.NEXT, yycolumn, yyline, yytext());}
    ( If )          {return new Symbol(sym.IF, yycolumn, yyline, yytext());}
    ( Then )        {return new Symbol(sym.THEN, yycolumn, yyline, yytext());}
    ( ElseIf )      {return new Symbol(sym.ELSEIF, yycolumn, yyline, yytext());}
    ( Else )        {return new Symbol(sym.ELSE, yycolumn, yyline, yytext());}
    ( Select )      {return new Symbol(sym.SELECT, yycolumn, yyline, yytext());}
    ( Case )        {return new Symbol(sym.CASE, yycolumn, yyline, yytext());}
    ( Public )      {return new Symbol(sym.PUBLIC, yycolumn, yyline, yytext());}
    ( Function )    {return new Symbol(sym.FUNCTION, yycolumn, yyline, yytext());}
    ( Sub )         {return new Symbol(sym.SUB, yycolumn, yyline, yytext());}
    ( Return )      {return new Symbol(sym.RETURN, yycolumn, yyline, yytext());}

    /* Otros */

    ( "," )         {return new Symbol(sym.COMA, yycolumn, yyline, yytext());}
    ( "(" )         {return new Symbol(sym.PARENTESIS_A, yycolumn, yyline, yytext());}
    ( ")" )         {return new Symbol(sym.PARENTESIS_C, yycolumn, yyline, yytext());}
    { LineNew }     {return new Symbol(sym.SALTO, yycolumn, yyline, "Salto");}
    
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