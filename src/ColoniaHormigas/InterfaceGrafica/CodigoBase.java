package ColoniaHormigas.InterfaceGrafica;

/**
 * Implementacion por defecto de los problemas, tanto externos como internos
 */
public class CodigoBase {
  /** devuelve el codigo base para codificar un problema */
  public static String getCodigoProblema(String nombreProblema){
    String codigoInicialProblema =
        "/**\n "+
        " *  PLANTILLA BASE (MODIFICAR PARA CODIFICAR EL PROBLEMA DESEADO)\n "+
        " */\n"+
	"import ColoniaHormigas.*;\n "+
	"/**\n "+
	" * IMplementacion del problema\n "+
	" */\n "+
	"public class Problema"+nombreProblema+" extends Problema{\n "+
	"\n "+
	"  /**\n "+
	"   * Debe definirse para cada problema en concreto la matriz de costes\n "+
	"   * @return int[] Matriz de costes definiada para el problema concreto\n "+
	"   */\n "+
	"  public double[][] getCostesEntrePosiciones(){\n "+
	"      return null;\n "+
	"  }\n "+
	"\n "+
	"\n "+
	"  //_____________Metodos que pueden ser sobrescritos_______________________\n "+
	"  public int valorDefectoNumeroHormigas(){ return 4; }\n "+
	"  public long valorDefectoIteracionesMax(){ return  50; }\n "+
	"  public double valorDefectoCosteAObtener(){ return 0; }\n "+
	"  public double valorDefectoFactorEvaporacionLocal(){ return 0.1; }\n "+
	"  public double valorDefectoFactorEvaporacionGlobal(){ return 0.1; }\n "+
	"  public double valorDefectoCosteAproximadoTrayecto(){ return 100; }\n "+
	"  public double valorDefectoFactorExplotacionInicial(){ return 0.1; }\n "+
	"  public boolean valorDefectoIncrementoDinamicoFactorExploracion(){ return true; }\n "+
	"\n "+
	"\n "+
	"\n "+
	" /** Probando el problema */\n "+
	"  public static void main(String args[]){\n "+
	"    Problema"+nombreProblema+" problema"+nombreProblema+" = new Problema"+nombreProblema+"();\n "+
	"    OptimizadorColoniaHormigas aco = new OptimizadorColoniaHormigas(problema"+nombreProblema+");\n "+
	"    int[] trayectoSolucion = aco.optimizacionSecuencial();\n "+
	"    for (int i = 0; i < trayectoSolucion.length; i++){\n "+
	"      System.out.println(\"Posicion visitada: \" + trayectoSolucion[i]);\n "+
	"    }\n "+
	"  }\n "+
	"\n "+
	"}\n ";


    return codigoInicialProblema;
  }
}
