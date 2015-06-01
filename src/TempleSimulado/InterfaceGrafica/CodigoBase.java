package TempleSimulado.InterfaceGrafica;

/**
 * Implementacion por defecto de los problemas, tanto externos como internos
 */
public class CodigoBase {

  /** Devuelve el codigo base para codificar un problema */
  public static String getCodigoProblema(String nombreProblema){
    return
        "/**\n "+
        " *  PLANTILLA BASE (MODIFICAR PARA CODIFICAR EL PROBLEMA DESEADO)\n "+
        " */\n"+
	"import TempleSimulado.*;\n\n "+
	"public class Problema" + nombreProblema + " extends Problema{\n\n " +
	"    public Problema"+nombreProblema+"(){}\n\n " +
	"     /**\n " +
	"      * Funcion heuristica que representa la codificacion del problema y que se intente minimizar,\n " +
	"      * en este caso se trata de ...         \n " +
	"      * @param estado Estado que se desea evaluar\n " +
	"      * @return double Energia asociada al estado\n " +
	"      */\n " +
	"      public double funcionEvaluacionEnergia(Estado estado){\n " +
	"           return 0; \n " +
	"      }\n "+

	"      /**\n " +
	"       * @return Estado Estado desde el que se inicia la solucion del problema\n " +
	"       */\n " +
	"       public Estado estadoInicial(){\n " +
	"           return (new Estado" + nombreProblema + "());\n " +
	"       }\n\n\n\n " +

	"        //______________Metodos que pueden ser sobrescritos para adaptarlos al problema en concreto__________\n " +

	"       /**\n " +
	"        * Valor por defecto con el que se resolvera el problema: 100\n " +
	"        * Temperatura desde la que se parte y se desea minimizar\n " +
	"        * @return double\n " +
	"        */\n " +
	"       public double valorDefectoTemperaturaInicial(){\n " +
	"           return 100;\n " +
	"       }\n\n " +

	"       /**\n " +
	"        * Valor por defecto con el que se resolvera el problema: 0\n " +
	"        * @return double Temperatura que se desea alcanzar\n " +
	"        */\n " +
	"       public double valorDefectoTemperaturaFinal(){\n " +
	"           return 0;\n " +
	"       }\n\n " +

	"       /**\n " +
	"        * Valor por defecto con el que se resolvera el problema: 1000\n " +
	"        * @return double Numero maximo de etapas a generar\n " +
	"        */\n " +
	"       public long valorDefectoEtapasMax(){\n " +
	"           return 100;\n " +
	"       }\n\n " +

	"       /**\n " +
	"        * Valor por defecto con el que se resolvera el problema: 100\n " +
	"        * @return double Iteracciones maximias permitidas en cada etapa inicialmente\n "+
	"                  (luego va variando segun el factor de incremento\n " +
	"        */\n " +
	"       public long valorDefectoIteraccionesMaxEtapa(){\n " +
	"           return 10;\n " +
	"       }\n\n " +

	"       /**\n " +
	"        * Valor por defecto con el que se resolvera el problema: 10\n " +
	"        * @return int Numero maximo de cambios de estados permitidos para una misma etapa\n " +
	"        */\n " +
	"       public int valorDefectoAceptacionesMaxEtapa(){\n " +
	"           return 5;\n " +
	"       }\n\n " +

	"       /**\n " +
	"        * Valor por defecto con el que se resolvera el problema: 0.9\n " +
	"        * @return double Factor por el que se va a multiplicar la temperatura en cada etapa que disminuya\n " +
	"        */\n " +
	"       public double valorDefectoFactorEnfriamiento(){\n " +
	"           return 0.9;\n " +
	"       }\n\n " +

	"       /**\n " +
	"        * Valor por defecto con el que se resolvera el problema: 1.30\n " +
	"        * @return double Factor por el que se va a multiplicar la temperatura en cada etapa que aumente\n " +
	"        */\n " +
	"       public double valorDefectoFactorCalentamiento(){\n " +
	"           return 1.30;\n " +
	"       }\n\n " +

	"       /**\n " +
	"        * Valor por defecto con el que se resolvera el problema: 1.50\n " +
	"        * @return double Factor por el que se va a multiplicar el numero de iteracciones max por etapa al finalizar la etapa\n " +
	"        */\n " +
	"       public double valorDefectoFactorIncrementoIteraccionesMaxEtapa(){\n " +
	"           return 1.50;\n " +
	"       }\n\n " +


	"      /**\n " +
	"       * Valor por defecto de la ayuda del problema: \"No se ha indicado ningun tipo de informacion para este problema\"\n " +
	"       * @return String Informacion sobre el problema\n " +
	"       */\n " +
	"      public String ayuda(){\n " +
	"          return \"No se ha indicado ningun tipo de informacion para este problema\";\n " +
	"      }\n\n " +

	"       /** Prueba del problema (no es necesario tener el main, se puede borrar) */\n "+
	"       public static void main (String args[] ){\n "+
	"           Problema" + nombreProblema + " problema" + nombreProblema + " = new Problema" + nombreProblema + "();\n " +
	"           TempleSimulado temple = new TempleSimulado(problema" + nombreProblema + ");\n " +
	"           temple.templadoSimulado();\n " +
	"       }\n " +

	"}\n ";

  }

  /** devuelve el codigo base para codificar una poblacion para problema */
  public static String getCodigoEstado(String nombreProblema){
    return
      "import TempleSimulado.Estado;\n"+
      "import TempleSimulado.Problema;\n\n"+

      "public class Estado"+nombreProblema+" implements Estado {\n "+
      "   /**\n"+
      "    * @return Estado Devuelve un estado siguiente posible al actual: devuelve uno de los posibles vecinos\n "+
      "    */\n "+
      "   public Estado obtenerUnSiguiente() {\n "+
      "      return new Estado"+nombreProblema+"();\n "+
      "   }\n\n "+
      "   /**\n "+
      "     * @return Devuelve informacion textual sobre el estado que sea compresnsible en el contexto del problema\n "+
      "     */\n "+
      "   public String descripcionTextualEstado(Problema problema) {\n "+
      "      String aDevolver = \"\";\n "+
      "      return aDevolver;\n "+
      "   }\n "+
      "}\n ";
  }
}
