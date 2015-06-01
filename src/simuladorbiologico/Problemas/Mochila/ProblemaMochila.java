package simuladorbiologico.Problemas.Mochila;
import simuladorbiologico.*;
import simuladorbiologico.Individuos.*;



/**
 * Contexto especifico para el problema de la mochila, que consiste en introducir en una mochila
 * tantos objetos como se pueda para maximizar una cantidad, cada tipo de objeto a introducir tiene un peso
 * determinado, y se introduciran objetos de tal manera que se acerque lo mas posible al peso maximo soportado
 * por la cartera pero sin sobrepasarlo
 */
public class ProblemaMochila extends Problema {

  double _pesoMaximo=-1; //peso maximo soportado por la mochila
  double[] _peso=null; //peso de cada uno de los elementos

  public ProblemaMochila(){

      if (_pesoMaximo==-1 || _peso==null){
	  //pedimos el peso maximo soportado por la mochila
	  boolean error;
	  String leido;
	  do{
	    error = false;
	    leido = javax.swing.JOptionPane.showInputDialog(null,"Introduce peso maximo soportado por la mochila (mayor o = que la puntuacion a obtener)");
	    try{ _pesoMaximo = Integer.parseInt(leido); }catch (Exception e){ error = true;}
	  }while (error);

	  //pedimos los pesos de cada tipo de objeto que puede introducirse en la mochila
	  int numPesos;
	  do{
	    error = false;
	    leido = javax.swing.JOptionPane.showInputDialog(null,"Introduce los pesos de cada tipo de objeto (tantos pesos como tamanyo del cromosoma)");
	    java.util.StringTokenizer st = new java.util.StringTokenizer(leido);
	    numPesos = st.countTokens();
	    _peso= new double[numPesos];
	    String token;
	    for (int i = 0; st.hasMoreTokens() && !error; i++){
	      token = st.nextToken();
	      try{
		_peso[i] = Double.parseDouble(token);
	      }catch (Exception e){
		System.out.println("Hay error en el parseo");
		error = true;
	      }
	    }
	  }while (error);
	}
    }


    /**
     * Este metodo sirve para recibir los parametros si ya nos los dan de tal manera que no hace falta pedirlos
     */
    public void asignarArgumentos(Object[] argumentos) throws Exception{
     _pesoMaximo = ((Double)argumentos[0]).doubleValue();
     _peso = (double[])argumentos[1];
    }

    /**
     * Evalua la adaptacion del individuo al problema, como de bien lo resuelve
     * @param individuo
     * @return Devuelve un valor numerico con la puntuacion del individuo (peso total que lleva, y si supera el maximo permitido se devuelbe 0)
     * @throws Exception Si el individuo no esta soportado para este contexto
     */
    public double evaluarIndividuo(Individuo individuo){
      double total = 0;

      //------------------------para individuos Binarios Haploides-------------------
      if (individuo instanceof IndividuoBinarioHaploide){
	byte[] cromosomas = ((IndividuoBinarioHaploide)individuo)._cromosomas;
	int longitud = cromosomas.length;
	for (int ind = 0; ind < longitud; ind++){
	  total += cromosomas[ind]*_peso[ind];  //en cromosomas[ind] aparecera el numero de veces que interviene dicho tipo de objeto en la mochila
	}

      //----------------------para individuos Positivos Haploides-------------------

      }else if (individuo instanceof IndividuoPositivoHaploide){
	double[] cromosomas = ((IndividuoPositivoHaploide)individuo)._cromosomas;
	int longitud = cromosomas.length;
	for (int ind = 0; ind < longitud; ind++){
	  total += cromosomas[ind]*_peso[ind];  //en cromosomas[ind] aparecera el numero de veces que interviene dicho tipo de objeto en la mochila
	}
      }

      //si el peso acumulado supera el peso soportado por la mochila, devolvemos ese mismo valor pero en negativo
      if(total>_pesoMaximo){
	return -total;
      }else{
        return total;
      }

    }

    /**
     * Para determinar si es un problema de maximizacion o minimizacion
     * @return True si es de maximizar y False si es de minimizar
     */
    public boolean isMaximizeProblem(){
      return true;
    }



    /**
     * Devuelbe una descripcion textual de como es el individuo para este problema
     * (Se hace una descripcion a nivel de fenotipo)
     * @param invididuo
     * @return La interpretacion que se hace en este contexto del genotipo del individuo (es decir el fenotipo)
     * @throws Exception si el individuo no esta soportado por este contexto
     */
    public String interpretacionDelIndividuo(Individuo individuo) {
      String descripcion = "la mochila esta cargada con " + evaluarIndividuo(individuo) + " de peso, sobre un maximo de " + _pesoMaximo+"\n";
       if (individuo instanceof IndividuoBinarioHaploide){
	byte[] cromosomas = ((IndividuoBinarioHaploide)individuo)._cromosomas;
	for (int i = 0; i < _peso.length; i++){
	  descripcion += "Del tipo de objeto " + i + " que pesa " + _peso[i] + " llevamos " + cromosomas[i] + " objetos\n";
	}

      }else if (individuo instanceof IndividuoPositivoHaploide){
	double[] cromosomas = ((IndividuoPositivoHaploide)individuo)._cromosomas;
	for (int i = 0; i < _peso.length; i++){
	  descripcion += "Del tipo de objeto " + i + " que pesa " + _peso[i] + " llevamos " +cromosomas[i] + " objetos\n";
	}
      }
      return descripcion;
    }


    /** @return Individuo[] Devuelve los individuos para los cuales soporta implementacion este problema */
    public String[] individuosSoporta(){
      String[] individuosSoportados = { "Binario Haploide","Positivo Haploide"};
      return individuosSoportados;
    }

    /** @return int Numero de individuos que tendra por defecto la poblacion */
    public int numeroIndividuosPorDefecto(){ return 10; }

    /** @return long Longitud del cromosoma de los individuos por defecto */
    public int longitudCromosomaPorDefecto(){ return _peso.length;}

    /** @return double Puntuacion a obtener por defecto para detener la simulacion */
    public double puntuacionAObtenerPorDefecto(){return _pesoMaximo;}

    /** @return int Itercaciones maximas a realizar para detener la simulacion */
    public int iteracionesMaximasPorDefecto(){return 100;}

    /** @return boolean True si la solucion puede ser relativa al individuo  */
    public boolean individuoPuedeSerSolucion(){return true;}

    /** @return boolean True Si la solucion puede ser relativa a la poblacion (solucion de Manhatan)*/
    public boolean poblacionPuedeSerSolucion(){return false;}

    /** @return String Ayuda al problema */
    public String obtenerAyuda(){
      return "Se trata de llenar al maximo la mochila hasta un peso dado, introduciendo objetos de diversos tipos \n"+
      "El peso maximo que soporta la mochila y el peso de cada tipo de objeto se pide al seleccionar el problema \n"+
      "CODIFICACIoN: cada posicion del cromosoma indica un tipo de objeto, el numero que aparezca es el numero de veces que se ha cargado en la mochila.. \n"+
      "..si solo puede aparecer una vez un objeto en la mochila elegir un individuo binario \n"+
      "-Tamanyo del cromosoma: indica los distintos tipos de objetos que se pueden introducir en la mochila (si disponemos de 4 tipos de objetos distintos, sera de 4)\n" +
      "-Tipo de solucion: tiene sentido solo para individuos concretos \n"+
      "-Puntuacion: hace referencia al peso de la mochila ,debera ser menor o = que el peso maximo soportado";
    }

    /** Main para probar el problema sin necisidad de interfaz grafica */
public static void main(String[] args){
   try{
      Problema problemaMochila = new ProblemaMochila(); //instanciamos el problema y su poblacion
      Poblacion poblacionMochila = new PoblacionMochila(problemaMochila);
      String[] individuosSoporta = problemaMochila.individuosSoporta();
      Individuo individuoSolucion = EjecutaAG.lanzarAlgoritmoAG(poblacionMochila, individuosSoporta[1]);
   }catch (Exception ex) {
     System.out.println("Error al intentar solucional el problema mediante algoritmos geneticos");
   }
}


}
