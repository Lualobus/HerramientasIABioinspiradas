package simuladorbiologico;

/**
 * Clase encargada de la reproduccion de los individuos de la poblacion, en ella se implementa
 * el metodo de reproduccion por torneos y los distintos tipos de sobrecruzamiento
 */
public class Reproduccion {

    /** Se gunta la mitad de un cromosoma de un individuo con la otra mitad del cromosoma de otro individuo */
    public final static String sobrecruzamientoPartirMitad = "Partir por la mitad";
    /** Igual que el anterior pero dividiendo el cromosoma no por la mitad, sino en un punto cualquiera del mismo */
    public final static String sobrecruzamientoPartirAleatio = "Partir aleatorio";
    /** El nuevo cromosoma en cada posicion se elige entre el gen de esa misma posicion de uno de los dos padres */
    public final static String sobrecruzamientoSeleccionGen = "Seleccion gen";
    /** No se realiza sobrecruzamiento en la reproduccion */
    public final static String sobrecruzamientoNinguno = "Ninguno";

  /**
   * Se basa en dos pasos:
   * 1º Primero se debe SELECCIONAR la poblacion en funcion de su evaluacion (aplicando el metodo de Torneos)
   * 2º La poblacion seleccionada se REPRODUCE conformando la nueva poblacion
   *
   * Torneos: se producen torneos entre grupos de la poblacion, el que tenga mas valoracion de cada grupo se reproduce;
   * este metodo tiene la ventaja de que es posible que individuos no muy buenos, si les toca un
   * grupo malo pasen, asi se puede conservar caracteristicas suyas que en el futuro sean favorables,
   * y evitar la aparicion de superindividuos. La poblacion sera sustituida por la nueva creada.
   * Se haran tantos torneos como individuos tenga la poblacion (para manteren el mismo numero de invidivuos),
   * salvo en el caso de que queramos que pasen los n-mejores, que se haran tantos torneos como individuos-n
   *
   * @param individuos Individuos que conforman la poblacion
   * @param presionSelectiva Numero de grupos que se forman, a grupos mas pequenyos menos presion selectiva
   * @param isMaximizationProblem True si es un problema de maximizacion, false si es de minimizacion
   * @param elitismo Numero de individuos que pasan directamente a la siguiente generacion por ser los mejores
   * @param tipoSobrecruzamiento String la manera en la que se hara el sobrecruzamiento entre los individuos
   * @param mejorInmutable boolean Determina si el individuo mejor esta expuesto a mutacion o no
   */
  public static void torneos (Individuo[] individuos, boolean isMaximizationProblem, int presionSelectiva, int elitismo, String tipoSobrecruzamiento, boolean mejorInmutable) throws Exception{


     /*Hacemos tantos torneos como individius tengamos en al poblacion,
       el numero de individuos por torneo sera los que se indiquen en presion selectiva,
       y dichos individuos seran cogidos al azar para cada torneo*/

       int numIndividuos = individuos.length; //numero de individuos que tiene la poblacion
       int numIndividuo;  //individuo seleccionado
       int numIndividuoMax=0;  //el individuo con mas valoracion
       double maxValoracion; //la valoracion del individuo mejor valorado

       Individuo[] poblacionIntermedia = new Individuo[numIndividuos]; //poblacion resultante de hacer los torneos

       int numTorneo = 0;

       //hacemos tantos torneos como individuos tenga la poblacion restandole los individuos que pasan por ser los mejores (elitistas)
       for (numTorneo = 0; numTorneo < numIndividuos; numTorneo++){
         if (isMaximizationProblem){
           maxValoracion=0;
         }else{
           maxValoracion=Double.MAX_VALUE;
         }
	 //hacemos un torneo y nos quedamos con el que tenga mas (menos) valoracion
	 for (int i = 0; i < presionSelectiva; i++){
	   numIndividuo = ((int)(Math.random()* numIndividuos)) % numIndividuos;
           if (isMaximizationProblem){  //problema de maximizacion
             if (individuos[numIndividuo]._valoracion > maxValoracion) {
               maxValoracion = individuos[numIndividuo]._valoracion;
               numIndividuoMax = numIndividuo;
             }
           }else{  //problema de minimizacion
             if (individuos[numIndividuo]._valoracion < maxValoracion) {
               maxValoracion = individuos[numIndividuo]._valoracion;
               numIndividuoMax = numIndividuo;
             }
           }
	 }
	 poblacionIntermedia[numTorneo] = individuos[numIndividuoMax];
       }

       if (elitismo != 0){  //hay elitismo (deben pasar tambien los mejores individuos)
	   ordenarIndividuosPorValoracion(individuos,isMaximizationProblem);
	   if (mejorInmutable)
	     individuos[0].inmutable = true;
	}

       if (tipoSobrecruzamiento==sobrecruzamientoNinguno){
	 individuos = poblacionIntermedia;
       }else{
	 /*la poblacion resultante de los torneos (la que ha sobrevido por seleccion natural al estar mas adaptada)
	 la tenemos que cruzar para crear una nueva poblacion resultado de la reproduccion entre la anterior generacion*/
	 //Individuo[] poblacionNueva = new Individuo[numIndividuos];

	 /*hacemos tantos cruces como poblacion teniamos inicialmente (menos los que pasan a la siguiente generacion por elitismo)
	   ya que de cada cruce optenemos un individuo nuevo*/

	 int ind1,ind2;
	 for (int i = elitismo; i < numIndividuos; i++){
	   ind1 = ((int)(Math.random()* numIndividuos))% numIndividuos;
	   ind2 = ((int)(Math.random()* numIndividuos))% numIndividuos;
	   individuos[i] = poblacionIntermedia[ind1].cruzar(poblacionIntermedia[ind2],tipoSobrecruzamiento);
	 }
       }
  }


  /**
   * Produce un cromosoma "hibrido" resultado de intercambiar partes del cromosoma 1 y partes del cromosoma2
   * Este intercambio es basicamente dividir cada uno de los dos cromosomas en 2 partes
   * y el cromosoma resultado es poner una parte de cada cromosoma en el (elegida entre las 2 de cada cromosoma aleatoriamente),
   * el orden de si la parte del 1 o la del 2 primero tambien es aleatorio
   *
   * @param cromosoma1
   * @param cromosoma2
   * @param tipoCruce Discriminante para elegir entre las diversas tecnicas para realizar el sobrecruzamiento
   * -"mitad" : se parten los cromosomas a la mitad
   * -"aleatorio": se parten aleatoriamente
   * -"especializado": si 0 y 0 entonces 0, si 1 y 1 entonces 1, si 1 y 0 entonces aleatorio
   * @return Devuelbe el cromosoma resultado del intercambio
   */
  public static int[] sobrecruzamientoEntero (int[] cromosoma1, int[] cromosoma2, String tipoCruce) throws Exception{
    int[] nuevoCromosoma = new int[cromosoma1.length];
    int longitud = cromosoma1.length;

    if (tipoCruce == sobrecruzamientoPartirMitad){
      int mitadCromosoma = cromosoma1.length/2;
      double aleatorio1,aleatorio2,aleatorio3;  //para elegir las partes que se copian al azar
      aleatorio1 = Math.random();
      aleatorio2 = Math.random();
      aleatorio3 = Math.random();

      if (aleatorio1 < 0.5){  //primero ponemos el 1 y luego el 2
	  if (aleatorio2 < 0.5){  //usamos la primera mitad del cromosoma1
	    System.arraycopy(cromosoma1,0,nuevoCromosoma,0,mitadCromosoma);
	    if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma2
	      System.arraycopy(cromosoma2,0,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	    }else{  //usamos la segunda mitad del cromosoma2
	      System.arraycopy(cromosoma2,mitadCromosoma,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	    }
	  }else{ //usamos la segunda mitad del cromosoma1
	    System.arraycopy(cromosoma1,mitadCromosoma,nuevoCromosoma,0,mitadCromosoma);
	    if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma2
	      System.arraycopy(cromosoma2,0,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	    }else{  //usamos la segunda mitad del cromosoma2
	      System.arraycopy(cromosoma2,mitadCromosoma,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	    }
	  }
      }else{  //primero ponemos el 2 y luego el 1
	if (aleatorio2 < 0.5){  //usamos la primera mitad del cromosoma2
	  System.arraycopy(cromosoma2,0,nuevoCromosoma,0,mitadCromosoma);
	  if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma1
	    System.arraycopy(cromosoma1,0,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	  }else{  //usamos la segunda mitad del cromosoma1
	    System.arraycopy(cromosoma1,mitadCromosoma,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	  }
	}else{ //usamos la segunda mitad del cromosoma2
	  System.arraycopy(cromosoma2,mitadCromosoma,nuevoCromosoma,0,mitadCromosoma);
	  if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma1
	    System.arraycopy(cromosoma1,0,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	  }else{  //usamos la segunda mitad del cromosoma1
	    System.arraycopy(cromosoma1,mitadCromosoma,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	  }
	}
      }
    }else if (tipoCruce==sobrecruzamientoSeleccionGen){
      for (int i =0; i < longitud; i++){
	if (cromosoma1[i]  == cromosoma2[i]){
	  nuevoCromosoma[i] = cromosoma1[i];
	}else{
	  if (Math.random() < 0.5){
	    nuevoCromosoma[i] =cromosoma1[i];
	  }else{
	    nuevoCromosoma[i] =cromosoma2[i];
	  }
	}
      }


    }else if (tipoCruce==sobrecruzamientoPartirAleatio){
      int corte = Math.round((float)Math.random()*cromosoma1.length);
       double aleatorio1,aleatorio2,aleatorio3;  //para elegir las partes que se copian al azar
       aleatorio1 = Math.random();
       aleatorio2 = Math.random();
       aleatorio3 = Math.random();

       if (aleatorio1 < 0.5){  //primero ponemos el 1 y luego el 2
	 if (aleatorio2 < 0.5){  //usamos la primera mitad del cromosoma1
	   System.arraycopy(cromosoma1,0,nuevoCromosoma,0,corte);
	   if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma2
	     System.arraycopy(cromosoma2,0,nuevoCromosoma,corte,cromosoma2.length - corte);
	   }else{  //usamos la segunda mitad del cromosoma2
	     System.arraycopy(cromosoma2,corte,nuevoCromosoma,corte,cromosoma2.length -corte);
	   }
	 }else{ //usamos la segunda mitad del cromosoma1
	   System.arraycopy(cromosoma1,corte,nuevoCromosoma,0,cromosoma1.length - corte);
	   if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma2
	     System.arraycopy(cromosoma2,0,nuevoCromosoma,cromosoma2.length -corte,corte);
	   }else{  //usamos la segunda mitad del cromosoma2
	     System.arraycopy(cromosoma2,cromosoma1.length-corte,nuevoCromosoma,cromosoma2.length -corte,corte);
	   }
	 }
       }else{  //primero ponemos el 2 y luego el 1
	 if (aleatorio2 < 0.5){  //usamos la primera mitad del cromosoma2
	   System.arraycopy(cromosoma2,0,nuevoCromosoma,0,corte);
	   if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma1
	     System.arraycopy(cromosoma1,0,nuevoCromosoma,corte,cromosoma1.length - corte);
	   }else{  //usamos la segunda mitad del cromosoma1
	     System.arraycopy(cromosoma1,corte,nuevoCromosoma,corte,cromosoma1.length -corte);
	     }
	 }else{ //usamos la segunda mitad del cromosoma2
	   System.arraycopy(cromosoma2,corte,nuevoCromosoma,0,cromosoma2.length - corte);
	   if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma1
	     System.arraycopy(cromosoma1,0,nuevoCromosoma,cromosoma1.length -corte,corte);
	   }else{  //usamos la segunda mitad del cromosoma1
	     System.arraycopy(cromosoma1,cromosoma1.length-corte,nuevoCromosoma,cromosoma1.length -corte,corte);
	   }
	 }
       }
    }else{
      throw new Exception("Tipo de sobrecruzamiento no soportado "+tipoCruce);
    }
    return nuevoCromosoma;
  }



  /**
   * Produce un cromosoma "hibrido" resultado de intercambiar partes del cromosoma 1 y partes del cromosoma2
   * Este intercambio es basicamente dividir cada uno de los dos cromosomas en 2 partes
   * y el cromosoma resultado es poner una parte de cada cromosoma en el (elegida entre las 2 de cada cromosoma aleatoriamente),
   * el orden de si la parte del 1 o la del 2 primero tambien es aleatorio
   *
   * @param cromosoma1
   * @param cromosoma2
   * @param tipoCruce Discriminante para elegir entre las diversas tecnicas para realizar el sobrecruzamiento
   * -"mitad" : se parten los cromosomas a la mitad
   * -"aleatorio": se parten aleatoriamente
   * -"especializado": si 0 y 0 entonces 0, si 1 y 1 entonces 1, si 1 y 0 entonces aleatorio
   * @return Devuelbe el cromosoma resultado del intercambio
   */
  public static byte[] sobrecruzamientoBinario (byte[] cromosoma1, byte[] cromosoma2, String tipoCruce) throws Exception{

    byte[] nuevoCromosoma = new byte[cromosoma1.length];
    int longitud = cromosoma1.length;

    if (tipoCruce==sobrecruzamientoPartirMitad){
      int mitadCromosoma = cromosoma1.length/2;
      double aleatorio1,aleatorio2,aleatorio3;  //para elegir las partes que se copian al azar
      aleatorio1 = Math.random();
      aleatorio2 = Math.random();
      aleatorio3 = Math.random();

      if (aleatorio1 < 0.5){  //primero ponemos el 1 y luego el 2
	  if (aleatorio2 < 0.5){  //usamos la primera mitad del cromosoma1
	    System.arraycopy(cromosoma1,0,nuevoCromosoma,0,mitadCromosoma);
	    if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma2
	      System.arraycopy(cromosoma2,0,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	    }else{  //usamos la segunda mitad del cromosoma2
	      System.arraycopy(cromosoma2,mitadCromosoma,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	    }
	  }else{ //usamos la segunda mitad del cromosoma1
	    System.arraycopy(cromosoma1,mitadCromosoma,nuevoCromosoma,0,mitadCromosoma);
	    if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma2
	      System.arraycopy(cromosoma2,0,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	    }else{  //usamos la segunda mitad del cromosoma2
	      System.arraycopy(cromosoma2,mitadCromosoma,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	    }
	  }
      }else{  //primero ponemos el 2 y luego el 1
	if (aleatorio2 < 0.5){  //usamos la primera mitad del cromosoma2
	  System.arraycopy(cromosoma2,0,nuevoCromosoma,0,mitadCromosoma);
	  if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma1
	    System.arraycopy(cromosoma1,0,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	  }else{  //usamos la segunda mitad del cromosoma1
	    System.arraycopy(cromosoma1,mitadCromosoma,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	  }
	}else{ //usamos la segunda mitad del cromosoma2
	  System.arraycopy(cromosoma2,mitadCromosoma,nuevoCromosoma,0,mitadCromosoma);
	  if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma1
	    System.arraycopy(cromosoma1,0,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	  }else{  //usamos la segunda mitad del cromosoma1
	    System.arraycopy(cromosoma1,mitadCromosoma,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	  }
	}
      }
    }else if (tipoCruce==sobrecruzamientoSeleccionGen){
      for (int i =0; i < longitud; i++){
	if (cromosoma1[i]  == cromosoma2[i]){
	  nuevoCromosoma[i] = cromosoma1[i];
	}else{
	  if (Math.random() < 0.5){
	    nuevoCromosoma[i] =cromosoma1[i];
	  }else{
	    nuevoCromosoma[i] =cromosoma2[i];
	  }
	}
      }

    }else if (tipoCruce==sobrecruzamientoPartirAleatio){
	  int corte = Math.round((float)Math.random()*cromosoma1.length);
	   double aleatorio1,aleatorio2,aleatorio3;  //para elegir las partes que se copian al azar
	   aleatorio1 = Math.random();
	   aleatorio2 = Math.random();
	   aleatorio3 = Math.random();

	   if (aleatorio1 < 0.5){  //primero ponemos el 1 y luego el 2
	     if (aleatorio2 < 0.5){  //usamos la primera mitad del cromosoma1
	       System.arraycopy(cromosoma1,0,nuevoCromosoma,0,corte);
	       if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma2
		 System.arraycopy(cromosoma2,0,nuevoCromosoma,corte,cromosoma2.length - corte);
	       }else{  //usamos la segunda mitad del cromosoma2
		 System.arraycopy(cromosoma2,corte,nuevoCromosoma,corte,cromosoma2.length -corte);
	       }
	     }else{ //usamos la segunda mitad del cromosoma1
	       System.arraycopy(cromosoma1,corte,nuevoCromosoma,0,cromosoma1.length - corte);
	       if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma2
		 System.arraycopy(cromosoma2,0,nuevoCromosoma,cromosoma2.length -corte,corte);
	       }else{  //usamos la segunda mitad del cromosoma2
		 System.arraycopy(cromosoma2,cromosoma1.length-corte,nuevoCromosoma,cromosoma2.length -corte,corte);
	       }
	     }
	   }else{  //primero ponemos el 2 y luego el 1
	     if (aleatorio2 < 0.5){  //usamos la primera mitad del cromosoma2
	       System.arraycopy(cromosoma2,0,nuevoCromosoma,0,corte);
	       if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma1
		 System.arraycopy(cromosoma1,0,nuevoCromosoma,corte,cromosoma1.length - corte);
	       }else{  //usamos la segunda mitad del cromosoma1
		 System.arraycopy(cromosoma1,corte,nuevoCromosoma,corte,cromosoma1.length -corte);
		 }
	     }else{ //usamos la segunda mitad del cromosoma2
	       System.arraycopy(cromosoma2,corte,nuevoCromosoma,0,cromosoma2.length - corte);
	       if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma1
		 System.arraycopy(cromosoma1,0,nuevoCromosoma,cromosoma1.length -corte,corte);
	       }else{  //usamos la segunda mitad del cromosoma1
		 System.arraycopy(cromosoma1,cromosoma1.length-corte,nuevoCromosoma,cromosoma1.length -corte,corte);
	       }
	     }
       }
    }else{
       throw new Exception("Tipo de sobrecruzamiento no soportado "+tipoCruce);
    }
    return nuevoCromosoma;
  }


  private static void ordenarIndividuosPorValoracion(Individuo[] individuos,boolean isMaximizationProblem){
    int numIndividuos = individuos.length;
    Object[] objetos = new Object[numIndividuos];
    System.arraycopy(individuos,0,objetos,0,individuos.length);
    java.util.Arrays.sort(objetos,individuos[0]);
    if (!isMaximizationProblem){  //el problema es de minimizacion
      for (int i = 0; i < numIndividuos; i++){
        individuos[i] = (Individuo)objetos[numIndividuos-i-1];
      }
    }else{
      System.arraycopy(objetos,0,individuos,0,numIndividuos);
    }
  }



  /**
 * Produce un cromosoma "hibrido" resultado de intercambiar partes del cromosoma 1 y partes del cromosoma2
 * Este intercambio es basicamente dividir cada uno de los dos cromosomas en 2 partes
 * y el cromosoma resultado es poner una parte de cada cromosoma en el (elegida entre las 2 de cada cromosoma aleatoriamente),
 * el orden de si la parte del 1 o la del 2 primero tambien es aleatorio
 *
 * @param cromosoma1
 * @param cromosoma2
 * @param tipoCruce Discriminante para elegir entre las diversas tecnicas para realizar el sobrecruzamiento
 * -"mitad" : se parten los cromosomas a la mitad
 * -"aleatorio": se parten aleatoriamente
 * -"especializado": si 0 y 0 entonces 0, si 1 y 1 entonces 1, si 1 y 0 entonces aleatorio
 * @return Devuelbe el cromosoma resultado del intercambio
 */
public static double[] sobrecruzamientoReal (double[] cromosoma1, double[] cromosoma2, String tipoCruce) throws Exception{
  double[] nuevoCromosoma = new double[cromosoma1.length];
  int longitud = cromosoma1.length;

  if (tipoCruce==sobrecruzamientoPartirMitad){
    int mitadCromosoma = cromosoma1.length/2;
    double aleatorio1,aleatorio2,aleatorio3;  //para elegir las partes que se copian al azar
    aleatorio1 = Math.random();
    aleatorio2 = Math.random();
    aleatorio3 = Math.random();

    if (aleatorio1 < 0.5){  //primero ponemos el 1 y luego el 2
	if (aleatorio2 < 0.5){  //usamos la primera mitad del cromosoma1
	  System.arraycopy(cromosoma1,0,nuevoCromosoma,0,mitadCromosoma);
	  if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma2
	    System.arraycopy(cromosoma2,0,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	  }else{  //usamos la segunda mitad del cromosoma2
	    System.arraycopy(cromosoma2,mitadCromosoma,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	  }
	}else{ //usamos la segunda mitad del cromosoma1
	  System.arraycopy(cromosoma1,mitadCromosoma,nuevoCromosoma,0,mitadCromosoma);
	  if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma2
	    System.arraycopy(cromosoma2,0,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	  }else{  //usamos la segunda mitad del cromosoma2
	    System.arraycopy(cromosoma2,mitadCromosoma,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	  }
	}
    }else{  //primero ponemos el 2 y luego el 1
      if (aleatorio2 < 0.5){  //usamos la primera mitad del cromosoma2
	System.arraycopy(cromosoma2,0,nuevoCromosoma,0,mitadCromosoma);
	if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma1
	  System.arraycopy(cromosoma1,0,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	}else{  //usamos la segunda mitad del cromosoma1
	  System.arraycopy(cromosoma1,mitadCromosoma,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	}
      }else{ //usamos la segunda mitad del cromosoma2
	System.arraycopy(cromosoma2,mitadCromosoma,nuevoCromosoma,0,mitadCromosoma);
	if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma1
	  System.arraycopy(cromosoma1,0,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	}else{  //usamos la segunda mitad del cromosoma1
	  System.arraycopy(cromosoma1,mitadCromosoma,nuevoCromosoma,mitadCromosoma,mitadCromosoma);
	}
      }
    }
  }else if (tipoCruce==sobrecruzamientoSeleccionGen){
    for (int i =0; i < longitud; i++){
      if (cromosoma1[i] == 0 && cromosoma2[i]== 0){
	nuevoCromosoma[i] =0;
      }else if (cromosoma1[i] == 1 && cromosoma2[i] == 1){
	nuevoCromosoma[i] =1;
      }else{
	if (Math.random() < 0.5){
	  nuevoCromosoma[i] =0;
	}else{
	  nuevoCromosoma[i] =1;
	}
      }
    }

  }else if (tipoCruce==sobrecruzamientoPartirAleatio){
      int corte = Math.round((float)Math.random()*cromosoma1.length);
       double aleatorio1,aleatorio2,aleatorio3;  //para elegir las partes que se copian al azar
       aleatorio1 = Math.random();
       aleatorio2 = Math.random();
       aleatorio3 = Math.random();

       if (aleatorio1 < 0.5){  //primero ponemos el 1 y luego el 2
	 if (aleatorio2 < 0.5){  //usamos la primera mitad del cromosoma1
	   System.arraycopy(cromosoma1,0,nuevoCromosoma,0,corte);
	   if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma2
	     System.arraycopy(cromosoma2,0,nuevoCromosoma,corte,cromosoma2.length - corte);
	   }else{  //usamos la segunda mitad del cromosoma2
	     System.arraycopy(cromosoma2,corte,nuevoCromosoma,corte,cromosoma2.length -corte);
	   }
	 }else{ //usamos la segunda mitad del cromosoma1
	   System.arraycopy(cromosoma1,corte,nuevoCromosoma,0,cromosoma1.length - corte);
	   if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma2
	     System.arraycopy(cromosoma2,0,nuevoCromosoma,cromosoma2.length -corte,corte);
	   }else{  //usamos la segunda mitad del cromosoma2
	     System.arraycopy(cromosoma2,cromosoma1.length-corte,nuevoCromosoma,cromosoma2.length -corte,corte);
	   }
	 }
       }else{  //primero ponemos el 2 y luego el 1
	 if (aleatorio2 < 0.5){  //usamos la primera mitad del cromosoma2
	   System.arraycopy(cromosoma2,0,nuevoCromosoma,0,corte);
	   if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma1
	     System.arraycopy(cromosoma1,0,nuevoCromosoma,corte,cromosoma1.length - corte);
	   }else{  //usamos la segunda mitad del cromosoma1
	     System.arraycopy(cromosoma1,corte,nuevoCromosoma,corte,cromosoma1.length -corte);
	     }
	 }else{ //usamos la segunda mitad del cromosoma2
	   System.arraycopy(cromosoma2,corte,nuevoCromosoma,0,cromosoma2.length - corte);
	   if (aleatorio3 < 0.5){  //usamos la primera mitad del cromosoma1
	     System.arraycopy(cromosoma1,0,nuevoCromosoma,cromosoma1.length -corte,corte);
	   }else{  //usamos la segunda mitad del cromosoma1
	     System.arraycopy(cromosoma1,cromosoma1.length-corte,nuevoCromosoma,cromosoma1.length -corte,corte);
	   }
	 }
       }
  }else{
    throw new Exception("Tipo de sobrecruzamiento no soportado "+tipoCruce);
  }
  return nuevoCromosoma;


}



}












