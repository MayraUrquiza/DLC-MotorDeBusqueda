package BD;

import Busqueda.Documento;
import Vocabulario.Termino;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public static String servidor = "jdbc:mysql://localhost:3306/DLC-Posteo";
    public static String user = "root";
    public static String pass = "";
    
    public static String driver = "com.mysql.jdbc.Driver";
    public static Connection conexion;
    
    public BD()
    {
        
    }
    
    public void openConnection() throws Exception 
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = (Connection) DriverManager.getConnection(BD.servidor, BD.user, BD.pass);
            System.out.println("Conexión iniciada.");
        } 
        catch (ClassNotFoundException | SQLException ex) 
        {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeConnection() 
    {
        try 
        {
            conexion.close();
//            JOptionPane.showMessageDialog(null, "Se ha finalizado la conexión con el servidor");
            System.out.println("Conexión finalizada.");
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadData(String dataFile, String tabla)
    {
        try 
        {
            String query = "LOAD DATA LOCAL INFILE '" + dataFile + "' INTO TABLE " + tabla + " FIELDS TERMINATED BY \',\'";
            Statement st = (Statement) conexion.createStatement();
            st.executeUpdate(query);
            System.out.println("Se cargó el posteo en la base de datos.");
        } 
        catch (SQLException ex) 
        {
            System.out.println("No se pudo cargar el posteo en la base de datos.");
        } 
    }
    
    public ArrayList selectData(Termino t, int R, int N)
    {
        ArrayList<Documento> resultados = new ArrayList<>();
        if(t != null)
        {
            try
            {
                String query = "SELECT documento, frecuencia FROM palabraXDocumento WHERE palabra = '" + t.getPalabra() +"' ORDER BY frecuencia DESC LIMIT " + R;
                Statement st = (Statement) conexion.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next())
                {
                    String documento = rs.getString("documento");
                    int frecuencia = rs.getInt("frecuencia");
                    Documento doc = new Documento(documento);
                    doc.calcularPeso(t, N, frecuencia);
                    resultados.add(doc);
                }
            }
            catch(SQLException ex)
            {
                System.out.println("No se pudo realizar la consulta.");
            }
        }
        return resultados;
    }
    
    public int getCantidadDeDocumentos(String tabla)
    {
        int cantidad = 0;
        try 
        {
            String query = "SELECT DISTINCT documento FROM " + tabla + " WHERE documento LIKE '%.txt'";
            Statement st = (Statement) conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.last();
            cantidad = rs.getRow();
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidad;
    }
}
