package simuladorbiologico.InterfazGrafica;

import java.awt.*;
import java.awt.event.*;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.net.URLClassLoader;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import com.borland.jbcl.layout.*;
import com.sun.management.OperatingSystemMXBean;
import simuladorbiologico.*;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.windows.WindowsLookAndFeel;
import com.borland.plaf.borland.BorlandLookAndFeel;
import fam.utilidades.*;


public class InterfaceGrafica  extends JFrame{

  /** Lanza la interfaz grafica */
  public static void main (String args[]){
    new InterfaceGrafica(null);
  }

  public InterfaceGrafica() {
    try {
      jbInit();
    }catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  Grafica _grafica;                         //hilo de ejecucion que ira graficando la simulacion
  Simulador _simulador = new Simulador();;  //hilo de ejecucion que hara la simulacion
  String problemaSeleccionado;              //problema actualmente seleccionado
  Object[] argumentosAdicionales;           //argumentos que necesita adicionalemtne el problema
  Problema problema;                        //problema seleccionado e instanciado
  Poblacion poblacion;                      //poblacion relativa al problema seleccionado instanciada

  MiJPanel panelPrincipal = new MiJPanel(null);
  private JPanel panelGrafica = new JPanel();
  private JComboBox jComboBoxTipoIndividuo = new JComboBox();
  private JTextField _generacionesMax = new JTextField();
  private JTextField _presionSelectiva = new JTextField();
  private JTextField _TamCromosoma = new JTextField();
  private JTextField _TamPoblacion = new JTextField();
  private JLabel etiquetaPoblacion = new JLabel();
  private JLabel cromosoma = new JLabel();
  private JLabel PasosMax = new JLabel();
  private JLabel Presion = new JLabel();
  private JLabel AumentopresionSelectiva = new JLabel();
  private JTextField _aumentoPresion = new JTextField();
  private JLabel Elitismo = new JLabel();
  private JTextField _elitismo = new JTextField();
  private JComboBox jComboBoxProblema = new JComboBox();
  private JLabel TipoIndividuo = new JLabel();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabelMutarGen = new JLabel();
  private JTextField _mutacion = new JTextField();
  private JLabel jLabel3 = new JLabel();
  private JTextField _puntuacion = new JTextField();
  private JButton Arrancar = new JButton();
  private JCheckBox jCheckBoxActivarGrafica = new JCheckBox();
  private JPanel jPanelParametrosFinalizacion = new JPanel();
  private JButton Abortar = new JButton();
  private JRadioButton radioBotonIndividuo = new JRadioButton();
  private JRadioButton radioBotonPoblacion = new JRadioButton();
  private PaneLayout paneLayout1 = new PaneLayout();
  private JCheckBox jCheckBoxIndividuoMedio = new JCheckBox();
  private JComboBox comboBoxSobrecruzamiento = new JComboBox();
  private JLabel jLabelTipoSobrecruzamiento = new JLabel();
  private JButton ayudaProblema = new JButton();
  private JComboBox jComboBoxSalida = new JComboBox();
  private JLabel jLabelTipoSalida = new JLabel();
  private JLabel jLabel6 = new JLabel();
  private JTextField iteraccionesRedibujado = new JTextField();
  private JCheckBox jCheckBoxMostrarPoblacion = new JCheckBox();
  private JCheckBox jCheckBoxIncrementarMutacion = new JCheckBox();
  private JCheckBox mutacionElitistas = new JCheckBox();
  private XYLayout xYLayout1 = new XYLayout();
  private XYLayout xYLayout2 = new XYLayout();
  private JButton botonGrafica = new JButton();//indica si vamos a hacer la grafica o no
  private JButton botonCrearProblema = new JButton();
  private boolean borrandoProblemas = false;
  private JPanel jPanelParametrosSimulador = new JPanel();
  private XYLayout xYLayout3 = new XYLayout();
  private TitledBorder titledBorder2 = new TitledBorder("Parametros de configuracion");
  private TitledBorder titledBorder3 = new TitledBorder("Parametros de Finalizacion");
  private JPanel jPanelSeleccionProblema = new JPanel();
  private TitledBorder titledBorder4 = new TitledBorder("Seleccion Problema");
  private XYLayout xYLayout4 = new XYLayout();
  private JTextFieldDroppable jTextFieldRutaProblemas = new JTextFieldDroppable();
  private JLabel jLabel2 = new JLabel();
  private JPanel jPanelVisualizacionResultados = new JPanel();
  private TitledBorder titledBorder5 = new TitledBorder("Visualizar Resultados");
  private XYLayout xYLayout5 = new XYLayout();
  private String rutaProblemas = System.getProperty("user.dir")+"/classes/simuladorbiologico/Problemas";
  private JButton jButtonBuscar = new JButton();
  public InterfaceGrafica(Object[] argumentosAdicionales) {
    try {
      this.argumentosAdicionales = argumentosAdicionales;
      jbInit();
      //cambiarLoockFeel();
      panelPrincipal.cambiarImagenFondo("skins/skinSimuladorBiologico.png");
      this.setVisible(true);
      this.pack();
    }catch(Exception e) { e.printStackTrace();}
  }

  /** Crea la interfaz grafica */
  private void jbInit() throws Exception {

   this.setContentPane(panelPrincipal);

    Simulador.hacerGrafica = true; //inicialmente no hacemos la grafica
    //Simulador.hacerGraficaPoblacionMedia = false; //inicialmente no hacemos la grafica del individuo medio
    Simulador.puntuacionPorIndividuo = true; //inicialmente la puntuacion para parar es referida al individuo y no al valor total de la poblaicon
    Simulador.simulado = false; //incialmente todavia no se ha hecho ninguna simulacion
    getContentPane().setLayout(xYLayout1);

    Abortar.setEnabled(false);
   jComboBoxSalida.addItem("Grafica");
   jComboBoxSalida.addItem("Terminal");
   jComboBoxSalida.addItem("Fichero");
   jComboBoxSalida.setSelectedIndex(0);
   radioBotonIndividuo.setOpaque(false);
    radioBotonIndividuo.setSelected(true);
   TipoIndividuo.setToolTipText("Elige un tipo de individuo de entre los implementados");
   TipoIndividuo.setText("Tipo de individuos");
    _generacionesMax.setText("300");
   _presionSelectiva.setText("4");
   _TamCromosoma.setText("100");
   _TamPoblacion.setText("100");
   etiquetaPoblacion.setToolTipText("Numero de individuos en la poblacion");
   etiquetaPoblacion.setText("Tamanyo poblacion");
   cromosoma.setToolTipText("Numero de alelos que componen el cromosoma de todos los individuos");
   cromosoma.setText("Tamanyo Cromosoma");
   PasosMax.setFont(new java.awt.Font("Dialog", 1, 11));
    PasosMax.setToolTipText("Iteraciones a dar hasta que el programa se para sino encuentra una " + "solucion");
   PasosMax.setText("Gener. Max");
   Presion.setToolTipText("numero de individuos contra los que tiene que competir por sobrevivir");
   Presion.setText("Presion selectiva");
   AumentopresionSelectiva.setToolTipText("Cada cuantas iteraciones se aumenta en 1 la presion selectiva");
   AumentopresionSelectiva.setText("Aumento Presion");
   _aumentoPresion.setText("10");
   Elitismo.setToolTipText("Numero de individuos que pasan a la siguiente generacion por ser " + "los mejores");
   Elitismo.setText("Elitismo");
   _elitismo.setText("1");
   jLabel1.setToolTipText("Elige un problema de entre los implementados");
   jLabel1.setText("Tipo de problema");
    jLabelMutarGen.setToolTipText("Probabilidad que teine cada gen de cada individuo de ser mutado");
    jLabelMutarGen.setText("Mutar gen % (0-100)");
   _mutacion.setText("0.2");
    _mutacion.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	_mutacion_actionPerformed(e);
      }
    });
   jLabel3.setFont(new java.awt.Font("Dialog", 1, 11));
    jLabel3.setToolTipText("Puntuacion que queremos obtener para resolver el problema");
   jLabel3.setText("Puntuacion Obtener");
   _puntuacion.setText("100");
   Arrancar.setBackground(SystemColor.textHighlight);
   Arrancar.setToolTipText("Inicia la simulacion, acaba cuando otbiene la puntuacion deseada "+"o cuando se llega a un determinado numero de pasos maximos");
   Arrancar.setText("Iniciar");
   Arrancar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	Arrancar_actionPerformed(e);
      }
   });
   panelGrafica.setLayout(paneLayout1);
    panelGrafica.setBorder(BorderFactory.createRaisedBevelBorder());
    panelGrafica.setOpaque(true);
    panelGrafica.addMouseMotionListener(new MouseMotionListener(){
      public void mouseMoved(MouseEvent evento){
	ratonMovioSobreGrafica(evento);
      }
      public void mouseDragged(MouseEvent evento){}
    });

    jCheckBoxActivarGrafica.setToolTipText("si se activa esta casilla se dibujara la grafica correspondiente " + "a puntuacion/iteraciones, esto lastra algo el rendimiento del programa");
    jCheckBoxActivarGrafica.setSelected(true);
    jCheckBoxActivarGrafica.setText("Activar grafica");
    jCheckBoxActivarGrafica.setBorderPaintedFlat(false);
    jCheckBoxActivarGrafica.setEnabled(false);
    jCheckBoxActivarGrafica.setOpaque(false);
    jCheckBoxActivarGrafica.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	jCheckBoxActivarGrafica_actionPerformed(e);
      }
    });
    jPanelParametrosFinalizacion.setOpaque(false);
    jPanelParametrosFinalizacion.setBorder(titledBorder3);
    jPanelParametrosFinalizacion.setToolTipText("condiciones para parar la busqueda");
    jPanelParametrosFinalizacion.setLayout(xYLayout2);
    Abortar.setBackground(Color.gray);
    Abortar.setToolTipText("aborta la ejecucion");
    Abortar.setText("Abortar");
    Abortar.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	Abortar_actionPerformed(e);
      }
    });
    radioBotonIndividuo.setText("Individuo");
    radioBotonIndividuo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	radioBotonIndividuo_actionPerformed(e);
      }
    });
    radioBotonPoblacion.setOpaque(false);
    radioBotonPoblacion.setRequestFocusEnabled(true);
    radioBotonPoblacion.setText("poblacion");
    radioBotonPoblacion.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	radioBotonPoblacion_actionPerformed(e);
      }
    });


    jComboBoxTipoIndividuo.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	jComboBoxTipoIndividuo_actionPerformed(e);
      }
    });

    jComboBoxTipoIndividuo.setToolTipText("selecciona el tipo de individuo para resolver el problema");
    jComboBoxTipoIndividuo.setSelectedItem(this);
    jComboBoxProblema.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	jComboBoxProblema_actionPerformed(e);
      }
    });
    jComboBoxProblema.setBackground(new Color(215, 224, 244));
    jComboBoxProblema.setToolTipText("Selecciona uno de los problemas implementados");
    jComboBoxProblema.setSelectedItem(this);
    jCheckBoxIndividuoMedio.setEnabled(true);
    jCheckBoxIndividuoMedio.setOpaque(false);
    jCheckBoxIndividuoMedio.setSelected(true);
    jCheckBoxIndividuoMedio.setToolTipText("Muestra en cada momento la puntuacion media de los individuos de " + "la poblacion");
    jCheckBoxIndividuoMedio.setText("Mostrar individuo medio");
    jCheckBoxIndividuoMedio.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	jCheckBox2_actionPerformed(e);
      }
    });
    jLabelTipoSobrecruzamiento.setToolTipText("selecciona la manera en que se realizara el cruzamiento en el cromosoma " + "de cada individuo");
    jLabelTipoSobrecruzamiento.setText("Tipo sobrecruzamiento");
    comboBoxSobrecruzamiento.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	comboBoxSobrecruzamiento_actionPerformed(e);
      }
    });
    comboBoxSobrecruzamiento.setActionCommand("comboBoxSobrecruzamiento");
    ayudaProblema.setIcon(new ImageIcon("recursos/ayuda.png"));
    ayudaProblema.setMargin(new Insets(2, 2, 2, 2));
    ayudaProblema.setToolTipText("Ayuda a ajustar los parametros para el problema selecionado, indicando " + "como se ha codificado cada problema y en que consiste");
    ayudaProblema.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	ayudaProblema_actionPerformed(e);
      }
    });
    jLabelTipoSalida.setText("Tipo de Salida de los Resultados");
    jComboBoxSalida.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	jComboBoxSalida_actionPerformed(e);
      }
    });
    jLabel6.setToolTipText("Se repinta la grafica aproximadamente cada X iteraciones");
    jLabel6.setText("iteraciones para actualizar resultados");
    iteraccionesRedibujado.setText("1");
    iteraccionesRedibujado.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	iteraccionesRedibujado_actionPerformed(e);
      }
    });
    jComboBoxSalida.setBackground(Color.pink);
    jComboBoxSalida.setToolTipText("Sobre que soporte se mostraran los resultados");
    jComboBoxSalida.setSelectedItem("Grafica");
    jCheckBoxMostrarPoblacion.setText("Mostrar poblacion cada generacion");
    jCheckBoxMostrarPoblacion.setEnabled(true);
    jCheckBoxMostrarPoblacion.setOpaque(false);
    jCheckBoxMostrarPoblacion.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	jCheckBoxMostrarPoblacion_actionPerformed(e);
      }
    });

    jCheckBoxIncrementarMutacion.setText("Aumento mutacion progresiva");
    jCheckBoxIncrementarMutacion.setEnabled(true);
    jCheckBoxIncrementarMutacion.setSelected(true);
    jCheckBoxIncrementarMutacion.setOpaque(false);
    jCheckBoxIncrementarMutacion.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	jCheckBoxIncrementarMutacion_actionPerformed(e);
      }
    });


    this.setResizable(false);
    this.setTitle("Simulador Biologico Generico y Portable");
    mutacionElitistas.setEnabled(true);
    mutacionElitistas.setOpaque(false);
    mutacionElitistas.setToolTipText("El mejor individuo durante que lo sea no sufrira mutaciones");
    mutacionElitistas.setSelected(true);
    mutacionElitistas.setText("Inmutable el mejor ");
    mutacionElitistas.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	mutacionElitistas_actionPerformed(e);
      }
    });
    xYLayout1.setWidth(678);
    xYLayout1.setHeight(570);
    botonGrafica.setBackground(new Color(237, 236, 236));
    botonGrafica.setBorder(null);
    botonGrafica.setDebugGraphicsOptions(0);
    botonGrafica.setToolTipText("En este area se dibuja una grafica");
    botonGrafica.setVerifyInputWhenFocusTarget(true);
    botonGrafica.setBorderPainted(false);
    botonGrafica.setIcon(new ImageIcon("recursos/tonecurve.gif"));
    botonGrafica.setText("");
    jButtonBuscar.setToolTipText("Path carpeta donde estan los problemas implementados");
    jButtonBuscar.setIcon(new ImageIcon("recursos/buscar.png"));
    jButtonBuscar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	jButtonBuscar_actionPerformed(e);
      }
    });
    //botonCrearProblema.setText("crearProblema");
    botonCrearProblema.setIcon(new ImageIcon("recursos/mas.gif"));
    botonCrearProblema.setToolTipText("Sirve para crear un nuevo problema, generando los ficheros necesarios para que el usuario los pueda editar");
    jTextFieldRutaProblemas.setText(rutaProblemas);
    botonCrearProblema.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	botonCrearProblema_actionPerformed(e);
      }
    });
    getContentPane().addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
	panelPrincipal_mouseClicked(e);
      }
    });
    anyadirSobrecruzamientosImplementados();
    jPanelParametrosSimulador.setBorder(titledBorder2);
    jPanelParametrosSimulador.setOpaque(false);
    jPanelParametrosSimulador.setLayout(xYLayout3);
    jPanelSeleccionProblema.setBorder(titledBorder4);
    jPanelSeleccionProblema.setOpaque(false);
    jPanelSeleccionProblema.setLayout(xYLayout4);
    jTextFieldRutaProblemas.setBackground(new Color(237, 234, 232));
    jTextFieldRutaProblemas.setBorder(BorderFactory.createLineBorder(Color.black));
    jTextFieldRutaProblemas.setToolTipText("Path carpeta donde estan los problemas implementados");
    jLabel2.setToolTipText("Path carpeta donde estan los problemas implementados");
    jLabel2.setText("Path problemas");
    jPanelVisualizacionResultados.setBorder(titledBorder5);
    jPanelVisualizacionResultados.setOpaque(false);
    jPanelVisualizacionResultados.setLayout(xYLayout5);
    panelPrincipal.setEnabled(true);
    jPanelParametrosFinalizacion.add(radioBotonIndividuo, new XYConstraints(13, 5, 76, 13));
    jPanelParametrosFinalizacion.add(radioBotonPoblacion, new XYConstraints(98, 2, 85, 16));
    jPanelParametrosFinalizacion.add(jLabel3, new XYConstraints(1, 37, 118, -1));
    jPanelParametrosFinalizacion.add(_generacionesMax, new XYConstraints(122, 68, 69, -1));
    this.getContentPane().add(Abortar, new XYConstraints(118, 500, 82, 52));
    this.getContentPane().add(Arrancar, new XYConstraints(28, 500, 83, 52));
    this.getContentPane().add(jPanelParametrosFinalizacion, new XYConstraints(5, 352, 209, 131));
    jPanelParametrosSimulador.add(etiquetaPoblacion, new XYConstraints(10, 8, -1, -1));
    jPanelParametrosSimulador.add(_TamPoblacion, new XYConstraints(135, 2, 43, -1));
    jPanelParametrosSimulador.add(cromosoma, new XYConstraints(10, 42, -1, -1));
    jPanelParametrosSimulador.add(_TamCromosoma, new XYConstraints(135, 36, 43, -1));
    jPanelParametrosSimulador.add(Presion, new XYConstraints(10, 76, -1, -1));
    jPanelParametrosSimulador.add(_presionSelectiva, new XYConstraints(135, 70, 43, -1));
    jPanelParametrosSimulador.add(AumentopresionSelectiva, new XYConstraints(10, 109, -1, -1));
    jPanelParametrosSimulador.add(_aumentoPresion, new XYConstraints(135, 103, 43, -1));
    jPanelParametrosSimulador.add(Elitismo, new XYConstraints(10, 142, -1, -1));
    jPanelParametrosSimulador.add(_elitismo, new XYConstraints(135, 136, 43, -1));
    jPanelParametrosSimulador.add(jLabelMutarGen, new XYConstraints(10, 174, -1, -1));
    jPanelParametrosSimulador.add(_mutacion, new XYConstraints(135, 168, 43, -1));
    jPanelSeleccionProblema.add(TipoIndividuo, new XYConstraints(9, 83, 121, -1));
    jPanelSeleccionProblema.add(jLabel1, new XYConstraints(9, 33, 123, -1));
    jPanelVisualizacionResultados.add(jCheckBoxIndividuoMedio, new XYConstraints(226, 4, 176, -1));
    jPanelVisualizacionResultados.add(jComboBoxSalida, new XYConstraints(7, 23, 184, 19));
    jPanelVisualizacionResultados.add(jCheckBoxActivarGrafica, new XYConstraints(41, 46, 143, 20));
    jPanelVisualizacionResultados.add(jCheckBoxMostrarPoblacion, new XYConstraints(226, 34, 203, -1));
    jPanelVisualizacionResultados.add(jLabelTipoSalida, new XYConstraints(7, 5, 157, -1));
    panelGrafica.add(botonGrafica, new PaneConstraints("jButton1", "jButton1", PaneConstraints.ROOT, 0.5F));
    jPanelVisualizacionResultados.add(iteraccionesRedibujado, new XYConstraints(287, 361, 115, -1));
    jPanelVisualizacionResultados.add(jLabel6, new XYConstraints(33, 367, 249, -1));
    jPanelParametrosSimulador.add(jLabelTipoSobrecruzamiento, new XYConstraints(10, 209, 136, 18));
    jPanelParametrosSimulador.add(comboBoxSobrecruzamiento, new XYConstraints(35, 229, 143, -1));
    jPanelParametrosSimulador.add(mutacionElitistas, new XYConstraints( -4, 285, -1, -1));
    jPanelParametrosSimulador.add(jCheckBoxIncrementarMutacion, new XYConstraints( -4, 261, 203, 23));
    this.getContentPane().add(jPanelParametrosSimulador, new XYConstraints(5, 0, 209, 342));
    this.getContentPane().add(jPanelSeleccionProblema, new XYConstraints(217, 0, 458, 151));
    jPanelVisualizacionResultados.add(panelGrafica, new XYConstraints(11, 79, 414, 276));
    this.getContentPane().add(jPanelVisualizacionResultados, new XYConstraints(219, 155, 454, 409));
    jPanelParametrosFinalizacion.add(PasosMax, new XYConstraints(43, 65, 67, 23));
    jPanelSeleccionProblema.add(jComboBoxProblema, new XYConstraints(9, 51, 229, 20));
    jPanelSeleccionProblema.add(jComboBoxTipoIndividuo, new XYConstraints(9, 99, 229, 20));
    jPanelSeleccionProblema.add(botonCrearProblema, new XYConstraints(252, 44, 28, 27));
    jPanelSeleccionProblema.add(ayudaProblema, new XYConstraints(288, 44, 28, 27));
    jPanelSeleccionProblema.add(jButtonBuscar, new XYConstraints(395, 0, 29, 22));
    jPanelSeleccionProblema.add(jTextFieldRutaProblemas, new XYConstraints(94, 5, 298, 15));
    jPanelSeleccionProblema.add(jLabel2, new XYConstraints(10, 5, 81, 16));
    jPanelParametrosFinalizacion.add(_puntuacion, new XYConstraints(122, 32, 69, -1));
    anyadirProblemasImplementados();
    //this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
  }


//____________________________________ESCUCHADORES DE EVENTOS_____________________________________________________

  /**
   * ARRANCAR LA SIMULACION
   * Metodo que se encarga de lanzar a ejectuar la aplicacion, crea un hilo para ejectuar  el algoritmo que ejecuta el algoritmo genetico
   * y otro hilo para realizar la grafica si es que asi se elige
   */
  private void Arrancar_actionPerformed(ActionEvent e) {

    try{
      _simulador = new Simulador(this);
      double puntuacion = Double.parseDouble(_puntuacion.getText());
      int pasosMaximo = Integer.parseInt(_generacionesMax.getText());
      int tamanyoPoblacion = Integer.parseInt(_TamPoblacion.getText());
      int tamanyoCromosoma = Integer.parseInt(_TamCromosoma.getText());
      int presionSelectiva = Integer.parseInt(_presionSelectiva.getText());
      int aumentoPresionSelectiva = Integer.parseInt(_aumentoPresion.getText());
      double mutacion = Double.parseDouble(_mutacion.getText());
      int elitismo = Integer.parseInt(_elitismo.getText());
      Simulador.iteracionesRedibujado = Integer.parseInt(iteraccionesRedibujado.getText());
      String individuo = (String)jComboBoxTipoIndividuo.getSelectedItem();
      String sobrecruzamiento = (String)comboBoxSobrecruzamiento.getSelectedItem();


       _simulador.asignarParametros(poblacion,individuo, puntuacion, tamanyoPoblacion, tamanyoCromosoma, presionSelectiva, mutacion, aumentoPresionSelectiva, elitismo, pasosMaximo,sobrecruzamiento,Simulador.iteracionesRedibujado, argumentosAdicionales);
       cambiarEstadoParametrosNoActualizables(false);  //desactivamos el arrancar grafica, tamanyo cromosoma, tipo de problema, etc

	if (Simulador.tipoSalidaResultados == "Grafica"){
	  //<<<<<<<<arrancamos la grafica (en un nuevo hilo)>>>>>>>>>>
	  _grafica = new Grafica(_simulador,panelGrafica);
	  this.pack();
	  int num = Integer.parseInt(iteraccionesRedibujado.getText());
	  Simulador.iteracionesRedibujado = num;
	  _grafica.arrancarGrafica();
	}
      //<<<<<<<<<<arrancamos la simulacion (en un nuevo hilo)>>>>>>>
      _simulador.asignarGrafica(_grafica);
      _simulador.start();

    }catch (Exception error){
      cambiarEstadoParametrosNoActualizables(true);
      javax.swing.JOptionPane.showMessageDialog(this,"Alguno/s de los parametros es incorrecto "+ error.getMessage());
      error.printStackTrace();
    }

  }

  /** ABORTA la ejecucion del algortimo genetico*/
  private void Abortar_actionPerformed(ActionEvent e) {
    if (_simulador != null){
      _simulador.parar();
      _simulador.mostrarResultadosFinales(Simulador.tipoSalidaResultados);
      if (_grafica != null) _grafica.parar();
      Arrancar.setIcon(null);
      Arrancar.setText("Iniciar");
    }
    cambiarEstadoParametrosNoActualizables(true);
  }

  /**
   * INSTANCIACIoN DEL PROBLEMA SELECCIONADO
   * Se selecciona el tipo de problema a resolver y se anyaden los tipos de
   * individuos con los que se puede resolver y los valores de la simulacion
   * de por defecto para ese problema
   */
  void jComboBoxProblema_actionPerformed(ActionEvent e) {
   if (borrandoProblemas) return;
    String seleccionado = (((JComboBox)e.getSource()).getSelectedItem().toString());
    problemaSeleccionado=seleccionado;

    try{
      jComboBoxTipoIndividuo.removeAllItems();

      //es un problema interno (no necesista ser cargada la clase pues ya se carga con el proyecto)
      if (rutaProblemas.equalsIgnoreCase(System.getProperty("user.dir")+"/classes/simuladorbiologico/Problemas")){  //es un problema del propio simulador
	  //instanciamos el problema seleccionado
	  this.problema = Problema.instanciarProblema("simuladorbiologico.Problemas." + problemaSeleccionado + ".Problema" + problemaSeleccionado);
	  //instanciamos la poblacion
	  this.poblacion = Poblacion.instanciarPoblacion(problema, "simuladorbiologico.Problemas." + problemaSeleccionado + ".Poblacion" + problemaSeleccionado);

      //es un problema externo (necesita cargarse con un classLoader la clase externa)
      }else{
	//cargamos las clases
	URL listaURLs[] = { new java.io.File(rutaProblemas+"/"+problemaSeleccionado+"/").toURL()};
	ClassLoader cargador = new URLClassLoader (listaURLs);

	//las instanciamos
	Class claseProblema = cargador.loadClass("Problema"+problemaSeleccionado);
	problema = ((Problema)claseProblema.newInstance());
	Class clasePoblacion = cargador.loadClass("Poblacion"+problemaSeleccionado);
	java.lang.reflect.Constructor[] constructoresPoblacion = clasePoblacion.getConstructors();
	Object[] argumentosPoblacion={problema};
	poblacion = ((Poblacion)constructoresPoblacion[0].newInstance(argumentosPoblacion));
      }


      String[] individuosSoporta = problema.individuosSoporta();
      for (int i = 0; i < individuosSoporta.length; i++){
	jComboBoxTipoIndividuo.addItem(individuosSoporta[i]);
      }

      _TamPoblacion.setText(""+problema.numeroIndividuosPorDefecto());
      _TamCromosoma.setText(""+problema.longitudCromosomaPorDefecto());
      _generacionesMax.setText(""+problema.iteracionesMaximasPorDefecto());
      _puntuacion.setText(""+problema.puntuacionAObtenerPorDefecto());
      radioBotonIndividuo.setEnabled(problema.individuoPuedeSerSolucion());
      radioBotonPoblacion.setEnabled(problema.poblacionPuedeSerSolucion());

      this.radioBotonIndividuo.setSelected(true);                       //por defecto la solucion hace referencia al individuo
      comboBoxSobrecruzamiento.setSelectedItem("Partir aleatorio");     //por defecto el tipo de sobrecruzamiento es especilaizado

    }catch (Exception error){
      jComboBoxProblema.removeItem(problemaSeleccionado);
      System.out.println("No se puede instanciar el problema " + problemaSeleccionado + ", seguramente porque necesite recibir arguemtnos el constructor del problema o porque no ha sido compilado");
    }
  }

  /**
   * CREAR UN NUEVO PROBLEMA A SOLUCIONAR
   *  Evento del boton que permite crear un nuevo problema y anyadirlo a los problemas implementados
   */
  public void botonCrearProblema_actionPerformed(ActionEvent e) {
    try{
      String nombreProblema;
      if (jTextFieldRutaProblemas.getText().equalsIgnoreCase(System.getProperty("user.dir")+"/classes/simuladorbiologico/Problemas")){
	do{
	  nombreProblema = javax.swing.JOptionPane.showInputDialog(null,"Introduce el nombre del problema que se creara dentro del propio proyecto");
	}while (nombreProblema.length()==0);
	new CreadorProblemasInternos(nombreProblema,this); //creamos los ficheros y los mostramos para poder editarlos y compilarlos
      }else{
	do{
	  nombreProblema = javax.swing.JOptionPane.showInputDialog(null,"Introduce el nombre del problema que se creara en: " + jTextFieldRutaProblemas.getText());
	}while (nombreProblema.length()==0);
	new CreadorProblemasExternos(nombreProblema,jTextFieldRutaProblemas.getText());
      }
    }catch (Exception error){
      JOptionPane.showMessageDialog(this,"NO se ha podido crear el problema");
    }
  }

  /**
   * BUSCA LOS PROBLEMAS QUE SE HAN IMPLEMENTADO EN LA RUTA INDICADA
   */
  public void jButtonBuscar_actionPerformed(ActionEvent e) {
    rutaProblemas = jTextFieldRutaProblemas.getText();
    anyadirProblemasImplementados();
  }



  /** Cambia el tipo de individuo con el que se va a simular*/
  void jComboBoxTipoIndividuo_actionPerformed(ActionEvent e) {
    Object objeto = ((JComboBox)e.getSource()).getSelectedItem();
     if ((objeto != null) && (_simulador != null)){
      _simulador.setTipoIndividuo(objeto.toString());
    }
  }

  /** Cambia el tipo de sobrecruzamiento con el que se va a simular*/
  void comboBoxSobrecruzamiento_actionPerformed(ActionEvent e) {
    Object objeto = ((JComboBox)e.getSource()).getSelectedItem();
    if (objeto != null){
      _simulador.setTipoSobrecruzamiento(objeto.toString());
    }
    System.out.println("Hemos cambiado el tipo de sobrecruzamiento: " + objeto.toString());
  }


  /** Activa/desactiva la opcion de hacer la grafica durante la simulacion*/
  private void jCheckBoxActivarGrafica_actionPerformed(ActionEvent e) {
    panelGrafica.removeAll();
    Simulador.hacerGrafica = !Simulador.hacerGrafica;
  }


  /** Cambia el tipo de solucion a obtener, el individuo es la solucion */
  void radioBotonIndividuo_actionPerformed(ActionEvent e) {
    Simulador.puntuacionPorIndividuo = true;
    radioBotonPoblacion.setSelected(false);
  }

  /** Cambia el tipo de solucion a obtener, la poblacion es la solucion */
  void radioBotonPoblacion_actionPerformed(ActionEvent e) {
    Simulador.puntuacionPorIndividuo = false;
    radioBotonIndividuo.setSelected(false);
  }


  /**Activa desactiva el graficado de el individuo medio de toda la poblacion*/
  void jCheckBox2_actionPerformed(ActionEvent e) {
    Simulador.hacerGraficaPoblacionMedia = jCheckBoxIndividuoMedio.isSelected();
  }

  /**Mostramos ayuda para cada problema en concreto selecionado*/
  void ayudaProblema_actionPerformed(ActionEvent e) {
    try{
      javax.swing.JOptionPane.showMessageDialog(null,problema.obtenerAyuda(),"Informacion",javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }catch(Exception error){System.out.println("No se encuentra ayuda para ese problema");}
  }

  /** Determina por donde se va ha hacer la salida de los resultados*/
  void jComboBoxSalida_actionPerformed(ActionEvent e) {
    System.out.println("Hemos cambiado el tipo de salida de resultados");
    Object objeto = ((JComboBox)e.getSource()).getSelectedItem();
    if (objeto != null){
      Simulador.tipoSalidaResultados = objeto.toString();
      if (objeto.toString().equalsIgnoreCase("Terminal") || objeto.toString().equalsIgnoreCase("Fichero")){
	jCheckBoxActivarGrafica.setEnabled(false);
	jCheckBoxMostrarPoblacion.setEnabled(true);
      }else if (objeto.toString().equalsIgnoreCase("Grafica")){
	jCheckBoxActivarGrafica.setEnabled(true);
	jCheckBoxMostrarPoblacion.setEnabled(false);

      }
    }
  }

  /** Determina si en la simulacion se iran mostrando las sucesivas generaciones de poblacion */
  void jCheckBoxMostrarPoblacion_actionPerformed(ActionEvent e) {
    Simulador.mostrarPoblacion = !Simulador.mostrarPoblacion;
  }

  /** Determina si en la simulacion se va a ir incrementando la mutacion segun pasan las generaciones */
  void jCheckBoxIncrementarMutacion_actionPerformed(ActionEvent e) {
    _simulador.setIncrementarMutacionConIteraciones(jCheckBoxIncrementarMutacion.isSelected());
  }


  /** Cambia el criterio de si mantener o no inmutables a los elitistas*/
  void mutacionElitistas_actionPerformed(ActionEvent e) {
    if (_simulador != null){
      _simulador.setMejorInmutable(!_simulador.esMejorInmutable());
    }
  }

  /**Cambia la probabilidad de mutar un gen en el simulador*/
  void _mutacion_actionPerformed(ActionEvent e) {
    double probabilidad;
    try{
      probabilidad = Double.parseDouble(_mutacion.getText());
      _simulador.setProbabilidadMutarGen(probabilidad);
      System.out.println("Nuevo nivel mutacion: "+probabilidad);
    }catch (Exception err){System.out.println("Error al cambiar el intervalo de redibujado");}
  }

  /**Cambia cada cuanto se muestran los resultados de la simulacion en el simulador*/
  void iteraccionesRedibujado_actionPerformed(ActionEvent e) {
    int iteracciones;
    try{
      iteracciones = Integer.parseInt(iteraccionesRedibujado.getText());
      Simulador.iteracionesRedibujado = iteracciones;
      System.out.println("nueva frecuencia");
    }catch (Exception err){ System.out.println("Error al cambiar el intervalo de redibujado");}
  }

  /**
   * Informacion que se muestra en un Tooltip cuando se mueve el raton sobre la grafica para indicar
   * la posicion real del raton sobre la grafica
   */
  private void ratonMovioSobreGrafica(MouseEvent evento) {
    try{
      int alturaPanelGrafica = panelGrafica.getHeight();
      int anchuraPanelGrafica = panelGrafica.getWidth();
      int x = evento.getX() - 20;
      int y = evento.getY() - alturaPanelGrafica + 20;
      y = -y;
      double puntuacionObtener;
      if (problema.isMaximizeProblem()) {
        puntuacionObtener = Double.parseDouble(_puntuacion.getText());
      } else {
        puntuacionObtener = _grafica.puntuacionAObtener;
      }
      int generaccionesMax = Integer.parseInt(_generacionesMax.getText());
      double graphX = (int) (x * generaccionesMax) / (anchuraPanelGrafica - 40);
      double graphY = (y * puntuacionObtener) / (alturaPanelGrafica - 40);
      panelGrafica.setToolTipText(" Generacion:" + Math.round(graphX) + " Fitness:" + graphY);
    }catch (Exception e){}
  }



  /**
   * Permite activar o desactivar los parametros no reconfigurables de la interface grafica durante la ejecucion
   * @param activar Si esta true se activaran , si False se desactivaran
   */
  public void cambiarEstadoParametrosNoActualizables (boolean activar){
   if (activar){
      _TamCromosoma.setBackground(Color.WHITE);
      _TamPoblacion.setBackground(Color.WHITE);
      _presionSelectiva.setBackground(Color.WHITE);
      _elitismo.setBackground(Color.WHITE);
      _aumentoPresion.setBackground(Color.WHITE);
      _puntuacion.setBackground(Color.WHITE);
      _generacionesMax.setBackground(Color.WHITE);
    }else{
      _TamCromosoma.setBackground(Color.GRAY);
      _TamPoblacion.setBackground(Color.GRAY);
      _presionSelectiva.setBackground(Color.GRAY);
      _elitismo.setBackground(Color.GRAY);
      _aumentoPresion.setBackground(Color.GRAY);
      _puntuacion.setBackground(Color.GRAY);
      _generacionesMax.setBackground(Color.GRAY);
    }
    jCheckBoxActivarGrafica.setEnabled(activar);
    radioBotonIndividuo.setEnabled(activar);
    radioBotonPoblacion.setEnabled(activar);
    jComboBoxSalida.setEnabled(activar);
    _TamCromosoma.setEnabled(activar);
    _TamPoblacion.setEnabled(activar);
    jComboBoxProblema.setEnabled(activar);
    _presionSelectiva.setEnabled(activar);
    _elitismo.setEnabled(activar);
    _aumentoPresion.setEnabled(activar);
    jComboBoxTipoIndividuo.setEnabled(activar);
    _puntuacion.setEnabled(activar);
    _generacionesMax.setEnabled(activar);
    Arrancar.setEnabled(activar);
    Abortar.setEnabled(!activar);
  }

  /** Anyade al combobox de problemas los problemas que estan implementados y compilados  */
  public void anyadirProblemasImplementados(){
    try{
      java.io.File fich = new java.io.File(rutaProblemas);
      borrandoProblemas = true;
      jComboBoxProblema.removeAllItems();
      borrandoProblemas = false;
      String[] problemas = fich.list();

      for (int i = 0; i < problemas.length; i++){
	//anyadimos los problemas que son carpetas (los ficheros sueltos no se miran)
	if ((new java.io.File(rutaProblemas+"/"+problemas[i])).isDirectory()){
	  jComboBoxProblema.addItem(problemas[i]);
	}
     }
    }catch (Exception e){ System.out.println("Se ha producido un fallo al intentar acceder a los problemas implementados: "+e.getMessage());}
 }

 /** Anyade al combobox de sobrecurzamientos los que estan implementados en la clase Reproduccion */
 public void anyadirSobrecruzamientosImplementados(){

   this.comboBoxSobrecruzamiento.addItem(Reproduccion.sobrecruzamientoPartirMitad);
   this.comboBoxSobrecruzamiento.addItem(Reproduccion.sobrecruzamientoPartirAleatio);
   this.comboBoxSobrecruzamiento.addItem(Reproduccion.sobrecruzamientoSeleccionGen);
   this.comboBoxSobrecruzamiento.addItem(Reproduccion.sobrecruzamientoNinguno);
   this.comboBoxSobrecruzamiento.setSelectedItem(Reproduccion.sobrecruzamientoPartirAleatio);//seleccioando por defecto
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
  public void panelPrincipal_mouseClicked(MouseEvent e) {
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
  /** Muestra informacion de la memoria de la aplicacion y del sistema operativo */
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

  /** Permite cambiar la imagen de fondo de la ventana */
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

