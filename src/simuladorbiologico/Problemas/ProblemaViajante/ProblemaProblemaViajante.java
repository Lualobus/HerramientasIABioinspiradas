package simuladorbiologico.Problemas.ProblemaViajante;
import simuladorbiologico.*;
import simuladorbiologico.Individuos.*;
import fam.utilidades.JTableDoubles;
import java.util.Vector;

/**
* Problema especifico
* establece la funcion de fitness o evaluacion de cada individuo en funcion de
* como se aproxima a la solucion final
*/
public class ProblemaProblemaViajante extends Problema{

  double[][] distanciasCiudades;
  int numCiudades;

  public ProblemaProblemaViajante(){
    JTableDoubles tabla = new JTableDoubles();
    distanciasCiudades = tabla.getDistancias();
    numCiudades = distanciasCiudades[0].length;
  }


   /** FUNCIoN DE FITNESS
     * Evalua la adaptacion del individuo al problema, como de bien lo resuelve
     * @param individuo
     * @return Devuelbe un valor numerico
     * @throws Exception si el individuo no esta soportado
     */
   public double evaluarIndividuo (Individuo individuo){

     double totalDistancia = 0;
     if (individuo instanceof IndividuoPositivoHaploide){
       double[] cromosomas = ((IndividuoPositivoHaploide)individuo)._cromosomas;

       //como queremos que no se repitan las ciudades sustituimos las ciudades repetidas por otras sin repetir
       Vector ciuadesSinVisitar = new Vector();
       for (int i = 0; i < numCiudades; i++){
         ciuadesSinVisitar.add(new Integer(i));
       }

       for (int i = 0; i < cromosomas.length; i++){
	 for (int j = 0; j < cromosomas.length; j++){
           ciuadesSinVisitar.remove(new Integer((int)cromosomas[i]));  //esta ciudad ya tiene visita
	   if ( (i!=j) && (cromosomas[i] ==cromosomas[j])){  //vemos que ya esta visitada
             //elegimos aleatoriamente una de las ciudades no visitadas, y la cambiamos por la repetida
             int aleatorio = (int)(Math.random()*numCiudades)%ciuadesSinVisitar.size();
             int ciudadesEscogida = ((Integer)(ciuadesSinVisitar.get(aleatorio))).intValue();
             cromosomas[j] = ciudadesEscogida;
             ciuadesSinVisitar.removeElementAt(aleatorio);
	   }
	 }
       }

       for (int i = 1; i < cromosomas.length; i++){
	 totalDistancia += distanciasCiudades[(int)cromosomas[i-1]][(int)cromosomas[i]];
       }
     }
     //como queremos minimizar lo que hacemos es restar una cantidad al total de la distancia
     return totalDistancia;
   }

   /**
     * Devuelve los individuos para los cuales soporta implementacion este problema
     * @return Individuo[] Individuos soportados
     */
    public String[] individuosSoporta(){
      String[] individuosSoportados = { "Positivo Haploide"};
      return individuosSoportados;
    }

    /**
     * Para determinar si es un problema de maximizacion o minimizacion
     * @return True si es de maximizar y False si es de minimizar
     */
    public boolean isMaximizeProblem(){
      return false;
    }



//____METODOS NO NECESARIOS SOBRESCRIBIRLOS: si si no se quiere la implementacion por defecto_____

   /**
     * @param invididuo a interpretar segun el problema
     * @return Devuelbe una descripcion del individuo relativa al problema Electoral
     */
   public String interpretacionDelIndividuo(Individuo individuo){
     double[] cromosomas = ((IndividuoPositivoHaploide)individuo)._cromosomas;
     String interpretacion = "El camino mas corto a visitar es: \n";
     for (int i = 0; i < cromosomas.length; i++){
       interpretacion += cromosomas[i]+ " ";
     }
     interpretacion +="\n de longitud " + (evaluarIndividuo(individuo));
     return interpretacion;
   }

    /** @return int Numero de individuos que tendra por defecto la poblacion */
    public int numeroIndividuosPorDefecto(){ return distanciasCiudades.length*2; }

   /** @return int Longitud del cromosoma de los individuos por defecto */
   public int longitudCromosomaPorDefecto(){ return distanciasCiudades.length;}

   /** @return int Numero de individuos contra los que tiene que competir en cada reproduccion por defecto */
    public int presionSelectivaPorDefecto(){return 4;}

   /** @return int Numero de iteracciones necesaria para incrementar en uno la presion selectiva */
   public int numIteraccionesParaAumentarPresionSelectivaPorDefecto(){return 100;}

   /** @return int Numero de individuos que pasan directamente a la siguiente generacion por ser los mejores */
   public int elitismoPorDefecto(){return 1;}

   /** @return String Tipo de sobrecruzamiento que se usa por defecto en el problema */
   public String tipoSobrecruzamientoPorDefecto(){return Reproduccion.sobrecruzamientoPartirAleatio;}

   /** @return boolean Determina si el mejor esta expuesto o no a mutacion */
  public boolean mejorInmutablePorDefecto(){return true;}

   /** @return double Puntuacion a obtener por defecto para detener la simulacion */
   public double puntuacionAObtenerPorDefecto(){return 0;}

   /** @return int Itercaciones maximas a realizar para detener la simulacion */
   public int iteracionesMaximasPorDefecto(){return 100;}

   /** @return boolean True si la solucion puede ser relativa al individuo  */
   public boolean individuoPuedeSerSolucion(){return true;}

   /** @return boolean True Si la solucion puede ser relativa a la poblacion (solucion de Manhatan)*/
   public boolean poblacionPuedeSerSolucion(){return false;}

   /** @return String Ayuda al problema */
   public String obtenerAyuda(){
     return "El problema del viajante (Travel Salesman Problem) trata de visitar todas\n"+
	     "las ciudades pasando una sola vez por cada una y invirtiendo en el camino el menor coste\n"+
	     "\nNOTA: para modificar la matriz de costes modiquela directamente\n"+
	     "en el codigo fuente de la clase que implementa el problema";

   }


   /** Main para probar el problema sin necisidad de interfaz grafica */
   public static void main(String[] args){
      try{
	 Problema problemaProblemaViajante = new ProblemaProblemaViajante(); //instanciamos el problema y su poblacion
	 Poblacion poblacionProblemaViajante = new PoblacionProblemaViajante(problemaProblemaViajante);
	 String[] individuosSoporta = problemaProblemaViajante.individuosSoporta();
	 Individuo individuoSolucion = EjecutaAG.lanzarAlgoritmoAG(poblacionProblemaViajante, individuosSoporta[0]);
      }catch (Exception ex) {
	System.out.println("Error al intentar solucional el problema mediante algoritmos geneticos");
      }
   }
}
