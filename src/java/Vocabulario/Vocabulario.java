/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vocabulario;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

/**
 *
 * @author mayur
 */
public class Vocabulario implements Serializable
{
    private Hashtable<String, Termino> vocabulario;
    
    public Vocabulario()
            
    {
        this.vocabulario = new Hashtable();
    }
    
    public void agregarTermino(Termino t)
    {
        //recibe un termino y agrega a la hashtable
        if (this.vocabulario.containsKey(t.getPalabra())) 
        {
            Termino aux = this.vocabulario.get(t.getPalabra());
            aux.setNroDocumentos(aux.getNroDocumentos()+1); //aumenta el número de documentos donde aparece la palabra
            if (aux.getMaxFrecuencia() < t.getMaxFrecuencia()) 
            {
                aux.setMaxFrecuencia(t.getMaxFrecuencia());
            }
            return;
        }
        this.vocabulario.put(t.getPalabra(), t);
    }
    
    public void agregarDocumento(String ruta, String archivo, String posteo) throws IOException
    {
        LectorDocumento l = new LectorDocumento(ruta + archivo); //toma un documento lo parsea y agrega las palabras a la hashtable
        
        Hashtable<String, Integer> aux = l.palabrasObtenidas();

        //probando crear archivo .txt
        FileWriter fichero = new FileWriter(posteo, true);
        PrintWriter pw = new PrintWriter(fichero);
            
        for (String palabra : aux.keySet()) 
        {
            int frecuencia = aux.get(palabra);

            Termino t = new Termino(palabra, frecuencia);
            this.agregarTermino(t);
            
            //probando escribir en archivo .txt
            pw.println(palabra + "," + archivo + "," + frecuencia);
        }
        System.out.println(archivo);
    }
    
    public Termino get(String palabra)
    {
        return this.vocabulario.get(palabra);
    }
    
    public void agregarCarpeta() throws FileNotFoundException, Exception
    {
        FilenameFilter filter = new FilenameFilter() //creo un filtro para archivos .txt
        {
            public boolean accept(File dir, String fileName)
            {
                return fileName.endsWith("txt");
            }
        };

        File f = new File("DocumentosTP1");
        String [] archivos = f.list(filter);
        
        if(archivos != null)
        {
            for(int i = 0; i < archivos.length; i++)
            {
                this.agregarDocumento(f.toString() + "\\", archivos[i], "posteo.txt");
            }
        }
    }

    @Override
    public String toString() 
    {
        return this.toString(vocabulario);
    }
    
    public String toString(Hashtable ht)
    {
        StringBuilder cadena = new StringBuilder("VOCABULARIO: \n");
        for (Iterator i = ht.keySet().iterator(); i.hasNext();)
        {
            cadena.append(ht.get((String)i.next()).toString());
            cadena.append("\n");
        }
        return cadena.toString();
    }
    
    public ArrayList<String> obtenerVocabulario(Vocabulario v)
    {
        ArrayList<String> voc = new ArrayList<>();
        for (Iterator i = v.vocabulario.keySet().iterator(); i.hasNext();)
        {
            voc.add(v.vocabulario.get((String)i.next()).toString());
        }
        return voc;
    }
}
