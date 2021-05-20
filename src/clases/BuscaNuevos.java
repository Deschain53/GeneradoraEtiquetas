/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.File;
import java.util.ArrayList;

/**
 * Esta clase busca los productos nuevos entre un ArrayActual y un ArrayAntiguo
 * En el constructor se ingresan dos arrays de String provenientes de dos archivos excel distintos
 *      el primer Array contendra los datos del archivo mas reciente y el segundo los datos del
 *      archivo mas antiguo
 * Esta clase esta adaptada para ser usada mediante hilos
 * @author donyo
 */
public class BuscaNuevos implements Runnable {

    ArrayList<String[]> arrayDatosExcel = null;
    ArrayList<String[]> arrayDatosExcelComparacion = null;
    String[] ProductosNuevos = null;
    int numeroLogicoProductosNuevos = 0;

    public BuscaNuevos(ArrayList<String[]> arrayDatosExcel, ArrayList<String[]> arrayDatosExcelComparacion) {
        this.arrayDatosExcel = arrayDatosExcel;
        this.arrayDatosExcelComparacion = arrayDatosExcelComparacion;
        this.ProductosNuevos = new String[arrayDatosExcelComparacion.size()];
    }

    public String[] getProductosNuevos() {

        return ProductosNuevos;
    }

    @Override
    public void run() {

        int i = 0;

        for (String[] next : arrayDatosExcel) {
            //int i = 0 ;
            boolean bandera = false;
            String codigoModelo = next[0].toUpperCase();

            for (String[] nextC : arrayDatosExcelComparacion) {
                String codigoComparado = nextC[0].toUpperCase();
                if (codigoModelo.equals(codigoComparado)) {     //si se encuentra el codigo indicado significa que el producto existe 
                    bandera = true;
                    break;
                }

            }
                
            if (bandera == false) {                              //Si no se encuentra el codigo indicado significa que el producto no existe
                Formato code = new Formato(next[0]);
                Formato descripcion = new Formato(next[1]);
                Formato precio = new Formato(next[2]);
                ProductosNuevos[i] = code.codigo() + "   \t\t\t   " + descripcion.descripcion() + "\t" + "$" + precio.precio() + "0";
                //System.out.println("Productos nuevo : " + i + ProductosNuevos[i]);
                i++;
            }

            //numeroLogicoProductosNuevos = i;
        }   
        numeroLogicoProductosNuevos = i;
        //System.out.println("Numero Logico de productos nuevos :" + numeroLogicoProductosNuevos);

        /*for (int j = 0; j < numeroLogicoProductosNuevos; j++) {
                if (ProductosNuevos[j] != null && ProductosNuevos[j] != "") {
                    System.out.println(ProductosNuevos[j]);
                }
        }*/  //para pruebas
            
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getNumeroProductosNuevos (){
        return numeroLogicoProductosNuevos ;
    }
    
}
