package RNA;

public class PruebaFuncionCuadrado{

  public static void main(String[] args){
      try{

	System.out.println("---------Vamos a probar si la Red de neuronas aprende la funcion X^2-------\n");

	//estructura de la red
	int[] configuracion = { 1,4,1 };

	//patrones de entrenamiento
	double[][]  entradas =          { {0},{1}, {2}, {3}, {4},  {4.5},  {5}, {6}, {6.5},  {7}, {7.5},  {8}, {8.5},   {9},  {20}};
	double[][]  salidasEsperadas =  { {0},{1}, {4}, {9}, {16},{20.25}, {25},{36},{42.25},{49},{56.25},{64},{72.25}, {81}, {400}};

	//double[][]  entradas =          { {0},{2}, {4},  {5}, {6},   {7},  {8},   {9},  {10}};
	//double[][]  salidasEsperadas =  { {0},{4}, {16}, {25},{36}, {49}, {64},  {81}, {100}};


	//double[][]  entradas =          { {0}, {2}, {4},  {6},  {8}};
	//double[][]  salidasEsperadas =  { {1}, {4}, {16}, {36}, {64}};

	double[][] entradasValidacion = { {0.3},{2},{7.2},{17}};                //{12},{13},{16},{18},{20}};
	double[][] salidasValidacion= { {0.009},{4},{51.84},{289}};             //  {144},{169},{256},{324},{400}};


	//entrenamiento
	String funcionActivacion = "noLineal";
	long vecesPasarPatrones = 1000;
	double errorPermitidoParaSalida = 0.0;

	Red rna = new Red(configuracion,funcionActivacion);
	double errorEntrenamiento = rna.entrenar(entradas,salidasEsperadas,errorPermitidoParaSalida,vecesPasarPatrones,1000,Red.ENJAMBRE);
	//double errorEntrenamiento = rna.entrenarHastaNoSobreentrenar(entradas,salidasEsperadas,entradasValidacion,salidasValidacion,10);
	System.out.println("FIN ENTRENAMIENTO: El error medio que se produce para cada neurona de salida del entrenamiento es de " + errorEntrenamiento+"\n");


	double valor = 0;
	while (valor <= 20){
	  double[] entradaValidacion = {valor};
	  double[] salida = rna.validarRed(entradaValidacion);
	  System.out.println("La red dice que el cuadrado de "+ valor +" es---->>>> " +  salida[0]);
	  valor+=1;
	}

      }catch (Exception e){
	System.out.println("Se ha producido un error: " + e.getMessage());
      }
    }
}
