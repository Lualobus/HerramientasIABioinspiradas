import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.borland.jbcl.layout.*;
import fam.utilidades.MiJPanel;


public class HerramientasIA extends JFrame implements ActionListener{

    MiJPanel fondo = new MiJPanel("./recursos/fondoIA.png");

  /** Ejecucion de la aplicacion en modo grafico */
  public static void main(String args[]){
    new HerramientasIA();
  }

  public HerramientasIA() {
    try {
      jbInit();
      cambiarLoockFeel();
      setVisible(true);
      pack();
    }catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Crea la ventana */
  private void jbInit() throws Exception {

    setContentPane(fondo);

    //comprobamos que al menos la version de JAVA es la 1.5
    try {
      String JVMversion = System.getProperty("java.version");
      java.util.StringTokenizer st = new java.util.StringTokenizer(JVMversion, ".");
      st.nextToken();
      int version = Integer.parseInt(st.nextToken());
     /*if (version < 5) {
	javax.swing.JOptionPane.showMessageDialog(this, "Es necesario al menos la version 1.5 de Java para poder compilar y ejecutar la aplicacion ");
	System.exit( -1);
      }*/
    }catch (Exception error) {}

    //creamos el menu
    menuAyuda = new JMenu("Ayuda");
    xYLayout1.setWidth(771);
    jButtonTempleSimulado.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evento){
	new TempleSimulado.InterfaceGrafica.InterfazGrafica();
      }
    });

    menu.add(menuAyuda);
    documentacion = new JMenuItem("Documentacion del codigo (API)");
    documentacion.addActionListener(this);
    menuAyuda.add(documentacion);
    manualRef = new JMenuItem("Manual de referencia");
    menuAyuda.add(manualRef);
    manualRef.addActionListener(this);
    acercaDe = new JMenuItem("Acerca de ...");
    menuAyuda.add(acercaDe);
    acercaDe.addActionListener(this);
    this.setJMenuBar(menu);

    //ponemos el titulo y el layout a la ventana
    this.getContentPane().setLayout(xYLayout1);
    this.setResizable(false);
    this.setTitle("HerramientasIA v1.0   Universidad Carlos III Madrid");

    //creamos los botones y la ayuda de las aplicaciones
    jButtonSimuladorBiologico.setText("SimuladorBiologico");
    jButtonSimuladorBiologico.setToolTipText("Simulador que usa para solucionar los problemas Algoritmos Geneticos");
    jButtonSimuladorBiologico.setIcon(new ImageIcon("./recursos/biologico.png"));
    //jButtonSimuladorBiologico.setIcon(new ImageIcon("./recursos/botonSimuladorBiologico.png"));
    jButtonSimuladorBiologico.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evento){
	new simuladorbiologico.InterfazGrafica.InterfaceGrafica(null);
      }
    });

    jButtonEnjambreParticulas.setToolTipText("Simulador que usa para solucionar los problemas Optimizacion por " + "Enjambres de Particulas");
    jButtonEnjambreParticulas.setIcon(new ImageIcon("./recursos/enjambre.png"));
    jButtonEnjambreParticulas.setText("Enjambre de Particulas");
    jButtonEnjambreParticulas.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evento){
	new OptimizadorEnjambreParticulas.InterfaceGrafica.InterfazGrafica();
      }
    });
    xYLayout1.setHeight(521);
    jButtonRNA.setText("Red de Neuronas");
    jButtonRNA.setToolTipText("Implementacion de un modelo de Redes de Neuronas Artificiales");
    jButtonRNA.setIcon(new ImageIcon("./recursos/RedNeuronas.png"));
    jButtonRNA.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evento){
	new RNA.InterfazGrafica.InterfaceGrafica();
      }
    });

    jButtonTempleSimulado.setText("Temple Simulado");
    jButtonTempleSimulado.setToolTipText("Simulador que usa para solucionar los problemas el metodo del Templado " + "Simulado (Simulated Annealing)");
    jButtonTempleSimulado.setIcon(new ImageIcon("./recursos/Templado.png"));
    jButtonColoniaHormigas.setText("Colonia Hormigas");
    jButtonColoniaHormigas.setToolTipText("Simulador que usa para solucionar los problemas Optimizacion por " + "Colonia de Hormigas");
    jButtonColoniaHormigas.setIcon(new ImageIcon("./recursos/Hormiga.png"));
    jButtonColoniaHormigas.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent evento){
	new ColoniaHormigas.InterfaceGrafica.InterfazGrafica();
      }
    });
    this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
    this.getContentPane().add(jButtonRNA, new XYConstraints(6, 428, 180, 49));
    this.getContentPane().add(jButtonTempleSimulado, new XYConstraints(6, 333, 180, 49));
    this.getContentPane().add(jButtonColoniaHormigas, new XYConstraints(6, 232, 180, 49));
    this.getContentPane().add(jButtonSimuladorBiologico, new XYConstraints(6, 35, 180, 49));
    this.getContentPane().add(jButtonEnjambreParticulas, new XYConstraints(7, 131, 180, 49));

  }


  /**
   * Cambia el loock & feel
   */
  public void cambiarLoockFeel() {
    try {
      String lookSO = UIManager.getSystemLookAndFeelClassName();
      String lookJava = UIManager.getCrossPlatformLookAndFeelClassName();
      String lookActual = UIManager.getLookAndFeel().getName();
      if (lookActual == "Metal") {
	UIManager.setLookAndFeel(lookSO);
      }else{
	UIManager.setLookAndFeel(lookJava);
      }
      SwingUtilities.updateComponentTreeUI(this);
    }
    catch (Exception error) {}
  }

  private XYLayout xYLayout1 = new XYLayout();
  private JButton jButtonSimuladorBiologico = new JButton();
  private JButton jButtonRNA = new JButton();
  private JButton jButtonEnjambreParticulas = new JButton();
  private JButton jButtonTempleSimulado = new JButton();
  private JButton jButtonColoniaHormigas = new JButton();
  private JMenuBar menu = new JMenuBar();
  private JMenu menuAyuda;
  private JMenuItem  documentacion;
  private JMenuItem  manualRef;
  private JMenuItem acercaDe;

  /**
  * Escucha los eventos producidos en el menu
  */
 public void actionPerformed(ActionEvent evento){
    //abre la documentacion del codigo
   if (evento.getActionCommand().equalsIgnoreCase("Documentacion del codigo (API)")){
     String ruta = "file://"+System.getProperty("user.dir")+"/doc/index.html";
     Ayuda.presentaURL(ruta);

   //abre el manual de la aplicacion
   }else if (evento.getActionCommand().equalsIgnoreCase("Manual de referencia")){
     String ruta = "file://"+System.getProperty("user.dir")+"/ManualRef.htm";
     Ayuda.presentaURL(ruta);
   //abre la ventana de Acerca de...
   }else if ((evento.getActionCommand().equalsIgnoreCase("Acerca de ..."))){
     JFrame ventana = new JFrame();
     XYLayout xYLayout1 = new XYLayout();
     ventana.setLayout(xYLayout1);
     ventana.setTitle("Acerca de...");
     JButton jButtonLogo = new JButton();
     jButtonLogo.setBorder(BorderFactory.createEmptyBorder());
     jButtonLogo.setIcon(new ImageIcon("./recursos/Fer.gif"));
     JLabel nombre = new JLabel();
     JLabel jLabelTitulacion = new JLabel();
     JLabel jLabelTitulo = new JLabel();
     JLabel jLabelUniversidad = new JLabel();
     JLabel jLabelAnyo = new JLabel();
     nombre.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 16));
     nombre.setText("Fernando Alonso Martin");
     this.getContentPane().setLayout(xYLayout1);
     jLabelTitulacion.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 14));
     jLabelTitulacion.setText("Ingeniero Informatica");
     jLabelTitulo.setFont(new java.awt.Font("Sylfaen", Font.PLAIN, 19));
     jLabelTitulo.setText("Herramientas IA Bioinspiradas");
     jLabelUniversidad.setFont(new java.awt.Font("Tahoma", Font.BOLD, 11));
     jLabelUniversidad.setText("Universidad Carlos III Madrid");
     xYLayout1.setHeight(288);
     xYLayout1.setWidth(370);
     jLabelAnyo.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 15));
     jLabelAnyo.setText("2006");
     ventana.add(jButtonLogo, new XYConstraints(116, 27, 115, 39));
     ventana.add(jLabelAnyo, new XYConstraints(158, 186, -1, -1));
     ventana.add(jLabelTitulo, new XYConstraints(63, 149, 255, 40));
     ventana.add(jLabelUniversidad, new XYConstraints(96, 129, -1, -1));
     ventana.add(jLabelTitulacion, new XYConstraints(112, 108, -1, -1));
     ventana.add(nombre, new XYConstraints(96, 78, 168, 30));
     ventana.pack();
     ventana.setVisible(true);
     ventana.setResizable(false);
   }
 }
}
