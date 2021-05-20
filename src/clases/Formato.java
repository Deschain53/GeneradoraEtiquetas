/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author donyo
 */
public class Formato {
    String cadena = "";
    
    public Formato(String cadena){
        this.cadena = cadena;
    }
    
    public String codigo(){
        String cad = cadena.trim();

        for (int i = cadena.length(); i < 25; i++){
            cad += " ";
        }
        cad += "";

        return cad;
    }
    
    public String descripcion(){
        String cad = cadena;
        for (int i = cadena.length(); i < 40; i++){
            cad += " ";
        }
        cad += "\t\t";
        return cad;        
    }
    
    public String precio(){
        String cad = cadena;
        String punto = ".";
        if(punto.indexOf(cadena)>=0){
            //cad = cad + "0";
            if (cadena.indexOf(".") == cadena.length() - 2){
                cad = cad + "0";
            }  
        }
        return cad;
    }
    
    
}
