package OptimizadorEnjambreParticulas;

/**
 * Particula que forma parte del enjambre
 */
public class Particula {

  private int numDimensiones;                      /** Numero de dimensiones en las que se mueve la particula*/
  private double velocidad[];                      /** Vector con la velocidad en que se mueve en cada componente dimensional */
  private double posicion[];                       /** Vector con la posicion en cada componente dimensional */
  private double valoracion;                       /** Valoracion obtenida para esta particula y el problema en que se esta evaluando */
  private double mejorPosicionAlcanzada[];         /** Mejor posicion que ha alcanzado la particula en todo su trayecto (memoria) */
  private double valoracionMejorPosicionAlcanzada; /** Valoracion de la mejor posicion que ha podido alcanzar la particula */
  private Enjambre enjambrePertenece;              /** Enjambre al que pertenece esta particula*/


  /**
   * Constructor: crea una particula en una posicion y velocidad aleatoria
   * @param numDimensiones int Numero de dimensiones
   * @param enjambre Enjambre Enjambre al que pertenece
   */
  public Particula(int numDimensiones, Enjambre enjambre){

    this.numDimensiones = numDimensiones;
    this.velocidad = new double[numDimensiones];
    this.posicion = new double[numDimensiones];
    this.mejorPosicionAlcanzada = new double[numDimensiones];  //cambiar a la mejor alcanzada por el mismo
    this.enjambrePertenece = enjambre;

    //inicializamos aleatoriamente la posicion, velocidad y mejorPosicionObservada
    for (int i = 0; i < numDimensiones; i++) {
      posicion[i] = Math.random();
      velocidad[i] = Math.random();
      mejorPosicionAlcanzada[i] = posicion[i];
    }
  }

  /**
   * Actualiza la posicion sumandole la velocidad
   */
  public void actualizarPosicion(){
    int vecesSobrepaso=0;
    for (int i = 0; i < numDimensiones; i++) {
      posicion[i] = posicion[i] + velocidad[i];
      //limitamos la posicion (si se sobrepasa el limite la rebotamos contra la pared)
      if (posicion[i] > 0) {
        if (posicion[i] > enjambrePertenece.getPosicionMax()) {
          //posicion[i] = posicion[i] - 2 * velocidad[i];
          posicion[i] = enjambrePertenece.getPosicionMax();
          vecesSobrepaso++;
        }
      }else{
        if (posicion[i] < -(enjambrePertenece.getPosicionMax())) {
          //posicion[i] = posicion[i] + 2 * velocidad[i];
          posicion[i] = -enjambrePertenece.getPosicionMax();
          vecesSobrepaso++;
        }
      }
    }
    //System.out.println("veces sobrepaso: " + vecesSobrepaso);
  }
  /**
   * Actualiza la velocidad teniendo en cuenta la velocidad anterior, la mejor posicion observada y la mejor posicion del enjambre
   * @param inercia double Valor de inercia: cuanto mayor sea, mas influye la velocidad anterior (exploracion), cuanto menor sea menos influye la velocidad anterior (explotacion)
   * @param constanteAtraccion1 double Indica cuanto atrae a la particula la posicion suya historica
   * @param constanteAtraccion2 double Indica cuanto atrae a la particula la posicion mejor del enjambre
   */
  public void actualizarVelocidad(double inercia, double constanteAtraccion1, double constanteAtraccion2){
    for (int i = 0; i < numDimensiones; i++){
      velocidad[i] = inercia * velocidad[i] + (constanteAtraccion1 * Math.random()) * (mejorPosicionAlcanzada[i] - posicion[i])
                     + (constanteAtraccion2 * Math.random()) * (enjambrePertenece.getMejorParticula().posicion[i] - posicion[i]);
      //limitamos el valor de la velocidad (si se supera el maximo la reiniciamos aleatoriamente)
      if (velocidad[i] > 0) {
        if (velocidad[i] > enjambrePertenece.getVelocidadMax()) {
          velocidad[i] = Math.random();
        }
      } else {
        if (velocidad[i] < -(enjambrePertenece.getVelocidadMax())) {
          velocidad[i] = -Math.random();
        }
      }
    }
  }

  /**
   * Establece una valoracion a la particula
   * @param valoracion double Valoracion a asignar
   */
  public void setValoracion(double valoracion){
    this.valoracion = valoracion;
  }

  /**
   * @return double Devuelve la valoracion de la mejor posicion alcanzada en toda su historia
   */
  public double getMejorValoracionAlcanzada(){
    return valoracionMejorPosicionAlcanzada;
  }

  /**
   * Establece el valor de la mejor posicion alcanzada a lo largo de vida de la particula
   * @param valor double Valor a asignar
   */
  public void setMejorValoracionAlcanzada(double valor){
    valoracionMejorPosicionAlcanzada = valor;
  }

  /**
   * @return double Devuelve la valoracion de la particula
   */
  public double getValoracion(){
    return valoracion;
  }

  /**
   * Establece la mejor posicion que se ha podido alcanzar por la particula
   * @param posicion double[] Posicion observada
   */
  public void setMejorPosicionAlcanzada(double[] posicion){
    mejorPosicionAlcanzada = posicion;
  }

  /**
   * @return double[] Posicion actual de la particula en cada componente
   */
  public double[] getPosicion(){
    return posicion;
  }

  /**
   * @return double[] Velocidad actual de la particula en cada componente
   */
  public double[] getVelocidad(){
    return velocidad;
  }

  /**
   * @return int Numero de dimensiones en las que se "flota" la particula
   */
  public int getDimensiones(){
    return numDimensiones;
  }

  /** Devuelve un clon de la particula actual */
  public Particula clon(){
    Particula particulaClon = new Particula(numDimensiones,enjambrePertenece);
    for (int i = 0; i < numDimensiones; i++){
      particulaClon.posicion[i] = posicion[i];
      particulaClon.velocidad[i] = velocidad[i];
    }
    particulaClon.valoracion = valoracion;
    particulaClon.mejorPosicionAlcanzada = mejorPosicionAlcanzada;
    return particulaClon;
  }


}
