//Clase para leer una celda indicando el indice de la columna y fila no logicos(Asi como se ve en excel)
//Se recomienda NO MODIFICAR
package clases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadCellData {

    private int columna, fila;
    private boolean bandera;
    private String ruta = "";

    public ReadCellData(int columna, int fila, String ruta) {
        this.columna = columna;
        this.fila = fila;
        this.ruta = ruta.trim();
    }

    public String read() {
        String value = "";
        Workbook wb = null;

        try {
            FileInputStream fis = new FileInputStream(ruta);
            wb = new XSSFWorkbook(fis);

        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e1) {
            e1.printStackTrace();

        }

        Sheet sheet = wb.getSheetAt(0);         //getting the XSSFSheet object at given index , en este caso de la hoja con indice logico 0
        Row row = sheet.getRow(this.fila - 1);
        Cell cell = row.getCell(this.columna - 1);

        if (cell != null) {
            bandera = true;
        }

        FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
        
        switch (formulaEvaluator.evaluateInCell(cell).getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                value = Float.toString((float) cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
        }

        return value;
    }

    public boolean tieneDatos() {
        return bandera;
    }

}
