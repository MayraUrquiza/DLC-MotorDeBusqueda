package BD;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mayur
 */
public class BD 
{
    public static String servidor = "jdbc:mysql://localhost:3306/DLC_Posteo";
    public static String user = "root";
    public static String pass = "";
    
    public static String driver = "com.mysql.jdbc.Driver";
    public static Connection conexion;
    
    public BD()
    {
        
    }
    
    public void MySQLConnection() throws Exception 
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = (Connection) DriverManager.getConnection(this.servidor, this.user, this.pass);
            JOptionPane.showMessageDialog(null, "Se ha iniciado la conexión con el servidor de forma exitosa");
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection() 
    {
        try 
        {
            conexion.close();
            JOptionPane.showMessageDialog(null, "Se ha finalizado la conexión con el servidor");
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    public void insertData(String table, String palabra, String documento, int frecuencia) 
    {
        try 
        {
            String query = "INSERT INTO " + table + " VALUES("
                    + "\"" + palabra + "\", "
                    + "\"" + documento + "\", "
                    + "\"" + frecuencia + "\")";
            Statement st = (Statement) conexion.createStatement();
            st.executeUpdate(query);
//            System.out.println("Datos almacenados");
//            JOptionPane.showMessageDialog(null, "Datos almacenados de forma exitosa");
        } 
        catch (SQLException ex) 
        {
//            System.out.println("Error");
//            JOptionPane.showMessageDialog(null, "Error en el almacenamiento de datos");
        }
    } 
    
    public void loadData(String dataFile, String table)
    {
        try 
        {
            String query = "LOAD DATA LOCAL INFILE '" + dataFile + "' INTO TABLE " + table + " FIELDS TERMINATED BY \',\'";
            Statement st = (Statement) conexion.createStatement();
            st.executeUpdate(query);
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
