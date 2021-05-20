/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/////////
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/*import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;*/
/**
 * Clase que lee los datos de un archivo Excel y los carga en un Array de String 
 * Esta modificado para leer archivos .xls y .xlsx pero es importante que para los 
 * primeros las celdas esten tengan fondo blanco y de preferencia tengan formato de texto y no numerico 
 * (Estar libres de formato)
 * @author Jorge
 */
public class LeeDatosExcel {

    File excelFile = null;

    public LeeDatosExcel(String rutaExcel) {
        this.excelFile = new File(rutaExcel);

    }

    /**
     * Explanation of the method by which we read the excel file we pass as
     * parameter if exists, we return the excel file values in an ArrayList<>.
     * Explicación del método con el que leemos el fichero excel que pasamos
     * como parámetro si existe, devolvemos los valores de la hoja excel en un
     * ArrayList<>.
     * <h3>Example (Ejemplo)</h3>
     * <pre>
     * JavaPoiUtils javaPoiUtils = new JavaPoiUtils();
     * javaPoiUtils.readExcelFile(new File("/home/xules/codigoxules/apachepoi/PaisesIdiomasMonedas.xls"));
     * </pre>
     *
     * @param excelFile <code>String</code> excel File we are going to read.
     * Fichero excel que vamos a leer.
     * @return <code>ArrayList<String[]></code> we return the excel file values
     * in an ArrayList<> (devolvemos los valores de la hoja excel en un
     * ArrayList<>).
     */
    public ArrayList<String[]> readExcelFileToArray() {

        ArrayList<String[]> arrayDatos = new ArrayList<>();
        InputStream excelStream = null;

        if (excelFile.getAbsolutePath().endsWith("xls")) {
            try {
                ////AQUI SE PODRIA AGREGAR CODIGO PARA QUE SE PUEDA LEER EXCEL EN VERSION 2003 Y +2007
                excelStream = new FileInputStream(excelFile);
                // High level representation of a workbook.
                // Representación del más alto nivel de la hoja excel.
                HSSFWorkbook hssfWorkbook = new HSSFWorkbook(excelStream);
                // We chose the sheet is passed as parameter. 
                // Elegimos la hoja que se pasa por parámetro.
                HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
                // An object that allows us to read a row of the excel sheet, and extract from it the cell contents.
                // Objeto que nos permite leer un fila de la hoja excel, y de aquí extraer el contenido de las celdas.
                HSSFRow hssfRow = hssfSheet.getRow(hssfSheet.getTopRow());
                String[] datos = new String[hssfRow.getLastCellNum()];
                // For this example we'll loop through the rows getting the data we want
                // Para este ejemplo vamos a recorrer las filas obteniendo los datos que queremos
                    //HSSFCellStyle style = hssfWorkbook.createCellStyle();     //Era un intento de ponerle color a la celda
                    //style.setFillBackgroundColor(IndexedColors.WHITE.getIndex());   //Extraera el indice del color blanco para aplicarlo a la celda
		//style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
                for (Row row : hssfSheet) {
                    for (Cell cell : row) {
                        /* 
                    We have those cell types (tenemos estos tipos de celda): 
                        CELL_TYPE_BLANK, CELL_TYPE_NUMERIC, CELL_TYPE_BLANK, CELL_TYPE_FORMULA, CELL_TYPE_BOOLEAN, CELL_TYPE_ERROR
                         */

                            //cell.setCellStyle(style);//codigo agregado dew prueba
                        
                        datos[cell.getColumnIndex()]
                                = (cell.getCellType() == Cell.CELL_TYPE_STRING) ? cell.getStringCellValue()
                                : (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? "" + cell.getNumericCellValue()
                                : (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) ? "" + cell.getBooleanCellValue()
                                : (cell.getCellType() == Cell.CELL_TYPE_BLANK) ? "BLANK"
                                : (cell.getCellType() == Cell.CELL_TYPE_FORMULA) ? "FORMULA"
                                : (cell.getCellType() == Cell.CELL_TYPE_ERROR) ? "ERROR" : "";
                    }
                    arrayDatos.add(datos);
                    datos = new String[hssfRow.getLastCellNum()];
                }
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("The file not exists (No se encontró el fichero): " + fileNotFoundException);
            } catch (IOException ex) {
                System.out.println("Error in file procesing (Error al procesar el fichero): " + ex);
            } finally {
                try {
                    excelStream.close();
                } catch (IOException ex) {
                    System.out.println("Error in file processing after close it (Error al procesar el fichero después de cerrarlo): " + ex);
                }
            }
            
        } else if (excelFile.getAbsolutePath().endsWith("xlsx")) {
            //Para leer version 2007
            try {

                excelStream = new FileInputStream(excelFile);
                // High level representation of a workbook.
                // Representación del más alto nivel de la hoja excel.
                XSSFWorkbook xssfWorkbook = new XSSFWorkbook(excelStream);
                // We chose the sheet is passed as parameter. 
                // Elegimos la hoja que se pasa por parámetro.
                XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
                // An object that allows us to read a row of the excel sheet, and extract from it the cell contents.
                // Objeto que nos permite leer un fila de la hoja excel, y de aquí extraer el contenido de las celdas.
                XSSFRow xssfRow = xssfSheet.getRow(xssfSheet.getTopRow());
                String[] datos = new String[xssfRow.getLastCellNum()];
                // For this example we'll loop through the rows getting the data we want
                // Para este ejemplo vamos a recorrer las filas obteniendo los datos que queremos            
                for (Row row : xssfSheet) {
                    for (Cell cell : row) {
                        /* 
                    We have those cell types (tenemos estos tipos de celda): 
                        CELL_TYPE_BLANK, CELL_TYPE_NUMERIC, CELL_TYPE_BLANK, CELL_TYPE_FORMULA, CELL_TYPE_BOOLEAN, CELL_TYPE_ERROR
                         */
                        datos[cell.getColumnIndex()]
                                = (cell.getCellType() == Cell.CELL_TYPE_STRING) ? cell.getStringCellValue()
                                : (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) ? "" + cell.getNumericCellValue()
                                : (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) ? "" + cell.getBooleanCellValue()
                                : (cell.getCellType() == Cell.CELL_TYPE_BLANK) ? "BLANK"
                                : (cell.getCellType() == Cell.CELL_TYPE_FORMULA) ? "FORMULA"
                                : (cell.getCellType() == Cell.CELL_TYPE_ERROR) ? "ERROR" : "";
                    }
                    arrayDatos.add(datos);
                    datos = new String[xssfRow.getLastCellNum()];
                }
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("The file not exists (No se encontró el fichero): " + fileNotFoundException);
            } catch (IOException ex) {
                System.out.println("Error in file procesing (Error al procesar el fichero): " + ex);
            } finally {
                try {
                    excelStream.close();
                } catch (IOException ex) {
                    System.out.println("Error in file processing after close it (Error al procesar el fichero después de cerrarlo): " + ex);
                }
            }

        }else{
                    JOptionPane.showMessageDialog(null, "El tipo de archivo seleccionado no es EXCEL \n"
                            + "Por favor seleccione un tipo de archivo valido para continuar", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return arrayDatos;
    }

    public void setExcelFile(File excelFile) {
        this.excelFile = excelFile;
    }

}
