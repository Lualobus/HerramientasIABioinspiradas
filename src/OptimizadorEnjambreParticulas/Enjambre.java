package OptimizadorEnjambreParticulas;



/**
 * Clase que proporciona una implementacion del algoritmo PSO, o tambien llamado
 * optimizacion por Enjambres de Particulas (Swarm Intelligence)
 */
public class Enjambre {

  private Particula[] particulas;          /** Particulas de las que consta el enjambre */
  private Problema contextoProblema;       /** Problema concreto que se quiere resolver con el enjambre */
  private Particula mejorParticula;        /** Particula con la mejor valoracion de todo el enjambre */
  private double valoracionTotalEnjambre;  /** Puntuacion total obtenida por el enjambre */
  private long iteraccionesRealizadas;     /** Numero de iteracciones del algoritmo PSO realizadas */
  private Particula clonMejorInmutable;    /** Para no perder la mejor posicion alcanzada */
  private double velocidadMax;             /** La velocidad de cada particula no puede sobrepasar este valor */
  private double posicionMax;              /** La posicion de cada particula no puede sobrepasar este valor */


  /**
   * Constructor del Enjambre
   * @param problema Contexto Problema a resolver (con la codificacion del mismo en su funcion de Evaluacion)
   * @param numParticulas int Numero de particulas en el enjambre
   * @param numDimensiones int Numero de dimensiones de las que consta cada particula del enjambre
   * @param velocidadMax double Maxima velocidad que puede alcanzar cualquier particula
   * @param posicionMax dobule Posicion maxima que puede alcanzar cualquier particula
   */
  public Enjambre(Problema problema, int numParticulas, int numDimensiones, double velocidadMax, double posicionMax){
    contextoProblema = problema;
    particulas = new Particula[numParticulas];
    for (int i = 0; i < numParticulas; i++){
      particulas[i] = new Particula(numDimensiones,this);
    }
    this.velocidadMax = velocidadMax;
    this.posicionMax = posicionMax;

    //inicialmente la mejor particula decimos que es la primera de todas
    mejorParticula= particulas[0];
    double valoracionParticula = contextoProblema.funcionEvaluacion(mejorParticula);
    mejorParticula.setValoracion(valoracionParticula);
    clonMejorInmutable = mejorParticula.clon();
  }


  /**
   * Constructor del Enjambre, el numero de dimensiones y el numero de particulas
   * los obtiene de los valores por defecto que se hayan dado en el problema
   * @param problema Contexto Problema a resolver (con la codificacion del mismo en su funcion de Evaluacion)   *
   */
  public Enjambre(Problema problema){
    contextoProblema = problema;
    int numParticulas = problema.valorDefectoNumParticulas();
    int numDimensiones = problema.valorDefectoNumDimensiones();
    velocidadMax = problema.valorDefectoVelocidadMax();
    posicionMax = problema.valorDefectoPosicionMax();
    particulas = new Particula[numParticulas];
    for (int i = 0; i < numParticulas; i++){
      particulas[i] = new Particula(numDimensiones,this);
    }

    //inicialmente la mejor particula decimos que es la primera de todas
    mejorParticula= particulas[0];
    double valoracionParticula = contextoProblema.funcionEvaluacion(mejorParticula);
    mejorParticula.setValoracion(valoracionParticula);
    clonMejorInmutable = mejorParticula.clon();

  }


  /**
   * Algoritmo Optimizador de enjambre de particulas (para valores reales) y en el que el mejor valor posible observado es el la mejor posicon en el enjambre:
   * actualiza todas las particulas en cada iteraccion hasta encontrar una cuya funcion de evaluacion obtenga la va
   * @param valoracionObtener double Puntuacion al menos que debe tener la particula que se devuelva como resultado de este metodo
   * @param iteraccionesMax long Iteracciones maximas del algoritmo
   * @param irMostrandoTerminal boolean Si se quiere ir mostrando por el terminal como va evolucionando
   * @param cteAtraccion1 double Constante de atraccion1: Como influye mi mejor posicion para cada dimension en la futura velocidad
   * @param cteAtraccion2 double Como influye la mejor posicion obtenida para cada dimension en el enjambre a mi futura velocidad
   * @param inercia double Como influye la velocidad anterior en la actual
   * @param disminucionProgresivaInercia boolean La inercia se va disminuyendo paulatinamente a lo largo de las iteracciones hasta un minimo de 0.4
   * @return Particula Mejor particula obtenida
   */
  public Particula pso(double valoracionObtener, long iteraccionesMax, boolean irMostrandoTerminal, double cteAtraccion1, double cteAtraccion2, double inercia, boolean disminucionProgresivaInercia){
    int numParticulas = particulas.length;
    double valoracionParticula;
    iteraccionesRealizadas = 0;

    do{  //para cada iteraccion
      valoracionTotalEnjambre = 0;
      for (int i = 0; i < numParticulas; i++){  //para cada particula
	//actualizamos su posicion
      	particulas[i].actualizarPosicion();
	//la valoramos segun la funcion de evaluacion para el problema
	valoracionParticula = contextoProblema.funcionEvaluacion(particulas[i]);
	valoracionTotalEnjambre+=valoracionParticula;
	//la asignamos dicha valoracion
	particulas[i].setValoracion(valoracionParticula);
        if (iteraccionesRealizadas==0){  //si es la primera iteracion le fijamos la actual posicion como la mejor alcanzada hasta el momento (no ha habido mas)
          particulas[i].setMejorPosicionAlcanzada(particulas[i].getPosicion());
          particulas[i].setMejorValoracionAlcanzada(valoracionParticula);
        }

        //actualizamos su memoria local (su mejor posicion alcanzada) y la global (si se ha convertido en la mejor del enjambre)
        if (contextoProblema.isMaximizeProblem()){

            //vemos si se ha convertido en la mejor particula del enjambre
            if (valoracionParticula > mejorParticula.getValoracion()) {
              mejorParticula = particulas[i];
              clonMejorInmutable = particulas[i].clon();
            }

            //miramos si su actual posicion es la mejor que ha alcanzado la particula en su vida
            if (valoracionParticula >  particulas[i].getMejorValoracionAlcanzada()){
              particulas[i].setMejorPosicionAlcanzada(particulas[i].clon().getPosicion());
              particulas[i].setMejorValoracionAlcanzada(valoracionParticula);
            }

        }else{
            if (valoracionParticula < clonMejorInmutable.getValoracion()) {
              mejorParticula = particulas[i];
              clonMejorInmutable = particulas[i].clon();
            }

            //miramos si su actual posicion es la mejor que ha alcanzado la particula en su vida
            if (valoracionParticula <  particulas[i].getMejorValoracionAlcanzada()){
              particulas[i].setMejorPosicionAlcanzada(particulas[i].clon().getPosicion());
              particulas[i].setMejorValoracionAlcanzada(valoracionParticula);
            }
        }

	//le actualizamos la velocidad
	particulas[i].actualizarVelocidad(inercia,cteAtraccion1,cteAtraccion2);
      }
      iteraccionesRealizadas++;
      if ((disminucionProgresivaInercia)&&(inercia>=0.2)&&(iteraccionesRealizadas%(iteraccionesMax/5)==0)){
	inercia-=0.1;System.out.println("Inercia: "+ inercia);
      }
      if (irMostrandoTerminal) System.out.println("generacion " + iteraccionesRealizadas +": " + valoracionTotalEnjambre + " mejor: " + mejorParticula.getValoracion());
      Thread.yield();

    //mientras no obtengamos la puntuacion deseada y no superemos las iteracciones maximas
    }while( ((contextoProblema.isMaximizeProblem() && (mejorParticula.getValoracion() < valoracionObtener)) ||  (!contextoProblema.isMaximizeProblem() && (mejorParticula.getValoracion() > valoracionObtener)))
           && (iteraccionesRealizadas<iteraccionesMax));

     return clonMejorInmutable;
  }

  /**
   * Algoritmo Optimizador de enjambre de particulas (para valores reales) y en el que el mejor valor posible observado es el la mejor posicon en el enjambre:
   * actualiza todas las particulas en cada iteraccion hasta encontrar una cuya funcion de evaluacion obtenga la va.   *
   * Los parametros del algortimo los coge de los valores por defecto dados en el problema
   * @param irMostrandoTerminal boolean Si se quiere ir mostrando por el terminal como va evolucionando
   * @return Particula Mejor particula obtenida
   */
  public Particula pso(boolean irMostrandoTerminal){
    double valoracionObtener = contextoProblema.valorDefectoValorObtener();
    long iteraccionesMax = contextoProblema.valorDefectoIteraccionesMax();
    double cteAtraccion1=contextoProblema.valorDefectoCteAtraccion1();
    double cteAtraccion2=contextoProblema.valorDefectoCteAtraccion2();
    double inercia=contextoProblema.valorDefectoInercia();
    boolean disminucionProgresivaInercia=contextoProblema.valorDefectoDisminucionProgresivaInercia();
    return pso(valoracionObtener,iteraccionesMax,irMostrandoTerminal,cteAtraccion1,cteAtraccion2,inercia,disminucionProgresivaInercia);
  }

  /**
   * @return Particula  Devuelve la mejor particula del enjambre
   */
  public Particula getMejorParticula(){
    return clonMejorInmutable;
  }

  /**
   * @return long Devuelve el numero de iteracciones realizadas del algoritmo
   */
  public long getIteraccionesRealizadas(){
    return iteraccionesRealizadas;
  }

  /**
   * @return double Devuelve la valoracion total del enjambre (la suma de la valoracion de todas las particulas que lo componen)
   */
  public double getValoracionTotalEnjambre(){
    return valoracionTotalEnjambre;
  }

  /**
   * @return int Devuelve el numero de particulas que componen el enjambre
   */
  public int getNumParticulas(){
    return particulas.length;
  }

  /**
   * @return double La velocidad maxima permitida a las particulas
   */
  public double getVelocidadMax(){
    return velocidadMax;
  }

  /**
   * @return double La posicion maxima permitida a las particulas (en cada dimension)
   */
  public double getPosicionMax(){
    return posicionMax;
  }

  /**
   * @return boolean True si es un problema de maximizacion y False si es un problema de minimizacion
   */
  public boolean isMaximizationProblem(){
    return contextoProblema.isMaximizeProblem();
  }
}
