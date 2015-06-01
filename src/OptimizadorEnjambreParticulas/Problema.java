package OptimizadorEnjambreParticulas;

/**
 * Clase abstracta que define que metodos debe implementar cada problema que se cree
 * Define un metodo obligatorio a implementar que es la funcion de evaluacion (fitness)
 * y deja el resto para que los pueda sobrescribir el problema concreto si asi lo desea
 */
public abstract class Problema{

  /** Funcion de fitness que debe ser implementada por cada problema */
  public abstract double funcionEvaluacion(Particula particula);

  /**
   * Para determinar si es un problema de maximizacion o minimizacion
   * @return True si es de maximizar y False si es de minimizar
   */
  public abstract boolean isMaximizeProblem();



  /**
   * Sirve para instanciar problemas especificos que no necesiten argumentos indicando la ruta completa
   * @param pathProblema String path completo donde se encuentra el problema (ej: TempleSimulado.Problemas.ProblemaAjustarPesosRNA)
   * @exception Excepciones gneeradas si el path del problema no es valido
   * @return Problema problema concreto instanciado
   */
  public static Problema  instanciarProblema(String pathProblema) throws Exception{
    Class claseProblema =Class.forName(pathProblema);
    return ((Problema)claseProblema.newInstance());
  }


  // Metodos que pueden ser sobrescritos por cada problema en cuestion

  /** El valor por defecto es de 3 dimensiones sino se sobrescribe */
  public int valorDefectoNumDimensiones(){ return 3; }

  /* El valor por defecto es de 100 particulas sino se sobrescribe */
  public int valorDefectoNumParticulas(){ return 100; }

  /** El valor por defecto de velocidad maxima que puede alcanzar una particula */
  public double valorDefectoVelocidadMax(){ return Double.MAX_VALUE; }

  /** El valor por defecto de la posicion maxima que puede alcanzar una particula */
  public double valorDefectoPosicionMax(){ return Double.MAX_VALUE; }

  /* El valor por defecto es de 0.9 sino se sobrescribe */
  public double valorDefectoCteAtraccion1(){ return 0.9; }

  /* El valor por defecto es de 0.9 sino se sobrescribe */
  public double valorDefectoCteAtraccion2(){ return 0.9; }

  /* El valor por defecto es de 0.9 sino se sobrescribe */
  public double valorDefectoInercia(){ return 0.9; }

  /* El valor por defecto es de true sino se sobrescribe */
  public boolean valorDefectoDisminucionProgresivaInercia(){ return true; }

  /* El valor por defecto de la puntuaciona obtener es de 1000 sino se sobrescribe */
  public double valorDefectoValorObtener(){ return 1000; }

  /* El valor por defecto del numero de iteracciones max es de 100 sino se sobrescribe */
  public long valorDefectoIteraccionesMax(){ return 100; }

  /** Sino se sobrescribe simplemente se devuelve que no se tiene informacion para este problema*/
  public String ayuda(){ return "Informacion sobre este problema"; }

  /** Sino se sobrescribe simplemente informa de la posicion y la velocidad de la particula en el espacio multidimensional,
   * pero se debe usar para interpretar el problema segun la codificacion de la particula, para interpretar el
   * resultado obtenido por la mejor partiucla
   */
  public String interpretracionParticula(Particula particula){   return ("Posicion : " + particula.getPosicion() + " velocidad: " + particula.getVelocidad() );   }

}
