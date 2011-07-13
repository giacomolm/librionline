/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zilfio
 */
public class InserisciAutore extends HttpServlet {
    
    private boolean analizza_form_autore(HttpServletRequest request, HttpServletResponse response) {
      
        String cognome = request.getParameter("insertauthor_cognome");
        String nome = request.getParameter("insertauthor_nome");
        
        if((cognome == null || cognome.isEmpty())||(nome == null || nome.isEmpty())){
            return false;
        }
        
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();

        if(dl.insertAutore(cognome, nome)){
            return true;
        }
        else{
            return false;
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
                
                String insert_tag = request.getParameter("Inserisci Autore");
                
                if(insert_tag == null){
                    request.setAttribute("title","Inserisci Autore");
                    request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='Inserisci'>Inserisci</a>");
                    res.activate("backoffice_inserisciautore.ftl.html", request, response);
                }
                else{
                    boolean result = analizza_form_autore(request,response);
                    if(result){
                        request.setAttribute("title","Inserisci Autore");
                        request.setAttribute("messaggio","L'Autore è stato inserito correttamente!");
                        request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='Inserisci'>Inserisci</a>");
                        res.activate("backoffice_inserisciautore.ftl.html", request, response);
                    }
                    else{
                        request.setAttribute("title","Inserisci Autore");
                        request.setAttribute("messaggio","Inserimento Autore fallito: Si prega di compilare bene i campi sottostanti!");
                        request.setAttribute("navigazione","<a href='Home'>Homepage</a> -> <a href='Inserisci'>Inserisci</a>");
                        res.activate("backoffice_inserisciautore.ftl.html", request, response);
                    }
                    
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
