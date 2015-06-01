package simuladorbiologico.Problemas.ConseguirUnos;
import simuladorbiologico.*;
import simuladorbiologico.Individuos.*;

/**
 * Contexto especifico para nuestro problema de conseguir el mayor numero de 1's,
 * establece la funcion de fitness o evaluacion de cada individuo en funcion de
 * como se aproxima a la solucion final
 */
public class ProblemaConseguirUnos extends Problema {

  /**
   * Evalua la adaptacion del individuo al problema, como de bien lo resuelve
   * @param individuo
   * @return Devuelbe un valor numerico
   * @throws Exception si el individuo no esta soportado
   */
  public double evaluarIndividuo (Individuo individuo){
    double total=0;
    if (individuo instanceof IndividuoBinarioHaploide){
      byte[] cromosomas = ((IndividuoBinarioHaploide)individuo)._cromosomas;
      int longitud = cromosomas.length;
      for (int ind = 0; ind < longitud; ind++){
	total+=cromosomas[ind];
      }
    }else  if (individuo instanceof IndividuoBinarioDiploide){
      byte[][] cromosomas = ((IndividuoBinarioDiploide)individuo)._cromosomas;
      int longitud = cromosomas[0].length;
      for (int i =0; i < cromosomas.length; i++){
	for (int j = 0; j< longitud; j++){
	  total+=cromosomas[i][j];
	}
      }
      total = total/2;
    }
    return total;
  }

  /**
   * Devuelve los individuos para los cuales soporta implementacion este problema
   * @return Individuo[] Individuos soportados
   */
  public String[] individuosSoporta(){
    String[] individuosSoportados = { "Binario Haploide","Binario Diploide"};
    return individuosSoportados;
  }

  /**
   * Para determinar si es un problema de maximizacion o minimizacion
   * @return True si es de maximizar y False si es de minimizar
   */
 public boolean isMaximizeProblem(){
   return true;
 }


  /**
  * @param invididuo a interpretar segun el problema
  * @return Devuelbe una descripcion del individuo relativa al problema Electoral
  */
  public String interpretacionDelIndividuo(Individuo individuo){
    return new String ("Este individuo tiene " + evaluarIndividuo(individuo) +" unos");
  }

   /** @return int Numero de individuos que tendra por defecto la poblacion */
  public int numeroIndividuosPorDefecto(){ return 30; }

  /** @return long Longitud del cromosoma de los individuos por defecto */
  public int longitudCromosomaPorDefecto(){ return 1000;}

  /** @return double Puntuacion a obtener por defecto para detener la simulacion */
  public double puntuacionAObtenerPorDefecto(){return longitudCromosomaPorDefecto();}

  /** @return int Itercaciones maximas a realizar para detener la simulacion */
  public int iteracionesMaximasPorDefecto(){return 100;}

  /** @return boolean True si la solucion puede ser relativa al individuo  */
  public boolean individuoPuedeSerSolucion(){return true;}

  /** @return boolean True Si la solucion puede ser relativa a la poblacion (solucion de Manhatan)*/
  public boolean poblacionPuedeSerSolucion(){return true;}

  /** @return String Ayuda al problema */
  public String obtenerAyuda(){
    return "Se trata de maximizar el numero de 1's (en un cromosoma solucion o en toda la poblacion solucion)\n"+
      "El cromosoma de cada invididuo tendra 0's o 1's en cada posicion (inicialmente aleatoriamente) \n"+
      "Los individuos se evalua en funcion del numero de 1's que tengan \n"+
      "Tamanyo del cromosoma: indica el numero de 1's y 0's que tiene cada inviduo\n"+
      "Tipo de solucion: puede ser un individuo, o toda la poblacion\n"+
      "Puntuacion: numero total de 1's (en un individuo o en toda la poblacion)";
  }


  /** Main para probar el problema sin necisidad de interfaz grafica */
  public static void main(String[] args){
      try {
	Problema problemaConseguirUnos = new ProblemaConseguirUnos(); //instanciamos el problema y su poblacion
	Poblacion poblacionConseguirUnos = new PoblacionConseguirUnos(problemaConseguirUnos);
	String[] individuosSoporta = problemaConseguirUnos.individuosSoporta();
	Individuo individuoSolucion = EjecutaAG.lanzarAlgoritmoAG(poblacionConseguirUnos, individuosSoporta[0]); //lanzamos el algoritmo genetico
      } catch (Exception ex) {
	System.out.println("Error al intentar solucional el problema mediante algoritmos geneticos\n");
      }
  }
}


