package TempleSimulado.InterfaceGrafica;

import java.awt.*;
import java.awt.event.*;
import java.lang.management.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import TempleSimulado.*;
import com.borland.jbcl.layout.*;
import com.sun.management.OperatingSystemMXBean;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.windows.WindowsLookAndFeel;
import com.borland.plaf.borland.BorlandLookAndFeel;
import fam.utilidades.*;


public class InterfazGrafica extends JFrame {

  /** Metodo main */
  public static void main(String args[]) {
    new InterfazGrafica();
  }

  private Problema problema; //problema que esta seleccionado
  private String rutaProblemas = System.getProperty("user.dir") + "/classes/TempleSimulado/Problemas"; //ruta donde esta el problema implementado o que vamos a crear

  MiJPanel panelPrincipal = new MiJPanel(null);
  private XYLayout xYLayout1 = new XYLayout();
  private JLabel jLabelAceptacionesMax = new JLabel();
  private JLabel jLabeliteracionesEtapa = new JLabel();
  private JLabel jLabelFactorEnfriamiento = new JLabel();
  private JLabel jLabelFactorCalentamiento = new JLabel();
  private JLabel jLabelTemperaturalFinal = new JLabel();
  private JLabel jLabelEtapasMax = new JLabel();
  private JButton jButtonIniciar = new JButton();
  private JLabel jLabel7 = new JLabel();
  private JComboBox jComboBoxProblema = new JComboBox();
  private JLabel jLabel8 = new JLabel();
  private JComboBox jComboBoxTipoSalidaResultados = new JComboBox();
  private JButton jButtonCrearProblema = new JButton();
  private JButton jButtonAyuda = new JButton();
  private JPanel panelGrafica = new JPanel();
  private PaneLayout paneLayout1 = new PaneLayout();
  private JButton jButtonGrafica = new JButton();
  private JLabel jLabelTemperaturaInicial = new JLabel();
  private JPanel jPanelParametrosConfiguracion = new JPanel();
  private XYLayout xYLayout2 = new XYLayout();
  private TitledBorder titledBorder1 = new TitledBorder("Parametros Configuracion");
  private JPanel jPanelSeleccionProblema = new JPanel();
  private XYLayout xYLayout3 = new XYLayout();
  private TitledBorder titledBorder2 = new TitledBorder("Seleccion Problema");
  private JPanel jPanelVisualizarResultados = new JPanel();
  private TitledBorder titledBorder3 = new TitledBorder("Visualizar Resultados");
  private XYLayout xYLayout4 = new XYLayout();
  private JPanel jPanelParametrosFinalizacion = new JPanel();
  private XYLayout xYLayout5 = new XYLayout();
  private TitledBorder titledBorder4 = new TitledBorder("Parametros Finalizacion");
  private SpinnerNumberModel restriccionesTemperaturaInicial = new SpinnerNumberModel(1D, Double.MIN_VALUE, Double.MAX_VALUE, 1D);
  private SpinnerNumberModel restriccionesAceptacionesEtapa = new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1);
  private SpinnerNumberModel restriccionesIteracionesEtapa = new SpinnerNumberModel(new Long(1),new Long(1),new Long(Long.MAX_VALUE),new Long(1));
  private SpinnerNumberModel restriccionesFactorEnfriamiento = new SpinnerNumberModel(0.9D, 0D, 1D, 0.05D);
  private SpinnerNumberModel restriccionesCalentamiento = new SpinnerNumberModel(1.25D, 1D, 2D, 0.05D);
  private SpinnerNumberModel restriccionesTemperaturaFinal = new SpinnerNumberModel(1D, 0D, Double.MAX_VALUE, 1D);
  private SpinnerNumberModel restriccionesEtapasMax = new SpinnerNumberModel(new Long(1),new Long(1),new Long(Long.MAX_VALUE),new Long(1));
  private SpinnerNumberModel restriccionesFactorIncrementoiteraciones = new SpinnerNumberModel(1.5D, 1D, Double.MAX_VALUE, 1D);
  private JSpinner jTemperaturaInicial = new JSpinner(restriccionesTemperaturaInicial);
  private JSpinner jAceptacionesEtapa = new JSpinner(restriccionesAceptacionesEtapa);
  private JSpinner jIteracionesEtapa = new JSpinner(restriccionesIteracionesEtapa);
  private JSpinner jFactorEnfriamiento = new JSpinner(restriccionesFactorEnfriamiento);
  private JSpinner jFactorCalentamiento = new JSpinner(restriccionesCalentamiento);
  private JSpinner jTemperaturaFinal = new JSpinner(restriccionesTemperaturaFinal);
  private JSpinner jEtapasMax = new JSpinner(restriccionesEtapasMax);
  private JSpinner jFactorIncrementoiteraciones = new JSpinner(restriccionesFactorIncrementoiteraciones);
  private JTextFieldDroppable jTextFieldRutaProblemas = new JTextFieldDroppable(rutaProblemas);
  private JLabel jLabelPathProblemas = new JLabel();
  private JButton jButtonBuscar = new JButton();
  private boolean borrandoProblemas = false;
  private JLabel jLabel2 = new JLabel();

  public InterfazGrafica() {
    try {
      jbInit();
      //cambiarLoockFeel();
      setVisible(true);
      panelPrincipal.cambiarImagenFondo("skins/skinTempleSimulado.png");
      pack();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setContentPane(panelPrincipal);
    this.setResizable(false);
    setTitle("Temple Simulado (Simulated Annealign)");
    this.addMouseListener(new InterfazGrafica_this_mouseAdapter(this));
    this.getContentPane().setLayout(xYLayout1);
    jLabelAceptacionesMax.setToolTipText("Cambios de estados  maximos permitidos en cada etapa");
    jLabelAceptacionesMax.setText("Aceptaciones etapa");
    xYLayout1.setWidth(651);
    xYLayout1.setHeight(450);
    jLabel7.setText("Seleccione Problema");
    jLabel8.setText("Tipo Salida Resultados");
    panelGrafica.setLayout(paneLayout1);
    jButtonGrafica.setBorder(null);
    jButtonGrafica.setToolTipText("Grafica");
    jButtonGrafica.setBorderPainted(false);
    jButtonGrafica.setBackground(new Color(237,236,236));
    jButtonCrearProblema.setToolTipText("Crear un nuevo problema");
    jButtonCrearProblema.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	botonCrearProblema_actionPerformed(e);
      }
    });
    jButtonAyuda.setToolTipText("Ayuda sobre el problema");
    jButtonAyuda.addActionListener(new InterfazGrafica_jButton3_actionAdapter(this));
    jComboBoxTipoSalidaResultados.addItem("Grafica");
    jComboBoxTipoSalidaResultados.addItem("Terminal");
    panelGrafica.setBorder(BorderFactory.createLoweredBevelBorder());
    panelGrafica.addMouseMotionListener(new MouseMotionListener() {
      public void mouseMoved(MouseEvent evento) {
	ratonMovioSobreGrafica(evento);
      }

      public void mouseDragged(MouseEvent evento) {}
    });
    jButtonBuscar.setToolTipText("Path carpeta donde estan los problemas implementados");
    jButtonBuscar.setIcon(new ImageIcon("recursos/buscar.png"));
    jButtonBuscar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	jButtonBuscar_actionPerformed(e);
      }
    });
    jPanelParametrosFinalizacion.setOpaque(false);
    jLabelPathProblemas.setText("Path problemas");
    jTextFieldRutaProblemas.setBackground(new Color(237, 234, 232));
    jButtonIniciar.addActionListener(new InterfazGrafica_jButton1_actionAdapter(this));
    jComboBoxProblema.addActionListener(new InterfazGrafica_jComboBoxProblema_actionAdapter(this));
    jComboBoxProblema.setBackground(new Color(215, 224, 244));
    jButtonIniciar.setText("Iniciar");
    jLabelEtapasMax.setText("Etapas Max");
    jLabelTemperaturalFinal.setText("Temperatura Final");
    jLabelFactorCalentamiento.setToolTipText("Por cuanto se va a multiplicar la temperatura al cambiar de una etapa " + "mas fria a otra mas caliente");
    jLabelFactorCalentamiento.setText("Factor Calentamiento");
    jLabelFactorEnfriamiento.setToolTipText("Por cuanto se multiplica la temperatura al cambiar de una etapa a " + "otra de menor temperatura");
    jLabelFactorEnfriamiento.setText("Factor Enfiamiento");
    jButtonCrearProblema.setIcon(new ImageIcon("recursos/mas.gif"));
    jButtonAyuda.setIcon(new ImageIcon("recursos/ayuda.png"));
    jButtonGrafica.setIcon(new ImageIcon("recursos/tonecurve.gif"));
    jLabeliteracionesEtapa.setToolTipText("Valor inicial de las iteraciones maximas que se permiten en cada " + "etapa, va variando dinamicamente segun un factor");
    jLabelTemperaturaInicial.setText("Temperatura Inicial");
    jPanelParametrosConfiguracion.setLayout(xYLayout2);
    jPanelParametrosConfiguracion.setBorder(titledBorder1);
    jPanelParametrosConfiguracion.setOpaque(false);
    jPanelSeleccionProblema.setLayout(xYLayout3);
    jPanelSeleccionProblema.setBorder(titledBorder2);
    jPanelSeleccionProblema.setOpaque(false);
    jPanelVisualizarResultados.setBorder(titledBorder3);
    jPanelVisualizarResultados.setOpaque(false);
    jPanelVisualizarResultados.setLayout(xYLayout4);
    jPanelParametrosFinalizacion.setLayout(xYLayout5);
    jPanelParametrosFinalizacion.setBorder(titledBorder4);
    jLabel2.setText("Factor Inc Iteraciones");
    jPanelVisualizarResultados.add(jLabel8, new XYConstraints(6, 7, -1, -1));
    jPanelVisualizarResultados.add(jComboBoxTipoSalidaResultados, new XYConstraints(145, 1, 145, -1));
    panelGrafica.add(jButtonGrafica, new PaneConstraints("jButtonGrafica", "jButtonGrafica", PaneConstraints.ROOT, 0.5F));
    jPanelParametrosFinalizacion.add(jLabelTemperaturalFinal, new XYConstraints(7, 17, -1, -1));
    jPanelParametrosFinalizacion.add(jLabelEtapasMax, new XYConstraints(7, 54, -1, -1));
    this.getContentPane().add(jButtonIniciar, new XYConstraints(75, 406, -1, -1));
    jPanelSeleccionProblema.add(jLabel7, new XYConstraints(14, 27, -1, -1));
    jPanelSeleccionProblema.add(jComboBoxProblema, new XYConstraints(14, 48, 227, 21));
    jPanelSeleccionProblema.add(jButtonCrearProblema, new XYConstraints(266, 39, 31, 30));
    jPanelSeleccionProblema.add(jButtonAyuda, new XYConstraints(307, 38, 38, 31));
    jPanelSeleccionProblema.add(jButtonBuscar, new XYConstraints(383, 0, 28, 23));
    this.getContentPane().add(jPanelVisualizarResultados, new XYConstraints(216, 112, 424, 330));
    jPanelSeleccionProblema.add(jTextFieldRutaProblemas, new XYConstraints(110, 1, 267, -1));
    jPanelSeleccionProblema.add(jLabelPathProblemas, new XYConstraints(13, 6, 94, -1));
    this.getContentPane().add(jPanelSeleccionProblema, new XYConstraints(216, 6, 424, 101));
    jPanelParametrosConfiguracion.add(jLabelTemperaturaInicial, new XYConstraints(7, 16, 121, -1));
    jPanelParametrosConfiguracion.add(jTemperaturaInicial, new XYConstraints(132, 10, 54, -1));
    jPanelParametrosConfiguracion.add(jAceptacionesEtapa, new XYConstraints(132, 42, 54, -1));
    jPanelParametrosConfiguracion.add(jLabelAceptacionesMax, new XYConstraints(7, 48, 119, -1));
    jPanelParametrosConfiguracion.add(jLabeliteracionesEtapa, new XYConstraints(7, 83, 121, -1));
    jPanelParametrosConfiguracion.add(jIteracionesEtapa, new XYConstraints(132, 79, 54, -1));
    jPanelParametrosConfiguracion.add(jFactorEnfriamiento, new XYConstraints(132, 114, 54, -1));
    jPanelParametrosConfiguracion.add(jLabelFactorEnfriamiento, new XYConstraints(7, 119, 123, -1));
    jPanelParametrosConfiguracion.add(jLabelFactorCalentamiento, new XYConstraints(7, 155, 123, -1));
    jPanelParametrosConfiguracion.add(jFactorCalentamiento, new XYConstraints(132, 151, 54, -1));
    jPanelParametrosConfiguracion.add(jFactorIncrementoiteraciones, new XYConstraints(132, 192, 54, -1));
    jPanelParametrosConfiguracion.add(jLabel2, new XYConstraints(7, 188, 129, 24));
    jPanelParametrosFinalizacion.add(jTemperaturaFinal, new XYConstraints(118, 12, 68, -1));
    jPanelParametrosFinalizacion.add(jEtapasMax, new XYConstraints(118, 51, 68, -1));
    this.getContentPane().add(jPanelParametrosFinalizacion, new XYConstraints(6, 260, 203, 117));
    this.getContentPane().add(jPanelParametrosConfiguracion, new XYConstraints(6, 6, 201, 249));
    jPanelVisualizarResultados.add(panelGrafica, new XYConstraints(5, 34, 407, 266));
    jLabeliteracionesEtapa.setText("Iteraciones etapa");
    //setDefaultCloseOperation(EXIT_ON_CLOSE);
    anyadirProblemasImplementados();
  }


  //____________________________________ESCUCHADORES DE EVENTOS_____________________________________________________


  /**
   * AnyADE LOS PROBLEMAS IMPLEMENTADOS
   * Anyade al combobox de problemas los problemas que estan implementados y compilados
   */
  public void anyadirProblemasImplementados() {
    try {
      java.io.File fich = new java.io.File(rutaProblemas);
      borrandoProblemas = true;
      jComboBoxProblema.removeAllItems();
      borrandoProblemas = false;
      String[] problemas = fich.list();

      for (int i = 0; i < problemas.length; i++) {
	//anyadimos los problemas que son carpetas (los ficheros sueltos no se miran)
	if ((new java.io.File(rutaProblemas + "/" + problemas[i])).isDirectory()) {
	  jComboBoxProblema.addItem(problemas[i]);
	}
      }
    } catch (Exception e) {
      System.out.println("Se ha producido un fallo al intentar acceder a los problemas implementados: " + e.getMessage());
    }
  }


  /**
   *  EJECUTAR SIMULACION
   *  Cuando se pulsa el boton de iniciar se lanza la ejecucion
   */
  public void jButtonIniciar_actionPerformed(ActionEvent e) {

    Estado mejorEstado;
    try {
      double temperaturaInicial = ((Double) jTemperaturaInicial.getValue()).doubleValue();
      double temperaturaFinal = ((Double) jTemperaturaFinal.getValue()).doubleValue();
      long iteracionesMaxEtapa = ((Long) jIteracionesEtapa.getValue()).longValue();
      long etapasMax =  ((Long)jEtapasMax.getValue()).longValue();
      int aceptacionesMaxEtapa = ((Integer) jAceptacionesEtapa.getValue()).intValue();
      double factorEnfriamiento = ((Double) jFactorEnfriamiento.getValue()).doubleValue();
      double factorCalentamiento = ((Double) jFactorCalentamiento.getValue()).doubleValue();
      double factorIncrementoIteracionexMaxEtapa = ((Double) jFactorIncrementoiteraciones.getValue()).doubleValue();
      Estado estadoInicial = problema.estadoInicial();

      TempleSimulado templeSimulado = new TempleSimulado(problema);

      if (jComboBoxTipoSalidaResultados.getSelectedItem().equals("Grafica")) {
	Grafica grafica = new Grafica(templeSimulado, panelGrafica, etapasMax, temperaturaInicial);
	grafica.arrancarGrafica();
	cambiarEstadoParametrosNoActualizables(false);
	mejorEstado = templeSimulado.templadoSimulado(temperaturaInicial, temperaturaFinal, etapasMax, iteracionesMaxEtapa, aceptacionesMaxEtapa, factorEnfriamiento, factorCalentamiento, factorIncrementoIteracionexMaxEtapa, estadoInicial);
	JOptionPane.showMessageDialog(this, "Solucion: \n" + mejorEstado.descripcionTextualEstado(problema));
	cambiarEstadoParametrosNoActualizables(true);
	grafica.parar();
      } else {
	cambiarEstadoParametrosNoActualizables(false);
	mejorEstado = templeSimulado.templadoSimulado(temperaturaInicial, temperaturaFinal, etapasMax, iteracionesMaxEtapa, aceptacionesMaxEtapa, factorEnfriamiento, factorCalentamiento, factorIncrementoIteracionexMaxEtapa, estadoInicial);
	System.out.println("Solucion: \n" + mejorEstado.descripcionTextualEstado(problema));
	cambiarEstadoParametrosNoActualizables(true);

      }
    } catch (Exception error) {
      error.printStackTrace();
      cambiarEstadoParametrosNoActualizables(true);
      JOptionPane.showMessageDialog(this, "Alguno/s de los parametros es incorrecto " + error.getMessage());
    }
  }

  /** Devuelve un string con los resultados de la simulacion para que puedan ser mostrados */
  /*private String getEstadisticasSimulacion(Particula particulaMejor,Enjambre enjambre, Problema problema) {
    int numParticulas = enjambre.getNumParticulas();
     String linea =
    "> Valoracion de la mejor particula: " + particulaMejor.getValoracion() +  "\n"+
    "> Interpretacion de la particula : " + problema.interpretracionParticula(particulaMejor)+"\n"+
    "> Valoracion total del enjambre: " + enjambre.getValoracionTotalEnjambre() + "\n" +
    "> Valoracion particula media del enjambre: " + (enjambre.getValoracionTotalEnjambre()/numParticulas) + "\n" +
    "> Nº de iteraciones: " + enjambre.getiteracionesRealizadas() + "\n" +
    "> Nº total de particulas evaluadas:" + (numParticulas * enjambre.getiteracionesRealizadas());
    return linea;
    return "";
     }*/



  /**
   * INSTANCIACIoN DEL PROBLEMA SELECCIONADO
   * Cuando seleccionamos un problema
   */
  public void jComboBoxProblema_actionPerformed(ActionEvent e) {
    if (borrandoProblemas) {
      return;
    }
    String problemaSeleccionado = (((JComboBox) e.getSource()).getSelectedItem().toString());

    try {

      //es un problema interno (no necesista ser cargada la clase pues ya se carga con el proyecto)
      if (rutaProblemas.equalsIgnoreCase(System.getProperty("user.dir") + "/classes/TempleSimulado/Problemas")) { //es un problema del propio simulador
	//instanciamos el problema seleccionado
	this.problema = Problema.instanciarProblema("TempleSimulado.Problemas." + problemaSeleccionado + ".Problema" + problemaSeleccionado);
	//es un problema externo (necesita cargarse con un classLoader la clase externa)
      } else {
	//cargamos las clases
	URL listaURLs[] = {new java.io.File(rutaProblemas + "/" + problemaSeleccionado + "/").toURL()};
	ClassLoader cargador = new URLClassLoader(listaURLs);
	//las instanciamos
	Class claseProblema = cargador.loadClass("Problema" + problemaSeleccionado);
	problema = ((Problema) claseProblema.newInstance());
      }

      jEtapasMax.setValue(new Long(problema.valorDefectoEtapasMax()));
      jFactorCalentamiento.setValue(new Double(problema.valorDefectoFactorCalentamiento()));
      jFactorEnfriamiento.setValue(new Double(problema.valorDefectoFactorEnfriamiento()));
      jFactorIncrementoiteraciones.setValue(new Double(problema.valorDefectoFactorIncrementoIteraccionesMaxEtapa()));
      jIteracionesEtapa.setValue(new Long(problema.valorDefectoIteraccionesMaxEtapa()));
      jAceptacionesEtapa.setValue(new Integer(problema.valorDefectoAceptacionesMaxEtapa()));
      jTemperaturaInicial.setValue(new Double(problema.valorDefectoTemperaturaInicial()));
      jTemperaturaFinal.setValue(new Double(problema.valorDefectoTemperaturaFinal()));


    } catch (Exception error) {
      jComboBoxProblema.removeItem(problemaSeleccionado);
      System.out.println("No se puede instanciar el problema " + problemaSeleccionado + ", seguramente porque necesite recibir arguemtnos el constructor del problema o porque no ha sido compilado");
    }
  }


  /**
   * CREAR UN NUEVO PROBLEMA A SOLUCIONAR
   *  Evento del boton que permite crear un nuevo problema y anyadirlo a los problemas implementados
   */
  public void botonCrearProblema_actionPerformed(ActionEvent e) {
    try {
      String nombreProblema;
      if (jTextFieldRutaProblemas.getText().equalsIgnoreCase(System.getProperty("user.dir") + "/classes/TempleSimulado/Problemas")) {
	do {
	  nombreProblema = javax.swing.JOptionPane.showInputDialog(null, "Introduce el nombre del problema que se creara dentro del propio proyecto");
	} while (nombreProblema.length() == 0); new CreadorProblemasInternos(nombreProblema, this); //creamos los ficheros y los mostramos para poder editarlos y compilarlos
      } else {
	do {
	  nombreProblema = javax.swing.JOptionPane.showInputDialog(null, "Introduce el nombre del problema que se creara en: " + jTextFieldRutaProblemas.getText());
	} while (nombreProblema.length() == 0); new CreadorProblemasExternos(nombreProblema, jTextFieldRutaProblemas.getText());
      }
    } catch (Exception error) {
      JOptionPane.showMessageDialog(this, "NO se ha podido crear el problema");
    }
  }

  /**
   * BUSCA LOS PROBLEMAS QUE SE HAN IMPLEMENTADO EN LA RUTA INDICADA
   */
  public void jButtonBuscar_actionPerformed(ActionEvent e) {
    rutaProblemas = jTextFieldRutaProblemas.getText();
    anyadirProblemasImplementados();
  }


  /** Cuando se pulsa el boton ayuda */
  public void jButton3_actionPerformed(ActionEvent e) {
    if (problema != null) {
      JOptionPane.showMessageDialog(this, problema.ayuda(),"Informacion sobre el problema seleccionado",JOptionPane.INFORMATION_MESSAGE);
    }
  }

  /** Cambia el loock and feel de la ventana */
  public void cambiarLoockFeel() {
    try {
      String lookSO = UIManager.getSystemLookAndFeelClassName();
      String lookJava = UIManager.getCrossPlatformLookAndFeelClassName();
      Plastic3DLookAndFeel plastic = new Plastic3DLookAndFeel();
      String lookPlastic = plastic.getName();
      WindowsLookAndFeel windows = new WindowsLookAndFeel();
      String lookWindows = windows.getName();
      String lookActual = UIManager.getLookAndFeel().getName();
      BorlandLookAndFeel borland = new BorlandLookAndFeel();
      String lookBorland = borland.getName();

      if ( lookActual.equalsIgnoreCase("metal")) {
	UIManager.setLookAndFeel(plastic);
      }else if (lookActual.equalsIgnoreCase("windows")){
	UIManager.setLookAndFeel(lookJava);
      }else if (lookActual.equalsIgnoreCase(lookPlastic)){
	UIManager.setLookAndFeel(windows);
      }else if (lookActual.equalsIgnoreCase(lookWindows)){
	UIManager.setLookAndFeel(borland);
      }else{
	UIManager.setLookAndFeel(lookJava);
      }
      SwingUtilities.updateComponentTreeUI(this);
      repaint();
    }catch (Exception error){}
  }

  /** Menu contextual desplegable cuando se pulsa con el boton derecho del raton */
  public void this_mouseClicked(MouseEvent e) {
    if (e.getButton() == 3) {
      JPopupMenu menuContextual = new JPopupMenu();
      JMenuItem cambiarLoockFeel = new JMenuItem("Cambiar Loock & Feel");
      cambiarLoockFeel.addActionListener(new InterfaceGrafica_CambiarLoockFeel_actionAdapter(this));
      JMenuItem cambiarImagenFondo = new JMenuItem("Cambiar Imagen de Fondo");
      cambiarImagenFondo.addActionListener(new InterfaceGrafica_cambiarImagenFondo_actionAdapter(this));
      JMenuItem ejecutarGC = new JMenuItem("Ejecutar Recolector de Basura");
      ejecutarGC.addActionListener(new InterfaceGrafica_ejecutarGC_actionAdapter(this));
      JMenuItem mirarMemoria = new JMenuItem("Ver Informacion Memoria");
      mirarMemoria.addActionListener(new InterfaceGrafica_verInformacionMemoria_actionAdapter(this));
      menuContextual.setBorder(BorderFactory.createEtchedBorder());
      menuContextual.add(cambiarLoockFeel);
      menuContextual.add(cambiarImagenFondo);
      menuContextual.add(mirarMemoria);
      menuContextual.add(ejecutarGC);
      menuContextual.show(this, e.getX(), e.getY());
    }
  }

  /**
   * Informacion que se muestra en un Tooltip cuando se mueve el raton sobre la grafica para indicar
   * la posicion real del raton sobre la grafica
   */
  private void ratonMovioSobreGrafica(MouseEvent evento) {
    try {
      int alturaPanelGrafica = panelGrafica.getHeight();
      int anchuraPanelGrafica = panelGrafica.getWidth();
      int x = evento.getX() - 20;
      int y = evento.getY() - alturaPanelGrafica + 20;
      y = -y;
      double valoracionObtener = ((Double) jTemperaturaInicial.getValue()).doubleValue();
      double valorMaxGrafica = valoracionObtener;
      long iteracionesMax = ((Long) jEtapasMax.getValue()).longValue();
      double graphX = Math.round((x * iteracionesMax) / (anchuraPanelGrafica - 40));
      double graphY = Math.round((y * valorMaxGrafica) / (alturaPanelGrafica - 40));
      panelGrafica.setToolTipText(" Etapas:" + Math.round(graphX) + " Temperatura:" + graphY);
    } catch (Exception e) {}
  }


  /**
   * Permite activar o desactivar los parametros no reconfigurables de la interface grafica durante la ejecucion
   * @param activar Si esta true se activaran , si False se desactivaran
   */
  public void cambiarEstadoParametrosNoActualizables(boolean activar) {
    jIteracionesEtapa.setEnabled(activar);
    jFactorEnfriamiento.setEnabled(activar);
    jEtapasMax.setEnabled(activar);
    jFactorIncrementoiteraciones.setEnabled(activar);
    jTemperaturaInicial.setEnabled(activar);
    jAceptacionesEtapa.setEnabled(activar);
    jTemperaturaFinal.setEnabled(activar);
    jFactorCalentamiento.setEnabled(activar);
    jButtonIniciar.setEnabled(activar);
  }


}


/*----  CLASES ESCUCHADORAS DE LOS EVENTOS PRODUCIDOS EN LA INTERFAZ ------ */
class InterfazGrafica_this_mouseAdapter extends MouseAdapter {
  private InterfazGrafica adaptee;
  InterfazGrafica_this_mouseAdapter(InterfazGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.this_mouseClicked(e);
  }
}

class InterfazGrafica_jButton3_actionAdapter implements ActionListener {
  private InterfazGrafica adaptee;
  InterfazGrafica_jButton3_actionAdapter(InterfazGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButton3_actionPerformed(e);
  }
}

class InterfazGrafica_jComboBoxProblema_actionAdapter implements ActionListener {
  private InterfazGrafica adaptee;
  InterfazGrafica_jComboBoxProblema_actionAdapter(InterfazGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jComboBoxProblema_actionPerformed(e);
  }
}

class InterfazGrafica_jButton1_actionAdapter implements ActionListener {
  private InterfazGrafica adaptee;
  InterfazGrafica_jButton1_actionAdapter(InterfazGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonIniciar_actionPerformed(e);
  }
}

/*--CLASES ESCUCHADORAS DEL MENO CONTEXTUAL QUE SE DESPLIGA PULSANDO EN EL BOTON DERECHO DEL RATON--- */
class InterfaceGrafica_CambiarLoockFeel_actionAdapter implements ActionListener {
  private InterfazGrafica adaptee;
  InterfaceGrafica_CambiarLoockFeel_actionAdapter(InterfazGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.cambiarLoockFeel();
  }
}


class InterfaceGrafica_ejecutarGC_actionAdapter implements ActionListener {
  private InterfazGrafica adaptee;
  InterfaceGrafica_ejecutarGC_actionAdapter(InterfazGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    System.gc();
  }
}

class InterfaceGrafica_verInformacionMemoria_actionAdapter implements ActionListener {
  private InterfazGrafica adaptee;
  InterfaceGrafica_verInformacionMemoria_actionAdapter(InterfazGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    OperatingSystemMXBean mxbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    long memFisicaLibre = mxbean.getFreePhysicalMemorySize() / 1048576;
    long memFisicaTotal = mxbean.getTotalPhysicalMemorySize() / 1048576;
    long memFisicaUsada = memFisicaTotal - memFisicaLibre;
    long memVirtualTotal = mxbean.getTotalSwapSpaceSize() / 1048576;
    long memVirtualLibre = mxbean.getFreeSwapSpaceSize() / 1048576;
    long memVirtualUsada = memVirtualTotal - memVirtualLibre;
    long aplicacionMemoriaLibre = Runtime.getRuntime().freeMemory() / 1048576;
    long aplicacionMemoriaMax = Runtime.getRuntime().maxMemory() / 1048576;
    long aplicacionMemoriaUsada = aplicacionMemoriaMax - aplicacionMemoriaLibre;
    JOptionPane.showMessageDialog(adaptee, "...SISTEMA OPERATIVO...\nFisica usada: " + memFisicaUsada + "Mb/" + memFisicaTotal + "Mb\nVirtual usada: " + memVirtualUsada + "Mb/" + memVirtualTotal + "Mb\n\n...APLICACIoN...\nFisica usada: " + aplicacionMemoriaUsada + "Mb/" + aplicacionMemoriaMax + "Mb", "Informacion memoria", 1);
  }
}

class InterfaceGrafica_cambiarImagenFondo_actionAdapter implements ActionListener {
  private InterfazGrafica adaptee;
  InterfaceGrafica_cambiarImagenFondo_actionAdapter(InterfazGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    JFileChooser selectorImagen = new JFileChooser();
    selectorImagen.setFileFilter(new javax.swing.filechooser.FileFilter() {
      public boolean accept(java.io.File f) {
	if (f.isDirectory()) {
	  return true;
	}
	Utils utils = new Utils();
	String extension = utils.getExtension(f);
	if (extension != null) {
	  if (extension.equals(Utils.tiff) || extension.equals(Utils.tif) || extension.equals(Utils.gif) || extension.equals(Utils.jpeg) || extension.equals(Utils.jpg) || extension.equals(Utils.png)) {
	    return true;
	  } else {
	    return false;
	  }
	}
	return false;
      }

      public String getDescription() {
	return "Imagenes";
      }
    });
    int returnVal = selectorImagen.showOpenDialog(adaptee);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      try {
	adaptee.panelPrincipal.cambiarImagenFondo(selectorImagen.getSelectedFile().getPath());
	adaptee.repaint();
      } catch (Exception error) {}
    }
  }

  /** Clase interna usada para la seleccion de la foto de fondo, para filtrar los archivos a solo por imagenes */
  public class Utils {
    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";

    public String getExtension(java.io.File f) {
      String ext = null;
      String s = f.getName();
      int i = s.lastIndexOf('.');

      if (i > 0 && i < s.length() - 1) {
	ext = s.substring(i + 1).toLowerCase();
      }
      return ext;
    }
  }
}
