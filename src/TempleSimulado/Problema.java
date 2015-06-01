package TempleSimulado;

import TempleSimulado.Problema;

/**
 * Define los metodos que deben tener los problemas que se pueden resolver con
 * Temple simulado
 */
public abstract class Problema {


 //___________METODOS DE IMPLEMENTACIoN OBLIGATORIA EN CADA NUEVO PROBLEMA________________________

  /**
   * Funcion heuristica que representa la codificacion del problema y que se intente minimizar
   * @param estado Estado que se desea evaluar
   * @return double Temperatura asociada al estado
   */
  public abstract double funcionEvaluacionEnergia(Estado estado);

  /**
   * @return Estado Estado desde el que se inicia la solucion del problema
   */
  public abstract Estado estadoInicial();



  /**
   * Sirve para instanciar problemas especificos que no necesiten argumentos indicando la ruta completa
   * @param pathProblema String path completo donde se encuentra el problema (ej: TempleSimulado.Problemas.Viajante.ProblemaViajante)
   * @exception Excepciones gneeradas si el path del problema no es valido
   * @return Problema problema concreto instanciado
   */
  public static Problema  instanciarProblema(String pathProblema) throws Exception{
    Class claseProblema =Class.forName(pathProblema);
    java.lang.reflect.Constructor[] constructoresProblema = claseProblema.getConstructors();
    Object[] argumentosProblema=null;
    return ((Problema)constructoresProblema[0].newInstance(argumentosProblema));
  }

  //______________Metodos que pueden ser sobrescritos para adaptarlos al problema en concreto__________

  /**
   * Valor por defecto con el que se resolvera el problema: 100
   * Temperatura desde la que se parte y se desea minimizar
   * @return double
   */
  public double valorDefectoTemperaturaInicial(){
    return 100;
  }

  /**
   * Valor por defecto con el que se resolvera el problema: 0
   * @return double Temperatura que se desea alcanzar
   */
  public double valorDefectoTemperaturaFinal(){
   return 0;
  }

  /**
   * Valor por defecto con el que se resolvera el problema: 1000
   * @return double Numero maximo de etapas a generar
   */
  public long valorDefectoEtapasMax(){
   return 100;
  }

  /**
   * Valor por defecto con el que se resolvera el problema: 100
   * @return double Iteracciones maximias permitidas en cada etapa inicialmente (luego va variando segun el factor de incremento
   */
  public long valorDefectoIteraccionesMaxEtapa(){
    return 10;
  }

  /**
   * Valor por defecto con el que se resolvera el problema: 10
   * @return int Numero maximo de cambios de estados permitidos para una misma etapa
   */
  public int valorDefectoAceptacionesMaxEtapa(){
    return 5;
  }

  /**
   * Valor por defecto con el que se resolvera el problema: 0.9
   * @return double Factor por el que se va a multiplicar la temperatura en cada etapa que disminuya
   */
  public double valorDefectoFactorEnfriamiento(){
    return 0.9;
  }

  /**
   * Valor por defecto con el que se resolvera el problema: 1.30
   * @return double Factor por el que se va a multiplicar la temperatura en cada etapa que aumente
   */
  public double valorDefectoFactorCalentamiento(){
    return 1.30;
  }

  /**
  * Valor por defecto con el que se resolvera el problema: 1.50
  * @return double Factor por el que se va a multiplicar el numero de iteracciones max por etapa al finalizar la etapa
  */
  public double valorDefectoFactorIncrementoIteraccionesMaxEtapa(){
    return 1.50;
  }



  /**
  * Valor por defecto con el que se resolvera el problema: "No se ha indicado ningun tipo de informacion para este problema"
  * @return String Informacion sobre el problema
  */
  public String ayuda(){
    return "No se ha indicado ningun tipo de informacion para este problema";
  }

}
