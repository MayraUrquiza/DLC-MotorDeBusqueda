package BD;

import Busqueda.Documento;
import Vocabulario.Termino;
import Vocabulario.Vocabulario;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

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
    public static String pass = "root";
    public static String driver = "com.mysql.jdbc.Driver";
    
    public static final int SINGLECONNECTIONMODE = 1;
    public static final int POOLCONNECTIONMODE = 2;
    
    private int connectionMode = SINGLECONNECTIONMODE;
    private static String resourceName = "jdbc/__default";
    
    Context ctx = null;
    DataSource ds = null;
    java.sql.Connection cn = null;
    java.sql.Statement stmt = null;
    PreparedStatement pstmt = null;
    CallableStatement cstmt = null;
    
    public BD()
    {
        
    }
    
    public void setConnectionMode(int connectionMode) 
    {
        this.connectionMode = connectionMode;
        if (this.connectionMode != POOLCONNECTIONMODE) 
        {
            this.connectionMode = SINGLECONNECTIONMODE;
        }
    }
    
    public int getConnectionMode() 
    {
        return this.connectionMode;
    }
    
    public void openConnection() throws Exception 
    {
        if (this.cn == null) 
        {
            if (this.connectionMode == SINGLECONNECTIONMODE) 
            {
                Class.forName(driver);
                this.cn = DriverManager.getConnection(BD.servidor, BD.user, BD.pass);
            } 
            else 
            {
                this.setContext();
                this.cn = this.ds.getConnection();
            }
            System.out.println("Conexión iniciada.");
        }
    }    
    
    private void setContext() throws Exception 
    {
        if (this.ds == null) 
        {
            if (BD.resourceName == null) 
            {
                throw new Exception("DBManager ERROR: ResourceName (JNDI) NO especificado");
            }
            this.ctx = new InitialContext();
            this.ds = (DataSource) this.ctx.lookup(resourceName);
        }
    }
    
    public void closeConnection() 
    {
        if (this.stmt != null) 
        {
            try 
            {
            this.stmt.close();
            } 
            catch (Exception e) {}
            this.stmt = null;
        }

        if (this.pstmt != null) 
        {
            try 
            {
                this.pstmt.close();
            } 
            catch (Exception e) {}
            this.pstmt = null;
        }

        if (this.cstmt != null) 
        {
            try 
            {
                this.cstmt.close();
            } 
            catch (Exception e) {}
            this.cstmt = null;
        }

        if (this.cn != null) 
        {
            try 
            {
                this.cn.close();
            } 
            catch (Exception e) {}
            this.cn = null;
            this.ds = null;
        }

        if (this.ctx != null) 
        {
            try 
            {
                this.ctx.close();
            } 
            catch (Exception e) {}
            this.ctx = null;
        }
    }
    
    public void loadData(String dataFile, String tabla)
    {
        try 
        {
            String query = "LOAD DATA LOCAL INFILE '" + dataFile + "' INTO TABLE " + tabla + " FIELDS TERMINATED BY \';\'";
            stmt = cn.createStatement();
            stmt.executeUpdate(query);
            System.out.println("Se cargó el posteo en la base de datos.");
        } 
        catch (SQLException ex) 
        {
            System.out.println("No se pudo cargar el posteo en la base de datos.");
            System.err.println(ex.getMessage());
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
                stmt = cn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
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
                System.err.println(ex.getMessage());
            }
        }
        return resultados;
    }
    
    //Calculo el módulo normalizado para no favorecer a los documentos más largos.
    public double[] calcularCociente(String documento, int N, Vocabulario v)
    {
        double cociente = 0.0;
        int palabras = 0;
        try
        {
            String query = "SELECT palabra, frecuencia FROM palabraXDocumento WHERE documento = '" + documento + "'";
            stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next())
            {
                palabras += 1;
                Termino ts = v.get(rs.getString("palabra"));
                if (ts != null)
                {
                    cociente += Math.pow((double) rs.getInt("frecuencia") * Math.log10((double) N / (double) ts.getNroDocumentos()), 2);
                }
            }
        }
        catch(SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
        double[] vector = new double[2]; 
        vector[0] = cociente;
        vector[1] = palabras;
        return vector;
    }
    
    public ArrayList<String> obtenerCoincidencias(ArrayList<Termino> terminos, String documento)
    {
        ArrayList<String> coincidencias = new ArrayList<>();
        if(terminos != null)
        {
            for (Termino t : terminos)
            {
                try
                {
                    String query = "SELECT * FROM palabraXDocumento WHERE palabra = '" + t.getPalabra() + "' AND documento = '" + documento + "'";
                    stmt = cn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next())
                    {
                        coincidencias.add(rs.getString("palabra") + " (" + rs.getInt("frecuencia") + ")");
                    }
                }
                catch(SQLException ex)
                {
                    System.out.println("No se pudo realizar la consulta.");
                    System.err.println(ex.getMessage());
                }
            }
        }
        return coincidencias;
    }
    
    public int getCantidadDeDocumentos(String tabla)
    {
        int cantidad = 0;
        try 
        {
            String query = "SELECT DISTINCT documento FROM " + tabla + " WHERE documento LIKE '%.txt'";
            stmt = cn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
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
