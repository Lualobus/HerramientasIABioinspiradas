package TempleSimulado.InterfaceGrafica;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.Line2D;
import javax.swing.*;
import TempleSimulado.*;


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
    private Line2D[] lineasRelativas;
    private Line2D linea = new Line2D.Float();
    private double puntuacionAnterior=0;
    private double evaluacionAnterior=0;
    private double etapaAnterior=0;
    public boolean graficar;
    JTextField tf;

    TempleSimulado templeSimulado;
    long etapasMax;
    double valorMaxGrafica;

    public Grafica(TempleSimulado templeSimulado,JPanel panelGrafica, long etapasMax, double valorMaxGraf) {
      this.templeSimulado = templeSimulado;
      this._dimensionPanelGrafica = panelGrafica.getSize();
      this._panelGrafica = panelGrafica;
      this.setSize(_dimensionPanelGrafica);
      panelGrafica.removeAll();
      panelGrafica.add(this);  //anyadimos la grafica al panel de la aplicacion dedicado a tal fin
      graficar = true;
      lineasAbsolutas = new Line2D[(int)etapasMax];
      lineasRelativas = new Line2D[(int)etapasMax];
      setBackground(Color.black);
      this.etapasMax = etapasMax;
      this.valorMaxGrafica = valorMaxGraf;
    }

    /** Metodo que se llama cada vez que se produce un repaint() y es el que dibuja la grafica*/
    public void paint(Graphics g) {
      try{

      double temperatura =  templeSimulado.temperaturaActual();
      double evaluacion = templeSimulado.evaluacionEstadoActual();
      int etapas = templeSimulado.numEtapa();
      if (evaluacion > valorMaxGrafica) valorMaxGrafica = evaluacion;

      grafico.setBackground(getBackground());
      grafico.clearRect(0,0,w,h);

      // .. Dibujamos las iteraciones y las puntuaciones obtenidas ..

      grafico.setColor(Color.RED);
      grafico.drawString("Temperatura "+Math.round(temperatura),(int)_dimensionPanelGrafica.getWidth()-110,10);
      grafico.setColor(Color.YELLOW);
      grafico.drawString("Puntuacion " + evaluacion, 10,10);
      grafico.setColor(Color.GREEN);
      grafico.drawString("Etapas "+etapas, (float)(((this._dimensionPanelGrafica.getWidth()/2))-30),(float)(this._dimensionPanelGrafica.getHeight()-8));



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
      if (etapas != 0){
	grafico.setColor(Color.red);

	if(templeSimulado.numEtapa() < etapasMax){
	  //lo guardamos en coordenadas sin relativizar al tamanyo de la grafica
	  lineasAbsolutas[etapas] = new Line2D.Float();
	  lineasAbsolutas[etapas].setLine(etapaAnterior,puntuacionAnterior,etapas,temperatura);
          lineasRelativas[etapas] = new Line2D.Float();
	  lineasRelativas[etapas].setLine(etapaAnterior,evaluacionAnterior,etapas,evaluacion);
	}
	int ultimaNoNula = -1;
	for (int i =1; i <= etapas; i++){

	  //estos if son necesarios porque tenemos iteraciones nulas salteadas (al no estar sincronizado en las dos direcciones la grafica y la simulacion)
	  if (( i < lineasAbsolutas.length) && (lineasAbsolutas[i] != null)) {
	    if (ultimaNoNula == -1){
	      ultimaNoNula = i;
	    }else{

              //dibujamos las lineas ajustandolas al tamanyo de la grafica
              grafico.setColor(Color.RED);
              grafico.drawLine(
                  (int) (posX + ((lineasAbsolutas[ultimaNoNula].getX1() * anchuraGraf) / etapasMax)),
                  (int) (posY + alturaGraf - ((lineasAbsolutas[ultimaNoNula].getY1() * alturaGraf) / valorMaxGrafica)),
                  (int) (posX + ((lineasAbsolutas[i].getX1() * anchuraGraf) / etapasMax)),
                  (int) (posY + alturaGraf - ((lineasAbsolutas[i].getY1() * alturaGraf) / valorMaxGrafica)));
              grafico.setColor(Color.YELLOW);
              grafico.drawLine(
                  (int)(posX + ((lineasRelativas[ultimaNoNula].getX1()*anchuraGraf)/etapasMax)),
                  (int)(posY+alturaGraf - ((lineasRelativas[ultimaNoNula].getY1()*alturaGraf)/valorMaxGrafica)) ,
                  (int)(posX + ((lineasRelativas[i].getX1()*anchuraGraf)/etapasMax)),
                  (int)(posY+alturaGraf - ((lineasRelativas[i].getY1()*alturaGraf)/valorMaxGrafica)));                }
              ultimaNoNula = i;
	    }

	}
      }

      puntuacionAnterior = temperatura;
      evaluacionAnterior = evaluacion;
      etapaAnterior = etapas;

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
