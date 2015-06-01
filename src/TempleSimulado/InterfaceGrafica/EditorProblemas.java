package TempleSimulado.InterfaceGrafica;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.*;
import javax.swing.text.*;

/** Editor para modificar el codigo de los problemas con coloreado de codigo */
public class EditorProblemas extends JFrame implements ActionListener{

  private String nombreProblema;
  private boolean esProblema;
  private CreadorProblemasInternos creadorProbl;
  private BorderLayout borderLayout1 = new BorderLayout();
  private JScrollPane scrollbar = new JScrollPane();
  private JLabel jLabelRuta = new JLabel();
  private JTextPane editor;
  private int tamanyoFuente=12;
  private JPanel jPanelBotones = new JPanel();
  private JButton jButtonCompilar = new JButton();
  private JButton jButtonAmpliar = new JButton();
  private JButton jButtonReducir = new JButton();
  private FlowLayout flowLayout1 = new FlowLayout();

  public EditorProblemas(String nombreProblema, boolean esProblema, CreadorProblemasInternos creadorProbl) {
    this.nombreProblema = nombreProblema;
    this.esProblema = esProblema;
    this.creadorProbl = creadorProbl;

    editor  = new JTextPane(){
          public void setSize(Dimension d){
            if (d.width < getParent().getSize().width){
              d.width = getParent().getSize().width;
            }
            super.setSize(d);
          }

          public boolean getScrollableTracksViewportWidth(){
            return false;
          }
    };
    try {
      jbInit();  //creamos la interfaz
      this.setVisible(true);
      this.pack();
      Thread th = new Thread( new Runnable () {  //creamos un thread que se encarga de ir resaltando el codigo
	public void run() {
	  while (true){
	    maquearCodigo();
	    try{Thread.sleep(500);}catch (InterruptedException error){}
	  }
	}
      });
      th.setPriority(Thread.MIN_PRIORITY);
      th.start();
    }catch (Exception ex) {}
  }

  /**
   * Crea la Interfaz Grafica
   */
  private void jbInit() throws Exception {
    this.getContentPane().setLayout(borderLayout1);

    if (esProblema){
      this.setTitle("Problema"+nombreProblema+".java");
      jLabelRuta.setText("Ruta del archivo: ./src/TempleSimulado/Problemas/" + nombreProblema+"/Problema"+nombreProblema+".java");
      editor.setText(CodigoBase.getCodigoProblema(nombreProblema));
      maquearCodigo();
    }else{
      this.setTitle("Estado"+nombreProblema+".java");
      jLabelRuta.setText("Ruta del archivo: ./src/TempleSimulado/Problemas/" + nombreProblema+"/Estado"+nombreProblema+".java");
      editor.setText(CodigoBase.getCodigoEstado(nombreProblema));
      maquearCodigo();
    }
    jLabelRuta.setBackground(UIManager.getColor("TabbedPane.selectHighlight"));
    jLabelRuta.setDisplayedMnemonic('0');

    jButtonCompilar.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 10));
    jButtonCompilar.setBorder(BorderFactory.createRaisedBevelBorder());
    jButtonCompilar.setPreferredSize(new Dimension(50, 20));
    jButtonCompilar.setToolTipText("Compila y guarda el fichero");
    jButtonCompilar.setMargin(new Insets(2, 2, 2, 2));
    jButtonCompilar.setText("Compilar");
    jButtonCompilar.addActionListener(this);
    jButtonAmpliar.setBorder(BorderFactory.createLoweredBevelBorder());
    jButtonAmpliar.setPreferredSize(new Dimension(20, 20));
    jButtonAmpliar.setToolTipText("Reduce el tamanyo de la fuente");
    jButtonAmpliar.setMargin(new Insets(2, 2, 2, 2));
    jButtonAmpliar.setText("-");
    jButtonAmpliar.addActionListener(this);
    jButtonReducir.setBorder(BorderFactory.createLoweredBevelBorder());
    jButtonReducir.setPreferredSize(new Dimension(20, 20));
    jButtonReducir.setToolTipText("Amplia la fuente");
    jButtonReducir.setMargin(new Insets(2, 2, 2, 2));
    jButtonReducir.setText("+");
    jButtonReducir.addActionListener(this);
    jPanelBotones.setAlignmentX((float) 0.2);
    jPanelBotones.setAlignmentY((float) 0.2);
    jPanelBotones.setBorder(BorderFactory.createRaisedBevelBorder());
    jPanelBotones.setMinimumSize(new Dimension(88, 27));
    jPanelBotones.setPreferredSize(new Dimension(114, 32));
    jPanelBotones.setLayout(flowLayout1);

    this.setSize(640,480);
    flowLayout1.setAlignment(FlowLayout.LEFT);
    scrollbar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    scrollbar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollbar.getViewport().add(editor);
    this.getContentPane().add(scrollbar, java.awt.BorderLayout.CENTER);
    jPanelBotones.add(jButtonCompilar, java.awt.BorderLayout.CENTER);
    jPanelBotones.add(jButtonAmpliar);
    jPanelBotones.add(jButtonReducir);
    jPanelBotones.add(jLabelRuta);
    this.getContentPane().add(jPanelBotones, java.awt.BorderLayout.NORTH);
  }

  /** Para que quede el codigo coloreado */
  private void maquearCodigo(){
    try{
      StyledDocument estilo = editor.getStyledDocument();
      MutableAttributeSet estiloComentario = new SimpleAttributeSet();
      MutableAttributeSet estiloString = new SimpleAttributeSet();
      MutableAttributeSet estiloPalabraReservada = new SimpleAttributeSet();
      String codigo = editor.getText();

      //para determinar el tamanyo del texto
      editor.setFont(new java.awt.Font("DialogInput", Font.PLAIN, 12));
      MutableAttributeSet estiloTamanyoFuente = new SimpleAttributeSet();
      StyleConstants.setFontSize(estiloTamanyoFuente,tamanyoFuente);
      estilo.setCharacterAttributes(0,codigo.length(),estiloTamanyoFuente,false);


      //resaltamos los comenarios
      StyleConstants.setForeground(estiloComentario,new Color(0.01568F,0.4823F,0.12156F));
      int posIni=0,posFin = 0;
      String caracterInicio = "/*";
      String caracterFin = "*/";
      while ((posIni = codigo.indexOf(caracterInicio, posIni)) >= 0) {
	  posFin = codigo.indexOf(caracterFin,posIni)+2;
	  estilo.setCharacterAttributes(posIni,posFin-posIni,estiloComentario,false);
	  posIni += caracterInicio.length();
      }

      //resaltamos los Strings
      StyleConstants.setForeground(estiloString,Color.BLUE);
      posIni=0;posFin = -1;
      char caracter = '"';
      while ( (posIni = codigo.indexOf(caracter, posFin) )  >= 0 ){
	posFin = codigo.indexOf(caracter,posIni+1)+1;
	estilo.setCharacterAttributes(posIni,posFin-posIni,estiloString,false);
      }

      //resaltamos los comenarios de una linea
      posIni=0;posFin = 0;
      caracterInicio = "//";
      caracterFin = "\n";
      while ((posIni = codigo.indexOf(caracterInicio, posIni)) >= 0) {
	posFin = codigo.indexOf(caracterFin,posIni)+1;
	estilo.setCharacterAttributes(posIni,posFin-posIni,estiloComentario,false);
	posIni += caracterInicio.length();
      }

      //resaltamos las palabras reservadas
      Color azulOscuro = new Color(0.0392F,0.0078F,0.6078F);
      StyleConstants.setForeground(estiloPalabraReservada,azulOscuro);
      StyleConstants.setBold(estiloPalabraReservada,true);
      Vector palabrarReservadas = new Vector();
      palabrarReservadas.add("void");palabrarReservadas.add("class");palabrarReservadas.add("interface");
      palabrarReservadas.add("return");palabrarReservadas.add("public");palabrarReservadas.add("private");
      palabrarReservadas.add("synchronized");palabrarReservadas.add("new");palabrarReservadas.add("while");
      palabrarReservadas.add("do");palabrarReservadas.add("for");palabrarReservadas.add("if");
      palabrarReservadas.add("else");palabrarReservadas.add("select");palabrarReservadas.add("case");
      palabrarReservadas.add("int");palabrarReservadas.add("short");palabrarReservadas.add("byte");
      palabrarReservadas.add("long");palabrarReservadas.add("double");palabrarReservadas.add("float");
      palabrarReservadas.add("try");palabrarReservadas.add("catch");palabrarReservadas.add("this");
      palabrarReservadas.add("extends");palabrarReservadas.add("implements");palabrarReservadas.add("throws");
      palabrarReservadas.add("throw");palabrarReservadas.add("break");palabrarReservadas.add("continue");
      palabrarReservadas.add("true");palabrarReservadas.add("false");palabrarReservadas.add("import");
      palabrarReservadas.add("package"); palabrarReservadas.add("final");
      posIni=0;
      posFin = 0;
      caracterInicio = " ";
      caracterFin = " ";
      StringTokenizer st;
      String palabra;
      while ((posIni = codigo.indexOf(caracterInicio, posIni)) >= 0) {
	posFin = codigo.indexOf(caracterFin,posIni)+2;
	st = new StringTokenizer(codigo.substring(posIni));
	palabra = st.nextToken();
	if (palabrarReservadas.contains(palabra)){
	  estilo.setCharacterAttributes(posIni, palabra.length()+1, estiloPalabraReservada, false);
	}
	posIni += caracterInicio.length()+1;
      }

    }catch (Exception e){}
    editor.repaint();
  }
  /**
  * Escucha los eventos producidos en el menu/para la paleta de botones
  * Sirve para reducir/aumentar el tamanyo del texto y compilarlo
  */
 public void actionPerformed(ActionEvent evento){

   // Para los botones
   if (((JButton)(evento.getSource())).getText()== "Compilar"){
     if (esProblema){
       creadorProbl.compilarArchivoProblema(editor.getText());
     }else{
       creadorProbl.compilarArchivoEstado(editor.getText());
     }
   }else if (((JButton)(evento.getSource())).getText()== "+"){
     tamanyoFuente++;
     maquearCodigo();
   }else if (((JButton)(evento.getSource())).getText()== "-"){
     tamanyoFuente--;
     maquearCodigo();
   }
 }
}




