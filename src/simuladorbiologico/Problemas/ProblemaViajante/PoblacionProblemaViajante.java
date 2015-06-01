package simuladorbiologico.Problemas.ProblemaViajante;
import simuladorbiologico.*;

/** Poblacion basada en reproduccion de torneos*/
public class PoblacionProblemaViajante  extends Poblacion{

    /**Creamos individuos diploides con codificacion binaria */
    public PoblacionProblemaViajante (Problema problema){
        super(problema);
    }

    /** @return Valor minimo que podra tomar cada gen de cada individuo de la poblacion */
    public double valorMinimoGenIndividuos(){
        return 0;
    }

    /** @return Valor maximo que podra tomar cada gen de cada individuo de la poblacion */
    public double valorMaximoGenIndividuos(){
        return 4;
    }


    // PUEDEN SER SOBRESCRITOS LOS SIGUIENTES MeTODOS (sino borrense)

    /** Se le da una valoracion a cada individuo para tener mas probabilidades de sobrevivir */
    public void aplicarSeleccionNatural(){
       for (int i = 0; i < _individuos.length; i++){
           _individuos[i]._valoracion = _contexto.evaluarIndividuo(_individuos[i]);
       }
    }

   /**
     * La poblacion se reproduce en funcion a las valoraciones de cada individuo; notar que cada individuo de la nueva
     * poblacion no ha sido evaluado todavia ni ha sufrido todavia procesos de seleccion
     * @param presionSelectiva int  a numero mas grande mas presion se hace, cuanto mas pequenyo menos presion
     * @param elitismo int Numero de individuos que pasan directamente a la siguiente generacion por ser los mejores
     * @param tipoSobrecruzamiento String la forma en la que se realizara el sobrecruzamiento entre los individuos
     * @param mejorInmutable boolean Determina si el mejor individuo esta expuesto a mutacion o no
     * @throws Exception
     */
   public void reproducir(boolean isMaximizationProblem, int presionSelectiva,int elitismo, String tipoSobrecruzamiento,boolean mejorInmutable) throws Exception{
      Reproduccion.torneos(_individuos,isMaximizationProblem, presionSelectiva,elitismo,tipoSobrecruzamiento,mejorInmutable);
   }
}

