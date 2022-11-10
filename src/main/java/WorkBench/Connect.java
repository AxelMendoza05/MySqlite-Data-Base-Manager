
package WorkBench;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Connect {

    //public static String urlDb = "jdbc:sqlite:";
    public static Connection connect = null;

    public void Connect() {
        
    }

    public static void setCon(String urlDB) throws SQLException {

        try {
            connect = DriverManager.getConnection(urlDB);

            //conectar con la bd
            if (connect != null) {
                JOptionPane.showMessageDialog(null, "CONECTADO!!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
        }
    }

    public static Connection getConn() {
        return connect;
    }

    public static void Disconnect() throws SQLException {

        connect.close();
        JOptionPane.showMessageDialog(null, "DESCONECTADO!!");

    }

    PreparedStatement prepareStatement(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
