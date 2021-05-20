/*
 * EN ESTA VERSION YA COMPLETAMENTE FUNCIONAL, SOLO FALTAN MEJORAR PEQUENOS DETALLES 
 *  LISTA DE CAMBIOS POR HACER Y FUNCIONES POR AGREGAR:
 * -Modificar los metodos de GeneraPDF para que se reduzca el numero de columnas en la tabla de acuerdo con el largo
 *  del codigo indicado (para mejor legilbildad). Mejor se hara a mano para codigos del 2 al 5
  *  PARA VERSION 5:
 * -Agregar funcion para generar etiquetitas de productos que no esten en la base de datos (En la parte de diseno se
 *  debe agregar un jboton para elegir esta opcion o que funcione con base de datos, ademas que al selecionar opcion sin 
 *  base de datos se oculte el boton de mostrar y se muestre un jlabel con "precio" y un txtPrecio). Al presionar opcion
 *  de generar etiquetas se debe borrar el contenido de estos campos mostrar ventanita indicando que se genero el pdf
 * -Mejorar los botones para que se pueda seleccinar que contenidos agregar a la etiquetita
 * -Funcion de indicarle en que columna esta cada tipo de dato(codigo,descripcion,precio).Puede ser con numeros
 * -Opcion para previsualizar en un jPanel el archivo pdf que se esta creando
Amanita subcokeri
 */
package ventanas;

//import clases.BuscaCambiados;
import javax.swing.JOptionPane;
/*import clases.BuscaColumna;
import clases.LeeCeldaEnFila;
import clases.ReadCellData;*/
import clases.Formato;
import javax.swing.DefaultListModel;
import clases.GeneraPDF;
import clases.LeeDatosExcel;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import clases.BuscaNuevos;
import clases.ObtenRuta;
//import java.io.BufferedReader;
import java.io.BufferedWriter;
//import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author donyo
 */
public class GenerarEtiquetasV4 extends javax.swing.JFrame {

    DefaultListModel defaultLM_Resultados = new DefaultListModel();
    DefaultListModel defaultLM_Agregados = new DefaultListModel();

    LeeDatosExcel javaPoiUtils = null;    //aqui ira la ruta por defecto de archivo excel para evitar errores se debe insertar un archivo de prueba  al inicio
    ArrayList<String[]> arrayDatosExcel = null;       //estaba originalmmente

    int r = 0;
    int contadorAgregados = 0;
    String descripcioncambiada = "";
    String direccionImportado;
    ConfiguracionExcel ce = new ConfiguracionExcel();
    boolean configuracionExcelAbierta = false;
    /**
     * Creates new form GenerarEtiquetas
     */
    public GenerarEtiquetasV4() {
        initComponents();
        this.setSize(850, 600);       //Establece el tamano del componente
        this.setResizable(false);    //Evita que el usuario pueda modificar el tamano de la interfaz
        this.setTitle("Genera etiquetas");
        this.setLocationRelativeTo(null);
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/IconoGeneraEtiquetas.png")));

        defaultLM_Resultados = new DefaultListModel();
        jList_Resultados.setModel(defaultLM_Resultados);
        defaultLM_Agregados = new DefaultListModel();
        jList_Agregados.setModel(defaultLM_Agregados); //Antes Jlist_agregados

        jLabel_SelecioneTamano.setVisible(true);
        jRadioButton_EtiquetasChicas.setVisible(true);
        jRadioButton_EtiquetasMedianas.setVisible(true);
        jRadioButton_EtiquetasGrandes.setVisible(true);
        jRadioButton_EtiquetasJumbo.setVisible(true);
        jLabel_SeleccioneFormato.setVisible(false);
        jRadioButton_Barcode.setVisible(false);
        jRadioButton_BarcodeCode.setVisible(false);
        jRadioButton_BarcodeDescription.setVisible(false);
        jRadioButton_BarcodeCodeDescription.setVisible(false);
        jButton_CambiarDescripcion.setVisible(false);
        txtCambiarDescripcion.setText("");
        txtCambiarDescripcion.setVisible(false);
        jButton_AgregarTodos.setVisible(true);
        
        //OPTIMIZAR CODIGO TANTO DE LA CLASE COMO EL CODIGO PRINCIPAL 
        //se recomienda reducir el minimo la creacion de objetos o copiar el codigo lector aqui y sustituir el 
        //rutaImport de abajo con alguna ruta random. El punto es reducir el tiempo de procesamiento
        direccionImportado = "import.txt";  //El nombre del archivo de texto del que se extraera la ruta del ultimo archivo importado
        //direccionImportado = "C:\\Users\\donyo\\Desktop\\GeneraEtiquetas\\rutas/import.txt";
        //direccionImportado ="C:\\Users\\Windows 7\\Desktop\\GeneraEtiquetas/import.txt";
        String rutaImport = "";
        ObtenRuta or;
        
        try {
            or = new ObtenRuta(direccionImportado); //checar
            rutaImport = or.getRuta();
        } catch (IOException ex) {
            Logger.getLogger(GenerarEtiquetasV4.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
        File f = new File(rutaImport);  //Es para que busque el ultimo archivo que se abrio
        if (f.exists()) {  // && f.isDirectory()
            javaPoiUtils = new LeeDatosExcel(f.getAbsolutePath());
            jLabel_RutaArchivo.setText(f.getAbsolutePath());
            arrayDatosExcel = javaPoiUtils.readExcelFileToArray();

        } else {
            JOptionPane.showMessageDialog(null, "Importe un archivo de excel para poder leer datos");
        }

        txt_Codigo.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    busca();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });
        //Metodo para que al pulsar enter luego de escribir el texto se ejecute el metodo buscar
        txt_Descripcion.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    busca();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }

        });
        
        ce.setVisible(false);
    
        }//llave del finally
    }//FINALIZA CONSTRUCTOR

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jRadioButton_Codigo = new javax.swing.JRadioButton();
        jRadioButton_Descripcion = new javax.swing.JRadioButton();
        txt_Codigo = new javax.swing.JTextField();
        txt_Descripcion = new javax.swing.JTextField();
        jButton_Buscar = new javax.swing.JButton();
        jLabel_Agregados = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList_Resultados = new javax.swing.JList<>();
        jButton_Eliminar = new javax.swing.JButton();
        jButton_Generar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList_Agregados = new javax.swing.JList<>();
        jButton_Agregar = new javax.swing.JButton();
        jButton_EliminarTodos = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txt_NombreArchivo = new javax.swing.JTextField();
        jRadioButton_Varios = new javax.swing.JRadioButton();
        jRadioButton_Unico = new javax.swing.JRadioButton();
        jLabel_SelecioneTamano = new javax.swing.JLabel();
        jRadioButton_EtiquetasChicas = new javax.swing.JRadioButton();
        jRadioButton_EtiquetasMedianas = new javax.swing.JRadioButton();
        jRadioButton_EtiquetasGrandes = new javax.swing.JRadioButton();
        jLabel_SeleccioneFormato = new javax.swing.JLabel();
        jRadioButton_Barcode = new javax.swing.JRadioButton();
        jRadioButton_BarcodeCode = new javax.swing.JRadioButton();
        jRadioButton_BarcodeDescription = new javax.swing.JRadioButton();
        jRadioButton_BarcodeCodeDescription = new javax.swing.JRadioButton();
        jButton_AgregarTodos = new javax.swing.JButton();
        jButton_CambiarDescripcion = new javax.swing.JButton();
        txtCambiarDescripcion = new javax.swing.JTextField();
        jButton_Importar = new javax.swing.JButton();
        jLabel_RutaArchivo = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton_Informacion = new javax.swing.JButton();
        jButton_Compara = new javax.swing.JButton();
        jRadioButton_EtiquetasJumbo = new javax.swing.JRadioButton();
        jButton_ConfiguracionExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText("Seleccione funcionalidad:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 98, 216, -1));

        buttonGroup1.add(jRadioButton_Codigo);
        jRadioButton_Codigo.setText("Codigo:");
        getContentPane().add(jRadioButton_Codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 20, -1, -1));

        buttonGroup1.add(jRadioButton_Descripcion);
        jRadioButton_Descripcion.setSelected(true);
        jRadioButton_Descripcion.setText("Descripcion:");
        getContentPane().add(jRadioButton_Descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 58, -1, -1));

        txt_Codigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txt_CodigoMousePressed(evt);
            }
        });
        txt_Codigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_CodigoActionPerformed(evt);
            }
        });
        getContentPane().add(txt_Codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(129, 22, 259, -1));

        txt_Descripcion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txt_DescripcionMousePressed(evt);
            }
        });
        txt_Descripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_DescripcionActionPerformed(evt);
            }
        });
        getContentPane().add(txt_Descripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 60, 346, -1));

        jButton_Buscar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton_Buscar.setText("Buscar");
        jButton_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_BuscarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(406, 15, 98, 37));

        jLabel_Agregados.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel_Agregados.setText("0");
        getContentPane().add(jLabel_Agregados, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 227, 30, 20));

        jList_Resultados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList_Resultados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList_ResultadosMouseClicked(evt);
            }
        });
        jList_Resultados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_ResultadosKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jList_Resultados);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 98, 467, 105));

        jButton_Eliminar.setText("Eliminar producto");
        jButton_Eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EliminarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 523, 134, -1));

        jButton_Generar.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jButton_Generar.setText("Generar etiquetas");
        jButton_Generar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_GenerarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Generar, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 520, 311, -1));

        jList_Agregados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList_Agregados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList_AgregadosMouseClicked(evt);
            }
        });
        jList_Agregados.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jList_AgregadosKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jList_Agregados);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 266, 467, 245));

        jButton_Agregar.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton_Agregar.setText("Agregar");
        jButton_Agregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AgregarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 119, -1));

        jButton_EliminarTodos.setText("Eliminar todos");
        jButton_EliminarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_EliminarTodosActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_EliminarTodos, new org.netbeans.lib.awtextra.AbsoluteConstraints(189, 523, 134, -1));

        jLabel3.setText("Nombre del archivo :");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 450, 127, 24));

        txt_NombreArchivo.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txt_NombreArchivo.setText("Etiquetas");
        getContentPane().add(txt_NombreArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 480, 309, 29));

        buttonGroup2.add(jRadioButton_Varios);
        jRadioButton_Varios.setSelected(true);
        jRadioButton_Varios.setText("Generar PDF con etiquetas de varios productos ");
        jRadioButton_Varios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_VariosActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton_Varios, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 126, 310, -1));

        buttonGroup2.add(jRadioButton_Unico);
        jRadioButton_Unico.setText("Generar PDF de etiquetas de un solo producto ");
        jRadioButton_Unico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_UnicoActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton_Unico, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 166, 342, -1));

        jLabel_SelecioneTamano.setText("Seleccione tamaño:");
        getContentPane().add(jLabel_SelecioneTamano, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 266, 230, -1));

        buttonGroup3.add(jRadioButton_EtiquetasChicas);
        jRadioButton_EtiquetasChicas.setText("Etiquetas chicas");
        getContentPane().add(jRadioButton_EtiquetasChicas, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 298, 144, -1));

        buttonGroup3.add(jRadioButton_EtiquetasMedianas);
        jRadioButton_EtiquetasMedianas.setText("Etiquetas medianas");
        getContentPane().add(jRadioButton_EtiquetasMedianas, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 338, -1, -1));

        buttonGroup3.add(jRadioButton_EtiquetasGrandes);
        jRadioButton_EtiquetasGrandes.setText("Etiquetas grandes");
        jRadioButton_EtiquetasGrandes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_EtiquetasGrandesActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton_EtiquetasGrandes, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 378, 144, -1));

        jLabel_SeleccioneFormato.setText("Seleccione formato de etiqueta:");
        getContentPane().add(jLabel_SeleccioneFormato, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 266, 210, -1));

        buttonGroup4.add(jRadioButton_Barcode);
        jRadioButton_Barcode.setText("Solo codigo de barras");
        getContentPane().add(jRadioButton_Barcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 298, 230, -1));

        buttonGroup4.add(jRadioButton_BarcodeCode);
        jRadioButton_BarcodeCode.setText("Codigo de barras y codigo numerico");
        getContentPane().add(jRadioButton_BarcodeCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 338, 250, -1));

        buttonGroup4.add(jRadioButton_BarcodeDescription);
        jRadioButton_BarcodeDescription.setText("Codigo de barras y descripcion");
        getContentPane().add(jRadioButton_BarcodeDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 378, 220, -1));

        buttonGroup4.add(jRadioButton_BarcodeCodeDescription);
        jRadioButton_BarcodeCodeDescription.setSelected(true);
        jRadioButton_BarcodeCodeDescription.setText("Codigo de barras, codigo numerico y descripcion");
        getContentPane().add(jRadioButton_BarcodeCodeDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 418, 320, -1));

        jButton_AgregarTodos.setText("Agregar todo");
        jButton_AgregarTodos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_AgregarTodosActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_AgregarTodos, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 222, 119, -1));

        jButton_CambiarDescripcion.setText("Cambiar descripcion");
        jButton_CambiarDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_CambiarDescripcionActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_CambiarDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 222, 130, -1));
        getContentPane().add(txtCambiarDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 221, 290, -1));

        jButton_Importar.setText("Importar base de datos");
        jButton_Importar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ImportarActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Importar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, -1, -1));

        jLabel_RutaArchivo.setBackground(new java.awt.Color(204, 204, 204));
        getContentPane().add(jLabel_RutaArchivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 60, 290, 30));

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel4.setText("Productos agregados:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 227, 170, -1));

        jButton_Informacion.setFont(new java.awt.Font("Cooper Std Black", 1, 12)); // NOI18N
        jButton_Informacion.setText("i");
        jButton_Informacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_InformacionActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Informacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 520, 40, 30));

        jButton_Compara.setFont(new java.awt.Font("Cooper Std Black", 1, 10)); // NOI18N
        jButton_Compara.setText("C");
        jButton_Compara.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ComparaActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_Compara, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 520, 40, 30));

        buttonGroup3.add(jRadioButton_EtiquetasJumbo);
        jRadioButton_EtiquetasJumbo.setText("Etiquetas jumbo");
        jRadioButton_EtiquetasJumbo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton_EtiquetasJumboActionPerformed(evt);
            }
        });
        getContentPane().add(jRadioButton_EtiquetasJumbo, new org.netbeans.lib.awtextra.AbsoluteConstraints(522, 418, 200, -1));

        jButton_ConfiguracionExcel.setText("A");
        jButton_ConfiguracionExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_ConfiguracionExcelActionPerformed(evt);
            }
        });
        getContentPane().add(jButton_ConfiguracionExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_CodigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_CodigoActionPerformed
        //jRadioButton_Codigo.setSelected(true);
        //jRadioButton_Descripcion.setSelected(false);*/
    }//GEN-LAST:event_txt_CodigoActionPerformed

    private void jButton_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_BuscarActionPerformed
        busca();
    }//GEN-LAST:event_jButton_BuscarActionPerformed

    //Metodo para buscar una palabra especifica dentro de una columna de excel
    private void busca() {
        //arrayDatosExcel = javaPoiUtils.readExcelFileToArray();//codigo agregado para buscar en el archivo importado

        if (jRadioButton_Codigo.isSelected() && jLabel_RutaArchivo.getText().equals("") == false && txt_Codigo.getText().equals("") == false) {
            defaultLM_Resultados.removeAllElements();
            txt_Descripcion.setText("");
            //limpia los resultados de busqueda anteriores
            String busca = txt_Codigo.getText().trim();

            for (String[] next : arrayDatosExcel) {
                int c = 0;
                String cadenaUpper = next[c].toUpperCase();
                if (cadenaUpper.indexOf(busca.toUpperCase()) >= 0) {
                    Formato codigo = new Formato(next[0]);
                    Formato descripcion = new Formato(next[1]);
                    Formato precio = new Formato(next[2]);
                    defaultLM_Resultados.addElement(codigo.codigo() + "   \t\t\t   " + descripcion.descripcion() + "\t" + "$" + precio.precio() + "0");
                }
            }

            jList_Resultados.setModel(defaultLM_Resultados);
            txt_Codigo.setText("");
            //Aqui puede ir un metodo para hacer como si el raton hubiese pulsado en dicho txt

        } else if (jRadioButton_Codigo.isSelected() && jLabel_RutaArchivo.getText().equals("") == true) {
            JOptionPane.showMessageDialog(this, "Importe un archivo de excel para poder leer datos", null, JOptionPane.ERROR_MESSAGE);
        }

        if (jRadioButton_Descripcion.isSelected() && jLabel_RutaArchivo.getText().equals("") == false && txt_Descripcion.getText().equals("") == false) {
            defaultLM_Resultados.removeAllElements();     //busca los resultados de busqueda anteriores
            txt_Codigo.setText("");
            String busca = txt_Descripcion.getText().trim();

            for (String[] next : arrayDatosExcel) {
                int c = 1;
                String cadenaUpper = next[c].toUpperCase();
                if (cadenaUpper.indexOf(busca.toUpperCase()) >= 0) {
                    Formato codigo = new Formato(next[0]);
                    Formato descripcion = new Formato(next[1]);
                    Formato precio = new Formato(next[2]);
                    defaultLM_Resultados.addElement(codigo.codigo() + "   \t\t\t   " + descripcion.descripcion() + "\t" + "$" + precio.precio() + "0");
                }
            }

            jList_Resultados.setModel(defaultLM_Resultados);
            txt_Descripcion.setText("");

        } else if (jRadioButton_Descripcion.isSelected() && jLabel_RutaArchivo.getText().equals("") == true) {
            JOptionPane.showMessageDialog(this, "Importe un archivo de excel para poder leer datos", null, JOptionPane.ERROR_MESSAGE);
        }

        if (jRadioButton_Descripcion.isSelected() == false && jRadioButton_Codigo.isSelected() == false) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una opción para empezar busqueda");
        }
    }


    private void jButton_AgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AgregarActionPerformed
        //int indexResultados =jList_Resultados.getSelectedIndex();
        Agrega();
    }//GEN-LAST:event_jButton_AgregarActionPerformed

    private void Agrega() {
        if (jList_Resultados.getSelectedValue() != null) {
            String selectedText = jList_Resultados.getSelectedValue();
            //System.out.println(jList_Resultados.getSelectedValue());  Para fines de testeo
            defaultLM_Agregados.addElement(selectedText);
            jList_Agregados.setModel(defaultLM_Agregados);
            contadorAgregados = contadorAgregados + 1;
            jLabel_Agregados.setText("" + contadorAgregados);
        }
        txt_Codigo.setText("");
        txt_Descripcion.setText("");
    }

    private void jButton_EliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EliminarActionPerformed
        Elimina();
    }//GEN-LAST:event_jButton_EliminarActionPerformed

    private void Elimina() {
        if (jList_Agregados.getSelectedValue() != null) {
            defaultLM_Agregados.remove(jList_Agregados.getSelectedIndex());
            jList_Agregados.setModel(defaultLM_Agregados);
            contadorAgregados = contadorAgregados - 1;
            jLabel_Agregados.setText("" + contadorAgregados);
        }
    }

    private void jButton_GenerarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_GenerarActionPerformed
        arrayDatosExcel = javaPoiUtils.readExcelFileToArray();      //agregado para funcionalidad de selecionar lo indicado
        String nombreArchivo = txt_NombreArchivo.getText();
        ListModel model = jList_Agregados.getModel();

        if (jRadioButton_Unico.isSelected()) {
            String codes = "";
            String des = "";
            String pre = "";

            if (model.getElementAt(jList_Agregados.getSelectedIndex()) != null) {
                String contenido = model.getElementAt(jList_Agregados.getSelectedIndex()).toString();
                String codigo = contenido.substring(0, 25).trim();
                for (String[] next : arrayDatosExcel) {
                    int c = 0;
                    String caden = next[c];
                    if (caden.trim().equals(codigo.trim())) {
                        codes = next[0];
                        des = next[1];
                        pre = next[2];
                        break;
                    }
                }
            }

            if (descripcioncambiada.equals("") != true) {
                des = descripcioncambiada;
            }

            //System.out.println(codes + "\t" + des + "\t" + pre);
            String[] auxcode = {codes};
            String[] auxpre = {pre};
            String[] auxdes = {des};
            /*if(descripcioncambiada.equals("")){               //esta linia de codigo esta mal
            auxdes[0] = descripcioncambiada;
            }*/
            GeneraPDF g = new GeneraPDF(nombreArchivo, auxcode, auxdes, auxpre, 7, 6, 6, 0);

            if (jRadioButton_Barcode.isSelected()) {
                g.generaEtiquetitasBarcode();
                JOptionPane.showMessageDialog(null, "Etiquetas pequeñas con codigo de barras creadas exitosamente");
            }

            if (jRadioButton_BarcodeDescription.isSelected()) {
                g.generaEtiquetitasBarcodeDescripcion();
                JOptionPane.showMessageDialog(null, "Etiquetas pequeñas con codigo de barras y descripcion creadas exitosamente");
            }

            if (jRadioButton_BarcodeCode.isSelected()) {
                g.generaEtiquetitasBarcodeCode();
                JOptionPane.showMessageDialog(null, "Etiquetas pequeñas con codigo de barras y codigo numerico creadas exitosamente");
            }

            if (jRadioButton_BarcodeCodeDescription.isSelected()) {
                g.generaEtiquetitasBarcodeCodeDescripcion();
                JOptionPane.showMessageDialog(null, "Etiquetas pequeñas codigo de barras, codigo numerico y descripcion creadas exitosamente");
            }

        }

        descripcioncambiada = "";
        txtCambiarDescripcion.setText("");

        if (jRadioButton_Varios.isSelected()) {

            int filas = defaultLM_Agregados.getSize();
            String[] codigos = new String[filas];
            String[] descripciones = new String[filas];
            String[] precios = new String[filas];
            String espacio = "  ";

            for (int i = 0; i < filas; i++) { //int i : jList_Agregados.getSelectedIndices()  filas-1

                jList_Agregados.setSelectedIndex(i);        //creo que aqui es el error
                String contenido = model.getElementAt(i).toString();
                if (model.getElementAt(i) != null && contenido != "null") {    //agregado recientemente
                    String codigo = contenido.substring(0, 25).trim();
                    for (String[] next : arrayDatosExcel) {
                        int c = 0;
                        String caden = next[c];
                        if (caden.trim().equals(codigo.trim())) {
                            String codes = next[0];
                            String des = next[1];
                            String pre = next[2];
                            codigos[i] = codes.trim();
                            descripciones[i] = des.trim();
                            precios[i] = pre.trim();
                            break;

                        }
                    }
                }

            }

            GeneraPDF g = new GeneraPDF(nombreArchivo, codigos, descripciones, precios);

            if (jRadioButton_EtiquetasChicas.isSelected()) {
                //g.setTamanoPrecio(45);
                g.generaEtiquetasChicasDescripcionPrecio();
                JOptionPane.showMessageDialog(null, "Etiquetas chicas creadas exitosamente");
            }

            if (jRadioButton_EtiquetasMedianas.isSelected()) {
                g.setNumColumnasTabla(3);
                g.setTamanoDescripcion(16);
                g.setTamanoPrecio(55);
                g.generaEtiquetasMedianasDescripcionPrecio();
                JOptionPane.showMessageDialog(null, "Etiquetas medianas creadas exitosamente");

            }

            if (jRadioButton_EtiquetasGrandes.isSelected()) {
                g.setNumColumnasTabla(2);
                g.setTamanoDescripcion(25);
                g.setTamanoPrecio(80);
                g.generaEtiquetasGrandesDescripcionPrecio();
                JOptionPane.showMessageDialog(null, "Etiquetas grandes creadas exitosamente");
            }
            
            if (jRadioButton_EtiquetasJumbo.isSelected()) {
                g.setNumColumnasTabla(1);
                g.setTamanoDescripcion(65);
                g.setTamanoPrecio(200);
                g.generaEtiquetasJumboDescripcionPrecio();
                JOptionPane.showMessageDialog(null, "Etiquetas jumbo creadas exitosamente");
            }            

        }


    }//GEN-LAST:event_jButton_GenerarActionPerformed

    private void jRadioButton_VariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_VariosActionPerformed
        if (jRadioButton_Varios.isSelected()) {
            jLabel_SelecioneTamano.setVisible(true);
            jRadioButton_EtiquetasChicas.setVisible(true);
            jRadioButton_EtiquetasMedianas.setVisible(true);
            jRadioButton_EtiquetasGrandes.setVisible(true);
            jRadioButton_EtiquetasJumbo.setVisible(true);
            jLabel_SeleccioneFormato.setVisible(false);
            jRadioButton_Barcode.setVisible(false);
            jRadioButton_BarcodeCode.setVisible(false);
            jRadioButton_BarcodeDescription.setVisible(false);
            jRadioButton_BarcodeCodeDescription.setVisible(false);
            jButton_CambiarDescripcion.setVisible(false);
            txtCambiarDescripcion.setVisible(false);
            jButton_AgregarTodos.setVisible(true);
            //txt_NombreArchivo.setText("EtiquetasVarias");
        }
    }//GEN-LAST:event_jRadioButton_VariosActionPerformed

    private void jRadioButton_EtiquetasGrandesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_EtiquetasGrandesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton_EtiquetasGrandesActionPerformed

    private void jRadioButton_UnicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_UnicoActionPerformed
        if (jRadioButton_Unico.isSelected()) {
            jLabel_SelecioneTamano.setVisible(false);
            jRadioButton_EtiquetasChicas.setVisible(false);
            jRadioButton_EtiquetasMedianas.setVisible(false);
            jRadioButton_EtiquetasGrandes.setVisible(false);
            jRadioButton_EtiquetasJumbo.setVisible(false);
            jLabel_SeleccioneFormato.setVisible(true);
            jRadioButton_Barcode.setVisible(true);
            jRadioButton_BarcodeCode.setVisible(true);
            jRadioButton_BarcodeDescription.setVisible(true);
            jRadioButton_BarcodeCodeDescription.setVisible(true);
            jButton_CambiarDescripcion.setVisible(true);
            txtCambiarDescripcion.setVisible(true);
            jRadioButton_BarcodeCodeDescription.setSelected(true);
            jButton_AgregarTodos.setVisible(false);
            //txt_NombreArchivo.setText("EtiquetasUnicas");90ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
        }
    }//GEN-LAST:event_jRadioButton_UnicoActionPerformed

    private void txt_DescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_DescripcionActionPerformed
        //System.out.println("Descripcion seleccionada");
        /*  jRadioButton_Descripcion.setSelected(true);
        jRadioButton_Codigo.setSelected(false);*/
    }//GEN-LAST:event_txt_DescripcionActionPerformed

    private void jButton_CambiarDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_CambiarDescripcionActionPerformed
        ListModel model = jList_Agregados.getModel();

        try {
            if (model.getElementAt(0) == null) {  //.getElementAt(jList_Agregados.getSelectedIndex())
                //JOptionPane.showMessageDialog(null, "Debes seleccionar un elemento para poder cambiar la descripcion");
            } else {
                descripcioncambiada = txtCambiarDescripcion.getText();
                JOptionPane.showMessageDialog(null, "Descripcion cambiada a : '' " + descripcioncambiada + " ''");
            }
        } catch (IndexOutOfBoundsException e) {
            //JOptionPane.showMessageDialog(null, "Debes seleccionar un elemento para poder cambiar la descripcion");
            JOptionPane.showMessageDialog(this, "Debes seleccionar un elemento para poder cambiar la descripcion", null, JOptionPane.WARNING_MESSAGE);
            //System.out.println("Error " + e);
        } finally {
            txtCambiarDescripcion.setText("");
        }
    }//GEN-LAST:event_jButton_CambiarDescripcionActionPerformed

    private void jButton_ImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ImportarActionPerformed
        JFileChooser jf = new JFileChooser();
        jf.showOpenDialog(this);
        File archivo = jf.getSelectedFile();

        if (archivo != null) {
            if (jLabel_RutaArchivo.getText().equals("")) {    //Si el texto del jLabel esta vacio entonces se crea objeto LeeDatosExcel
                javaPoiUtils = new LeeDatosExcel(archivo.getAbsolutePath());
            }
            jLabel_RutaArchivo.setText(archivo.getAbsolutePath());
            javaPoiUtils.setExcelFile(archivo);
            arrayDatosExcel = javaPoiUtils.readExcelFileToArray();//codigo agregado para crear array de datos del archivo importado            
            
            BufferedWriter bw;
            try {
                //String rutaArchivoImportado = "C:/Program Files (x86)/GeneraEtiquetas/rutas/import.txt";
                String rutaArchivoImportado = direccionImportado;//"C:\\Users\\Windows 7\\Desktop\\GeneraEtiquetas/import.txt";
                File arch = new File(rutaArchivoImportado);//"C:/Users/donyo/Documents/NetBeansProjects/GenerarEtiquetas/rutas/import.txt");
                bw = new BufferedWriter(new FileWriter(arch));
                String imp = archivo.getAbsolutePath();
                bw.write(imp);
                bw.close();
                System.out.println(imp);
            } catch (IOException ex) {
                Logger.getLogger(GenerarEtiquetasV4.class.getName()).log(Level.SEVERE, null, ex);
            }
            

        }


    }//GEN-LAST:event_jButton_ImportarActionPerformed

    private void jButton_EliminarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_EliminarTodosActionPerformed
        // if (jList_Agregados.getSelectedValue() != null) {

        int eleccion = JOptionPane.showConfirmDialog(this, "Esta a punto de borrar todos los elementos de la lista inferior\n"
                + "                             "
                + "¿Desea continuar?", "Advertencia", JOptionPane.OK_CANCEL_OPTION);

        if (eleccion == 0) {
            defaultLM_Agregados.removeAllElements();  //.remove(jList_Agregados.getSelectedIndex());
            jList_Agregados.setModel(defaultLM_Agregados);
            contadorAgregados = 0;
            jLabel_Agregados.setText("" + contadorAgregados);
        }

    }//GEN-LAST:event_jButton_EliminarTodosActionPerformed

    private void jButton_InformacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_InformacionActionPerformed
        JOptionPane.showMessageDialog(this, "                          "
                + "Version 1.4.1 \n"
                + "Creado por: Jorge Adrian Lucas Sanchez \n"
                + "Contacto : generaetiquetas@gmail.com ", "Acerca de", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_jButton_InformacionActionPerformed

    private void jList_ResultadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_ResultadosMouseClicked
        if (evt.getClickCount() == 2) {
            Agrega();
        }

    }//GEN-LAST:event_jList_ResultadosMouseClicked

    private void jList_AgregadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_AgregadosMouseClicked
        if (evt.getClickCount() == 3) {
            Elimina();
        }
    }//GEN-LAST:event_jList_AgregadosMouseClicked

    private void txt_CodigoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_CodigoMousePressed
        jRadioButton_Codigo.setSelected(true);
        jRadioButton_Descripcion.setSelected(false);
    }//GEN-LAST:event_txt_CodigoMousePressed

    private void txt_DescripcionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_DescripcionMousePressed
        jRadioButton_Codigo.setSelected(false);
        jRadioButton_Descripcion.setSelected(true);
    }//GEN-LAST:event_txt_DescripcionMousePressed

    private void jButton_ComparaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ComparaActionPerformed

        JOptionPane.showMessageDialog(null, "Seleccione archivo de excel con el cual hacer comparacion");
        JFileChooser jf = new JFileChooser();
        jf.showOpenDialog(this);
        File archivo = jf.getSelectedFile();

        if (archivo != null) {
            defaultLM_Resultados.removeAllElements();
            LeeDatosExcel libroComparacion = new LeeDatosExcel(archivo.getAbsolutePath());;
            ArrayList<String[]> arrayDatosExcelComparacion = libroComparacion.readExcelFileToArray();
            String[] codigosProductosCambiados = new String[arrayDatosExcelComparacion.size()];
            int i = 0;
            //long startTime = System.nanoTime();     //Para testeo            
            BuscaNuevos bn = new BuscaNuevos(arrayDatosExcel, arrayDatosExcelComparacion);
            Thread bnH = new Thread(bn);
            bnH.start();

            /*BuscaCambiados bc = new BuscaCambiados(arrayDatosExcel, arrayDatosExcelComparacion);
            Thread bcH = new Thread(bc);    //Este fragmento de codigo era en caso de implementar un segundo hilo
            bcH.start();*/                  //Sin embargo se demostro que esa forma era mas lenta que la actual
            for (String[] next : arrayDatosExcel) {

                String codigo = next[0];                        //Obtendra el codigo
                String precioActual = next[2].toUpperCase();    //Obtendra el precio actual del producto al que el codigo corresponde

                for (String[] nextC : arrayDatosExcelComparacion) {

                    if (codigo.trim().equals(nextC[0].trim())) {    //se se encuentra el codigo indicado                         
                        String precioAntiguo = nextC[2].toUpperCase();
                        if (precioActual.equals(precioAntiguo) == false) {
                            codigosProductosCambiados[i] = codigo;
                            Formato code = new Formato(next[0]);
                            Formato descripcion = new Formato(next[1]);
                            Formato precio = new Formato(next[2]);
                            defaultLM_Resultados.addElement(code.codigo() + "   \t\t\t   " + descripcion.descripcion() + "\t" + "$" + precio.precio() + "0");
                            i++;
                            //contadorAgregados = contadorAgregados + 1;
                            //jLabel_Agregados.setText("" + contadorAgregados);
                            break;
                        }
                    }
                }
            }
            /*do {
                if (bcH.isAlive() == false) {
                    String[] productosCambiados = bc.getProductosCambiados();
                    for (int j = 0; j < bc.getNumeroProductosCambiados(); j++) {
                        ///if(productosNuevos[i] != null && productosNuevos[i] != ""){
                        defaultLM_Resultados.addElement(productosCambiados[j]);    //No apuntara a un elemento nulo porque siempre obtendra exactamente                                                                                 //el numero de elementos nuevos 
                    }
                }
            } while (bcH.isAlive());  //En caso de implementar segundo hilo que busque precios cambiados
             */
            do {
                if (bnH.isAlive() == false) {
                    String[] productosNuevos = bn.getProductosNuevos();
                    for (int j = 0; j < bn.getNumeroProductosNuevos(); j++) {
                        ///if(productosNuevos[i] != null && productosNuevos[i] != ""){
                        defaultLM_Resultados.addElement(productosNuevos[j]);    //No apuntara a un elemento nulo porque siempre obtendra exactamente                                                                                 //el numero de elementos nuevos 
                    }
                }
            } while (bnH.isAlive());  //Se repetira mientras el hilo siga vivo

            /*long endTime = System.nanoTime();            
            long diferenciaTiempo = endTime - startTime;
            System.out.println("La diferencia de tiempo usando dos hilos es :" + diferenciaTiempo);*/
        }
    }//GEN-LAST:event_jButton_ComparaActionPerformed


    private void jList_AgregadosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_AgregadosKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_DELETE: //Al presionar la tecla suprimir
                Elimina();
                break;
        }
    }//GEN-LAST:event_jList_AgregadosKeyPressed

    private void jButton_AgregarTodosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_AgregarTodosActionPerformed
        int eleccion = JOptionPane.showConfirmDialog(this, "Esta a punto de agregar todos los elementos de la lista superior\n"
                + "                             "
                + "¿Desea continuar?", "Advertencia", JOptionPane.OK_CANCEL_OPTION);

        if (eleccion == 0) {
            //arrayDatosExcel = javaPoiUtils.readExcelFileToArray();      
            ListModel model = jList_Resultados.getModel();
            int filas = defaultLM_Resultados.getSize();

            for (int i = 0; i < filas; i++) {
                jList_Resultados.setSelectedIndex(i);
                String contenido = model.getElementAt(i).toString();
                if (model.getElementAt(i) != null && contenido != "null") {
                    Agrega();
                }
            }

        }
    }//GEN-LAST:event_jButton_AgregarTodosActionPerformed

    private void jList_ResultadosKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jList_ResultadosKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:   //Al presionar la tecla agregar
                Agrega();
                break;
        }
    }//GEN-LAST:event_jList_ResultadosKeyPressed

    private void jRadioButton_EtiquetasJumboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton_EtiquetasJumboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton_EtiquetasJumboActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
             /*   switch (evt.getKeyCode()) {
            case KeyEvent.VK_F11:   //Al presionar la tecla agregar
               //Funcion abre pdf de ayuda
               System.out.println("Abre manual we");
                break;
        }*/
    }//GEN-LAST:event_formKeyPressed

    private void jButton_ConfiguracionExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_ConfiguracionExcelActionPerformed
        ce.setVisible(true);
            //this.setEnabled(false);

        //do{
        ce.getcCodigo();
        System.out.println(ce.getcCodigo());
        ce.getcDescripcion();
        System.out.println(ce.getcDescripcion());
        ce.getcPrecio();
        System.out.println(ce.getcPrecio());
        //}while(ce.isVisible());
        
       // this.setEnabled(true);
        System.out.println(ce.getcCodigo());
        /*if(configuracionExcelAbierta == false){
        //this.setEnabled(false);
        ConfiguracionExcel ce = new ConfiguracionExcel();
        ce.setVisible(true);
        configuracionExcelAbierta = true;
        }
        //this.setEnabled(true);
        //ce.*/
    }//GEN-LAST:event_jButton_ConfiguracionExcelActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GenerarEtiquetasV4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GenerarEtiquetasV4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GenerarEtiquetasV4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GenerarEtiquetasV4.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //para darle al jfile chooser y el programa el aspecto de windows 
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GenerarEtiquetasV4.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(GenerarEtiquetasV4.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(GenerarEtiquetasV4.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(GenerarEtiquetasV4.class.getName()).log(Level.SEVERE, null, ex);
                }
                new GenerarEtiquetasV4().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JButton jButton_Agregar;
    private javax.swing.JButton jButton_AgregarTodos;
    private javax.swing.JButton jButton_Buscar;
    private javax.swing.JButton jButton_CambiarDescripcion;
    private javax.swing.JButton jButton_Compara;
    private javax.swing.JButton jButton_ConfiguracionExcel;
    private javax.swing.JButton jButton_Eliminar;
    private javax.swing.JButton jButton_EliminarTodos;
    private javax.swing.JButton jButton_Generar;
    private javax.swing.JButton jButton_Importar;
    private javax.swing.JButton jButton_Informacion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel_Agregados;
    private javax.swing.JLabel jLabel_RutaArchivo;
    private javax.swing.JLabel jLabel_SeleccioneFormato;
    private javax.swing.JLabel jLabel_SelecioneTamano;
    private javax.swing.JList<String> jList_Agregados;
    private javax.swing.JList<String> jList_Resultados;
    private javax.swing.JRadioButton jRadioButton_Barcode;
    private javax.swing.JRadioButton jRadioButton_BarcodeCode;
    private javax.swing.JRadioButton jRadioButton_BarcodeCodeDescription;
    private javax.swing.JRadioButton jRadioButton_BarcodeDescription;
    private javax.swing.JRadioButton jRadioButton_Codigo;
    private javax.swing.JRadioButton jRadioButton_Descripcion;
    private javax.swing.JRadioButton jRadioButton_EtiquetasChicas;
    private javax.swing.JRadioButton jRadioButton_EtiquetasGrandes;
    private javax.swing.JRadioButton jRadioButton_EtiquetasJumbo;
    private javax.swing.JRadioButton jRadioButton_EtiquetasMedianas;
    private javax.swing.JRadioButton jRadioButton_Unico;
    private javax.swing.JRadioButton jRadioButton_Varios;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtCambiarDescripcion;
    private javax.swing.JTextField txt_Codigo;
    private javax.swing.JTextField txt_Descripcion;
    private javax.swing.JTextField txt_NombreArchivo;
    // End of variables declaration//GEN-END:variables
}
