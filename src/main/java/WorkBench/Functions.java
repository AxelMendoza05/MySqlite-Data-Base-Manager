
package WorkBench;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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

import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;

/**
 *
 * @author AxelM
 */
public class Functions {

    public static ArrayList<String> NameIndex = new ArrayList<>();
    public static ArrayList<String> NameTABLE = new ArrayList<>();
    public static ArrayList<String> NameTRIGGER = new ArrayList<>();
    public static ArrayList<String> NameVIEW = new ArrayList<>();

    public Functions() {

    }

    public ArrayList<String> GetTablesName(String url) throws SQLException {

        Connection connect = DriverManager.getConnection(url);
        if (connect == null) {
            System.out.println("NULO");
        }

        ArrayList<String> nameTables = new ArrayList<String>();

        try {

            ResultSet result = null;
            //la consulta a realizar
            PreparedStatement st = connect.prepareStatement("select name from sqlite_master where type = \"table\"");
            result = st.executeQuery();

            //int cantColums = rsmd.getColumnCount();
            //Object[]filas = new Object[cantColums];
            while (result.next()) {

                nameTables.add(result.getString("name"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }

        return nameTables;

    }

    public Object GetTablesName2(Connection dbs) throws SQLException {

        Object[] filas = null;

        try {

            ResultSet result = null;
            //la consulta a realizar
            PreparedStatement st = dbs.prepareStatement("select name from sqlite_master where type = table");
            result = st.executeQuery();
            ResultSetMetaData rsmd = result.getMetaData();

            int cantColums = rsmd.getColumnCount();
            filas = new Object[cantColums];

            while (result.next()) {

                for (int i = 0; i < cantColums; i++) {

                    filas[i] = result.getObject(i);
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }

        return filas;

    }

}
