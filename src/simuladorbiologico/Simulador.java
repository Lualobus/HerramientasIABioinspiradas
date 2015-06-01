package simuladorbiologico;

import simuladorbiologico.InterfazGrafica.Grafica;
import simuladorbiologico.InterfazGrafica.InterfaceGrafica;
import fam.utilidades.*;

/**
 *  Esta clase es la que contiene el metodo main, y lanza la simulacion biologica
 * en busca de una solucion optima
 */

public class Simulador  extends Thread {

  private InterfaceGrafica interfaceGrafica=null; //para poder comunicarse con la interfaz y avisarle cuando acabe la simulacion (en caso de que exista interfaz grafica)
  private Grafica _grafica;   //para sacar los resultados por la grafica (en caso de que exista interfaz grafica)
  private FicheroEscritura escritor;  //para escribir los resultados en un fichero
  private boolean parar = false; //para parar el thread

  /* Parametros propios de la presentacion*/
  public static boolean puntuacionPorIndividuo = true; //la puntuacion se refiere al individuo (si false es a toda la poblacion)
  public static boolean hacerGrafica = true; //indica si hacer la grafica cuando se simula
  public static boolean hacerGraficaPoblacionMedia = true; //indica si hay que hacer la grafica que saca el individuo medio de cada poblacion

  public static boolean simulado = false; //incialmente todavia no se ha hecho ninguna simulacion
  public static String tipoSalidaResultados = "Terminal"; //indica por donde se sacan los resultados de la simulacion
  public static long iteracionesRedibujado =  1; //iteraciones de la simulacion que se dan hasta que se sincroniza con la grafica
  public static boolean mostrarPoblacion = false;   //en cada generacion se muestra toda la poblacion


  /*---Parametros de configuracion de la simualacion (los public static pueden ser cambiados en tiempo de ejecucion)--------*/
   private Poblacion poblacion;  //poblacion que va a solucionar el problema
  private int numeroIndividuos; //numero de individuos que tiene la poblacion
  private int longitudCromosoma; //numero de genes que tendra cada cromosoma
  private int presionSelectiva; //numero de individuos contra los que tiene que competir en cada generacion
  private double probabilidadMutarGen; //probabilidad de que se mute cada gen de cada individuo
  private int pasosParaAumentarLaPresion; //cada cuantos pasos aumentamos en 1 la presion selectiva
  private int elitismo; //numero de individuos que pasan a la siuiente generacion por ser los mejores
  private double puntuacionAObtener; //puntuacion del invidividuo a buscar
  private long iteracionesMaximas; //pasosMaximo pasos maximos que tendra la simulacion
  private Object[] otrosParametros;  //posibles parametros que pueda necesitar el algortimo
  private String tipoIndividuo; //indica que tipo de inviduo esta seleccionado (binario diploide, binario haploide...)
  private String tipoSobrecruzamiento = "Partir por la mitad"; //indica la manera en que debe hacerse el sobrecruzamiento en los cromosomas
  private boolean mejorInmutable=true;  //determina si el individuo mejor de la poblacion esta expuesto o no a mutacion
  private boolean incrementarMutacionConIteraciones = true;  //determina si vamos incrementando la mutacion segun crece el numero de iteraciones
  private boolean isMaximizationProblem; //indica si es un problmea de maximizacion o minimizacion

  /*-------------Parametros dinamicos de la simulacion----------------*/
  private Individuo individuo;                 //mejor individuo de la poblacion
  public int iteraciones = 0;                  //nº de iteraciones realizadas
  public double puntuacionObtenida = 0;        //puntuacion del mejor individuo obtenida hasta el momento
  public double puntuacionPoblacionMedia = 0;  //puntuacion media de los individuos de la poblacion
  double puntuacionAnterior = 0;               //puntuacion del mejor individuo de la generacion anterior
  boolean mutacionAlterada = false;            //indica si se ha duplicado la mutacion durante una generacion debido a que no se ha mejorado la mejor puntuacion (si es asi se duplica la mutacion para una sola generacion)


  /** Constructor que recibe la interfaz grafica para poder comunicarse con ella */
  public Simulador(InterfaceGrafica interfaceGrafica){
    this.interfaceGrafica = interfaceGrafica;
  }

  /** Constructor que no recibe nada, para ser llamado sin interfaz grafica */
  public Simulador(){}


  /**
   * Constructor de Simulador: obtiene los valores de la simulacion de los dados por defecto en el problema
   * @param problema Poblacion relativa al problema que se quiere solucionar
   * @param individuoSoluciona String Individuo con el que se quiere solucionar (debe estar soportado por el problema)
   */
  public Simulador(Poblacion poblacion, String individuoSoluciona) {
    this.poblacion = poblacion;
    Problema problema = poblacion.getProblema();
    this.tipoIndividuo = individuoSoluciona;
    this.puntuacionAObtener = problema.puntuacionAObtenerPorDefecto();
    this.numeroIndividuos = problema.numeroIndividuosPorDefecto();
    this.longitudCromosoma = problema.longitudCromosomaPorDefecto();
    this.presionSelectiva = problema.presionSelectivaPorDefecto();
    this.probabilidadMutarGen = problema.probabilidadMutarGenPorDefecto();
    this.pasosParaAumentarLaPresion = problema.numIteraccionesParaAumentarPresionSelectivaPorDefecto();
    this.elitismo = problema.elitismoPorDefecto();
    this.iteracionesMaximas = problema.iteracionesMaximasPorDefecto();
    this.tipoSobrecruzamiento = problema.tipoSobrecruzamientoPorDefecto();
    this.mejorInmutable = problema.mejorInmutablePorDefecto();
  }

  /**
   * Asigna los parametros de la simulacion
   * @param poblacion Poblacion Poblacion necesaria para solucionar el problema
   * @param tipoIndividuo String Individuo con el que se quiere solucionar (debe estar soportado por el problema)
   * @param puntuacion double Puntuacion a obtener para deterner la simulacion
   * @param numeroIndividuos int Numero de individuos que forman la poblacion
   * @param longitudCromosoma int Tamanyo del cromosoma de cada individuo (numero de genes)
   * @param presionSelectiva int Numero de individuos contra los que hay que competir para reproducirse
   * @param probabilidadMutarGen double Probabilidad de un gen de un individuo de ser mutado
   * @param pasosParaAumentarLaPresion int Numero de generaciones que tienen que pasara para aumentar en uno la presion selectiva
   * @param elitismo int Numero que determina cuantos individuos pasan a la siguiente generacion simplemente por ser los mejores
   * @param iteracionesMaximas long Numero de iteraciones maximas a generar
   * @param tipoSobrecruzamiento String Cada cuantas iteraciones se mostraran resultados de la simulacion
   * @param iteracionesRedibujado long Relativo a los posibles parametros que pueda necesitar el algoritmo para evaluar a los individuos
   * @param otrosParametros Object[] Relativo a los posibles parametros que pueda necesitar el algoritmo para evaluar a los individuos
   * @throws Exception
   */
  public void asignarParametros(Poblacion poblacion,
				String tipoIndividuo,
				double puntuacion,
				int numeroIndividuos,
				int longitudCromosoma,
				int presionSelectiva,
				double probabilidadMutarGen,
				int pasosParaAumentarLaPresion,
				int elitismo,
				long iteracionesMaximas,
				String tipoSobrecruzamiento,
				long iteracionesRedibujado,
				Object[] otrosParametros
				) throws Exception{

    if (tipoIndividuo==null || numeroIndividuos<1 || presionSelectiva<1 || pasosParaAumentarLaPresion < 0 || elitismo < 0 || iteracionesMaximas <1 || tipoSobrecruzamiento==null ){
      throw new Exception("Los parametros asignados a la simulacion no son validos");
    }
    this.poblacion = poblacion;
    this.tipoIndividuo = tipoIndividuo;
    this.puntuacionAObtener = puntuacion;
    this.numeroIndividuos = numeroIndividuos;
    this.longitudCromosoma = longitudCromosoma;
    this.presionSelectiva = presionSelectiva;
    this.probabilidadMutarGen = probabilidadMutarGen;
    this.pasosParaAumentarLaPresion = pasosParaAumentarLaPresion;
    this.elitismo = elitismo;
    this.iteracionesMaximas = iteracionesMaximas;
    this.tipoSobrecruzamiento = tipoSobrecruzamiento;
    this.otrosParametros = otrosParametros;
    this.iteracionesRedibujado=iteracionesRedibujado;
    this.isMaximizationProblem = poblacion.isMaximizationProblem();
  }

  /**
   *  Metodo que lanza la simulacion
   */
  public void run() {

     inicializarParametrosDinamicos();     //inicializa los parametros dinamicos

    //instanciamos la poblacion especifica en funcion del problema que se haya elegido (factory Method)
    try {

       //incializamos los individuos de la poblacion (dando valores a sus cromosomas)
       poblacion.inicializarPoblacion( numeroIndividuos,longitudCromosoma,tipoIndividuo);


       //hacemos una primera evaluacion de cada individuos que compone la poblacion
       poblacion.aplicarSeleccionNatural();
       actualizarParametrosDinamicos();
       mostrarSimulacion(tipoSalidaResultados); //mostramos la generacion 0


       //--------------------------------------------------------------------------
       //iteramos mientras no se haya alcanzado una determinada puntuacion del mejor individuo o no hayamos iterado el numero de Pasos Maximo
       do {

	 poblacion.reproducir(isMaximizationProblem,presionSelectiva, elitismo,tipoSobrecruzamiento,mejorInmutable); //reproducimos
	 poblacion.mutarPoblacion(probabilidadMutarGen); //mutamos
	 poblacion.aplicarSeleccionNatural(); //valoramos cada individuo

	 actualizarParametrosDinamicos();  //relativas a mejor individuo y puntuaciones

	 if ( (presionSelectiva < numeroIndividuos) && (iteraciones % pasosParaAumentarLaPresion == 0))  presionSelectiva += 1;  //decidimos si aumentar la presion selectiva que se produce en cada torneo

	 iteraciones++;

	 if ( (iteraciones % iteracionesRedibujado) == 0)  mostrarSimulacion(tipoSalidaResultados);  //decidimos si mostrar los resultados de la simulacion hasta el momento


       }while ( ( (isMaximizationProblem && (puntuacionObtenida < puntuacionAObtener)) || (!isMaximizationProblem && (puntuacionObtenida > puntuacionAObtener)) )
                 &&  (iteraciones < iteracionesMaximas)
                 && !parar );

       //--------------------------------------------------------------------------

       mostrarResultadosFinales(tipoSalidaResultados);

     }catch (Exception e) {  System.err.println(e.getMessage()); e.printStackTrace(); return;} //si se produce un error al instanciar la poblacion por cualquier fallo para ese contexto no se hara nada de simulacion

     parar();
   }


  /** Realiza la simulacion y devuelve el mejor individuo, pero sin crear un nuevo hilo*/
  public Individuo realizarSimulacionBloqueante(){
    run();
    return individuo;
  }


  /**
   * Metodo que muestra el estado actual de la simulacion
   * @param tipoSalidaResultados Por donde se van a mostrar los resultados
   */
  private void mostrarSimulacion(String tipoSalidaResultados){
      Simulador.simulado = true; //ya hemos simulado
      if (tipoSalidaResultados == "Grafica"){
	sincronizarConGrafica();
      }else if (tipoSalidaResultados== "Terminal"){
	System.out.println("Generacion " + this.iteraciones +"\nMejor puntuacion: "+puntuacionObtenida);
	if (hacerGraficaPoblacionMedia)
	  System.out.println("Individuo medio de la poblacion: "+puntuacionPoblacionMedia+"\n\n");
	if (mostrarPoblacion)
	  poblacion.mostrarPoblacion();

      }else if (tipoSalidaResultados == "Fichero"){
	escritor.print("Generacion " + this.iteraciones +"\nMejor puntuacion: "+puntuacionObtenida);
	if (hacerGraficaPoblacionMedia)
	  escritor.print("\nPuntuacion media de la poblacion: "+puntuacionPoblacionMedia+"\n\n");
      }
  }

  /**
   * Muestra los resultados finales de la simulacion
   * @param salida String indica donde mostrar los resultados (terminal, ventana o archivo)
   */
  public void mostrarResultadosFinales(String salida) {
    double individuosGenerados = (numeroIndividuos * this.iteraciones);
    double individuosPosibles = numPosiblesIndividuos();
    double porcent = porcentaje(individuosGenerados, individuosPosibles);
    double valorPoblacion = poblacion.valorPoblacion();

    String linea =
	"> Valoracion del mejor individuo: " + individuo._valoracion +  "\n"+
	poblacion.descripcionSusIndividuos(individuo) /*descripcion del mejor individuo*/ + "\n"+
	"> Valoracion de la poblacion: " + valorPoblacion + "\n" +
	"> Individuo medio de la poblacion: " + (valorPoblacion / numeroIndividuos) + "\n" +
	"> Nº de Generaciones: " + iteraciones + "\n" +
	"> Nº de individuos evaluados: " +(numeroIndividuos * this.iteraciones) +
	" de los " +   numPosiblesIndividuos() + " individuos posibles (" + porcent + "%) ";
    if (salida.equalsIgnoreCase("terminal")) {
      System.out.println(linea);
    }else if (salida.equalsIgnoreCase("grafica")) {
      javax.swing.JOptionPane.showMessageDialog(null,linea,"Resultados simulacion",javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }else if (salida.equalsIgnoreCase("fichero")) {
     escritor.print(linea);
     escritor.cerrar();
     javax.swing.JOptionPane.showMessageDialog(null, linea);
    }
  }


//_________________________MeTODOS PRIVADOS________________________________________________

  /**
   * Inicializa los parametros dinamicos de la simulacion
   */
  private void inicializarParametrosDinamicos() {
    parar = false;
    individuo = null;
    this.puntuacionObtenida = 0;
    this.iteraciones = 0;
    if (tipoSalidaResultados.equalsIgnoreCase("Fichero")){
      String nombreFich = javax.swing.JOptionPane.showInputDialog("Nombre del fichero para escribir la simulacion");
      escritor = new FicheroEscritura(nombreFich);
      try{  escritor.abrir(); }catch (Exception ex){System.out.println("NO se ha podido escribir en fichero"); }
    }
  }

  /**
   * Calculamos mejor individuo, puntuacion total de la poblacion y puntuacion media por individuo,
   * actualizamos el el valor de la mutacion si es preciso
   */
  private void actualizarParametrosDinamicos() {

      individuo = poblacion.mejorIndividuo(); //actualizo el mejor individuo

      //actualizo la puntuacion mejor obtenida y la media
      if (hacerGraficaPoblacionMedia) {
        puntuacionPoblacionMedia = poblacion.valorPoblacion() / numeroIndividuos;
        puntuacionObtenida = puntuacionPoblacionMedia;
      }

      if (Simulador.puntuacionPorIndividuo) {
        puntuacionObtenida = individuo._valoracion; //la puntuacion se refiere al individuo
      }

      //si no se ha mejorado la puntuacion en una serie de generacion dublicamos la probabilidad de mutar gen y luego la volvemos a dejar como estaba
      if (mutacionAlterada) {
        probabilidadMutarGen /= 3;
        mutacionAlterada = false;
        //System.out.println("Mutacion normal");
      } else {
        if ((iteraciones % 10 == 0)) {
          if (puntuacionObtenida == puntuacionAnterior) {
            probabilidadMutarGen *= 3;
            //System.out.println("Mutacion alterada");
            mutacionAlterada = true;
          }
          puntuacionAnterior = puntuacionObtenida;
        }
      }
      if (incrementarMutacionConIteraciones) {
        probabilidadMutarGen += (iteraciones * 1) / iteracionesMaximas;
      }
  }

  /** Determina el numero maximo de individuos distintos que se pueden generar para este problema*/
  private double numPosiblesIndividuos() {
    return java.lang.Math.pow(++ (individuo._valorMaximoPosible), longitudCromosoma);
  }

  /**Devuelve el porcentaje que supone una cantidad en relacion a una cantidad total*/
  private double porcentaje(double cantidad, double cantidadTotal) {
    return ( (cantidad * 100F) / cantidadTotal);
  }


//_____________________________METODOS PARA BLOQUEAR/DESBLOQUEAR EL THREAD Y SINCRONIZARLO CON LA GRAFICA_______________

  public synchronized void sincronizarConGrafica() {
    if (Simulador.simulado == true) {
      try {
	wait(); //como estoy simulando y me interesa dejar de simular para que se grafique, me bloqueo
      }catch (Exception e) {
	System.out.println(e);
	System.out.println("el simulador no hace el wait");
      }
    }
  }

  /** LLamado desde el exterior sirve para desbloquear al thread*/
  public synchronized void despertar() {
    //System.out.println("voy a despertar el simulador");
    this.notify();
    //System.out.println("deberia aberse despertado ya el simulador");
  }

  /** Le pasamos a la clase la grafica para luego desde ella poder sincronizarse y desbloquearla*/
  public void asignarGrafica(Grafica g) {
    _grafica = g;
  }

  /**Para la simulacion*/
  public void parar() {
    if (tipoSalidaResultados.equalsIgnoreCase("Fichero")){
      escritor.cerrar();
    }else  if (this.tipoSalidaResultados == "Grafica") {
      _grafica.parar();
    }
    if (interfaceGrafica!=null){
      interfaceGrafica.cambiarEstadoParametrosNoActualizables(true);
    }
    parar = true;
  }



//_________________________________MeTODOS GET/SET_____________________________________________________

  /** metodo que sirve para cambiar la probabilidad de mutar un gen (se una para cambiarla en tiempo de ejecucion)*/
  public void setProbabilidadMutarGen(double probabilidad){
    this.probabilidadMutarGen = probabilidad;
  }

  /** Sirve para asignar el individuo con el que se realizara la simulacion*/
  public void setTipoIndividuo(String individuo){
    this.tipoIndividuo = individuo;
  }

  public void setTipoSobrecruzamiento(String tipoSobrecruzamiento) {
    this.tipoSobrecruzamiento = tipoSobrecruzamiento;
  }

  public void setIteracionesMaximas(long iteracionesMaximas) {
    this.iteracionesMaximas = iteracionesMaximas;
  }

  public void setPuntuacion(double puntuacion) {
    this.puntuacionAObtener = puntuacion;
  }

  public void setMejorInmutable(boolean mejorInmutable) {
    this.mejorInmutable = mejorInmutable;
  }

  public void setIncrementarMutacionConIteraciones(boolean incrementarMutacionConIteraciones) {
    this.incrementarMutacionConIteraciones = incrementarMutacionConIteraciones;
  }

  /** Devuelve la probabilidad de mutar un gen */
  public double getProbabilidadMutarGen(){
    return this.probabilidadMutarGen;
  }

  /** Devuelve el tipo de individuo con el que se realizara la solucion */
  public String getTipoIndividuo(){
    return this.tipoIndividuo;
  }

  public String getTipoSobrecruzamiento() {
    return tipoSobrecruzamiento;
  }

  public long getIteracionesMaximas() {
    return iteracionesMaximas;
  }

  public double getPuntuacionAObtener() {
    return puntuacionAObtener;
  }

  public boolean isMejorInmutable() {
    return mejorInmutable;
  }

  public boolean isIncrementarMutacionConIteraciones() {
    return incrementarMutacionConIteraciones;
  }

  public boolean esMejorInmutable() {
    return mejorInmutable;
  }

  public boolean isMaximizationProblem(){
    return isMaximizationProblem;
  }


}
