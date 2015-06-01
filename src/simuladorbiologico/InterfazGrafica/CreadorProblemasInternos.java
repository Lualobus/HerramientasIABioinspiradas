package simuladorbiologico.InterfazGrafica;

import java.awt.Color;
import java.io.File;
import javax.swing.*;
import fam.utilidades.*;

/**
 * Se encarga de generar los ficheros necesarios para crear un nuevo problema
 * @author not attributable
 * @version 1.0
 */
public class CreadorProblemasInternos {

  String nombreProblema;
  FicheroEscritura fichProblema, fichPoblacion;
  InterfaceGrafica gui;
  boolean ProblemaCompilado = false;
  boolean poblacionCompilado = false;
  boolean editorClasePoblacionMostrado = true;
  boolean editorClaseProblemaMostrado = false;

  /**
   * Constructor que se encarga de crear los ficheros necesarios para el problema concreto,
   * crea los ficheros y lanza un editor para que el usuario los pueda editar
   */

  public CreadorProblemasInternos(String nombreProblema, InterfaceGrafica gui){
    this.gui = gui;
    this.nombreProblema = nombreProblema;
    //creamos el directorio donde se guardara el problema
    File fichero = new File("./src/simuladorbiologico/Problemas/" +   nombreProblema );
    fichero.mkdir();
    //mostramos una ventana para cada clase, para poder editarlas y compilarlas
    boolean primeroCompilarProblema = false; //primero compilamos la poblacion
    new EditorProblemas(nombreProblema,primeroCompilarProblema,this);
  }


  /**
   * Compila  y guarda el archivo del Problema y muesta en una ventana los posibles errores
   * @param codigo String
   */
  public void compilarArchivoProblema(String codigo){
    try{
     //guardamos el fichero en su directorio correspondiente (en la carpeta src)
     fichProblema = new FicheroEscritura("./src/simuladorbiologico/Problemas/" + nombreProblema+"/Problema"+nombreProblema+".java");
     fichProblema.abrir();
     fichProblema.print("package simuladorbiologico.Problemas."+nombreProblema+"; \n"+codigo);
     fichProblema.cerrar();

     //compilamos el fichero y lo guardamos en el directorio relativo al problema (en la carpeta classes)
     String salidaJavac = JavacWrapper.compile(System.getProperty("user.dir")+"/src","simuladorbiologico/Problemas/" + nombreProblema +"/Problema" + nombreProblema + ".java", System.getProperty("user.dir")+"/classes");
     if (salidaJavac.length() == 0)  ProblemaCompilado = true;
     mostrarVentanaTexto(salidaJavac);
     if (!editorClasePoblacionMostrado){
       new EditorProblemas(nombreProblema,false,this);
       editorClasePoblacionMostrado=true;
     }
     if (poblacionCompilado && ProblemaCompilado)  gui.anyadirProblemasImplementados();
   }catch (Exception e){
     javax.swing.JOptionPane.showMessageDialog(null,"Se ha producido un error al compilar y guardar");
   }
 }

  /**
   * Compila y guarda el archivo de la poblacion y muestra en una ventana los posibles errores
   * @param codigo String
   */
  public void compilarArchivoPoblacion(String codigo){
    try {
      //guardamos el fichero en su directorio correspondiente (en la carpeta src)
      fichPoblacion = new FicheroEscritura("./src/simuladorbiologico/Problemas/" + nombreProblema+"/Poblacion"+nombreProblema+".java");
      fichPoblacion.abrir();
      fichPoblacion.print("package simuladorbiologico.Problemas."+nombreProblema+";\n"+codigo);  //escribimos en el fichero
      fichPoblacion.cerrar();

      //compilamos el fichero y lo guardamos en el directorio relativo al problema (en la carpeta classes)
      String salidaJavac = JavacWrapper.compile(System.getProperty("user.dir")+"/src","simuladorbiologico/Problemas/" + nombreProblema +"/Poblacion" + nombreProblema + ".java", System.getProperty("user.dir")+"/classes");
      if (salidaJavac.length() == 0) poblacionCompilado=true;
      mostrarVentanaTexto(salidaJavac);
      if (!editorClaseProblemaMostrado){
	new EditorProblemas(nombreProblema,true,this);
	editorClaseProblemaMostrado=true;
      }


      if (ProblemaCompilado && poblacionCompilado)  gui.anyadirProblemasImplementados();

    }catch (Exception ex) {
      javax.swing.JOptionPane.showMessageDialog(null,"Se ha producido un error al compilar y guardar");
    }
  }

  /* Muestra el texto en una ventana */
  private void mostrarVentanaTexto(String salidaJavac){
    //mostramos que se ha compilado sin errores
    if (salidaJavac.length()==0) {
      JOptionPane.showMessageDialog(null,"Guardado y compilado OK");
    //mostramos el error que indica el javac
    }else{
      JFrame ventana = new JFrame("");
      ventana.setTitle("Errores de compilacion");
      JScrollPane scrollbar = new JScrollPane();
      JEditorPane area = new JEditorPane();
      scrollbar.getViewport().add(area);
      ventana.getContentPane().add(scrollbar);
      area.setForeground(Color.RED);
      area.setText(salidaJavac);
      ventana.setVisible(true);
      ventana.pack();
    }
  }
}



