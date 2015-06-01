package TempleSimulado;

/**
 * Define la interfaz de al menos los metodos que debe tener cualquier estado de un problema
 */
public interface Estado {

    /** @return Estado Devuelve un estado siguiente posible al actual: devuelve uno de los posibles vecinos    */
    public Estado obtenerUnSiguiente();


    /**@return Devuelve informacion textual sobre el estado que sea compresnsible en el contexto del problema    */
    public String descripcionTextualEstado(Problema problema);


}
