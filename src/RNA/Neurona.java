package RNA;

import java.lang.reflect.Method;

/**
 * Neurona Artificial, tiene unas entradas, unos pesos para dichas entradas, una salida, un umbral
 * y una funcion de activacion de las cuales se implementan varias y se debera seleccionar la
 * apropiada al calcular la salida de la neurona
 */

public class Neurona {

  private double[] pesosEntrada;  /** Pesos de las entradas que le llegan a la neurona */
  private double umbral;          /** Umbral de decision de la neuronas */
  private int numEntradas;        /** Numero de entradas que le llegan a la neurona */
  private double[] entradas;      /** Valor de las entradas a la red */
  private double salida;          /** Valor de salida de la neurona */


  /**
   * Generamos una neurona dando valores a los pesos de las entradas y al umbral de decision
   */
  public Neurona (int numEntradas) {
    this.numEntradas = numEntradas;
    pesosEntrada = new double[numEntradas];
    entradas = new double[numEntradas];
    for (int i = 0; i < numEntradas; i++) {
      pesosEntrada[i] = generarValor();
    }
    umbral = generarValor();
  }

  /**
   * Calcula la salida de la neurona, segun las entradas que tiene, el peso de dichas entradas, y el umbral
   * Notar que para las neuronas de la capa inicial no se debe llamar a este metodo, pues su salida es directamente la entrada que reciben
   * @param inputs double[] Valor de las entradas a dicha neurona
   * @param funcionActivacion String metodo que sera llamado para determinar la salida de la red (se usa reflexion)
   * @return double la salida de la neurona para esas entradas y con esa funcion de activacion
   * @throws Exception Si la funcion de activacion indicada no se corresponde con ningun metodo de esta clase
   */
  public double calcularNeurona(double[] inputs, String funcionActivacion) throws Exception{

    double resultado = 0.0;

    entradas = inputs;
    if (inputs.length != pesosEntrada.length) {
      System.err.println("ERROR: hemos recivido distinto numero de entradas de los que admite la neurona ");
      System.exit( -1);
    }

    //multiplicamos cada valor de cada entrada por su peso y lo vamos acumulando
    for (int i = 0; i < inputs.length; i++) {
      resultado += inputs[i] * pesosEntrada[i];
    }
    resultado += umbral;
    resultado = invocarFuncionActivacion(funcionActivacion, resultado);
    salida = resultado;
    return salida;
  }




//_______________ FUNCIONES DE ACTIVACION QUE SE IMPLEMENTAN____________________

  /** Usando la funcion sigmoidal */
  public double noLineal(double x) {
    double b = Math.exp( -x);
    return 1 / (1 + b);
  }

  /** Usando la funcion escalon */
  public double escalon(double x){
    if (x > 0){
      return 1;
    }else{
      return -1;
    }
  }
//______________________________________________________________________________




  /** Genera un valor entre -1 y 1 */
  private double generarValor() {
    double signo;
    double resultado;

    signo = Math.random();
    if (signo > 0.5) {
      resultado = Math.random();
    }else {
      resultado = Math.random() * ( -1.0);
    }
    return resultado;
  }


  /**
   * Invoca la funcion de activacion de esta clase, con el nombre indicado y
   * pasandole como argumento el valor pasado por parametros. Devuelve el valor
   * de returno de la funcoin de activacion. SE USA LA REFLEXIoN DE JAVA para
   * encontrar el metodo que coincide con el String pasado por argumentos
   */
  private double invocarFuncionActivacion(String funcionActivacion, double valor ) throws Exception{
    try{
      Class[] argumentos = {Double.TYPE};
      Class claseActual = Class.forName("RNA.Neurona");
      Method funcion = claseActual.getMethod(funcionActivacion, argumentos);
      Object[] parametros = new Object[1];
      parametros[0] = new Double(valor);
      return ((Double) funcion.invoke(this, parametros)).doubleValue();
    }catch (NoSuchMethodException error){
      throw new Exception("No existe la funcion de activacion que se ha indicado, elija una de las implementadas");
    }
  }





//____________________________METODOS GET/SET___________________________________

  /** Devuelve el numero de entradas que teine la neurona*/
  public int getNumeroEntradas(){
    return numEntradas;
  }
  /** Devuelve el valor de las entradas a la neurona */
  public double[] getEntradas() {
    return entradas;
  }


  /** @return double Devuelve el valor concreto de una entrada a la neurona */
  public double getEntrada(int numEntrada) {
    return entradas[numEntrada];
  }

  /** @return double Devuelve la salida de la nuerona (no realiza el calculo) */
  public double getSalida() {
    return salida;
  }

  /** @return double[] Devuelve el valor de los pesos de las entradas a la neurona */
  public double[] getPesosEntrada() {
    return pesosEntrada;
  }

  /** @return double Devuelve el valor del umbral */
  public double getUmbral() {
    return umbral;
  }

  /**
   * Establece el valor de los pesos de las entradas a la neurona
   * @param pesos double[] pesos de la red
   */
  public void setPesosEntrada(double[] pesos) {
    pesosEntrada = pesos;
  }

  /**
   * Establece el valor del umbral
   * @param nuevo double Nuevo umbral
   */
  public void setUmbral(double nuevo) {
    umbral = nuevo;
  }

  /**
   * Establece la salida para la neurona sin calcularla
   * @param salida double valor de salida de la neurona
   */
  public void setSalida(double salida){
    this.salida=salida;
  }


}
