/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serializacion;

import Vocabulario.Vocabulario;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 *
 * @author mayur
 */
public class VocabularioReader 
{
    private String arch = "vocabulario.dat";
    
      /**
       * Crea un objeto HashtableReader. Asume que el nombre del archivo desde el 
       * cual se recupera es "vocabulario.dat".
       */
      public VocabularioReader()
      {
          
      }
      
      /**
       * Crea un objeto HashtableReader. Fija el nombre del archivo desde el cual 
       * se recupera con el nombre tomado como parametro.
       * @param nom el nombre del archivo a abrir para iniciar la recuperacion.
       */
      public VocabularioReader(String nom)
      {
          arch = nom;
      }
      
      
      /**
       * Recupera una hashtable desde un archivo serializado.
       * @throws VocabularioIOException si se encuentra un error de IO.
       * @return una referencia al objeto recuperado.
       */
      public Vocabulario read() throws VocabularioIOException
      {
           Vocabulario v = null;
           
           try
           {
                 FileInputStream istream = new FileInputStream(arch);
                 ObjectInputStream p = new ObjectInputStream(istream);
          
                 v = (Vocabulario) p.readObject();
                 
                 p.close();
                 istream.close();
           }
           catch (Exception e)
           {
             throw new VocabularioIOException("No se pudo recuperar la hashtable.");
           }
           
           return v;
       }
}
