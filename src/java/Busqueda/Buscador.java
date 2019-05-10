package Busqueda;


import BD.BD;
import Serializacion.VocabularioIOException;
import Serializacion.VocabularioReader;
import Vocabulario.Termino;
import Vocabulario.Vocabulario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
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
public abstract class Buscador 
{
   
    private static final ArrayList<Termino> busqueda = new ArrayList<>();
    private static int N; // Cantidad total de documentos en la base
    private static int R = 25; // Cantidad de documentos que interesan
    private static HashMap<String, Documento> documentos = new HashMap<>();
    
    public static ArrayList getResultados(ArrayList<String> lista, Vocabulario v) 
    {
        Buscador.buscarTerminos(lista, v);
        return busqueda;
    }
    
    private static void buscarTerminos(ArrayList<String> lista, Vocabulario v)
    {
        for (String palabra : lista) 
        {
            busqueda.add(v.get(palabra));
        }
    }    
    
    public static ArrayList<Documento> buscarDocumentos(Termino t)
    {
        ordenarPalabrasBuscadas();
        BD posteo = new BD();
        ArrayList<Documento> docs = new ArrayList<>();
        try 
        {
            posteo.openConnection();
            docs = posteo.selectData(t, R, 593);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE, null, ex);
        }        
        finally
        {
            posteo.closeConnection();
        }
        return docs;
    }
       
    public static void agregarDocumentos()
    {
        ArrayList<Documento> docs = new ArrayList<>();
        ArrayList<Documento> buscados;
        for (int i = 0; i < busqueda.size(); i++)
        {
            Termino t = busqueda.get(i);
            buscados = buscarDocumentos(t);
            if(buscados != null)
            {
                for(Documento d : buscados)
                {
                    docs.add(d);
                }
            }
        }
        int cantidadDocs = 0;
        for (Documento d : docs)
        {
            if (cantidadDocs < R) 
            {
                if (!documentos.containsKey(d.getNombre()))
                {
                    documentos.put(d.getNombre(), d);
                    cantidadDocs ++;
                }
                else
                {
                    double peso = d.getPeso() + documentos.get(d.getNombre()).getPeso();
                    documentos.get(d.getNombre()).setPeso(peso);
                }
            }
            else
            {
                break;
            }
        }
        ordenarPorRelevancia();
    }
    
    public static void mostrarResultados()
    {
        System.out.println("DOCUMENTOS: \n");
        for(Iterator i = documentos.keySet().iterator(); i.hasNext();)
        {
            Documento d = (Documento) documentos.get((String)i.next());
            System.out.println(d.getNombre() + " - Peso: " + d.getPeso());
        }
    }
    
    private static void ordenarPalabrasBuscadas()
    {
        if(!busqueda.contains(null))Collections.sort(busqueda);
    }
    
    private static void ordenarPorRelevancia()
    {
        documentos.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(System.out::println);
    }

    public static int getN() 
    {
        return N;
    }

    public static void setN(int N) 
    {
        Buscador.N = N;
    }

    public static int getR() 
    {
        return R;
    }

    public static void setR(int R) 
    {
        Buscador.R = R;
    }

    public static HashMap<String, Documento> getDocumentos() 
    {
        return documentos;
    }

    public static void setDocumentos(HashMap<String, Documento> documentos) 
    {
        Buscador.documentos = documentos;
    }
}
