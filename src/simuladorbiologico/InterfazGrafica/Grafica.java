package simuladorbiologico.InterfazGrafica;

import simuladorbiologico.Simulador;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Line2D;
import java.util.Date;
import javax.swing.*;




/**
 * Esta clase consigue realizar la grafica entre iteraciones / puntuacion; el codigo no es muy claro
 * ya que esta "reescrita" sobre una clase ya escrita por otra persona con codigo bastante deficiente
 */


public class Grafica extends JPanel implements Runnable {

    static JCheckBox dateStampCB = new JCheckBox("Output Date Stamp");
    public Dimension _dimensionPanelGrafica;
    public JPanel _panelGrafica;
    JPanel controlVelocidad;  //controla la velocidad de visualizacion de la grafica
    public  static long tiempoDormido = 400;  //tiempo que esta dormido el hilo que construye la grafica
    public Thread thread;
    private int w, h;
    private BufferedImage bufferImagen;
    private Graphics2D grafico;  //es abstracta
    private Font font = new Font("Times New Roman", Font.PLAIN, 11);

    private Rectangle rectangulo = new Rectangle();
    private Line2D[] lineasPoblacion;
    private Line2D[] lineasAbsolutas;
    private Line2D linea = new Line2D.Float();
    private String usedStr;
    private double puntuacionAnterior=0;
    private double iteracionAnterior=0;
    private double puntuacionPoblacionMediaAnterior = 0;
    public boolean graficar;
    JTextField tf;
    Simulador _simulador; //_______________referencia a simulador, para poder sincronizarse con el

    public double puntuacionAObtener;
    public double iteraccionesMax;

    public Grafica(Simulador simulador,JPanel panelGrafica) {
      this._dimensionPanelGrafica = panelGrafica.getSize();
      this._panelGrafica = panelGrafica;
      this.setSize(_dimensionPanelGrafica);
      panelGrafica.removeAll();
      panelGrafica.add(this);  //anyadimos la grafica al panel de la aplicacion dedicado a tal fin
      _simulador = simulador;
      graficar = true;
      lineasAbsolutas = new Line2D[(int)_simulador.getIteracionesMaximas()];
      lineasPoblacion = new Line2D[(int)_simulador.getIteracionesMaximas()];
      setBackground(Color.black);
      iteraccionesMax = _simulador.getIteracionesMaximas();
      if (_simulador.isMaximizationProblem()){
        puntuacionAObtener= _simulador.getPuntuacionAObtener();
      }
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


    /** Metodo que se llama cada vez que se produce un repaint() y es el que dibuja la grafica*/
    public void paint(Graphics g) {

      if (!Simulador.tipoSalidaResultados.equals("Grafica"))    return;  //si no queremos hacer salida grafica no mostramos nada en el panel
      this._dimensionPanelGrafica = this._panelGrafica.getSize();
      //System.out.println("repintamos");

      if (grafico == null) { return;}

      grafico.setBackground(getBackground());
      grafico.clearRect(0,0,w,h);

      double puntuacion= _simulador.puntuacionObtenida;
      int iteraciones = _simulador.iteraciones;
      double puntuacionPoblacionMedia = _simulador.puntuacionPoblacionMedia;


      // .. Dibujamos las iteraciones y las puntuaciones obtenidas ..

      grafico.setColor(Color.YELLOW);
      grafico.drawString("Mejor "+puntuacion, 10,10);
      grafico.setColor(Color.GREEN);
      grafico.drawString("Generaciones "+iteraciones, (float)(((this._dimensionPanelGrafica.getWidth()/2))-30),(float)(this._dimensionPanelGrafica.getHeight()-8));

      grafico.setColor(Color.GREEN);
      if (_simulador.isMaximizationProblem()){
        grafico.drawString("MAXIMIZA",(int)_dimensionPanelGrafica.getWidth()/2-30,10);
      }else{
        grafico.drawString("MINIMIZA",(int)_dimensionPanelGrafica.getWidth()/2-30,10);
      }

      grafico.setColor(Color.red);
      if (Simulador.hacerGraficaPoblacionMedia)
	grafico.drawString("Media " + Math.round(puntuacionPoblacionMedia),(float)((this._dimensionPanelGrafica.getWidth())-70),10);


      //DIBUJAMOS LA GRAFICA PROPIAMENTE DICHA
      if (Simulador.hacerGrafica){


        /*calculamos el valor maximo de la grafica (en un problema de minimizacion, suponemos
          que es el primer valor que se alcanza en la generacion inicial) */
        if (!_simulador.isMaximizationProblem()){
          if (puntuacionAObtener==0){
            if (_simulador.hacerGraficaPoblacionMedia){
              puntuacionAObtener = _simulador.puntuacionPoblacionMedia;
            }else{
              puntuacionAObtener = _simulador.puntuacionObtenida;
            }
          }
        }

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
	  grafico.drawString(""+Math.round((puntuacionAObtener/10)*(10-ind)),(float)graphX-20,(float)j);
	  grafico.draw(linea);
	  ind++;
	}
	//grafico.setColor(Color.cyan);
	rectangulo.setRect(graphX, graphY, graphW, graphH);
	grafico.draw(rectangulo);

	//..... Dibujamos la grafica....
	if (_simulador.iteraciones != 0){
	  grafico.setColor(Color.red);

	  if(_simulador.iteraciones < _simulador.getIteracionesMaximas()){
	    //lo guardamos en coordenadas sin relativizar al tamanyo de la grafica
	    lineasAbsolutas[iteraciones] = new Line2D.Float();
	    lineasAbsolutas[iteraciones].setLine(iteracionAnterior,puntuacionAnterior,iteraciones,puntuacion);
	    lineasPoblacion[iteraciones] = new Line2D.Float();
	    lineasPoblacion[iteraciones].setLine(iteracionAnterior,puntuacionPoblacionMediaAnterior,iteraciones,puntuacionPoblacionMedia);
	  }
	  int ultimaNoNula = -1;
	  for (int i =1; i < _simulador.iteraciones; i++){

	    //estos if son necesarios porque tenemos iteraciones nulas salteadas (al no estar sincronizado en las dos direcciones la grafica y la simulacion)
	    if (lineasAbsolutas[i] != null) {
	      if (ultimaNoNula == -1){
		ultimaNoNula = i;
	      }else{

		//dibujamos las lineas ajustandolas al tamanyo de la grafica
		 grafico.setColor(Color.YELLOW);
		grafico.drawLine(
		    (int)(graphX + ((lineasAbsolutas[ultimaNoNula].getX1()*graphW)/iteraccionesMax)),
		    (int)(graphY+graphH - ((lineasAbsolutas[ultimaNoNula].getY1()*graphH)/puntuacionAObtener)) ,
		    (int)(graphX + ((lineasAbsolutas[i].getX1()*graphW)/iteraccionesMax)),
		    (int)(graphY+graphH - ((lineasAbsolutas[i].getY1()*graphH)/puntuacionAObtener)));
		if (Simulador.hacerGraficaPoblacionMedia){
		  grafico.setColor(Color.red);
		  grafico.drawLine(
		      (int)(graphX + ((lineasPoblacion[ultimaNoNula].getX1()*graphW)/iteraccionesMax)),
		      (int)(graphY+graphH - ((lineasPoblacion[ultimaNoNula].getY1()*graphH)/puntuacionAObtener)) ,
		      (int)(graphX + ((lineasPoblacion[i].getX1()*graphW)/iteraccionesMax)),
		      (int)(graphY+graphH - ((lineasPoblacion[i].getY1()*graphH)/puntuacionAObtener)));
		}
		ultimaNoNula = i;
	      }
	    }
	  }
	}

	puntuacionAnterior = puntuacion;
	iteracionAnterior = iteraciones;
	puntuacionPoblacionMediaAnterior = puntuacionPoblacionMedia;
      }
      g.drawImage(bufferImagen, 0, 0, this);

    }

//_________________________________METODOS PARA MANEJAR EL THREAD_______________________
    /**Lanza el tread*/
    public void arrancarGrafica() {
      thread = new Thread(this);  //creo un thread que va a ejecutar el metodo run que implemento en esta clase (runnable)
      thread.setPriority(Thread.MIN_PRIORITY);
      thread.start();
    }

    /** Para el thread*/
    public void parar(){
      System.out.println("el graficar se va a parar");
      graficar = false;
    }

    /** Sirve para desbloquear al hilo cuando se queda en wait*/
    public synchronized void desbloquear(){
      Thread me = Thread.currentThread();
      me.notify();
    }


    /** Metedo que se ejecuta cuando se lanza el thread*/
    public synchronized void run() {
      Thread me = Thread.currentThread();
      while (thread == me && isShowing() && graficar) {
	Dimension d = getSize();
	if (d.width != w || d.height != h) {
	  w = d.width;
	  h = d.height;
	  bufferImagen = (BufferedImage) createImage(w, h);
	  grafico = bufferImagen.createGraphics();
	  grafico.setFont(font);
	}
	repaint();
	if (Grafica.dateStampCB.isSelected()) {
	  System.out.println(new Date().toString() + " " + usedStr);
	}

	Simulador.simulado = false;  //me he desbloqueado, vuelbo a ponerme a graficar y simular a false
	_simulador.despertar();  //notifico a simular que ya puede despertarse porque tiene trabajo
      }
    }
}



