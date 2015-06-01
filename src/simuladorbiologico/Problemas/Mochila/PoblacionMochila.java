package simuladorbiologico.Problemas.Mochila;
import simuladorbiologico.*;

//Poblacion especifica para resolver el problema de la mochila.
public class PoblacionMochila extends Poblacion {



   /**
   * Para crear una poblacion que resuelva el problema de la mochila se necesita saber
   * el numero de individuos, asi como el peso maximo de la mochila (que se pedira aqui)
   * y el peso de cada objeto que se puede introducir (tambien se pedira aqui)
   * @param Problema problema que se va a solucionar
   */
  public PoblacionMochila(Problema problema) {
    super(problema);
  }


  /** @return Valor minimo que podra tomar cada gen de cada individuo de la poblacion */
  public double valorMinimoGenIndividuos(){
    return 0;
  }

/** @return Valor maximo que podra tomar cada gen de cada individuo de la poblacion */
  public double valorMaximoGenIndividuos(){
    return 20;
  }


}
