package RNA.InterfazGrafica;

import java.util.StringTokenizer;
import fam.utilidades.*;

public class FicheroPatrones {

  String nombreFichero;
  double[][] entradas;
  double[][] salidas;

  /**
   * Lee el fichero de los patrones indicado
   * @param fichero String Nombre del fichero de patrones
   * @throws Exception Si el fichero de los patrones no esta bien formado
   */
  public FicheroPatrones(String fichero) throws Exception{
    nombreFichero = fichero;
    leer();
  }

  /**
   * Devuelve las entradas leeidas del fichero de patrones
   * @return double[][] Por cada entrada de la matriz se corresponde con un patron y sus correspondientes entradas
   */
  public double[][] getEntradas(){
   return entradas;
  }

  /** Devuelve las salidas leidas del fichero de patrones
   * @return doubles[][] Por cada entrada de la matriz se corresponde con un patron y  sus correspondientes salidas
   */
  public double[][] getSalidas(){
    return salidas;
  }

  /**
   * Lee el fichero de patrones, y carga las entradas y salidas
   * @throws Exception Si el fichero de patrones no esta bien formado
   */
  private void leer() throws Exception{
    FicheroLectura lector = new FicheroLectura(nombreFichero);
    lector.abrir();
    String linea;

    do{
      linea = lector.leerLinea();
      if (linea == null) return;
    }while (linea.trim().length()==0 || linea.charAt(0)=='#');
    int numeroEntradas = Integer.parseInt(linea);

    do{
      linea = lector.leerLinea();
      if (linea == null) return;
    }while (linea.trim().length()==0 || linea.charAt(0)=='#');
    int numeroSalidas = Integer.parseInt(linea);

    do{
      linea = lector.leerLinea();
      if (linea == null) return;
    }while (linea.trim().length()==0 || linea.charAt(0)=='#');
    int numeroPatrones = Integer.parseInt(linea);


    entradas = new double[numeroPatrones][numeroEntradas];
    salidas = new double[numeroPatrones][numeroSalidas];

    StringTokenizer st;

    for (int i  = 0; i < numeroPatrones; i++){

      //leemos las entadas del patron actual
      do{
	linea = lector.leerLinea();
	if (linea == null) return;
      }while (linea.trim().length()==0 || linea.charAt(0)=='#');


      st = new StringTokenizer(linea);
      for (int j = 0; j < numeroEntradas; j++){
	entradas[i][j] = Double.parseDouble(st.nextToken());
      }


      //leemos las salidas del patron actual
      do{
	linea = lector.leerLinea();
	if (linea == null) return;
      }while (linea.trim().length()==0 || linea.charAt(0)=='#');


      st = new StringTokenizer(linea);
      for (int j = 0; j < numeroSalidas; j++){
	salidas[i][j] = Double.parseDouble(st.nextToken());
      }
    }
    lector.cerrar();
  }
}
