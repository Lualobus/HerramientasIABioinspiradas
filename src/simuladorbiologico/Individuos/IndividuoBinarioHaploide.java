package simuladorbiologico.Individuos;
import simuladorbiologico.*;

/**
 * Individuo cuyos alelos del cromosoma pueden tomar solamente valores binarios,
 * y que esta compuesto de una sola tira cromosomas (no como los humanos)
 */
public class IndividuoBinarioHaploide extends Individuo{


    public byte[] _cromosomas;


    public IndividuoBinarioHaploide(int tamanyoCromosoma,double valorMaximoPosible, double valorMinimoPosible){
	 _cromosomas = new byte[tamanyoCromosoma];
	 super._valorMaximoPosible = valorMaximoPosible;
	 super. _valorMinimoPosible = valorMinimoPosible;
	 super.inmutable = false;
    }

    /**
     * Crea un individuo cuya informacion genetica es la recibida
     * @param cromosomas Cromosomas que tendra el nuevo individuo
     */
    public IndividuoBinarioHaploide( byte[] cromosomas, double valorMaximoPosible, double valorMinimoPosible){
      _cromosomas = cromosomas;
      super._valorMaximoPosible = valorMaximoPosible;
      super. _valorMinimoPosible = valorMinimoPosible;
    }

    /**
     * Incializa el cromosoma con bits aleatorios
     * @param numero de bytes que tiene el cromosoma
     */
    public void inicializar(){
      for (int i =0; i < _cromosomas.length ; i++){
	if (Math.random() < 0.5){
	  _cromosomas[i] = (byte)0;
	}else{
	  _cromosomas[i] = (byte)1;
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
	    if (_cromosomas[i] == 0){
	      _cromosomas[i] = (byte)1;
	    }else{
	      _cromosomas[i] = (byte)0;
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
      IndividuoBinarioHaploide pareja = (IndividuoBinarioHaploide)individuo;

      /*el individuo haploide cruza su gameto (que es igual que el, haploide) con el del otro, los cromosomas hay que pasarlos como int[]*/
      byte[] cromosomasNuevoIndividuo = Reproduccion.sobrecruzamientoBinario(_cromosomas,pareja._cromosomas,tipoSobrecruzamiento);
      return (new IndividuoBinarioHaploide(cromosomasNuevoIndividuo,super._valorMaximoPosible,super._valorMinimoPosible));
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
