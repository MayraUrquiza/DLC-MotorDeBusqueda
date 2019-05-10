/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Busqueda.Buscador;
import Busqueda.Documento;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mayur
 */
@WebServlet(name = "ControladorPaginaPrincipal", urlPatterns = {"/ControladorPaginaPrincipal"})
public class CtrlPaginaPrincipal extends HttpServlet {

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
        String busqueda = request.getParameter("busqueda");
        StringTokenizer st = new StringTokenizer(busqueda, "\"’,.-_+&<>``={}~^@/()[]%'*$|°[0-1-2-3-4-5-6-7-8-9]#:*»«?¡!¿; \n");
        ArrayList<String> palabrasBuscadas = new ArrayList<>();
        while(st.hasMoreTokens())
        {
            String p = st.nextToken().toLowerCase();
            palabrasBuscadas.add(p);
        }
        Buscador.agregarDocumentos();
        HashMap<String, Documento> documentos = Buscador.getDocumentos();
        
        ArrayList<String> docs = new ArrayList<>();
        ArrayList<Double> pesos = new ArrayList<>();
        for (String documento : documentos.keySet())
        {
            docs.add(documento);
            pesos.add(documentos.get(documento).getPeso());
        }

        request.setAttribute("documentos", docs);
        request.setAttribute("pesos", pesos);
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
