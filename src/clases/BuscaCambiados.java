/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.util.ArrayList;

/**
 * Esta clase compara los precios que cambiaron entre dos Arrays
 * En el constructor se ingresan dos arrays de String provenientes de dos archivos excel distintos
 *      el primer Array contendra los datos del archivo mas reciente y el segundo los datos del
 *      archivo mas antiguo
 * Esta clase esta adaptada para ser usada mediante hilos
 * @author donyo
 */
public class BuscaCambiados implements Runnable {

    ArrayList<String[]> arrayDatosExcel = null;
    ArrayList<String[]> arrayDatosExcelComparacion = null;
    String[] ProductosCambiados = null;
    int numeroLogicoProductosCambiados = 0;

    public BuscaCambiados(ArrayList<String[]> arrayDatosExcel, ArrayList<String[]> arrayDatosExcelComparacion) {
        this.arrayDatosExcel = arrayDatosExcel;
        this.arrayDatosExcelComparacion = arrayDatosExcelComparacion;
        this.ProductosCambiados = new String[arrayDatosExcelComparacion.size()];
    }

    public String[] getProductosCambiados() {
        return ProductosCambiados;
    }

    @Override
    public void run() {
        int i = 0;
        for (String[] next : arrayDatosExcel) {

            //String codigo = next[0];                                   //Obtendra el codigo
            String precioActual = next[2].toUpperCase();                 //Obtendra el precio actual del producto al que el codigo corresponde

            for (String[] nextC : arrayDatosExcelComparacion) {

                if (next[0].trim().equals(nextC[0].trim())) {           //se se encuentra el codigo indicado                         
                    String precioAntiguo = nextC[2].toUpperCase();
                    if (precioActual.equals(precioAntiguo) == false) {  //Si los precios cambiaron
                        Formato code = new Formato(next[0]);
                        Formato descripcion = new Formato(next[1]);
                        Formato precio = new Formato(next[2]);
                        ProductosCambiados[i] = code.codigo() + "   \t\t\t   " + descripcion.descripcion() + "\t" + "$" + precio.precio() + "0";
                        i++;
                        //System.out.println("Producto cambiado " + i + ": " + ProductosCambiados[i]);
                        break;
                    }
                }
            }
        }
        numeroLogicoProductosCambiados = i;
        //System.out.println("Numero de productos cambiados :" + i);
        
    }

    public int getNumeroProductosCambiados() {
        return numeroLogicoProductosCambiados;
    }

}
