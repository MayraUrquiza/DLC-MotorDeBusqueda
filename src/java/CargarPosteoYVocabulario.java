
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
        agregarDocumentoPosteo(v);
        
//        ArrayList<String> voc = v.obtenerVocabulario(v);
//        for (String entrada : voc)
//        {
//            System.out.println(entrada + "\n");
//        }

//        String cadena = " SELDOME stopes.";
//        StringTokenizer st = new StringTokenizer(cadena, "\"’,.-_+&<>``={}~^@/()[]%'*$|°[0-1-2-3-4-5-6-7-8-9]#:*»«?¡!¿; \n");
//        ArrayList<String> palabrasBuscadas = new ArrayList<>();
//        while(st.hasMoreTokens())
//        {
//            String p = st.nextToken().toLowerCase();
//            palabrasBuscadas.add(p);
//        }
//         ArrayList<Termino> terminos = Buscador.getResultados(palabrasBuscadas, v);
//        for (Termino t : terminos)
//        {
//            System.out.println(t.getPalabra());
//        }
//        Buscador.agregarDocumentos();
//        HashMap<String, Documento> documentos = Buscador.getDocumentos();
//        ArrayList<String> docs = new ArrayList<>();
//        ArrayList<Double> pesos = new ArrayList<>();
//        for (String documento : documentos.keySet())
//        {
//            docs.add(documento);
//            System.out.println(documento);
//            pesos.add(documentos.get(documento).getPeso());
//            System.out.println(documentos.get(documento).getPeso());
//        }

        
//        BD posteo = new BD();
//        long ti = System.currentTimeMillis();
//        try 
//        {
//            posteo.openConnection();
//            System.out.println("Cantidad de archivos: " + posteo.getCantidadDeDocumentos("palabraXDocumento"));
//        } 
//        catch (Exception ex) 
//        {
//            Logger.getLogger(CargarPosteoYVocabulario.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        finally
//        {
//            posteo.closeConnection();
//        }
//        long tf = System.currentTimeMillis() - ti;
//        System.out.println("Tiempo total: " + tf/1000 + " segundos");
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
}
