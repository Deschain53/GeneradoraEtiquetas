/*
 * Esta clase tiene metodos que extraen un arreglo de numeros con las posiciones de las coincidencias del String a buscar
 * dentro de cierta columna de una hoja de calculo
 * En  el numero de filas debes poner cuantas filas con datos existen dentro de la hoja, es importante para evitar errores o perdida de informacion
 * Dependiendo de en que columna quieras buscar es el metodo que elegiras, siendo Primera para la columna 1 no logica y 
 */
package clases;
import clases.Busca;
/**
 *
 * @author donyo
 */
public class BuscaColumna {
    private int numeroFilas = 6935;       //50 
    private String buscar = "", ruta ="C:\\demo\\Inventarios14082020.xlsx";

    public BuscaColumna(String buscar) {
        this.buscar = buscar;
    }

    public int[] Primera(){
        Busca b1 = new Busca(0, 1, 1, numeroFilas, buscar,ruta.trim());
        int[] array = b1.IndexArray();
        return array;
    }
    
    public int[] Segunda(){
        Busca b1 = new Busca(0, 2, 1, numeroFilas, buscar,ruta.trim());
        int[] array = b1.IndexArray();
        return array;
    }
    
    public int[] Tercera(){
        Busca b1 = new Busca(0, 2, 1, numeroFilas, buscar,ruta.trim());
        int[] array = b1.IndexArray();
        return array;
    }    
    
}
