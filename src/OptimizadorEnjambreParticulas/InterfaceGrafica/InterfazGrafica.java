package OptimizadorEnjambreParticulas.InterfaceGrafica;

import java.awt.event.*;
import java.lang.management.ManagementFactory;
import javax.swing.*;
import OptimizadorEnjambreParticulas.*;
import com.borland.jbcl.layout.*;
import com.sun.management.OperatingSystemMXBean;
import javax.swing.border.TitledBorder;
import java.net.URL;
import java.net.URLClassLoader;
import java.awt.Color;
import java.awt.Font;
import com.jgoodies.looks.plastic.*;
import com.jgoodies.looks.windows.*;
import com.borland.plaf.borland.BorlandLookAndFeel;
import fam.utilidades.*;

public class InterfazGrafica extends JFrame{

  /** Metodo main */
  public static void main(String args[]){
    new InterfazGrafica();
  }

  private Problema problema;   //problema que esta seleccionado
  private String rutaProblemas = System.getProperty("user.dir")+"/classes/OptimizadorEnjambreParticulas/Problemas"; //ruta donde esta el problema implementado o que vamos a crear

  MiJPanel panelPrincipal = new MiJPanel(null);
  private XYLayout xYLayout1 = new XYLayout();
  private JLabel jLabel1 = new JLabel();
  private JLabel jLabel2 = new JLabel();
  private JLabel jLabel3 = new JLabel();
  private JLabel jLabelInercia = new JLabel();
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
  private JCheckBox jCheckBoxMostrarValoracionEnjambre = new JCheckBox();
  private JLabel jLabelNumDimensiones = new JLabel();
  private JCheckBox jCheckBoxActualizarInercia = new JCheckBox();
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
  private JRadioButton jRadioButtonSolucionEnjambre = new JRadioButton();
  private JRadioButton jRadioButtonSolucionParticula = new JRadioButton();
  private SpinnerNumberModel restriccionesNumDimensiones = new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1);
  private SpinnerNumberModel restriccionesNumParticulas = new SpinnerNumberModel(1,1,Integer.MAX_VALUE,1);
  private SpinnerNumberModel restriccionesAtraccion1 = new SpinnerNumberModel(1D,0D,Double.MAX_VALUE,0.05D);
  private SpinnerNumberModel restriccionesAtraccion2 = new SpinnerNumberModel(1D,0D,Double.MAX_VALUE,0.05D);
  private SpinnerNumberModel restriccionesInercia = new SpinnerNumberModel(1D,0D,Double.MAX_VALUE,0.05D);
  private SpinnerNumberModel restriccionesValoracionObtener = new SpinnerNumberModel(1D,0D,Double.MAX_VALUE,1D);
  private SpinnerNumberModel restriccionesIteraccionesMax = new SpinnerNumberModel(new Long(1),new Long(1),new Long(Long.MAX_VALUE),new Long(1));
  private JSpinner jTextFieldNumDimensiones = new JSpinner(restriccionesNumDimensiones);
  private JSpinner jTextFieldNumParticulas = new JSpinner(restriccionesNumParticulas);
  private JSpinner jTextFieldAtraccion1 = new JSpinner(restriccionesAtraccion1);
  private JSpinner jTextFieldAtraccion2 = new JSpinner(restriccionesAtraccion2);
  private JSpinner jTextFieldInercia = new JSpinner(restriccionesInercia);
  private JSpinner jTextFieldValoracionObtener = new JSpinner(restriccionesValoracionObtener);
  private JSpinner jTextFieldIteraccionesMax = new JSpinner(restriccionesIteraccionesMax);
  private JTextFieldDroppable jTextFieldRutaProblemas = new JTextFieldDroppable(rutaProblemas);
  private JLabel jLabelPathProblemas = new JLabel();
  private JButton jButtonBuscar = new JButton();
  private boolean borrandoProblemas = false;
  private Grafica grafica;

  public InterfazGrafica() {
    try {
      jbInit();
      //cambiarLoockFeel();
      panelPrincipal.cambiarImagenFondo("skins/skinEnjambreParticulas.png");
      setVisible(true);
      pack();
    }catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    setContentPane(panelPrincipal);
    this.setResizable(false);
    setTitle("Optimizador de Enjambres de Particulas (PSO)");
    this.addMouseListener(new InterfazGrafica_this_mouseAdapter(this));
    this.getContentPane().setLayout(xYLayout1);
    jLabel1.setText("N.Particulas");
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
    jRadioButtonSolucionEnjambre.setOpaque(false);
    jRadioButtonSolucionEnjambre.setText("Enjambre");
    jRadioButtonSolucionParticula.setOpaque(false);
    jRadioButtonSolucionParticula.setSelected(true);
    jRadioButtonSolucionParticula.setText("Particula");
    jPanelParametrosFinalizacion.setOpaque(false);
    jLabelPathProblemas.setText("Path problemas");
    jTextFieldRutaProblemas.setBackground(new Color(237, 234, 232));
    jCheckBoxActualizarInercia.setFont(new java.awt.Font("Dialog", Font.BOLD, 12));
    jCheckBoxActualizarInercia.setToolTipText("Disminucir dinamicamente la inercia durante la simulacion");
    grupoRadioButton.add(jRadioButtonSolucionEnjambre);
    grupoRadioButton.add(jRadioButtonSolucionParticula);
    jButtonIniciar.addActionListener(new InterfazGrafica_jButton1_actionAdapter(this));
    jComboBoxProblema.addActionListener(new InterfazGrafica_jComboBoxProblema_actionAdapter(this));
    jComboBoxProblema.setBackground(new Color(215, 224, 244));
    jButtonIniciar.setText("Iniciar");
    jLabelIteraccionesMax.setText("Iteraciones Max");
    jLabelValoracionObtener.setText("Valoracion obtener");
    jLabelInercia.setToolTipText("Entre 0 y 1, cuanto mas proximo a 1, una vez que empieza a subir, " + "sube con mayor rapidez (mas pendiente en la grafica)");
    jLabelInercia.setText("Inercia");
    jLabel3.setToolTipText("Indica como es atraida la particula, por la posicion de la mejor particula del enjambre.Valores entre 0 y 1, cuanto mas proximo a 1 mas explotacion");
    jLabel3.setText("Cte atraccion 2");
    jButtonCrearProblema.setIcon(new ImageIcon("recursos/mas.gif"));
    jButtonAyuda.setIcon(new ImageIcon("recursos/ayuda.png"));
    jButtonGrafica.setIcon(new ImageIcon("recursos/tonecurve.gif"));
    jCheckBoxMostrarValoracionEnjambre.setOpaque(false);
    jCheckBoxMostrarValoracionEnjambre.setSelected(true);
    jCheckBoxMostrarValoracionEnjambre.setText("mostrar valoracion total del enjambre");
    jLabel2.setToolTipText("Indica como es atraida la particula, por la posicion mejor alcanzada hasta el momento por la propia particula. Valores entre 0 y 1, cuanto mas proximo a 1 mas explotacion");
    jLabelNumDimensiones.setText("N.Dimensiones");
    jCheckBoxActualizarInercia.setOpaque(false);
    jCheckBoxActualizarInercia.setSelected(true);
    jCheckBoxActualizarInercia.setText("Dec dinamicamente inercia");
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
    jPanelParametrosConfiguracion.add(jLabelNumDimensiones, new XYConstraints(6, 16, -1, -1));
    jPanelParametrosConfiguracion.add(jLabel1, new XYConstraints(6, 48, -1, -1));
    jPanelParametrosConfiguracion.add(jLabel2, new XYConstraints(6, 92, -1, -1));
    jPanelParametrosConfiguracion.add(jLabel3, new XYConstraints(6, 123, -1, -1));
    jPanelParametrosConfiguracion.add(jLabelInercia, new XYConstraints(6, 152, -1, -1));
    jPanelVisualizarResultados.add(jLabel8, new XYConstraints(6, 7, -1, -1));
    jPanelVisualizarResultados.add(jCheckBoxMostrarValoracionEnjambre, new XYConstraints(97, 29, -1, -1));
    jPanelVisualizarResultados.add(panelGrafica, new XYConstraints(4, 61, 407, 241));
    panelGrafica.add(jButtonGrafica, new PaneConstraints("jButtonGrafica", "jButtonGrafica", PaneConstraints.ROOT, 0.5F));
    jPanelVisualizarResultados.add(jComboBoxTipoSalidaResultados, new XYConstraints(145, 1, 145, -1));
    jPanelParametrosFinalizacion.add(jTextFieldIteraccionesMax, new XYConstraints(116, 57, 60, -1));
    jPanelParametrosFinalizacion.add(jLabelIteraccionesMax, new XYConstraints(8, 63, -1, -1));
    jPanelParametrosFinalizacion.add(jTextFieldValoracionObtener, new XYConstraints(115, 29, 60, -1));
    jPanelParametrosFinalizacion.add(jLabelValoracionObtener, new XYConstraints(7, 35, -1, -1));
    jPanelParametrosFinalizacion.add(jRadioButtonSolucionEnjambre, new XYConstraints(96, 0, 83, -1));
    jPanelParametrosFinalizacion.add(jRadioButtonSolucionParticula, new XYConstraints(10, 0, 80, -1));
    this.getContentPane().add(jButtonIniciar, new XYConstraints(75, 406, -1, -1));
    jPanelParametrosConfiguracion.add(jTextFieldInercia, new XYConstraints(100, 146, 70, -1));
    jPanelParametrosConfiguracion.add(jTextFieldAtraccion2, new XYConstraints(100, 117, 70, -1));
    jPanelParametrosConfiguracion.add(jTextFieldAtraccion1, new XYConstraints(100, 86, 70, -1));
    jPanelParametrosConfiguracion.add(jTextFieldNumParticulas, new XYConstraints(100, 42, 69, -1));
    jPanelParametrosConfiguracion.add(jTextFieldNumDimensiones, new XYConstraints(100, 10, 69, -1));
    this.getContentPane().add(jPanelParametrosFinalizacion, new XYConstraints(13, 260, 196, 117));
    jPanelSeleccionProblema.add(jLabel7, new XYConstraints(14, 27, -1, -1));
    jPanelSeleccionProblema.add(jComboBoxProblema, new XYConstraints(14, 48, 227, 21));
    jPanelSeleccionProblema.add(jButtonCrearProblema, new XYConstraints(266, 39, 31, 30));
    jPanelSeleccionProblema.add(jButtonAyuda, new XYConstraints(307, 38, 38, 31));
    jPanelSeleccionProblema.add(jButtonBuscar, new XYConstraints(383, 0, 28, 23));
    this.getContentPane().add(jPanelVisualizarResultados, new XYConstraints(216, 112, 424, 330));
    jPanelParametrosConfiguracion.add(jCheckBoxActualizarInercia, new XYConstraints(0, 186, -1, -1));
    jPanelSeleccionProblema.add(jTextFieldRutaProblemas, new XYConstraints(110, 1, 267, -1));
    jPanelSeleccionProblema.add(jLabelPathProblemas, new XYConstraints(13, 6, 94, -1));
    this.getContentPane().add(jPanelSeleccionProblema, new XYConstraints(216, 6, 424, 101));
    this.getContentPane().add(jPanelParametrosConfiguracion, new XYConstraints(13, 6, 194, 249));
    jLabel2.setText("Cte atraccion 1");
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
    Particula particulaMejor;
    try{
      int numDimensiones= ((Integer)jTextFieldNumDimensiones.getValue()).intValue();
      int numeroParticulas = ((Integer)jTextFieldNumParticulas.getValue()).intValue();
      double cteAtraccion1 = ((Double)jTextFieldAtraccion1.getValue()).doubleValue();
      double cteAtraccion2 = ((Double)jTextFieldAtraccion2.getValue()).doubleValue();
      double inercia = ((Double)jTextFieldInercia.getValue()).doubleValue();
      boolean irDisminuyendoInercia = jCheckBoxActualizarInercia.isSelected();
      double valoracionObtener = ((Double)jTextFieldValoracionObtener.getValue()).doubleValue();
      long iteraccionesMax = ((Long)jTextFieldIteraccionesMax.getValue()).longValue();

      Enjambre enjambre = new Enjambre(problema,numeroParticulas,numDimensiones,problema.valorDefectoVelocidadMax(),problema.valorDefectoPosicionMax());
      if (jComboBoxTipoSalidaResultados.getSelectedItem().equals("Grafica")){
	grafica = new Grafica(enjambre,panelGrafica,iteraccionesMax,valoracionObtener,jCheckBoxMostrarValoracionEnjambre.isSelected());
	grafica.arrancarGrafica();
	cambiarEstadoParametrosNoActualizables(false);
	particulaMejor = enjambre.pso(valoracionObtener,iteraccionesMax,false,cteAtraccion1,cteAtraccion2,inercia,irDisminuyendoInercia);
	cambiarEstadoParametrosNoActualizables(true);
	String linea = getEstadisticasSimulacion(particulaMejor, enjambre,problema);
	javax.swing.JOptionPane.showMessageDialog(null,linea,"Resultados",1);
	grafica.parar();
      }else{
	cambiarEstadoParametrosNoActualizables(false);
	particulaMejor = enjambre.pso(valoracionObtener,iteraccionesMax,true,cteAtraccion1,cteAtraccion2,inercia,irDisminuyendoInercia);
	String linea = getEstadisticasSimulacion(particulaMejor, enjambre,problema);
	System.out.println("\n\n"+linea+"\n");
	cambiarEstadoParametrosNoActualizables(true);
      }
      double valoracion = particulaMejor.getValoracion();
      System.out.println("Se ha obtenido una particula de valoracion " + valoracion);
      repaint();


    }catch (Exception error){
      cambiarEstadoParametrosNoActualizables(true);
      error.printStackTrace();
      JOptionPane.showMessageDialog(this,"Alguno/s de los parametros es incorrecto "+ error.getMessage());
    }
  }

  /** Devuelbe un string con los resultados de la simulacion para que puedan ser mostrados */
  private String getEstadisticasSimulacion(Particula particulaMejor,Enjambre enjambre, Problema problema) {
    int numParticulas = enjambre.getNumParticulas();
     String linea =
    "> Valoracion de la mejor particula: " + particulaMejor.getValoracion() +  "\n"+
    "> Interpretacion de la particula : " + problema.interpretracionParticula(particulaMejor)+"\n"+
    "> Valoracion total del enjambre: " + enjambre.getValoracionTotalEnjambre() + "\n" +
    "> Valoracion particula media del enjambre: " + (enjambre.getValoracionTotalEnjambre()/numParticulas) + "\n" +
    "> Nº de iteracciones: " + enjambre.getIteraccionesRealizadas() + "\n" +
    "> Nº total de particulas evaluadas:" + (numParticulas * enjambre.getIteraccionesRealizadas());
    return linea;
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
      if (rutaProblemas.equalsIgnoreCase(System.getProperty("user.dir")+"/classes/OptimizadorEnjambreParticulas/Problemas")){  //es un problema del propio simulador
	//instanciamos el problema seleccionado
	problema = Problema.instanciarProblema("OptimizadorEnjambreParticulas.Problemas." + problemaSeleccionado);
      //es un problema externo (necesita ser cargada la clase externa al proyecto)
      }else{
	//cargamos las clases
	URL listaURLs[] = { new java.io.File(rutaProblemas+"/").toURL()};
	ClassLoader cargador = new URLClassLoader (listaURLs);

	//las instanciamos
	Class claseProblema = cargador.loadClass(problemaSeleccionado);
	problema = ((Problema)claseProblema.newInstance());
      }

      jTextFieldAtraccion1.setValue(new Double(problema.valorDefectoCteAtraccion1()));
      jTextFieldAtraccion2.setValue(new Double(problema.valorDefectoCteAtraccion2()));
      jTextFieldInercia.setValue(new Double(problema.valorDefectoInercia()));
      jTextFieldNumParticulas.setValue(new Integer(problema.valorDefectoNumParticulas()));
      jTextFieldIteraccionesMax.setValue(new Long(problema.valorDefectoIteraccionesMax()));
      jTextFieldValoracionObtener.setValue(new Double(problema.valorDefectoValorObtener()));
      jTextFieldNumDimensiones.setValue(new Integer(problema.valorDefectoNumDimensiones()));
      jCheckBoxActualizarInercia.setSelected(problema.valorDefectoDisminucionProgresivaInercia());

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
      if (jTextFieldRutaProblemas.getText().equalsIgnoreCase(System.getProperty("user.dir")+"/classes/OptimizadorEnjambreParticulas/Problemas")){
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
      double valoracionObtener;
      if (problema.isMaximizeProblem()){
        valoracionObtener = ((Double)jTextFieldValoracionObtener.getValue()).doubleValue();
        valoracionObtener = valoracionObtener + (valoracionObtener * 0.3);
      }else{
        valoracionObtener = Math.round((grafica.valorMaxGrafica/10)*(10-0));
      }

      //double valorMaxGrafica = valoracionObtener + (valoracionObtener * 0.3);
      long iteraccionesMax = ((Long)jTextFieldIteraccionesMax.getValue()).longValue();
      double graphX = Math.round((x * iteraccionesMax) / (anchuraPanelGrafica - 40));
      double graphY = Math.round((y * valoracionObtener) / (alturaPanelGrafica - 40));
      panelGrafica.setToolTipText(" Iteraciones:" + Math.round(graphX) + " Fitness:" + graphY);
    }catch (Exception e){}
  }


  /**
   * Permite activar o desactivar los parametros no reconfigurables de la interface grafica durante la ejecucion
   * @param activar Si esta true se activaran , si False se desactivaran
   */
  public void cambiarEstadoParametrosNoActualizables (boolean activar){
    jTextFieldAtraccion1.setEnabled(activar);
    jTextFieldAtraccion2.setEnabled(activar);
    jTextFieldIteraccionesMax.setEnabled(activar);
    jTextFieldNumDimensiones.setEnabled(activar);
    jTextFieldNumParticulas.setEnabled(activar);
    jTextFieldValoracionObtener.setEnabled(activar);
    jTextFieldInercia.setEnabled(activar);
    jCheckBoxActualizarInercia.setEnabled(activar);
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

