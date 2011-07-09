/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.univaq.idw.librionline.controller;

import it.univaq.idw.librionline.framework.util.SecurityLayer;
import it.univaq.idw.librionline.framework.util.TemplateResult;
import it.univaq.idw.librionline.model.Autore;
import it.univaq.idw.librionline.model.LibriOnLineDataLayer;
import it.univaq.idw.librionline.model.impl.LibriOnLineDataLayerMysqlImpl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Zilfio
 */
public class UpdateAutore extends HttpServlet {

    private boolean analizza_form_autore(HttpServletRequest request, HttpServletResponse response) {
        String cognome = request.getParameter("updateautore_cognome");
        String nome = request.getParameter("updateautore_nome");
        
        if((cognome == null || cognome.isEmpty()) || (nome == null || nome.isEmpty())){
            return false;
        }
        return true;
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
        LibriOnLineDataLayer dl = new LibriOnLineDataLayerMysqlImpl();
        
        if(session != null){
            request.setAttribute("stato_log", "Logout");

            if(dl.isAdmin((String)session.getAttribute("username"))){
                request.setAttribute("bibliotecario",true);
                request.setAttribute("tipologia_utente","Bibliotecario");
                
                String update = request.getParameter("Modifica Autore");
                
                if(update == null){
                    String id = request.getParameter("id");
                    Autore autore = dl.getAutore(Integer.parseInt(id));
                    
                    request.setAttribute("title", "Modifica Autore");
                    request.setAttribute("autore", autore);
                    res.activate("backoffice_updateautori.ftl.html", request, response);
                }
                
                else if(update.equals("Modifica Autore")){
                    String id = request.getParameter("updateautore_id");
                    int id_autore = Integer.parseInt(id);
                    String cognomeautore = request.getParameter("updateautore_cognome");
                    String nomeautore = request.getParameter("updateautore_nome");
                   
                    boolean result = analizza_form_autore(request, response);
                    if(result){
                        if(dl.modificaAutore(id_autore, cognomeautore,nomeautore)){
                            request.setAttribute("messaggio", "Autore modificato correttamente!");
                        }
                        else{
                            request.setAttribute("messaggio", "Autore non modificato!");
                        }
                        Autore object_autore = dl.getAutore(id_autore);
                        request.setAttribute("autore", object_autore);
                        request.setAttribute("title", "Modifica Autore");
                        res.activate("backoffice_updateautori.ftl.html", request, response);
                    }
                    else{
                        Autore object_autore = dl.getAutore(id_autore);
                        request.setAttribute("autore", object_autore);
                        request.setAttribute("title", "Modifica Autore");
                        request.setAttribute("messaggio", "Impossibile modificare l'Autore!");
                        res.activate("backoffice_updateautori.ftl.html", request, response);
                    }
                }
                else{
                    String id = request.getParameter("updateautore_id");
                    int id_autore = Integer.parseInt(id);
                    if(dl.eliminaAutore(id_autore)){
                         response.sendRedirect("VisualizzaAutori");
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
