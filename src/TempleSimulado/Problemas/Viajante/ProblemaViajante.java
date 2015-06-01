package TempleSimulado.Problemas.Viajante;
import TempleSimulado.*;
import fam.utilidades.JTableDoubles;

/**
 * Problema del viajante solucionado mediante Simulated Annealign
 */
public class ProblemaViajante extends  Problema{

    /**
     *  Matriz con las distanicas entre cada ciudad, la distancia entre la ciudad 3 y la 2 es
     * la correspondiente en buscar en la fila 3 y columna 2, o viceversa
     */
    private double distancias[][];

    /**
     * Constructor para el problema del viajante (Travel Salesman Problem)
     * @param matrizDistancias int[][] Matriz con las distancias entre cada ciudad
     */
    public ProblemaViajante (double[][] matrizDistancias ){
      this.distancias = matrizDistancias;
    }

    /** Constructor por defecto que construye siempre la misma matriz de distancias para usarla como ejemplo */
    public ProblemaViajante(){
      JTableDoubles tabla = new JTableDoubles();
      this.distancias =  tabla.getDistancias();
    }

    /**
     * Funcion heuristica que representa la codificacion del problema y que se intente minimizar,
     * en este caso se trata de la distancia total recorrida siguiendo el orden
     * de las ciudades que representa el estado.
     *
     * @param estado Estado que se desea evaluar
     * @return double Temperatura asociada al estado
     */
    public double funcionEvaluacionEnergia(Estado estado){
      int[] ordenCiudadesVisitar = ((EstadoViajante)estado).obtenerOrdenCiudadesVisitar();
      double distanciaAcumulada =  0;
      for (int i = 1; i < ordenCiudadesVisitar.length; i++){
	distanciaAcumulada+= distancias[ordenCiudadesVisitar[i-1]][ordenCiudadesVisitar[i]];
      }
      return distanciaAcumulada;
    }

    /**
     * @return Estado Estado desde el que se inicia la solucion del problema
     */
    public Estado estadoInicial(){
      int[] ordenCiudadesVisitar = new int[distancias.length];
      for (int i = 0; i < distancias.length; i++){
	ordenCiudadesVisitar[i] = i;
      }
      return (new EstadoViajante(ordenCiudadesVisitar));
    }



    //______________Metodos que pueden ser sobrescritos para adaptarlos al problema en concreto__________

    /**
    * Valor por defecto con el que se resolvera el problema: "No se ha indicado ningun tipo de informacion para este problema"
    * @return String Informacion sobre el problema
    */
    public String ayuda(){
      return "El problema del viajante (Travel Salesman Problem) trata de visitar todas\n"+
	     "las ciudades pasando una sola vez por cada una y invirtiendo en el camino el menor coste\n"+
	     "\nNOTA: para modificar la matriz de costes modiquela directamente\n"+
	     "en el codigo fuente de la clase que implementa el problema";
  }



  /**______________Probamos el problema__________________________________*/
  public static void main (String args[] ){

    double distancias[][] = {  {0,  7,  15,  3, 1},
			 {7,  0,  4, 16,  9},
			 {15, 4,  0,  5,  2} ,
			 {3,  16, 5,  0,  6},
			 {1,  9,  2,  6,  0}};

    ProblemaViajante problemaViajante = new ProblemaViajante(distancias);
    TempleSimulado temple = new TempleSimulado(problemaViajante);
    Estado solucion = temple.templadoSimulado();
    System.out.println(solucion.descripcionTextualEstado(problemaViajante));

  }


}
