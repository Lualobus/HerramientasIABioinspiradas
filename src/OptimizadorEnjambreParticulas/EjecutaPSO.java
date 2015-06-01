package OptimizadorEnjambreParticulas;

public class EjecutaPSO {

  /**
   * Lanza el algoritmo pso para solucionar el problema, usando los parametros de configuracion dados por defecto en el problema
   * @param problema Problema Problema a solucionar
   * @return Particula Mejor particula encontrada para la solucion del problema
   */
  public static Particula lanzarPSO(Problema problema){
    OptimizadorEnjambreParticulas.Enjambre enjambre = new OptimizadorEnjambreParticulas.Enjambre(problema);
    return enjambre.pso(true);
  }


  /**
   * Lanza el algoritmo pso para solucionar el problema, usando los parametros que se pasan en esta llamada
   * @param problema Problema Problema a solucionar
   * @param numDimensiones int Numero de dimensiones de las que consta cada particula del enjambre
   * @param numParticulas int Numero de particulas en el enjambre
   * @param valoracionObtener double Puntuacion al menos que debe tener la particula que se devuelva como resultado de este metodo
   * @param iteraccionesMax long Iteracciones maximas del algoritmo
   * @param irMostrandoTerminal boolean Si se quiere ir mostrando por el terminal como va evolucionando
   * @param cteAtraccion1 double Constante de atraccion1: Como influye mi mejor posicion para cada dimension en la futura velocidad
   * @param cteAtraccion2 double Como influye la mejor posicion obtenida para cada dimension en el enjambre a mi futura velocidad
   * @param inercia double Como influye la velocidad anterior en la actual
   * @param disminucionProgresivaInercia boolean La inercia se va disminuyendo paulatinamente a lo largo de las iteracciones hasta un minimo de 0.4
   * @param velocidadMax double Velocidad maxima que puede alcanzar cualquier particula
   * @param posicionMax double Posicion maxima que puede alcanzar cualquier particula en cada componente dimensional
   * @return Particula Mejor particula obtenida
   */
  public static Particula lanzarPSO(Problema problema,int numDimensiones, int numParticulas, double valoracionObtener, long iteraccionesMax, boolean irMostrandoTerminal, double cteAtraccion1, double cteAtraccion2, double inercia, boolean disminucionProgresivaInercia, double velocidadMax, double posicionMax ){
    OptimizadorEnjambreParticulas.Enjambre enjambre = new OptimizadorEnjambreParticulas.Enjambre(problema,numParticulas,numDimensiones,velocidadMax,posicionMax);
    return enjambre.pso(valoracionObtener,iteraccionesMax,irMostrandoTerminal,cteAtraccion1,cteAtraccion2,inercia,disminucionProgresivaInercia);
  }

}
