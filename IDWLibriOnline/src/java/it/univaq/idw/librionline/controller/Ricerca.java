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
public class Ricerca extends HttpServlet {
    
    private List analizza_ricerca_avanzata(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        String titolo = request.getParameter("titolo_avanzato");
        String tag = request.getParameter("tag_avanzato");
        String autore = request.getParameter("autore_avanzato");
        String isbn = request.getParameter("isbn_avanzato");
        
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        
        if(titolo != null){
            List<Libro> lc = dl.searchByTitle(titolo);
            
            if(lc.isEmpty()){
                return null;
            }
            else{
                return lc;
            }
        }
        return null;
    }
    
    private List analizza_ricerca_base(HttpServletRequest request) {
        
        List<Libro> bl = null;
        
        String titolo = request.getParameter("titolo");
        
        if(titolo.equals("")){
            return bl;
        }
        
        //Questo è l'oggetto che devi dichiarare per qualsiasi interazione con il DB
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();

        //Questa è la funzione da richiamare per la ricerca base
        //Attenzion! restituisco una collezione di libri! Perchè più libri potrebbero avere lo stesso titolo
        List<Libro> bc = dl.simpleBookSearch(titolo);
        
        if(bc.isEmpty()){
            return null;
        }
        
        return bc;
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
                    request.setAttribute("stato_log", "logout");
                }
                
                String s = request.getParameter("Invia");                             
                
                if("Ricerca avanzata".equals(s)){
                    request.setAttribute("title","Risultati Ricerca Avanzata");
                    request.setAttribute("libri", analizza_ricerca_avanzata(request,response));
                    res.activate("risultati_ricerca_avanzata.ftl.html", request, response);
                }
                else if("Ricerca".equals(s)){
                    request.setAttribute("title","Risultati Ricerca Base"); 
                    request.setAttribute("libri",analizza_ricerca_base(request));
                    res.activate("risultati_ricerca_base.ftl.html", request, response);
                    response.sendRedirect("Home");
                }
                else{
                    request.setAttribute("title","Ricerca Avanzata");
                    res.activate("form_ricerca_avanzata.ftl.html", request, response);
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
        return "Servlet RicercaAvanzata";
    }// </editor-fold>


}
