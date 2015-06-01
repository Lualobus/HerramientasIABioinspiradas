package simuladorbiologico;
import java.util.Comparator;
import simuladorbiologico.Individuos.*;
/**
 * Especifica los metodos que debe implementar cualquier individuo concreto, todo
 * individuo tiene una valoracion
 */

public abstract class Individuo implements Comparator,Comparable{

  public double _valoracion;  /**valoracion de cada individuo en funcion de la seleccion Natural*/
  public static double _valorMaximoPosible;  /**valor maximo que puede tener un cromosoma*/
  public static double _valorMinimoPosible;  /**valor minimo posible que puede tener un cromosoma*/
  public boolean inmutable = false;  /** determina si el individuo puede o no sufrir mutacion*/

  /** Crea un individuo sin inicializar sus atributos*/
  public Individuo(){}

  /**
   * Inicializa aleatoriamente el cromosoma del individuo
   */
  public abstract void inicializar();

  /**
   * Puede producir una mutacion en alguna parte del cromosoma del individuo
   * @param probabilidad Probabilidad de mutar cada gen en concreto del cromosoma
   */
  public abstract void mutar(double probabilidad);

  /**
   * Devuelve un String con la representacion del individuo (lista para mostrarse)
   */
  public abstract String toString();

  /**
   * Se cruza el individuo actual con el recibido por parametros
   * @param individuo Individuo con el que se desea cruzar
   * @param tipoSobrecruzamiento String la manera en la que se hara el cruce
   * @return Devuelbe el individuo "hijo" resultado del cruce
   * @exception Si la manera de hacer el sobrecruzamiento no esta soportada
   */
  public abstract Individuo cruzar( Individuo individuo, String tipoSobrecruzamiento) throws Exception;

  /**
   * Es un metodo de tipo Factoy Method, en el se instancia los individuos concretos en funcion del parametro recibido
   * @param tipoIndividuo String Nombre del individuo a instanciar
   * @param tamanyoCromosoma int tamanyo del cromosoma del individuo a instanciar
   * @param valorMaximoPosible double Valor maximo que puede tomar cada gen del cromosoma
   * @param valorMinimoPosible double Valor minimo que puede tomar cada gen del cromosoma
   * @return Individuo concreto instanciado
   */
  public static Individuo instanciarIndividuo(String tipoIndividuo, int tamanyoCromosoma, double valorMaximoPosible, double valorMinimoPosible){
    Individuo aDevolver=null;
    //------------>>>>> AQUI AnyADIR LOS NUEVOS TIPOS DE INDIVIDUOS
    if (tipoIndividuo.equalsIgnoreCase("Binario Haploide")){
      aDevolver = new IndividuoBinarioHaploide(tamanyoCromosoma,valorMaximoPosible,valorMinimoPosible);
    }else if (tipoIndividuo.equalsIgnoreCase("Binario Diploide")){
      aDevolver = new IndividuoBinarioDiploide(tamanyoCromosoma,valorMaximoPosible,valorMinimoPosible);
    }else if (tipoIndividuo.equalsIgnoreCase("Entero Haploide")){
      aDevolver = new IndividuoEnteroHaploide(tamanyoCromosoma,valorMaximoPosible,valorMinimoPosible);
    }else if (tipoIndividuo.equalsIgnoreCase("Entero Diploide")){
      aDevolver = new IndividuoEnteroDiploide(tamanyoCromosoma,valorMaximoPosible,valorMinimoPosible);
    }else if (tipoIndividuo.equalsIgnoreCase("Positivo Haploide")){
      aDevolver = new IndividuoPositivoHaploide(tamanyoCromosoma,valorMaximoPosible,valorMinimoPosible);
    }else if (tipoIndividuo.equalsIgnoreCase("Positivo Diploide")){
      aDevolver = new IndividuoPositivoDiploide(tamanyoCromosoma,valorMaximoPosible,valorMinimoPosible);
    }else if (tipoIndividuo.equalsIgnoreCase("Decimal Haploide")){
      aDevolver = new IndividuoDecimalHaploide(tamanyoCromosoma,valorMaximoPosible,valorMinimoPosible);
    }else{
      System.err.println("Se ha seleccionado un individuo del cual no se dispone implementacion");
    }
    return aDevolver;
  }



//____________________________METODOS PARA COMPARAR INDIVIDUOS__________________

  /**
   * Compara dos individuos en funcion de su evaluacion
   * @param otroIndividuo Object individuo con el que se va a comparar
   * @return int -1 si el recibido por los parametros es de peor valoracion, +1 si es de mejor valoracion, y cero si son =
   */
  public int compareTo(Object otroIndividuo){
    int aDevolver=0;
    double valoracionOtroIndividuo = ((Individuo)otroIndividuo)._valoracion;
    if (this._valoracion < valoracionOtroIndividuo){
      aDevolver = 1;
    }else if (this._valoracion > valoracionOtroIndividuo){
      aDevolver = -1;
    }
    return aDevolver;
  }

  /**
   * Compara dos individuos recibidos por parametros
   * @param individuo1
   * @param individuo2
   * @return 1 si individiuo1 > individuo2, -1 si individuo2 > individuo1, 0 si son iguales
   */
  public int compare(Object individuo1, Object individuo2){
    int aDevolver=0;
    double valoracionIndividuo1 = ((Individuo)individuo1)._valoracion;
    double valoracionIndividuo2 = ((Individuo)individuo2)._valoracion;
    if (valoracionIndividuo1 < valoracionIndividuo2){
      aDevolver = 1;
    }else   if (valoracionIndividuo1 > valoracionIndividuo2){
      aDevolver = -1;
    }
    return aDevolver;
  }


  /**
   * Compara si el objeto recibido es igual que el de la clase
   * @param otro
   * @return True si son iguales
   */
  public boolean equals(Object otro){
    boolean aDevolver = false;
    try{
    if (((Individuo)otro)._valoracion == this._valoracion){
      aDevolver = false;
    }
    }catch (Exception e){}
    return aDevolver;

  }

}
