/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Busqueda.Buscador;
import Busqueda.Documento;
import Vocabulario.Termino;
import Vocabulario.Vocabulario;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mayur
 */
public class CtrlBusqueda extends HttpServlet {

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
        HttpSession sesion = request.getSession();
        Vocabulario v = (Vocabulario) sesion.getAttribute("vocabulario");
        ArrayList<String> voc = v.obtenerVocabulario(v);
        
        String busqueda = request.getParameter("busqueda");
        StringTokenizer st = new StringTokenizer(busqueda, "\"’,.-_+&<>``={}~^@/()[]%'*$|°[0-1-2-3-4-5-6-7-8-9]#:*»«?¡!¿; \n");
        ArrayList<String> palabrasBuscadas = new ArrayList<>();
        while(st.hasMoreTokens())
        {
            String p = st.nextToken().toLowerCase();
            palabrasBuscadas.add(p);
        }
        //hasta acá bien, palabrasBuscadas se puede mostrar en jsp
        
        ArrayList<Termino> terminos = Buscador.getResultados(palabrasBuscadas, v);
        //prueba
        ArrayList<String> terms = new ArrayList<>();
        for (Termino t : terminos)
        {
            if(t != null)System.out.println(t.getPalabra());
            if(t != null)terms.add(t.getPalabra());
        }
        //
        
        Buscador.agregarDocumentos();
        HashMap<String, Documento> documentos = Buscador.getDocumentos();
        //prueba
        
        ArrayList<String> docs = new ArrayList<>();
        ArrayList<Double> pesos = new ArrayList<>();
        for (Iterator it = documentos.keySet().iterator(); it.hasNext();)
        {
            Documento d = (Documento) documentos.get((String)it.next());
            System.out.println(d.getNombre() + " - Peso: " + d.getPeso());
            docs.add(d.getNombre());
            pesos.add(d.getPeso());
        }
        //

        request.setAttribute("documentos", voc);
        request.setAttribute("pesos", pesos);
        ServletContext app = this.getServletContext();
        RequestDispatcher dis = request.getRequestDispatcher("resultados.jsp");
        dis.forward(request, response);
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
    
}