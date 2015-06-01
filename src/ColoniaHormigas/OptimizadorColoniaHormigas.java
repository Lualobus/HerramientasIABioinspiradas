package ColoniaHormigas;

public class OptimizadorColoniaHormigas {

  private Problema problema; /** Problema a resolver */
  private double[][] matrizCostes; /** Matriz de costes entre cada posicion, se definen para cada probelma en concreto */
  private Hormiga[] hormigas; /** Hormigas artificiales que intervienen en el algoritmo */
  private int numPosiciones; /** Numero de posiciones a visitar por las hormigas */
  private int numHormigas; /** Numero de hormigas artificiales */
  private boolean abortar; /** Si se activa aborta la ejecucion del optimizador */

  private int[] trayectoMejor;  /** Mejor trayecto obtenido hasta el momento por cualquier hormiga */
  private double costeTrayectoMejor=Double.MAX_VALUE; /** Coste del trayecto mejor obtenido actualmente */
  private long iteraciones=0; /** Iteracion actuales del algoritmo */
  private double factorExplotacion; /** Probabilidad de realizar explotacion frente a exploracion */
  private boolean factorExplotacionDinamico; /** True: si se puede variar dinamicamente el factor de explotacion por el algoritmo de optimizacion */

  /**
   * Constructor del optimizador
   * @param numHormigas int Numero de hormigas que se van a usar
   * @param problema Problema Problema que se desa resolver, del que se obtendra la matriz de costes
   */
  public OptimizadorColoniaHormigas(int numHormigas, Problema problema) {
    this.problema = problema;
    this.matrizCostes = problema.getCostesEntrePosiciones();
    numPosiciones = matrizCostes.length;
    trayectoMejor=new int[numPosiciones];
    hormigas = new Hormiga[numHormigas];
    this.numHormigas = numHormigas;
    for (int i = 0; i < numHormigas; i++) {
      hormigas[i] = new Hormiga(numPosiciones);
    }
    abortar = false;
  }


  /**
   * Constructor del optimizador que obtiene el numero de hormigas del dado por defecto en la implementacion del problema
   * @param problema Problema Problema que se desa resolver, del que se obtendra la matriz de costes
   */
  public OptimizadorColoniaHormigas(Problema problema) {
    this.problema = problema;
    this.matrizCostes = problema.getCostesEntrePosiciones();
    numPosiciones = matrizCostes.length;
    numHormigas = problema.valorDefectoNumeroHormigas();
    trayectoMejor=new int[numPosiciones];
    hormigas = new Hormiga[numHormigas];
    for (int i = 0; i < numHormigas; i++) {
      hormigas[i] = new Hormiga(numPosiciones);
    }
    abortar = false;
  }


  /**
   * Algoritmo de optimizacion por colonia de hormigas secuencial que se lanza
   * con los parametros por defecto dados por la implementacion del problema
   * @return int[] Posiciones por las que pasa el mejor trayecto encontrado
   */
  public int[] optimizacionSecuencial() {
    long iteracionesMax = problema.valorDefectoIteracionesMax();
    double costeTrayectoDeseado = problema.valorDefectoCosteAObtener();
    double factorEvaporacionLocal = problema.valorDefectoFactorEvaporacionLocal();
    double factorEvaporacionGlobal = problema.valorDefectoFactorEvaporacionGlobal();
    double factorExplotacionInicial = problema.valorDefectoFactorExplotacionInicial();
    boolean incrementarFactorExploracion = problema.valorDefectoIncrementoDinamicoFactorExploracion();
    double costeAproximadoTrayecto = problema.valorDefectoCosteAproximadoTrayecto();
    return optimizacionSecuencial(iteracionesMax, costeTrayectoDeseado, factorEvaporacionLocal, factorEvaporacionGlobal, factorExplotacionInicial, incrementarFactorExploracion, costeAproximadoTrayecto,true);
  }


  /**
   * Algoritmo de optimizacio por colonias de hormigas secuencial (AntColonyOptimization)
   * @param iteracionesMax long Iteraciones maximas del algoritmo
   * @param costeTrayectoDeseado double Coste del mejor trayecto a obtener
   * @param factorEvaporacionLocal double Como de rapido se evapora la ferormona que deja en cada movmiento cada hormiga
   * @param factorEvaporacionGlobal double Como de rapido se evapora el refuerzo de feromona dado al mejor camino hayado hasta el momento
   * @param factorExplotacionInicial double probabilidad de realizar explotacion frente a exploracion
   * @param  incremenarFactorExplotacion boolean Determina si se ira aumentando dinamicamente la probabilidad de realizar explotacion frente a exploracion
   * @param costeAproximadoTrayecto double Estimacion del coste medio de cada trayecto (es un factor inicial que no influye mucho en la convergencia del algoritmo, se puede dar una aproximacion muy "burda")
   * @param mostrarInformacion True muestra en la consola como evoluciona la simulacion
   * @return int[] Posiciones por las que pasa el mejor trayecto encontrado
   */
  public int[] optimizacionSecuencial(long iteracionesMax, double costeTrayectoDeseado, double factorEvaporacionLocal, double factorEvaporacionGlobal, double factorExplotacionInicial, boolean incremenarFactorExplotacion, double costeAproximadoTrayecto, boolean mostrarInformacion) {

    //matriz con las ferormonas entre cada posicion a visitar
    double[][] matrizFeromona = new double[matrizCostes.length][matrizCostes[0].length];
    //mejor trayecto conseguido hasta el momento
    int[] trayectoMejorIteracion;
    double costeMejorTrayectoIteracion;

    this.factorExplotacion = factorExplotacionInicial;
    this.factorExplotacionDinamico = incremenarFactorExplotacion;

    //inicializamos la matriz de feromonas con el valor por defecto
   // double valorFeromonaDefecto = 20;
    double valorFeromonaDefecto = 1 / (numPosiciones * costeAproximadoTrayecto);
    for (int i = 0; i < numPosiciones; i++) {
      for (int j = 0; j < numPosiciones; j++) {
	matrizFeromona[i][j] = valorFeromonaDefecto;
      }
    }

    //colocamos aleatoriamente cada hormiga
    int posicionAleatoria;
    for (int i = 0; i < numHormigas; i++) {
      posicionAleatoria = new Double(Math.random()*numPosiciones).intValue();
      hormigas[i].setPosicionOrigen(posicionAleatoria);
    }

    //iteracciones del algoritmo
    iteraciones = 0;
    do {

      //ACTUALIZACION LOCAL DE FEROMONAS: calculamos un trayecto completo para cada hormiga
      for (int numPosVisitadas = 0; numPosVisitadas < numPosiciones-1; numPosVisitadas++) {
	for (int i = 0; i < numHormigas; i++) {
	  int posActual = hormigas[i].getPosicion();
	  //elegimos la proxima ciudad a visitar
	  int posSiguiente = hormigas[i].proximaPosicion(matrizCostes, matrizFeromona, factorExplotacion);
	  /*cambiamos la posicion de la hormiga a esa nueva posicion (cambiandola de posicion, anyadiendo la nueva posicion a su lista de visitadas e incrementando el coste acumulado del trayecto)*/
	  hormigas[i].setPosicion(posSiguiente, matrizCostes[posActual][posSiguiente]);
	  //incremntamos el valor de feromona en ese nuevo movimiento
	  matrizFeromona[posActual][posSiguiente] = ((1 - factorEvaporacionLocal) * matrizFeromona[posActual][posSiguiente]) + (factorEvaporacionLocal * valorFeromonaDefecto);
	}
      }

      //vemos cual es el trayecto menos costoso realizado por las hormigas en esta iteraccion
      int mejorHormiga = 0;
      for (int i = 0; i < numHormigas; i++) {
	if ( hormigas[i].getCosteTrayecto() < hormigas[mejorHormiga].getCosteTrayecto()) { //intentamos minimizar el coste del trayecto
	  mejorHormiga = i;
	}
      }

      //vemos si el mejor trayecto de esta iteracion es el mejor que habia de iteraciones anteriores
      trayectoMejorIteracion = hormigas[mejorHormiga].getPosicionesVisitadas();
      costeMejorTrayectoIteracion = hormigas[mejorHormiga].getCosteTrayecto();
      //System.out.println("Mejor trayecto iteracion: " + costeMejorTrayectoIteracion);
      //System.out.println("Mejor global: " + costeTrayectoMejor);
      if (costeMejorTrayectoIteracion < costeTrayectoMejor){
	costeTrayectoMejor=costeMejorTrayectoIteracion;
	for (int i = 0; i < trayectoMejorIteracion.length; i++){
	  trayectoMejor[i] = trayectoMejorIteracion[i];
	}
      }

      //ACTUALIZACION GLOBAL FEROMONAS: reforzamos el mejor trayecto obtenido con ferormonas y el resto evaporamos
      for (int i = 0; i < numPosiciones; i++){
        for (int j =0; j < numPosiciones; j++){
          if (mostrarInformacion) System.out.print(Math.round(matrizFeromona[i][j])+ " ");
          //no es un arco del mejor trayecto
          if (!estaEnTrayectoMejor(i,j,trayectoMejor)){
            matrizFeromona[i][j]=(1-factorEvaporacionGlobal)*matrizFeromona[i][j];
          //es un arco del mejor trayecto
          }else{
            matrizFeromona[i][j]=(1-factorEvaporacionGlobal)*matrizFeromona[i][j]+(factorEvaporacionGlobal*(costeTrayectoMejor));
          }
        }
        if (mostrarInformacion) System.out.println();
      }

      //borramos los trayectos realizados por las hormigas y su coste
      for (int i = 0; i < numHormigas; i++) {
        posicionAleatoria = new Double(Math.random()*numPosiciones).intValue();
        hormigas[i].setPosicionOrigen(posicionAleatoria);
	hormigas[i].reiniciarTrayecto();
      }

      //actualizamos el numero de iteraciones realizadas
      iteraciones++;

      if (incremenarFactorExplotacion)  actualizarFactorExplotacion(iteracionesMax);

      if (mostrarInformacion) System.out.println("iteraciones: " + iteraciones + " coste trayecto mejor: " + costeTrayectoMejor+ " factorExplotacion: " + factorExplotacion);
      Thread.yield();
    } while ((iteraciones < iteracionesMax) && (costeTrayectoMejor > costeTrayectoDeseado) && !abortar);
    return trayectoMejor;
  }

  /** Mira si un arco esta en el trayecto mejor */
  public boolean estaEnTrayectoMejor  (int primera , int segunda, int[] trayectoMejor){
    if (primera == segunda) return false;
    boolean estaEnTrayecto = false;
    for (int i =0; i < numPosiciones-1; i++){
      if (trayectoMejor[i]==primera && trayectoMejor[i+1]== segunda){
        estaEnTrayecto = true;
      }
    }
    return estaEnTrayecto;
  }

  /**
   * @return double Coste del mejor trayecto encontrado hasta el momento
   */
  public double getCosteTrayectoMejor() {
    return costeTrayectoMejor;
  }

  public boolean isFactorExplotacionDinamico() {
    return factorExplotacionDinamico;
  }

  /**
   * @return long Iteraciones realizadas actualmente del algoritmo
   */
  public long iteracionesActuales() {
    return iteraciones;
  }

  /**
   * Para la ejecucion de la simulacion se se esta ejecutando desde otro hilo
   */
  public void abortarSimulacion() {
    this.abortar = true;
  }

  /** Establece un valor para el factor de explotacion */
  public void setFactorExplotacion (double valor){
    System.out.println("Nuevo valor factor explotacion: " + valor);
    this.factorExplotacion = valor;
  }

  /** Establece si se puede variar el factor de explotacion durante la ejecucion del optimizador */
  public void setFactorExplotacionDinamico(boolean factorExplotacionDinamico) {
    System.out.println("Factor de explotacion dinamico: " + factorExplotacionDinamico);
    this.factorExplotacionDinamico = factorExplotacionDinamico;
  }

  /** Actualiza el valor del factor de explotacion */
  private void actualizarFactorExplotacion(long iteracionesMax) {
    //actualizamos el valor dinamico de la probabilidad de explotacion
    try{
      if ((factorExplotacion < 1) && (iteraciones % (iteracionesMax / 10) == 0)) {
        factorExplotacion += 0.08;
      }
    }catch (Exception e){}
  }

  /**
   * @return int[] Devuelve el mejor trayecto encontrado hasta el momento
   */
  public int[] getMejorTrayectoActual(){
    return trayectoMejor;
  }

  /**
   * @return String Devuelve el mejor trayecto encontrado hasta el momento
   */
  public String getStringMejorTrayectoActual(){
    String aDevolver="";
    for (int i = 0; i < trayectoMejor.length; i++){
      aDevolver+=trayectoMejor[i]+" ";
    }
    return aDevolver.trim();
  }
}
