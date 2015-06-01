package ColoniaHormigas;

import java.util.Vector;

public class Hormiga {

  private double costeTrayecto; /** Coste acumulado del trayecto */
  private int[] posicionesVisitadas; /** Trayecto realizado hasta el momento */
  private boolean[] estaVisitada; /** Para cada ciudad se tiene un booleano para saber rapidamente si ha sido o no visitada */
  private int indicePosActual;  /** Indica en que posicion del array de posiciones visitadas insertaremos la proxima posicion*/
  private int posicionActual; /** Posicion actual de la hormiga */
  private int posicionOrigen; /** Posicion desde la que a iniciado el trayecto */

  /**
   * Constructor
   * @param numPosiciones Numero de posiciones a visitar
   */
  public Hormiga(int numPosiciones) {
    posicionesVisitadas = new int[numPosiciones];
    estaVisitada = new boolean[numPosiciones];
    for (int i = 0; i < numPosiciones; i++){
      posicionesVisitadas[i] = -1;
      estaVisitada[i] = false;
    }
    costeTrayecto = 0;
    indicePosActual = 0;
  }

  /**
   * Mueve la hormiga a una nueva posicion, posiciion que pasa a quedar como visitada
   * @param posicionNueva int Posicion nueva a visitar
   * @param costeMovimiento double Coste de movernos a esa nueva posicion
   */
  public void setPosicion(int posicionNueva, double costeMovimiento) {
    posicionesVisitadas[indicePosActual++] = posicionNueva;
    estaVisitada[posicionNueva] = true;
    posicionActual = posicionNueva;
    costeTrayecto += costeMovimiento;
  }

  /**
   * @return int Posicion actual de la hormiga
   */
  public int getPosicion() {
    return posicionActual;
  }

  /** Se coloca la hormiga en una posicion de origen */
  public void setPosicionOrigen(int posicion) {
    this.posicionOrigen = posicion;
    this.posicionActual = posicion;
    for (int i = 0; i < posicionesVisitadas.length; i++){
      posicionesVisitadas[i] = -1;
      estaVisitada[i] = false;
    }
    this.posicionesVisitadas[0] = posicionOrigen;
    this.estaVisitada[posicionOrigen] = true;
    this.indicePosActual = 1;
    this.costeTrayecto = 0;
  }

  /**
   * @return int Devuelve la posicion desde la que comenzo el trayecto actual
   */
  public int getPosicionOrigen() {
    return posicionOrigen;
  }

  /**
   * @return int[] Posiones que ha visitado la hormiga en el trayecto actual
   */
  public int[] getPosicionesVisitadas() {
    return posicionesVisitadas;
  }

  /**
   * @return double Coste total del trayecto realizado hasta el momento
   */
  public double getCosteTrayecto() {
    return costeTrayecto;
  }

  /**
   * Reinicia el trayecto colocando la hormiga en la posicion origen, poniendo el coste
   * del trayecto a cero, y ninguna ciudad visitada
   */
  public void reiniciarTrayecto() {
     this.posicionActual = posicionOrigen;
    for (int i = 0; i < posicionesVisitadas.length; i++){
      posicionesVisitadas[i] = -1;
      estaVisitada[i] = false;
    }
    this.posicionesVisitadas[0] = posicionOrigen;
    estaVisitada[posicionOrigen] = true;
    this.indicePosActual = 1;
    this.costeTrayecto = 0;
  }

  /**
   * Decide cual es la proxima posicion que es conveniente visitar
   * @return int Proxima posicion que se deberia visitar
   */
  public int proximaPosicion(double[][] feromonas, double[][] costes, double factorExplotacion) {

    final double constFeromona2Distancia =1;

    int numCiudades = posicionesVisitadas.length;
    int mejorSiguiente = 0;

    if (Math.random() < factorExplotacion) { //realizamos explotacion

      //vemos de entre las ciudades no visitadas cual es la que mejor puntuacion obtiene
      double mejorPuntuacion = 0, puntuacion;
      for (int i = 0; i < numCiudades; i++) {
	if (!estaVisitada[i]) {
	  puntuacion = feromonas[posicionActual][i] * Math.pow((1 / costes[posicionActual][i]),constFeromona2Distancia);
	  if (puntuacion > mejorPuntuacion) {
	    mejorPuntuacion = puntuacion;
	    mejorSiguiente = i;
	  }
	}
      }
      //System.out.println("EXPLOTACION: "+  mejorSiguiente);
    } else { //realizamos exploracion

      double numerador, denominador, puntuacion, mejorPuntuacion = 0;
      for (int i = 0; i < numCiudades; i++) {
	if (!estaVisitada[i]) {
	  numerador = (feromonas[posicionActual][i] * Math.pow((1 / costes[posicionActual][i]),constFeromona2Distancia));
	  denominador = 0;
	  for (int j = 0; j < numCiudades; j++) {
	    if (!estaVisitada[j]) {
	      denominador += (feromonas[posicionActual][j] * Math.pow((1 / costes[posicionActual][j]),constFeromona2Distancia));
	    }
	  }
	  puntuacion = numerador / denominador;
	  if (puntuacion > mejorPuntuacion) {
	    mejorPuntuacion = puntuacion;
	    mejorSiguiente = i;
	  }
	}
      }
      //System.out.println("Exploracion: "+ mejorSiguiente);
    }
    return mejorSiguiente;
  }

  /**
   * @return int Numero de posiciones visitadas en el trayecto actual
   */
  public int numPosicionesVisitadas() {
    int num= 0;
    for (int i = 0; i < posicionesVisitadas.length; i++){
      if (posicionesVisitadas[i] != -1){
	num++;
      }
    }
    return num;
  }

}
