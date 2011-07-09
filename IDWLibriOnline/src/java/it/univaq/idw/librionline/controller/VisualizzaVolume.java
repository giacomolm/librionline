/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.Libro;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zilfio
 */
public class VisualizzaVolume extends HttpServlet {
    
    private List analizza_form_ricerca_titolo_libro(HttpServletRequest request, HttpServletResponse response) {
        
        String titolo = request.getParameter("ricerca_libro_titolo2");
        
        if(titolo == null || titolo.isEmpty()){
            return null;
        }
        
        LibriOnLineDataLayer model = new LibriOnLineDataLayerMysqlImpl();
        
        List<Libro> libro = model.searchByTitle(titolo);
        
        if(libro.isEmpty()){
            return null;
        }
        else{
            return libro;
        }
        
        
    }
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TemplateResult res = new TemplateResult(getServletContext());
        HttpSession session = SecurityLayer.checkSession(request);
        
        if(session != null){
            request.setAttribute("stato_log", "Logout");

            LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();

            if(dl.isAdmin((String)session.getAttribute("username"))){
                request.setAttribute("bibliotecario",true);
                request.setAttribute("tipologia_utente","Bibliotecario");
                
                String search_title = request.getParameter("Ricerca Libro Titolo");
                
                if(search_title == null){
                    request.setAttribute("title","Ricerca Libro Titolo");
                    request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='Visualizza'>Modifica</a>");
                    res.activate("backoffice_ricercalibro2.ftl.html", request, response);
                }
                else{
                    List<Libro> libri = analizza_form_ricerca_titolo_libro(request,response);
                        request.setAttribute("title","Risultati Ricerca Libri");
                        request.setAttribute("libri",libri);
                        request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='Visualizza'>Modifica</a> -> <a href='VisualizzaVolume'>ModificaVolume</a>");
                        res.activate("backoffice_risultatiricercalibri2.ftl.html", request, response);     
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
        processRequest(request, response);
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
        processRequest(request, response);
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
