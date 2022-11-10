
package WorkBench;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author ferna
 */
public class ForeignKey extends javax.swing.JFrame {

    /**
     * Creates new form ForeignKey
     */
     
    Connection connectBD = null;
    public ForeignKey() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        llenarTablas();
    }
    
    public ForeignKey(Connection connect) {
        initComponents();
        connectBD = connect;
        this.setLocationRelativeTo(null);
        
        llenarTablas();
    }
  
  
    
//    public void conectar(){
//                try{ 
//            //conectar con la bd
//            connect = DriverManager.getConnection(urlDb);
//            if(connect!=null){
//            JOptionPane.showMessageDialog(null, "CONECTADO!!");
//            }
//        }catch(Exception e){  
//            JOptionPane.showMessageDialog(null, e.getMessage().toString());  
//        }
//    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jcbTable = new javax.swing.JComboBox<>();
        jcbColumn = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Foreign Table:");

        jLabel2.setText("Foreign Column:");

        jButton1.setText("Apply");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jcbTable.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jcbTableMouseDragged(evt);
            }
        });
        jcbTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcbTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jcbTableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jcbTableMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jcbTableMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jcbTableMouseReleased(evt);
            }
        });
        jcbTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbTableActionPerformed(evt);
            }
        });
        jcbTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jcbTableKeyPressed(evt);
            }
        });

        jcbColumn.setEnabled(false);

        jButton3.setText("..");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jcbColumn, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(31, 31, 31)
                                .addComponent(jcbTable, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jButton1)
                        .addGap(30, 30, 30)
                        .addComponent(jButton2)))
                .addGap(32, 32, 32))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jcbTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jcbColumn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String tabla = jcbTable.getSelectedItem().toString();
        String columna = jcbColumn.getSelectedItem().toString();
        addColumn s = new addColumn();
        addColumn.table=tabla;
        addColumn.column=columna;
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jcbTableMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbTableMousePressed
        // TODO add your handling code here:
 
        
    }//GEN-LAST:event_jcbTableMousePressed

    private void jcbTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbTableActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jcbTableActionPerformed

    private void jcbTableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jcbTableKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jcbTableKeyPressed

    private void jcbTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbTableMouseEntered
        // TODO add your handling code here:

    }//GEN-LAST:event_jcbTableMouseEntered

    private void jcbTableMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbTableMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_jcbTableMouseReleased

    private void jcbTableMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbTableMouseDragged

    }//GEN-LAST:event_jcbTableMouseDragged

    private void jcbTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbTableMouseClicked

    }//GEN-LAST:event_jcbTableMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_jButton2ActionPerformed
boolean cargar =false;
    private void jcbTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcbTableMouseExited
        // TODO add your handling code here:
       
    }//GEN-LAST:event_jcbTableMouseExited

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
            jcbColumn.removeAllItems();
         
            jcbColumn.setEnabled(true);
            
            String tabla = jcbTable.getSelectedItem().toString();
            
            ResultSet result = null;
            try{
                //la consulta a realizar
                PreparedStatement st = connectBD.prepareStatement("SELECT * FROM pragma_table_info('"+tabla+"')");
                result = st.executeQuery();
                while(result.next()){
                    jcbColumn.addItem(result.getString(2));
                }  
                cargar = true;
            }catch(Exception e){
                 JOptionPane.showMessageDialog(null, e.getMessage().toString());
           }
    }//GEN-LAST:event_jButton3ActionPerformed
    public void llenarTablas(){
        ResultSet result = null;
        try{
            
            //la consulta a realizar
            PreparedStatement st = connectBD.prepareStatement("SELECT * FROM sqlite_master WHERE type = \"table\"");
            result = st.executeQuery();
            while(result.next()){
                jcbTable.addItem(result.getString(2));
            }  
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
    }
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
            java.util.logging.Logger.getLogger(ForeignKey.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ForeignKey.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ForeignKey.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ForeignKey.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ForeignKey().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JComboBox<String> jcbColumn;
    private javax.swing.JComboBox<String> jcbTable;
    // End of variables declaration//GEN-END:variables
}