package simuladorbiologico.InterfazGrafica;

import java.io.File;
import java.io.IOException;
import fam.utilidades.*;

/**
 * Crea problemas que usan este simualador desde un proyecto externo a el
 */
public class CreadorProblemasExternos {



  /**
   * Crea el problema con el nombre indicado y con la ruta indicada
   * @param nombreProblema String Nombre del problema
   * @param pathProblemas String Path donde se va a colocar el problema
   * @throws IOException Si no se ha podido crear el problema
   */
  public CreadorProblemasExternos (String nombreProblema, String pathProblemas) throws IOException{

    //creamos el directorio de los problemas sino estaba creado
    File fichero = new File(pathProblemas+"/"+nombreProblema);
    fichero.mkdirs();


    //escribimos el fichero correspondiente al problema
    FicheroEscritura fichProblema = new FicheroEscritura(pathProblemas+"/"+nombreProblema+"/Problema"+nombreProblema+".java");
    fichProblema.abrir();
    fichProblema.print(CodigoBase.getCodigoProblema(nombreProblema));
    fichProblema.cerrar();

    //escribimos el fichero correspondiente a la poblacion
    FicheroEscritura fichPoblacion = new FicheroEscritura(pathProblemas+"/"+nombreProblema+"/Poblacion"+nombreProblema+".java");
    fichPoblacion.abrir();
    fichPoblacion.print(CodigoBase.getCodigoPoblacion(nombreProblema));
    fichPoblacion.cerrar();

    //escribimos el fichero correspondiente al Make
    FicheroEscritura fichMake = new FicheroEscritura(pathProblemas+"/"+nombreProblema+"/Make.bat");
    String codigoMake = "javac  -classpath "+ "\""+System.getProperty("user.dir")+ "/HerramientasIA.jar\" *.java \n pause";
    fichMake.abrir();
    fichMake.print(codigoMake);
    fichMake.cerrar();

    javax.swing.JOptionPane.showMessageDialog(null,"Problema creado en "+ pathProblemas+"/"+nombreProblema+ " ya puede ser editado y compilado sobre el esqueleto proporcionado");
  }
}
