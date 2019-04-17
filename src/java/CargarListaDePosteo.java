
import BD.BD;
import Vocabulario.Vocabulario;
import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mayur
 */
public class CargarListaDePosteo 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception 
    {
        // TODO code application logic here
        Vocabulario v = new Vocabulario();
        long ti = System.currentTimeMillis();
        File f = new File("prueba.txt");
        try
        {
            f.delete();
        }
        catch(Exception ex)
        {
            throw ex;
        }
        v.agregarCarpetaDocumentos();
        BD posteo = new BD();
        posteo.MySQLConnection();
        posteo.loadData("prueba.txt", "palabraXDocumento");
        posteo.closeConnection();
        long tf = System.currentTimeMillis() - ti;
        System.out.println("Tiempo total: " + tf/1000 + " segundos");
    }
    
}
