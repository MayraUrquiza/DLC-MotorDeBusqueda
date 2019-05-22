/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import BD.BD;
import Busqueda.Buscador;
import Serializacion.VocabularioIOException;
import Serializacion.VocabularioWriter;
import Vocabulario.Vocabulario;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mayur
 */
public class CtrlAgregarDoc extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        HttpSession session = request.getSession(true);
        Vocabulario v = (Vocabulario) session.getAttribute("vocabulario");
        
        String nombre = request.getParameter("nombre");
        String contenido = request.getParameter("contenido");
        
        String ruta = "C:\\NetBeansProjects\\DLC-MotorDeBusqueda\\DocumentosTP1\\";
        String archivo = nombre + ".txt";

        try
        {
            File arch = new File(ruta + archivo);
            FileWriter fw = new FileWriter(arch);
            fw.write(contenido);
            fw.close();
            this.agregarDocumento(v, ruta, archivo);
        }
        catch(IOException e)
        {
            System.out.println("Error al escribir el archivo.");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void agregarDocumento(Vocabulario v, String ruta, String archivo)
    {
        this.eliminar("C:\\NetBeansProjects\\DLC-MotorDeBusqueda\\posteo.txt");
        try 
        {
            v.agregarDocumento(ruta, archivo, "C:\\NetBeansProjects\\DLC-MotorDeBusqueda\\posteo.txt");
            Buscador buscador = new Buscador();
            buscador.setN(buscador.getN() + 1);
        } 
        catch (IOException ex) 
        {
            System.out.println("No se pudo agregar el archivo.");
        }
        
        // Serializo el vocabulario con los términos recién agregados
        this.serializar(v);

        // Cargo el posteo del nuevo archivo en la base de datos
        this.cargarPosteo("C:\\\\NetBeansProjects\\\\DLC-MotorDeBusqueda\\\\posteo.txt");
    }
    
    private void eliminar(String archivo)
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
    
    private void serializar(Vocabulario v)
    {
        VocabularioWriter vw = new VocabularioWriter();
        try
        {
            vw.write(v);
            System.out.println("Hashtable serializada correctamente.");
        }
        catch (VocabularioIOException ex)
        {
            System.out.println("No se pudo escribir la hashtable.");
        }
    }
    
    private void cargarPosteo(String archivo)
    {
        BD posteo = new BD();
        try 
        {
            posteo.openConnection();
            posteo.loadData(archivo, "palabraXDocumento");
            System.out.println("Posteo cargado correctamente.");
        } 
        catch (Exception ex) 
        {
            System.out.println("No se pudo cargar el posteo.");
            String error = ex.getMessage();
            System.out.println(error);
        }
        finally
        {
            posteo.closeConnection();
        }
    }
}
