/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinal.ctdoa;;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author bryan
 */
public class GeneradorArchivo {
    
    private static GeneradorArchivo generadorArchivos;
    public static final String PARSER = "-parser";
    public static final String SYM = "sym.java";
    
    public static final String PATH_VB = "src/proyectofinal/ctdoa/backend/analizador/visual_basic/";
    public static final String PATH_JAVA = "src/proyectofinal/ctdoa/backend/analizador/java/";
    public static final String PATH_PYTHON = "src/proyectofinal/ctdoa/backend/analizador/python/";
    public static final String PATH_C = "src/proyectofinal/ctdoa/backend/analizador/c/";
    
    //Constantes para generar archivos del analizador lexico y sintactico de la sintaxis de VB
    public static final String PATH_FLEX_VB = PATH_VB + "LexicoVisualBasic.flex";
    public static final String PATH_CUP_VB = PATH_VB + "SintacticoVisualBasic.cup";
    public static final String PATH_SYM_VB = PATH_VB + "sym.java";
    public static final String PATH_SINTAX_VB = PATH_VB + "SintacticoVisualBasic.java";
    public static final String NOMBRE_SINTAX_VB = "SintacticoVisualBasic";
    public static final String NOMBRE_CLASE_SINTAX_VB = "SintacticoVisualBasic.java";
    
    //Constantes para generar archivos del analizador lexico y sintactico de la sintaxis de Java
    public static final String PATH_FLEX_JAVA = PATH_JAVA + "LexicoJava.flex";
    public static final String PATH_CUP_JAVA = PATH_JAVA + "SintacticoJava.cup";
    public static final String PATH_SYM_JAVA = PATH_JAVA + "sym.java";
    public static final String PATH_SINTAX_JAVA = PATH_JAVA + "SintacticoJava.java";
    public static final String NOMBRE_SINTAX_JAVA = "SintacticoJava";
    public static final String NOMBRE_CLASE_SINTAX_JAVA = "SintacticoJava.java";
    
    //Constantes para generar archivos del analizador lexico y sintactico de la sintaxis de Python
    public static final String PATH_FLEX_PYTHON = PATH_PYTHON + "LexicoPython.flex";
    public static final String PATH_CUP_PYTHON = PATH_PYTHON + "SintacticoPython.cup";
    public static final String PATH_SYM_PYTHON = PATH_PYTHON + "sym.java";
    public static final String PATH_SINTAX_PYTHON = PATH_PYTHON + "SintacticoPython.java";
    public static final String NOMBRE_SINTAX_PYTHON = "SintacticoPython";
    public static final String NOMBRE_CLASE_SINTAX_PYTHON = "SintacticoPython.java";

    //Constantes para generar archivos del analizador lexico y sintactico de la sintaxis de C
    public static final String PATH_FLEX_C = PATH_C + "LexicoC.flex";
    public static final String PATH_CUP_C = PATH_C + "SintacticoC.cup";
    public static final String PATH_SYM_C = PATH_C + "sym.java";
    public static final String PATH_SINTAX_C = PATH_C + "SintacticoC.java";
    public static final String NOMBRE_SINTAX_C = "SintacticoC";
    public static final String NOMBRE_CLASE_SINTAX_C = "SintacticoC.java";

    private GeneradorArchivo() {}
    
    /* Metodo que retorna una instancia si no se a generado ninguna realiza una nueva */
    public static GeneradorArchivo getGeneradorArchivos() {
        if (generadorArchivos == null) {
            generadorArchivos = new GeneradorArchivo();
        } 
        return generadorArchivos;
    }
    
    /* Metodo que prepara los recurso para poder crear un nuevo archivo de flex y cup*/
    private void gen(String rutaFlex, String rutaCup, String rutaSym, String rutaSintaxJava, String nombreSintax, String nombreClase) throws Exception {
        String[] rutaSintactico = {PARSER, nombreSintax, rutaCup};
        generador(rutaFlex, rutaSintactico, rutaSym, SYM, rutaSintaxJava, nombreClase);
    }
    
    /* Metodo que genera las clases de los anlizadores lexcio y sintactico */
    private void generador(String rutaLexicoProyecto, String[] rutaSintax, String rutaSymTXT, String nombreSym, String rutaSint, String nombreSin) throws IOException, Exception{
        File archivo;
        archivo = new File(rutaLexicoProyecto);
        JFlex.Main.generate(archivo);
        java_cup.Main.main(rutaSintax);
        Path rutaSym = Paths.get(rutaSymTXT);
        if (Files.exists(rutaSym)) {
            Files.delete(rutaSym);
        }
        Files.move(
                Paths.get(nombreSym), 
                Paths.get(rutaSymTXT)
        );
        Path rutaSin = Paths.get(rutaSint);
        if (Files.exists(rutaSin)) {
            Files.delete(rutaSin);
        }
        Files.move(
                Paths.get(nombreSin), 
                Paths.get(rutaSint)
        );
    }
    
    /* Metodo que genera losa archivos de cada analizador
    ** 1. Visual Basic
    ** 2. Java
    ** 3. Python
    ** 4. C
    */
    public static void generador(int caso) throws Exception{
        GeneradorArchivo gArchivos = getGeneradorArchivos();
        switch (caso) {
            case 1:
                gArchivos.gen(GeneradorArchivo.PATH_FLEX_VB, GeneradorArchivo.PATH_CUP_VB, GeneradorArchivo.PATH_SYM_VB, 
                GeneradorArchivo.PATH_SINTAX_VB, GeneradorArchivo.NOMBRE_SINTAX_VB, GeneradorArchivo.NOMBRE_CLASE_SINTAX_VB);
                break;
            case 2:
                gArchivos.gen(GeneradorArchivo.PATH_FLEX_JAVA, GeneradorArchivo.PATH_CUP_JAVA, GeneradorArchivo.PATH_SYM_JAVA, 
                GeneradorArchivo.PATH_SINTAX_JAVA, GeneradorArchivo.NOMBRE_SINTAX_JAVA, GeneradorArchivo.NOMBRE_CLASE_SINTAX_JAVA);
                break;
            case 3:
                gArchivos.gen(GeneradorArchivo.PATH_FLEX_PYTHON, GeneradorArchivo.PATH_CUP_PYTHON, GeneradorArchivo.PATH_SYM_PYTHON, 
                GeneradorArchivo.PATH_SINTAX_PYTHON, GeneradorArchivo.NOMBRE_SINTAX_PYTHON, GeneradorArchivo.NOMBRE_CLASE_SINTAX_PYTHON);
                break;
            case 4:
                gArchivos.gen(GeneradorArchivo.PATH_FLEX_C, GeneradorArchivo.PATH_CUP_C, GeneradorArchivo.PATH_SYM_C, 
                GeneradorArchivo.PATH_SINTAX_C, GeneradorArchivo.NOMBRE_SINTAX_C, GeneradorArchivo.NOMBRE_CLASE_SINTAX_C);
                break;
            case 5:
                break;
        }
        
    } 
}
