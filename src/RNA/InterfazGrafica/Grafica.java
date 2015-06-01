package RNA.InterfazGrafica;

import RNA.Red;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Line2D;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


/**
* Esta clase consigue realizar la grafica entre iteraciones / error cometido, para
* ello se vale de los atributos estaticos de la clase RNA.Red que son los que va a graficar,
* para realizar la sincronizacion entre el hilo de la grafica y el aprendizaje de la red se usa
* un semaforo
*/
public class Grafica extends JPanel implements Runnable {

  static JCheckBox dateStampCB = new JCheckBox("Output Date Stamp");
  public Dimension _dimensionPanelGrafica;
  public JPanel _panelGrafica;
  JPanel controlVelocidad;  //controla la velocidad de visualizacion de la grafica
  public Thread thread;
  private int w, h;
  private BufferedImage bufferImagen;
  private Graphics2D grafico;  //es abstracta
  private Font font = new Font("Times New Roman", Font.PLAIN, 11);
  private Rectangle rectangulo = new Rectangle();
  private Line2D[] lineasRelativas;
  private Line2D[] lineasAbsolutas;
  private Line2D linea = new Line2D.Float();
  private double puntuacionAnterior=0;
  private double iteracionAnterior=0;
  private double puntuacionPoblacionMediaAnterior = 0;
  static double errorMaxObtenido=0;
  JTextField tf;


  public Grafica(JPanel panelGrafica) {
    this._dimensionPanelGrafica = panelGrafica.getSize();
    this._panelGrafica = panelGrafica;
    this.setSize(_dimensionPanelGrafica);
    panelGrafica.removeAll();
    panelGrafica.add(this);  //anyadimos la grafica al panel de la aplicacion dedicado a tal fin
    setLayout(new BorderLayout());
    setBorder(new TitledBorder(new EtchedBorder(), "Puntuacion / Iteraciones"));
    controlVelocidad = new JPanel();
    controlVelocidad.setPreferredSize(new Dimension(135,80));
    Font font = new Font("serif", Font.PLAIN, 10);
    JLabel label = new JLabel("Sample Rate");
    label.setFont(font);
    label.setForeground(Color.black);
    controlVelocidad.add(label);
    tf = new JTextField("1000");
    tf.setPreferredSize(new Dimension(45,20));
    controlVelocidad.add(tf);
    controlVelocidad.add(label = new JLabel("ms"));
    label.setFont(font);
    label.setForeground(Color.black);
    controlVelocidad.add(dateStampCB);
    dateStampCB.setFont(font);
    setBackground(Color.black);
  }


  /** Metodo que se llama cada vez que se produce un repaint() y es el que dibuja la grafica*/
  public void paint(Graphics g) {


    this._dimensionPanelGrafica = this._panelGrafica.getSize();

    if (grafico == null) { return;}

    grafico.setBackground(getBackground());
    grafico.clearRect(0,0,w,h);

    double errorEntrenamiento= Red.errorPorNeuronaSalida;
    long iteraciones = Red.iteracciones;
    double errorValidacion = Red.errorValidacion;

    if (errorEntrenamiento > errorMaxObtenido){
      errorMaxObtenido = errorEntrenamiento;
    }

    if (errorValidacion > errorMaxObtenido){
      errorMaxObtenido = errorValidacion;
    }

    if (errorMaxObtenido == Double.MAX_VALUE) return;

    // .. Dibujamos las iteraciones y las puntuaciones obtenidas ..
    grafico.setColor(Color.green);
    //grafico.drawString("Puntuacion "+puntuacion, 4.0f, (float) ascenso+0.5f);
    //grafico.drawString("Iteraciones "+iteraciones, 4, h-descenso);
    grafico.setColor(Color.cyan);
    grafico.drawString("ErrorEntrenamiento "+Math.round(errorEntrenamiento), 10,10);
    grafico.setColor(Color.green);
    grafico.drawString("Iteracciones "+iteraciones, (float)(((this._dimensionPanelGrafica.getWidth()/2))-30),(float)(this._dimensionPanelGrafica.getHeight()-8));
    grafico.setColor(Color.red);
    if (errorValidacion>=0)
      grafico.drawString("ErrorValidacion " + Math.round(errorValidacion),(float)((this._dimensionPanelGrafica.getWidth())-200),10);


    // .. Dibuja la cuadricula de la grafica ..
    double graphX= 20;//30;   //coordenadas X de la esquina superior donde empieza la cuadricula
    double graphY= 20;//(int) ssH; //coordenadas Y de la esquina superior donde empieza la cuadricula
    double graphW= getPreferredSize().getWidth() - 2*graphX; //ancho de la cuadricula
    double graphH= getPreferredSize().getHeight() - 2*graphY; //alto de la cuadricula

    grafico.setColor(Color.green);
    double graphRow = graphH/10;
    int ind = 0;
    for (double j = graphY; j <= graphH+graphY; j += graphRow) {
      linea.setLine(graphX,j,graphX+graphW,j);
      grafico.drawString(""+Math.round((errorMaxObtenido/10)*(10-ind)),(float)graphX-20,(float)j);
      grafico.draw(linea);
      ind++;
    }

    //grafico.setColor(Color.cyan);
    rectangulo.setRect(graphX, graphY, graphW, graphH);
    grafico.draw(rectangulo);

    //..... Dibujamos la grafica....
    if (Red.iteracciones != 0){
      grafico.setColor(Color.red);

      if((iteraciones>=0) && (iteraciones < lineasAbsolutas.length)){
	  //lo guardamos en coordenadas sin relativizar al tamanyo de la grafica
	  lineasAbsolutas[(int)iteraciones] = new Line2D.Float();
	  lineasAbsolutas[(int)iteraciones].setLine(iteracionAnterior,puntuacionAnterior,iteraciones,errorEntrenamiento);
	  lineasRelativas[(int)iteraciones] = new Line2D.Float();
	  lineasRelativas[(int)iteraciones].setLine(iteracionAnterior,puntuacionPoblacionMediaAnterior,iteraciones,errorValidacion);
      }
      int ultimaNoNula = -1;
      for (int i =1; i <= Red.iteracciones; i++){

	  //estos if son necesarios porque tenemos iteraciones nulas salteadas (al no estar sincronizado en las dos direcciones la grafica y la simulacion)
	  if ( (i < lineasAbsolutas.length) && lineasAbsolutas[i] != null) {
	    if (ultimaNoNula == -1){
	      ultimaNoNula = i;
	    }else{

	      //dibujamos las lineas ajustandolas al tamanyo de la grafica
	      grafico.setColor(Color.cyan);
	      grafico.drawLine(
		  (int)(graphX + ((lineasAbsolutas[ultimaNoNula].getX1()*graphW)/Red.iteraccionesMax)),
		  (int)(graphY+graphH - ((lineasAbsolutas[ultimaNoNula].getY1()*graphH)/errorMaxObtenido)) ,
		  (int)(graphX + ((lineasAbsolutas[i].getX1()*graphW)/Red.iteraccionesMax)),
		  (int)(graphY+graphH - ((lineasAbsolutas[i].getY1()*graphH)/errorMaxObtenido)));
	      if (Red.errorValidacion>=0){
		grafico.setColor(Color.red);
		grafico.drawLine(
		    (int)(graphX + ((lineasRelativas[ultimaNoNula].getX1()*graphW)/Red.iteraccionesMax)),
		    (int)(graphY+graphH - ((lineasRelativas[ultimaNoNula].getY1()*graphH)/errorMaxObtenido)) ,
		    (int)(graphX + ((lineasRelativas[i].getX1()*graphW)/Red.iteraccionesMax)),
		    (int)(graphY+graphH - ((lineasRelativas[i].getY1()*graphH)/errorMaxObtenido)));
	      }
	      ultimaNoNula = i;
	    }
	  }
	}
      }

      puntuacionAnterior = errorEntrenamiento;
      iteracionAnterior = iteraciones;
      puntuacionPoblacionMediaAnterior = errorValidacion;
      g.drawImage(bufferImagen, 0, 0, this);
  }

//_________________________________METODOS PARA MANEJAR EL THREAD_______________________
  /**Lanza el tread*/
  public void arrancarGrafica() {
    thread = new Thread(this);  //creo un thread que va a ejecutar el metodo run que implemento en esta clase (runnable)
    //thread.setPriority(Thread.MIN_PRIORITY);
    thread.setName("Grafica");
    thread.start();
  }

  /** Para el thread*/
  public void parar(){
    System.out.println("el graficar se va a parar");
    Thread thread = Thread.currentThread();
    //thread.stop();
    thread = null;
  }


  /** Metedo que se ejecuta cuando se lanza el thread*/
  public synchronized void run() {

    Thread me = Thread.currentThread();
    lineasAbsolutas=null;

    while (thread == me && isShowing()) {
      if ((Red.mutexVacio != null) && (Red.mutexAlternancia != null)) {
	//System.out.println("Grafica se bloqueara");
	Red.mutexVacio.bajar();
	Red.mutexAlternancia.bajar();
	//System.out.println("Grafica desbloqueada");
      }

      //if (!Red.calculando) {  break;  }

      if (Red.iteraccionesMax <0 ) continue;

      if (lineasAbsolutas == null) {
	lineasAbsolutas = new Line2D[(int) Red.iteraccionesMax];
	lineasRelativas = new Line2D[(int) Red.iteraccionesMax];
      }else if (lineasAbsolutas.length < Red.iteraccionesMax) {
	Line2D[] lineasAux = new Line2D[(int) Red.iteraccionesMax];
	System.arraycopy(lineasAbsolutas, 0, lineasAux, 0, lineasAbsolutas.length); ;
	lineasAbsolutas = lineasAux;
	lineasAux = new Line2D[(int) Red.iteraccionesMax];
	System.arraycopy(lineasRelativas, 0, lineasAux, 0, lineasRelativas.length); ;
	lineasRelativas = lineasAux;
      }

      Dimension d = getSize();
      if (d.width != w || d.height != h) {
	w = d.width;
	h = d.height;
	bufferImagen = (BufferedImage) createImage(w, h);
	grafico = bufferImagen.createGraphics();
	grafico.setFont(font);

      }
      paintImmediately(0, 0, (int) getSize().getWidth(), (int) getSize().getHeight());

      if ((Red.mutexAlternancia != null) && (Red.mutex != null)) {
	//System.out.println("Grafica desbloquea red");
	Red.mutexAlternancia.subir();
	Red.mutex.subir();
      }
    }
    System.out.println("paro de redibujar");
  }

    public Dimension getMinimumSize() {
    return getPreferredSize();
  }

  public Dimension getMaximumSize() {
    return getPreferredSize();
  }

  public Dimension getPreferredSize() {
    return _dimensionPanelGrafica;
  }


}
