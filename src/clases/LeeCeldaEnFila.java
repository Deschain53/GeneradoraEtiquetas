/*
 * Esta clase lee una celda de una columna. recibe un int con el numero de fila a buscar y 
 * dependiendo del metodo sera la columna en donde leera la celda del numero de fila correspondiente
 * La cree para ahorrarme trabajo en el futuro pero podria modificarse
 */
package clases;

/**
 *
 * @author donyo
 */
public class LeeCeldaEnFila {
    private int fila = 1;
    private String ruta ="C:\\demo\\Inventarios14082020.xlsx";
    
    public LeeCeldaEnFila( int fila){
        this.fila = fila;
    }
        
    public String primera(){
        ReadCellData rcd= new ReadCellData(1, fila, ruta);
        String resultado = rcd.read();
        return resultado;
    }
    
    public String segunda(){
        ReadCellData rcd= new ReadCellData(2, fila, ruta);
        String resultado = rcd.read();
        return resultado;        
    }    
    
    public String tercera(){
        ReadCellData rcd= new ReadCellData(1, fila, ruta);
        String resultado = rcd.read();
        return resultado;
    }    
}
