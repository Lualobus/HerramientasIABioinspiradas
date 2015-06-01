package ColoniaHormigas.InterfaceGrafica;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Line2D;
import javax.swing.*;
import ColoniaHormigas.*;


/**
 * Esta clase consigue realizar la grafica entre iteraciones / puntuacion; el codigo no es muy claro
 * ya que esta "reescrita" sobre una clase ya escrita por otra persona con codigo bastante deficiente
 */
public class Grafica extends JPanel implements Runnable {

    public Dimension _dimensionPanelGrafica;
    public JPanel _panelGrafica;
    JPanel controlVelocidad;  //controla la velocidad de visualizacion de la grafica
    public Thread thread;
    private int w, h;
    private BufferedImage bufferImagen;
    private Graphics2D grafico;  //es abstracta
    private Rectangle rectangulo = new Rectangle();
    private Line2D[] lineasAbsolutas;
    private Line2D linea = new Line2D.Float();
    private double puntuacionAnterior=0;
    private double iteracionAnterior=0;
    private double puntuacionPoblacionMediaAnterior = 0;
    public boolean graficar;
    JTextField tf;
    OptimizadorColoniaHormigas coloniaHormigas;
    long iteraccionesMax;
    double valoracionObtener;
    double valorMaxGrafica;

    public Grafica(OptimizadorColoniaHormigas coloniaHormigas,JPanel panelGrafica, long iteraccionesMax, double valorInicial) {
      this._dimensionPanelGrafica = panelGrafica.getSize();
      this._panelGrafica = panelGrafica;
      this.setSize(_dimensionPanelGrafica);
      panelGrafica.removeAll();
      panelGrafica.add(this);  //anyadimos la grafica al panel de la aplicacion dedicado a tal fin
      this.coloniaHormigas = coloniaHormigas;
      graficar = true;
      lineasAbsolutas = new Line2D[(int)iteraccionesMax];
      setBackground(Color.black);
      this.iteraccionesMax = iteraccionesMax;
      this.valoracionObtener = valorInicial;
      this.valorMaxGrafica = valoracionObtener;
    }

    /** Metodo que se llama cada vez que se produce un repaint() y es el que dibuja la grafica*/
    public void paint(Graphics g) {
      try{

      double puntuacion =  coloniaHormigas.getCosteTrayectoMejor();
      int iteracciones = (int)coloniaHormigas.iteracionesActuales();
      //if (iteracciones == 0) return;


      grafico.setBackground(getBackground());
      grafico.clearRect(0,0,w,h);

      // .. Dibujamos las iteraciones y las puntuaciones obtenidas ..
      grafico.setColor(Color.green);
      grafico.setColor(Color.YELLOW);
      grafico.drawString("Mejor "+puntuacion, 10,10);
      grafico.drawString("Iteracciones "+iteracciones, (float)(((this._dimensionPanelGrafica.getWidth()/2))-30),(float)(this._dimensionPanelGrafica.getHeight()-8));
      grafico.setColor(Color.red);


      // .. Dibuja la cuadricula de la grafica ..
      double posX= 20;//30;   //coordenadas X de la esquina superior donde empieza la cuadricula
      double posY= 20;//(int) ssH; //coordenadas Y de la esquina superior donde empieza la cuadricula
      double anchuraGraf= getPreferredSize().getWidth() - 2*posX; //ancho de la cuadricula
      double alturaGraf= getPreferredSize().getHeight() - 2*posY; //alto de la cuadricula

      grafico.setColor(Color.green);
      double graphRow = alturaGraf/10;
      int ind = 0;
      for (double j = posY; j <= alturaGraf+posY; j += graphRow) {
	linea.setLine(posX,j,posX+anchuraGraf,j);
	grafico.drawString(""+Math.round((valorMaxGrafica/10)*(10-ind)),(float)posX-20,(float)j);
	grafico.draw(linea);
	ind++;
      }

      rectangulo.setRect(posX, posY, anchuraGraf, alturaGraf);
      grafico.draw(rectangulo);

      //linea que indica la puntuacion que se quiere obtener
      /*grafico.setColor(Color.PINK);
      linea.setLine(posX,posY+(alturaGraf-((valoracionObtener*alturaGraf)/valorMaxGrafica)),posX+anchuraGraf,posY+(alturaGraf-((valoracionObtener*alturaGraf)/valorMaxGrafica)));
      grafico.draw(linea);*/

      //..... Dibujamos la grafica....
      if (iteracciones != 0){
	grafico.setColor(Color.red);

	if(coloniaHormigas.iteracionesActuales() < iteraccionesMax){
	  //lo guardamos en coordenadas sin relativizar al tamanyo de la grafica
	  lineasAbsolutas[iteracciones] = new Line2D.Float();
	  lineasAbsolutas[iteracciones].setLine(iteracionAnterior,puntuacionAnterior,iteracciones,puntuacion);
	}
	int ultimaNoNula = -1;
	for (int i =2; i <= iteracciones; i++){

	  //estos if son necesarios porque tenemos iteraciones nulas salteadas (al no estar sincronizado en las dos direcciones la grafica y la simulacion)
	  if (( i < lineasAbsolutas.length) && (lineasAbsolutas[i] != null)) {
	    if (ultimaNoNula == -1){
	      ultimaNoNula = i;
	    }else{

	      //dibujamos las lineas ajustandolas al tamanyo de la grafica
	      grafico.setColor(Color.YELLOW);
	      grafico.drawLine(
		  (int)(posX + ((lineasAbsolutas[ultimaNoNula].getX1()*anchuraGraf)/iteraccionesMax)),
		  (int)(posY+alturaGraf - ((lineasAbsolutas[ultimaNoNula].getY1()*alturaGraf)/valorMaxGrafica)) ,
		  (int)(posX + ((lineasAbsolutas[i].getX1()*anchuraGraf)/iteraccionesMax)),
		  (int)(posY+alturaGraf - ((lineasAbsolutas[i].getY1()*alturaGraf)/valorMaxGrafica)));

	      ultimaNoNula = i;
	    }
	  }
	}
      }

      puntuacionAnterior = puntuacion;
      iteracionAnterior = iteracciones;
      g.drawImage(bufferImagen, 0, 0, this);
      }catch (Exception err){}
    }

    //_________________________________METODOS PARA MANEJAR EL THREAD_______________________

    /**Lanza el tread*/
    public void arrancarGrafica() {
      thread = new Thread(this);  //creo un thread que va a ejecutar el metodo run que implemento en esta clase (runnable)
      thread.start();
    }

    /** Para el thread*/
    public void parar(){
      graficar = false;
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
	}
	paintImmediately(0, 0, (int) getSize().getWidth(), (int) getSize().getHeight());
	thread.yield();
      }
    }

    public Dimension getMinimumSize() {
      return _dimensionPanelGrafica;
    }

    public Dimension getMaximumSize() {
      return _dimensionPanelGrafica;
    }

    public Dimension getPreferredSize() {
      return _dimensionPanelGrafica;
    }
}
