package ColoniaHormigas;

/**
 * Define como se debe codificar cada problema en concreto
 */
public abstract class Problema {

	/**
	 * Debe definirse para cada problema en concreto la matriz de costes
	 * @return int[] Matriz de costes definiada para el problema concreto
	 */
	public abstract double[][] getCostesEntrePosiciones();

	//_____________Metodos que pueden ser sobrescritos_______________________
	public int valorDefectoNumeroHormigas(){ return 10; }
	public long valorDefectoIteracionesMax(){ return  100; }
	public double valorDefectoCosteAObtener(){ return 0; }
	public double valorDefectoFactorEvaporacionLocal(){ return 0.1; }
	public double valorDefectoFactorEvaporacionGlobal(){ return 0.1; }
	public double valorDefectoCosteAproximadoTrayecto(){ return 100; }
	public double valorDefectoFactorExplotacionInicial(){ return 0.1; }
	public boolean valorDefectoIncrementoDinamicoFactorExploracion(){ return true; }
	public String ayuda(){ return "Sin ayuda"; }

	/**
	 * Sirve para instanciar problemas especificos que no necesiten argumentos indicando la ruta completa
	 * @param pathProblema String path completo donde se encuentra el problema (ej: TempleSimulado.Problemas.ProblemaAjustarPesosRNA)
	 * @exception Excepciones gneeradas si el path del problema no es valido
	 * @return Problema problema concreto instanciado
	 */
	public static Problema  instanciarProblema(String pathProblema) throws Exception{
	  Class claseProblema =Class.forName(pathProblema);
	  return ((Problema)claseProblema.newInstance());
	}

}
