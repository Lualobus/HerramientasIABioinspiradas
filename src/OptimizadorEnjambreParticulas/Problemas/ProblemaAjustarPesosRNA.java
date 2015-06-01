package OptimizadorEnjambreParticulas.Problemas;

import OptimizadorEnjambreParticulas.*;
import RNA.Red;

/**
 * Este problema sirve para ajustar los pesos de una Red de Neuronas usando enjambres de particulas
 */
public class ProblemaAjustarPesosRNA extends Problema {

  private Red rna; //red de neuronas
  private double[][] entradas; //entradas de entremiento normalizadas
  private double[][] salidasEsperadas; //salidas esperadas para los entrenamientos (normalizadas)
  private int numPesos; //numero de pesos total de la red

  /** Sirve para recivir argumentos que sean necesarios para el problema: recive la red de neuronas, necesaria para poder evaluar cada particula*/
  public ProblemaAjustarPesosRNA(Object[] argumentosAdicionales) throws Exception {
    try {
      rna = (Red) argumentosAdicionales[0];
      entradas = (double[][]) argumentosAdicionales[1];
      salidasEsperadas = (double[][]) argumentosAdicionales[2];
      numPesos = ((Integer) argumentosAdicionales[4]).intValue();
    }catch (Exception e) {
      throw new Exception("Los argumentos recividos para el problema de ajustar los pesos de la RNA no son validos");
    }
  }


  /**
   * FUNCION FITNES PARA LA PARTiCULA
   * En la posicion de la particula queda codificados todos los pesos de la red,
   * es decir, en cada componente de la posicion se relaciona con un peso de la red,
   * de tal manera que se tienen tantas dimensiones (componentes) como pesos tenga la red,
   * y como resultado se devuelve un valor MAX-error cometido por la red
   */
  public double funcionEvaluacion(Particula particula) {
    try{
      rna.asignarPesos(particula.getPosicion());
      double errorPorPasadaPatrones = 0;
      double[][] salidasObtenidas = new double[salidasEsperadas.length][salidasEsperadas[0].length];
      for (int i = 0; i < salidasEsperadas.length; i++) {
	salidasObtenidas[i] = rna.calcularSalidaRed(entradas[i]);
	for (int j = 0; j < salidasEsperadas[i].length; j++) {
	  errorPorPasadaPatrones += Math.abs(salidasObtenidas[i][j] - salidasEsperadas[i][j]);
	}
      }
      errorPorPasadaPatrones /= (entradas.length * salidasEsperadas[0].length); //obtenemos el error normalizado por cada neurona de salida
      return errorPorPasadaPatrones;
    }catch (Exception e){
      return Double.MAX_VALUE;
    }
  }

  /**
   * Es minimizacion
   * @return False porque es de minimizar
   */
  public boolean isMaximizeProblem(){
    return false;
  }



  /** Se tienen tantas dimensiones como pesos la red de neuronas */
  public int valorDefectoNumDimensiones() {
    return numPesos;
  }

  /* El valor por defecto es de 100 particulas sino se sobrescribe */
  public int valorDefectoNumParticulas() {
    return 100;
  }

  /* El valor por defecto es de 0.9 sino se sobrescribe */
  public double valorDefectoCteAtraccion1() {
    return 0.9;
  }

  /* El valor por defecto es de 0.9 sino se sobrescribe */
  public double valorDefectoCteAtraccion2() {
    return 0.9;
  }

  /* El valor por defecto es de 0.9 sino se sobrescribe */
  public double valorDefectoInercia() {
    return 0.9;
  }

  /* El valor por defecto de la puntuaciona obtener es de 1000 sino se sobrescribe */
  public double valorDefectoValorObtener() {
    return 0;
  }

  /* El valor por defecto del numero de iteracciones max es de 100 sino se sobrescribe */
  public long valorDefectoIteraccionesMax() {
    return 1000;
  }

  /** Sino se sobrescribe simplemente se devuelve que no se tiene informacion para este problema*/
  public String ayuda() {
    return "Se esta intentando entrenar una red de neuronas con enjambres de particulas";
  }
}
