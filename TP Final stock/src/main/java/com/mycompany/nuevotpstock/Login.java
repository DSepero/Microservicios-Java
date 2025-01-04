package com.mycompany.nuevotpstock;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

//LOGIN PRINCIPAL



public class Login extends javax.swing.JFrame {

   
    public Login() {
        initComponents();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        loginNombretxt = new javax.swing.JTextField();
        loginBT = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        passtxt = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        loginNombretxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                loginNombretxtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                loginNombretxtFocusLost(evt);
            }
        });
        loginNombretxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginNombretxtActionPerformed(evt);
            }
        });

        loginBT.setText("Login");
        loginBT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginBTActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Programa de Stock");

        passtxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passtxtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(loginBT, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                    .addComponent(loginNombretxt, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                    .addComponent(passtxt)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(loginNombretxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(passtxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(loginBT, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(168, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginBTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginBTActionPerformed
       
        // LOGIN BTN
        // Obtenemos los valores de loginNombretxt y passtxt
        String usuario = loginNombretxt.getText();
        String pass = passtxt.getText();
        String rutaArchivo = "D:/usuarios.txt"; // CORREGIR LA RUTA DEL ARCHIVO ANTES DE USAR
        boolean usuarioValido = false;

        try (BufferedReader leerArchivo = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            // Leer el archivo línea por línea
            while ((linea = leerArchivo.readLine()) != null) {
                String[] data = linea.split(", ");

                // Validar que haya suficientes elementos en data
                if (data.length >= 3) {                 //Nos aseguramos que haya hasta 3 datos guardados
                    String usuarioGuardado = data[1];   
                    String passGuardado = data[2];

                    // Validar credenciales
                    if (usuario.equals(usuarioGuardado) && pass.equals(passGuardado)) {
                        usuarioValido = true; // Se encontró una coincidencia
                        break; // Salir del bucle
                    }
                }
            }

            // Verificar si el usuario es válido
            if (usuarioValido) {
                this.dispose(); // Cerramos ventana de login

                String stockRuta = "D:/Stock.txt"; // Ruta al archivo de stock

                // Ingresamos a la ventana de Stock
                ControlStock stock = new ControlStock(stockRuta, usuario);
                stock.setAlwaysOnTop(true);
                stock.setVisible(true);
                stock.setLocationRelativeTo(null);
            } else {
                // Mostrar mensaje de error si no se encuentra el usuario
                JOptionPane.showMessageDialog(this, "Contraseña / Usuario incorrectos", "Error Fatal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            // Manejar excepciones relacionadas con la lectura del archivo
            JOptionPane.showMessageDialog(this, "Error al leer el archivo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_loginBTActionPerformed

    private void loginNombretxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginNombretxtActionPerformed
        //txt anterior sin uso
       
    }//GEN-LAST:event_loginNombretxtActionPerformed

    private void passtxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passtxtActionPerformed
        // JTextField Password
        
        if (passtxt.getText().equals("*****")) {
            passtxt.setText("");
        }
    }

    private void passtxtFocusLost(java.awt.event.FocusEvent evt) {
        if (passtxt.getText().equals("")) {
            passtxt.setText("*****");
        }
        
    }//GEN-LAST:event_passtxtActionPerformed

    private void loginNombretxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_loginNombretxtFocusGained
        if (loginNombretxt.getText().equals("usuario")) {

            loginNombretxt.setText("");
            loginNombretxt.setForeground(new Color(0, 0, 0));
        }
    }//GEN-LAST:event_loginNombretxtFocusGained

    private void loginNombretxtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_loginNombretxtFocusLost
        if(loginNombretxt.getText().equals("")){
            
            loginNombretxt.setText("usuario");
            loginNombretxt.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_loginNombretxtFocusLost

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loginBT;
    private javax.swing.JTextField loginNombretxt;
    private javax.swing.JPasswordField passtxt;
    // End of variables declaration//GEN-END:variables
}
