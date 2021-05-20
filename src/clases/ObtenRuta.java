/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author donyo
 */
public class ObtenRuta {

    String ruta = "";   //en realidad puede ser cualquiera /CarpetaPrueba/excel.xls
    File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;

    public ObtenRuta(String rutaImportado) throws IOException{  //constructor
        this.archivo = new File(rutaImportado); //"C:/Program Files (x86)/GeneraEtiquetas/rutas/import.txt"
        //try {
            obtenRutaImportado();
            /*try {
            fr = new FileReader(archivo);
            } catch (FileNotFoundException ex) {
            Logger.getLogger(ObtenRuta.class.getName()).log(Level.SEVERE, null, ex);
            }
            br = new BufferedReader(fr);*/
        //} catch (IOException ex) {
           // Logger.getLogger(ObtenRuta.class.getName()).log(Level.SEVERE, null, ex);
        //}
    }

    private void obtenRutaImportado() throws IOException {

        if (archivo.exists()) {

            try {
                // Apertura del fichero y creacion de BufferedReader para poder
                // hacer una lectura comoda (disponer del metodo readLine()).

                //Agregar si archivo existe
                fr = new FileReader(archivo.getAbsolutePath());
                br = new BufferedReader(fr);

                // Lectura del fichero
                ruta = br.readLine();
                //while ((ruta = br.readLine()) != null) {   //ELiminar while y d
                //System.out.println(ruta);//para testeo
                //}
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                // En el finally cerramos el fichero, para asegurarnos
                // que se cierra tanto si todo va bien como si salta 
                // una excepcion.
                try {
                    //if (null != fr) {
                        fr.close();
                    //}
                } catch (IOException e2) {
                    System.out.println(e2);
                }
            }

        }else{
            //archivo = new File(rutaImportado);//"C:/Users/donyo/Documents/NetBeansProjects/GenerarEtiquetas/rutas/import.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
                //String imp = archivo.getAbsolutePath();
                bw.write(ruta);
                bw.close();
        }

    }

    public String getRuta(){
        //obtenRutaImportado();
        
        return ruta;
    }
}
