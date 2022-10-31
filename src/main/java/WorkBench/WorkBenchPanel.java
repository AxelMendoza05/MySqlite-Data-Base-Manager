/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package WorkBench;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFileChooser;

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;

/**
 *
 * @author AxelM
 */
public class WorkBenchPanel extends javax.swing.JFrame {

    /**
     * Creates new form WorkBenchPanel
     */
    public WorkBenchPanel() throws SQLException {

        initComponents();
        jPanel2.setLocation(getHeight(), getWidth());
        //tabla resultado consultas
        model = (DefaultTableModel) this.table.getModel();
        model.setRowCount(0);
        //tabla status
        modelStatus = (DefaultTableModel) this.status.getModel();
        modelStatus.setRowCount(0);
        modelStatus.addColumn("STATUS");
        modelStatus.setColumnCount(1);

        //this.getContentPane().setBackground(Color.decode(toStrin));
        //botones
        run.setEnabled(false);
        clear.setEnabled(false);
        disconnect.setEnabled(false);

        //cargar jtree
        LoadTree();

        query.setLineWrap(true);
        query.setWrapStyleWord(true);
    }

//visualizador de base de datos 
    DefaultTreeModel tree;
    DefaultMutableTreeNode inicio = null;
    File miCarpeta = null;
    //ESCOGER BASE DE DATOS A ELEGIR
    String pathTree = "jdbc:sqlite:C:/Users/AxelM/Downloads/archive/";
    String BDSELECTED;
    String BDLOCATION;
    Connection connectBD;

    //TABLA VISUALIZAR CODIGO CONSULTAS
    DefaultTableModel model;

    //Functions functions = new Functions();
    //TABLA STATUS RESULTADO
    DefaultTableModel modelStatus;
    DefaultTableModel modelInfo;
    DefaultTableModel modelData;

    DefaultMutableTreeNode nodoSeleccionado = null;

    //VARIABLES DE APOYO
    ArrayList<String> nameTables = new ArrayList<>();
    String BDCONNCECTED = "";

    public void LoadTree() throws SQLException {

        miCarpeta = new File("C:/Users/AxelM/Downloads/archive/");
        inicio = new DefaultMutableTreeNode("DBS");
        RecLoadTree(miCarpeta, inicio, "Nombre");
    }

    private void RecLoadTree(File direccion, DefaultMutableTreeNode nodo, String filtro) throws SQLException {

        if (direccion.isDirectory() && filtro.equalsIgnoreCase("Nombre")) {

            for (File file : direccion.listFiles()) {

                if (file.getName().contains(".db")) {

                    DefaultMutableTreeNode BD = new DefaultMutableTreeNode(file.getName());
                    String urlDb = "jdbc:sqlite:C:/Users/AxelM/Downloads/archive/" + file.getName();
                    Connection connect = DriverManager.getConnection(urlDb);
                    nodo.add(BD);
                    DefaultMutableTreeNode TABLESFOLDER = new DefaultMutableTreeNode("TABLES");
                    BD.add(TABLESFOLDER);

                    try {

                        ResultSet result = null;
                        //la consulta a realizar
                        PreparedStatement st = connect.prepareStatement("select name from sqlite_master where type = \"table\"");
                        result = st.executeQuery();

                        ArrayList<String> tabla = new ArrayList<String>();

                        while (result.next()) {
                            //ADD TABLES TO FOLDER

                            tabla.add(result.getString(1));
                            nameTables.add(result.getString(1));

                        }

                        //System.out.println(tabla.get(0));
                        for (int i = 0; i < tabla.size(); i++) {
                            //PARTE DE TABLAS
                            DefaultMutableTreeNode TABLE = new DefaultMutableTreeNode(tabla.get(i));
                            TABLESFOLDER.add(TABLE);

                            DefaultMutableTreeNode COLUMNS = new DefaultMutableTreeNode("COLUMNS");
                            TABLE.add(COLUMNS);

                            PreparedStatement st2 = connect.prepareStatement("select name from pragma_table_info ('" + tabla.get(i) + "')");
                            ResultSet result2 = st2.executeQuery();

                            while (result2.next()) {

                                COLUMNS.add(new DefaultMutableTreeNode(result2.getString(1)));
                            }

                            //PARTE DE TRIGGERS
                            DefaultMutableTreeNode TRIGGERS = new DefaultMutableTreeNode("TRIGGERS");
                            TABLE.add(TRIGGERS);

                            PreparedStatement st3 = connect.prepareStatement("select * from sqlite_master where type = 'trigger' and tbl_name = '" + tabla.get(i) + "'");
                            ResultSet result3 = st3.executeQuery();

                            while (result3.next()) {

                                TRIGGERS.add(new DefaultMutableTreeNode(result3.getString(2)));

                            }
                            //------------------------------------------------------
                            //PARTE DE INDICES
                            DefaultMutableTreeNode INDEX = new DefaultMutableTreeNode("INDEXES");
                            TABLE.add(INDEX);

                            PreparedStatement st4 = connect.prepareStatement("select * from sqlite_master where type = 'index' and tbl_name = '" + tabla.get(i) + "'and sql not null");
                            ResultSet result4 = st4.executeQuery();

                            while (result4.next()) {

                                INDEX.add(new DefaultMutableTreeNode(result4.getString(2)));

                            }

                        }

                    } catch (Exception e) {

                        JOptionPane.showMessageDialog(null, e.getMessage().toString());

                    }

                    DefaultMutableTreeNode VISTAS = new DefaultMutableTreeNode("VISTAS");
                    BD.add(VISTAS);

                    PreparedStatement st5 = connect.prepareStatement("select tbl_name from sqlite_master where type = 'view'");
                    ResultSet result5 = st5.executeQuery();

                    while (result5.next()) {

                        VISTAS.add(new DefaultMutableTreeNode(result5.getString("tbl_name")));

                    }
                }

            }
            tree = new DefaultTreeModel(inicio);
            DBS.setModel(tree);
        }
    }

    private void AD_DBS_TO_TREE(File direccion, DefaultMutableTreeNode nodo, String filtro) throws SQLException {

        if (direccion.isFile() && filtro.equalsIgnoreCase("Nombre")) {

            if (direccion.getName().contains(".db")) {

                DefaultMutableTreeNode BD = new DefaultMutableTreeNode(direccion.getName());
                String urlDb = "jdbc:sqlite:" + direccion.getAbsolutePath();
                Connection connect = DriverManager.getConnection(urlDb);
                tree.insertNodeInto(BD, nodo, nodo.getChildCount());
                DefaultMutableTreeNode TABLESFOLDER = new DefaultMutableTreeNode("TABLES");
                BD.add(TABLESFOLDER);

                try {

                    ResultSet result = null;
                    //la consulta a realizar
                    PreparedStatement st = connect.prepareStatement("select name from sqlite_master where type = \"table\"");
                    result = st.executeQuery();

                    ArrayList<String> tabla = new ArrayList<String>();

                    while (result.next()) {
                        //ADD TABLES TO FOLDER

                        tabla.add(result.getString(1));
                        nameTables.add(result.getString(1));

                    }

                    //System.out.println(tabla.get(0));
                    for (int i = 0; i < tabla.size(); i++) {
                        //PARTE DE TABLAS
                        DefaultMutableTreeNode TABLE = new DefaultMutableTreeNode(tabla.get(i));
                        TABLESFOLDER.add(TABLE);

                        DefaultMutableTreeNode COLUMNS = new DefaultMutableTreeNode("COLUMNS");
                        TABLE.add(COLUMNS);

                        PreparedStatement st2 = connect.prepareStatement("select name from pragma_table_info ('" + tabla.get(i) + "')");
                        ResultSet result2 = st2.executeQuery();

                        while (result2.next()) {

                            COLUMNS.add(new DefaultMutableTreeNode(result2.getString(1)));
                        }

                        //PARTE DE TRIGGERS
                        DefaultMutableTreeNode TRIGGERS = new DefaultMutableTreeNode("TRIGGERS");
                        TABLE.add(TRIGGERS);

                        PreparedStatement st3 = connect.prepareStatement("select * from sqlite_master where type = 'trigger' and tbl_name = '" + tabla.get(i) + "'");
                        ResultSet result3 = st3.executeQuery();

                        while (result3.next()) {

                            TRIGGERS.add(new DefaultMutableTreeNode(result3.getString(2)));

                        }
                        //------------------------------------------------------
                        //PARTE DE INDICES
                        DefaultMutableTreeNode INDEX = new DefaultMutableTreeNode("INDEXES");
                        TABLE.add(INDEX);

                        PreparedStatement st4 = connect.prepareStatement("select * from sqlite_master where type = 'index' and tbl_name = '" + tabla.get(i) + "'and sql not null");
                        ResultSet result4 = st4.executeQuery();

                        while (result4.next()) {

                            INDEX.add(new DefaultMutableTreeNode(result4.getString(2)));

                        }

                    }

                } catch (Exception e) {

                    JOptionPane.showMessageDialog(null, e.getMessage().toString());

                }

                DefaultMutableTreeNode VISTAS = new DefaultMutableTreeNode("VISTAS");
                BD.add(VISTAS);

                PreparedStatement st5 = connect.prepareStatement("select tbl_name from sqlite_master where type = 'view'");
                ResultSet result5 = st5.executeQuery();

                while (result5.next()) {

                    VISTAS.add(new DefaultMutableTreeNode(result5.getString("tbl_name")));

                }

                tree.insertNodeInto(BD, nodo, nodo.getChildCount());
            }

            //tree = new DefaultTreeModel(inicio);
            //DBS.setModel(tree);
        }
    }

    private boolean TableExists(String table) {

        for (int i = 0; i < nameTables.size(); i++) {

            if (table.equals(nameTables.get(i))) {
                return true;
            }
        }

        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        TreePopMenu = new javax.swing.JPopupMenu();
        SELECT = new javax.swing.JMenuItem();
        SQLDETAILS = new javax.swing.JMenuItem();
        CREATETABLE = new javax.swing.JMenuItem();
        AddDatabase = new javax.swing.JMenuItem();
        filePopMenu = new javax.swing.JPopupMenu();
        DATABASE = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        connect = new javax.swing.JButton();
        disconnect = new javax.swing.JButton();
        run = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        dataTable = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        infoTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        query = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        DBS = new javax.swing.JTree();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jScrollPane4 = new javax.swing.JScrollPane();
        status = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        CREATETABLETOOLBAR = new javax.swing.JMenuItem();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        SELECT.setText("SELECT");
        SELECT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SELECTActionPerformed(evt);
            }
        });
        TreePopMenu.add(SELECT);

        SQLDETAILS.setText("SEE SQL DETAILS");
        SQLDETAILS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SQLDETAILSActionPerformed(evt);
            }
        });
        TreePopMenu.add(SQLDETAILS);

        CREATETABLE.setText("CREATE TABLE");
        CREATETABLE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CREATETABLEActionPerformed(evt);
            }
        });
        TreePopMenu.add(CREATETABLE);

        AddDatabase.setText("ADD DATABASE\n");
        AddDatabase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddDatabaseActionPerformed(evt);
            }
        });
        TreePopMenu.add(AddDatabase);

        DATABASE.setText("ADD DATABASE");
        filePopMenu.add(DATABASE);

        jMenu1.setText("jMenu1");

        jMenu2.setText("File");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar1.add(jMenu3);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 204, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(1680, 869));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 156, 236));
        jLabel2.setText("SQLITE  DATA BASE MANAGER");

        connect.setIcon(new javax.swing.ImageIcon("C:\\Users\\AxelM\\OneDrive\\Documentos\\NetBeansProjects\\ProyectoTBD2\\src\\main\\java\\WorkBench\\Imagen\\power-plug.png")); // NOI18N
        connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectActionPerformed(evt);
            }
        });

        disconnect.setIcon(new javax.swing.ImageIcon("C:\\Users\\AxelM\\OneDrive\\Documentos\\NetBeansProjects\\ProyectoTBD2\\src\\main\\java\\WorkBench\\Imagen\\dePlug.png")); // NOI18N
        disconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectActionPerformed(evt);
            }
        });

        run.setIcon(new javax.swing.ImageIcon("C:\\Users\\AxelM\\OneDrive\\Documentos\\NetBeansProjects\\ProyectoTBD2\\src\\main\\java\\WorkBench\\Imagen\\rayo2.png")); // NOI18N
        run.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runActionPerformed(evt);
            }
        });

        clear.setIcon(new javax.swing.ImageIcon("C:\\Users\\AxelM\\OneDrive\\Documentos\\NetBeansProjects\\ProyectoTBD2\\src\\main\\java\\WorkBench\\Imagen\\cepillo.png")); // NOI18N
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearActionPerformed(evt);
            }
        });

        dataTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(dataTable);

        jTabbedPane3.addTab("DATA", jScrollPane5);

        infoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(infoTable);

        jTabbedPane3.addTab("INFORMATION", jScrollPane6);

        query.setColumns(20);
        query.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        query.setRows(5);
        query.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                queryInputMethodTextChanged(evt);
            }
        });
        query.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                queryKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(query);

        jTabbedPane3.addTab("QUERY", jScrollPane1);

        DBS.setBorder(null);
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        DBS.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        DBS.setComponentPopupMenu(TreePopMenu);
        DBS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DBSMouseClicked(evt);
            }
        });
        DBS.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                DBSValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(DBS);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(table);

        jTabbedPane1.addTab("RESULT", jScrollPane3);

        status.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(status);

        jTabbedPane2.addTab("STATUS", jScrollPane4);

        jButton3.setIcon(new javax.swing.ImageIcon("C:\\Users\\AxelM\\OneDrive\\Documentos\\NetBeansProjects\\ProyectoTBD2\\src\\main\\java\\WorkBench\\Imagen\\sqlite-logo.png")); // NOI18N
        jButton3.setBorder(null);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 359, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(474, 474, 474)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(connect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(disconnect))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(run, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clear))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1186, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 57, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(clear, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(run, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(connect, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(disconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jMenu4.setText("File");

        jMenuItem1.setText("ADD DATABASE");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem1);

        jMenuBar2.add(jMenu4);

        jMenu5.setText("STRUCTURE");

        CREATETABLETOOLBAR.setText("CREATE TABLE");
        jMenu5.add(CREATETABLETOOLBAR);

        jMenuBar2.add(jMenu5);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1465, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 747, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SELECTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SELECTActionPerformed
        // TODO add your handling code here:

        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        modelStatus = (DefaultTableModel) status.getModel();
        String status = "";
        ResultSet result = null;

        try {

            //la consulta a realizar
            PreparedStatement st = connectBD.prepareStatement("select * from " + BDSELECTED);
            result = st.executeQuery();
            ResultSetMetaData rsmd = result.getMetaData();
            int cantColumnasResult = rsmd.getColumnCount();

            for (int i = 0; i < cantColumnasResult; i++) {

                model.addColumn(rsmd.getColumnLabel(i + 1));
            }

            int vueltas = 0;
            while (result.next()) {
                Object[] rows = new Object[cantColumnasResult];
                vueltas++;

                for (int i = 0; i < cantColumnasResult; i++) {

                    rows[i] = result.getObject(i + 1);

                }
                model.addRow(rows);

            }

            status = "Query[ select * from " + BDSELECTED + " ] finished, it returns " + String.valueOf(model.getRowCount()) + " rows";
            String[] statusObj = new String[1];
            statusObj[0] = status;

            modelStatus.addRow(statusObj);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }


    }//GEN-LAST:event_SELECTActionPerformed

    private void SQLDETAILSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SQLDETAILSActionPerformed
        // TODO add your handling code here:
        String query = "select type,sql  from sqlite_master where name ='" + BDSELECTED + "'";
        boolean IsTrigger = false, IsIndex = false, IsView = false;
        ResultSet result = null;

        try {

            //la consulta a realizar
            PreparedStatement st = connectBD.prepareStatement("select type,sql  from sqlite_master where name ='" + BDSELECTED + "'");
            result = st.executeQuery();
            ResultSetMetaData rsmd = result.getMetaData();

            while (result.next()) {

                result.getString("type");

                if (result.getString("type").contains("index")) {
                    IsIndex = true;
                }
                if (result.getString("type").contains("trigger")) {
                    IsTrigger = true;
                }
                if (result.getString("type").contains("view")) {
                    IsView = true;
                }
            }

            if (IsTrigger == true) {
                TriggerPanel trigger = new TriggerPanel(connectBD, query);
                trigger.setVisible(true);
            }
            if (IsIndex == true) {

                IndexPanel index = new IndexPanel(connectBD, query);
                index.setVisible(true);

            }
            if (IsView == true) {
                String query2 = "select sql from sqlite_master where tbl_name = '" + BDSELECTED + "'";
                ViewPanel view = new ViewPanel(connectBD, query2);
                view.setVisible(true);
            }

        } catch (Exception e) {

        }


    }//GEN-LAST:event_SQLDETAILSActionPerformed

    private void CREATETABLEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CREATETABLEActionPerformed
        // TODO add your handling code here:

        if (BDSELECTED.equals("TABLES")) {

            CreateTable createTable = new CreateTable();
            createTable.setVisible(true);
        }


    }//GEN-LAST:event_CREATETABLEActionPerformed

    private void DBSValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_DBSValueChanged
        // TODO add your handling code here:

        TreePath path = DBS.getSelectionPath();
        Object[] nodos = path.getPath();
        nodoSeleccionado = (DefaultMutableTreeNode) DBS.getLastSelectedPathComponent();

        for (Object nodo : nodos) {
            if (pathTree.equals("")) {
                pathTree = nodo.toString();
            } else {
                pathTree = pathTree + "/" + nodo.toString();
            }
        }
    }//GEN-LAST:event_DBSValueChanged

    private void DBSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DBSMouseClicked
        // TODO add your handling code here:
        TreeSelectionModel smd = DBS.getSelectionModel();

        if (smd.getSelectionCount() > 0) {

            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) DBS.getSelectionPath().getLastPathComponent();
            File miFile = new File(pathTree);

            if (miFile.exists() && miFile.isFile()) {

                BDSELECTED = selectedNode.getUserObject().toString();

            } else {
                BDSELECTED = selectedNode.getUserObject().toString();
            }
        }

        if (connectBD != null && evt.getClickCount() == 2 && TableExists(BDSELECTED) == true) {

            CREATETABLE.setEnabled(true);

            modelData = (DefaultTableModel) dataTable.getModel();
            modelData.setRowCount(0);
            modelData.setColumnCount(0);

            modelInfo = (DefaultTableModel) infoTable.getModel();
            modelInfo.setRowCount(0);
            modelInfo.setColumnCount(0);

            ResultSet result = null;

            try {

                //la consulta a realizar
                PreparedStatement st = connectBD.prepareStatement("select * from pragma_table_info('" + BDSELECTED + "')");

                result = st.executeQuery();
                ResultSetMetaData rsmd = result.getMetaData();
                int cantColumnasResult = rsmd.getColumnCount();

                for (int i = 0; i < cantColumnasResult; i++) {

                    modelData.addColumn(rsmd.getColumnLabel(i + 1));
                }

                int vueltas = 0;
                while (result.next()) {
                    Object[] rows = new Object[cantColumnasResult];
                    vueltas++;

                    for (int i = 0; i < cantColumnasResult; i++) {

                        rows[i] = result.getObject(i + 1);

                    }
                    modelData.addRow(rows);

                    //OTRA TABLA
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage().toString());
            }

            try {

                modelInfo = (DefaultTableModel) infoTable.getModel();
                modelInfo.setRowCount(0);
                modelInfo.setColumnCount(0);
                ResultSet result2 = null;
                PreparedStatement st2 = connectBD.prepareStatement("select * from " + BDSELECTED + "");

                result2 = st2.executeQuery();
                ResultSetMetaData rsmd2 = result2.getMetaData();
                int cantColumnasResult2 = rsmd2.getColumnCount();

                for (int i = 0; i < cantColumnasResult2; i++) {

                    modelInfo.addColumn(rsmd2.getColumnLabel(i + 1));
                }

                int vueltas2 = 0;
                while (result2.next()) {

                    Object[] rows2 = new Object[cantColumnasResult2];
                    vueltas2++;

                    for (int i = 0; i < cantColumnasResult2; i++) {

                        rows2[i] = result2.getObject(i + 1);

                    }
                    modelInfo.addRow(rows2);
                }
            } catch (Exception e) {

            }

            //CONTROL DE LOS POPMENU DEL TREE
        }

        if (BDSELECTED.equals("TABLES")) {

            CREATETABLE.setEnabled(true);

        } else if (!BDSELECTED.equals("TABLES")) {

            CREATETABLE.setEnabled(false);

        } else if (BDSELECTED.equals("DBS")) {

            AddDatabase.setEnabled(true);

        } else if (!BDSELECTED.equals("DBS")) {

            AddDatabase.setEnabled(false);

        }


    }//GEN-LAST:event_DBSMouseClicked

    private void queryKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_queryKeyReleased
        // TODO add your handling code here:

        if (!query.getText().isEmpty()) {
            run.setEnabled(true);
            clear.setEnabled(true);
        } else {
            run.setEnabled(false);
            clear.setEnabled(false);
        }

        if (query.getText().contains("select")) {

            //RESALTECOMANDOS();
        }
    }//GEN-LAST:event_queryKeyReleased

    private void queryInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_queryInputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_queryInputMethodTextChanged

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearActionPerformed
        // TODO add your handling code here:
        query.setText("");
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        //limpio tabla result
        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        //limpio tablas
        modelData = (DefaultTableModel) dataTable.getModel();
        modelData.setRowCount(0);
        modelData.setColumnCount(0);

        modelInfo = (DefaultTableModel) infoTable.getModel();
        modelInfo.setRowCount(0);
        modelInfo.setColumnCount(0);
    }//GEN-LAST:event_clearActionPerformed

    private void runActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runActionPerformed
        // TODO add your handling code here:

        model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);

        modelStatus = (DefaultTableModel) status.getModel();
        String status = "";
        ResultSet result = null;

        try {

            //la consulta a realizar
            PreparedStatement st = connectBD.prepareStatement(query.getText());
            result = st.executeQuery();
            ResultSetMetaData rsmd = result.getMetaData();
            int cantColumnasResult = rsmd.getColumnCount();

            for (int i = 0; i < cantColumnasResult; i++) {

                model.addColumn(rsmd.getColumnLabel(i + 1));
            }

            int vueltas = 0;
            while (result.next()) {

                Object[] rows = new Object[cantColumnasResult];
                vueltas++;

                for (int i = 0; i < cantColumnasResult; i++) {

                    rows[i] = result.getObject(i + 1);

                }
                model.addRow(rows);

            }

            status = "Query[ " + query.getText() + " ] finished, it returns " + String.valueOf(model.getRowCount()) + " rows";
            String[] statusObj = new String[1];
            statusObj[0] = status;

            modelStatus.addRow(statusObj);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
    }//GEN-LAST:event_runActionPerformed

    private void disconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectActionPerformed
        // TODO add your handling code here:

        try {

            connectBD.close();
            connect.setEnabled(true);
            disconnect.setEnabled(false);
            run.setEnabled(false);
            clear.setEnabled(false);

            query.setText("");
            //limpio tabla result
            model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            model.setColumnCount(0);

            modelData = (DefaultTableModel) dataTable.getModel();
            modelData.setRowCount(0);
            modelData.setColumnCount(0);

            modelInfo = (DefaultTableModel) infoTable.getModel();
            modelInfo.setRowCount(0);
            modelInfo.setColumnCount(0);

            JOptionPane.showMessageDialog(null, "DESCONECTADO DE LA BASE DE DATOS!!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
    }//GEN-LAST:event_disconnectActionPerformed

    private void connectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectActionPerformed
        // TODO add your handling code here:
        try {

            //conectar con la bd
            BDCONNCECTED = "jdbc:sqlite:C:/Users/AxelM/Downloads/archive/" + BDSELECTED;

            if (BDSELECTED.contains(".db") == true) {

                connectBD = DriverManager.getConnection(BDCONNCECTED);

                if (connectBD != null) {
                    JOptionPane.showMessageDialog(null, "CONECTADO A LA BASE DE DATOS!!");
                    connect.setEnabled(false);
                    disconnect.setEnabled(true);
                    model = (DefaultTableModel) table.getModel();
                    model.setRowCount(0);
                    model.setColumnCount(0);
                }
            } else {
                JOptionPane.showMessageDialog(null, "ELIGE UNA BASE DE DATOS .DB!!");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, e.getMessage().toString());

        }
    }//GEN-LAST:event_connectActionPerformed

    private void AddDatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddDatabaseActionPerformed
        // TODO add your handling code here:

        if (BDSELECTED.equals("DBS")) {

            JFileChooser JF = new JFileChooser();
            JF.showOpenDialog(this);
            File fileChoosed = JF.getSelectedFile();

            if (fileChoosed != null) {

                try {
                    AD_DBS_TO_TREE(fileChoosed, nodoSeleccionado, "Nombre");

                } catch (SQLException ex) {
                    Logger.getLogger(WorkBenchPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

    }//GEN-LAST:event_AddDatabaseActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:

        DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getRoot();

        JFileChooser JF = new JFileChooser();
        JF.showOpenDialog(this);
        File fileChoosed = JF.getSelectedFile();

        if (fileChoosed != null) {

            try {
                AD_DBS_TO_TREE(fileChoosed, root, "Nombre");

            } catch (SQLException ex) {
                Logger.getLogger(WorkBenchPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    boolean selectPuesto = false;

    private void RESALTECOMANDOS() {

        String consulta = query.getText();
        String comando = "select";

        DefaultHighlighter.DefaultHighlightPainter highlight = new DefaultHighlighter.DefaultHighlightPainter(Color.BLUE);
        Highlighter h = query.getHighlighter();
        h.removeAllHighlights();
        int tamano = comando.length();

        for (int i = 0; i < consulta.length(); i++) {

            //char letra = consulta.charAt(i); if(comando.indexOf(letra)>=0)
            if (query.getText().contains(comando) && i < tamano) {
                //query.setForeground(Color.BLUE);
                try {
                    h.addHighlight(i, i + 1, highlight);

                } catch (BadLocationException e) {

                    //Logger.getLogger(comando);
                }

            } else {
                //query.setIgnoreRepaint(true);
            }
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
            java.util.logging.Logger.getLogger(WorkBenchPanel.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WorkBenchPanel.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WorkBenchPanel.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WorkBenchPanel.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new WorkBenchPanel().setVisible(true);

                } catch (SQLException ex) {
                    Logger.getLogger(WorkBenchPanel.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem AddDatabase;
    private javax.swing.JMenuItem CREATETABLE;
    private javax.swing.JMenuItem CREATETABLETOOLBAR;
    private javax.swing.JMenuItem DATABASE;
    private javax.swing.JTree DBS;
    private javax.swing.JMenuItem SELECT;
    private javax.swing.JMenuItem SQLDETAILS;
    private javax.swing.JPopupMenu TreePopMenu;
    private javax.swing.JButton clear;
    private javax.swing.JButton connect;
    private javax.swing.JTable dataTable;
    private javax.swing.JButton disconnect;
    private javax.swing.JPopupMenu filePopMenu;
    private javax.swing.JTable infoTable;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTextArea query;
    private javax.swing.JButton run;
    private javax.swing.JTable status;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
