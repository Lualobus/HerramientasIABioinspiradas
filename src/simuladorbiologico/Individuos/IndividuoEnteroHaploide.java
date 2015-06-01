package simuladorbiologico.Individuos;
import simuladorbiologico.*;

/**
 * Individuo formado por un cromosoma de numeros enteros
 * Puede tener un valor maximo cada cromosoma (tanto en positivo como en negativo)
 * asi como una probabilidad establecida de encontrarnos con cromosomas negativos
 */
public class IndividuoEnteroHaploide extends Individuo{


  public int[] _cromosomas;              //el cromosoma esta formado por numeros enteros
  private double _puntuacionResultados;
  double _probabilidadCromosomaNegativo; //probabilidad de poner un entero negativo

  public IndividuoEnteroHaploide(int tamanyoCromosoma, double valorMaximoPosible, double valorMinimoPosible){
    _cromosomas = new int[tamanyoCromosoma];
    super._valorMaximoPosible = valorMaximoPosible;
    super. _valorMinimoPosible = valorMinimoPosible;
    super.inmutable = false;
    _probabilidadCromosomaNegativo = 0.240; //<<<------ valor de probabilidad de poner un cromosoma negativo
  }

  public IndividuoEnteroHaploide( int[] cromosomas, double valorMaximoPosible, double valorMinimoPosible){
    _cromosomas = cromosomas;
    super._valorMaximoPosible = valorMaximoPosible;
    super. _valorMinimoPosible = valorMinimoPosible;
    _probabilidadCromosomaNegativo = 0.240; //<<<------ valor de probabilidad de poner un cromosoma negativo
  }

  /**
   * @return Devuelbe el atributo cromosomas
   */
  public int[] getCromosomas(){
    return _cromosomas;
  }


  /**
   * Inicializa aleatoriamente el cromosoma del individuo (no se permite el 0)
   */
  public  void inicializar(){
    int longitudCromosoma = _cromosomas.length;
    int nuevoGen = 0;
    for (int i =0; i < longitudCromosoma; i++){
      do{
	nuevoGen = Math.round((float)(Math.random()*_valorMaximoPosible));
      }while (nuevoGen == 0);
      _cromosomas[i] = nuevoGen;
      if (Math.random()<_probabilidadCromosomaNegativo){
	_cromosomas[i] -= 2*_cromosomas[i];  //ponemos el mismo valor pero en negativo
      }
    }
  }

  /**
   * Puede producir una mutacion en alguna parte del cromosoma del individuo (no permitimos el 0)
   * @param probabilidad Probabilidad de mutar cada gen en concreto del cromosoma (0-100)
   */
  public void mutar(double probabilidad){
    if (!inmutable){  //todos son susceptibles de ser mutados menos los denominados Inmutables (que suelen ser los elitistas)
      int longitudCromosoma = _cromosomas.length;
      int nuevoGen = 0;
      for (int i =0; i < longitudCromosoma; i++){
	if (Math.random()*100 < probabilidad){
	  do{
	    nuevoGen = Math.round((float)(Math.random()*_valorMaximoPosible));
	  }while (nuevoGen == 0);
	  _cromosomas[i] = nuevoGen;
	 if (Math.random()<_probabilidadCromosomaNegativo){
	   _cromosomas[i] += -2*_cromosomas[i];  //si estaba en positivo lo ponemos en negativo, y si estaba en negativo lo ponemos en positivo
	 }
	}
      }
    }else{
      inmutable = false; //es inmutable solo durante una generacion;
    }
  }
  /**
   * Devuelve una cadena con su composicion
   */
  public String toString(){
    String aDevolver = "";
    int longitudCromosoma = _cromosomas.length;
    for (int i =0; i < longitudCromosoma; i++){
      aDevolver +=_cromosomas[i]+ " ";
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
    int[] cromosomas = ((IndividuoEnteroHaploide)individuo)._cromosomas;
    int[] cromosomasNuevoIndividuo =  Reproduccion.sobrecruzamientoEntero(this._cromosomas,cromosomas,tipoSobrecruzamiento);
    return (new IndividuoEnteroHaploide(cromosomasNuevoIndividuo,super._valorMaximoPosible,super._valorMinimoPosible));
  }

  /**
   * Fija la puntuacion de este individuo.
   * @param puntuacionResultados Porcentaje de parecido a los resultados finales.
   */
  public void setPuntuacion(double puntuacionResultados){
    _puntuacionResultados = puntuacionResultados;
  }

  /**
   * Devuelve la puntuacion de este individuo.
   */
  public double getPuntuacion(){
    return _puntuacionResultados;
  }
}
