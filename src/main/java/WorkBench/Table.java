
package WorkBench;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Function;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;


public class Table extends javax.swing.JFrame {

    Connection connectBD  = null;
    public Table() {
        initComponents();
        model  = (DefaultTableModel) this.jTable1.getModel();
        this.setLocationRelativeTo(null);
        setTable();
       
    }
    
    public Table(Connection connect) {
        
        initComponents();
        connectBD = connect;
        model  = (DefaultTableModel) this.jTable1.getModel();
        this.setLocationRelativeTo(null);
        setTable();
       
    }


    DefaultTableModel model;

    String info[] = new String[6];
    ArrayList<String> sql = new ArrayList<String>();
    ArrayList<String> nColumn = new ArrayList<String>();
    
    public static String name="";
    public static String type;
    public static int p1=0;
    public static int reference=0;
    public static int uni=0;
    public static int not=0;
    public static String consul;
    
    Functions function = new Functions();
    
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btNew = new javax.swing.JButton();
        btAdd = new javax.swing.JButton();
        btView = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        btDelete = new javax.swing.JButton();
        btView1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btNew.setText("Nuevo");
        btNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNewActionPerformed(evt);
            }
        });

        btAdd.setText("Crear Tabla");
        btAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddActionPerformed(evt);
            }
        });

        btView.setText("actualizar dll");
        btView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewActionPerformed(evt);
            }
        });

        jLabel1.setText("Tabla:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTabbedPane1.addTab("Columns", jScrollPane1);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jTabbedPane1.addTab("DLL", jScrollPane2);

        btDelete.setText("Eliminar");
        btDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btDeleteActionPerformed(evt);
            }
        });

        btView1.setText("Cancelar");
        btView1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btView1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 860, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btNew, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btView, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(40, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btView1, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel1))
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btNew)
                    .addComponent(btDelete)
                    .addComponent(btView))
                .addGap(20, 20, 20)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAdd)
                    .addComponent(btView1))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
Connect s = new Connect();    public void setTable(){
       model = new DefaultTableModel();
       model.addColumn("Nombre");
       model.addColumn("Data type");
       model.addColumn("Primary Key");
       model.addColumn("Foreign Key");
       model.addColumn("Unique");
       model.addColumn("Not Null");
       jTable1.setModel(model);        
    }
    private void btNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNewActionPerformed
        // TODO add your handling code here:
         addColumn p = new addColumn(connectBD);
         p.setVisible(true);    
    }//GEN-LAST:event_btNewActionPerformed
public  void llenar(){  
      if(!consul.equals("")){
            sql.add(consul);
            nColumn.add(name);
            info[0] = name;
            info[1] = type;
            info[2] = String.valueOf(p1);
            info[3] = String.valueOf(reference);
            info[4] = String.valueOf(uni);
            info[5] = String.valueOf(not);
            //SE LE AGREGA LA FILA A LA TABLA
            this.model.addRow(info); 
      }
}
    private void btAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddActionPerformed
            ResultSet result = null;
            try{
                //la consulta a realizar
                if(!data.equals("")){
                PreparedStatement st = connectBD.prepareStatement(data);
                System.out.println(data);
                st.executeUpdate();
          
                model.setRowCount(0);
                function.NameTABLE.add(txtName.getText());
                txtName.setText("");
                sql.clear();
                nColumn.clear();
              } 
            }catch(Exception e){
                 JOptionPane.showMessageDialog(null, e.getMessage().toString());
           }
            
           
            
    }//GEN-LAST:event_btAddActionPerformed
public static String data;
public void sentencia(){
        if(!name.equals("")){
            llenar();
            llenarDDL();
            name="";
            type="";
            p1=0;
            reference=0;
            uni=0;
            not=0;
        }
}

    private void btViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewActionPerformed
        // TODO add your handling code here:
        sentencia();
    }//GEN-LAST:event_btViewActionPerformed

    private void btDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btDeleteActionPerformed
        // TODO add your handling code here:
        String columna = JOptionPane.showInputDialog("Introduzca el nombre de la columna a eliminar:");
        if(!columna.equals("")){
            for(int x=0; x<nColumn.size();x++){
                System.out.println(nColumn.get(x));
                if(nColumn.get(x).equals(columna)){
                    nColumn.remove(x);
                    sql.remove(x);
                    model.removeRow(x);
                    llenarDDL();
                }
            } 
        }
        /*if(jTable1.getSelectedColumn()!=-1){
           System.out.println(jTable1.getSelectedRow());
           model.removeRow(jTable1.getSelectedRow());
        }else{
            JOptionPane.showMessageDialog(null, "No has seleccionado un registro");
        }*/
    }//GEN-LAST:event_btDeleteActionPerformed

    private void btView1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btView1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btView1ActionPerformed
public  void llenarDDL(){
        String nombre = txtName.getText();
        data="CREATE TABLE "+nombre+"(";
        String dll ="CREATE TABLE "+nombre+"(\n";
        int predecir=1;
        for(int x=0; x<sql.size();x++){
            data=data+sql.get(x);  
            dll=dll+sql.get(x); 
            if(predecir!=sql.size()){
                data=data+",";
                dll=dll+",\n";
                predecir++;
            }
        }
        data = data + ");";
        dll = dll + "\n);";
        jTextArea1.setText(dll);
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdd;
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btNew;
    private javax.swing.JButton btView;
    private javax.swing.JButton btView1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
