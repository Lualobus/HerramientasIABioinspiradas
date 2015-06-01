package OptimizadorEnjambreParticulas.Problemas;

import OptimizadorEnjambreParticulas.*;
import fam.utilidades.JTableDoubles;
import java.util.Vector;

public class ProblemaProblemaViajante extends Problema {

  double[][] distanciasCiudades;


  public ProblemaProblemaViajante() {
    JTableDoubles tabla = new JTableDoubles();
    distanciasCiudades = tabla.getDistancias();
    try {
      jbInit();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }


  /**
   * @param particula Particula Particula a evaluar
   * @return double      * Devuelve la distancia recorrida por el camino que representa la particula, deuvelve cero si el camino no es valido al repetir ciudades
   */
  public double funcionEvaluacion(Particula particula) {

      double totalDistancia = 0;
      double[] posiciones = particula.getPosicion();
      double[] velocidad = particula.getVelocidad();
      int numDimensiones = particula.getDimensiones();
      try {

      //las hacemos positivas y dentro del rango que nosotros queremos
      int[] posicionesEnteras = normalizarParticula(posiciones, numDimensiones);


      //como queremos que no se repitan las ciudades las repetidas las sustituimos por otras sin repetir
     Vector ciuadesSinVisitar = new Vector();
     for (int i = 0; i < numDimensiones; i++){
       ciuadesSinVisitar.add(new Integer(i));
     }


     for (int i = 0; i < numDimensiones; i++){
       for (int j = 0; j < numDimensiones; j++){
         ciuadesSinVisitar.remove(new Integer(posicionesEnteras[i]));  //esta ciudad ya tiene visita
         if ( (i!=j) && (posicionesEnteras[i] ==posicionesEnteras[j])){  //vemos que ya esta visitada
           //elegimos aleatoriamente una de las ciudades no visitadas, y la cambiamos por la repetida
           int aleatorio = (int)(Math.random()*numDimensiones)%ciuadesSinVisitar.size();
           int ciudadesEscogida = ((Integer)(ciuadesSinVisitar.get(aleatorio))).intValue();
           posicionesEnteras[j] = ciudadesEscogida;
           ciuadesSinVisitar.removeElementAt(aleatorio);
         }
       }
     }


      //establecemos la posicion nueva de la particula
      for (int i=0; i < numDimensiones; i++){
        posiciones[i] = posicionesEnteras[i];
      }


      //evaluamos la particula
      for (int i = 1; i < numDimensiones; i++) {
        if (posicionesEnteras[i] >=0 && posicionesEnteras[i-1] >= 0){
          totalDistancia += distanciasCiudades[posicionesEnteras[i - 1]][posicionesEnteras[i]];
        }else{
          System.out.println("ATENCIOOOOON POSICIONES NEGATIVATAS: " + posicionesEnteras[i] + "  " + posicionesEnteras[i - 1]);
          System.exit(-1);
        }
      }
      //System.out.println("3");


    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
      return totalDistancia;
  }

  /**
   * Determinamos que es un problema de minimizacion
   * @return False porque es de minimizar
   */
  public boolean isMaximizeProblem(){
    return false;
  }


  private int[] normalizarParticula(double[] posiciones, int numDimensiones) {
    int[] posicionesEnteras = new int[numDimensiones];

    //normalizamos la posicion para ajustarlo a numeros enteros y dentro del rango que nos es valido (entero positivos y con valor maximio el de numero de dimensiones
    for (int i = 0; i < numDimensiones; i++){
      //les hacemos enteros
      posicionesEnteras[i] = (int)posiciones[i];

      //hacemos positivo
      posicionesEnteras[i]=Math.abs(posicionesEnteras[i]);
    }
    return posicionesEnteras;
  }

  /** Main para probar el problema sin necisidad de interfaz grafica */
  public static void main(String[] args) {
    Problema problemaProblemaViajante = new ProblemaProblemaViajante(); //instanciamos el problema
    Particula particulaSolucion = EjecutaPSO.lanzarPSO(problemaProblemaViajante); //lanzamos el algoritmo
    double[] solucion = particulaSolucion.getPosicion(); //obtenemos la solucion
  }


  /** Se tienen tantas dimensiones como pesos la red de neuronas */
  public int valorDefectoNumDimensiones() {
    return distanciasCiudades.length;
  }

  /* El valor por defecto es de 100 particulas sino se sobrescribe */
  public int valorDefectoNumParticulas() {
    return valorDefectoNumDimensiones()-1;
  }

  /* El valor por defecto es de 0.9 sino se sobrescribe */
  public double valorDefectoCteAtraccion1() {
    return 0.5;
  }

  /* El valor por defecto es de 0.9 sino se sobrescribe */
  public double valorDefectoCteAtraccion2() {
    return 0.5;
  }

  /* El valor por defecto es de 0.9 sino se sobrescribe */
  public double valorDefectoInercia() {
    return 0.7;
  }

  /* El valor por defecto de la puntuaciona obtener es de 1000 sino se sobrescribe */
  public double valorDefectoValorObtener() {
    return 0;
  }

  /* El valor por defecto del numero de iteracciones max es de 100 sino se sobrescribe */
  public long valorDefectoIteraccionesMax() {
    return 3000;
  }

  /** El valor por defecto de velocidad maxima que puede alcanzar una particula */
  public double valorDefectoVelocidadMax(){
    return Double.MAX_VALUE;
  }

  /** El valor por defecto de la posicion maxima que puede alcanzar una particula */
  public double valorDefectoPosicionMax(){ return valorDefectoNumDimensiones()-1; }

  /* El valor por defecto es de true sino se sobrescribe */
   public boolean valorDefectoDisminucionProgresivaInercia(){
     return false;
   }


  /** Sino se sobrescribe simplemente se devuelve que no se tiene informacion para este problema*/
  public String ayuda() {
    return "Se esta intentando resolver el problema del viajante con Enjambres de particulas \n" + "para ello cada particula se movera en un espacio de tantas dimensiones como ciudades a visitar. \n" + "La posicion de la particula en el espacio n-dimensional determina el orden de las ciudades que visita su codificacion: \n" +
        "por ej la particula en el espacio: 4,0,2,1,3 codifica el orden de visita a las ciudades 4,0,2,1,3 por ese orden" + "\nNOTA: para modificar la matriz de costes modiquela directamente\n" + "en el codigo fuente de la clase que implementa el problema";
  }


  public String interpretracionParticula(Particula particula) {
    String interpretarcion = "El trayecto a seguir es: " ;
    double[] posiciones = particula.getPosicion();
    int[] posicionesEnteras = normalizarParticula(posiciones,posiciones.length);
    for (int i = 0; i < posicionesEnteras.length; i++) {
      interpretarcion +=  posicionesEnteras[i] + " ";
    }
    double costeTrayecto = funcionEvaluacion(particula);
    interpretarcion+= "\nCon un coste de : " +  costeTrayecto;
    return interpretarcion.trim();
  }

  private void jbInit() throws Exception {
  }
}
