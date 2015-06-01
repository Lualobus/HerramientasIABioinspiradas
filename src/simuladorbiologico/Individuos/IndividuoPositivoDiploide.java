package simuladorbiologico.Individuos;

import simuladorbiologico.*;


public class IndividuoPositivoDiploide extends Individuo {

  public double[][] _cromosomas; //el cromosoma esta formado por numeros positivos


  public IndividuoPositivoDiploide(int tamanyoCromosoma, double valorMaximoPosible, double valorMinimoPosible) {
    _cromosomas = new double[2][tamanyoCromosoma];
    super._valorMaximoPosible = valorMaximoPosible;
    super._valorMinimoPosible = valorMinimoPosible;
    //super._valorMaximoPosible = 20;
    //super. _valorMinimoPosible = 0;
  }

  public IndividuoPositivoDiploide(double[][] cromosomas, double valorMaximoPosible, double valorMinimoPosible) {
    _cromosomas = cromosomas;
    super._valorMaximoPosible = valorMaximoPosible;
    super._valorMinimoPosible = valorMinimoPosible;
    //super._valorMaximoPosible = 20;
    //super. _valorMinimoPosible = 0;
  }

  public IndividuoPositivoDiploide(double[] cromosomas1, double[] cromosomas2, double valorMaximoPosible, double valorMinimoPosible) {
    _cromosomas = new double[2][cromosomas1.length];
    System.arraycopy(cromosomas1,0,_cromosomas[0],0,cromosomas1.length);
    System.arraycopy(cromosomas2,0,_cromosomas[1],0,cromosomas2.length);
    super._valorMaximoPosible = valorMaximoPosible;
    super._valorMinimoPosible = valorMinimoPosible;
    //super._valorMaximoPosible = 20;
    //super. _valorMinimoPosible = 0;
  }



  /**
   * Inicializa aleatoriamente el cromosoma del individuo
   */
  public void inicializar() {
    int longitudCromosoma = _cromosomas[0].length;
    for (int j = 0;j <2; j++){
      for (int i = 0; i < longitudCromosoma; i++) {
	_cromosomas[j][i] = Math.round((float) (Math.random() * _valorMaximoPosible));
      }
    }
  }

  /**
   * Puede producir una mutacion en alguna parte del cromosoma del individuo
   * @param probabilidad Probabilidad de mutar cada gen en concreto del cromosoma
   */
  public void mutar(double probabilidad) {
    if (!inmutable){  //todos son susceptibles de ser mutados menos los denominados Inmutables (que suelen ser los elitistas)
      int longitudCromosoma = _cromosomas[0].length;
      for (int j = 0; j < 2; j++) {
        for (int i = 0; i < longitudCromosoma; i++) {
          if (Math.random() * 100 < probabilidad) {
            _cromosomas[j][i] = Math.round((float) (Math.random() * _valorMaximoPosible));
          }
        }
      }
    }else{
      inmutable = false; //es inmutable solo durante una generacion;
    }
  }

  /**
   * Devuelve un string con su representacion
   */
  public String toString() {
    String aDevolver = "";
    int longitudCromosoma = _cromosomas[0].length;
    for (int j = 0; j < 2; j++){
      for (int i = 0; i < longitudCromosoma; i++) {
	aDevolver += (_cromosomas[j][i] + " ");
      }
      aDevolver += "\n";
    }
    aDevolver += "\n";
    return aDevolver;
  }


  /**
   * Se cruza el individuo actual con el recibido por parametros
   * @param individuo Individuo con el que se desea cruzar
   * @return Devuelbe el individuo "hijo" resultado del cruce
   */
  public Individuo cruzar(Individuo individuo, String tipoSobrecruzamiento) throws Exception {
    IndividuoPositivoDiploide pareja = (IndividuoPositivoDiploide)individuo;
    /*el individuo diploide pasa a generar un gameto haploide, para conseguir el gameto
       haploide debo por sobrecruzamiento entre los dos pares de cromosomas uno solo*/
    double[] gameto1 = Reproduccion.sobrecruzamientoReal(_cromosomas[0],_cromosomas[1],tipoSobrecruzamiento);
    double[] gameto2 = Reproduccion.sobrecruzamientoReal(pareja._cromosomas[0],pareja._cromosomas[1],tipoSobrecruzamiento);
    return (new IndividuoPositivoDiploide(gameto1,gameto2,super._valorMaximoPosible,super._valorMinimoPosible));
  }

}
