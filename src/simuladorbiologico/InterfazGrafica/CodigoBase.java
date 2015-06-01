package simuladorbiologico.InterfazGrafica;

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
			  "import simuladorbiologico.*; \n"+
			  "import simuladorbiologico.Individuos.*; \n"+

			  "/** \n"+
			  "* Problema especifico \n"+
			  "* establece la funcion de fitness o evaluacion de cada individuo en funcion de \n"+
			  "* como se aproxima a la solucion final \n"+
			  "*/ \n"+
			  "public class Problema"+nombreProblema+" extends Problema{ \n\n"+
			  "   /** FUNCIoN DE FITNESS \n"+
			  "     * Evalua la adaptacion del individuo al problema, como de bien lo resuelve \n"+
			  "     * @param individuo \n"+
			  "     * @return Devuelbe un valor numerico \n"+
			  "     * @throws Exception si el individuo no esta soportado \n"+
			  "     */ \n"+
			  "   public double evaluarIndividuo (Individuo individuo){ \n"+
			  "     if (individuo instanceof IndividuoBinarioHaploide){ \n"+
			  "       byte[] cromosomas = ((IndividuoBinarioHaploide)individuo)._cromosomas; \n"+
			  "     }else  if (individuo instanceof IndividuoBinarioDiploide){ \n"+
			  "       byte[][] cromosomas = ((IndividuoBinarioDiploide)individuo)._cromosomas; \n"+
			  "     } \n"+
			  "     return 0; \n"+
			  "   } \n\n"+

			  "   /** \n"+
			  "     * Devuelve los individuos para los cuales soporta implementacion este problema \n"+
			  "     * @return Individuo[] Individuos soportados \n"+
			  "     */ \n"+
			  "    public String[] individuosSoporta(){ \n"+
			  "      String[] individuosSoportados = { \"Binario Haploide\",\"Binario Diploide\"}; \n"+
			  "      return individuosSoportados; \n"+
			  "    } \n"+
                          "  /** \n"+
                          "    * Para determinar si es un problema de maximizacion o minimizacion\n"+
                          "   * @return True si es de maximizar y False si es de minimizar\n"+
                          "   */\n"+
                          "   public boolean isMaximizeProblem(){\n"+
                          "      return true;\n"+
                          "   }\n\n\n"+
			  "//____METODOS NO NECESARIOS SOBRESCRIBIRLOS: si si no se quiere la implementacion por defecto_____\n\n"+
			  "   /** \n"+
			  "     * @param invididuo a interpretar segun el problema \n"+
			  "     * @return Devuelbe una descripcion del individuo relativa al problema Electoral \n"+
			  "     */ \n"+
			  "   public String interpretacionDelIndividuo(Individuo individuo){ \n"+
			  "     return new String (\"Este individuo tiene \" + evaluarIndividuo(individuo) +\" unos\"); \n"+
			  "   } \n"+
			  "    /** @return int Numero de individuos que tendra por defecto la poblacion */ \n"+
			  "    public int numeroIndividuosPorDefecto(){ return 30; } \n\n"+
			  "   /** @return int Longitud del cromosoma de los individuos por defecto */ \n"+
			  "   public int longitudCromosomaPorDefecto(){ return 1000;} \n\n"+
			  "   /** @return int Numero de individuos contra los que tiene que competir en cada reproduccion por defecto */ \n"+
			  "    public int presionSelectivaPorDefecto(){return 4;} \n\n"+
			  "   /** @return int Numero de iteracciones necesaria para incrementar en uno la presion selectiva */ \n"+
			  "   public int numIteraccionesParaAumentarPresionSelectivaPorDefecto(){return 100;} \n\n"+
			  "   /** @return int Numero de individuos que pasan directamente a la siguiente generacion por ser los mejores */ \n"+
			  "   public int elitismoPorDefecto(){return 1;} \n\n" +
			  "   /** @return String Tipo de sobrecruzamiento que se usa por defecto en el problema */ \n"+
			  "   public String tipoSobrecruzamientoPorDefecto(){return Reproduccion.sobrecruzamientoPartirAleatio;} \n\n"+
			  "   /** @return boolean Determina si el mejor esta expuesto o no a mutacion */ \n" +
			  "  public boolean mejorInmutablePorDefecto(){return true;} \n\n"+
			  "   /** @return double Puntuacion a obtener por defecto para detener la simulacion */ \n"+
			  "   public double puntuacionAObtenerPorDefecto(){return 1000;} \n\n"+
			  "   /** @return int Itercaciones maximas a realizar para detener la simulacion */ \n"+
			  "   public int iteracionesMaximasPorDefecto(){return 100;} \n\n"+
			  "   /** @return boolean True si la solucion puede ser relativa al individuo  */ \n"+
			  "   public boolean individuoPuedeSerSolucion(){return true;} \n\n"+
			  "   /** @return boolean True Si la solucion puede ser relativa a la poblacion (solucion de Manhatan)*/ \n"+
			  "   public boolean poblacionPuedeSerSolucion(){return true;} \n\n"+
			  "   /** @return String Ayuda al problema */ \n"+
			  "   public String obtenerAyuda(){ \n"+
			  "     return \"Ayuda\"; \n"+
			  "   } \n\n\n"+

			  "   /** Main para probar el problema sin necisidad de interfaz grafica */ \n"+
			  "   public static void main(String[] args){ \n"+
			  "      try{ \n"+
			  "         Problema problema"+nombreProblema+" = new Problema"+nombreProblema+"(); //instanciamos el problema y su poblacion \n"+
			  "         Poblacion poblacion"+nombreProblema+" = new Poblacion"+nombreProblema+"(problema"+nombreProblema+"); \n"+
			  "         String[] individuosSoporta = problema"+nombreProblema+".individuosSoporta(); \n"+
			  "         Individuo individuoSolucion = EjecutaAG.lanzarAlgoritmoAG(poblacion"+nombreProblema+", individuosSoporta[0]); \n"+
			  "      }catch (Exception ex) { \n"+
			  "        System.out.println(\"Error al intentar solucional el problema mediante algoritmos geneticos\"); \n"+
			  "      } \n"+
			  "   } \n"+
			  "}";

    return codigoInicialProblema;
  }

  /** devuelve el codigo base para codificar una poblacion para problema */
  public static String getCodigoPoblacion(String nombreProblema){
    String codigoInicialPoblacion =
			   "import simuladorbiologico.*;\n\n"+
			   "/** Poblacion basada en reproduccion de torneos*/\n"+
			   "public class Poblacion"+nombreProblema+"  extends Poblacion{\n\n"+
			   "    /**Creamos individuos diploides con codificacion binaria */\n"+
			   "    public Poblacion"+nombreProblema+" (Problema problema){\n"+
			   "        super(problema);\n"+
			   "    }\n\n"+
			   "    /** @return Valor minimo que podra tomar cada gen de cada individuo de la poblacion */\n"+
			   "    public double valorMinimoGenIndividuos(){\n"+
			   "        return 0;\n"+
			   "    }\n\n"+
			   "    /** @return Valor maximo que podra tomar cada gen de cada individuo de la poblacion */\n"+
			   "    public double valorMaximoGenIndividuos(){\n"+
			   "        return 1;\n"+
			   "    }\n\n\n"+
			   "    // PUEDEN SER SOBRESCRITOS LOS SIGUIENTES MeTODOS (sino borrense) \n\n"+
			   "    /** Se le da una valoracion a cada individuo para tener mas probabilidades de sobrevivir */ \n"+
			   "    public void aplicarSeleccionNatural(){ \n"+
			   "       for (int i = 0; i < _individuos.length; i++){ \n"+
			   "           _individuos[i]._valoracion = _contexto.evaluarIndividuo(_individuos[i]); \n"+
			   "       } \n"+
			   "    } \n\n"+
			   "   /**  \n"+
			   "     * La poblacion se reproduce en funcion a las valoraciones de cada individuo; notar que cada individuo de la nueva \n"+
			   "     * poblacion no ha sido evaluado todavia ni ha sufrido todavia procesos de seleccion \n"+
			   "     * @param presionSelectiva int  a numero mas grande mas presion se hace, cuanto mas pequenyo menos presion \n"+
			   "     * @param elitismo int Numero de individuos que pasan directamente a la siguiente generacion por ser los mejores \n"+
			   "     * @param tipoSobrecruzamiento String la forma en la que se realizara el sobrecruzamiento entre los individuos \n"+
			   "     * @param mejorInmutable boolean Determina si el mejor individuo esta expuesto a mutacion o no \n"+
			   "     * @throws Exception \n"+
			   "     */ \n"+
			   "   public void reproducir(boolean isMaximizationProblem, int presionSelectiva,int elitismo, String tipoSobrecruzamiento,boolean mejorInmutable) throws Exception{ \n"+
			   "      Reproduccion.torneos(_individuos,isMaximizationProblem, presionSelectiva,elitismo,tipoSobrecruzamiento,mejorInmutable); \n"+
			   "   } \n"+
			   "} \n";


    return codigoInicialPoblacion;
  }
}
