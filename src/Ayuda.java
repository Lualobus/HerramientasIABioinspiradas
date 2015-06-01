import java.io.IOException;

/**
 * Clase encargada de lanzar la ayuda en formato Html, sobre el navegador por
 * defecto del sistema
 * Bajo Unix, el navegador esta codificado para que sea 'netscape', que
 * debe estar en el PATH.
 * Bajo Windows, se lanzara el navegador por defecto, ya sea Netscape,
 * Internet Explorer, Neoplanet, o cualquier otro. El navegador por
 * defecto esta determinado por el Sistema Operativo.
 *
 * Este ejemplo muestra una clase simple y estatica para presentar una
 * direccion URL, o pagina HTML, en el navegador del Sistema.
 * Ejemplos de uso de la clase, y de la invocacion del metodo presentaURL()
 * son los siguientes:
 *   java819.presentaURL( "http://members.es.tripod.de/froufe" )
 *   java819.presentaURL( "file://c:\\docs\\index.html" )
 *   java819.presentaURL( "file:///home/users/afq/index.html" )
 * Es necesario incluir el tipo de pagina al que se quiere acceder, ya sea
 * "http://" o "file://"
 */
public class Ayuda {

  /** Identificacion de la plataforma Windows*/
  private static final String WIN_ID = "Windows";

  /**El navegador por defecto utilizado en Windows*/
  private static final String WIN_PATH = "rundll32";

  /**El Flag que hay que poner para presentar una direccion URL*/
  private static final String WIN_FLAG = "url.dll,FileProtocolHandler";

  /**El navegador por defecto en Unix*/
  private static final String UNIX_PATH = "netscape";

  /**Las opciones de la linea de comandos para Netscape*/
  private static final String UNIX_FLAG = "-remote openURL";


  /**
   * Presenta un fichero en el navegador del sistema. Si se quiere presentar
   * un fichero, hay que introducir el camino completo
   * @param url String Ruta completa del fichero que se quiere representar en el navegador
   */
  public static void presentaURL(String url) {
    boolean windows = sobrePlataformaWindows();
    String cmd = null;

    try {
      if (windows) {
	// cmd = 'rundll32 url.dll,FileProtocolHandler http://...'
	cmd = WIN_PATH + " " + WIN_FLAG + " " + url;
		Runtime.getRuntime().exec(cmd);
      }else{
	// En Unix, Netscape tiene que ejecutarse con el parametro
	// "-remote", por si esta ya lanzada otra copia; lo que se
	// hace es observar el valor de retorno, si devuelve 0, se
	// habra lanzado correctamente y en cualquier otro caso,
	// sera que no esta corriendo el navegador, asi que hay que
	// lanzarlo

	// cmd = 'netscape -remote openURL(http://java.sun.com)'
	cmd = UNIX_PATH + " " + UNIX_FLAG + "(" + url + ")";
	Process p = Runtime.getRuntime().exec(cmd);

	try {
	  // Esperamos al cosigo de salida, si es 0 indica que el
	  // comando ha funcionado; y en otro caso, es necesario
	  // arrancar directamente el navegador
	  int exitCode = p.waitFor();

	  // El comando ha fallado, asi que hay que lanzar el
	  // navegador
	  if (exitCode != 0) {
	    // cmd = 'netscape http://java.sun.com'
	    cmd = UNIX_PATH + " " + url;
	    p = Runtime.getRuntime().exec(cmd);
	  }
	}catch (InterruptedException e) {
	  System.err.println("Error lanzando el navegador, comando='" + cmd + "'");
	  e.printStackTrace();
	}
      }
    }catch (IOException e) {
      System.err.println("No se puede invocar al navegador, comando=" + cmd);
      e.printStackTrace();
    }
  }


  /**
   * Este metodo intenda saber si la aplicacion esta corriendo bajo
   * Windows, o cualquier otra plataforma, comprobando el valor de
   * la propiedad "os.name"
   * @return boolean True si se trata de la plataforma Windows, False en caso contrario
   */
  public static boolean sobrePlataformaWindows() {
    String os = System.getProperty("os.name");
    if (os != null && os.startsWith(WIN_ID)) {
      return (true);
    }else{
      return (false);
    }
  }
}


