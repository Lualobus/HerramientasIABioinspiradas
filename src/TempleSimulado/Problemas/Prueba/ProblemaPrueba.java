package TempleSimulado.Problemas.Prueba;
import TempleSimulado.*;

 public class ProblemaPrueba extends Problema{

     public ProblemaPrueba(){}

      /**
       * Funcion heuristica que representa la codificacion del problema y que se intente minimizar,
       * en este caso se trata de ...
       * @param estado Estado que se desea evaluar
       * @return double Energia asociada al estado
       */
       public double funcionEvaluacionEnergia(Estado estado){
            return 0;
       }
       /**
        * @return Estado Estado desde el que se inicia la solucion del problema
        */
        public Estado estadoInicial(){
            return (new EstadoPrueba());
        }



         //______________Metodos que pueden ser sobrescritos para adaptarlos al problema en concreto__________
        /**
         * Valor por defecto con el que se resolvera el problema: 100
         * Temperatura desde la que se parte y se desea minimizar
         * @return double
         */
        public double valorDefectoTemperaturaInicial(){
            return 100;
        }

        /**
         * Valor por defecto con el que se resolvera el problema: 0
         * @return double Temperatura que se desea alcanzar
         */
        public double valorDefectoTemperaturaFinal(){
            return 0;
        }

        /**
         * Valor por defecto con el que se resolvera el problema: 1000
         * @return double Numero maximo de etapas a generar
         */
        public long valorDefectoEtapasMax(){
            return 100;
        }

        /**
         * Valor por defecto con el que se resolvera el problema: 100
         * @return double Iteracciones maximias permitidas en cada etapa inicialmente
                   (luego va variando segun el factor de incremento
         */
        public long valorDefectoIteraccionesMaxEtapa(){
            return 10;
        }

        /**
         * Valor por defecto con el que se resolvera el problema: 10
         * @return int Numero maximo de cambios de estados permitidos para una misma etapa
         */
        public int valorDefectoAceptacionesMaxEtapa(){
            return 5;
        }

        /**
         * Valor por defecto con el que se resolvera el problema: 0.9
         * @return double Factor por el que se va a multiplicar la temperatura en cada etapa que disminuya
         */
        public double valorDefectoFactorEnfriamiento(){
            return 0.9;
        }

        /**
         * Valor por defecto con el que se resolvera el problema: 1.30
         * @return double Factor por el que se va a multiplicar la temperatura en cada etapa que aumente
         */
        public double valorDefectoFactorCalentamiento(){
            return 1.30;
        }

        /**
         * Valor por defecto con el que se resolvera el problema: 1.50
         * @return double Factor por el que se va a multiplicar el numero de iteracciones max por etapa al finalizar la etapa
         */
        public double valorDefectoFactorIncrementoIteraccionesMaxEtapa(){
            return 1.50;
        }

       /**
        * Valor por defecto de la ayuda del problema: "No se ha indicado ningun tipo de informacion para este problema"
        * @return String Informacion sobre el problema
        */
       public String ayuda(){
           return "No se ha indicado ningun tipo de informacion para este problema";
       }

        /** Prueba del problema (no es necesario tener el main, se puede borrar) */
        public static void main (String args[] ){
            ProblemaPrueba problemaPrueba = new ProblemaPrueba();
            TempleSimulado temple = new TempleSimulado(problemaPrueba);
            temple.templadoSimulado();
        }
 }

