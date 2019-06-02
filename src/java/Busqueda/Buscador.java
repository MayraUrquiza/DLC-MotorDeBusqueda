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
    private int N = 593; // Cantidad total de documentos en la base
    private int R = 10; // Cantidad de documentos que interesan
    private HashMap<String, Documento> documentos = new HashMap<>();
    private Vocabulario v;
    
    public Buscador(ArrayList<String> lista, Vocabulario v)
    {
        this.v = v;
        this.buscarTerminos(lista);
    }
    
    public Buscador()
    {
        
    }
    
//    public ArrayList getResultados(ArrayList<String> lista, Vocabulario v) 
//    {
//        this.buscarTerminos(lista, v);
//        return busqueda;
//    }
    
    //Crea un arraylist con los términos correspondientes a las palabras buscadas
    private void buscarTerminos(ArrayList<String> lista)
    {
        if (lista != null)
        {
            for (String palabra : lista) 
            {
                busqueda.add(v.get(palabra));
            }
        }
    }    
    
    //Por cada término busca hasta R documentos con coincidencias
    private ArrayList<Documento> buscarDocumentos(Termino t)
    {
        BD posteo = new BD();
        posteo.setConnectionMode(2);
        ArrayList<Documento> docs = new ArrayList<>();
        try 
        {
            posteo.openConnection();
            docs = posteo.selectData(t, R, N);
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }        
        finally
        {
            posteo.closeConnection();
        }
        return docs;
    }
    
    //Reealiza la búsqueda de los R documentos más relevantes para la búsqueda
    public void agregarDocumentos()
    {
        this.ordenarPalabrasBuscadas(); //Ordena las palabras buscadas de acuerdo a su máxima frecuencia
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
                    docs.add(d); //Agrego los documentos a un arraylist auxiliar porque el contenido de "buscados" será reemplazado para cada término
                }
            }
        }
        
        int cantidadDocs = 0;
        for (Documento d : docs)
        {
            if (cantidadDocs < R) 
            {
                if (!documentos.containsKey(d.getNombre())) //Si el documento actual no se encuentra en el Hashmap lo agrego
                {
                    documentos.put(d.getNombre(), d);
                    cantidadDocs ++;
                }
                else //Si el documento ya se encuentra en el Hashmap incremento su peso
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
    
    //Para cada documento relevante, busca las palabras de la búsqueda para las cuales hay coincidencia
    private void agregarCoincidencias()
    {
        BD posteo = new BD();
        posteo.setConnectionMode(2);
        
        for (Iterator it = documentos.values().iterator(); it.hasNext();)
        {
            ArrayList<String> coincidencias;
            Documento doc = (Documento) it.next();
            
            try 
            {
                posteo.openConnection();
                coincidencias = posteo.obtenerCoincidencias(busqueda, doc.getNombre());
                for (String palabra : coincidencias)
                {
                    doc.agregarCoincidencia(palabra);
                }
            } 
            catch (Exception ex) 
            {
                Logger.getLogger(Buscador.class.getName()).log(Level.SEVERE, null, ex);
            }        
            finally
            {
                posteo.closeConnection();
            }
        }
    }
    
    private void ordenarPalabrasBuscadas()
    {
        if(!busqueda.contains(null))Collections.sort(busqueda);
    }
    
    //Ordena los documentos relevantes de acuerdo a su peso
    private LinkedHashMap<String, Documento> ordenarPorRelevancia() 
    {
        this.agregarCoincidencias();
        this.ajustarPeso();
                
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
    
    private void ajustarPeso()
    {
        BD posteo = new BD();
        posteo.setConnectionMode(2);
        try
        {
            posteo.openConnection();

            for (Documento d : documentos.values())
            {
                double[] vector = posteo.calcularCociente(d.getNombre(), N, v);
                double cociente = vector[0];
                d.agregarFactorDeAjustePeso(cociente);
                d.setCantidadPalabras((int) vector[1]);
            }
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
