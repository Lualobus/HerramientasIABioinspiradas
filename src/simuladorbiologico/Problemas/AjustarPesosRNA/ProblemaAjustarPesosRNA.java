package simuladorbiologico.Problemas.AjustarPesosRNA;
import simuladorbiologico.Individuos.*;
import simuladorbiologico.*;
import RNA.*;

/**
 * Este problema sirve para ajustar los pesos de una Red de Neuronas usando Alg. Geneticos
 */
public class ProblemaAjustarPesosRNA extends Problema{

  private Red rna;                      //red de neuronas
  private double[][] entradas;          //entradas de entremiento normalizadas
  private double[][] salidasEsperadas;  //salidas esperadas para los entrenamientos (normalizadas)
  private long pasadasPatronesMax;      //veces pasada max patrones a la red
  private int numPesos;                 //numero de pesos total de la red


  /** Recibe los argumentos necesarios para poder evaluar los individuos*/
  public ProblemaAjustarPesosRNA(Object[] argumentosAdicionales) throws Exception{
    try{
      rna = (Red) argumentosAdicionales[0];
      entradas = (double[][]) argumentosAdicionales[1];
      salidasEsperadas = (double[][]) argumentosAdicionales[2];
      pasadasPatronesMax = ((Long) argumentosAdicionales[3]).longValue();
      numPesos = ((Integer) argumentosAdicionales[4]).intValue();
    }catch(Exception e){
      throw new Exception("Los argumentos recividos para el problema de ajustar los pesos de la RNA no son validos");
    }
  }


  /**
   * FUNCION FITNES PARA EL INDIVIDUO
   * Cada cromosoma del individuo se corresponde con un peso de la red de neuronas
   * Se devuelve un valor que es un VALOR_MAX - error cometido por la red con estos pesos
   */
   public double evaluarIndividuo (Individuo individuo){
     if (rna == null){ System.err.println("NO se han recivido la red de neuronas que se quiere configurar"); return 0;}
     double errorPorPasadaPatrones = 0;
     try{
       double[] cromosomas = ((IndividuoDecimalHaploide) individuo)._cromosomas;
       rna.asignarPesos(cromosomas);
       //rna.mostrarRed();
       double[][] salidasObtenidas = new double[salidasEsperadas.length][salidasEsperadas[0].length];
       for (int i = 0; i < salidasEsperadas.length; i++){
	  salidasObtenidas[i] = rna.calcularSalidaRed(entradas[i]);
	  for (int j = 0; j < salidasEsperadas[i].length; j++){
	    errorPorPasadaPatrones += Math.abs(salidasObtenidas[i][j]-salidasEsperadas[i][j]);
	  }
       }
       errorPorPasadaPatrones /= (entradas.length*salidasEsperadas[0].length);  //obtenemos el error normalizado por cada neurona de salida
     }catch (Exception e){
       System.out.println(e);
       return Double.MAX_VALUE;}
     return errorPorPasadaPatrones;
   }

   /**
     * Para determinar si es un problema de maximizacion o minimizacion
     * @return True si es de maximizar y False si es de minimizar
     */
  public boolean isMaximizeProblem(){
    return false;
  }


   /**
   * @param invididuo a interpretar segun el problema
   * @return Devuelbe una descripcion del individuo relativa al problema Electoral
   */
   public String interpretacionDelIndividuo(Individuo individuo){
     double[] cromosomas = ((IndividuoDecimalHaploide) individuo)._cromosomas;
     return new String ("Este individuo representa los siguientes pesos para la red neuronal: " + cromosomas.toString());
   }

    /**
    * Devuelve los individuos para los cuales soporta implementacion este problema
    * @return Individuo[] Individuos soportados
    */
   public String[] individuosSoporta(){
     String[] individuosSoportados = {"Decimal Haploide"};
     return individuosSoportados;
   }

    /** @return int Numero de individuos que tendra por defecto la poblacion */
   public int numeroIndividuosPorDefecto(){ return 300; }

   /** @return long Longitud del cromosoma de los individuos por defecto */
   public int longitudCromosomaPorDefecto(){ return numPesos;}

   /** @return double Puntuacion a obtener por defecto para detener la simulacion */
   public double puntuacionAObtenerPorDefecto(){return 0;}

   /** @return int Itercaciones maximas a realizar para detener la simulacion */
   public int iteracionesMaximasPorDefecto(){return 10000;}

   /** @return boolean True si la solucion puede ser relativa al individuo  */
   public boolean individuoPuedeSerSolucion(){return true;}

   /** @return boolean True Si la solucion puede ser relativa a la poblacion (solucion de Manhatan)*/
   public boolean poblacionPuedeSerSolucion(){return false;}

   /** @return double Numero de individuos contra los que tiene que competir en cada reproduccion por defecto */
   public double probabilidadMutarGenPorDefecto(){return 0.2;}

   /** @return int Numero de iteracciones necesaria para incrementar en uno la presion selectiva */
   public int numIteraccionesParaAumentarPresionSelectivaPorDefecto(){return (((int)iteracionesMaximasPorDefecto()*15)/numeroIndividuosPorDefecto());}

   /** @return int Numero de individuos que pasan directamente a la siguiente generacion por ser los mejores */
   public int elitismoPorDefecto(){return 1;}

   /** @return String Tipo de sobrecruzamiento que se usa por defecto en el problema */
   public String tipoSobrecruzamientoPorDefecto(){return Reproduccion.sobrecruzamientoPartirAleatio;}

   /** @return boolean Determina si el mejor esta expuesto o no a mutacion */
   public boolean mejorInmutablePorDefecto(){return true;}

   /** @return int Numero de individuos contra los que tiene que competir en cada reproduccion por defecto */
  public int presionSelectivaPorDefecto(){return 3;}

   /** @return String Ayuda al problema */
   public String obtenerAyuda(){
     return "Este problema intenta configurar los pesos de una red de neuronas, esto es, cada individuo"+
	     "esta formado pos los pesos que tendria la red, y el mejor individuo seria aquel que pasando"+
	     "todos los patrones de entrenamiento a la red que tuviese los pesos de dicho individuo dara el menor"+
	     "error posible";
   }

}
