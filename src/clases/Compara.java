//Clase para buscar datos de dos String dados, 
package clases;

/**
 * Esta clase solo debe usarse para comparar celdas individuales, se usa por clases de niveles superiores para hacer funciones
 * se recomienda NO MODIFICAR, sino simplemente usar     
 * @author donyo
 */
public class Compara {

    private String buscar = "", cadena = "";

    public Compara(String cadena, String buscar) {
        this.buscar = buscar;
        this.cadena = cadena;      
    }

    public boolean Coincidencia() {  //Clase que indica si hay una coincidencia en "cadena" de la  frase buscada, util para los "if"
        boolean bandera = false;

        if (buscar.length() <= cadena.length()) {
            
            for (int i = 0; buscar.length()+i <= cadena.length(); i++) {
                if (bandera == false) {
                    String cadenacortada = cadena.substring(i, buscar.length() + i);
                    if (cadenacortada.equals(buscar)) {
                        bandera = true;
                    }
                }
            }
        }
        return bandera;
    }
    
    public boolean CoincidenciaUpper() {  //Clase que indica si hay una coincidencia en "cadena" de la  frase buscada aumentando todas a mayusculas
        boolean bandera = false;
        String buscar2 = buscar.toUpperCase();
        String cadena2 = cadena.toUpperCase();
        if (buscar.length() <= cadena.length()) {
            
            for (int i = 0; buscar2.length()+i <= cadena2.length(); i++) {
                if (bandera == false) {
                    String cadenacortada = cadena2.substring(i, buscar2.length() + i);
                    if (cadenacortada.equals(buscar2)) {
                        bandera = true;
                    }
                }
            }
            
        }
        return bandera;
    }    
    
    public boolean CoincidenciaLower() {  //Clase que indica si hay una coincidencia en "cadena" de la  frase buscada reduciendo todas a minusculas
        boolean bandera = false;
        String buscar3 = buscar.toUpperCase();
        String cadena3 = cadena.toUpperCase();
        if (buscar.length() <= cadena.length()) {
            
            for (int i = 0, j = 0; buscar3.length()+i <= cadena3.length(); i++) {
                if (bandera == false) {
                    String cadenacortada = cadena3.substring(i, buscar3.length() + i);
                    if (cadenacortada.equals(buscar3)) {
                        bandera = true;
                    }
                }
            }
            
        }
        return bandera;
    }       

}