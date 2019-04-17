/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vocabulario;

/**
 *
 * @author mayur
 */
public class Termino 
{
    private String palabra; 
    private int maxFrecuencia; //máxima cantidad de veces que aparece el término en alguno de los documentos.
    private int nroDocumentos; //cantidad de documentos donde aparece el término.
    
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
}
