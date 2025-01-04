package com.mycompany.nuevotpstock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ControlStock extends javax.swing.JFrame {

    private String usuario;   //pasar parametro para que se vea el nombre de quien ingreso al programa
    
    public ControlStock(String Stock, String usuario) {
        initComponents();
        cargarDatosEnTabla(Stock);          //pasamos al constructor los datos del stock para que los lea la jtable
        this.usuario=usuario;               
        nombreStocktxt1.setText(usuario);   //pasamos el usuario
    }

    //CARGAMOS DATOS A LA JTABLE
    
    private void cargarDatosEnTabla(String rutaArchivo) {
        File archivo = new File(rutaArchivo);               //Creamos objeto tipo File y lo instanciamos en "archivo" y le pasamos el parametro de la ruta de mi archivo

        // Verifica si el archivo existe
        if (!archivo.exists()) {
            JOptionPane.showMessageDialog(this, "El archivo no existe: " + rutaArchivo, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Verifica si el archivo se puede leer
        if (!archivo.canRead()) {
            JOptionPane.showMessageDialog(this, "Acceso denegado al archivo: " + rutaArchivo, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        //DefaultTableModel: Es una implementación del modelo de tabla que permite agregar y manejar filas dinámicamente.
        DefaultTableModel modeloTabla = (DefaultTableModel) jTable1.getModel();     //Obtenemos el modelo de la Jtable y todo lo vamos a agregar a "modeloTabla"
        

        //leemos el archivo linea por linea hasta llegar al null
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");  //Datos separados por ","
                modeloTabla.addRow(datos); // Agrega una nueva fila con los datos para cargarlos uno por uno del archivo a la tabla
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nombreStocktxt = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nombreStocktxt1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        volverBT = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        idtxt = new javax.swing.JTextField();
        detalletxt = new javax.swing.JTextField();
        preciotxt = new javax.swing.JTextField();
        cantidadtxt = new javax.swing.JTextField();
        fechaCompratxt = new javax.swing.JTextField();
        addBT = new javax.swing.JButton();
        DeleteBT = new javax.swing.JButton();
        clearBT = new javax.swing.JToggleButton();
        saveBT = new javax.swing.JButton();
        FindBT = new javax.swing.JButton();

        nombreStocktxt.setEditable(false);
        nombreStocktxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreStocktxtActionPerformed(evt);
            }
        });

        jTextField3.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setText("Usuario ingresado:");

        nombreStocktxt1.setEditable(false);
        nombreStocktxt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombreStocktxt1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Detalle", "Precio", "Cantidad", "Fecha_Compra"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(262, 262, 262)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(nombreStocktxt1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 794, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nombreStocktxt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        volverBT.setText("Volver");
        volverBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverBTActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ID");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Detalle");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Precio");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Fecha Compra");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Cantidad");

        addBT.setText("Añadir");
        addBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBTActionPerformed(evt);
            }
        });

        DeleteBT.setText("Borrar");
        DeleteBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteBTActionPerformed(evt);
            }
        });

        clearBT.setText("Limpiar");
        clearBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBTActionPerformed(evt);
            }
        });

        saveBT.setText("Guardar cambios");
        saveBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBTActionPerformed(evt);
            }
        });

        FindBT.setText("Buscar");
        FindBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FindBTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(idtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(clearBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(saveBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(preciotxt, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(detalletxt, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cantidadtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fechaCompratxt, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(volverBT, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                        .addComponent(FindBT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addBT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(DeleteBT, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(clearBT)
                    .addComponent(addBT))
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(detalletxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(FindBT)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(preciotxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cantidadtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(DeleteBT)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(fechaCompratxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveBT)
                    .addComponent(volverBT, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nombreStocktxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreStocktxtActionPerformed

    }//GEN-LAST:event_nombreStocktxtActionPerformed

    private void nombreStocktxt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombreStocktxt1ActionPerformed

    }//GEN-LAST:event_nombreStocktxt1ActionPerformed

    
    
    
    
    private void volverBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverBTActionPerformed
        //VOLVER BTN
        
        Login log = new Login();
        
        log.setAlwaysOnTop(true);
        log.setVisible(true);
        log.setLocationRelativeTo(null);        
        this.dispose();
    }//GEN-LAST:event_volverBTActionPerformed

    private void addBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBTActionPerformed
        // add btn
        // Verifica que TODOS los campos sean completados
        if(idtxt.getText().equals("")||detalletxt.getText().equals("")||cantidadtxt.getText().equals("")||preciotxt.getText().equals("")||fechaCompratxt.getText().equals("")){
            //Mensaje de error 
            JOptionPane optionPane = new JOptionPane("Por favor complete todos los campos");
            optionPane.setMessageType(JOptionPane.INFORMATION_MESSAGE);
            JDialog dialog = optionPane.createDialog("¡ERROR MORTAL!");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
        }//Si completamos los campos agregamos
        else{
            // Verificar si el ID ya existe
        if (existeID(idtxt.getText())) {
            JOptionPane.showMessageDialog(this, "El ID ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si el ID ya existe
            }
        
            //Por ahora solo agregamos
            //Faltaria ordenamiento si es posible
            
            String data[] = {idtxt.getText(),detalletxt.getText(),preciotxt.getText(),cantidadtxt.getText(),fechaCompratxt.getText()};

            DefaultTableModel modeloTabla = (DefaultTableModel) jTable1.getModel(); //Obtenemos modelo de la jtable1
            modeloTabla.addRow(data);                                               //añadimos todo lo obtenido y agrupado en "data"

        }
               
    }                                     

    // Método para verificar si el ID ya existe en la tabla
    private boolean existeID(String id) {
        DefaultTableModel modeloTabla = (DefaultTableModel) jTable1.getModel();     //Obtenemos el modelo de la tabla
        for (int i = 0; i < modeloTabla.getRowCount(); i++) {                       //recorremos la fila
            if (modeloTabla.getValueAt(i, 0).toString().equals(id)) {               //Comparamos la fila "i" columna 0 con el "id" 
                return true; // El ID ya existe
            }
        }
        return false;
    
    }//GEN-LAST:event_addBTActionPerformed

    private void DeleteBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteBTActionPerformed
        // delete btn
        
        int filaElegida = jTable1.getSelectedRow();
        
        //Verificamos si seleccionamos una fila
        
        if (filaElegida != -1) {
            int confirmation = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro que deseas eliminar esta fila?", 
                "Confirmación", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
            
            if (confirmation == JOptionPane.YES_OPTION) {
            // Obtener el modelo de la tabla
            DefaultTableModel modeloTabla = (DefaultTableModel) jTable1.getModel();
            // Eliminar la fila seleccionada del modelo
            modeloTabla.removeRow(filaElegida);
            }
        }
    }//GEN-LAST:event_DeleteBTActionPerformed
    
    private void clearBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBTActionPerformed
        // BTN limpiar

        idtxt.setText("");
        detalletxt.setText("");
        cantidadtxt.setText("");
        preciotxt.setText("");
        fechaCompratxt.setText("");
    }//GEN-LAST:event_clearBTActionPerformed

    private void saveBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBTActionPerformed
        
        
        File archivo = new File("D:/Stock.txt");                                    //ponemos en "archivo" la ruta de mi archivo.txt

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {     //leemos
            DefaultTableModel modeloTabla = (DefaultTableModel) jTable1.getModel(); //modelo tabla
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {                   //Recorremos filas
                StringBuilder sb = new StringBuilder();                             //Usamos StringBuilder para luego concatenar los datos de la tabla
                for (int j = 0; j < modeloTabla.getColumnCount(); j++) {            //Recorremos columnas
                    sb.append(modeloTabla.getValueAt(i, j).toString());         
                    if (j < modeloTabla.getColumnCount() - 1) {                     //Si la columna no es la ultima, agregamos una coma ,
                        sb.append(",");
                    }
                }
                bw.write(sb.toString());                                            //Convertimos el contedigo de columna en un String  y guardamos
                bw.newLine();                                                       //Guardamos/colocamos un salto de linea
            }
            
            // Mostrar mensaje de éxito después de guardar los datos
            JOptionPane.showMessageDialog(this, "Datos guardados exitosamente", "exito", JOptionPane.INFORMATION_MESSAGE);
    
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al guardar los datos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveBTActionPerformed

    private void FindBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FindBTActionPerformed
        // Buscar BTN

        // Obtener los valores de los JTextFields
        String idBuscar = idtxt.getText().trim();
        String detalleBuscar = detalletxt.getText().trim();
        String cantidadBuscar = cantidadtxt.getText().trim();
        String precioBuscar = preciotxt.getText().trim();
        String fechaBuscar = fechaCompratxt.getText().trim();

        // Validar que al menos un campo esté lleno
        if (idBuscar.isEmpty() && detalleBuscar.isEmpty() && precioBuscar.isEmpty() &&
                cantidadBuscar.isEmpty()  && precioBuscar.isEmpty() && fechaBuscar.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese al menos un dato para buscar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Formateador para validar la fecha (opcional)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");              //Convertimos una fecha a tipo String
        sdf.setLenient(false);                                                  // Para evitar aceptar fechas no válidas

        Date fechaIngresada = null;
        if (!fechaBuscar.isEmpty()) {
            try {
                fechaIngresada = sdf.parse(fechaBuscar);                        //analizamos si "fechaBuscar" tiene el formato de yyyy-MM-dd
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato de fecha no válido. Use el formato yyyy-MM-dd.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir si la fecha no es válida
            }
        }

        // Buscar coincidencias en la tabla
        if (!buscarPorCriterios(idBuscar, detalleBuscar, precioBuscar, cantidadBuscar, fechaIngresada, sdf)) {
            JOptionPane.showMessageDialog(this, "Producto NO encontrado.",
                    "Resultado", JOptionPane.INFORMATION_MESSAGE);
        }
    }

// Método para buscar por criterios en la tabla
    private boolean buscarPorCriterios(String idBuscar, String detalleBuscar, String cantidadBuscar,
            String precioBuscar, Date fechaIngresada, SimpleDateFormat sdf) {
        DefaultTableModel modeloTabla = (DefaultTableModel) jTable1.getModel();
        boolean encontrado = false;
        
        
        ListSelectionModel seleccion = jTable1.getSelectionModel();             //Para gestionar filas y columnas de las tablas
        seleccion.clearSelection(); // Limpiar selecciones anteriores

        for (int i = 0; i < modeloTabla.getRowCount(); i++) {
            boolean coincide = true;

            // Comparar ID
            if (!idBuscar.isEmpty() && !modeloTabla.getValueAt(i, 0).toString().trim().equalsIgnoreCase(idBuscar.trim())) {
                coincide = false;
            }

            // Comparar Detalle
            if (!detalleBuscar.isEmpty()) {
                String detalleTabla = modeloTabla.getValueAt(i, 1).toString().trim().toLowerCase();
                String detalleIngresado = detalleBuscar.trim().toLowerCase();
                if (!detalleTabla.contains(detalleIngresado)) {
                    coincide = false;
                }
            }

            // Comparar Cantidad
            if (!cantidadBuscar.isEmpty()) {
                try {
                    int cantidadTabla = Integer.parseInt(modeloTabla.getValueAt(i, 2).toString().trim());
                    int cantidadIngresada = Integer.parseInt(cantidadBuscar.trim());
                    if (cantidadTabla != cantidadIngresada) {
                        coincide = false;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "La cantidad ingresada no es válida.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false; // Detenemos la búsqueda si hay un error de formato
                }
            }

            // Comparar Precio
            if (!precioBuscar.isEmpty()) {
                try {
                    double precioTabla = Double.parseDouble(modeloTabla.getValueAt(i, 3).toString().trim());
                    double precioIngresado = Double.parseDouble(precioBuscar.trim());
                    if (precioTabla != precioIngresado) {
                        coincide = false;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El precio ingresado no es válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return false; // Detenemos la búsqueda si hay un error de formato
                }
            }

            // Comparar Fecha
            if (fechaIngresada != null) {
                try {
                    String fechaTablaStr = modeloTabla.getValueAt(i, 4).toString().trim(); // Columna de fecha
                    Date fechaTabla = sdf.parse(fechaTablaStr);
                    if (!fechaTabla.equals(fechaIngresada)) {
                        coincide = false;
                    }
                } catch (ParseException ex) {
                    System.err.println("Formato de fecha inválido en la fila " + (i + 1));
                }
            }

            // Si coincide, seleccionamos la fila
            if (coincide) {
                seleccion.addSelectionInterval(i, i); // Selecciona la fila en la tabla
                jTable1.scrollRectToVisible(jTable1.getCellRect(i, 0, true)); // Asegura que las filas sean visibles
                encontrado = true;
            }
        }

        return encontrado;
    }//GEN-LAST:event_FindBTActionPerformed
        
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DeleteBT;
    private javax.swing.JButton FindBT;
    private javax.swing.JButton addBT;
    private javax.swing.JTextField cantidadtxt;
    private javax.swing.JToggleButton clearBT;
    private javax.swing.JTextField detalletxt;
    private javax.swing.JTextField fechaCompratxt;
    private javax.swing.JTextField idtxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField nombreStocktxt;
    private javax.swing.JTextField nombreStocktxt1;
    private javax.swing.JTextField preciotxt;
    private javax.swing.JButton saveBT;
    private javax.swing.JButton volverBT;
    // End of variables declaration//GEN-END:variables
}
