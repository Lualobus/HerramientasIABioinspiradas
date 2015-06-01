import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JWindow;

 /**
  * Es una splash screen (ventana de bienvenida mientras carga la aplicacion)
  * que simula el efecto de transparencia
  */
 public class VentanaBienvenida extends JWindow{

   public static void main(String args[]){
     VentanaBienvenida vb = new VentanaBienvenida("./recursos/bienvenidaCopia.png");
     vb.invocaMainAplicacion("HerramientasIA",null);
     vb.dispose();
   }

   private String rutaImagen; /** Ruta de la imagen, se recomienda una png-24 al tener mas niveles de transparencia */
   private BufferedImage bufImage;  /** Representacion en memoria de la imagen que se quiere mostrar */
   private Rectangle capturaPantalla;  /** Captura de la pantalla, sobre la cual dibujaremos nuestra imagen encima para simular la transparencia */


   /** Crea la ventana de vienbenida */
   public VentanaBienvenida(String imageFile) {
     this.rutaImagen = imageFile;
     mostrarSplashScreen();
   }

   /** Muestra la ventana de bienvenida   */
   public void mostrarSplashScreen() {
     Image image = new ImageIcon(rutaImagen).getImage();
     int anchuraImagen = image.getWidth(this);
     int alturaImagen = image.getHeight(this);
     if (anchuraImagen > 0 && alturaImagen > 0) {
       int anchuraPantalla = Toolkit.getDefaultToolkit().getScreenSize().width;
       int alturaPantalla = Toolkit.getDefaultToolkit().getScreenSize().height;
      //capturamos la pantalla
       capturaPantalla = new Rectangle((anchuraPantalla - anchuraImagen) / 2, (alturaPantalla - alturaImagen) / 2, anchuraImagen, alturaImagen);
       try {
	 bufImage = new Robot().createScreenCapture(capturaPantalla);
       } catch (AWTException e) {
	 e.printStackTrace();
       }
       Graphics2D g2D = bufImage.createGraphics();

       g2D.drawImage(image, 0, 0, this);        //dibujamos la imagen sobre la captura de la pantalla
       setBounds(capturaPantalla);
       setVisible(true);
       //show();
     } else {
       System.err.println("No encontrada la imagen: " + rutaImagen);
     }
   }

   /** Invoca el main de la aplicacion principal */
   public void invocaMainAplicacion(String claseAInvocar, String[] args) {
     try {
       Class.forName(claseAInvocar).getMethod("main", new Class[] {String[].class}).invoke(null, new Object[] {args});
     } catch (Exception e) {
       InternalError error = new InternalError("Fallo al invocar el main de la aplicacion");
       error.initCause(e);
       throw error;
     }
   }


   /**  Sobrescribimos el metodo de JWindow   */
   public void paint(Graphics g) {
     Graphics2D g2D = (Graphics2D) g;
     g2D.drawImage(bufImage, 0, 0, this);
   }

 }
