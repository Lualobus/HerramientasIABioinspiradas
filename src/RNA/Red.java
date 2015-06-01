/**
 *Implementa una red de neuronas en la que se puede configurar el numero de capas, neuronas en cada capa,
 * funcion de activacion, tasa de aprendizaje. Todas las neuronas de cada capa estan conectadas con todas
 * las de la siguiente.
 *
 * 1º Se debe crear la red con una topologia (llamada el constructor)
 * 2º Se debe entrenar pasandole patrones de entrada-salida (que la propia red normalizara)
 * 3º Se debe provar o validar la red para una determinada entrada de la que se quiere obtener la salida
 */

package RNA;

import java.util.Vector;
import simuladorbiologico.*;
import simuladorbiologico.Individuos.IndividuoDecimalHaploide;
import simuladorbiologico.Problemas.AjustarPesosRNA.*;
import OptimizadorEnjambreParticulas.*;
import OptimizadorEnjambreParticulas.Problemas.ProblemaAjustarPesosRNA;
import fam.utilidades.*;


public class Red extends Thread{

  private int numEntradas;                     /** Numero de entradas que tiene la red */
  private int numSalidas;                      /** Numero de salidas que tiene la red */
  private int numCapas;                        /** Numero total de capas de la red */
  private String funcionActivacion;            /** Nombre de la funcion de activacion para las neuronas, debe estar implementada en la clase neurona */
  private double tasaAprendizaje = 0.3;        /** Tasa de aprendizaje a la hora de modificar los pesos */
  private double[] mayorEntrada;               /** Valor maximo de la entrada i-esima (necesario para la normalizacion) */
  private double[] menorEntrada;               /** Valor minimo de las entrada i-esima (necesario para la normalizacion) */
  private double[] mayorSalida;                /** Valor maximo de las salida i-esima (necesario para la normalizacion) */
  private double[] menorSalida;                /** Valor minimo de las salida i-esima (necesario para la normalizacion) */
  private boolean autonormalizacion;           /** Si es False, es que se han especificado por el usuarios los valores maximos y minimos que pueden tomar las entradas y salidas, si esta a TRUE, el propio programa los busca, pero luego la validacion no deberia ser mayor que los valores con los que se ha entrenado */
  private Vector[] neuronas;                   /** Contiene en cada posicion un vector con las neuronas que contiene cada capa */

  public final static String BACKPROPAGATION = "backpropagation";
  public final static String GENETICO = "genetico";
  public final static String  ENJAMBRE= "EnjambreParticulas";

  /* Para hacer la grafica */
  public static double errorPorNeuronaSalida = -1;
  public static double errorValidacion = -1;
  public static double errorMax =-1;
  public static long iteraccionesMax=-1;
  public static long iteracciones = -1;
  public static boolean calculando = false;
  public static Semaforo mutex;
  public static Semaforo mutexAlternancia;
  public static Semaforo mutexVacio;
  public static boolean sincronizarConGrafica = false;


  /**
   * Constructor: Crea la estructura de neuronas de la red y da valor inicial a los pesos
   * NOTA: necesita de los valores mayores y menores, usar si se puede validar con numeros mayores a los entrenamientos (sino usar el otro constructor que ya busca el los valores mayores y menores)
   * @param configuracionCapas int[] En cada poscion del array se indica el numero de neuronas que tiene cada capa correspondiente (la primera sera el numero de entradas y la ultima el numero de salidas)
   * @param nombreFuncionActivacion String Nombre de la funcion de activacion de las neuronas (debe estar implementado el metodo con el mismo nombre en la clase RNA.Neurona)
   */
  public Red(int[] configuracionCapas, String nombreFuncionActivacion, double[] valorMayorEnt, double[] valorMenorEnt, double[] valorMayorSal, double[] valorMenorSal){
    autonormalizacion = false;  //<----------
    funcionActivacion = nombreFuncionActivacion;
    numCapas = configuracionCapas.length;
    numEntradas = configuracionCapas[0];
    numSalidas = configuracionCapas[numCapas -1];
    mayorEntrada = new double[numEntradas];
    menorEntrada = new double[numEntradas];
    mayorSalida = new double[numSalidas];
    menorSalida = new double[numSalidas];
    mayorEntrada = valorMayorEnt;
    menorEntrada = valorMenorEnt;
    mayorSalida = valorMayorSal;
    menorSalida = valorMenorSal;
    neuronas = new Vector[numCapas];

    for (int numCapa = 0; numCapa < numCapas; numCapa++) {
       neuronas[numCapa] = new Vector();
      for (int numNeurona = 0; numNeurona < configuracionCapas[numCapa]; numNeurona++) {
	if (numCapa == 0) {
	  anyadirNeurona(numCapa, numEntradas);  //para la primera capa tendra cada neurona tantos enlaces como entradas
	}else{
	  anyadirNeurona(numCapa, configuracionCapas[numCapa - 1]);  //para el resto de capas tendra cada neurona tantas entradas como neuronas la capa anteriores
	}
      }
    }
  }



  /**
   * Constructor: Crea la estructura de neuronas de la red y da valor inicial a los pesos
   * Nota: NO necesita de los valores mayores y menores, usar si se valida con numeros menores o igual a los entrenamientos
   * @param configuracionCapas int[]
   * @param nombreFuncionActivacion String
   */
  public Red (int[] configuracionCapas, String nombreFuncionActivacion){
    autonormalizacion = true;
    funcionActivacion = nombreFuncionActivacion;
    numCapas = configuracionCapas.length;
    numEntradas = configuracionCapas[0];
    numSalidas = configuracionCapas[numCapas -1];
    mayorEntrada = new double[numEntradas];
    menorEntrada = new double[numEntradas];
    mayorSalida = new double[numSalidas];
    menorSalida = new double[numSalidas];
    neuronas = new Vector[numCapas];

    for (int numCapa = 0; numCapa < numCapas; numCapa++) {
      neuronas[numCapa] = new Vector();
      for (int numNeurona = 0; numNeurona < configuracionCapas[numCapa]; numNeurona++) {
	if (numCapa == 0) {
	  anyadirNeurona(numCapa, numEntradas);  //para la primera capa tendra cada neurona tantos enlaces como entradas
	}else{
	  anyadirNeurona(numCapa, configuracionCapas[numCapa - 1]);  //para el resto de capas tendra cada neurona tantas entradas como neuronas la capa anteriores
	}
      }
    }
  }

  /**
   * Entrena la red mediante aprendizaje supervisado hasta que se cumpla una de estas 3 cosas:
   *     -  obtener un error menor al indicado
   *     -  hasta que se vea que el error no disminuye durante un numero grande de iteracciones
   *     -  hasta un numero maximo de iteraciones
   *
   * ejemplo de patrontes de entreanmiento para una red con 2 entradas y 2 salidas
   *
   * entradas         {  {17,4} , {31,8} , {23, 9} }
   * salidasEsperadas {  {21,3} , {14,9},  {21,4 } }
   *
   * @param entradas double[][] Valores de las entradas para la red (tantas como neuronas de entrada) y a su vez tantas como patrones
   * @param salidasEsperadas double[][] Valores esperados de salidas de la red (tantas como neuronas de salidas tenga la red) y a su vez tantas como patrones
   * @param errorMax double Error medio maximo permitido por cada neurona de salida (para cada salida de la red cuanto es el error permitido)
   * @param iteraccionesMax long Iteracciones maximas a desarrollar en el entrenamiento
   * @param iterMostrarResultados int Cada cuantas iteraciones se muestar como va el entrenamiento
   * @param modoEntrenamiento String Modo en el que se entrena la red (backpropagation, con algoritmo genetico...)
   * @return double Error medio que se produce enre la salida esperada y la deseada para cualquier patron de entrada
   * @throws Exception
   */
  public synchronized double entrenar(double[][] entradas, double[][] salidasEsperadas, double errorMax, long iteraccionesMax,int iterMostrarResultados, String modoEntrenamiento) throws Exception {

    this.errorMax = errorMax;
    this.iteraccionesMax = iteraccionesMax;
    this.iteracciones = -1;
    this.errorValidacion = -1;
    calculando = true;

    if (autonormalizacion)  //si no se han pasado cuales son los valores mayores y menores de los patrones los busca el propio programa (para poder hacer la normalizacion)
      obtenerValoresMaxMinimos(entradas,salidasEsperadas);

    //-----------normalizamos las entradas y  las salidas----------------------------
    normalizarSalidas(salidasEsperadas);
    normalizarEntradas(entradas);

    //################# ENTRENANDO LA RED CON EL ALGORITMO DE BACKPROPAGATION ########################################
    if (modoEntrenamiento.equalsIgnoreCase(BACKPROPAGATION)){
      double error =  backpropagation(entradas, salidasEsperadas,errorMax,iteraccionesMax,iterMostrarResultados);
      calculando = false;
      return error;

    //############## ENTRENANDO LA RED CON UN ALGORITMO GENeTICO ###################################################
    }else if (modoEntrenamiento.equalsIgnoreCase(GENETICO)){
      int numPesos = numeroPesosNecesarios();

      //_______________________LANZAMOS EL ALGORTIMO GENeTICO Y ASIGNAMOS LOS PESOS RESULTANTES__________________________
      String tipoSobrecruzamiento="Partir aleatorio";
      int longitudCromosoma=numPesos; double puntuacion=0; int elitismo=1; int numeroIndividuos=60; int presionSelectiva=3; double probabilidadMutarGen=0.2;  int pasosParaAumentarLaPresion=(((int)iteraccionesMax*15)/numeroIndividuos);
      Object[] parametrosProblema = {this,entradas,salidasEsperadas, (new Long(iteraccionesMax)),(new Integer(numPesos))};
      simuladorbiologico.Problema problemaAjustarPesosRNA = new simuladorbiologico.Problemas.AjustarPesosRNA.ProblemaAjustarPesosRNA(parametrosProblema);
      simuladorbiologico.Poblacion poblacionAjustarPesosRNA = new simuladorbiologico.Problemas.AjustarPesosRNA.PoblacionAjustarPesosRNA(problemaAjustarPesosRNA);
      Individuo individuoSolucion = simuladorbiologico.EjecutaAG.lanzarAlgoritmoAG(poblacionAjustarPesosRNA,"Decimal Haploide",puntuacion,numeroIndividuos,longitudCromosoma,presionSelectiva,probabilidadMutarGen,pasosParaAumentarLaPresion,elitismo,iteraccionesMax,tipoSobrecruzamiento,iterMostrarResultados,false,parametrosProblema);
      double[] cromosomas = ((IndividuoDecimalHaploide) individuoSolucion)._cromosomas;
      asignarPesos(cromosomas);

      //calculamos el error medio que se produce para cada neurona de salida y lo devolvemos
      double error = calcularErrorMedioPorNeuronaSalida(entradas,salidasEsperadas);
      calculando = false;
      return error;

    //################### ENTRENANDO LA RED CON ENJAMBRES DE PARTiCULAS ########################################
    }else if (modoEntrenamiento.equalsIgnoreCase(ENJAMBRE)){
      int numPesos = numeroPesosNecesarios();
      Object[] parametrosProblema = {this,entradas,salidasEsperadas, (new Long(iteraccionesMax)),(new Integer(numPesos))};
      ProblemaAjustarPesosRNA optPesos = new ProblemaAjustarPesosRNA(parametrosProblema);
      int numDimensiones = numPesos; int numParticulas = 700; double valoracionObtener = 0;
      Particula particulaSolucion = OptimizadorEnjambreParticulas.EjecutaPSO.lanzarPSO(optPesos,numDimensiones,numParticulas,valoracionObtener,iteraccionesMax,true,0.9,0.9,0.9,true,optPesos.valorDefectoVelocidadMax(),optPesos.valorDefectoPosicionMax());
      double[] posiciones = particulaSolucion.getPosicion();
      asignarPesos(posiciones);

      //calculamos el error medio que se produce para cada neurona de salida y lo devolvemos
      double error = calcularErrorMedioPorNeuronaSalida(entradas,salidasEsperadas);
      calculando = false;
      return error;


    }else{
      System.out.println("No existe una implementacion para el modo de entrenamiento " + modoEntrenamiento);
      calculando = false;
      return 0;
    }
  }


  /**
   * Realiza el entrenamiento con los patrones de enrenamiento y al mismo tiempo que va entrenando va calculando
   * el error que se comete con los patrones de validacion, de tal manera que se
   * deja de entrenar en el momento que el error producido con los patrones de entrenamiento
   * deje de disminuir (esto es debido a que empieza a sobreentrenarse con los patrones de entrenamiento).
   * Usa para realizar el entrenamiento el algoritmo de backpropagation
   *
   * @param entradasEntrenamiento double[][]  Entradas de los patrones de entrenamiento
   * @param salidasEsperadasEntrenamiento double[][] Salidas de los patrones de entrenamiento
   * @param entradasValidacion double[][]  Entradas de los patrones de validacion
   * @param salidasEsperadasValidacion double[][] Salidas de los patrones de validacion
   * @param iterMostrarResultados long Cada cuantas iteraciones se muestran los resultados del entrenamiento
   * @return double Error medio que se produce enre la salida esperada y la deseada para cualquier patron de entrada
   * @throws Exception Si se produce algun error
   */
  public synchronized double entrenarHastaNoSobreentrenar(double[][] entradasEntrenamiento, double[][] salidasEsperadasEntrenamiento,double[][] entradasValidacion, double[][] salidasEsperadasValidacion, long iterMostrarResultados) throws Exception {

    if ((entradasEntrenamiento[0].length != entradasValidacion[0].length) || (salidasEsperadasEntrenamiento[0].length != salidasEsperadasValidacion[0].length) )   throw new Exception("Los patrones de entrenamiento y validacion tienen distinto numero de entradas o salidas");

    calculando = true;

    if (autonormalizacion){  //si no se han pasado cuales son los valores mayores y menores de los patrones los busca el propio programa (para poder hacer la normalizacion)
      double[][] totalEntradas = new double[entradasEntrenamiento.length+entradasValidacion.length][entradasEntrenamiento[0].length];
      double[][] totalSalidas = new double[salidasEsperadasEntrenamiento.length+salidasEsperadasValidacion.length][salidasEsperadasEntrenamiento[0].length];
      System.arraycopy(entradasEntrenamiento,0,totalEntradas,0,entradasEntrenamiento.length);
      System.arraycopy(entradasValidacion,0,totalEntradas,entradasEntrenamiento.length,entradasValidacion.length);
      System.arraycopy(salidasEsperadasEntrenamiento,0,totalSalidas,0,salidasEsperadasEntrenamiento.length);
      System.arraycopy(salidasEsperadasValidacion,0,totalSalidas,salidasEsperadasEntrenamiento.length,salidasEsperadasValidacion.length);
      obtenerValoresMaxMinimos(totalEntradas, totalSalidas);
    }

     //-----------normalizamos las entradas y  las salidas----------------------------
     normalizarEntradas(entradasEntrenamiento);
     normalizarSalidas(salidasEsperadasEntrenamiento);
     normalizarEntradas(entradasValidacion);
     normalizarSalidas(salidasEsperadasValidacion);

     int numPatrones = entradasEntrenamiento.length;
     double error = 0;  //error que se va produciendo entre la salida esperada y la que se da
     double[] salidas;
     errorPorNeuronaSalida=Double.MAX_VALUE;  //error medio real que se comete por cada neurona de salida
     double errorPorNeuronaSalidaAntiguo=Double.MAX_VALUE;  //anterior error medio real que se comete por cada neurona de salida
     long vecesAumentando=0;
     double[] mejoresPesos=null;
     double mejorResultado=Double.MAX_VALUE;
     double errorValidacionAntiguo = Double.MAX_VALUE;
     errorValidacion = Double.MAX_VALUE;
     this.iteracciones = -1;
     this.iteraccionesMax =10;
     int iteraccionesAumentandoErrorValidacion= 0;

     if (sincronizarConGrafica){  mutex = new Semaforo(1);  mutexAlternancia = new Semaforo(1);  mutexVacio = new Semaforo(0); }

     do{

       //System.out.println("Red se bloqueara");
       if (sincronizarConGrafica){ mutex.bajar(); mutexAlternancia.bajar();}
       //System.out.println("Red desbloqueada");
       errorValidacionAntiguo = errorValidacion;

       //aplicamos el algoritmo de backpropagation para modificar los pesos de la red para una pasada entera del conjunto de patrones
       for (int numPatron = 0; numPatron < entradasEntrenamiento.length; numPatron++){
	 salidas = calcularSalidaRed(entradasEntrenamiento[numPatron]);
	 error = calcularError(salidas, salidasEsperadasEntrenamiento[numPatron]);
	 modificarPesosRed(error);
       }

       //calculamos error medio real cometido por cada neurona de salida para el entrenamiento y para la validacion
       errorPorNeuronaSalida = calcularErrorMedioPorNeuronaSalida(entradasEntrenamiento,salidasEsperadasEntrenamiento);
       errorValidacion = calcularErrorMedioPorNeuronaSalida(entradasValidacion,salidasEsperadasValidacion);

       iteracciones++;

       //nos quedamos con la mejor solucion ya que en la busqueda se puede perder (la que menor error de validacion da)
       if (errorValidacion<mejorResultado){
	 mejorResultado = errorValidacion;
	 mejoresPesos=conseguirPesos();
       }

       //vemos si el error se minimiza para jugar con la razon de aprendizaje
       if ((iterMostrarResultados != 0) && (iteracciones%iterMostrarResultados==0)){
	 vecesAumentando = ajustarTasaAprendizaje(iteracciones,errorPorNeuronaSalida,errorPorNeuronaSalidaAntiguo, vecesAumentando, 0.001);
	 errorPorNeuronaSalidaAntiguo=errorPorNeuronaSalida;
       }

       //si se ha llegado a las iteraccionesMaximas, como en este modo realmente no hay iteraccionesMaximas, la duplicamos (solo la usamos para ir reescalando la grafica)
       if (iteracciones == iteraccionesMax) iteraccionesMax*=1.1;

       //miramos cuantas veces lleva aumentando el error de validacion, siempre que el error de entrenamiento este disminuyendo
       if (vecesAumentando == 0){
	 if (errorValidacion > errorValidacionAntiguo) {
	   iteraccionesAumentandoErrorValidacion++;
	 }else{
	   iteraccionesAumentandoErrorValidacion = 0;
	 }
       }
       //System.out.println("Red desbloquea grafica");
       if (sincronizarConGrafica){mutexAlternancia.subir(); mutexVacio.subir();}

     }while(iteraccionesAumentandoErrorValidacion<1000);
     asignarPesos(mejoresPesos);  //finalmente nos quedamos con la mejor solucion que se haya encontrado
     calculando = false;
     return mejorResultado;
   }


  /**
   * Devuelve el resultado de meter las entradas a la red
   * @param entradas double[] Entradas de la red sin normalizar (el propio metodo las normaliza)
   * @return double[]  La salida de la red con los datos sin normalizar (el propio metodo los desNormaliza)
   */
  public double[] validarRed(double[] entradas) throws Exception{
    double[][] ent = { entradas };
    normalizarEntradas(ent);
    double[] salidas = calcularSalidaRed(ent[0]);
    double[][] sal = { salidas };
    desNormalizarSalidas(sal);
    return sal[0];
  }


  /** Muestra la red de neuronas por pantalla */
  public void mostrarRed() {
    Neurona neurona;
    double[] entradas;
    double[] pesos;
    for (int numCapa =0; numCapa < neuronas.length; numCapa ++){
      System.out.println("______________CAPA " + numCapa + " ______________");
      for (int numNeurona = 0; numNeurona < neuronas[numCapa].size(); numNeurona++){
	neurona = (Neurona)neuronas[numCapa].elementAt(numNeurona);
	System.out.println(">>>>>>>>>>>>>Neurona "+ numNeurona + "<<<<<<<<<<<<<<<<<<<<<<<<<");
	entradas = neurona.getEntradas();
	pesos = neurona.getPesosEntrada();
	for (int numEntradas = 0; numEntradas < entradas.length; numEntradas++){
	  System.out.print("- " + numEntradas+ " Entrada>>  valor: " + entradas[numEntradas] );
	  if (numCapa==0){
	    System.out.println();
	  }else{
	   System.out.println(" peso: " + pesos[numEntradas]);
	  }
	}
	System.out.println("Salida de la neurona es: " + neurona.getSalida());
      }
      System.out.println();
    }
  }


  /** Asigna los pesos a la red, debe recibir tantos pesos como pesos en total necesite la red, empieza asignandolos
   * desde la capa 1 y hasta la ultima capa por orden de neurona */
  public void asignarPesos(double[] pesos) throws Exception{
    int contador = 0;
    Neurona neurona;
    double[] pesosEntradaNeurona;
    for (int i = 1; i < neuronas.length; i++){
      for (int j = 0; j < neuronas[i].size() ; j++){
	neurona = (Neurona)neuronas[i].elementAt(j);
	pesosEntradaNeurona = new double[neurona.getNumeroEntradas()];
	System.arraycopy(pesos,contador,pesosEntradaNeurona,0,neurona.getNumeroEntradas());
	neurona.setPesosEntrada(pesosEntradaNeurona);
	contador += neurona.getNumeroEntradas();
      }
    }
  }

  /** Devuelve los pesos que tiene la red, en el mismo orden que se asignan (no los borra de la red) */
  public double[] conseguirPesos(){
    Neurona neurona;
    int contador = 0;
    double[] pesos = new double[numeroPesosNecesarios()];
    for (int i = 1; i < neuronas.length; i++){
     for (int j = 0; j < neuronas[i].size() ; j++){
       neurona = (Neurona)neuronas[i].elementAt(j);
       System.arraycopy(neurona.getPesosEntrada(),0,pesos,contador,neurona.getNumeroEntradas());
       contador+=neurona.getNumeroEntradas();
     }
   }
   return pesos;
  }

  /**
   * Calcula la salida de la red para una determinada entrada y teniendo en cuenta la configuracion actual de la red
   * @param inputs double[] Valores de entrada para la red
   * @return Las salidas de la red para esas entradas sin desnormalizar
  *  @exception java.lang.NoSuchMethodException Si la funcion de activacion indicada no esta implementada en la clase neurona
   */
   public double[] calcularSalidaRed(double[] inputs) throws Exception{
     double[] entradas = null; //valores de entrada para cada neurona de la red
     double[] salidas = null;  //salidas que va generando cada capa de la red, que sera a su vez entradas de las neuronas de la siguiente capa
     Neurona neurona;          //neurona a calcular

     //vamos por cada capa calculando las salidas de sus neuronas
     for (int numCapa = 0; numCapa < numCapas; numCapa++) {

       //si es la capa inicial, las entradas son las recibidas por parametros
       if (numCapa == 0) {
	 entradas = inputs;
	 salidas = entradas;
	 for (int numNeurona = 0; numNeurona < entradas.length; numNeurona++) {
	   neurona = (Neurona) neuronas[numCapa].elementAt(numNeurona);
	   neurona.setPesosEntrada(null);
	   neurona.setSalida(entradas[numNeurona]);
	 }

       }else { //si es otra capa las entradas de cada neurona son las salidas de la capa anterior
	 entradas = new double[salidas.length];
	 System.arraycopy(salidas,0,entradas,0,salidas.length);

	 //vamos por cada neurona de la capa actual calculando su salida
	 salidas = new double[neuronas[numCapa].size()];
	 for (int numNeurona = 0; numNeurona < neuronas[numCapa].size(); numNeurona++) {
	   neurona = (Neurona) neuronas[numCapa].elementAt(numNeurona);
	   salidas[numNeurona] = neurona.calcularNeurona(entradas, funcionActivacion);
	 }
       }
    }
    return salidas;
  }


  /**
   * Devuelve el error real que se comete por cada neurona de salida, pasandole las entradas y las salidas esperadas normalizadas (el error se devuelve desnormalizado, es decir el real)
   * @exception java.lang.NoSuchMethodException Si la funcion de activacion indicada no se corresponde con ningun metodo de esta clase
   */
  public double calcularErrorMedioPorNeuronaSalida(double[][] entradas, double[][] salidasEsperadas) throws Exception{
    double[][] salidasObtenidas = new double[salidasEsperadas.length][salidasEsperadas[0].length];
    double salidaEsperada;
    double errorTotalPorTodosLosPatrones=0;  //error total acumulado de pasar todos los patrones
    double errorPorNeuronaSalida = 0;        //error que se va produciendo por cada neurona de salida de la red
    double errorTotalPorPatron=0;            //error total que se va produciendo a la pasada de un patron
    int numPatrones = entradas.length;

    //calculamos el error que se acumula para todo el entrenamiento (pasada de todos los patrones)
    for (int i = 0; i < salidasEsperadas.length; i++){
      salidasObtenidas[i] = calcularSalidaRed(entradas[i]);
      errorTotalPorPatron=0;
      //vamos por cada patron calculando el error que se produce
      for (int j = 0; j < salidasEsperadas[i].length; j++){
	salidasObtenidas[i][j] = (salidasObtenidas[i][j] * (mayorSalida[j] - menorSalida[j])) + menorSalida[j];  //desnormalizo la salida obtenida
	salidaEsperada = (salidasEsperadas[i][j] * (mayorSalida[j] - menorSalida[j])) + menorSalida[j]; //desnormalizo la salida esperada
	errorPorNeuronaSalida = Math.abs(salidasObtenidas[i][j]-salidaEsperada);  //calculo el error
	errorTotalPorPatron+=errorPorNeuronaSalida;
      }
      errorTotalPorTodosLosPatrones+=errorTotalPorPatron;
    }
    errorPorNeuronaSalida = errorTotalPorTodosLosPatrones/(numPatrones*numSalidas);
    return errorPorNeuronaSalida;  //devolvemos el error medio(sin normalizar, osea real) que se produce por cada neurona de salida de la red con la que se espera
  }



  //_____________________Metodos privados_________________________________________

  /**
   * Valiendose del algoritmo de configuracion de pesos backpropagation, los intenta configurar  y los deja configurados
   * Para intentar disminuir el error se juega con la razon de aprendizaje variandola segun las cirscunstancias
   *
   * @param entradas double[][] Patrones de entrada normalizados
   * @param salidasEsperadas double[][] Patrones de Salida normalizados
   * @param errorMax double Error maximo medio por cada neurona de salida permitido (real, sin normalizar)
   * @param iteracionesMax double  Iteracciones maximas del algoritmo permitido
   * @return double El menor error posible encontrado (la mejor solucion)
   * @throws Exception Puede ser lanzada si no esta implementada en la clase neurona la funcion de activacion que se usa
   */
  private double backpropagation(double[][] entradas, double[][] salidasEsperadas, double errorMax, double iteracionesMax,int iterMostrarResultados) throws Exception{

    int numPatrones = entradas.length;
    double error = 0;  //error que se va produciendo entre la salida esperada y la que se da
    double[] salidas;
    double errorPorNeuronaSalidaAntiguo=Double.MAX_VALUE;  //anterior error medio real que se comete por cada neurona de salida
    errorPorNeuronaSalida = Double.MAX_VALUE;
    long vecesAumentando=0;
    double[] mejoresPesos=null;
    double mejorResultado=Double.MAX_VALUE;

    if (sincronizarConGrafica){
	mutex = new Semaforo(1);
	mutexAlternancia = new Semaforo(1);
	mutexVacio = new Semaforo(0);
    }

    //pasamos tantas veces los patrones hasta que se minimice el error o se supere el numero de pasadas maximas
    do{
      if (sincronizarConGrafica){
	mutex.bajar();
	mutexAlternancia.bajar();
      }


      //aplicamos el algoritmo de backpropagation para modificar los pesos de la red para una pasada entera del conjunto de patrones
      for (int numPatron = 0; numPatron < entradas.length; numPatron++){
	salidas = calcularSalidaRed(entradas[numPatron]);
	error = calcularError(salidas, salidasEsperadas[numPatron]);
	modificarPesosRed(error);
      }

      //calculamos error medio real cometido por cada neurona de salida
      errorPorNeuronaSalida= calcularErrorMedioPorNeuronaSalida(entradas,salidasEsperadas);
      iteracciones++;

      //nos quedamos con la mejor solucion ya que en la busqueda se puede perder
      if (errorPorNeuronaSalida<mejorResultado){
	mejorResultado = errorPorNeuronaSalida;
	mejoresPesos=conseguirPesos();
      }

      //vemos si el error se minimiza para jugar con la razon de aprendizaje
      if ((iterMostrarResultados != 0) && (iteracciones%iterMostrarResultados==0)){
	vecesAumentando = ajustarTasaAprendizaje(iteracciones,errorPorNeuronaSalida,errorPorNeuronaSalidaAntiguo, vecesAumentando, errorMax);
	errorPorNeuronaSalidaAntiguo=errorPorNeuronaSalida;
      }
      //if (iteracciones%(iteraccionesMax/10) == 0)  Thread.yield();

      if (sincronizarConGrafica){
	mutexAlternancia.subir();
	mutexVacio.subir();
      }

    }while ( ( errorPorNeuronaSalida > errorMax ) && (iteracciones < iteracionesMax) );

    asignarPesos(mejoresPesos);  //finalmente nos quedamos con la mejor solucion que se haya encontrado
    return mejorResultado;
  }



  /**
   * Se modifican los pesos de todas las conexiones y los umbrales de las neuronas (Con Backpropagation)
   * @param error double Es la diferencia entre la salida esperada y la obtenida
   */
  private void modificarPesosRed(double error) {
    Neurona neurona;  //neurona actual
    double[] pesos;   //pesos de las entradas de la neurona actual
    double umbral;

    //desde la ultima capa y hasta la inicial (la inicial no tiene pesos en las entradas)
    for (int numCapa = neuronas.length - 1; numCapa > 0; numCapa--) {

      //para todas las neuronas de la capa actual
      for (int numNeurona = 0; numNeurona < neuronas[numCapa].size(); numNeurona++) {

	neurona = (Neurona) (neuronas[numCapa].elementAt(numNeurona));
	pesos = neurona.getPesosEntrada();

	//para cada peso de cada entrada de la neurona actual lo vamos modificando asi: pesoNuevo = pesoAntiguo + (tasa de aprendizaje * error * valorDeLaEntrada * salidaNeurona * (1-salidaNeurona) )
	for (int numPeso = 0; numPeso < pesos.length; numPeso++) {
	  //pesos[numPeso] +=  (tasaAprendizaje * error * neurona.getEntrada(numPeso) * neurona.getSalida() * (1 - neurona.getSalida()));
	  pesos[numPeso] = pesos[numPeso] + (tasaAprendizaje * error * neurona.getEntrada(numPeso) * neurona.getSalida() * (1-neurona.getSalida()));
	}

	//modifico el umbral de la neurona actual: umbralNuevo = umbralAntiguo + (tasa de aprendizaje * error * salidaNeurona * (1- salidaNeurona) )
	umbral = neurona.getUmbral();
	umbral += (tasaAprendizaje * error * neurona.getSalida() * (1 - neurona.getSalida()));

	neurona.setPesosEntrada(pesos);
	neurona.setUmbral(umbral);
      }
    }
  }


  /**
   * Intenta ajustar correctamente la tasa de aprendizaje en funcion de si el error esta aumentando o disminuyendo
   * y cuantas iteraciones lleva aumentando, o cuanto de cerca esta de la solucion
   * @param vecesPasadaPatrones long Veces que se ha iterado ya en el algoritmo
   * @param errorPorNeuronaSalida double Error medio real que se da por cada neurona de salida
   * @param errorPorNeuronaSalidaAntiguo double Error medio que se daba por cada neurona de salida en la anterior comprobacion
   * @param vecesAumentando long Veces que lleva el error aumentando
   * @param errorMax double Error maximo real permitido de error por cada neurona de salida
   * @return double Las veces que lleva el error aumentando
   */
  private long ajustarTasaAprendizaje(long vecesPasadaPatrones, double errorPorNeuronaSalida, double errorPorNeuronaSalidaAntiguo, long vecesAumentando, double errorMax){
    System.out.print(vecesPasadaPatrones+"> con error: " + errorPorNeuronaSalida+ " (" + tasaAprendizaje +") ");

    if (errorPorNeuronaSalidaAntiguo < errorPorNeuronaSalida) {    //el error ha aumentado
      vecesAumentando++;
      if (vecesAumentando < 10){
	if (vecesAumentando == 1) {
	  if (tasaAprendizaje > 0.3){
	    tasaAprendizaje = 0.1;
	  }else{
	    tasaAprendizaje += 0.1;
	  }
	}else{
	  if (tasaAprendizaje < 0.95){
	    this.tasaAprendizaje +=0.001;
	  }
	}
      }else{
	if (tasaAprendizaje > 0.6){
	  this.tasaAprendizaje -= 0.01;
	}else{
	  if (tasaAprendizaje < 0.95){
	    this.tasaAprendizaje += 0.001;
	  }
	}
      }
    }else{  //el error ha disminuido
	//segun va disminuyendo el error disminuimos la razon de aprendizaje para no pasarnos la solucion
	//this.tasaAprendizaje -= (errorMax * (tasaAprendizaje ))/(errorPorNeuronaSalida+0.1);
	tasaAprendizaje -= (0.0001 * (tasaAprendizaje ))/(errorPorNeuronaSalida+0.1);
	//tasaAprendizaje = 0.3;
	if (tasaAprendizaje < 0.1) this.tasaAprendizaje = 0.1;
	vecesAumentando=0;
    }
    System.out.println(" Nueva tasa aprendizaje: " + tasaAprendizaje);
    return vecesAumentando;
  }



  /**
   * Anyade una neurona en una capa
   * @param numCapa int Capa en la que se va a insertar la neurona
   * @param numEntradas int Numero de entradas que tendra esa neurona
   */
  private void anyadirNeurona(int numCapa, int numEntradas) {
    Neurona nueva = new Neurona(numEntradas);
    neuronas[numCapa].addElement(nueva);
  }

  /**
   * @return int Devuelve el numero de pesos totales que usa la red
   */
  private int numeroPesosNecesarios(){
    int numPesos=0;
      for (int i = 1; i < neuronas.length; i++){
	for (int j = 0; j < neuronas[i].size(); j++) {
	  numPesos += ((Neurona) neuronas[i].elementAt(j)).getNumeroEntradas();
	}
      }
      return numPesos;
  }

  /**
   * @return Devuelve la diferencia en valor relativo por cada salida  entre las salidas conseguidas y las esperadas
   */
  private double calcularError (double[] salidasConseguidas, double[] salidasEsperadas) throws Exception{
    double error = 0;
    if (salidasConseguidas.length != salidasEsperadas.length){
      throw (new Exception("Distinto numero de salidas esperadas de las que tiene la red de neuronas"));
    }
    for (int j=0; j < salidasConseguidas.length; j++){
      //error+=Math.abs(salidasConseguidas[j]-salidasEsperadas[j]);
      error += salidasEsperadas[j] - salidasConseguidas[j];
    }
    return error;
  }



//_______________________NORMALIZAR/DENORMALIZAR ENTRADAS Y SALIDAS_______________________

  /**
   * De los patrones de entrenamiento y de salida determina cuales son sus valores mayores y menores ( util para la normalizacion )
   * @param entradas double[][] Patrones de entrada
   * @param salidasEsperadas double[][] Patrones de salida
   */
  private void obtenerValoresMaxMinimos(double[][] entradas, double[][] salidasEsperadas) {

    if  ((entradas != null) && (salidasEsperadas != null)){

     for (int i = 0; i < entradas[0].length; i++){
       mayorEntrada[i] = Double.MIN_VALUE;
       menorEntrada[i] = Double.MAX_VALUE;
     }

     for (int fila = 0; fila < entradas.length; fila++){
       for (int col = 0; col < entradas[fila].length; col++){
	 if (entradas[fila][col] > mayorEntrada[col]){
	   mayorEntrada[col] = entradas[fila][col];
	 }
	 if (entradas[fila][col] < menorEntrada[col]){
	   menorEntrada[col] = entradas[fila][col];
	 }
       }
     }

     for (int i = 0; i < salidasEsperadas[0].length; i++){
	 mayorSalida[i] = Double.MIN_VALUE;
	 menorSalida[i] = Double.MAX_VALUE;
       }

       for (int fila = 0; fila < salidasEsperadas.length; fila++){
	 for (int col = 0; col < salidasEsperadas[fila].length; col++){
	   if (salidasEsperadas[fila][col] > mayorSalida[col]){
	     mayorSalida[col] = salidasEsperadas[fila][col];
	   }
	   if (salidasEsperadas[fila][col] < menorSalida[col]){
	     menorSalida[col] = salidasEsperadas[fila][col];
	   }
	 }
       }
   }
 }

  /**
   * Normaliza los valores del vector ajustandolos entre su valor maximo y su valor minimo
   * Normalizacion:  (valor - min) / (max - min)
   */
  private void normalizarEntradas (double[][] entradas){
    //construimos el vector normalizado aplicando la formula
    for (int i = 0; i < entradas.length; i++){
      for (int j = 0; j < entradas[i].length; j++){
	entradas[i][j] = (entradas[i][j] - menorEntrada[j]) / (mayorEntrada[j] - menorEntrada[j]);
      }
    }
  }

  /**
   * Normaliza los valores del vector ajustandolos entre su valor maximo y su valor minimo
   * Normalizacion:  (valor - min) / (max - min)
   */
  private void normalizarSalidas (double[][] salidas){

    //construimos el vector normalizado aplicando la formula
    for (int i = 0; i < salidas.length; i++){
      for (int j =0 ; j < salidas[i].length; j++){
	salidas[i][j] = (salidas[i][j] - menorSalida[j]) / (mayorSalida[j] - menorSalida[j]);
      }
    }
  }

  /**
   * Normaliza los valores del vector ajustandolos entre su valor maximo y su valor minimo
   * Normalizacion:  (normalidado * ( max - min)) + min
   */
  private void desNormalizarEntradas (double[][] entradas){

      //construimos el vector normalizado aplicando la formula
      for (int i = 0; i < entradas.length; i++){
	for (int j = 0; j < entradas[i].length; j++){
	  entradas[i][j] = (entradas[i][j] * (mayorEntrada[j] - menorEntrada[j])) +  menorEntrada[j];
	}
      }
    }

  /**
   * Normaliza los valores del vector ajustandolos entre su valor maximo y su valor minimo
   * Normalizacion:  (normalidado * ( max - min)) + min
   */
  private void desNormalizarSalidas (double[][] salidas){

    //construimos el vector normalizado aplicando la formula
    for (int i = 0; i < salidas.length; i++){
      for (int j = 0; j < salidas[i].length; j++){
	salidas[i][j] = (salidas[i][j] * (mayorSalida[j] - menorSalida[j])) + menorSalida[j];
      }
    }
  }
}
