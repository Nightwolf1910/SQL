package metodos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public static String url="jdbc:oracle:thin:@localhost:1521:XE";
    public static String usuario="system";
    public static String contraseña="admin";
    public static String clase="oracle.jdbc.OracleDriver";
    public static Connection conectarBaseDatos(){
        Connection conexion=null;
        try {
            Class.forName(clase);
            conexion=DriverManager.getConnection(url,usuario,contraseña);
            System.out.println("Conexion establecida");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return conexion;
    }
    public static void main(String[] args) {
        conectarBaseDatos();
    }
}
/*package metodos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ConexionBD {
    Connection con=null;
    public Connection getConnection(){
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            con=DriverManager.getConnection("jdbc:derby://localhost:1527/IngDatos");
            System.out.println("Conexion en linea");
        } catch (Exception e) {
            System.out.println("Error"+e.getMessage());
        }
        return con;
    }
    public ResultSet consulta(String consulta){
        ResultSet rs=null;
        try {
            PreparedStatement ps=con.prepareStatement(consulta);
            rs=ps.executeQuery();
        } catch (Exception e) {
            System.out.println("Error: "+e.getMessage());
        }
       return rs;
    }
    public static void main(String[] args) {
        ConexionBD c=new ConexionBD();
        c.getConnection();
    }
}
*/