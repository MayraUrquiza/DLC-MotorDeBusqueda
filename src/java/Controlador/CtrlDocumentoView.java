/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mayur
 */
public class CtrlDocumentoView extends HttpServlet {

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
        String dest = "/error.jsp";
        
        String doc = request.getParameter("documento");
        
        FilenameFilter filter = new FilenameFilter() //creo un filtro para archivos .txt
        {
            public boolean accept(File dir, String fileName)
            {
                return fileName.endsWith("txt");
            }
        };

        File f = new File("C:\\NetBeansProjects\\DLC-MotorDeBusqueda\\DocumentosTP1");
        String [] archivos = f.list(filter);
        StringBuilder sb = new StringBuilder("");
        
        if(archivos != null)
        {
            for(int i = 0; i < archivos.length; i++)
            {
                if(archivos[i].equals(doc))
                {
                    try (Scanner sc = new Scanner(new File(f.toString() + "\\" + archivos[i]))) 
                    {
                        while(sc.hasNextLine())
                        {
                            sb.append(sc.nextLine());
                        }
                    }
                }
            }
        }
        
        if(!(sb.toString().equals("")))
        {
            request.setAttribute("contenido", sb.toString());
            request.setAttribute("titulo", doc);
            dest = "/documentoView.jsp";
        }
        ServletContext app = this.getServletContext();
        RequestDispatcher dis = app.getRequestDispatcher(dest);
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
