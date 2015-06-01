package RNA.InterfazGrafica;

import java.awt.*;
import java.awt.event.*;
import java.lang.management.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;

import RNA.*;
import com.borland.jbcl.layout.*;
import com.borland.plaf.borland.*;
import com.jgoodies.looks.plastic.*;
import com.jgoodies.looks.windows.*;
import com.sun.management.OperatingSystemMXBean;

import fam.utilidades.MiJPanel;


/**
 * Interface Grafica de la Red de Neuronas, para ello tiene un objeto red que sobre el invoca los metodos
 * necesarios. Esta totalmente desacoplado, ya que la clase RNA.Red, no depende en absoluto
 * de la interfaz grafica para funcionar
 */

public class InterfaceGrafica extends JFrame{

  MiJPanel panelPrincipal = new MiJPanel(null);
  private JTextField textFieldConfiguracionRed = new JTextField();
  private JLabel jLabelFuncionActivacion = new JLabel();
  private JLabel jLabelConfiguracionRed = new JLabel();
  private JComboBox comboBoxFuncionActivacion = new JComboBox();
  private JPanel panelConfiguracionRed = new JPanel();
  private JPanel panelEntrenamiento = new JPanel();
  private JPanel panelGrafica = new JPanel();
  private TitledBorder titledBorder1 = new TitledBorder("Modo parada Entrenamiento");
  private TitledBorder titledBorder2 = new TitledBorder("Entrenamiento");
  private TitledBorder titledBorder4 = new TitledBorder("Red");
  private JLabel jLabelValidacion = new JLabel();
  private JLabel jLabelEntrenamiento = new JLabel();
  private JRadioButton jRadioButtonIteraccionesMax = new JRadioButton();
  private JRadioButton jRadioButtonHastaNoSobreentrenar = new JRadioButton();
  private JPanel panelModoParada = new JPanel();
  private JLabel jLabelIteraccionesMax = new JLabel();
  private JLabel jLabelErrorMaximo = new JLabel();
  private JTextField textFieldIteraccionesMax = new JTextField();
  private JTextField textFieldErrorMax = new JTextField();
  private JLabel jLabelAlgoritmoAprendizaje = new JLabel();
  private JComboBox comboBoxAlgAprendizaje = new JComboBox();
  private JButton botonValidar = new JButton();
  private JButton botonEntrenar = new JButton();
  private XYLayout xYLayout1 = new XYLayout();
  private XYLayout xYLayout2 = new XYLayout();
  private XYLayout xYLayout3 = new XYLayout();
  private XYLayout xYLayout4 = new XYLayout();
  private JLabel jLabelIteraccionesMostrarResultados = new JLabel();
  private JTextField textFieldIteraccionesMostrarResultados = new JTextField();
  private JButton imagenGrafica = new JButton();
  private JButton botonFichEntrenamiento = new JButton();
  private JButton botonFichValidacion = new JButton();
  private JTextFieldDroppable textFieldnombreFichEntrenamiento = new JTextFieldDroppable();
  private JTextFieldDroppable textFieldnombreFicheroValidacion = new JTextFieldDroppable();

  private Red red;
  private int[] configuracionRed;
  private double[][] entradasEntrenamiento;
  private double[][] salidasEntrenamiento;
  private double[][] entradasValidacion;
  private double[][] salidasValidacion;
  private String funcionActivacion = "noLineal";
  private long iteraccionesMax = 1000;
  private double errorPermitidoMax = 1;
  private String algoritmoAprendizaje = "backpropagation";
  private String modoParadaAprendizaje = "iteraccionesYErrorMax";
  private int itereccionesMostrarResultados = 1;
  private PaneLayout paneLayout1 = new PaneLayout();
  private JPanel panelObtenerSalidas = new JPanel();
  private JTextField jTextFieldEntradas = new JTextField();
  private XYLayout xYLayout5 = new XYLayout();
  private JLabel jLabelPonerEntrada = new JLabel();
  private JButton jButtonCalcularSalidas = new JButton();
  private TitledBorder titledBorder6 = new TitledBorder("Obtener Salidas");
  private JPanel panelTipoSalida = new JPanel();
  private XYLayout xYLayout6 = new XYLayout();
  private TitledBorder titledBorder7 = new TitledBorder("Tipo Salida Resultados Entrenamiento");
  private JComboBox jComboBoxTipoSalResultadosEntr = new JComboBox();
  private JLabel jLabelTipoSalidaResultados = new JLabel();
  private JPanel jPanelVisualizarEntrenamiento = new JPanel();
  private XYLayout xYLayout7 = new XYLayout();
  private TitledBorder titledBorder8 = new TitledBorder("Visualizar Entrenamiento");

  /** Lanza la interfaz grafica en pantalla */
  public static void main (String args[]){
    try{
      new InterfaceGrafica();
    }catch (Exception error){
      error.printStackTrace();
    }
  }
  /** Constructor que crea la interfaz grafica */
  public InterfaceGrafica() {
    try {
      jbInit();
      //cambiarLoockFeel();
      //setDefaultCloseOperation(EXIT_ON_CLOSE);
      setVisible(true);
      pack();
    }catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Crea la interfaz grafica */
  private void jbInit() throws Exception {
    this.setContentPane(panelPrincipal);
    textFieldConfiguracionRed.setToolTipText("Neuronas en cada capa separada por guiones");
    textFieldConfiguracionRed.setText("1-3-3-1");
    this.getContentPane().setLayout(xYLayout1);
    jLabelFuncionActivacion.setText("Funcion Activacion");
    panelEntrenamiento.setBackground(new Color(236, 233, 216));
    panelEntrenamiento.setBorder(titledBorder2);
    panelEntrenamiento.setOpaque(false);
    jLabelValidacion.setText("Path Validacion");
    jLabelEntrenamiento.setText("Path Entrenamiento");
    jRadioButtonIteraccionesMax.setBackground(new Color(236, 233, 216));
    jRadioButtonIteraccionesMax.setOpaque(false);
    jRadioButtonIteraccionesMax.setToolTipText("Entrena hasta minimizar el error indicado o hasta alcanzar el numero " + "de iteracciones maximimas permitidas");
    jRadioButtonIteraccionesMax.setSelected(true);
    jRadioButtonIteraccionesMax.setText("IteraccionesMax y ErrorMax");
    jRadioButtonIteraccionesMax.addActionListener(new InterfaceGrafica_jRadioButton1_actionAdapter(this));
    jRadioButtonHastaNoSobreentrenar.setBackground(new Color(236, 233, 216));
    jRadioButtonHastaNoSobreentrenar.setOpaque(false);
    jRadioButtonHastaNoSobreentrenar.setToolTipText("Enterna al mismo tiempo que valida, cuando el error de validacion " + "aumenta se para el entrenamiento");
    jRadioButtonHastaNoSobreentrenar.setText("Hasta no sobreentrenar");
    jRadioButtonHastaNoSobreentrenar.addActionListener(new InterfaceGrafica_jRadioButton2_actionAdapter(this));
    panelModoParada.setBackground(new Color(236, 233, 216));
    panelModoParada.setBorder(titledBorder1);
    panelModoParada.setOpaque(false);
    panelModoParada.setLayout(xYLayout4);
    this.setForeground(SystemColor.control);
    this.setResizable(false);
    this.setTitle("Simulador Red de Neuronas Artificiales (RNA)");
    this.addMouseListener(new InterfaceGrafica_this_mouseAdapter(this));
    jLabelIteraccionesMax.setText("Iteraciones Max");
    jLabelErrorMaximo.setText("Error Maximo");
    jLabelAlgoritmoAprendizaje.setText("Algoritmo aprendizaje");
    botonValidar.setToolTipText("Lanzar la validacion u obtencion de resultados");
    botonValidar.setText("Validar");
    botonValidar.setEnabled(false);
    botonValidar.addActionListener(new InterfaceGrafica_botonValidar_actionAdapter(this));
    botonEntrenar.setToolTipText("Lanzar entrenamiento");
    botonEntrenar.setText("Entrenar");
    botonEntrenar.addActionListener(new InterfaceGrafica_botonEntrenar_actionAdapter(this));
    comboBoxFuncionActivacion.addActionListener(new InterfaceGrafica_comboBoxFuncionActivacion_actionAdapter(this));
    comboBoxAlgAprendizaje.addActionListener(new InterfaceGrafica_comboBoxAlgAprendizaje_actionAdapter(this));
    panelConfiguracionRed.setBackground(new Color(236, 233, 216));
    panelConfiguracionRed.setBorder(titledBorder4);
    panelConfiguracionRed.setOpaque(false);
    comboBoxAlgAprendizaje.setBackground(UIManager.getColor("info"));
    panelGrafica.setBorder(BorderFactory.createLoweredBevelBorder());
    panelGrafica.setToolTipText("Grafica del error cometido");
    panelGrafica.setLayout(paneLayout1);
    panelGrafica.addMouseMotionListener(new MouseMotionListener(){
      public void mouseMoved(MouseEvent evento){
	ratonMovioSobreGrafica(evento);
      }
      public void mouseDragged(MouseEvent evento){}
    });
    panelEntrenamiento.setLayout(xYLayout2);
    panelConfiguracionRed.setLayout(xYLayout3);
    comboBoxAlgAprendizaje.addItem(Red.BACKPROPAGATION);
    comboBoxAlgAprendizaje.addItem(Red.GENETICO);
    comboBoxAlgAprendizaje.addItem(Red.ENJAMBRE);
    comboBoxFuncionActivacion.addItem("noLineal");
    comboBoxFuncionActivacion.addItem("escalon");
    xYLayout1.setWidth(819);
    xYLayout1.setHeight(515);
    jLabelIteraccionesMostrarResultados.setText("Iteracciones mostrar resultados");
    textFieldIteraccionesMostrarResultados.setText("1");
    jLabelConfiguracionRed.setText("Configuracion Red");
    comboBoxFuncionActivacion.setBackground(UIManager.getColor("info"));
    comboBoxFuncionActivacion.setToolTipText("Determina como se activaran cada una de las neuronas de la red");
    imagenGrafica.setIcon(null);
    imagenGrafica.setBackground(new Color(237, 236, 236));
    imagenGrafica.setBorder(null);
    imagenGrafica.setRequestFocusEnabled(false);
    imagenGrafica.setToolTipText("Grafica el error cometido en media por cada neurona de salida (es " + "el error real)");
    imagenGrafica.setVerifyInputWhenFocusTarget(false);
    imagenGrafica.setBorderPainted(false);
    imagenGrafica.setIcon(new ImageIcon("recursos/tonecurve.gif"));
    textFieldIteraccionesMax.setText("1000");
    textFieldErrorMax.setText("0");
    botonFichEntrenamiento.setText("...");
    botonFichEntrenamiento.addActionListener(new InterfaceGrafica_botonFichEntrenamiento_actionAdapter(this));
    botonFichValidacion.setText("...");
    botonFichValidacion.addActionListener(new InterfaceGrafica_botonFichValidacion_actionAdapter(this));
    panelObtenerSalidas.setLayout(xYLayout5);
    jLabelPonerEntrada.setText("Entradas");
    jButtonCalcularSalidas.setMargin(new Insets(2, 2, 2, 2));
    jButtonCalcularSalidas.setText("Calcular Salidas");
    jButtonCalcularSalidas.addActionListener(new InterfaceGrafica_jButtonCalcularSalidas_actionAdapter(this));
    panelObtenerSalidas.setBackground(new Color(236, 233, 216));
    panelObtenerSalidas.setBorder(titledBorder6);
    panelObtenerSalidas.setOpaque(false);
    panelTipoSalida.setBackground(new Color(236, 233, 216));
    panelTipoSalida.setBorder(titledBorder7);
    panelTipoSalida.setOpaque(false);
    panelTipoSalida.setLayout(xYLayout6);
    jLabelTipoSalidaResultados.setText("Tipo Salida Resultados Entrenamiento");
    jPanelVisualizarEntrenamiento.setLayout(xYLayout7);
    jPanelVisualizarEntrenamiento.setBorder(titledBorder8);
    jPanelVisualizarEntrenamiento.setOpaque(false);
    panelEntrenamiento.add(botonValidar, new XYConstraints(173, 264, -1, -1));
    panelEntrenamiento.add(comboBoxAlgAprendizaje, new XYConstraints(163, 224, 145, -1));
    panelEntrenamiento.add(jLabelAlgoritmoAprendizaje, new XYConstraints(9, 233, 128, -1));
    panelEntrenamiento.add(panelModoParada, new XYConstraints(9, 64, 371, 144));
    panelModoParada.add(jLabelIteraccionesMax, new XYConstraints(17, 40, 116, 15));
    panelModoParada.add(jLabelErrorMaximo, new XYConstraints(17, 77, 83, 15));
    panelModoParada.add(textFieldIteraccionesMax, new XYConstraints(126, 40, 101, -1));
    panelModoParada.add(textFieldErrorMax, new XYConstraints(126, 72, 101, -1));
    panelModoParada.add(jRadioButtonHastaNoSobreentrenar, new XYConstraints(194, 2, 163, 25));
    panelModoParada.add(jRadioButtonIteraccionesMax, new XYConstraints(2, 2, 186, 26));
    panelEntrenamiento.add(botonEntrenar, new XYConstraints(69, 264, -1, -1));
    panelConfiguracionRed.add(textFieldConfiguracionRed, new XYConstraints(140, 8, 97, -1));
    panelConfiguracionRed.add(comboBoxFuncionActivacion, new XYConstraints(140, 37, 123, 20));
    panelConfiguracionRed.add(jLabelFuncionActivacion, new XYConstraints(9, 35, 118, 22));
    panelConfiguracionRed.add(jLabelConfiguracionRed, new XYConstraints(9, 14, 119, -1));
    this.getContentPane().add(panelEntrenamiento, new XYConstraints(4, 100, 391, 322));
    this.getContentPane().add(panelConfiguracionRed, new XYConstraints(4, 6, 391, 90));
    panelEntrenamiento.add(textFieldnombreFichEntrenamiento, new XYConstraints(140, 4, 183, -1));
    panelEntrenamiento.add(textFieldnombreFicheroValidacion, new XYConstraints(140, 29, 183, -1));
    panelEntrenamiento.add(botonFichEntrenamiento, new XYConstraints(333, 4, 35, 20));
    panelEntrenamiento.add(botonFichValidacion, new XYConstraints(333, 29, 35, 20));
    panelEntrenamiento.add(jLabelEntrenamiento, new XYConstraints(9, 10, 127, -1));
    panelEntrenamiento.add(jLabelValidacion, new XYConstraints(9, 35, 127, -1));
    this.getContentPane().add(jPanelVisualizarEntrenamiento, new XYConstraints(403, 100, 408, 407));
    panelTipoSalida.add(jLabelTipoSalidaResultados, new XYConstraints(11, 9, 185, 21));
    panelTipoSalida.add(jComboBoxTipoSalResultadosEntr, new XYConstraints(230, 8, 149, 22));
    this.getContentPane().add(panelObtenerSalidas, new XYConstraints(4, 425, 390, 83));
    this.getContentPane().add(panelTipoSalida, new XYConstraints(403, 6, 409, 90));
    panelGrafica.add(imagenGrafica, new PaneConstraints("imagenGrafica", "imagenGrafica", PaneConstraints.ROOT, 0.5F));
    jPanelVisualizarEntrenamiento.add(textFieldIteraccionesMostrarResultados, new XYConstraints(227, 342, 117, -1));
    jPanelVisualizarEntrenamiento.add(jLabelIteraccionesMostrarResultados, new XYConstraints(59, 348, -1, -1));
    jPanelVisualizarEntrenamiento.add(panelGrafica, new XYConstraints(1, 24, 396, 300));
    panelObtenerSalidas.add(jLabelPonerEntrada, new XYConstraints(12, 2, -1, -1));
    panelObtenerSalidas.add(jTextFieldEntradas, new XYConstraints(72, 0, 270, -1));
    panelObtenerSalidas.add(jButtonCalcularSalidas, new XYConstraints(134, 34, 121, 22));
  }


  public void jRadioButton2_actionPerformed(ActionEvent e) {
    comboBoxAlgAprendizaje.setSelectedItem(Red.BACKPROPAGATION);
    comboBoxAlgAprendizaje.setEnabled(false);
    jRadioButtonIteraccionesMax.setSelected(false);
    jRadioButtonHastaNoSobreentrenar.setSelected(true);
    modoParadaAprendizaje = "entrenarHastaNoSobreentrenar";
    textFieldErrorMax.setEnabled(false);
    textFieldIteraccionesMax.setEnabled(false);
    textFieldErrorMax.setBackground(Color.GRAY);
    textFieldIteraccionesMax.setBackground(Color.GRAY);
  }

  public void jRadioButton1_actionPerformed(ActionEvent e) {
    comboBoxAlgAprendizaje.setEnabled(true);
    jRadioButtonIteraccionesMax.setSelected(true);
    jRadioButtonHastaNoSobreentrenar.setSelected(false);
    modoParadaAprendizaje = "iteraccionesYErrorMax";
    textFieldErrorMax.setEnabled(true);
    textFieldErrorMax.setBackground(Color.WHITE);
    textFieldIteraccionesMax.setEnabled(true);
    textFieldIteraccionesMax.setBackground(Color.WHITE);
  }


  public void comboBoxFuncionActivacion_actionPerformed(ActionEvent e) {
    funcionActivacion = (((JComboBox) e.getSource()).getSelectedItem().toString());
  }

  public void comboBoxAlgAprendizaje_actionPerformed(ActionEvent e) {
    algoritmoAprendizaje = (((JComboBox) e.getSource()).getSelectedItem().toString());
    jComboBoxTipoSalResultadosEntr.removeAllItems();
    if (algoritmoAprendizaje.equalsIgnoreCase(Red.BACKPROPAGATION)){
      jComboBoxTipoSalResultadosEntr.addItem("Grafica");
      jComboBoxTipoSalResultadosEntr.addItem("Terminal");
      jRadioButtonHastaNoSobreentrenar.setEnabled(true);
    }else{
      jRadioButton1_actionPerformed(null);
      jRadioButtonHastaNoSobreentrenar.setEnabled(false);
      jComboBoxTipoSalResultadosEntr.addItem("Terminal");
    }
  }

  public void botonFichEntrenamiento_actionPerformed(ActionEvent e) {
    JFileChooser selectorImagen = new JFileChooser();
    int returnVal = selectorImagen.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      textFieldnombreFichEntrenamiento.setText(selectorImagen.getSelectedFile().getPath());
    }
  }

  public void botonFichValidacion_actionPerformed(ActionEvent e) {
    JFileChooser selectorImagen = new JFileChooser();
    int returnVal = selectorImagen.showOpenDialog(this);
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      textFieldnombreFicheroValidacion.setText(selectorImagen.getSelectedFile().getPath());
    }
  }


  /** Cuando se pulsa el boton de entrenar*/
  public synchronized void  botonEntrenar_actionPerformed(ActionEvent e) {
    try {

      itereccionesMostrarResultados = Integer.parseInt(textFieldIteraccionesMostrarResultados.getText());
      String config = textFieldConfiguracionRed.getText();
      StringTokenizer st = new StringTokenizer(config, "-");
      configuracionRed = new int[st.countTokens()];
      int i = 0;
      while (st.hasMoreTokens()) {
	configuracionRed[i++] = Integer.parseInt(st.nextToken());
      }

      FicheroPatrones fichPatronesEntrenamiento = new FicheroPatrones(textFieldnombreFichEntrenamiento.getText());
      entradasEntrenamiento = fichPatronesEntrenamiento.getEntradas();
      salidasEntrenamiento= fichPatronesEntrenamiento.getSalidas();

      Grafica _grafica = null;


      red = new Red(configuracionRed, funcionActivacion);
      if (jComboBoxTipoSalResultadosEntr.getSelectedItem().equals("Grafica")){
	red.sincronizarConGrafica = true;
	itereccionesMostrarResultados = 0;  //si la salida es de tipo grafica no queremos mostrar nada en el terminal
	_grafica = new Grafica(panelGrafica);
	_grafica.arrancarGrafica();
      }

      if (modoParadaAprendizaje.equalsIgnoreCase("iteraccionesYErrorMax")){
	errorPermitidoMax = Double.parseDouble(textFieldErrorMax.getText());
	iteraccionesMax = Integer.parseInt(textFieldIteraccionesMax.getText());
	red.entrenar(entradasEntrenamiento, salidasEntrenamiento, errorPermitidoMax, iteraccionesMax, itereccionesMostrarResultados, algoritmoAprendizaje);
      } else {
	FicheroPatrones fichPatronesValidacion = new FicheroPatrones(textFieldnombreFicheroValidacion.getText());
	entradasValidacion = fichPatronesValidacion.getEntradas();
	salidasValidacion = fichPatronesValidacion.getSalidas();
	red.entrenarHastaNoSobreentrenar(entradasEntrenamiento, salidasEntrenamiento, entradasValidacion, salidasValidacion, itereccionesMostrarResultados);
      }

      if (red.sincronizarConGrafica && _grafica!=null)  _grafica.parar();
      botonValidar.setEnabled(true);
    } catch (Exception err) {
      javax.swing.JOptionPane.showMessageDialog(this, "Alguno/s de los parametros es incorrecto. Asegurese que la configuracion de la red coinciden el numero de neuronas de entrada y de salida con las establecidas en los patrones.\n Error: " + err.getMessage());
      //err.printStackTrace();
    }
  }

  /** Que se hace cuando se pulsa en el boton de validar*/
  public void botonValidar_actionPerformed(ActionEvent e) {
    try{
      FicheroPatrones fichPatronesValidacion = new FicheroPatrones(textFieldnombreFicheroValidacion.getText());
      entradasValidacion = fichPatronesValidacion.getEntradas();
      salidasValidacion = fichPatronesValidacion.getSalidas();
      //double errorPorNeuronaSalida = red.calcularErrorMedioPorNeuronaSalida(entradasValidacion,salidasValidacion);
      //javax.swing.JOptionPane.showMessageDialog(this, "El error medio cometido por neurona de salida es  " + errorPorNeuronaSalida);

      double salidasReales[]=null;
      double errorTotalAcumulado=0;
      for (int i = 0; i < salidasValidacion.length; i++){
	salidasReales = red.validarRed(entradasValidacion[i]);
	for (int j = 0; j < salidasReales.length; j++){
	  errorTotalAcumulado+=Math.abs(salidasReales[j]-salidasValidacion[i][j]);
	}
      }
      javax.swing.JOptionPane.showMessageDialog(this, "El error medio cometido por neurona de salida es  " + errorTotalAcumulado/(salidasValidacion.length*salidasReales.length));

    }catch (Exception err){
      javax.swing.JOptionPane.showMessageDialog(this, "Alguno/s de los parametros es incorrecto " + err.getMessage());
      //err.printStackTrace();
    }
  }

  /**
   * Informacion que se muestra en un Tooltip cuando se mueve el raton sobre la grafica para indicar
   * la posicion real del raton sobre la grafica
   */
  private void ratonMovioSobreGrafica(MouseEvent evento) {
    int alturaPanelGrafica = panelGrafica.getHeight();
    int anchuraPanelGrafica = panelGrafica.getWidth();
    int x = evento.getX()-20;
    int y = evento.getY()-alturaPanelGrafica+20;
    y = -y;

    int graphX = (int)(x*Red.iteraccionesMax)/(anchuraPanelGrafica-40);
    double graphY = (y*Grafica.errorMaxObtenido)/(alturaPanelGrafica-40);
    panelGrafica.setToolTipText(" Iteracciones:"+Math.round(graphX)+" Error:"+graphY);
  }


   /** Menu contextual desplegable cuando se pulsa con el boton derecho del raton */
  public void this_mouseClicked(MouseEvent e) {
    if (e.getButton() == 3){
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
      menuContextual.show(this,e.getX(),e.getY());
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

  /** Calcula las salidas de la red para las entradas dadas */
  public void jButtonCalcularSalidas_actionPerformed(ActionEvent e) {

    try {
      String stringEntradas = jTextFieldEntradas.getText();
      StringTokenizer st = new StringTokenizer(stringEntradas);
      double[] entradas = new double[st.countTokens()];
      for (int i = 0; i < entradas.length; i++) {
	entradas[i] = Double.parseDouble(st.nextToken());
      }
      double[] salidas = red.validarRed(entradas);
      String stringSalidas ="";
      for (int i = 0; i < salidas.length; i++){
	stringSalidas+=salidas[i]+" ";
      }
      JOptionPane.showMessageDialog(this,stringSalidas,"Salidas",1);
    }catch (Exception ex) {}
  }
}

class InterfaceGrafica_jButtonCalcularSalidas_actionAdapter implements ActionListener {
  private InterfaceGrafica adaptee;
  InterfaceGrafica_jButtonCalcularSalidas_actionAdapter(InterfaceGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonCalcularSalidas_actionPerformed(e);
  }
}

class InterfaceGrafica_botonValidar_actionAdapter implements ActionListener {
  private InterfaceGrafica adaptee;
  InterfaceGrafica_botonValidar_actionAdapter(InterfaceGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.botonValidar_actionPerformed(e);
  }
}

class InterfaceGrafica_this_mouseAdapter extends MouseAdapter {
  private InterfaceGrafica adaptee;
  InterfaceGrafica_this_mouseAdapter(InterfaceGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseClicked(MouseEvent e) {
    adaptee.this_mouseClicked(e);
  }
}

class InterfaceGrafica_botonEntrenar_actionAdapter implements ActionListener {
  private InterfaceGrafica adaptee;
  InterfaceGrafica_botonEntrenar_actionAdapter(InterfaceGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.botonEntrenar_actionPerformed(e);
  }
}

class InterfaceGrafica_botonFichValidacion_actionAdapter implements ActionListener {
  private InterfaceGrafica adaptee;
  InterfaceGrafica_botonFichValidacion_actionAdapter(InterfaceGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.botonFichValidacion_actionPerformed(e);
  }
}

class InterfaceGrafica_botonFichEntrenamiento_actionAdapter implements ActionListener {
  private InterfaceGrafica adaptee;
  InterfaceGrafica_botonFichEntrenamiento_actionAdapter(InterfaceGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.botonFichEntrenamiento_actionPerformed(e);
  }
}

class InterfaceGrafica_comboBoxAlgAprendizaje_actionAdapter implements  ActionListener {
  private InterfaceGrafica adaptee;
  InterfaceGrafica_comboBoxAlgAprendizaje_actionAdapter(InterfaceGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.comboBoxAlgAprendizaje_actionPerformed(e);
  }
}

class InterfaceGrafica_comboBoxFuncionActivacion_actionAdapter implements ActionListener {
  private InterfaceGrafica adaptee;
  InterfaceGrafica_comboBoxFuncionActivacion_actionAdapter(InterfaceGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.comboBoxFuncionActivacion_actionPerformed(e);
  }
}

class InterfaceGrafica_jRadioButton1_actionAdapter implements ActionListener {
  private InterfaceGrafica adaptee;
  InterfaceGrafica_jRadioButton1_actionAdapter(InterfaceGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jRadioButton1_actionPerformed(e);
  }
}

class InterfaceGrafica_jRadioButton2_actionAdapter implements ActionListener {
  private InterfaceGrafica adaptee;
  InterfaceGrafica_jRadioButton2_actionAdapter(InterfaceGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jRadioButton2_actionPerformed(e);
  }
}


/*--CLASES ESCUCHADORAS DEL MENO CONTEXTUAL QUE SE DESPLIGA PULSANDO EN EL BOTON DERECHO DEL RATON--- */
class InterfaceGrafica_CambiarLoockFeel_actionAdapter implements ActionListener{
  private InterfaceGrafica adaptee;
   InterfaceGrafica_CambiarLoockFeel_actionAdapter(InterfaceGrafica adaptee) {
     this.adaptee = adaptee;
   }
   public void actionPerformed(ActionEvent e) {
     adaptee.cambiarLoockFeel();
  }
}


class InterfaceGrafica_ejecutarGC_actionAdapter implements ActionListener{
  private InterfaceGrafica adaptee;
  InterfaceGrafica_ejecutarGC_actionAdapter(InterfaceGrafica adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    System.gc();
  }
}

class InterfaceGrafica_verInformacionMemoria_actionAdapter implements ActionListener{
  private InterfaceGrafica adaptee;
  InterfaceGrafica_verInformacionMemoria_actionAdapter(InterfaceGrafica adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    OperatingSystemMXBean mxbean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    long memFisicaLibre = mxbean.getFreePhysicalMemorySize()/1048576;
    long memFisicaTotal = mxbean.getTotalPhysicalMemorySize()/1048576;
    long memFisicaUsada = memFisicaTotal - memFisicaLibre;
    long memVirtualTotal = mxbean.getTotalSwapSpaceSize()/1048576;
    long memVirtualLibre = mxbean.getFreeSwapSpaceSize()/1048576;
    long memVirtualUsada = memVirtualTotal - memVirtualLibre;
    long aplicacionMemoriaLibre = Runtime.getRuntime().freeMemory()/1048576;
    long aplicacionMemoriaMax = Runtime.getRuntime().maxMemory()/1048576;
    long aplicacionMemoriaUsada = aplicacionMemoriaMax - aplicacionMemoriaLibre;
    JOptionPane.showMessageDialog(adaptee,"...SISTEMA OPERATIVO...\nFisica usada: "+memFisicaUsada+"Mb/"+memFisicaTotal+"Mb\nVirtual usada: "+memVirtualUsada+"Mb/"+memVirtualTotal+"Mb\n\n...APLICACIoN...\nFisica usada: "+aplicacionMemoriaUsada+"Mb/"+aplicacionMemoriaMax+"Mb","Informacion memoria",1);
  }
}


class InterfaceGrafica_cambiarImagenFondo_actionAdapter implements ActionListener{
  private InterfaceGrafica adaptee;
  InterfaceGrafica_cambiarImagenFondo_actionAdapter(InterfaceGrafica adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    JFileChooser selectorImagen = new JFileChooser();
    selectorImagen.setFileFilter(new javax.swing.filechooser.FileFilter(){
      public boolean accept(java.io.File f) {
	if (f.isDirectory()) {
	  return true;
	}
	Utils utils = new Utils();
	String extension = utils.getExtension(f);
	if (extension != null) {
	  if (extension.equals(Utils.tiff) ||
	      extension.equals(Utils.tif) ||
	      extension.equals(Utils.gif) ||
	      extension.equals(Utils.jpeg) ||
	      extension.equals(Utils.jpg) ||
	      extension.equals(Utils.png)) {
	    return true;
	  }else{
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
    if(returnVal == JFileChooser.APPROVE_OPTION) {
      try{
	adaptee.panelPrincipal.cambiarImagenFondo( selectorImagen.getSelectedFile().getPath() );
	adaptee.repaint();
      }catch (Exception error){}
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

      if (i > 0 &&  i < s.length() - 1) {
	ext = s.substring(i+1).toLowerCase();
      }
      return ext;
    }
  }
}




