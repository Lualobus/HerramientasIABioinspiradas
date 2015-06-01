package simuladorbiologico.Problemas.AjustarPesosRNA;
import simuladorbiologico.*;

/** Poblacion basada en reproduccion de torneos y el contexto es el de conseguir unos*/
public class PoblacionAjustarPesosRNA  extends Poblacion{

  /**
   *Creamos individuos diploides con codificacion binaria
   */
  public PoblacionAjustarPesosRNA(Problema problema){
    super(problema);
  }


  /** @return Valor minimo que podra tomar cada gen de cada individuo de la poblacion */
  public double valorMinimoGenIndividuos(){
    return -10;
  }

/** @return Valor maximo que podra tomar cada gen de cada individuo de la poblacion */
  public double valorMaximoGenIndividuos(){
    return 10;
  }


}
