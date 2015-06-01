package TempleSimulado.Problemas.Viajante;
import TempleSimulado.Estado;
import TempleSimulado.Problema;

/**
 * Estado usado para rerpesentar el problema del viajante, representa la secuencia
 * de ciudades a visitar, no solamente una ciudad en concreto, sino todo el trayecto completo
 */
public class EstadoViajante implements Estado {

  private int[] ordenCiudadesVisitar; /** Indica el orden de las ciudades a visitar, estando numeradas,  por ej: 3,5,2,1 indica que primero visitamos la ciudad 3, luego la 5, luego la 2 y finalmente la 1 */

  /**
   * Construcutor, recibe la lista de las ciudades que se van a visitar
   * @param ordenCiudadesVisitar int[]
   */
  public EstadoViajante(int[] ordenCiudadesVisitar) {
    this.ordenCiudadesVisitar = ordenCiudadesVisitar;
  }

  /**
   * El estado siguiente del actual va ser simplemente una permutacion entre la lista de las ciudades visitadas
   * @return Estado Devuelve un estado siguiente posible al actual: devuelve uno de los posibles vecinos
   */
  public Estado obtenerUnSiguiente() {

    //copiamos el array con la lista de ciudades a visitar del estado actual para no modificarlo
    int[] copiaOrdenCiudadesVisitar = new int[ordenCiudadesVisitar.length];
    System.arraycopy(ordenCiudadesVisitar, 0, copiaOrdenCiudadesVisitar, 0, ordenCiudadesVisitar.length);
    int indice1 = (int) (Math.random() * ordenCiudadesVisitar.length);
    int indice2 = (int) (Math.random() * ordenCiudadesVisitar.length);
    int aux = copiaOrdenCiudadesVisitar[indice1];
    copiaOrdenCiudadesVisitar[indice1] = copiaOrdenCiudadesVisitar[indice2];
    copiaOrdenCiudadesVisitar[indice2] = aux;
    return new EstadoViajante(copiaOrdenCiudadesVisitar);
  }


  /**
   * @return Devuelve informacion textual sobre el estado que sea compresnsible en el contexto del problema
   */
  public String descripcionTextualEstado(Problema problema) {
    String aDevolver = "";
    for (int i = 0; i < ordenCiudadesVisitar.length; i++) {
      aDevolver += "Vista la ciudad num " + ordenCiudadesVisitar[i] + "\n";
    }
    aDevolver += "Costo total: " + problema.funcionEvaluacionEnergia(this);
    return aDevolver;
  }

  /**
   * @return int[] Devuelve la lista con el orden de las ciudades a visitar, estando previamente las ciudades identificadas por un numero cada una de ellas
   */
  public int[] obtenerOrdenCiudadesVisitar() {
    return ordenCiudadesVisitar;
  }

}
