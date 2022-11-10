
package WorkBench;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author ferna
 */
public class newIndex extends javax.swing.JFrame {

    /**
     * Creates new form newTrigger
     */
    
    Connection connectBD = null;
    Functions function = new Functions();
    
    public newIndex() {
        initComponents();
        this.setLocationRelativeTo(null);
        llenarTablas();
        recargar();
       Sort.addItem("");
       Sort.addItem("ASC");
       Sort.addItem("DESC");
       collation.addItem("");
       collation.addItem("RTRIM");
       collation.addItem("NOCASE");
       collation.addItem("BINARY");

    }
    
     public newIndex(Connection connect) {
        initComponents();
        connectBD = connect;
        this.setLocationRelativeTo(null);
        llenarTablas();
        recargar();
       Sort.addItem("");
       Sort.addItem("ASC");
       Sort.addItem("DESC");
       collation.addItem("");
       collation.addItem("RTRIM");
       collation.addItem("NOCASE");
       collation.addItem("BINARY");

    }
    
    DefaultTableModel model;
    String info[] = new String[6];
    JComboBox Sort = new JComboBox();
    JComboBox collation = new JComboBox();
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbTable = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        btOk = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jDLL = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Index name:");

        jLabel2.setText("On Table:");

        jButton3.setText("update DLL");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btOk.setText("OK");
        btOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOkActionPerformed(evt);
            }
        });

        btCancel.setText("Cancel");
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1"
            }
        ));
        jScrollPane4.setViewportView(jTable1);

        jButton5.setText("cargar tabla");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("Unique index");
        jCheckBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jCheckBox1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btOk, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbTable, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2))
                                .addGap(44, 44, 44)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(0, 26, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton5))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jButton4)))
                .addGap(18, 18, 18)
                .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btOk, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Trigger", jPanel1);

        jDLL.setColumns(20);
        jDLL.setRows(5);
        jScrollPane1.setViewportView(jDLL);

        jTabbedPane1.addTab("DLL", jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public void recargar(){
       model = new DefaultTableModel();
       model.addColumn("Column");
       model.addColumn("Check");
       model.addColumn("Sort");
       model.addColumn("Collation");
       jTable1.setModel(model);
       
       addCheckBox(1);

       jTable1.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(Sort));
       jTable1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(collation));
    }

    public void addCheckBox(int column){
        TableColumn tc = jTable1.getColumnModel().getColumn(column);
        tc.setCellEditor(jTable1.getDefaultEditor(Boolean.class));
        tc.setCellRenderer(jTable1.getDefaultRenderer(Boolean.class));
    }
    public void verificar(){
     
    }
    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btCancelActionPerformed
  
  public void llenarTablas(){
        ResultSet result = null;
        try{
            
            PreparedStatement st = connectBD.prepareStatement("SELECT * FROM sqlite_master WHERE type = \"table\"");
            result = st.executeQuery();
            while(result.next()){
                cbTable.addItem(result.getString(2));
            }  
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
  }
   
  public void llenarData(String data){
        ResultSet result = null;
        try{
            
            PreparedStatement st = connectBD.prepareStatement("select * from pragma_table_info(\""+data+"\");");
            result = st.executeQuery();
            while(result.next()){
                info[0]=result.getString(2);
                this.model.addRow(info);
            }  
        }catch(Exception e){
             JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
  }
  String sql="";
  String dll="";
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String nombre = txtName.getText();
        String table = cbTable.getSelectedItem().toString();
        int predecir=1;
        int tmp=cantR();
        System.out.println(tmp);
        sql="CREATE "+unique+"INDEX "+nombre+" ON "+ table+"(";
        dll=sql+"\n";
        for(int i=0; i<jTable1.getRowCount();i++){
            
             String s = String.valueOf(jTable1.getValueAt(i, 1));
             if(s.equals("true")){
                 
                 String Column = String.valueOf(jTable1.getValueAt(i, 0));
                 String Sort = String.valueOf(jTable1.getValueAt(i, 2));
                 String Collation = String.valueOf(jTable1.getValueAt(i, 3));
                 
                if(Collation.equals("null")||Collation.equals("")){
                    sql=sql+Column+" ";
                    dll=dll+Column+" ";
       
                 }else{
                    sql=sql+Column+" COLLATE "+Collation+" ";
                    dll=dll+Column+" COLLATE "+Collation+" ";
                 }
                
                 if(Sort.equals("null")||Sort.equals("")){
                     sql=sql;
                     dll=dll;
                 }else{
                     
                     sql=sql+ Sort +" ";
                     dll=dll+ Sort +" ";
                 }
                  if(predecir<tmp){
                      sql=sql+",";
                      dll=dll+",\n";
                      predecir++;
                  }
             }
            
        }
        sql=sql+");";
        dll=dll+");";
        jDLL.setText(dll);
        
    }//GEN-LAST:event_jButton3ActionPerformed
public int cantR(){
    int tmp=0;
            for(int i=0; i<jTable1.getRowCount();i++){
             String s = String.valueOf(jTable1.getValueAt(i, 1));
             if(s.equals("true")){
                 tmp=tmp+1;
             }   
        }
    return tmp;
}
    private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed
        // TODO add your handling code here:
            ResultSet result = null;
            try{
              
                PreparedStatement st = connectBD.prepareStatement(sql);
                
                st.executeUpdate();
              
              
            }catch(Exception e){
                 JOptionPane.showMessageDialog(null, e.getMessage().toString());
           }
            
            function.NameIndex.add(txtName.getText());
        
    }//GEN-LAST:event_btOkActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
    }//GEN-LAST:event_jButton4ActionPerformed
public void limpiar(){
model=(DefaultTableModel) jTable1.getModel();
model.setRowCount(0);
}
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        String table = cbTable.getSelectedItem().toString();
        limpiar();
        recargar();
        llenarData(table);
    }//GEN-LAST:event_jButton5ActionPerformed
boolean check=false;
String unique="";
    private void jCheckBox1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jCheckBox1MousePressed
        // TODO add your handling code here:
        if(check==false){
            unique = "UNIQUE";
            check=true;
        }else{
            unique="";
            check=false;
        }
    }//GEN-LAST:event_jCheckBox1MousePressed


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
            java.util.logging.Logger.getLogger(newIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(newIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(newIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(newIndex.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new newIndex().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btOk;
    private javax.swing.JComboBox<String> cbTable;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JTextArea jDLL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
