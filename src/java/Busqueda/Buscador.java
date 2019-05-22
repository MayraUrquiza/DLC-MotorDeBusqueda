package Busqueda;


import BD.BD;
import Vocabulario.Termino;
import Vocabulario.Vocabulario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mayur
 */
public class Buscador 
{
   
    private final ArrayList<Termino> busqueda = new ArrayList<>();
    private int N = 597; // Cantidad total de documentos en la base
    private int R = 25; // Cantidad de documentos que interesan
    private HashMap<String, Documento> documentos = new HashMap<>();
    
    public Buscador(ArrayList<String> lista, Vocabulario v)
    {
        this.buscarTerminos(lista, v);
    }
    
    public Buscador()
    {
        
    }
    
//    public ArrayList getResultados(ArrayList<String> lista, Vocabulario v) 
//    {
//        this.buscarTerminos(lista, v);
//        return busqueda;
//    }
    
    private void buscarTerminos(ArrayList<String> lista, Vocabulario v)
    {
        if (lista != null)
        {
            for (String palabra : lista) 
            {
                busqueda.add(v.get(palabra));
            }
        }
    }    
    
    private ArrayList<Documento> buscarDocumentos(Termino t)
    {
        BD posteo = new BD();
        ArrayList<Documento> docs = new ArrayList<>();
        try 
        {
            posteo.openConnection();
            docs = posteo.selectData(t, R, N);
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
       
    public void agregarDocumentos()
    {
        this.ordenarPalabrasBuscadas();
        ArrayList<Documento> docs = new ArrayList<>();
        ArrayList<Documento> buscados;
        for (int i = 0; i < busqueda.size(); i++)
        {
            Termino t = busqueda.get(i);
            buscados = this.buscarDocumentos(t);
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
    }
    
//    public void mostrarResultados()
//    {
//        System.out.println("DOCUMENTOS: \n");
//        for(Iterator i = documentos.keySet().iterator(); i.hasNext();)
//        {
//            Documento d = (Documento) documentos.get((String)i.next());
//            System.out.println(d.getNombre() + " - Peso: " + d.getPeso());
//        }
//    }
    
    private void ordenarPalabrasBuscadas()
    {
        if(!busqueda.contains(null))Collections.sort(busqueda);
    }
    
    private LinkedHashMap<String, Documento> ordenarPorRelevancia() 
    {
        List<Documento> mapValues = new ArrayList<>(documentos.values());
        Collections.sort(mapValues);

        LinkedHashMap<String, Documento> sortedMap = new LinkedHashMap<>();
        Iterator<Documento> it = mapValues.iterator();
        while (it.hasNext())
        {
            Documento doc = it.next();
            sortedMap.put(doc.getNombre(), doc);
        }
        return sortedMap;
    }

    public int getN() 
    {
        return N;
    }

    public void setN(int N) 
    {
        this.N = N;
    }

    public int getR() 
    {
        return R;
    }

    public void setR(int R) 
    {
        this.R = R;
    }

    public LinkedHashMap<String, Documento> getDocumentos() 
    {
        return this.ordenarPorRelevancia();
    }

    public void setDocumentos(HashMap<String, Documento> documentos) 
    {
        this.documentos = documentos;
    }
}
