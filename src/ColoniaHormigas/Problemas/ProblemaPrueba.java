package ColoniaHormigas.Problemas;
import ColoniaHormigas.*;
 /**
  * IMplementacion del problema
  */
 public class ProblemaPrueba extends Problema{

   /**
    * Debe definirse para cada problema en concreto la matriz de costes
    * @return int[] Matriz de costes definiada para el problema concreto
    */
   public double[][] getCostesEntrePosiciones(){
       return null;
   }


   //_____________Metodos que pueden ser sobrescritos_______________________
   public int valorDefectoNumeroHormigas(){ return 4; }
   public long valorDefectoIteracionesMax(){ return  50; }
   public double valorDefectoCosteAObtener(){ return 0; }
   public double valorDefectoFactorEvaporacionLocal(){ return 0.1; }
   public double valorDefectoFactorEvaporacionGlobal(){ return 0.1; }
   public double valorDefectoCosteAproximadoTrayecto(){ return 100; }
   public double valorDefectoFactorExplotacionInicial(){ return 0.1; }
   public boolean valorDefectoIncrementoDinamicoFactorExploracion(){ return true; }



  /** Probando el problema */
   public static void main(String args[]){
     ProblemaPrueba problemaPrueba = new ProblemaPrueba();
     OptimizadorColoniaHormigas aco = new OptimizadorColoniaHormigas(problemaPrueba);
     int[] trayectoSolucion = aco.optimizacionSecuencial();
     for (int i = 0; i < trayectoSolucion.length; i++){
       System.out.println("Posicion visitada: " + trayectoSolucion[i]);
     }
   }

 }

