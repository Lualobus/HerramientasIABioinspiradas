 package TempleSimulado.Problemas.Prueba;
 import TempleSimulado.Estado;
import TempleSimulado.Problema;

public class EstadoPrueba implements Estado {
    /**
    * @return Estado Devuelve un estado siguiente posible al actual: devuelve uno de los posibles vecinos
     */
    public Estado obtenerUnSiguiente() {
       return new EstadoPrueba();
    }

    /**
      * @return Devuelve informacion textual sobre el estado que sea compresnsible en el contexto del problema
      */
    public String descripcionTextualEstado(Problema problema) {
       String aDevolver = "";
       return aDevolver;
    }
 }
 
