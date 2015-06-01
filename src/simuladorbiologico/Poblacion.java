package simuladorbiologico;


/** Esta clase abstracta simula una poblacion de individuos, contiene los operadores genericos y su implementacion
 *  pero queda para la poblacion especifica la posibilidad de sobrescribir estas  3 cosas:
 *      -la instanciacion de los individuos concretos (por ej Diploides y con cromosoma binario)
 *      -la manera de aplicar la seleccion natural (apliciando la valoracion en su contexto especifico)
 *      -la manera de reproducir la poblacion
 */

public abstract class Poblacion {

  protected Individuo[] _individuos;             /**individuos que conforman la poblacion*/
  protected Problema _contexto;                  /**contexto en el que seran evaluados los individuos*/
  protected Object[] argumentosOpcionales=null;  /**argumentos que puede necesitar el contexto*/

  /**
   *  Crea una poblacion para el problema indicado
   * @param contextoEspecifico Problema problema que se quiere solucionar
   */
  public Poblacion (Problema contextoEspecifico){
    _contexto = contextoEspecifico;
  }

  /**
   * Instanciamos una poblacion especifica
   * @param problema Problema problema relacionado con la poblacion
   * @param pathPoblacion String ruta completa donde se encuentra el archivo de la poblacion
   * @return Poblacion Poblacion concreta instanciada
   * @throws Exception generadas si el path del problema no es valido
   */
  public static Poblacion instanciarPoblacion(Problema problema, String pathPoblacion) throws Exception{
    Class clasePoblacionEspecifica =Class.forName(pathPoblacion);
    java.lang.reflect.Constructor[] constructores = clasePoblacionEspecifica.getConstructors();
    Object[] argumentos = { problema };
    return ( (Poblacion)(constructores[0].newInstance(argumentos)) );
  }


  /** @return Valor minimo que podra tomar cada gen de cada individuo de la poblacion */
  public abstract double valorMinimoGenIndividuos();


  /** @return Valor maximo que podra tomar cada gen de cada individuo de la poblacion */
  public abstract double valorMaximoGenIndividuos();


  /**
   * Se le da una valoracion a cada individuo para tener mas probabilidades de sobrevivir
   * ESTE METODO PUEDE SER SOBRESCRITO DE FORMA ESPECiFICA EN CADA SUBCLASE
   */
  public void aplicarSeleccionNatural(){
    for (int i = 0; i < _individuos.length; i++){
     _individuos[i]._valoracion = _contexto.evaluarIndividuo(_individuos[i]);
   }
  }


  public boolean isMaximizationProblem(){
    return _contexto.isMaximizeProblem();
  }

  /**
   * ESTE METODO PUEDE SER SOBRESCRITO DE FORMA ESPECiFICA EN CADA SUBCLASE
   * La poblacion se reproduce en funcion a las valoraciones de cada individuo; notar que cada individuo de la nueva
   * poblacion no ha sido evaluado todavia ni ha sufrido todavia procesos de seleccion
   * @param presionSelectiva int  a numero mas grande mas presion se hace, cuanto mas pequenyo menos presion
   * @param isMaximizationProblem True si es un problema de maximizacion y False si es de minimizacion
   * @param elitismo int Numero de individuos que pasan directamente a la siguiente generacion por ser los mejores
   * @param tipoSobrecruzamiento String la forma en la que se realizara el sobrecruzamiento entre los individuos
   * @param mejorInmutable boolean Determina si el mejor individuo esta expuesto a mutacion o no
   * @throws Exception
   */
  public void reproducir(boolean isMaximizationProblem, int presionSelectiva,int elitismo, String tipoSobrecruzamiento,boolean mejorInmutable) throws Exception{
    Reproduccion.torneos(_individuos,isMaximizationProblem, presionSelectiva,elitismo,tipoSobrecruzamiento,mejorInmutable);
  }


  /**
   * Dado un individuo que pertenezca a su poblacion lo debe describir en terminos del problema
   * @param individuo Individuo a describir
   * @return String con la descripcion del individuo
   */
  public  String descripcionSusIndividuos (Individuo individuo){
     return _contexto.interpretacionDelIndividuo(individuo);
  }

  /**
   * Inicializa toda la poblacion, inicializando cada individuo
   * @param numIndividuos int numero de individuos que tendra la poblacion
   * @param tamanyoCromosoma int tamanyo del cromosoma de cada individuo
   * @param tipoIndividuo String tipo de individuo que formara parte de la poblacion
   */
  public void inicializarPoblacion(int numIndividuos, int tamanyoCromosoma, String tipoIndividuo){
    _individuos = new Individuo[numIndividuos];
   for (int i=0; i < _individuos.length; i++){
      /*instanciamos el individuo concreto con el metodo factoria que hemos construido
       en la clase Individuo que fabrica todos los tipos posibles de individuos implementados*/
       _individuos[i] = Individuo.instanciarIndividuo(tipoIndividuo,tamanyoCromosoma,valorMaximoGenIndividuos(),valorMinimoGenIndividuos());
       _individuos[i].inicializar();
   }
  }

  /**
   * @return Devuelbe la suma total de la valoraciones de cada individuo
   */
  public double valorPoblacion(){
    double total=0;
    for (int i = 0; i < _individuos.length; i++){
      total += _individuos[i]._valoracion;
    }
    return total;
  }


  /**
   * @return Individuo Devuelbe el individuo con mejor valoracion
   */
  public Individuo mejorIndividuo(){
    double puntuacionMejor;
    if (_contexto.isMaximizeProblem()){
      puntuacionMejor = -Double.MAX_VALUE;
    }else{
      puntuacionMejor = Double.MAX_VALUE;
    }
    Individuo mejor= null;
    for (int i = 0; i < _individuos.length; i++){
      if (_contexto.isMaximizeProblem()){  //es un problema de maximizacion
        if (_individuos[i]._valoracion > puntuacionMejor) {
          mejor = _individuos[i];
          puntuacionMejor = _individuos[i]._valoracion;
        }
      }else{   //es un problema de minimizacion
        if (_individuos[i]._valoracion < puntuacionMejor) {
          mejor = _individuos[i];
          puntuacionMejor = _individuos[i]._valoracion;
        }
      }
    }
    return mejor;
  }

  /**
   * Produce mutacines en la poblacion en funcion de una probabilidad recibida
   * @param probabilidadIndividuo Probabilidad de mutar cada gen de cada individuo
   */
  public void mutarPoblacion(double probabilidadIndividuo){
    for (int i = 0; i < _individuos.length; i++){
      _individuos[i].mutar(probabilidadIndividuo);
    }
  }

  /** Muestra la poblacion */
  public void mostrarPoblacion(){
    for (int numInd =0; numInd < _individuos.length; numInd++){
      System.out.println(_individuos[numInd]);
    }
    System.out.println("La puntuacion global de la poblacion es de: " + valorPoblacion());
  }

  /** @return Problema el problema al que hace referencia esta poblacion*/
  public Problema getProblema(){
    return _contexto;
  }


}
