/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.Copiaelettronica;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zilfio
 */
public class UpdateCopiaElettronica extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NamingException {
        TemplateResult res = new TemplateResult(getServletContext());
        HttpSession session = SecurityLayer.checkSession(request);
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        
        if(session != null){
            request.setAttribute("stato_log", "Logout");

            if(dl.isAdmin((String)session.getAttribute("username"))){
                request.setAttribute("bibliotecario",true);
                request.setAttribute("tipologia_utente","Bibliotecario");

                String id_copia_elettronica = request.getParameter("id");
                String isbn = request.getParameter("isbn");
                Copiaelettronica ce = dl.getCopiaElettronica(Integer.parseInt(id_copia_elettronica));

                if(ce != null){
                        InitialContext ctx = new InitialContext();
                        String path = getServletContext().getInitParameter("copieelettroniche");
                        String filename = (getServletContext().getRealPath(path+"/"+ce.getUrl()));
                        
                        File f = new File(filename);
                        if (!f.exists())
                        throw new IllegalArgumentException(
                              "Delete: no such file or directory: " + filename);

                        if (!f.canWrite())
                          throw new IllegalArgumentException("Delete: write protected: "
                              + filename);

                        // If it is a directory, make sure it is empty
                        if (f.isDirectory()) {
                          String[] files = f.list();
                          if (files.length > 0)
                            throw new IllegalArgumentException(
                                "Delete: directory not empty: " + filename);
                        }

                        // Attempt to delete it
                        boolean success = f.delete();
                        if(success){
                            dl.eliminaCopiaElettronica(Integer.parseInt(id_copia_elettronica));
                            response.sendRedirect("VisualizzaCopieElettroniche2?isbn="+isbn);
                        }

                        if (!success)
                          throw new IllegalArgumentException("Delete: deletion failed");
                        }
                    }
                    else{
                        request.setAttribute("bibliotecario",false);
                        request.setAttribute("tipologia_utente","Utente");
                        response.sendRedirect("Home");
                    }
            }
            else{
                response.sendRedirect("Home");
            }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(UpdateCopiaElettronica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(UpdateCopiaElettronica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
