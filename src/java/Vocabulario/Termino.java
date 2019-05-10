/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vocabulario;

import java.io.Serializable;

/**
 *
 * @author mayur
 */
public class Termino implements Serializable, Comparable
{
    private String palabra; 
    private int maxFrecuencia; //máxima cantidad de veces que aparece el término en alguno de los documentos.
    private int nroDocumentos; //cantidad de documentos donde aparece el término. (nr)
    
    public Termino(String palabra, int maxFrecuencia)
    {
        this.palabra = palabra;
        this.maxFrecuencia = maxFrecuencia;
        this.nroDocumentos = 1;
    }

    public String getPalabra()
    {
        return palabra;
    }
    
    public int getMaxFrecuencia() 
    {
        return maxFrecuencia;
    }

    public void setMaxFrecuencia(int maxFrecuencia) 
    {
        this.maxFrecuencia = maxFrecuencia;
    }

    public int getNroDocumentos() 
    {
        return nroDocumentos;
    }

    public void setNroDocumentos(int nroDocumentos) 
    {
        this.nroDocumentos = nroDocumentos;
    }

    @Override
    public String toString() 
    {
        return "TÉRMINO: " + palabra + ", frecuencia máxima de apariciones: " 
                + maxFrecuencia + ", cantidad de documentos en los que aparece: " 
                + nroDocumentos;
    }

    @Override
    public int compareTo(Object t) 
    {
        if (this.nroDocumentos < ((Termino)t).nroDocumentos)
        {
            return -1;
        }
        else if (this.nroDocumentos == ((Termino)t).nroDocumentos)
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }
}
