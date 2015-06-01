package OptimizadorEnjambreParticulas.Problemas;
import OptimizadorEnjambreParticulas.*;

 public class ProblemaAlejarse extends Problema{

   public double funcionEvaluacion(Particula particula){
     int totalX = 0;
     double[] posicion = particula.getPosicion();
     double[] velocidad = particula.getVelocidad();
     int numDimensiones = particula.getDimensiones();
     for (int i = 0; i < numDimensiones; i++){
       totalX+=posicion[i];
     }
     return totalX;
   }


   public String interpretracionParticula(Particula particula) {
     String interpretarcion = "La posicion de la particula es : ";
     double[] posiciones = particula.getPosicion();
     for (int i = 0; i < posiciones.length; i++) {
       interpretarcion += (int) posiciones[i] + " ";
     }
     return interpretarcion.trim();
   }

   /**
    * Para determinar si es un problema de maximizacion o minimizacion
    * @return True si es de maximizar y False si es de minimizar
    */
   public boolean isMaximizeProblem(){
     return true;
   }

   /* El valor por defecto de la puntuaciona obtener es de 1000 sino se sobrescribe */
  public double valorDefectoValorObtener(){ return 1000; }


   /** Main para probar el problema sin necisidad de interfaz grafica */
   public static void main(String[] args){
      Problema problemaAlejarse = new ProblemaAlejarse();                           //instanciamos el problema
      Particula particulaSolucion =  EjecutaPSO.lanzarPSO (problemaAlejarse);     //lanzamos el algoritmo
      double[] solucion = particulaSolucion.getPosicion();	              //obtenemos la solucion
   }
 }

