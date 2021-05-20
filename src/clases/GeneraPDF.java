package clases;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeEAN;

/**
 * MODIIFCAR ATRIBUTOS DE LAS SUBCLASES PARA PODER HACERLOS EDITABLES EN EL MODO
 * AVANZADO
 *
 * @author donyo
 */
public class GeneraPDF {

    private String NombreArchivo = "";
    private String directorio = "";
    private String[] codigo = null;
    private String[] descripcion = null;
    private String[] precio = null;

    private int numColumnasTabla = 4;
    private int tamanoDescripcion = 13;
    private int tamanoPrecio = 40;
    private int tamanoCodigo = 6;
    private int numeroCeldas = 100;

    public GeneraPDF(String NombreArchivo, String[] codigo, String[] descripcion, String[] precio) {
        this.NombreArchivo = NombreArchivo;
        this.directorio = "/Desktop/";               //En un futuro se puede ampliar para elegir el directorio donde se guardara el pdf
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;

    }

    //Constructor sobrecargado
    public GeneraPDF(String NombreArchivo, String[] codigo, String[] descripcion, String[] precio,
            int numColumnasTabla, int tamanoCodigo, int tamanoDescripcion, int tamanoPrecio) {
        this.NombreArchivo = NombreArchivo;
        this.directorio = "/Desktop/";               //En un futuro se puede ampliar para elegir el directorio donde se guardara el pdf
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.numColumnasTabla = numColumnasTabla;
        this.tamanoCodigo = tamanoCodigo;
        this.tamanoDescripcion = tamanoDescripcion;
        this.tamanoPrecio = tamanoPrecio;
    }

    public GeneraPDF(String productosPrueba, String[] productos, String[] precios) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void generaEtiquetasChicasDescripcionPrecio() {

        try {
            Document documento = new Document();
            documento.setMargins(10, 10, 10, 10);       // (izq, der, arriba, abajo)

            try {
                String ruta = System.getProperty("user.home");
                PdfWriter.getInstance(documento, new FileOutputStream(ruta + directorio + NombreArchivo.trim() + ".pdf"));
                documento.open();
                PdfPTable tabla = new PdfPTable(numColumnasTabla); //Entre parentesis decir columas que tendra la tabla
                tabla.setWidthPercentage(100);      //permite que se rellene la hoja con porcentaje indicado
                tabla.setHorizontalAlignment(Element.ALIGN_CENTER);    //Para establecer la alineacion horizontal                

                for (int i = 0; i < descripcion.length; i++) {
                    //int tamano = 13;

                    Paragraph paragraph = new Paragraph();
                    paragraph.clear();
                    paragraph.setFont(new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL));
                    paragraph.setAlignment(Element.ALIGN_CENTER);

                    if (descripcion[i].length() > 30 && descripcion[i].length() < 60) {
                        paragraph.add(new Chunk(descripcion[i] + "\n\n", new Font(Font.FontFamily.UNDEFINED, 10, Font.BOLD)));

                        //tamanoDescripcion = 10;
                    } else {
                        paragraph.add(new Chunk(descripcion[i] + "\n\n", new Font(Font.FontFamily.UNDEFINED, tamanoDescripcion, Font.BOLD)));
                    }
                    paragraph.add(new Chunk("$" + precio[i] + "0", new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                    PdfPCell cell = new PdfPCell();
                    cell.setPaddingTop(0);
                    cell.setMinimumHeight(320f);
                    cell.setFixedHeight(70f);
                    cell.addElement(paragraph);

                    //cell.setColspan(1);                 //clase para agrupar celdas, si se desean agrupar tres celdas pooner tres
                    tabla.addCell(cell);
                    paragraph.clear();

                }

                tabla.addCell("");
                tabla.addCell("");  //Se podria hacer con mas paragraphs
                tabla.addCell("");  //Esto se tiene agregar para que los demas elementos PdfPCell sean visibles (no se porque)hacer un codigo para evitar agregar de mas               
                documento.add(tabla);   //agrega la tabla al documento
                documento.close();

            } catch (DocumentException e) { //agregar esto para que la imagen no muestre error
                System.out.println(e);
            }

        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el fichero para generar PDF" + e);
        }

    }

    public void generaEtiquetasMedianasDescripcionPrecio() {

        try {
            Document documento = new Document();
            documento.setMargins(10, 10, 10, 10);       // (izq, der, arriba, abajo)

            try {
                String ruta = System.getProperty("user.home");
                PdfWriter.getInstance(documento, new FileOutputStream(ruta + directorio + NombreArchivo.trim() + ".pdf"));
                documento.open();
                PdfPTable tabla = new PdfPTable(numColumnasTabla); //Entre parentesis decir columas que tendra la tabla
                tabla.setWidthPercentage(100);      //permite que se rellene la hoja con porcentaje indicado
                tabla.setHorizontalAlignment(Element.ALIGN_CENTER);    //Para establecer la alineacion horizontal                

                for (int i = 0; i < descripcion.length; i++) {
                    //int tamano = 13;
                    /*if (descripcion[i].length() > 35 && descripcion[i].length() < 60) {
                        tamanoDescripcion = 12;
                    }*/
                    Paragraph paragraph = new Paragraph();
                    paragraph.clear();
                    paragraph.setFont(new Font(Font.FontFamily.UNDEFINED, 10, Font.NORMAL));
                    paragraph.setAlignment(Element.ALIGN_CENTER);

                    if (descripcion[i].length() > 30 && descripcion[i].length() < 60) {
                        paragraph.add(new Chunk(descripcion[i] + "\n", new Font(Font.FontFamily.UNDEFINED, 12, Font.BOLD)));
                    } else {
                        paragraph.add(new Chunk(descripcion[i] + "\n", new Font(Font.FontFamily.UNDEFINED, tamanoDescripcion, Font.BOLD)));
                    }

                    Paragraph paragraph2 = new Paragraph();
                    paragraph2.clear();

                    if (precio[i].indexOf(".") == precio[i].length() - 2) {
                        //Para darle formato al precio, es decir, hacer que los decimales del precio se pongan mas chicos
                        String precioInt = precio[i].substring(0, precio[i].length() - 2);
                        String precioDec = precio[i].substring(precio[i].length() - 2);
                        paragraph2.add(new Chunk("\n\n" + "$" + precioInt, new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                        paragraph2.add(new Chunk(precioDec + "0", new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio - 10, Font.BOLD)));

                    } else {
                        paragraph2.add(new Chunk("\n\n" + "$" + precio[i] + "0", new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                    }

                    //paragraph2.add(new Chunk("\n\n" + "$" + precio[i] + "0", new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                    paragraph2.setPaddingTop(-2);
                    paragraph2.setAlignment(Element.ALIGN_CENTER);
                    PdfPCell cell = new PdfPCell();
                    cell.setPaddingTop(5);
                    cell.setMinimumHeight(700f);
                    cell.setFixedHeight(95f);//90
                    cell.addElement(paragraph);
                    cell.addElement(paragraph2);
                    //cell.setColspan(1);                 //clase para agrupar celdas, si se desean agrupar tres celdas pooner tres
                    tabla.addCell(cell);
                    paragraph.clear();

                }

                tabla.addCell("");
                tabla.addCell("");  //Se podria hacer con mas paragraphs
                tabla.addCell("");  //Esto se tiene agregar para que los demas elementos PdfPCell sean visibles (no se porque)hacer un codigo para evitar agregar de mas               
                documento.add(tabla);   //agrega la tabla al documento
                documento.close();

            } catch (DocumentException e) { //agregar esto para que la imagen no muestre error
                System.out.println(e);
            }

        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el fichero para generar PDF" + e);
        }

    }

    public void generaEtiquetasGrandesDescripcionPrecio() {  ///FALTA CORREGIR LA SUPERPOSICION DE LAS LETRAS EN LA DESCRIPCION
            ///CHECAR QUE ERROR HAY AQUI Y VERIFICAR EN DONDE SE ENCUENTRA Y COMO ARREGLARLO, SOSPECHO QUE ES EN LOS IFS4
        try {
            Document documento = new Document();
            documento.setMargins(10, 10, 10, 10);       // (izq, der, arriba, abajo)

            try {
                String ruta = System.getProperty("user.home");
                PdfWriter.getInstance(documento, new FileOutputStream(ruta + directorio + NombreArchivo.trim() + ".pdf"));
                documento.open();
                PdfPTable tabla = new PdfPTable(numColumnasTabla); //Entre parentesis decir columas que tendra la tabla
                tabla.setWidthPercentage(100);      //permite que se rellene la hoja con porcentaje indicado
                tabla.setHorizontalAlignment(Element.ALIGN_CENTER);    //Para establecer la alineacion horizontal                

                for (int i = 0; i < descripcion.length; i++) {
                    if (descripcion[i].length() > 30 && descripcion[i].length() < 60) {
                        tamanoDescripcion = tamanoDescripcion - 10;
                    }

                    int indexCortador = 0;
                    int aux = descripcion[i].length() / 2;
                    int tamanoDecimalesPrecio = tamanoPrecio - 30;

                    String parte1 = "";
                    String parte2 = "";
                    indexCortador = descripcion[i].indexOf(" ", aux);

                    if (indexCortador > 0 && descripcion[i].length() > 16) {
                        //System.out.println(descripcion[i] + "\t" + descripcion[i].length() + "\t" + indexCortador);
                        parte1 = descripcion[i].substring(0, indexCortador).trim();
                        //System.out.println(descripcion[i] + "\t" + parte1);
                        parte2 = descripcion[i].substring(indexCortador).trim();
                        //System.out.println(descripcion[i] + "\t" + parte2);
                    } else {
                        parte1 = descripcion[i];
                    }

                    Paragraph paragraph = new Paragraph();
                    paragraph.clear();
                    paragraph.setFont(new Font(Font.FontFamily.UNDEFINED, tamanoDescripcion, Font.NORMAL));
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    //paragraph.setPaddingTop(5);//5

                    paragraph.add(new Chunk("\n" + parte1 + "\n\n" + parte2, new Font(Font.FontFamily.UNDEFINED, tamanoDescripcion, Font.BOLD)));
                    //paragraph.setPaddingTop(10);
                    Paragraph paragraph2 = new Paragraph();
                    paragraph2.clear();

                    if (precio[i].indexOf(".") == precio[i].length() - 2) {
                        //Para darle formato al precio, es decir, hacer que los decimales del precio se pongan mas chicos
                        String precioInt = precio[i].substring(0, precio[i].length() - 2);
                        String precioDec = precio[i].substring(precio[i].length() - 2);

                        if (descripcion[i].length() < 17) { //para darle mejor formato
                            paragraph2.add(new Chunk("\n\n" + "$" + precioInt, new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                            paragraph2.add(new Chunk(precioDec + "0", new Font(Font.FontFamily.TIMES_ROMAN, tamanoDecimalesPrecio, Font.BOLD)));    //antes -20
                        } else {
                            paragraph2.add(new Chunk("\n\n\n" + "$" + precioInt, new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                            paragraph2.add(new Chunk(precioDec + "0", new Font(Font.FontFamily.TIMES_ROMAN, tamanoDecimalesPrecio, Font.BOLD)));    //antes -20
                        }

                    } else if (precio[i].indexOf(".") == precio[i].length() - 3) {
                        String precioInt = precio[i].substring(0, precio[i].length() - 2);
                        String precioDec = precio[i].substring(precio[i].length() - 2);

                        if (descripcion[i].length() < 17) { //para darle mejor formato
                            paragraph2.add(new Chunk("\n\n" + "$" + precioInt, new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                            paragraph2.add(new Chunk(precioDec, new Font(Font.FontFamily.TIMES_ROMAN, tamanoDecimalesPrecio, Font.BOLD)));    //antes -20
                        } else {
                            paragraph2.add(new Chunk("\n\n\n" + "$" + precioInt, new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                            paragraph2.add(new Chunk(precioDec, new Font(Font.FontFamily.TIMES_ROMAN, tamanoDecimalesPrecio, Font.BOLD)));    //antes -20
                        }

                    } else {

                        if (descripcion[i].length() < 17) { //para darle mejor formato
                            paragraph2.add(new Chunk("\n\n" + "$" + precio[i] + "0", new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                        } else {
                            paragraph2.add(new Chunk("\n\n\n" + "$" + precio[i] + "0", new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                        }

                    }

                    //paragraph2.setPaddingTop(5);
                    paragraph2.setAlignment(Element.ALIGN_CENTER);
                    PdfPCell cell = new PdfPCell();
                    cell.setPaddingTop(5);
                    if (descripcion[i].length() > 16) {
                        cell.setPaddingTop(-5);
                    }
                    cell.setMinimumHeight(800f);
                    cell.setFixedHeight(130f);
                    cell.addElement(paragraph);
                    cell.addElement(paragraph2);
                    tabla.addCell(cell);
                    paragraph.clear();
                    paragraph2.clear();
                    
                    if (descripcion[i].length() > 30 && descripcion[i].length() < 60) {
                        tamanoDescripcion = tamanoDescripcion + 10;
                    }
                    
                }

                tabla.addCell("");
                tabla.addCell("");  //Se podria hacer con mas paragraphs
                tabla.addCell("");  //Esto se tiene agregar para que los demas elementos PdfPCell sean visibles (no se porque)hacer un codigo para evitar agregar de mas               
                documento.add(tabla);   //agrega la tabla al documento
                documento.close();

            } catch (DocumentException e) { //agregar esto para que la imagen no muestre error
                System.out.println(e);
            }

        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el fichero para generar PDF" + e);
        }

    }

    public void generaEtiquetasJumboDescripcionPrecio() {  ///FALTA CORREGIR LA SUPERPOSICION DE LAS LETRAS EN LA DESCRIPCION

        try {
            Document documento = new Document();
            documento.setMargins(10, 10, 10, 10);       // (izq, der, arriba, abajo)

            try {
                String ruta = System.getProperty("user.home");
                PdfWriter.getInstance(documento, new FileOutputStream(ruta + directorio + NombreArchivo.trim() + ".pdf"));
                documento.open();
                PdfPTable tabla = new PdfPTable(numColumnasTabla); //Entre parentesis decir columas que tendra la tabla
                tabla.setWidthPercentage(100);      //permite que se rellene la hoja con porcentaje indicado
                tabla.setHorizontalAlignment(Element.ALIGN_CENTER);    //Para establecer la alineacion horizontal                

                for (int i = 0; i < descripcion.length; i++) {
                    if (descripcion[i].length() > 30 && descripcion[i].length() < 60) {
                        tamanoDescripcion = tamanoDescripcion - 5;
                    }

                    int indexCortador = 0;
                    int aux = descripcion[i].length() / 2;
                    if(aux > 13){
                        aux = 6;
                    }
                    int tamanoDecimalesPrecio = tamanoPrecio - 40;

                    String parte1 = "";
                    String parte2 = "";
                    String parte3 = "";
                    indexCortador = descripcion[i].indexOf(" ", aux);

                    if (indexCortador > 0 && descripcion[i].length() > 13) {
                        //System.out.println(descripcion[i] + "\t" + descripcion[i].length() + "\t" + indexCortador);
                        parte1 = descripcion[i].substring(0, indexCortador).trim();
                        parte2 = descripcion[i].substring(indexCortador).trim();
                        //System.out.println(descripcion[i] + "\t" + parte1);
                        if((parte2.length()) > 13 ){
                            parte2 = descripcion[i].substring(indexCortador, indexCortador + 13).trim();
                            parte3 =  "\n\n\n\n" + descripcion[i].substring(indexCortador + 13).trim();
                        } 
                           // parte2 = descripcion[i].substring(indexCortador, indexCortador + 12).trim();//indexCortador +13
                            //parte3 = descripcion[i].substring(indexCortador + 12).trim() + "\n\n\n\n";
                        
                        //System.out.println(descripcion[i] + "\t" + parte2);
                    } else {
                        parte1 = descripcion[i];
                    }

                    Paragraph paragraph = new Paragraph();
                    paragraph.clear();
                    paragraph.setFont(new Font(Font.FontFamily.UNDEFINED, tamanoDescripcion, Font.NORMAL));
                    paragraph.setAlignment(Element.ALIGN_CENTER);
                    //paragraph.setPaddingTop(5);//5

                    if (parte2.length() < 7) {
                        parte2 = parte2 + "\n";
                    }

                    //paragraph.add(new Chunk("\n" + parte1 + "\n\n" + parte2 + "\n\n\n\n\n\n", new Font(Font.FontFamily.UNDEFINED, tamanoDescripcion, Font.BOLD)));
                    paragraph.add(new Chunk("\n" + parte1 + "\n\n\n\n" + parte2 + parte3 + "\n\n\n\n\n\n", new Font(Font.FontFamily.UNDEFINED, tamanoDescripcion, Font.BOLD)));
                    //paragraph.setPaddingTop(10);
                    Paragraph paragraph2 = new Paragraph();
                    paragraph2.clear();
                    //paragraph2.setPaddingTop();

                    if (precio[i].indexOf(".") == precio[i].length() - 2) {
                        //Para darle formato al precio, es decir, hacer que los decimales del precio se pongan mas chicos
                        String precioInt = precio[i].substring(0, precio[i].length() - 2);
                        String precioDec = precio[i].substring(precio[i].length() - 2);
                        //System.out.println("precio entero: " + precioInt);
                        if (descripcion[i].length() < 17) { //para darle mejor formato
                            if (precioInt.length() < 3) {
                                paragraph2.add(new Chunk("\n\n" + "$" + precioInt, new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                            } else {
                                paragraph2.add(new Chunk("\n\n" + "$" + precioInt, new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio - 40, Font.BOLD)));
                            }
                            paragraph2.add(new Chunk(precioDec + "0", new Font(Font.FontFamily.TIMES_ROMAN, tamanoDecimalesPrecio, Font.BOLD)));    //antes -20
                        } else {
                            paragraph2.add(new Chunk("\n\n\n" + "$" + precioInt, new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                            paragraph2.add(new Chunk(precioDec + "0", new Font(Font.FontFamily.TIMES_ROMAN, tamanoDecimalesPrecio, Font.BOLD)));    //antes -20
                        }

                    } else if (precio[i].indexOf(".") == precio[i].length() - 3) {
                        String precioInt = precio[i].substring(0, precio[i].length() - 2);
                        String precioDec = precio[i].substring(precio[i].length() - 2);

                        if (descripcion[i].length() < 17) { //para darle mejor formato
                            if (precioInt.length() < 3) {
                                paragraph2.add(new Chunk("\n\n" + "$" + precioInt, new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                            } else {
                                paragraph2.add(new Chunk("\n\n" + "$" + precioInt, new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio - 40, Font.BOLD)));
                            }
                            paragraph2.add(new Chunk(precioDec, new Font(Font.FontFamily.TIMES_ROMAN, tamanoDecimalesPrecio, Font.BOLD)));    //antes -20
                        } else {
                            paragraph2.add(new Chunk("\n\n\n" + "$" + precioInt, new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                            paragraph2.add(new Chunk(precioDec, new Font(Font.FontFamily.TIMES_ROMAN, tamanoDecimalesPrecio, Font.BOLD)));    //antes -20
                        }

                    } else {

                        if (descripcion[i].length() < 17) { //para darle mejor formato
                            paragraph2.add(new Chunk("\n\n" + "$" + precio[i] + "0", new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                        } else {
                            paragraph2.add(new Chunk("\n\n\n" + "$" + precio[i] + "0", new Font(Font.FontFamily.TIMES_ROMAN, tamanoPrecio, Font.BOLD)));
                        }

                    }

                    //paragraph2.setPaddingTop(5);
                    paragraph2.setAlignment(Element.ALIGN_CENTER);
                    PdfPCell cell = new PdfPCell();
                    cell.setPaddingTop(30);
                    if (descripcion[i].length() > 16) {
                        //cell.setPaddingTop(-5);
                    }
                    cell.setMinimumHeight(250f);
                    cell.setFixedHeight(386f);
                    cell.addElement(paragraph);
                    cell.addElement(paragraph2);
                    tabla.addCell(cell);
                    paragraph.clear();
                    if (descripcion[i].length() > 30 && descripcion[i].length() < 60) {
                        tamanoDescripcion = tamanoDescripcion + 5;
                    }

                }

                tabla.addCell("");
                tabla.addCell("");  //Se podria hacer con mas paragraphs
                tabla.addCell("");  //Esto se tiene agregar para que los demas elementos PdfPCell sean visibles (no se porque)hacer un codigo para evitar agregar de mas               
                documento.add(tabla);   //agrega la tabla al documento
                documento.close();

            } catch (DocumentException e) { //agregar esto para que la imagen no muestre error
                System.out.println(e);
            }

        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el fichero para generar PDF" + e);
        }

    }

    //Para generar etiquetitas pequeñas predeterminadas
    public void generaEtiquetitasBarcode() {

        try {
            Document documento = new Document();
            documento.setMargins(10, 10, 10, 10); // (izq, der, arriba, abajo)

            try {

                String ruta = System.getProperty("user.home");
                PdfWriter pd = PdfWriter.getInstance(documento, new FileOutputStream(ruta + directorio + NombreArchivo.trim() + ".pdf"));
                documento.open();

                PdfPTable tabla = new PdfPTable(numColumnasTabla);  //Entre parentesis decir columas que tendra la tabla
                tabla.setWidthPercentage(100);       //permite que se rellene la hoja con porcentaje indicado
                tabla.setHorizontalAlignment(Element.ALIGN_CENTER); //Para establecer alineacion                

                Barcode128 code128 = new Barcode128();
                code128.setBaseline(0);    //Establece la separacion hacia abajo del texto respecto a el barcode
                code128.setSize(1);        //establece el tamano del texto dentro de la celda
                code128.setCode(codigo[0]);
                code128.setCodeType(Barcode.CODE128);   //el numero despues de ean indica cuantos elementos contendra como maximo                             

                Image codeImage = code128.createImageWithBarcode(pd.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                codeImage.setAlignment(Element.ALIGN_CENTER);

                PdfPCell celda = new PdfPCell();    //(codeImage,true);
                celda.setPadding(5);
                celda.addElement(codeImage);
                celda.setMinimumHeight(50f);
                celda.setBorderColor(BaseColor.LIGHT_GRAY);

                if (codigo[0].length() == 4) {
                    numeroCeldas = 112;
                }

                for (int i = 0; i < numeroCeldas; i++) {
                    tabla.addCell(celda);
                }

                tabla.addCell("");  //Esto se tiene agregar para que los demas elementos PdfPCell sean visibles )no se porque=                
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                documento.add(tabla);
                documento.close();

            } catch (DocumentException e) { //agregar esto para que la imagen no muestre error
                System.out.println(e);
            }

        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el fichero para generar PDF" + e);
        }

    }        //modificado ultimo

    public void generaEtiquetitasBarcodeCode() {

        try {
            Document documento = new Document();
            documento.setMargins(10, 10, 10, 10); // (izq, der, arriba, abajo)

            try {

                String ruta = System.getProperty("user.home");
                // PdfWriter pd = PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/ProbandoClases.pdf")); //en donde dice Desktop va la biblioteca
                PdfWriter pd = PdfWriter.getInstance(documento, new FileOutputStream(ruta + directorio + NombreArchivo.trim() + ".pdf"));
                documento.open();

                PdfPTable tabla = new PdfPTable(numColumnasTabla);  //Entre parentesis decir columas que tendra la tabla
                tabla.setWidthPercentage(100);       //permite que se rellene la hoja con porcentaje indicado
                tabla.setHorizontalAlignment(Element.ALIGN_CENTER); //Para establecer alineacion                

                Barcode128 code128 = new Barcode128();
                code128.setBaseline(7);    //Establece la separacion hacia abajo del texto respecto a el barcode
                code128.setSize(tamanoCodigo);        //establece el tamano del texto dentro de la celda
                code128.setCode(codigo[0]);
                code128.setCodeType(Barcode.CODE128);   //el numero despues de ean indica cuantos elementos contendra como maximo                             

                Image codeImage = code128.createImageWithBarcode(pd.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                codeImage.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragraph = new Paragraph();
                paragraph.clear();
                paragraph.setFont(new Font(Font.FontFamily.UNDEFINED, tamanoDescripcion, Font.BOLD));
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.add(new Chunk(descripcion[0], new Font(Font.FontFamily.UNDEFINED, tamanoDescripcion, Font.BOLD)));
                PdfPCell celda = new PdfPCell();    //(codeImage,true);
                celda.setPadding(5);
                celda.addElement(codeImage);
                celda.setMinimumHeight(50f);
                celda.setBorderColor(BaseColor.LIGHT_GRAY);

                if (codigo[0].length() == 4) {
                    numeroCeldas = 91;
                }

                for (int i = 0; i < numeroCeldas; i++) {
                    tabla.addCell(celda);
                }

                tabla.addCell("");  //Esto se tiene agregar para que los demas elementos PdfPCell sean visibles )no se porque=                
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                documento.add(tabla);
                documento.close();

            } catch (DocumentException e) { //agregar esto para que la imagen no muestre error
                System.out.println(e);
            }

        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el fichero para generar PDF" + e);
        }

    }   //modificado ultimo

    public void generaEtiquetitasBarcodeDescripcion() {

        try {
            Document documento = new Document();
            documento.setMargins(10, 10, 10, 10); // (izq, der, arriba, abajo)

            try {

                String ruta = System.getProperty("user.home");
                PdfWriter pd = PdfWriter.getInstance(documento, new FileOutputStream(ruta + directorio + NombreArchivo.trim() + ".pdf"));
                documento.open();

                PdfPTable tabla = new PdfPTable(numColumnasTabla);   //Entre parentesis decir columas que tendra la tabla   5
                tabla.setWidthPercentage(100);       //permite que se rellene la hoja con porcentaje indicado
                tabla.setHorizontalAlignment(Element.ALIGN_CENTER); //Para establecer alineacion                

                Barcode128 code128 = new Barcode128();
                code128.setBaseline(0);    //Establece la separacion hacia abajo del texto respecto a el barcode
                code128.setSize(1);        //establece el tamano del texto dentro de la celda
                code128.setCode(codigo[0]);
                code128.setCodeType(Barcode.CODE128);   //el numero despues de ean indica cuantos elementos contendra como maximo                             

                Image codeImage = code128.createImageWithBarcode(pd.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                codeImage.setAlignment(Element.ALIGN_CENTER);
                //codeImage.setScaleToFitHeight(true);

                Paragraph paragraph = new Paragraph();
                paragraph.clear();
                paragraph.setFont(new Font(Font.FontFamily.UNDEFINED, tamanoDescripcion, Font.BOLD));       //10
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.add(new Chunk(descripcion[0], new Font(Font.FontFamily.UNDEFINED, tamanoDescripcion, Font.BOLD)));   //10
                PdfPCell celda = new PdfPCell();    //(codeImage,true);                
                celda.setPaddingBottom(4);
                celda.setPaddingLeft(4);
                celda.setPaddingRight(4);
                celda.setPaddingTop(-5);
                celda.addElement(paragraph);
                celda.addElement(codeImage);
                celda.setMinimumHeight(50f);    //50f
                celda.setBorderColor(BaseColor.LIGHT_GRAY);

                if (codigo[0].length() == 4) {
                    numeroCeldas = 105;
                }

                for (int i = 0; i < numeroCeldas; i++) {
                    tabla.addCell(celda);
                }

                tabla.addCell("");  //Esto se tiene agregar para que los demas elementos PdfPCell sean visibles )no se porque=                
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                documento.add(tabla);
                documento.close();

            } catch (DocumentException e) { //agregar esto para que la imagen no muestre error
                System.out.println(e);
            }

        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el fichero para generar PDF" + e);
        }

    }       //modificado ultimo

    public void generaEtiquetitasBarcodeCodeDescripcion() {

        try {
            Document documento = new Document();
            documento.setMargins(10, 10, 10, 10); // (izq, der, arriba, abajo)

            try {

                String ruta = System.getProperty("user.home");
                PdfWriter pd = PdfWriter.getInstance(documento, new FileOutputStream(ruta + directorio + NombreArchivo.trim() + ".pdf"));
                documento.open();

                PdfPTable tabla = new PdfPTable(numColumnasTabla);  //Entre parentesis decir columas que tendra la tabla   //7
                tabla.setWidthPercentage(100);       //permite que se rellene la hoja con porcentaje indicado
                tabla.setHorizontalAlignment(Element.ALIGN_CENTER); //Para establecer alineacion                

                Barcode128 code128 = new Barcode128();
                code128.setBaseline(5);    //Establece la separacion hacia abajo del texto respecto a el barcode
                code128.setSize(6);        //establece el tamano del texto dentro de la celda   //6
                code128.setCode(codigo[0]);
                code128.setCodeType(Barcode.CODE128);   //el numero despues de ean indica cuantos elementos contendra como maximo                             

                Image codeImage = code128.createImageWithBarcode(pd.getDirectContent(), BaseColor.BLACK, BaseColor.BLACK);
                codeImage.setAlignment(Element.ALIGN_CENTER);

                Paragraph paragraph = new Paragraph();
                paragraph.clear();
                paragraph.setFont(new Font(Font.FontFamily.UNDEFINED, tamanoDescripcion, Font.BOLD));
                paragraph.setAlignment(Element.ALIGN_CENTER);
                paragraph.add(new Chunk(descripcion[0], new Font(Font.FontFamily.UNDEFINED, tamanoDescripcion, Font.BOLD))); //para etiquetitas configuracion normal tamanodescripcion es de 6
                PdfPCell celda = new PdfPCell();    //(codeImage,true);
                //celda.setPadding(0);
                celda.setPaddingBottom(1);
                celda.setPaddingLeft(4);
                celda.setPaddingRight(4);
                celda.setPaddingTop(-5);
                celda.setBorderColor(BaseColor.LIGHT_GRAY);
                celda.addElement(paragraph);
                celda.addElement(codeImage);
                celda.setMinimumHeight(40f);

                if (codigo[0].length() == 4) {
                    numeroCeldas = 91;
                }

                for (int i = 0; i < numeroCeldas; i++) {
                    tabla.addCell(celda);
                }

                tabla.addCell("");  //Esto se tiene agregar para que los demas elementos PdfPCell sean visibles )no se porque=                
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                tabla.addCell("");
                documento.add(tabla);
                documento.close();

            } catch (DocumentException e) { //agregar esto para que la imagen no muestre error
                System.out.println(e);
            }

        } catch (FileNotFoundException e) {
            System.out.println("No se encontro el fichero para generar PDF" + e);
        }

    }   //modificado

    public void setNumColumnasTabla(int numColumnasTabla) {
        this.numColumnasTabla = numColumnasTabla;
    }

    public void setTamanoDescripcion(int tamanoDescripcion) {
        this.tamanoDescripcion = tamanoDescripcion;
    }

    public void setTamanoPrecio(int tamanoPrecio) {
        this.tamanoPrecio = tamanoPrecio;
    }

    public void setTamanoCodigo(int tamanoCodigo) {
        this.tamanoCodigo = tamanoCodigo;
    }

    public void setNumeroCeldas(int numeroCeldas) {
        this.numeroCeldas = numeroCeldas;
    }

}
