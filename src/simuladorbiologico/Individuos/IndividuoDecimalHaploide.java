package simuladorbiologico.Individuos;

import simuladorbiologico.Individuo;
import simuladorbiologico.Reproduccion;
import simuladorbiologico.Simulador;

/**
 * Individuo cuyos alelos de la unica tira de cromosomas que posee pueden tomar valores decimales
 */
public class IndividuoDecimalHaploide extends Individuo {


  public double[] _cromosomas;


  public IndividuoDecimalHaploide(int tamanyoCromosoma,double valorMaximoPosible, double valorMinimoPosible){
    _cromosomas = new double[tamanyoCromosoma];
    super._valorMaximoPosible = valorMaximoPosible;
    super. _valorMinimoPosible = valorMinimoPosible;
    super.inmutable = false;
  }

  /**
   * Crea un individuo cuya informacion genetica es la recibida
   * @param cromosomas Cromosomas que tendra el nuevo individuo
   */
  public IndividuoDecimalHaploide( double[] cromosomas, double valorMaximoPosible, double valorMinimoPosible){
    _cromosomas = cromosomas;
    super._valorMaximoPosible = valorMaximoPosible;
    super. _valorMinimoPosible = valorMinimoPosible;
    super.inmutable = false;
  }

  /**
   * Incializa el cromosoma con numeros comprendidos entre el valor Maximo posible y el valor minimo posible
   * @param numero de bytes que tiene el cromosoma
   */
  public void inicializar(){
    for (int i =0; i < _cromosomas.length ; i++){
      if (Math.random() > 0.5){
	_cromosomas[i] = Math.random() * super._valorMaximoPosible;
      }else{
	_cromosomas[i] = Math.random()* super._valorMinimoPosible;
      }
    }
  }


  /**
   * Muta con una cierta probabilidad cada gen del cromosoma
   * @param probabiliad De mutar
   */
  public void mutar(double probabilidad){
    if (!inmutable){
      for (int i =0; i < _cromosomas.length ; i++){
	if (Math.random()*100 < probabilidad){
	  if (Math.random() > 0.5){
	    _cromosomas[i] = Math.random() * super._valorMaximoPosible;
	  }else{
	    _cromosomas[i] = Math.random()* super._valorMinimoPosible;
	  }
	}
      }
    }else{
      inmutable = false;  //es inmutable solo durante una generacion
    }
  }

  /**
   * Genera un nuevo individuo "hijo" resultado de cruzar el actual con el recibido por parametros
   * @param individuo Individuo con el que se va a cruzar
   * @return Devuelbe el individuo hijo de los dos que se han cruzado
   */
  public Individuo cruzar (Individuo individuo,String tipoSobrecruzamiento) throws Exception{
    IndividuoDecimalHaploide pareja = (IndividuoDecimalHaploide)individuo;
    /*el individuo haploide cruza su gameto (que es igual que el, haploide) con el del otro*/
    double[] cromosomasNuevoIndividuo = Reproduccion.sobrecruzamientoReal(_cromosomas,pareja._cromosomas,tipoSobrecruzamiento);
    return (new IndividuoDecimalHaploide(cromosomasNuevoIndividuo,super._valorMaximoPosible,super._valorMinimoPosible));
  }



  /**
   * Devuelve un String con su composicion
   */
  public String toString(){
    String aDevolver="";
    for (int i = 0; i < _cromosomas.length; i++){
      aDevolver += _cromosomas[i]+" ";
    }
    aDevolver+= "\n";
    return aDevolver;
  }
}
