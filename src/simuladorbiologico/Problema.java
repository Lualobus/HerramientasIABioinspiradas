package simuladorbiologico;

/**
 * Esta interface declara el metodo que todo contexto especifico debe cumplir
 * para resolber un problema, el valor que devuelbe el metodo esta relacionado
 * en como de bien resuelve el problema o se acerca a la solucion, para establecer
 * la relacion entre el genotipo (la codificacion del cromosoma) y fenotipo (en que
 * se traduce en nuestro problema esa codificacion, que representacion teine en el mundo real)
 * hace falta hacer una buena codificacion del problema que quedara reflejado en
 * la funcion de evaluacion que establece esta interface
 */
public abstract class Problema {

  //MeTODOS QUE SON NECESARIOS IMPLEMENTAR EN CADA PROBLEMA NUEVO

  /**
   * Es la funcion de fitness y debe implementarse en cada problema como sea oporturno
   * valora el cromosoma del individuo como es de bueno para resolver el problema y devuelbe un valor
   * (se valora el genotipo)
   * @param individuo Individuo a evaluar
   * @return Devuelbe un valor numerico en funcion de su adaptacion a la solucion
   * @throws Exception si el individuo no esta soportado por el problema
   */
  public abstract double evaluarIndividuo (Individuo individuo);

  /** @return Individuo[] Devuelve los individuos para los cuales soporta implementacion este problema */
  public abstract String[] individuosSoporta();

  /**
   * Para determinar si es un problema de maximizacion o minimizacion
   * @return True si es de maximizar y False si es de minimizar
   */
  public abstract boolean isMaximizeProblem();


  /**
   * Sirve para instanciar problemas especificos que no necesiten argumentos indicando la ruta completa
   * @param pathProblema String path completo donde se encuentra el problema (ej: simuladorbiologico.Problemas.AjustarPesosRNA.ProblemaAjustarPesosRNA)
   * @exception Excepciones gneeradas si el path del problema no es valido
   * @return Problema problema concreto instanciado
   */
  public static Problema  instanciarProblema(String pathProblema) throws Exception{
    Class claseProblema =Class.forName(pathProblema);
    java.lang.reflect.Constructor[] constructoresProblema = claseProblema.getConstructors();
    Object[] argumentosProblema=null;
    return ((Problema)constructoresProblema[0].newInstance(argumentosProblema));
  }



  //METODOS DE LOS QUE NO ES NECESARIO IMPLEMENTAR EN CADA PROBLEMA NUEVO: si es conveniente sobrescribirlos

  /**
   * Devuelbe una descripcion textual de como es el individuo para este problema
   * (Se hace una descripcion a nivel de fenotipo)
   * @param invididuo
   * @return La interpretacion que se hace en este contexto del genotipo del individuo (es decir el fenotipo)
   * @throws Exception si el individuo no esta soportado por este contexto
   */
  public String interpretacionDelIndividuo(Individuo invididuo){
    return "No se dispone de ninguna interpretracion del individuo";
  }

  /** @return boolean True el objetivo es maximizar el fitness y False el objetivo es minimizarlo*/
  //public boolean objetivoMaximizar();

  /** @return double Puntuacion a obtener por defecto para detener la simulacion */
  public double puntuacionAObtenerPorDefecto(){return 1000;}

  /** @return int Numero de individuos que tendra por defecto la poblacion */
  public int numeroIndividuosPorDefecto(){return 100;}

  /** @return int Longitud del cromosoma de los individuos por defecto */
  public int longitudCromosomaPorDefecto(){return 100;}

  /** @return double Numero de individuos contra los que tiene que competir en cada reproduccion por defecto */
  public double probabilidadMutarGenPorDefecto(){return 0.2;}

  /** @return int Numero de iteracciones necesaria para incrementar en uno la presion selectiva */
  public int numIteraccionesParaAumentarPresionSelectivaPorDefecto(){return 100;}

  /** @return int Numero de individuos que pasan directamente a la siguiente generacion por ser los mejores */
  public int elitismoPorDefecto(){return 1;}

  /** @return String Tipo de sobrecruzamiento que se usa por defecto en el problema */
  public String tipoSobrecruzamientoPorDefecto(){return Reproduccion.sobrecruzamientoPartirAleatio;}

  /** @return boolean Determina si el mejor esta expuesto o no a mutacion */
  public boolean mejorInmutablePorDefecto(){return true;}

  /** @return int Numero de individuos contra los que tiene que competir en cada reproduccion por defecto */
  public int presionSelectivaPorDefecto(){return 4;}

  /** @return int Itercaciones maximas a realizar para detener la simulacion */
  public int iteracionesMaximasPorDefecto(){ return 1000;}

  /** @return boolean True si la solucion puede ser relativa al individuo  */
  public boolean individuoPuedeSerSolucion(){ return true;}

  /** @return boolean True Si la solucion puede ser relativa a la poblacion (solucion de Manhatan)*/
  public boolean poblacionPuedeSerSolucion(){ return false;}

  /** @return String Ayuda al problema */
  public String obtenerAyuda(){ return "No hay ayuda para este problema";}

}
