package TempleSimulado;

/**
 * Clase que realiza el algoritmo de templado simulado para un determinado problema
 */
public class TempleSimulado {

  private Problema problema;
  private double temperaturaActual;  //<--- temperatura global del sistema
  private Estado estadoActual;
  private int numEtapa;
  double temperaturaEstadoActual;  //<--- resultado de la funcion de evaluacion del estado

  /**
   * Constructor, necesita recivir el problema a resolver
   * @param problema Problema Problema que evalua cada estado (codificacion del problema como funcion de evaluacion de temperatura de los posibles estados, es la heuristica)
   */
  public TempleSimulado(Problema problema) {
    this.problema = problema;
  }

  /**
   * Realiza el templado simulado parar resolver el problema usando sus valores por defecto
   */
  public Estado templadoSimulado(){
    return templadoSimulado (problema.valorDefectoTemperaturaInicial(),
			     problema.valorDefectoTemperaturaFinal(),
			     problema.valorDefectoEtapasMax(),
			     problema.valorDefectoIteraccionesMaxEtapa(),
			     problema.valorDefectoAceptacionesMaxEtapa(),
			     problema.valorDefectoFactorEnfriamiento(),
			     problema.valorDefectoFactorCalentamiento(),
			     problema.valorDefectoFactorIncrementoIteraccionesMaxEtapa(),
			     problema.estadoInicial());
  }


  /**
   *
   * Algoritmo de TempleSimulado o tambien llamado Simulated Annealing
   *
   * Trata de disminuir la temperatura partiendo desde una inicial a una final (es mayor la inicial que la final),
   * para ello el algortimo va ejecutando ETAPAS, cada etapa tiene una temperatura distinta,
   * y al finalizar la etapa se actualiza la temperatura y se pasa a la siguiente etapa.
   * En cada etapa se realizan un numero maximimo de iteracciones para lograr
   * cambiar de estado el sistema hasta un numero  maximo de cambios o aceptaciones
   *
   * @param temperaturaInicial double Temperatura desde la que se parte
   * @param temperaturaFinal double Temperatura que se quiere alcanzar
   * @param temperaturaFinal long Etapas maximas a generar
   * @param iteracionesMaxEtapa long Iteracciones maximas permitidas en cada etapa
   * @param aceptacionesMaxEtapa int Cambios maximos de estados permitidos en cada etapa
   * @param factorEnfriamiento double Factor con el que bajara la temperatura al cambiar de etapa (entre 0 y 1, normalmente proximo a 1)
   * @param factorCalentamiento double Factor con el que aumentara la temperatura al cambiar de etapa (entre 1 y 2, normalmente mayor a 1.25)
   * @param factorIncrementoIteraccionexMaxEtapa double Factor con el que va aumentando el numero maximo de iteracciones que se permiten en cada etapa (mayor que 1)
   * @param estadoInicial Estado Estado desde el que partimos
   * @param problema Problema
   */
  public Estado templadoSimulado(double temperaturaInicial, double temperaturaFinal, long etapasMax, long  iteracionesMaxEtapa, int aceptacionesMaxEtapa, double factorEnfriamiento, double factorCalentamiento, double factorIncrementoIteraccionexMaxEtapa, Estado estadoInicial) {


    temperaturaActual = temperaturaInicial;
    estadoActual = estadoInicial; Estado estadoNuevo = null;
    int aceptacionesRealizadas=0, iteracionesRealizadas=0;
    numEtapa = 0;
    double distribucionProbabilidad;
    double temperaturaEstadoNuevo;
    temperaturaEstadoActual = problema.funcionEvaluacionEnergia(estadoActual);


    //mientras la temperatura actual sigua siendo mayor que la final a obtener tenemos que seguir minimizandola: generamos etapas
    while ((temperaturaActual >= temperaturaFinal) && (numEtapa<etapasMax)){


      //iteramos en cada ETAPA cambiando de estados
      while ((aceptacionesRealizadas < aceptacionesMaxEtapa) && (iteracionesRealizadas < iteracionesMaxEtapa)) {

	estadoNuevo = estadoActual.obtenerUnSiguiente();  //obtenemos un estado vecino al actual
	temperaturaEstadoNuevo = problema.funcionEvaluacionEnergia(estadoNuevo);

	if (temperaturaEstadoNuevo < temperaturaEstadoActual) {
	  estadoActual = estadoNuevo;
	  temperaturaEstadoActual = temperaturaEstadoNuevo;
	  aceptacionesRealizadas++;
	}else{
	  distribucionProbabilidad = Math.exp((temperaturaEstadoActual - temperaturaEstadoNuevo) / temperaturaActual);
	  if (Math.random() < distribucionProbabilidad) {
	    estadoActual = estadoNuevo;
	    temperaturaEstadoActual = temperaturaEstadoNuevo;
	    aceptacionesRealizadas++;
	  }
	}

	iteracionesRealizadas++;
      } //fin etapa
      Thread.yield();

      System.out.println("Etapa " + numEtapa + " con una temperatura de " + temperaturaActual+ "y una funcion de evaluacion: " + temperaturaEstadoActual);

      if (aceptacionesRealizadas == aceptacionesMaxEtapa) { //si se ha cambiado de etapa por llegar al max de cambios de estado
	temperaturaActual *= factorEnfriamiento;
      }else if (iteracionesRealizadas == iteracionesMaxEtapa) { //si se ha cambiado de etapa por llegar al max de iteracciones permitidas
	temperaturaActual *= factorCalentamiento;
      }

      iteracionesMaxEtapa *= factorIncrementoIteraccionexMaxEtapa; //incrementamos el numero de iteracciones maximas permitidas por etapa debido a que cada vez debe ser mas dificil el movimiento a un estado mejor
      numEtapa++;
      aceptacionesRealizadas = 0;
      iteracionesRealizadas = 0;

    } //fin del algoritmo
    return estadoActual;
  }

  /**
   * @return double Devuelve la temperatura actual del sistema
   */
  public double temperaturaActual(){
    return temperaturaActual;
  }


  /**

   * @return double La evaluacion del estado en el que se encuentra el sistema
   */
  public double evaluacionEstadoActual(){
    return temperaturaEstadoActual;
  }

  /**
   * @return int Devuelve la etapa actual
   */
  public int numEtapa(){
    return numEtapa;
  }

}


