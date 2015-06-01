package simuladorbiologico;


/**
 * Clase que se encarga de lanzar la simulacion
 * @author Fernando Alonso
 * @version 1.0
 */
public class EjecutaAG {

  /**
   * Lanza el algoritmo genetico usando los parametros por defecto del problema
   * @param poblacion Poblacion que va a solucionar el problema
   * @param tipoIndividuoSoluciona String Individuo con el que se va a solucionar
   * @return Individuo Individuo que soluciona el problema
   * @throws Exception Cualquier excepcion lanzada durante la simulacion
   */
  public static Individuo lanzarAlgoritmoAG (Poblacion poblacion, String tipoIndividuoSoluciona) throws Exception{
    Simulador simulador = new Simulador(poblacion,tipoIndividuoSoluciona);
    Individuo mejorIndividuo = simulador.realizarSimulacionBloqueante();
    System.out.println(mejorIndividuo.toString());
    return mejorIndividuo;
  }


  /**
   * Lanza el algortimo genetico para solucionar el problema usando los parametros que se pasan en esta llamada
   * @param poblacion Poblacion Poblacion que va a solucionar el problema
   * @param tipoIndividuo String  Individuo con el que se va a solucionar
   * @param puntuacion double Puntuacion a obtener para deterner la simulacion
   * @param numeroIndividuos int Numero de individuos que forman la poblacion
   * @param longitudCromosoma int Tamanyo del cromosoma de cada individuo (numero de genes)
   * @param presionSelectiva int Numero de individuos contra los que hay que competir para reproducirse
   * @param probabilidadMutarGen double Probabilidad de un gen de un individuo de ser mutado
   * @param pasosParaAumentarLaPresion int Numero de generaciones que tienen que pasara para aumentar en uno la presion selectiva
   * @param elitismo int Numero que determina cuantos individuos pasan a la siguiente generacion simplemente por ser los mejores
   * @param iteracionesMaximas long Numero de iteraciones maximas a generar
   * @param tipoSobrecruzamiento String Tipo de sobrecruzamiento que se va a usar para resolver el problema "Partir Aleatorio", "Partir por la mitad", "Especializado", "Ninguno"
   * @param iteracionesRedibujado long Cada cuantas iteraciones se mostraran resultados de la simulacion
   * @param mejorInmutable boolean Determina si el mejor individuo puede estar o no expuesto a mutacion
   * @param argumentosAdicionales Object[] argumentos adicionales que necesite el problema concreto
   * @return Individuo que soluciona el problema
   * @throws Exception Una excepcion que se puede generar si algun argumeno es incorrecto
   */
  public static Individuo lanzarAlgoritmoAG (
					     Poblacion poblacion,
					   String tipoIndividuo,
					   double puntuacion,
					   int numeroIndividuos,
					   int longitudCromosoma,
					   int presionSelectiva,
					   double probabilidadMutarGen,
					   int pasosParaAumentarLaPresion,
					   int elitismo,
					   long iteracionesMaximas,
					   String tipoSobrecruzamiento,
					   long iteracionesRedibujado,
					   boolean mejorInmutable,
					   Object[] argumentosAdicionales) throws Exception{

    Simulador simulador = new Simulador();
    simulador.asignarParametros(poblacion,tipoIndividuo,puntuacion,numeroIndividuos,longitudCromosoma,presionSelectiva,probabilidadMutarGen,pasosParaAumentarLaPresion,elitismo,iteracionesMaximas,tipoSobrecruzamiento,iteracionesRedibujado,argumentosAdicionales);
    simulador.setMejorInmutable(mejorInmutable);
    Individuo mejorIndividuo = simulador.realizarSimulacionBloqueante();
    System.out.println(mejorIndividuo.toString());
    return mejorIndividuo;
  }
}
