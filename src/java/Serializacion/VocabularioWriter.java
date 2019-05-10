/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Serializacion;

import Vocabulario.Vocabulario;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author mayur
 */
public class VocabularioWriter 
{
    // nombre del archivo serializado...
      private String arch = "vocabulario.dat";
    
      /**
       * Crea un objeto HashtableWriter. Supone que el nombre del archivo a grabar 
       * sera "vocabulario.dat".
       */
      public VocabularioWriter()
      {
          
      }
      
      /**
       * Crea un objeto HashtableWriter. Fija el nombre del archivo que se graba con 
       * el nombre tomado como parametro.
       * @param nom el nombre del archivo a grabar.
       */
      public VocabularioWriter(String nom)
      {
            arch = nom;
      }
      
      /**
       * Graba la hashtable tomada como parametro.
       * @throws VocabularioIOException si se encuentra un error de IO.
       */
      public void write (Vocabulario v) throws VocabularioIOException
      {
           try
           {
             FileOutputStream ostream = new FileOutputStream(arch);
             ObjectOutputStream p = new ObjectOutputStream(ostream);
      
             p.writeObject(v);
      
             p.flush(); 
             ostream.close();
           }
           catch ( Exception e )
           {
             throw new VocabularioIOException("No se pudo grabar la hashtable.");
           }
      }
}
