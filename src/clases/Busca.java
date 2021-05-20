/*
 * Esta clase lo que hace es buscar un valor String dado en una columna NO LOGICA de una hoja de excel
 * es decir, el numero tal y como lo ves en la hoja, omitiendo indices logicos
 * 
 */
package clases;

import clases.Compara;
import clases.ReadCellData;

public class Busca {

    private int sheetI = 0, columna = 1, filaInicio = 1;
    private String subCadena = "", cadena = "";     //Esta es la frase o string que se  va a buscar
    private int tamanoIndex = 21;
    private int[] index = new int[tamanoIndex];              //Numero de elementos maximo que podran mostrar coincidencia
    private int filaFinal = 0;                      //Tamano de la columna
    private String ruta = "";

    public Busca(int sheetI, int columna, int filaInicio, int filaFinal, String subCadena, String ruta) {
        this.sheetI = sheetI;
        this.columna = columna;
        this.subCadena = subCadena;
        this.filaInicio = filaInicio;
        this.filaFinal = filaFinal;
        this.ruta = ruta.trim();
        for (int i = 0; i < tamanoIndex; i++) {
            index[i] = -1;
        }
    }

    public int Index() {    //Regresael indice del primer valor de la coincidencia

        for (int i = 0, j = 0; i < filaFinal; i++) {   //en laa condicional del if debe de ir el tamano de la columna de excel donde se leeran datos
            ReadCellData rc2 = new ReadCellData(1, 1 + i, ruta);
            cadena = rc2.read();

            Compara c2 = new Compara(cadena, subCadena);

            if (c2.CoincidenciaUpper() == true) {
                index[j] = i + 1;
                if (j < tamanoIndex - 1) {
                    j++;
                }
            }
            if (j == tamanoIndex - 1 ) {
                break;
            }
        }

        int primerIndex = index[0];

        return primerIndex;
    }

    public int[] IndexArray() {    //Regresa en un arreglo los valores de los indices en donde hubo coincidencia

        for (int i = 0, j = 0; i < filaFinal; i++) {   //en laa condicional del if debe de ir el tamano de la columna de excel donde se leeran datos
            ReadCellData rc2 = new ReadCellData(1, 1 + i, ruta);
            cadena = rc2.read();
            Compara c2 = new Compara(cadena, subCadena);
            if (c2.CoincidenciaUpper() == true) {   //Antes era simplemente coincidencia
                index[j] = i + 1;
                if (j < tamanoIndex - 1) {
                    j++;
                }
            }
            if (j == tamanoIndex - 1) {
                break;
            }
        }
        return index;
    }

}
