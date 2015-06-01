package simuladorbiologico.Individuos;
import simuladorbiologico.*;

/**
 * Individuo cuyos alelos del cromosoma pueden tomar solamente valores binarios,
 * y que esta compuesto de dos tiras de cromosomas (al igual que los humanos)
 */
public class IndividuoBinarioDiploide extends Individuo{

  public byte[][] _cromosomas;

  public IndividuoBinarioDiploide (int tamanyoCromosoma,double valorMaximoPosible, double valorMinimoPosible){
      _cromosomas = new byte[2][tamanyoCromosoma];
      super._valorMaximoPosible = valorMaximoPosible;
      super. _valorMinimoPosible = valorMinimoPosible;
      //super._valorMaximoPosible = 1;
      //super. _valorMinimoPosible = 0;
      super.inmutable = false;
  }


   /**
   * Constructor, instancia un individuo, con los cromosomas pasados por parametros
   * @param cromosomas La informacion genetica del individuo
   */
  public IndividuoBinarioDiploide(byte[][] cromosomas, double valorMaximoPosible, double valorMinimoPosible ){
    _cromosomas = cromosomas;
    super._valorMaximoPosible = valorMaximoPosible;
    super. _valorMinimoPosible = valorMinimoPosible;
    //super._valorMaximoPosible = 1;
    //super. _valorMinimoPosible = 0;
  }


  /** Constructor, insancia el individuo diploide con los dos cadenas de informacion recibidas
   * @param cromosomas1 Primera cadena de cromosomas que conforman la diploidad
   * @param cromosomas2 Segunda cadena de cromosomas que conforman la diploidad
   */
  public IndividuoBinarioDiploide(byte[] cromosomas1, byte[] cromosomas2){
    _cromosomas = new byte[2][cromosomas1.length];
    System.arraycopy(cromosomas1,0,_cromosomas[0],0,cromosomas1.length);
    System.arraycopy(cromosomas2,0,_cromosomas[1],0,cromosomas2.length);
  }

  /**
   * Incializa el cromosoma con bits aleatorios
   * @param numero de bytes que tiene el cromosoma
   */
  public void inicializar(){
    for (int j=0; j < 2; j++){
      for (int i =0; i < _cromosomas[0].length ; i++){
	if (Math.random() < 0.5){
	  _cromosomas[j][i] = (byte)0;
	}else{
	  _cromosomas[j][i] = (byte)1;
	}
      }
    }
  }

  /**
   * Muta con una cierta probabilidad cada gen del cromosoma
   * @param probabiliad De mutarGen
   */
  public void mutar(double probabilidad){
    if (!inmutable){
      for (int j=0; j < 2; j++){
	for (int i =0; i < _cromosomas[0].length ; i++){
	  if (Math.random()*100 < probabilidad){
	    if (_cromosomas[j][i] == 0){
	      _cromosomas[j][i] = (byte)1;
	    }else{
	      _cromosomas[j][i] = (byte)0;
	    }
	  }
	}
      }
    }else{
      inmutable = false; //es inmutable solo durante una generacion
    }
  }

  /**
   * Devuelve un string con su composicion
   */
  public String toString(){
    String aDevolver ="";
    for (int i = 0; i < 2; i++){
      for (int j = 0; j < _cromosomas[0].length; j++){
	aDevolver+=_cromosomas[i][j]+" ";
      }
      aDevolver+="\n";
    }
    return aDevolver;
  }

  /**
   * Genera un nuevo individuo "hijo" resultado de cruzar el actual con el recibido por parametros
   * @param individuo Individuo con el que se va a cruzar
   * @return Devuelbe el individuo hijo de los dos que se han cruzado
   */
  public Individuo cruzar (Individuo individuo,String tipoSobrecruzamiento) throws Exception{
    IndividuoBinarioDiploide pareja = (IndividuoBinarioDiploide)individuo;
    /*el individuo diploide pasa a generar un gameto haploide, para conseguir el gameto
      haploide debo por sobrecruzamiento entre los dos pares de cromosomas uno solo*/
     byte[] gameto1 = Reproduccion.sobrecruzamientoBinario(_cromosomas[0],_cromosomas[1],tipoSobrecruzamiento);
     byte[] gameto2 = Reproduccion.sobrecruzamientoBinario(pareja._cromosomas[0],pareja._cromosomas[1],tipoSobrecruzamiento);
     return (new IndividuoBinarioDiploide(gameto1,gameto2));

  }

}
