package simuladorbiologico.Individuos;
import simuladorbiologico.*;

/**
  * Individuo formado por un cromosoma de numeros positivos decimales
  * Puede tener un valor maximo cada cromosoma
  */

public class IndividuoPositivoHaploide extends Individuo {

  public double[] _cromosomas;              //el cromosoma esta formado por numeros positivos


  public IndividuoPositivoHaploide(int tamanyoCromosoma,double valorMaximoPosible, double valorMinimoPosible){
    _cromosomas = new double[tamanyoCromosoma];
    super._valorMaximoPosible = valorMaximoPosible;
    super. _valorMinimoPosible = valorMinimoPosible;
  }

  public IndividuoPositivoHaploide( double[] cromosomas,double valorMaximoPosible, double valorMinimoPosible){
    _cromosomas = cromosomas;
    super._valorMaximoPosible = valorMaximoPosible;
    super. _valorMinimoPosible = valorMinimoPosible;
  }


  /**
   * Inicializa aleatoriamente el cromosoma del individuo
   */
  public  void inicializar(){
    int longitudCromosoma = _cromosomas.length;
    for (int i =0; i < longitudCromosoma; i++){
      _cromosomas[i] = Math.round((float)(Math.random()*_valorMaximoPosible));
    }
  }

  /**
   * Puede producir una mutacion en alguna parte del cromosoma del individuo
   * @param probabilidad Probabilidad de mutar cada gen en concreto del cromosoma
   */
  public void mutar(double probabilidad){
    if (!inmutable){  //todos son susceptibles de ser mutados menos los denominados Inmutables (que suelen ser los elitistas)
      int longitudCromosoma = _cromosomas.length;
      for (int i = 0; i < longitudCromosoma; i++) {
        if (Math.random() * 100 < probabilidad) {
          _cromosomas[i] = Math.round((float) (Math.random() * _valorMaximoPosible));
        }
      }
    }else{
      inmutable = false;  //es inmutable solo durante una generacion;
    }
  }

  /**
   * Devuelve un string con su representacion
   */
  public String toString(){
    String aDevolver = "";
    int longitudCromosoma = _cromosomas.length;
    for (int i =0; i < longitudCromosoma; i++){
      aDevolver += (_cromosomas[i]+ " ") ;
    }
    aDevolver += "\n";
    return aDevolver;
  }


  /**
   * Se cruza el individuo actual con el recibido por parametros
   * @param individuo Individuo con el que se desea cruzar
   * @return Devuelbe el individuo "hijo" resultado del cruce
   */
  public Individuo cruzar( Individuo individuo,String tipoSobrecruzamiento) throws Exception{
    double[] cromosomas = ((IndividuoPositivoHaploide)individuo)._cromosomas;
    double[] cromosomasNuevoIndividuo =  Reproduccion.sobrecruzamientoReal(this._cromosomas,cromosomas,tipoSobrecruzamiento);
    return (new IndividuoPositivoHaploide(cromosomasNuevoIndividuo,super._valorMaximoPosible,super._valorMinimoPosible));
  }

}
