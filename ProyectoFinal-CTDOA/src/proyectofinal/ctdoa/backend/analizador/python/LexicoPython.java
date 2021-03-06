/* The following code was generated by JFlex 1.4.3 on 14/10/20 23:35 */

package proyectofinal.ctdoa.backend.analizador.python;

import java_cup.runtime.*;
import java.util.Stack;
import proyectofinal.ctdoa.backend.manejadores.*;
import proyectofinal.ctdoa.frontend.gui.FrameCTDOA;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 14/10/20 23:35 from the specification file
 * <tt>src/proyectofinal/ctdoa/backend/analizador/python/LexicoPython.flex</tt>
 */
public class LexicoPython implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = {
     0,  0,  0,  0,  0,  0,  0,  0, 18, 18,  2,  0,  3,  1,  0,  0, 
     0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0, 
    19, 25, 24, 21, 21, 34, 21, 20, 45, 46,  5, 32, 43, 33, 47,  4, 
    28, 28, 28, 28, 28, 28, 28, 28, 28, 28, 44, 21, 36, 35, 37, 21, 
    21, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 
    27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 27, 21, 22, 21, 21, 27, 
    21, 14, 23, 15, 38, 40, 11, 41, 16,  6, 27, 27, 12, 27,  7, 13, 
     9, 27, 17, 42,  8, 10, 27, 39, 27, 27, 27, 21, 21, 21, 21, 21, 
    30, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 31, 21, 21, 21, 21, 
    21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 
    21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 
    21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 
    21, 27, 21, 21, 21, 21, 21, 21, 21, 27, 21, 21, 21, 27, 21, 21, 
    21, 27, 21, 27, 21, 21, 21, 21, 21, 21, 27, 21, 21, 21, 21, 21, 
    21, 27, 29, 21, 21, 21, 21, 21, 21, 27, 21, 21, 21, 27, 21, 21, 
    21, 27, 21, 27, 21, 21, 21, 21,  0,  0, 26,  0,  0,  0,  0,  0
  };

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\1\1\2\1\1\1\3\1\4\1\5\11\6\1\1"+
    "\3\2\1\7\1\2\1\10\1\11\1\12\1\13\1\14"+
    "\1\15\3\6\1\16\1\17\1\20\1\21\1\3\1\22"+
    "\1\0\1\23\1\24\4\6\1\25\4\6\1\0\1\26"+
    "\2\0\1\27\1\30\2\0\1\31\1\32\1\33\1\34"+
    "\3\6\2\22\2\0\1\6\1\35\2\6\1\36\1\37"+
    "\3\6\1\26\1\40\1\1\1\41\3\6\1\0\1\22"+
    "\6\6\1\0\1\6\1\42\1\43\1\6\1\44\2\6"+
    "\1\45\1\6\1\46\3\6\1\47\3\6\1\50\3\6"+
    "\1\51\1\52";

  private static int [] zzUnpackAction() {
    int [] result = new int[115];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\60\0\60\0\140\0\220\0\60\0\300\0\360"+
    "\0\u0120\0\u0150\0\u0180\0\u01b0\0\u01e0\0\u0210\0\u0240\0\u0270"+
    "\0\u02a0\0\u02d0\0\u0300\0\u0330\0\u0360\0\60\0\60\0\60"+
    "\0\u0390\0\u03c0\0\u03f0\0\u0420\0\u0450\0\u0480\0\60\0\60"+
    "\0\60\0\60\0\u04b0\0\u04e0\0\u0510\0\u0540\0\u0120\0\u0570"+
    "\0\u05a0\0\u05d0\0\u0600\0\u0120\0\u0630\0\u0660\0\u0690\0\u06c0"+
    "\0\u06f0\0\u06f0\0\u0720\0\u02d0\0\60\0\60\0\u0750\0\u0780"+
    "\0\60\0\60\0\60\0\60\0\u07b0\0\u07e0\0\u0810\0\u0840"+
    "\0\60\0\u0870\0\u08a0\0\u08d0\0\u0120\0\u0900\0\u0930\0\u0120"+
    "\0\u0120\0\u0960\0\u0990\0\u09c0\0\60\0\u0750\0\u09f0\0\u0120"+
    "\0\u0a20\0\u0a50\0\u0a80\0\u0ab0\0\u0870\0\u0ae0\0\u0b10\0\u0b40"+
    "\0\u0b70\0\u0ba0\0\u0bd0\0\u0360\0\u0c00\0\u0120\0\u0120\0\u0c30"+
    "\0\u0120\0\u0c60\0\u0c90\0\u0120\0\u0cc0\0\u0120\0\u0cf0\0\u0d20"+
    "\0\u0d50\0\u0120\0\u0d80\0\u0db0\0\u0de0\0\u0120\0\u0e10\0\u0e40"+
    "\0\u0e70\0\u0120\0\u0120";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[115];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\2\1\3\1\4\1\3\1\5\1\6\1\7\1\10"+
    "\1\11\1\12\1\11\1\13\1\11\1\14\1\15\1\16"+
    "\1\11\1\17\1\2\1\20\1\21\2\2\1\11\1\22"+
    "\1\23\2\11\1\24\1\25\2\2\1\26\1\27\1\30"+
    "\1\31\1\32\1\33\1\34\1\35\1\36\2\11\1\37"+
    "\1\40\1\41\1\42\1\2\62\0\1\4\17\0\2\43"+
    "\40\0\1\44\1\45\60\0\1\11\1\46\3\11\1\47"+
    "\6\11\5\0\1\11\2\0\3\11\11\0\5\11\13\0"+
    "\7\11\1\50\4\11\5\0\1\11\2\0\3\11\11\0"+
    "\5\11\13\0\14\11\5\0\1\11\2\0\3\11\11\0"+
    "\5\11\13\0\13\11\1\51\5\0\1\11\2\0\3\11"+
    "\11\0\5\11\13\0\6\11\1\52\1\53\4\11\5\0"+
    "\1\11\2\0\3\11\11\0\5\11\13\0\13\11\1\54"+
    "\5\0\1\11\2\0\3\11\11\0\5\11\13\0\1\11"+
    "\1\55\12\11\5\0\1\11\2\0\3\11\11\0\5\11"+
    "\13\0\12\11\1\56\1\11\5\0\1\11\2\0\3\11"+
    "\11\0\5\11\13\0\10\11\1\57\3\11\5\0\1\11"+
    "\2\0\3\11\11\0\2\11\1\60\2\11\30\0\1\20"+
    "\40\0\16\61\2\0\1\62\1\61\1\63\1\61\1\0"+
    "\1\61\1\0\25\61\4\0\16\64\1\0\5\64\1\65"+
    "\27\64\43\0\1\66\50\0\1\24\22\0\1\67\36\0"+
    "\1\70\64\0\1\71\57\0\1\72\1\0\1\73\55\0"+
    "\1\74\22\0\14\11\5\0\1\11\2\0\3\11\11\0"+
    "\2\11\1\75\2\11\13\0\12\11\1\76\1\11\5\0"+
    "\1\11\2\0\3\11\11\0\5\11\13\0\6\11\1\77"+
    "\5\11\5\0\1\11\2\0\3\11\11\0\5\11\27\0"+
    "\2\43\34\0\1\44\1\100\1\101\55\44\5\102\1\103"+
    "\52\102\6\0\2\11\1\104\11\11\5\0\1\11\2\0"+
    "\3\11\11\0\5\11\13\0\2\11\1\105\11\11\5\0"+
    "\1\11\2\0\3\11\11\0\5\11\13\0\1\106\13\11"+
    "\5\0\1\11\2\0\3\11\11\0\5\11\13\0\7\11"+
    "\1\107\4\11\5\0\1\11\2\0\3\11\11\0\5\11"+
    "\13\0\13\11\1\110\5\0\1\11\2\0\3\11\11\0"+
    "\5\11\13\0\14\11\5\0\1\11\2\0\3\11\11\0"+
    "\1\111\4\11\13\0\10\11\1\112\3\11\5\0\1\11"+
    "\2\0\3\11\11\0\5\11\13\0\1\11\1\113\12\11"+
    "\5\0\1\11\2\0\3\11\11\0\5\11\13\0\2\11"+
    "\1\114\11\11\5\0\1\11\2\0\3\11\11\0\5\11"+
    "\31\0\1\115\42\0\1\61\14\0\1\115\2\0\2\61"+
    "\63\0\1\116\62\0\1\117\26\0\5\11\1\120\6\11"+
    "\5\0\1\11\2\0\3\11\11\0\5\11\13\0\1\121"+
    "\13\11\5\0\1\11\2\0\3\11\11\0\5\11\13\0"+
    "\1\122\13\11\5\0\1\11\2\0\3\11\11\0\4\11"+
    "\1\123\7\0\1\101\55\0\5\102\1\124\56\102\1\125"+
    "\1\124\52\102\6\0\1\126\13\11\5\0\1\11\2\0"+
    "\3\11\11\0\5\11\13\0\1\11\1\127\12\11\5\0"+
    "\1\11\2\0\3\11\11\0\5\11\13\0\10\11\1\130"+
    "\3\11\5\0\1\11\2\0\3\11\11\0\5\11\13\0"+
    "\13\11\1\131\5\0\1\11\2\0\3\11\11\0\5\11"+
    "\13\0\14\11\5\0\1\11\2\0\3\11\11\0\3\11"+
    "\1\132\1\11\13\0\4\11\1\133\7\11\5\0\1\11"+
    "\2\0\3\11\11\0\5\11\42\0\1\134\30\0\6\11"+
    "\1\135\5\11\5\0\1\11\2\0\3\11\11\0\5\11"+
    "\13\0\5\11\1\136\6\11\5\0\1\11\2\0\3\11"+
    "\11\0\5\11\13\0\14\11\5\0\1\11\2\0\3\11"+
    "\11\0\2\11\1\137\2\11\5\0\4\102\1\101\1\124"+
    "\52\102\6\0\1\11\1\140\12\11\5\0\1\11\2\0"+
    "\3\11\11\0\5\11\13\0\2\11\1\141\11\11\5\0"+
    "\1\11\2\0\3\11\11\0\5\11\13\0\2\11\1\142"+
    "\11\11\5\0\1\11\2\0\3\11\11\0\5\11\13\0"+
    "\1\143\13\11\5\0\1\11\2\0\3\11\11\0\5\11"+
    "\13\0\14\11\5\0\1\11\2\0\3\11\11\0\2\11"+
    "\1\144\2\11\13\0\13\11\1\145\5\0\1\11\2\0"+
    "\3\11\11\0\5\11\13\0\14\11\5\0\1\11\2\0"+
    "\3\11\11\0\2\11\1\146\2\11\13\0\3\11\1\147"+
    "\10\11\5\0\1\11\2\0\3\11\11\0\5\11\13\0"+
    "\1\150\13\11\5\0\1\11\2\0\3\11\11\0\5\11"+
    "\13\0\1\11\1\151\12\11\5\0\1\11\2\0\3\11"+
    "\11\0\5\11\13\0\1\11\1\152\12\11\5\0\1\11"+
    "\2\0\3\11\11\0\5\11\13\0\4\11\1\153\7\11"+
    "\5\0\1\11\2\0\3\11\11\0\5\11\13\0\1\11"+
    "\1\154\12\11\5\0\1\11\2\0\3\11\11\0\5\11"+
    "\13\0\3\11\1\155\10\11\5\0\1\11\2\0\3\11"+
    "\11\0\5\11\13\0\2\11\1\156\11\11\5\0\1\11"+
    "\2\0\3\11\11\0\5\11\13\0\3\11\1\157\10\11"+
    "\5\0\1\11\2\0\3\11\11\0\5\11\13\0\4\11"+
    "\1\160\7\11\5\0\1\11\2\0\3\11\11\0\5\11"+
    "\13\0\4\11\1\161\7\11\5\0\1\11\2\0\3\11"+
    "\11\0\5\11\13\0\2\11\1\162\11\11\5\0\1\11"+
    "\2\0\3\11\11\0\5\11\13\0\2\11\1\163\11\11"+
    "\5\0\1\11\2\0\3\11\11\0\5\11\5\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[3744];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\1\1\2\11\2\1\1\11\17\1\3\11\6\1\4\11"+
    "\2\1\1\0\13\1\1\0\1\1\2\0\2\11\2\0"+
    "\4\11\4\1\1\11\2\0\11\1\1\11\6\1\1\0"+
    "\7\1\1\0\27\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[115];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
   
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



  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public LexicoPython(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public LexicoPython(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 38: 
          { /*System.out.println(yytext()); */return new Symbol(sym.WHILE, yycolumn, yyline, yytext());
          }
        case 43: break;
        case 25: 
          { /*System.out.println(yytext()); */return new Symbol(sym.IGUAL_IGUAL, yycolumn, yyline, yytext());
          }
        case 44: break;
        case 16: 
          { /*System.out.println(yytext()); */return new Symbol(sym.PARENTESIS_A, yycolumn, yyline, yytext());
          }
        case 45: break;
        case 22: 
          { /*System.out.println(yytext()); */return new Symbol(sym.CARACTER, yycolumn, yyline, new String(yytext()));
          }
        case 46: break;
        case 21: 
          { /*System.out.println(yytext()); */return new Symbol(sym.OR, yycolumn, yyline, yytext());
          }
        case 47: break;
        case 19: 
          { /*System.out.println(yytext()); */return new Symbol(sym.IN, yycolumn, yyline, yytext());
          }
        case 48: break;
        case 6: 
          { /*System.out.println(yytext()); */return new Symbol(sym.ID, yycolumn, yyline, new String(yytext()));
          }
        case 49: break;
        case 29: 
          { /*System.out.println(yytext()); */return new Symbol(sym.NOT, yycolumn, yyline, yytext());
          }
        case 50: break;
        case 28: 
          { /*System.out.println(yytext()); */return new Symbol(sym.MAYOR_IGUAL, yycolumn, yyline, yytext());
          }
        case 51: break;
        case 17: 
          { /*System.out.println(yytext()); */return new Symbol(sym.PARENTESIS_C, yycolumn, yyline, yytext());
          }
        case 52: break;
        case 39: 
          { /*System.out.println(yytext()); */return new Symbol(sym.RETURN, yycolumn, yyline, yytext());
          }
        case 53: break;
        case 34: 
          { /*System.out.println(yytext()); */return new Symbol(sym.ELIF, yycolumn, yyline, yytext());
          }
        case 54: break;
        case 36: 
          { /*System.out.println(yytext()); */return new Symbol(sym.PRINT, yycolumn, yyline, yytext());
          }
        case 55: break;
        case 11: 
          { /*System.out.println(yytext()); */return new Symbol(sym.IGUAL, yycolumn, yyline, yytext());
          }
        case 56: break;
        case 2: 
          { frameCTDOA.addErrores(
                      "\nError Lexico: "
              + "\n\tLinea #:                     << " + (yyline + 1) + " >> "
              + "\n\tColumna #:                   << " + (yycolumn + 1) + " >> "
              + "\n\tToken NO Reconocido:         << " + yytext() + " >> ");
          }
        case 57: break;
        case 20: 
          { /*System.out.println(yytext()); */return new Symbol(sym.IF, yycolumn, yyline, yytext());
          }
        case 58: break;
        case 33: 
          { /*System.out.println(yytext()); */return new Symbol(sym.DEF, yycolumn, yyline, yytext());
          }
        case 59: break;
        case 8: 
          { /*System.out.println(yytext()); */return new Symbol(sym.MAS, yycolumn, yyline, yytext());
          }
        case 60: break;
        case 24: 
          { /*System.out.println(yytext()); */return new Symbol(sym.DISTINTO, yycolumn, yyline, yytext());
          }
        case 61: break;
        case 15: 
          { /*System.out.println(yytext()); */return new Symbol(sym.DOS_PUNTOS, yycolumn, yyline, yytext());
          }
        case 62: break;
        case 7: 
          { /*System.out.println(yytext()); */return new Symbol(sym.NUMERO, yycolumn, yyline, Integer.parseInt(yytext()));
          }
        case 63: break;
        case 31: 
          { /*System.out.println(yytext()); */return new Symbol(sym.AND, yycolumn, yyline, yytext());
          }
        case 64: break;
        case 26: 
          { /*System.out.println(yytext()); */return new Symbol(sym.MENOR_IGUAL, yycolumn, yyline, yytext());
          }
        case 65: break;
        case 12: 
          { /*System.out.println(yytext()); */return new Symbol(sym.MENOR, yycolumn, yyline, yytext());
          }
        case 66: break;
        case 32: 
          { /*System.out.println(yytext()); */return new Symbol(sym.REAL, yycolumn, yyline, Float.parseFloat(yytext()));
          }
        case 67: break;
        case 18: 
          { mc.addComentario(yytext());
          }
        case 68: break;
        case 9: 
          { /*System.out.println(yytext()); */return new Symbol(sym.MENOS, yycolumn, yyline, yytext());
          }
        case 69: break;
        case 23: 
          { /*System.out.println(yytext()); */return new Symbol(sym.CADENA, yycolumn, yyline, new String(yytext()));
          }
        case 70: break;
        case 13: 
          { /*System.out.println(yytext()); */return new Symbol(sym.MAYOR, yycolumn, yyline, yytext());
          }
        case 71: break;
        case 37: 
          { /*System.out.println(yytext()); */return new Symbol(sym.RANGE, yycolumn, yyline, yytext());
          }
        case 72: break;
        case 3: 
          { int tab = 0;
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
        case 73: break;
        case 40: 
          { /*System.out.println(yytext()); */return new Symbol(sym.IINPUT, yycolumn, yyline, new String(yytext()));
          }
        case 74: break;
        case 5: 
          { /*System.out.println(yytext()); */return new Symbol(sym.POR, yycolumn, yyline, yytext());
          }
        case 75: break;
        case 14: 
          { /*System.out.println(yytext()); */return new Symbol(sym.COMA, yycolumn, yyline, yytext());
          }
        case 76: break;
        case 42: 
          { /*System.out.println(yytext()); */return new Symbol(sym.FINPUT, yycolumn, yyline, new String(yytext()));
          }
        case 77: break;
        case 1: 
          { /*Ignore*/
          }
        case 78: break;
        case 41: 
          { /*System.out.println(yytext()); */return new Symbol(sym.CINPUT, yycolumn, yyline, new String(yytext()));
          }
        case 79: break;
        case 10: 
          { /*System.out.println(yytext()); */return new Symbol(sym.MOD, yycolumn, yyline, yytext());
          }
        case 80: break;
        case 27: 
          { /*System.out.println(yytext()); */return new Symbol(sym.DIFERENTE, yycolumn, yyline, yytext());
          }
        case 81: break;
        case 35: 
          { /*System.out.println(yytext()); */return new Symbol(sym.ELSE, yycolumn, yyline, yytext());
          }
        case 82: break;
        case 4: 
          { /*System.out.println(yytext()); */return new Symbol(sym.DIV, yycolumn, yyline, yytext());
          }
        case 83: break;
        case 30: 
          { /*System.out.println(yytext()); */return new Symbol(sym.FOR, yycolumn, yyline, yytext());
          }
        case 84: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
              { return new java_cup.runtime.Symbol(sym.EOF); }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }

  /**
   * Converts an int token code into the name of the
   * token by reflection on the cup symbol class/interface sym
   *
   * This code was contributed by Karl Meissner <meissnersd@yahoo.com>
   */
  private String getTokenName(int token) {
    try {
      java.lang.reflect.Field [] classFields = sym.class.getFields();
      for (int i = 0; i < classFields.length; i++) {
        if (classFields[i].getInt(null) == token) {
          return classFields[i].getName();
        }
      }
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }

    return "UNKNOWN TOKEN";
  }

  /**
   * Same as next_token but also prints the token to standard out
   * for debugging.
   *
   * This code was contributed by Karl Meissner <meissnersd@yahoo.com>
   */
  public java_cup.runtime.Symbol debug_next_token() throws java.io.IOException {
    java_cup.runtime.Symbol s = next_token();
    System.out.println( "line:" + (yyline+1) + " col:" + (yycolumn+1) + " --"+ yytext() + "--" + getTokenName(s.sym) + "--");
    return s;
  }

  /**
   * Runs the scanner on input files.
   *
   * This main method is the debugging routine for the scanner.
   * It prints debugging information about each returned token to
   * System.out until the end of file is reached, or an error occured.
   *
   * @param argv   the command line, contains the filenames to run
   *               the scanner on.
   */
  public static void main(String argv[]) {
    if (argv.length == 0) {
      System.out.println("Usage : java LexicoPython <inputfile>");
    }
    else {
      for (int i = 0; i < argv.length; i++) {
        LexicoPython scanner = null;
        try {
          scanner = new LexicoPython( new java.io.FileReader(argv[i]) );
          while ( !scanner.zzAtEOF ) scanner.debug_next_token();
        }
        catch (java.io.FileNotFoundException e) {
          System.out.println("File not found : \""+argv[i]+"\"");
        }
        catch (java.io.IOException e) {
          System.out.println("IO error scanning file \""+argv[i]+"\"");
          System.out.println(e);
        }
        catch (Exception e) {
          System.out.println("Unexpected exception:");
          e.printStackTrace();
        }
      }
    }
  }


}
