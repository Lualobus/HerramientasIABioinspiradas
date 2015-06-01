package OptimizadorEnjambreParticulas.InterfaceGrafica;

/**
 * Implementacion por defecto de los problemas, tanto externos como internos
 */
public class CodigoBase {
  /** devuelve el codigo base para codificar un problema */
  public static String getCodigoProblema(String nombreProblema){
    String codigoInicialProblema = "/**\n "+
                                   " *  PLANTILLA BASE (MODIFICAR PARA CODIFICAR EL PROBLEMA DESEADO)\n "+
                                   " */\n"+
                                   "import OptimizadorEnjambreParticulas.*;\n\n "+
				   "public class Problema"+nombreProblema+" extends Problema{\n\n "+
				   "  public double funcionEvaluacion(Particula particula){\n "+
				   "    int totalX = 0;\n "+
				   "    double[] posicion = particula.getPosicion();\n "+
				   "    double[] velocidad = particula.getVelocidad();\n "+
				   "    int numDimensiones = particula.getDimensiones();\n "+
				   "    for (int i = 0; i < numDimensiones; i++){\n "+
				   "      totalX+=posicion[i];\n "+
				   "    }\n "+
				   "    return totalX;\n "+
                                   "  }\n\n "+
                                   "  /**\n "+
                                   "   * Determinamos que es un problema de minimizacion\n "+
                                   "   * @return False porque es de minimizar\n "+
                                   "   */\n "+
                                   "  public boolean isMaximizeProblem(){\n "+
                                   "     return false;\n "+
                                   "  }\n\n "+
				   "  /** Main para probar el problema sin necisidad de interfaz grafica */\n "+
				   "  public static void main(String[] args){  \n "+
				   "     Problema problema"+nombreProblema+" = new Problema"+nombreProblema+"();                           //instanciamos el problema\n "+
				   "     Particula particulaSolucion =  EjecutaPSO.lanzarPSO (problema"+nombreProblema+");     //lanzamos el algoritmo \n "+
				   "     double[] solucion = particulaSolucion.getPosicion();	              //obtenemos la solucion  \n "+
				   "  }\n "+
				   "}\n ";

    return codigoInicialProblema;
  }
}
