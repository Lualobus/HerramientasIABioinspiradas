package ColoniaHormigas.InterfaceGrafica;

import java.awt.*;
import java.awt.event.*;
import java.lang.management.*;
import java.net.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import ColoniaHormigas.*;
import com.borland.jbcl.layout.*;    //importamos un layout nuevo
import com.borland.plaf.borland.*;
import com.jgoodies.looks.plastic.*;  //importamos un look&feel nuevo
import com.jgoodies.looks.windows.*;  //importamos un look&feel nuevo
import com.sun.management.OperatingSystemMXBean;
import fam.utilidades.*;



public class InterfazGrafica extends JFrame{

  /** Metodo main */
  public static void main(String args[]){
    new InterfazGrafica();
  }

  OptimizadorColoniaHormigas aco; // el optimizador por colonia de hormigas
  Grafica grafica; //la grafica de la evolucion del optimizador
  private Problema problema;   //problema que esta seleccionado
  private String rutaProblemas = System.getProperty("user.dir")+"/classes/ColoniaHormigas/Problemas"; //ruta donde esta el problema implementado o que vamos a crear

  MiJPanel panelPrincipal = new MiJPanel(null);
  private XYLayout xYLayout1 = new XYLayout();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabelFactorEvaporacionLocal = new JLabel();
  private JLabel jLabelFactorEvaporacionGlobal = new JLabel();
  private JLabel jLabelFactorExplotacionInicial = new JLabel();
  private JLabel jLabelValoracionObtener = new JLabel();
  private JLabel jLabelIteraccionesMax = new JLabel();
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
  private JLabel jLabelNumHormigas = new JLabel();
  private JCheckBox jCheckBoxActualizarExplotacion = new JCheckBox();
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
  private ButtonGroup grupoRadioButton = new ButtonGroup();
  private SpinnerNumberModel restriccionesNumHormigas = new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1);
  private SpinnerNumberModel restriccionesCosteInicialAproximado = new SpinnerNumberModel(1D,1D,Double.MAX_VALUE,1);
  private SpinnerNumberModel restriccionesFactorEvaporacionLocal = new SpinnerNumberModel(1D,0.0D,Double.MAX_VALUE,0.05D);
  private SpinnerNumberModel restriccionesFactorEvaporacionGlobal = new SpinnerNumberModel(1D,0D,Double.MAX_VALUE,0.05D);
  private SpinnerNumberModel restriccionesFactorExplotacionInicial = new SpinnerNumberModel(1D,0D,Double.MAX_VALUE,0.05D);
  private SpinnerNumberModel restriccionesCosteAObtener = new SpinnerNumberModel(1D,0D,Double.MAX_VALUE,1D);
  private SpinnerNumberModel restriccionesIteraccionesMax = new SpinnerNumberModel(new Long(1),new Long(1),new Long(Long.MAX_VALUE),new Long(1));
  private JSpinner jTextFieldNumHormigas = new JSpinner(restriccionesNumHormigas);
  private JSpinner jTextFieldCosteInicialAproxmimado = new JSpinner(restriccionesCosteInicialAproximado);
  private JSpinner jTextFieldFactorEvaporacionLocal = new JSpinner(restriccionesFactorEvaporacionLocal);
  private JSpinner jTextFieldFactorEvaporacionGlobal = new JSpinner(restriccionesFactorEvaporacionGlobal);
  private JSpinner jTextFieldFactorExplotacionInicial = new JSpinner(restriccionesFactorExplotacionInicial);
  private JSpinner jTextFieldCosteAObtener = new JSpinner(restriccionesCosteAObtener);
  private JSpinner jTextFieldIteracionesMax = new JSpinner(restriccionesIteraccionesMax);

  private JTextFieldDroppable jTextFieldRutaProblemas = new JTextFieldDroppable(rutaProblemas);
  private JLabel jLabelPathProblemas = new JLabel();
  private JButton jButtonBuscar = new JButton();
  private boolean borrandoProblemas = false;
  private JButton jButtonParar = new JButton();

  public InterfazGrafica() {
    try {
      jbInit();
      //cambiarLoockFeel();
     panelPrincipal.cambiarImagenFondo("skins/skinColoniaHormigas.png");
      setVisible(true);
      pack();
    }catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setContentPane(panelPrincipal);
    this.setResizable(false);
    setTitle("Optimizador por Colonia de Hormigas (ACO)");
    this.addMouseListener(new InterfazGrafica_this_mouseAdapter(this));
    this.getContentPane().setLayout(xYLayout1);
    jLabel1.setToolTipText("Estimacion inicial de lo que puede ser el coste medio del trayecto " + "que se desea minimizar");
    jLabel1.setText("Coste aprox inicial");
    xYLayout1.setWidth(651);
    xYLayout1.setHeight(450);
    jLabel7.setText("Seleccione Problema");
    jLabel8.setText("Tipo Salida Resultados");
    panelGrafica.setLayout(paneLayout1);
    jButtonGrafica.setBorder(null);
    jButtonGrafica.setToolTipText("Grafica");
    jButtonGrafica.setBackground(new Color(237,236,236));
    jButtonGrafica.setBorderPainted(false);
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
    panelGrafica.addMouseMotionListener(new MouseMotionListener(){
      public void mouseMoved(MouseEvent evento){
	ratonMovioSobreGrafica(evento);
      }
      public void mouseDragged(MouseEvent evento){}
    });
    jButtonBuscar.setToolTipText("Path carpeta donde estan los problemas implementados");
    jButtonBuscar.setIcon(new ImageIcon("recursos/buscar.png"));
    jButtonBuscar.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
	jButtonBuscar_actionPerformed(e);
      }
    });
    restriccionesFactorExplotacionInicial.addChangeListener(new ChangeListener(){
      public void stateChanged(ChangeEvent evento){
	if (aco!=null){
	  aco.setFactorExplotacion(((Double)((SpinnerNumberModel)evento.getSource()).getValue()).doubleValue());
	}
      }
    });

    jPanelParametrosFinalizacion.setOpaque(false);
    jLabelPathProblemas.setText("Path problemas");
    jTextFieldRutaProblemas.setBackground(new Color(237, 234, 232));
    jTextFieldRutaProblemas.setText(rutaProblemas);
    jCheckBoxActualizarExplotacion.setFont(new java.awt.Font("Dialog", Font.BOLD, 12));
    jCheckBoxActualizarExplotacion.setToolTipText("Disminucir dinamicamente la inercia durante la simulacion");
    jLabelValoracionObtener.setToolTipText("Coste que se desea obtener");
    jButtonIniciar.addActionListener(new InterfazGrafica_jButtonIniciar_actionAdapter(this));
    jComboBoxProblema.addActionListener(new InterfazGrafica_jComboBoxProblema_actionAdapter(this));
    jComboBoxProblema.setBackground(new Color(215, 224, 244));
    jButtonIniciar.setText("Iniciar");
    jLabelIteraccionesMax.setText("Iteraciones Max");
    jLabelValoracionObtener.setText("Coste minimizado");
    jLabelFactorExplotacionInicial.setToolTipText("Probabilidad de realizad explotacion frente a exploracion, puede " + "ir cambiando dinamicamente si asi se desea");
    jLabelFactorExplotacionInicial.setText("Fact. Explotacion");
    jLabelFactorEvaporacionGlobal.setToolTipText("Factor de evaporacion del reforzamiento que se da al mejor camino " + "encontrado hasta el momento");
    jLabelFactorEvaporacionGlobal.setText("Fact. Evap. Global");
    jButtonCrearProblema.setIcon(new ImageIcon("recursos/mas.gif"));
    jButtonAyuda.setIcon(new ImageIcon("recursos/ayuda.png"));
    jButtonGrafica.setIcon(new ImageIcon("recursos/tonecurve.gif"));
    jLabelFactorEvaporacionLocal.setToolTipText("Factor de evaporacion de la feromona que va dejando cada hormiga");
    jLabelNumHormigas.setText("Num. Hormigas");
    jCheckBoxActualizarExplotacion.setOpaque(false);
    jCheckBoxActualizarExplotacion.setSelected(true);
    jCheckBoxActualizarExplotacion.setText("Factor explotacion dinamico");
    jCheckBoxActualizarExplotacion.addActionListener(new InterfazGrafica_jCheckBoxActualizarExplotacion_actionAdapter(this));
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
    jButtonParar.setMaximumSize(new Dimension(63, 24));
    jButtonParar.setMinimumSize(new Dimension(63, 24));
    jButtonParar.setText("Abortar");
    jButtonParar.addActionListener(new InterfazGrafica_jButtonParar_actionAdapter(this));
    jPanelParametrosConfiguracion.add(jLabelNumHormigas, new XYConstraints(6, 16, -1, -1));
    jPanelParametrosConfiguracion.add(jLabel1, new XYConstraints(6, 48, -1, -1));
    jPanelParametrosConfiguracion.add(jLabelFactorExplotacionInicial, new XYConstraints(6, 152, -1, -1));
    jPanelVisualizarResultados.add(jLabel8, new XYConstraints(6, 7, -1, -1));
    panelGrafica.add(jButtonGrafica, new PaneConstraints("jButtonGrafica", "jButtonGrafica", PaneConstraints.ROOT, 0.5F));
    this.getContentPane().add(jButtonIniciar, new XYConstraints(33, 403, -1, -1));
    jPanelVisualizarResultados.add(jComboBoxTipoSalidaResultados, new XYConstraints(145, 1, 145, -1));
    jPanelParametrosFinalizacion.add(jLabelIteraccionesMax, new XYConstraints(8, 63, -1, -1));
    jPanelParametrosFinalizacion.add(jLabelValoracionObtener, new XYConstraints(7, 24, -1, -1));
    jPanelSeleccionProblema.add(jLabel7, new XYConstraints(14, 27, -1, -1));
    jPanelSeleccionProblema.add(jComboBoxProblema, new XYConstraints(14, 48, 227, 21));
    jPanelSeleccionProblema.add(jButtonCrearProblema, new XYConstraints(266, 39, 31, 30));
    jPanelSeleccionProblema.add(jButtonAyuda, new XYConstraints(307, 38, 38, 31));
    jPanelSeleccionProblema.add(jButtonBuscar, new XYConstraints(383, 0, 28, 23));
    jPanelSeleccionProblema.add(jTextFieldRutaProblemas, new XYConstraints(110, 1, 267, -1));
    jPanelSeleccionProblema.add(jLabelPathProblemas, new XYConstraints(13, 6, 94, -1));


    jPanelParametrosConfiguracion.add(jLabelFactorEvaporacionLocal, new XYConstraints(6, 82, -1, -1));
    jPanelParametrosConfiguracion.add(jLabelFactorEvaporacionGlobal, new XYConstraints(6, 114, -1, -1));
    jPanelParametrosConfiguracion.add(jTextFieldCosteInicialAproxmimado, new XYConstraints(135, 44, 53, -1));
    jPanelParametrosConfiguracion.add(jTextFieldFactorEvaporacionLocal, new XYConstraints(135, 78, 53, -1));
    jPanelParametrosConfiguracion.add(jTextFieldFactorEvaporacionGlobal, new XYConstraints(135, 110, 53, -1));
    jPanelParametrosConfiguracion.add(jTextFieldFactorExplotacionInicial, new XYConstraints(135, 148, 53, -1));
    jPanelParametrosConfiguracion.add(jTextFieldNumHormigas, new XYConstraints(135, 12, 53, -1));
    jPanelParametrosConfiguracion.add(jCheckBoxActualizarExplotacion, new XYConstraints(9, 183, -1, -1));
    jPanelParametrosFinalizacion.add(jTextFieldIteracionesMax, new XYConstraints(118, 57, 70, -1));
    jPanelParametrosFinalizacion.add(jTextFieldCosteAObtener, new XYConstraints(118, 18, 70, -1));

    this.getContentPane().add(jPanelParametrosFinalizacion, new XYConstraints(3, 260, 216, 117));
    this.getContentPane().add(jPanelParametrosConfiguracion, new XYConstraints(3, 6, 216, 249));
    this.getContentPane().add(jPanelSeleccionProblema, new XYConstraints(222, 6, 422, 101));
    this.getContentPane().add(jPanelVisualizarResultados, new XYConstraints(222, 112, 422, 330));

    jPanelVisualizarResultados.add(panelGrafica, new XYConstraints(4, 35, 407, 267));
    this.getContentPane().add(jButtonParar, new XYConstraints(112, 403, 82, -1));
    jLabelFactorEvaporacionLocal.setText("Fact. Evap. Local");
    //setDefaultCloseOperation(EXIT_ON_CLOSE);
    anyadirProblemasImplementados();
  }


  //____________________________________ESCUCHADORES DE EVENTOS_____________________________________________________

  /**
   * AnyADE LOS PROBLEMAS IMPLEMENTADOS
   * Anyade al combobox de problemas los problemas que estan implementados y compilados
   */
  public void anyadirProblemasImplementados(){
    try{
      java.io.File fich = new java.io.File(rutaProblemas);
      borrandoProblemas = true;
      jComboBoxProblema.removeAllItems();
      borrandoProblemas = false;
      String[] problemas = fich.list();
      for (int i = 0; i < problemas.length; i++){
	//buscamos solo los fichero .class
	if (problemas[i].substring(problemas[i].indexOf('.'),problemas[i].length()).equalsIgnoreCase(".class")){
	  //nos quedamos con el nombre de la clase sin la extension
	  problemas[i] = problemas[i].substring(0, problemas[i].indexOf('.'));
	  jComboBoxProblema.addItem(problemas[i]);
	}
      }
    }catch (Exception e){ System.out.println("Se ha producido un fallo al intentar acceder a los problemas implementados: "+e.getMessage());}
  }

  /**
   *  EJECUTAR SIMULACION
   *  Cuando se pulsa el boton de iniciar se lanza la ejecucion
   */
  public void jButtonIniciar_actionPerformed(ActionEvent e) {

    try{
      final int numHormigas= ((Integer) jTextFieldNumHormigas.getValue()).intValue();
      final long iteraccionesMax = ((Long)jTextFieldIteracionesMax.getValue()).longValue();
      final double costeObtener = ((Double) jTextFieldCosteAObtener.getValue()).doubleValue();
      final double factorEvaporacionLocal =  ((Double) jTextFieldFactorEvaporacionLocal.getValue()).doubleValue();
      final double factorEvaporacionGlobal =  ((Double) jTextFieldFactorEvaporacionGlobal.getValue()).doubleValue();
      final double factorExplotacionInicial = ((Double) jTextFieldFactorExplotacionInicial.getValue()).doubleValue();
      final double costeAproximadoTrayecto = ((Double) jTextFieldCosteInicialAproxmimado.getValue()).doubleValue();
      final boolean factorExplotacionDinamico = jCheckBoxActualizarExplotacion.isSelected();

      aco = new OptimizadorColoniaHormigas(numHormigas,problema);

      if (jComboBoxTipoSalidaResultados.getSelectedItem().equals("Grafica")){
	grafica = new Grafica(aco,panelGrafica,iteraccionesMax,costeAproximadoTrayecto);
	new Thread(){
	  public void run(){
	    grafica.arrancarGrafica();
	    cambiarEstadoParametrosNoActualizables(false);
	    int[] trayectoSolucion = aco.optimizacionSecuencial(iteraccionesMax, costeObtener, factorEvaporacionLocal, factorEvaporacionGlobal, factorExplotacionInicial, factorExplotacionDinamico, costeAproximadoTrayecto,false);
	    cambiarEstadoParametrosNoActualizables(true);
	    String resultado="";
	    resultado = " Coste total del trayecto mejor: " + aco.getCosteTrayectoMejor()+"\n";
	    for (int i = 0; i < trayectoSolucion.length; i++) {
	      resultado +=  "Posicion visitada: " + trayectoSolucion[i]+"\n";
	    }
	    javax.swing.JOptionPane.showMessageDialog(null,resultado,"Resultados",1);
	    grafica.parar();
	    grafica = null;
	  }
	}.start();
      }else{
	new Thread(){
	  public void run() {
	    cambiarEstadoParametrosNoActualizables(false);
	    int[] trayectoSolucion = aco.optimizacionSecuencial(iteraccionesMax, costeObtener, factorEvaporacionLocal, factorEvaporacionGlobal, factorExplotacionInicial, factorExplotacionDinamico, costeAproximadoTrayecto,true);
	    cambiarEstadoParametrosNoActualizables(true);
	    System.out.println("Coste total del trayecto mejor: " + aco.getCosteTrayectoMejor());
	    for (int i = 0; i < trayectoSolucion.length; i++) {
	      System.out.println("Posicion visitada: " + trayectoSolucion[i]);
	    }
	  }
	}.start();
      }
    }catch (Exception error){
      cambiarEstadoParametrosNoActualizables(true);
      JOptionPane.showMessageDialog(this,"Alguno/s de los parametros es incorrecto "+ error.getMessage());
      error.printStackTrace();
    }
  }


  /**
   * INSTANCIACIoN DEL PROBLEMA SELECCIONADO
   * Cuando seleccionamos un problema
   */
  public void jComboBoxProblema_actionPerformed(ActionEvent e) {

    if (borrandoProblemas) return;

    String problemaSeleccionado = (((JComboBox) e.getSource()).getSelectedItem().toString());
    try{

      //es un problema interno (no necesista ser cargada la clase pues ya se carga con el proyecto)
      if (rutaProblemas.equalsIgnoreCase(System.getProperty("user.dir")+"/classes/ColoniaHormigas/Problemas")){  //es un problema del propio simulador
	//instanciamos el problema seleccionado
	problema = Problema.instanciarProblema("ColoniaHormigas.Problemas." + problemaSeleccionado);

      //es un problema externo (necesita ser cargada la clase externa al proyecto)
      }else{
	//cargamos las clases
	URL listaURLs[] = { new java.io.File(rutaProblemas+"/").toURL()};
	ClassLoader cargador = new URLClassLoader (listaURLs);

	//las instanciamos
	Class claseProblema = cargador.loadClass(problemaSeleccionado);
	problema = ((Problema)claseProblema.newInstance());
      }

      jTextFieldNumHormigas.setValue(new Integer(problema.valorDefectoNumeroHormigas()));
      jTextFieldCosteInicialAproxmimado.setValue(new Double(problema.valorDefectoCosteAproximadoTrayecto()));
      jTextFieldFactorEvaporacionLocal.setValue(new Double(problema.valorDefectoFactorEvaporacionLocal()));
      jTextFieldFactorEvaporacionGlobal.setValue(new Double(problema.valorDefectoFactorEvaporacionGlobal()));
      jTextFieldFactorExplotacionInicial.setValue(new Double(problema.valorDefectoFactorExplotacionInicial()));
      jTextFieldCosteAObtener.setValue(new Double(problema.valorDefectoCosteAObtener()));
      jTextFieldIteracionesMax.setValue(new Long(problema.valorDefectoIteracionesMax()));

    }catch (Exception error){
     jComboBoxProblema.removeItem(problemaSeleccionado);
     System.out.println("No se puede instanciar el problema "+problemaSeleccionado  +", seguramente porque necesite recibir arguemtnos el constructor del problema");
    }
  }


  /**
   * CREAR UN NUEVO PROBLEMA A SOLUCIONAR
   *  Evento del boton que permite crear un nuevo problema y anyadirlo a los problemas implementados
   */
  public void botonCrearProblema_actionPerformed(ActionEvent e) {
    try{
      String nombreProblema;
      if (jTextFieldRutaProblemas.getText().equalsIgnoreCase(System.getProperty("user.dir")+"/classes/ColoniaHormigas/Problemas")){
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

  /** PARA LA EJECUCION DE LA SIMULACION */
  public void jButtonParar_actionPerformed(ActionEvent e) {
    aco.abortarSimulacion();
    if (grafica != null){
      grafica.parar();
      //JOptionPane.showMessageDialog(null,"Mejor trayecto encontrado: " + aco.getCosteTrayectoMejor()+"\nTrayecto: " +  aco.getStringMejorTrayectoActual(),"Resultado simulacion",JOptionPane.INFORMATION_MESSAGE);
    }else{
      System.out.println("\n\nMejor trayecto encontrado: " + aco.getCosteTrayectoMejor()+"\nTrayecto: " +  aco.getStringMejorTrayectoActual());
    }
    repaint();
  }



  /** Cuando se pulsa el boton ayuda */
  public void jButton3_actionPerformed(ActionEvent e) {
    if (problema != null){
       JOptionPane.showMessageDialog(this,problema.ayuda(),"Informacion sobre el problema seleccionado",JOptionPane.INFORMATION_MESSAGE);
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
      double valoracionObtener = ((Double) jTextFieldCosteInicialAproxmimado.getValue()).doubleValue();
      double valorMaxGrafica = valoracionObtener;
      long iteracionesMax = ((Long) jTextFieldIteracionesMax.getValue()).longValue();
      double graphX = Math.round((x * iteracionesMax) / (anchuraPanelGrafica - 40));
      double graphY = Math.round((y * valorMaxGrafica) / (alturaPanelGrafica - 40));
       panelGrafica.setToolTipText(" Iteraciones:" + Math.round(graphX) + " Coste:" + graphY);
    } catch (Exception e) {}


    /*try {
      int alturaPanelGrafica = panelGrafica.getHeight();
      int anchuraPanelGrafica = panelGrafica.getWidth();
      int x = evento.getX() - 20;
      int y = evento.getY() - alturaPanelGrafica + 20;
      y = -y;
      double valoracionObtener = ((Double)jTextFieldCosteInicialAproxmimado.getValue()).doubleValue();
      double valorMaxGrafica = valoracionObtener + (valoracionObtener * 0.3);
      long iteraccionesMax = ((Long)jTextFieldIteracionesMax.getValue()).longValue();
      double graphX = Math.round((x * iteraccionesMax) / (anchuraPanelGrafica - 40));
      double graphY = Math.round((y * valorMaxGrafica) / (alturaPanelGrafica - 40));
      panelGrafica.setToolTipText(" Iteraciones:" + Math.round(graphX) + " Coste:" + graphY);
    }catch (Exception e){}*/
  }


  /**
   * Permite activar o desactivar los parametros no reconfigurables de la interface grafica durante la ejecucion
   * @param activar Si esta true se activaran , si False se desactivaran
   */
  public void cambiarEstadoParametrosNoActualizables (boolean activar){
    jTextFieldFactorEvaporacionLocal.setEnabled(activar);
    jTextFieldFactorEvaporacionGlobal.setEnabled(activar);
    jTextFieldIteracionesMax.setEnabled(activar);
    jTextFieldNumHormigas.setEnabled(activar);
    jTextFieldCosteInicialAproxmimado.setEnabled(activar);
    jTextFieldCosteAObtener.setEnabled(activar);
    //jTextFieldFactorExplotacionInicial.setEnabled(activar);
    //jCheckBoxActualizarExplotacion.setEnabled(activar);
    jButtonIniciar.setEnabled(activar);
  }

  /** Establece si es dinamico o no el factor de explotacion */
  public void jCheckBoxActualizarExplotacion_actionPerformed(ActionEvent e) {
    if (aco!=null){
      aco.setFactorExplotacionDinamico(((JCheckBox)e.getSource()).isSelected());
    }
  }
}

class InterfazGrafica_jCheckBoxActualizarExplotacion_actionAdapter implements ActionListener {
  private InterfazGrafica adaptee;
  InterfazGrafica_jCheckBoxActualizarExplotacion_actionAdapter(InterfazGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxActualizarExplotacion_actionPerformed(e);
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

class InterfazGrafica_jButtonIniciar_actionAdapter implements ActionListener {
  private InterfazGrafica adaptee;
  InterfazGrafica_jButtonIniciar_actionAdapter(InterfazGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonIniciar_actionPerformed(e);
  }
}


class InterfazGrafica_jButtonParar_actionAdapter implements ActionListener {
  private InterfazGrafica adaptee;
  InterfazGrafica_jButtonParar_actionAdapter(InterfazGrafica adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonParar_actionPerformed(e);
  }
}

/*--CLASES ESCUCHADORAS DEL MENO CONTEXTUAL QUE SE DESPLIGA PULSANDO EN EL BOTON DERECHO DEL RATON--- */
class InterfaceGrafica_CambiarLoockFeel_actionAdapter implements ActionListener{
  private InterfazGrafica adaptee;
   InterfaceGrafica_CambiarLoockFeel_actionAdapter(InterfazGrafica adaptee) {
     this.adaptee = adaptee;
   }
   public void actionPerformed(ActionEvent e) {
     adaptee.cambiarLoockFeel();
  }
}


class InterfaceGrafica_ejecutarGC_actionAdapter implements ActionListener{
  private InterfazGrafica adaptee;
  InterfaceGrafica_ejecutarGC_actionAdapter(InterfazGrafica adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    System.gc();
  }
}

class InterfaceGrafica_verInformacionMemoria_actionAdapter implements ActionListener{
  private InterfazGrafica adaptee;
  InterfaceGrafica_verInformacionMemoria_actionAdapter(InterfazGrafica adaptee) {
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
  private InterfazGrafica adaptee;
  InterfaceGrafica_cambiarImagenFondo_actionAdapter(InterfazGrafica adaptee) {
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

