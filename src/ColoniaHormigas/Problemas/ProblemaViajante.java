package ColoniaHormigas.Problemas;

import ColoniaHormigas.*;
import fam.utilidades.JTableDoubles;

/**
 * Implementa el problema del viajante, para ello debemos darle la matriz con
 * las distancias que separa cada ciuadad del resto de ciudades
 */
public class ProblemaViajante extends Problema{

  double[][] distanciasCiudades;

  public ProblemaViajante(){
    JTableDoubles tabla = new JTableDoubles();
    distanciasCiudades = tabla.getDistancias();
  }

  /**
   * Debe definirse para cada problema en concreto la matriz de costes
   * @return int[] Matriz de costes definiada para el problema concreto
   */
  public double[][] getCostesEntrePosiciones(){
    return distanciasCiudades;
  }

  //_____________Metodos que pueden ser sobrescritos_______________________
  public int valorDefectoNumeroHormigas(){ return (distanciasCiudades.length); }
  public long valorDefectoIteracionesMax(){ return  100; }
  public double valorDefectoCosteAObtener(){ return 0; }
  public double valorDefectoFactorEvaporacionLocal(){ return 0.1; }
  public double valorDefectoFactorEvaporacionGlobal(){ return 0.1; }
  public double valorDefectoCosteAproximadoTrayecto(){ return 2000; }
  public double valorDefectoFactorExplotacionInicial(){ return 0.1; }
  public boolean valorDefectoIncrementoDinamicoFactorExploracion(){ return true; }
  public String ayuda(){
    return "Este problema consiste en encontrar el orden de ciudades a visitar\n"+
	   "que implique menor coste, teniendo en cuenta que\n"+
	   "unicamente se podra visitar una sola vez cada ciudad\n"+
	   "y todas deben ser visitadas en el trayecto completo"+
	   "\nNOTA: para modificar la matriz de costes modiquela directamente\n"+
	   "en el codigo fuente de la clase que implementa el problema";
   }

  /** Probando el problema */
  public static void main(String args[]){
    ProblemaViajante problemaViajante = new ProblemaViajante();
    OptimizadorColoniaHormigas aco = new OptimizadorColoniaHormigas(problemaViajante);
    aco.optimizacionSecuencial();
    int[] trayectoSolucion = aco.optimizacionSecuencial();
    for (int i = 0; i < trayectoSolucion.length; i++){
      System.out.println("Posicion visitada: " + trayectoSolucion[i]);
    }
  }

}
