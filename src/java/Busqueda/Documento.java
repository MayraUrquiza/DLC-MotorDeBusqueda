/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Busqueda;

import Vocabulario.Termino;
import java.io.File;

/**
 *
 * @author mayur
 */
public class Documento implements Comparable
{
    private String nombre;
    private File documento;
    private double peso;
    
    public Documento()
    {
        
    }
    
    public Documento(String nombre)
    {
        String ruta = "C:\\Users\\mayur\\Desktop\\DocumentosTP1\\";
        this.nombre = nombre;
        this.documento = new File(ruta + nombre);
        this.peso = 0.0;
    }

    public String getNombre() 
    {
        return nombre;
    }

    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }

    public File getDocumento() 
    {
        return documento;
    }

    public void setDocumento(File documento) 
    {
        this.documento = documento;
    }

    public double getPeso() 
    {
        return peso;
    }

    public void setPeso(double peso) 
    {
        this.peso = peso;
    }
    
    public void calcularPeso(Termino t, int N, int tf)
    {
        peso += (double) tf * Math.log10((double) N / (double) t.getNroDocumentos());
    }

    @Override
    public int compareTo(Object doc) 
    {
        if (this.getPeso() < ((Documento)doc).getPeso())
        {
            return 1;
        }
        else if (this.getPeso() == ((Documento)doc).getPeso())
        {
            return 0;
        }
        else
        {
            return -1;
        }
    }
}
