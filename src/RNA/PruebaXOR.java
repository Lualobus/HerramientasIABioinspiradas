package RNA;

class PruebaXOR {


   public static void main(String[] args){
     try{

       System.out.println("---------Vamos a probar si la Red de neuronas entiende la funcion XOR-------\n");

       //estructura de la red
       int[] configuracion = { 2,3,1 };

       //patrones de entrenamiento
       double[][]  entradas =          { {1,1},{1,0},{0,1},{0,0}};
       double[][]  salidasEsperadas =  { {0},{1},{1},{0}};

       //entrenamiento
       String funcionActivacion = "noLineal";
       long vecesPasarPatrones = 200;
       double errorPermitido = 0;
       Red rna = new Red(configuracion,funcionActivacion);
       double errorEntrenamiento = rna.entrenar(entradas,salidasEsperadas,errorPermitido,vecesPasarPatrones,(int)vecesPasarPatrones/10,Red.GENETICO);



       System.out.println("FIN ENTRENAMIENTO: El error medio que se produce para cada salida del entrenamiento es de " + errorEntrenamiento+"\n");

       //validacion
       double[] entradaValidacion1 = {1,1};
       double salida[] = rna.validarRed(entradaValidacion1);
       System.out.println("La red dice que el xor de 1,1 es: " +  salida[0]);
       double[] entradaValidacion2 = {0,1};
       salida = rna.validarRed(entradaValidacion2);
       System.out.println("La red dice que el xor de 0,1 es: " +  salida[0]);
       double[] entradaValidacion3 = {1,0};
       salida = rna.validarRed(entradaValidacion3);
       System.out.println("La red dice que el xor de 1,0 es: " +  salida[0]);
       double[] entradaValidacion4 = {0,0};
       salida = rna.validarRed(entradaValidacion4);
       System.out.println("La red dice que el xor de 0,0 es: " +  salida[0]);



     }catch (Exception e){
       System.out.println("Se ha producido un error: " + e);
     }
   }
}
