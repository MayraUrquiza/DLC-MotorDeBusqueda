
import BD.BD;
import Busqueda.Buscador;
import Busqueda.Documento;
import Serializacion.VocabularioIOException;
import Serializacion.VocabularioReader;
import Serializacion.VocabularioWriter;
import Vocabulario.Termino;
import Vocabulario.Vocabulario;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Scanner;
import java.util.StringTokenizer;
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
public class CargarPosteoYVocabulario 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException
    {           
        Vocabulario v;
//        v = primeraCarga();
        v = recuperarVocabulario();
        calcularRadicandos(593, v);
        
//        agregarDocumentoPosteo(v);
    }
    
    private static Vocabulario primeraCarga()
    {
        long ti = System.currentTimeMillis();
        
        // Creo el vocabulario por primera vez
        Vocabulario v = new Vocabulario();
        
        // Si existe el archivo que contiene la lista de posteo lo elimino
        eliminar("posteo.txt");
        eliminar("agregado.txt");
        
        try 
        {
            v.agregarCarpeta();
        
            // Serializo el vocabulario
            serializar(v);

            // Cargo el posteo en la base de datos
//            cargarPosteo("posteo.txt");
        } 
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(null, "No se pudo agregar la carpeta con archivos.");
        }
        
        long tf = System.currentTimeMillis() - ti;
        System.out.println("Tiempo total: " + tf/1000 + " segundos");
        return v;
    }
    
    private static Vocabulario recuperarVocabulario()
    {
        long ti = System.currentTimeMillis();
        
        //Recupero el vocabulario serialzado
        VocabularioReader vr = new VocabularioReader("vocabulario.dat");
        Vocabulario v = null;
        try
        {
            v = vr.read();
            JOptionPane.showMessageDialog(null, "Hashtable recuperada corretamente.");
//            System.out.println(v.toString());
        }
        catch (VocabularioIOException ex)
        {
            JOptionPane.showMessageDialog(null, "No se pudo recuperar la hashtable.");
        }
        
        long tf = System.currentTimeMillis() - ti;
        System.out.println("Tiempo total: " + tf/1000 + " segundos");
        return v;
    }
    
    private static void agregarDocumentoPosteo(Vocabulario v)
    {
        long ti = System.currentTimeMillis();
        eliminar("agregado.txt");
        try 
        {
            v.agregarDocumento("C:\\NetBeansProjects\\DLC-MotorDeBusqueda\\", "prueba.txt", "agregado.txt");
        } 
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(null, "No se pudo agregar el archivo.");
        }
        
        // Serializo el vocabulario con los términos recién agregados
        serializar(v);

        // Cargo el posteo del nuevo archivo en la base de datos
//        cargarPosteo("agregado.txt");
        
        long tf = System.currentTimeMillis() - ti;
        System.out.println("Tiempo total: " + tf/1000 + " segundos");
    }
    
    private static void eliminar(String archivo)
    {
        File f = new File(archivo);
        try
        {
            f.delete();
        }
        catch(Exception ex)
        {
            throw ex;
        }
    }
    
    private static void serializar(Vocabulario v)
    {
        VocabularioWriter vw = new VocabularioWriter();
        try
        {
            vw.write(v);
            JOptionPane.showMessageDialog(null, "Hashtable serializada correctamente.");
        }
        catch (VocabularioIOException ex)
        {
            JOptionPane.showMessageDialog(null, "No se pudo escribir la hashtable.");
        }
    }
    
    private static void cargarPosteo(String archivo)
    {
        BD posteo = new BD();
        try 
        {
            posteo.openConnection();
            posteo.loadData(archivo, "palabraXDocumento");
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(CargarPosteoYVocabulario.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            posteo.closeConnection();
        }
    }
    
    //Calcula para todos los documentos los radicandos para la fórmula del peso y los almacena en la tabla "Documento" de la base de datos
    private static void calcularRadicandos(int N, Vocabulario v)
    {
        BD posteo = new BD();
        posteo.setConnectionMode(1);
        
        FilenameFilter filter = new FilenameFilter() //filtro para archivos .txt
        {
            public boolean accept(File dir, String fileName)
            {
                return fileName.endsWith("txt");
            }
        };

        File f = new File("C:\\NetBeansProjects\\DLC-MotorDeBusqueda\\DocumentosTP1");
        String [] archivos = f.list(filter);
        try
        {
            long ti = System.currentTimeMillis();
            
            posteo.openConnection();

            for (int i = 0; i < archivos.length; i++)
            {
                double[] vector = posteo.calcularCociente(archivos[i], N, v);
                double radicando = vector[0];
                int cantPalabras = (int) vector[1];
                posteo.insertData("Documento", archivos[i], radicando, cantPalabras);
            }
            
            long tf = System.currentTimeMillis() - ti;
            System.out.println("Tiempo total: " + tf/1000 + " segundos");
        }
        catch (Exception ex)
        {
            System.err.println(ex.getMessage());
        }
        finally
        {
            posteo.closeConnection();
        }
    }
}
